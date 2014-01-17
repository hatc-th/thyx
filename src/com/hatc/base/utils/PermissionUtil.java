package com.hatc.base.utils;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 二进制权限工具类<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
* 添加 = 0 修改 = 1 删除 = 2
* 拥有添加权限：0001
* 拥有修改权限：0010
* 拥有删除权限：0100
* 拥有增，删权限：0101
* 拥有增，修，删权限：0111
*
**/
public class PermissionUtil {

	/**
	 * 权限验证
	 * 
	 * @param purview
	 *            权限集
	 * @param optPurview
	 *            当前权限
	 * @return
	 */
	public static boolean checkPower(long purview, long optPurview) {
		long purviewValue = (long) Math.pow(2, optPurview);
		return (purview & purviewValue) == purviewValue;
	}

	/**
	 * 权限集验证
	 * 
	 * @param purview
	 *            权限集
	 * @param optPurview[]
	 *            当前权限集
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
	 * 权限总集
	 * 
	 * @param count[]
	 *            权限集
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
