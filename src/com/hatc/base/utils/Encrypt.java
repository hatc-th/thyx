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
	 * ȥ���ַ����Ļ��з��� base64����3-DES������ʱ���õ����ַ����л��з��� ��һ��Ҫȥ��������uni-wiseƽ̨����Ʊ������ɹ���
	 * ��ʾ��sp��֤ʧ�ܡ����ڿ����Ĺ����У���Ϊ����������������޲ߣ� һ�����Ѹ����ҿ�������ͨҪһ�μ��ܺ� �����֣�Ȼ��ȥ���Լ����ɵ��ַ����Ƚϣ�
	 * ���Ǹ�����ĵ��Է����������ȽϷ��������ɵ��ַ���Ψһ��ͬ�� �Ƕ��˻��С� ����c#����Ҳд��Ʊ���������û�з���������⡣
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
		Encrypt.createKey("c:/d.key");

	}

}
