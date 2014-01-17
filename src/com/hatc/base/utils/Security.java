package com.hatc.base.utils;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ��ȫ��أ�����/���ܣ�<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2007-04-06<br/>
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
	 * ��̬��Ա��ʼ��,BASE64�����
	 */
	final private static String encodeTable = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

	/**
	 * BASE64������,�����㷨����: </b>
	 * <1>.�����ݰ�3���ֽ�һ��ֳ����飻 </b>
	 * <2>.ÿ�齫3��8λ������ת����4��6λ���ݶΣ� 11111111 00000000 11111111 ---- 111111 110000</b>
	 * 000011 111111 12345678 12345678 12345678 ---- 123456 781234 567812 345678 </b>
	 * <3>.����Base64�ַ���õ�4��6λ���ݶζ�Ӧ���ַ���</b>
	 * <4>.������һ��ֻ�������ֽڣ����������0λ��ת���ɶ�ӦBase64�ַ���������ַ������ڽ�β��һ��'='�ַ���</b>
	 * ������һ��ֻ��һ���ֽڣ�������ĸ�0λ��ת���ɶ�ӦBase64�ַ���������ַ������ڽ�β������'='�ַ���
	 * 
	 * @param data
	 *            ��Ҫ���������
	 * @return ���ر������
	 */
	public static String BASE64Encode(byte[] data) {
		// �ж��������ݵ���Ч��
		if (data == null) {
			return null;
		}
		// ��������ṹ�Ļ�����
		StringBuffer encoded = new StringBuffer();
		// ѭ����ʼ����
		int i, individual, remain = 0;
		for (i = 0; i < data.length; i++) {
			// ��BYTE��������ת����int
			individual = data[i] & 0x000000ff;
			switch (i % 3) {
			case 0:
				// �����������
				encoded.append(encodeTable.charAt(individual >> 2));
				// �������ƶ���λ
				remain = (individual << 4) & 0x30;
				break;
			case 1:
				// �����������
				encoded.append(encodeTable.charAt(remain | individual >> 4));
				// �������ƶ���λ
				remain = (individual << 2) & 0x3c;
				break;
			case 2:
				// �����������
				encoded.append(encodeTable.charAt(remain | individual >> 6));
				// �����������
				encoded.append(encodeTable.charAt(individual & 0x3f));
				break;
			}
			// �жϻ���
			if (((i + 1) % 57) == 0)
				encoded.append("\r\n");
		}
		// ĩβ��λ
		switch (i % 3) {
		case 1:
			// ���ĸ�0λ����������Base64�ַ�,ĩβ��==
			encoded.append(encodeTable.charAt(remain));
			encoded.append("==");
			break;
		case 2:
			// ������0λ����������Base64�ַ�,ĩβ��=
			encoded.append(encodeTable.charAt(remain));
			encoded.append('=');
			break;
		}
		// ���ؽ��
		return encoded.toString();
	}

	/**
	 * BASE64����,�����㷨���� </b>
	 * <1>.�����ݰ�4���ֽ�һ��ֳ����飻 </b>
	 * <2>.ÿ�齫4���ַ�ȥ�������λ��ת����3��8λ�����ݶΣ� </b>
	 * ע�⣺���ݿ��е��ַ�ȡֵ����ASCII����ֵ������Base64�ַ��������Ӧ������ֵ�� 00</b>
	 * 111111 00 110000 00 000011 00 111111 ---- 11111111 00000000 11111111 </b>
	 * <3>.����ASCII�ַ����õ�3��8λ���ݶζ�Ӧ���ַ���
	 * <4>.������һ��ֻ������'='��ȥ������'='����ȥ�������λ��ת���ɶ�ӦASSCII�ַ����������ַ��� </b>
	 * ������һ��ֻ��һ��'='��ȥ��'='����ȥ�������λ��ת���ɶ�ӦASSCII�ַ�����һ���ַ���</b>
	 * 
	 * @param encodedData
	 *            ��Ҫ���������
	 * @return ����������
	 */
	public static byte[] BASE64Decode(String encodedData) {
		byte[] data = encodedData.getBytes();
		// �����������ݻ�����
		ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
		// ѭ������,ÿ�ĸ��ֽڷ�Ϊһ���������ݿ�
		int nBlockIndex = 0;
		byte individual = 0, remain = 0;
		for (int i = 0; i < data.length; i++) {
			// ���˻س����з�
			if (data[i] == '\r' || data[i] == '\n')
				continue;
			// �������'=',�������
			if (data[i] == '=')
				break;
			// ��ȡһ�����ַ�
			individual = decodeBase64Char((char) data[i]);
			// ����ڿ�Ĳ�ͬλ�ã��ֱ���
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
		// ���ؽ������
		return output.toByteArray();
	}

	/**
	 * BASE64�ַ�������������
	 * 
	 * @param code
	 *            �����ַ�
	 * @return ����
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
	 * ��BASE64���ַ������м���
	 * </P>
	 * 
	 * @param str
	 *            ��Ҫ���ܵ��ַ���
	 * @return String ���ܺ���ַ���
	 */
	public static String BASE64EncodeString(String str) {
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		return encoder.encodeBuffer(str.getBytes()).trim();
	}

	/**
	 * <P>
	 * ��BASE64���ַ������м���
	 * </P>
	 * 
	 * @param str
	 *            ��Ҫ���ܵ��ַ���
	 * @return String ���ܺ���ַ���
	 */
	public static String BASE64EncodeString(byte[] str) {
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		return encoder.encodeBuffer(str).trim();
	}

	/**
	 * <P>
	 * ����BASE64���ܵ��ַ������н���
	 * </P>
	 * 
	 * @param str
	 *            ��Ҫ���ܵ��ַ���
	 * @return String ���ܺ���ַ���
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
	 * ��MD5���м���
	 */
	public static String MD5DecodeString(String str) {
		return decodeString(str, "MD5");
	}

	/**
	 * ��SHA���м���
	 */
	public static String SHADecodeString(String str) {
		return decodeString(str, "SHA-1");
	}

	/**
	 * ��MD5���м���
	 */
	public static byte[] MD5DecodeByte(byte[] str) {
		return decodeByte(str, "MD5");
	}

	/**
	 * ��SHA���м���
	 */
	public static byte[] SHADecodeByte(byte[] str) {
		return decodeByte(str, "SHA-1");
	}

	private static String decodeString(String str, String method) {
		MessageDigest md = null;
		String dstr = null;

		try {
			// ����һ��MD5���ܼ���ժҪ
			md = MessageDigest.getInstance(method);
			md.update(str.getBytes());
			// digest()���ȷ������md5 hashֵ������ֵΪ8Ϊ�ַ�����
			// ��Ϊmd5 hashֵ��16λ��hexֵ��ʵ���Ͼ���8λ���ַ�
			// BigInteger������8λ���ַ���ת����16λhexֵ�����ַ�������ʾ���õ��ַ�����ʽ��hashֵ
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
			// ����һ��MD5���ܼ���ժҪ
			md = MessageDigest.getInstance(method);
			md.update(strSrc);
			// digest()���ȷ������md5 hashֵ������ֵΪ8Ϊ�ַ�����
			// ��Ϊmd5 hashֵ��16λ��hexֵ��ʵ���Ͼ���8λ���ַ�
			// BigInteger������8λ���ַ���ת����16λhexֵ�����ַ�������ʾ���õ��ַ�����ʽ��hashֵ
			byte[] e = md.digest();

			// �ӿڶ�������Կ����Ϊ24λ����0
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
	 * ���ʱ���
	 */
	public static String getTimeStamp() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		String timeStamp = formatter.format(cal.getTime());
		return timeStamp;
	}

	/**
	 * ����ʱ��
	 */
	public static String getTimeExpire() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, TIME_EXPIRE);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		String timeStamp = formatter.format(cal.getTime());
		return timeStamp;
	}

	/**
	 * ���ժҪ(ͨ��SHA-1����)
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
	 * 3-DES����
	 * 
	 * @param byte[]
	 *            src Ҫ����3-DES���ܵ�byte[]
	 * @param byte[]
	 *            enKey 3-DES������Կ
	 * @return byte[] 3-DES���ܺ��byte[]
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
	 * ���ַ�������Base64����
	 * 
	 * @param byte[]
	 *            src Ҫ���б�����ַ�
	 * 
	 * @return String ���б������ַ���
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
	 * ȥ���ַ����Ļ��з��� base64����3-DES������ʱ���õ����ַ����л��з��� ��һ��Ҫȥ��������uni-wiseƽ̨����Ʊ������ɹ��� ��ʾ��sp��֤ʧ�ܡ����ڿ����Ĺ����У���Ϊ����������������޲ߣ�
	 * һ�����Ѹ����ҿ�������ͨҪһ�μ��ܺ� �����֣�Ȼ��ȥ���Լ����ɵ��ַ����Ƚϣ� ���Ǹ�����ĵ��Է����������ȽϷ��������ɵ��ַ���Ψһ��ͬ�� �Ƕ��˻��С� ����c#����Ҳд��Ʊ���������û�з���������⡣
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
	 * ���ַ�������URLDecoder.encode(strEncoding)����
	 * 
	 * @param String
	 *            src Ҫ���б�����ַ���
	 * 
	 * @return String ���б������ַ���
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
	 * 3-DES����
	 * 
	 * @param String
	 *            src Ҫ����3-DES���ܵ�String
	 * @param String
	 *            spkey�����SPKEY
	 * @return String 3-DES���ܺ��String
	 */

	public static String dESEncrypt(String src, byte[] enKey) {
		String requestValue = "";
		try {
			// Ҫ����3-DES���ܵ������ڽ���\"UTF-16LE\"ȡ�ֽ�
			byte[] src2 = src.getBytes("UTF-16LE");
			// ����3-DES���ܺ�����ݵ��ֽ�
			byte[] encryptedData = desEncrypt(src2, enKey);
			// ����3-DES���ܺ�����ݽ���BASE64����
			String base64String = base64Encode(encryptedData);
			// BASE64����ȥ�����з���
			String base64Encrypt = filter(base64String);
			// ��BASE64�����е�HTML���������ת��Ĺ���
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
		/// �ر�����
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
	 * ���ַ�������URLDecoder.decode(strEncoding)����
	 * 
	 * @param String
	 *            src Ҫ���н�����ַ���
	 * 
	 * @return String ���н������ַ���
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
	 * ����3-DES���ܣ���Կ�׵�ͬ�ڼ��ܵ���Կ�ף���
	 * 
	 * @param byte[]
	 *            src Ҫ����3-DES����byte[]
	 * @param String
	 *            spkey�����SPKEY
	 * @return String 3-DES���ܺ��String
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
	 * 3-DES����
	 * 
	 * @param String
	 *            src Ҫ����3-DES���ܵ�String
	 * @param String
	 *            spkey�����SPKEY
	 * @return String 3-DES���ܺ��String
	 */

	public static String dESDecrypt(String src, byte[] spkey) {
		String requestValue = "";
		try {

			// �õ�3-DES����Կ��
			// URLDecoder.decodeTML���������ת��Ĺ���
			String URLValue = uRLDecoderdecode(src);
			// ����3-DES���ܺ�����ݽ���BASE64����
			BASE64Decoder base64Decode = new BASE64Decoder();
			byte[] base64DValue = base64Decode.decodeBuffer(URLValue);
			// Ҫ����3-DES���ܵ������ڽ���\"UTF-16LE\"ȡ�ֽ�
			requestValue = desCrypt(base64DValue, spkey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestValue;
	}

	public static void createKey(String filePath) {
		try {
			// ������Կ
			FileOutputStream fout = new FileOutputStream(filePath);
			fout.write(initKey());
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] initKey() {
		try {
			// ���ɼ����õ�Key
			KeyGenerator kg = KeyGenerator.getInstance("DESede");
			// ��Կ����
			kg.init(168);
			// ������Կ
			SecretKey key = kg.generateKey();

			return key.getEncoded();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {

		// System.out.println(Encrypt.getTimeStamp());

		// String oldString = "���ط�";
		//
		// String SPKEY = "c:/d.key";
		// byte[] key = Encrypt.readKey(SPKEY);
		// System.out.println("1�������SPKEYΪ: " + SPKEY);
		// System.out.println("2��������Ϊ: " + oldString);
		// String reValue = Encrypt.dESEncrypt(oldString, key);
		// reValue = reValue.trim().intern();
		// System.out.println("����3-DES���ܺ������: " + reValue);
		// String reValue2 = Encrypt.dESDecrypt(reValue, key);
		// System.out.println("����3-DES���ܺ������: " + reValue2);
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
