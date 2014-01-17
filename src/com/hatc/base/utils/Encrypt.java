package com.hatc.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import sun.misc.BASE64Decoder;

public class Encrypt {

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
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DESede");
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
	 * 去掉字符串的换行符号 base64编码3-DES的数据时，得到的字符串有换行符号 ，一定要去掉，否则uni-wise平台解析票根不会成功，
	 * 提示“sp验证失败”。在开发的过程中，因为这个问题让我束手无策， 一个朋友告诉我可以问联通要一段加密后 的文字，然后去和自己生成的字符串比较，
	 * 这是个不错的调试方法。我最后比较发现我生成的字符串唯一不同的 是多了换行。 我用c#语言也写了票根请求程序，没有发现这个问题。
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
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DESede");
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
		Encrypt.createKey("c:/d.key");

	}

}
