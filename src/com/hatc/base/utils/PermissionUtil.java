package com.hatc.base.utils;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ������Ȩ�޹�����<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
* ��� = 0 �޸� = 1 ɾ�� = 2
* ӵ�����Ȩ�ޣ�0001
* ӵ���޸�Ȩ�ޣ�0010
* ӵ��ɾ��Ȩ�ޣ�0100
* ӵ������ɾȨ�ޣ�0101
* ӵ�������ޣ�ɾȨ�ޣ�0111
*
**/
public class PermissionUtil {

	/**
	 * Ȩ����֤
	 * 
	 * @param purview
	 *            Ȩ�޼�
	 * @param optPurview
	 *            ��ǰȨ��
	 * @return
	 */
	public static boolean checkPower(long purview, long optPurview) {
		long purviewValue = (long) Math.pow(2, optPurview);
		return (purview & purviewValue) == purviewValue;
	}

	/**
	 * Ȩ�޼���֤
	 * 
	 * @param purview
	 *            Ȩ�޼�
	 * @param optPurview[]
	 *            ��ǰȨ�޼�
	 * @return
	 */
	public static boolean[] checkPower(long purview, long[] optPurview) {
		boolean[] bool = new boolean[optPurview.length];
		for (int i = 0; i < optPurview.length; i++) {
			long purviewValue = (long) Math.pow(2, optPurview[i]);
			bool[i] = (purview & purviewValue) == purviewValue;
		}
		return bool;
	}

	/**
	 * Ȩ���ܼ�
	 * 
	 * @param count[]
	 *            Ȩ�޼�
	 * @return
	 */
	public static long permissionCount(String[] count) {
		long pur = 0;
		for (int i = 0; i < count.length; i++) {
			pur += (long) Math.pow(2, ConvertLang.convertlong(count[i]));
		}
		return pur;
	}

	public static void main(String[] args) {

		long pur = 0;

		for (int i = 10; i < 50; i++) {
			pur += (long) Math.pow(2, i);
		}

		for (int i = 0; i < 50; i++) {
			System.out.println(PermissionUtil.checkPower(pur, i));
		}

		boolean[] b = PermissionUtil.checkPower(pur, new long[] { 1, 2, 10 });
		for (int i = 0; i < b.length; i++) {
			System.out.println(b[i]);
		}
	}

}
