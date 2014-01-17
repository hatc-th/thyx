package com.hatc.base.utils;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 安全相关（加密/解密）<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2007-04-06<br/>
*
**/
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import sun.misc.BASE64Decoder;

public class Security {

	/**
	 * 静态成员初始化,BASE64编码表
	 */
	final private static String encodeTable = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

	/**
	 * BASE64编码器,编码算法如下: </b>
	 * <1>.将数据按3个字节一组分成数块； </b>
	 * <2>.每块将3个8位的数据转换成4个6位数据段； 11111111 00000000 11111111 ---- 111111 110000</b>
	 * 000011 111111 12345678 12345678 12345678 ---- 123456 781234 567812 345678 </b>
	 * <3>.根据Base64字符表得到4个6位数据段对应的字符；</b>
	 * <4>.如果最后一块只有两个字节，则添加两个0位，转换成对应Base64字符表的三个字符，并在结尾添一个'='字符；</b>
	 * 如果最后一块只有一个字节，则添加四个0位，转换成对应Base64字符表的两个字符，并在结尾添两个'='字符。
	 * 
	 * @param data
	 *            需要编码的数据
	 * @return 返回编码后结果
	 */
	public static String BASE64Encode(byte[] data) {
		// 判断输入数据的有效性
		if (data == null) {
			return null;
		}
		// 声明保存结构的缓冲区
		StringBuffer encoded = new StringBuffer();
		// 循环开始编码
		int i, individual, remain = 0;
		for (i = 0; i < data.length; i++) {
			// 把BYTE数据类型转换成int
			individual = data[i] & 0x000000ff;
			switch (i % 3) {
			case 0:
				// 保存编码数据
				encoded.append(encodeTable.charAt(individual >> 2));
				// 保留需移动的位
				remain = (individual << 4) & 0x30;
				break;
			case 1:
				// 保存编码数据
				encoded.append(encodeTable.charAt(remain | individual >> 4));
				// 保留需移动的位
				remain = (individual << 2) & 0x3c;
				break;
			case 2:
				// 保存编码数据
				encoded.append(encodeTable.charAt(remain | individual >> 6));
				// 保存编码数据
				encoded.append(encodeTable.charAt(individual & 0x3f));
				break;
			}
			// 判断换行
			if (((i + 1) % 57) == 0)
				encoded.append("\r\n");
		}
		// 末尾补位
		switch (i % 3) {
		case 1:
			// 补四个0位，生成两个Base64字符,末尾加==
			encoded.append(encodeTable.charAt(remain));
			encoded.append("==");
			break;
		case 2:
			// 补两个0位，生成三个Base64字符,末尾加=
			encoded.append(encodeTable.charAt(remain));
			encoded.append('=');
			break;
		}
		// 返回结果
		return encoded.toString();
	}

	/**
	 * BASE64解码,解码算法如下 </b>
	 * <1>.将数据按4个字节一组分成数块； </b>
	 * <2>.每块将4个字符去掉最高两位并转换成3个8位的数据段； </b>
	 * 注意：数据块中的字符取值不是ASCII集的值，而是Base64字符表中相对应的索引值！ 00</b>
	 * 111111 00 110000 00 000011 00 111111 ---- 11111111 00000000 11111111 </b>
	 * <3>.根据ASCII字符集得到3个8位数据段对应的字符；
	 * <4>.如果最后一块只有两个'='，去掉两个'='，并去掉最低两位，转换成对应ASSCII字符集的两个字符； </b>
	 * 如果最后一块只有一个'='，去掉'='，并去掉最低四位，转换成对应ASSCII字符集的一个字符。</b>
	 * 
	 * @param encodedData
	 *            需要解码的数据
	 * @return 解码后的数据
	 */
	public static byte[] BASE64Decode(String encodedData) {
		byte[] data = encodedData.getBytes();
		// 分配解码后数据缓冲区
		ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
		// 循环解码,每四个字节分为一个处理数据块
		int nBlockIndex = 0;
		byte individual = 0, remain = 0;
		for (int i = 0; i < data.length; i++) {
			// 过滤回车换行符
			if (data[i] == '\r' || data[i] == '\n')
				continue;
			// 如果到达'=',处理完成
			if (data[i] == '=')
				break;
			// 读取一个块字符
			individual = decodeBase64Char((char) data[i]);
			// 针对在块的不同位置，分别处理
			switch ((nBlockIndex++) % 4) {
			case 0:
				remain = (byte) (individual << 2);
				break;
			case 1:
				output.write((char) (remain | (individual >> 4)));
				remain = (byte) (individual << 4);
				break;
			case 2:
				output.write((char) (remain | (individual >> 2)));
				remain = (byte) (individual << 6);
				break;
			case 3:
				output.write(remain | individual);
				break;
			}
		}
		// 返回解码后结果
		return output.toByteArray();
	}

	/**
	 * BASE64字符与其索引反解
	 * 
	 * @param code
	 *            编码字符
	 * @return 索引
	 */
	private static byte decodeBase64Char(char code) {
		if (code >= 'A' && code <= 'Z')
			return (byte) (code - 'A');
		else if (code >= 'a' && code <= 'z')
			return (byte) (code - 'a' + 26);
		else if (code >= '0' && code <= '9')
			return (byte) (code - '0' + 52);
		else if (code == '+')
			return 62;
		else if (code == '/')
			return 63;
		return 64;
	}

	/**
	 * <P>
	 * 用BASE64对字符串进行加密
	 * </P>
	 * 
	 * @param str
	 *            需要加密的字符串
	 * @return String 加密后的字符串
	 */
	public static String BASE64EncodeString(String str) {
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		return encoder.encodeBuffer(str.getBytes()).trim();
	}

	/**
	 * <P>
	 * 用BASE64对字符串进行加密
	 * </P>
	 * 
	 * @param str
	 *            需要加密的字符串
	 * @return String 加密后的字符串
	 */
	public static String BASE64EncodeString(byte[] str) {
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		return encoder.encodeBuffer(str).trim();
	}

	/**
	 * <P>
	 * 对用BASE64加密的字符串进行解密
	 * </P>
	 * 
	 * @param str
	 *            需要解密的字符串
	 * @return String 解密后的字符串
	 */
	public static String BASE64DecodeString(String str) {
		sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
		try {
			return new String(dec.decodeBuffer(str));
		} catch (IOException io) {
			throw new RuntimeException(io.getMessage(), io.getCause());
		}
	}

	/**
	 * 用MD5进行加密
	 */
	public static String MD5DecodeString(String str) {
		return decodeString(str, "MD5");
	}

	/**
	 * 用SHA进行加密
	 */
	public static String SHADecodeString(String str) {
		return decodeString(str, "SHA-1");
	}

	/**
	 * 用MD5进行加密
	 */
	public static byte[] MD5DecodeByte(byte[] str) {
		return decodeByte(str, "MD5");
	}

	/**
	 * 用SHA进行加密
	 */
	public static byte[] SHADecodeByte(byte[] str) {
		return decodeByte(str, "SHA-1");
	}

	private static String decodeString(String str, String method) {
		MessageDigest md = null;
		String dstr = null;

		try {
			// 生成一个MD5加密计算摘要
			md = MessageDigest.getInstance(method);
			md.update(str.getBytes());
			// digest()最后确定返回md5 hash值，返回值为8为字符串。
			// 因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			dstr = new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return dstr;
	}

	private static byte[] decodeByte(byte[] strSrc, String method) {
		byte[] returnByte = null;
		MessageDigest md = null;
		try {
			// 生成一个MD5加密计算摘要
			md = MessageDigest.getInstance(method);
			md.update(strSrc);
			// digest()最后确定返回md5 hash值，返回值为8为字符串。
			// 因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			byte[] e = md.digest();

			// 接口定义中密钥长度为24位，补0
			returnByte = new byte[24];
			int i = 0;
			while (i < e.length && i < 24) {
				returnByte[i] = e[i];
				i++;
			}

			if (i < 24) {
				returnByte[i] = 0;
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnByte;
	}

	public static final int TIME_EXPIRE = 1;

	/**
	 * 获得时间戳
	 */
	public static String getTimeStamp() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		String timeStamp = formatter.format(cal.getTime());
		return timeStamp;
	}

	/**
	 * 过期时间
	 */
	public static String getTimeExpire() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, TIME_EXPIRE);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		String timeStamp = formatter.format(cal.getTime());
		return timeStamp;
	}

	/**
	 * 获得摘要(通过SHA-1加密)
	 */
	public static String digest(String strSrc) {
		String digest = "";
		try {
			byte[] srcSHA = hash(strSrc);
			digest = Security.BASE64EncodeString(srcSHA);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return digest;
	}

	private static byte[] hash(String strSrc) {
		byte[] returnByte = null;
		try {
			returnByte = Security.SHADecodeByte(strSrc.getBytes("GBK"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnByte;
	}

	/**
	 * 3-DES加密
	 * 
	 * @param byte[]
	 *            src 要进行3-DES加密的byte[]
	 * @param byte[]
	 *            enKey 3-DES加密密钥
	 * @return byte[] 3-DES加密后的byte[]
	 */

	public static byte[] desEncrypt(byte[] src, byte[] enKey) {
		byte[] encryptedData = null;
		try {
			DESedeKeySpec dks = new DESedeKeySpec(enKey);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey key = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encryptedData = cipher.doFinal(src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedData;
	}

	/**
	 * 对字符串进行Base64编码
	 * 
	 * @param byte[]
	 *            src 要进行编码的字符
	 * 
	 * @return String 进行编码后的字符串
	 */

	public static String base64Encode(byte[] src) {
		String requestValue = "";
		try {
			requestValue = Security.BASE64EncodeString(src);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return requestValue;
	}

	/**
	 * 去掉字符串的换行符号 base64编码3-DES的数据时，得到的字符串有换行符号 ，一定要去掉，否则uni-wise平台解析票根不会成功， 提示“sp验证失败”。在开发的过程中，因为这个问题让我束手无策，
	 * 一个朋友告诉我可以问联通要一段加密后 的文字，然后去和自己生成的字符串比较， 这是个不错的调试方法。我最后比较发现我生成的字符串唯一不同的 是多了换行。 我用c#语言也写了票根请求程序，没有发现这个问题。
	 * 
	 */

	public static String filter(String str) {
		String output = null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			int asc = str.charAt(i);
			if (asc != 10 && asc != 13)
				sb.append(str.subSequence(i, i + 1));
		}
		output = new String(sb);
		return output;
	}

	/**
	 * 对字符串进行URLDecoder.encode(strEncoding)编码
	 * 
	 * @param String
	 *            src 要进行编码的字符串
	 * 
	 * @return String 进行编码后的字符串
	 */

	public static String uRLEncode(String src) {
		String requestValue = "";
		try {
			requestValue = URLEncoder.encode(src, "GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return requestValue;
	}

	/**
	 * 3-DES加密
	 * 
	 * @param String
	 *            src 要进行3-DES加密的String
	 * @param String
	 *            spkey分配的SPKEY
	 * @return String 3-DES加密后的String
	 */

	public static String dESEncrypt(String src, byte[] enKey) {
		String requestValue = "";
		try {
			// 要进行3-DES加密的内容在进行\"UTF-16LE\"取字节
			byte[] src2 = src.getBytes("UTF-16LE");
			// 进行3-DES加密后的内容的字节
			byte[] encryptedData = desEncrypt(src2, enKey);
			// 进行3-DES加密后的内容进行BASE64编码
			String base64String = base64Encode(encryptedData);
			// BASE64编码去除换行符后
			String base64Encrypt = filter(base64String);
			// 对BASE64编码中的HTML控制码进行转义的过程
			requestValue = uRLEncode(base64Encrypt);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return requestValue;
	}

	public static byte[] readKey(String filePath) {
		byte[] kb = null;
        /////////////////////////////////////////////////////////////////
		/// ningliyu add 2011-11-16 
		/// 关闭连接
		////////////////////////////////////////////////////////////////
		FileInputStream f = null;
		try {
			File file = new File(filePath);
			if (file.exists()) {
				f = new FileInputStream(filePath);
				int size = f.available();
				kb = new byte[size];
				f.read(kb);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(f!=null)
			{
				try{
					f.close();
				}catch(Exception e){
					
				}
			}
			
		}
		return kb;
	}

	/**
	 * 对字符串进行URLDecoder.decode(strEncoding)解码
	 * 
	 * @param String
	 *            src 要进行解码的字符串
	 * 
	 * @return String 进行解码后的字符串
	 */

	public static String uRLDecoderdecode(String src) {
		String requestValue = "";
		try {
			requestValue = URLDecoder.decode(src, "GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return requestValue;
	}

	/**
	 * 
	 * 进行3-DES解密（密钥匙等同于加密的密钥匙）。
	 * 
	 * @param byte[]
	 *            src 要进行3-DES解密byte[]
	 * @param String
	 *            spkey分配的SPKEY
	 * @return String 3-DES解密后的String
	 */
	public static String desCrypt(byte[] debase64, byte[] key) {
		String strDe = null;
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("DESede");
			DESedeKeySpec dks = new DESedeKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey sKey = keyFactory.generateSecret(dks);
			cipher.init(Cipher.DECRYPT_MODE, sKey);
			byte ciphertext[] = cipher.doFinal(debase64);
			strDe = new String(ciphertext, "UTF-16LE");
		} catch (Exception ex) {
			strDe = "";
			ex.printStackTrace();
		}
		return strDe;
	}

	/**
	 * 3-DES解密
	 * 
	 * @param String
	 *            src 要进行3-DES解密的String
	 * @param String
	 *            spkey分配的SPKEY
	 * @return String 3-DES加密后的String
	 */

	public static String dESDecrypt(String src, byte[] spkey) {
		String requestValue = "";
		try {

			// 得到3-DES的密钥匙
			// URLDecoder.decodeTML控制码进行转义的过程
			String URLValue = uRLDecoderdecode(src);
			// 进行3-DES加密后的内容进行BASE64编码
			BASE64Decoder base64Decode = new BASE64Decoder();
			byte[] base64DValue = base64Decode.decodeBuffer(URLValue);
			// 要进行3-DES加密的内容在进行\"UTF-16LE\"取字节
			requestValue = desCrypt(base64DValue, spkey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestValue;
	}

	public static void createKey(String filePath) {
		try {
			// 保存密钥
			FileOutputStream fout = new FileOutputStream(filePath);
			fout.write(initKey());
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] initKey() {
		try {
			// 生成加密用的Key
			KeyGenerator kg = KeyGenerator.getInstance("DESede");
			// 密钥长度
			kg.init(168);
			// 生成密钥
			SecretKey key = kg.generateKey();

			return key.getEncoded();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {

		// System.out.println(Encrypt.getTimeStamp());

		// String oldString = "毒素发";
		//
		// String SPKEY = "c:/d.key";
		// byte[] key = Encrypt.readKey(SPKEY);
		// System.out.println("1。分配的SPKEY为: " + SPKEY);
		// System.out.println("2。的内容为: " + oldString);
		// String reValue = Encrypt.dESEncrypt(oldString, key);
		// reValue = reValue.trim().intern();
		// System.out.println("进行3-DES加密后的内容: " + reValue);
		// String reValue2 = Encrypt.dESDecrypt(reValue, key);
		// System.out.println("进行3-DES解密后的内容: " + reValue2);
		//
		// System.out.println(Encrypt.getTimeStamp());
		//
		// System.out.println(Encrypt.getTimeExpire());

		// Calendar cal = Calendar.getInstance();
		// Calendar cal1 = Calendar.getInstance();
		//		
		// System.out.println(cal.getTime());
		// cal1.add(Calendar.HOUR_OF_DAY,1);
		// System.out.println(cal1.getTime());
		//		
		// System.out.println(cal1.after(cal));

		// String s ="100001$100002$10003";
		// String[] s1 = s.split("$");
		// System.out.println(s1.length);
		// Security.createKey("c:/d.key");
		System.out.println(Security.BASE64DecodeString("Tm90IExvZ29uIQ=="));

	}
}
