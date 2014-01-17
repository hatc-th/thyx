package com.hatc.base.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.support.AopUtils;

import com.hatc.base.common.RequestMap;
import com.hatc.base.hibernate.pojo.BaseObject;
import com.hatc.base.web.form.BaseForm;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 值Bean转换工具类<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class BeanUtil {

	private static Log log = LogFactory.getLog(BeanUtil.class);

	/**
	 * 得到fields的属性
	 * 
	 * @param Class
	 *            objClass 当前对象的Class对象
	 * @return Map 对象属性地图(属性名称，属性类型)
	 */
	public static Map<String, Class> getFilds(Class objClass) {
		Map<String, Class> map = null;
		try {
			// 得到所有的属性
			Field[] fields = objClass.getDeclaredFields();
			int size = fields.length;
			if (size > 0) {
				map = new HashMap<String, Class>();
				for (int i = 0; i < size; i++) {
					map.put(fields[i].getName(), fields[i].getType());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 对首字母进行专大写
	 */
	public static String upFirstChar(String str) {
		String first = (str.substring(0, 1)).toUpperCase();
		String other = str.substring(1);
		return first + other;
	}

	/**
	 * 得到所有Method对照Map
	 * 
	 * @param Class
	 *            objClass 当前对象的Class对象
	 * @return Map 对象方法地图(方法名，方法)
	 */
	public static Map<String, Method> getMethods(Class objClass) {
		Map<String, Method> map = null;
		try {
			// 得到所有的方法
			Method[] methods = objClass.getDeclaredMethods();
			int size = methods.length;
			if (size > 0) {
				map = new HashMap<String, Method>();
				for (int i = 0; i < size; i++) {
					map.put(methods[i].getName(), methods[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 对象拷贝
	 * @param objClass　目标对象类型
	 * @param obj1　拷贝对象
	 * @return　目标对象实例
	 */
	public static Object convertObject(Class objClass, Object obj1) {
		try {
			Class cla = Class.forName(objClass.getName());
			Object obj = cla.newInstance();
			BeanUtils.copyProperties(obj, obj1);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 对象拷贝
	 * @param obj　目标对象
	 * @param obj1　拷贝对象
	 */
	public static void convertObject(Object obj, Object obj1) {
		// 属性的名称及类型
		Map<String, Class> fileds = getFilds(obj1.getClass());
		// 方法名称及方法
		Map<String, Method> methods = getMethods(obj1.getClass());

		try {
			for (Iterator it = fileds.entrySet().iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();
				// 属性名称
				String filed = (String) entry.getKey();
				// 转换成SET方法(首字母大写)
				StringBuffer sub = new StringBuffer("set");
				sub.append(upFirstChar(filed));
				// 获取SET方法
				Method setMethod = methods.get(sub.toString());
				// 转换成GET方法(首字母大写)
				StringBuffer gub = new StringBuffer("get");
				gub.append(upFirstChar(filed));
				// 获取GET方法
				Method getMethod = methods.get(gub.toString());

				if (setMethod != null && getMethod != null) {
					// 从baseForm中取出对应的值
					Object temp = getMethod.invoke(obj1);
					// 注入到对象中相应的属性
					if (temp != null) {
						setMethod.invoke(obj, new Object[] { temp });
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从请求MAP中生成对象
	 * @param objClass　目标对象类型
	 * @param map　请求MAP
	 * @return　目标对象实例
	 */
	public static Object convertObject(Class objClass, RequestMap map) {
		// 属性的名称及类型
		Map<String, Class> fileds = getFilds(objClass);
		// 方法名称及方法
		Map<String, Method> methods = getMethods(objClass);
		// 返回类型及方法
		Map<Class, Method> returns = getMethodsReturn(map.getClass());

		try {
			Class cla = Class.forName(objClass.getName());
			Object obj = cla.newInstance();

			for (Iterator it = fileds.entrySet().iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();

				// 属性名称
				String filed = (String) entry.getKey();
				// 属性类型
				Class type = (Class) (entry.getValue());

				// 转换成SET方法(首字母大写)
				StringBuffer sub = new StringBuffer("set");
				sub.append(upFirstChar(filed));
				// SET方法名称
				String setFiled = sub.toString();
				// 获取SET方法
				Method setMethod = methods.get(setFiled);

				if (setMethod != null) {
					// 根据类型找出对应的方法
					Method rMethod = returns.get(type);
					if (rMethod != null) {
						// 从RequestMap中取出对应的值
						Object temp = rMethod.invoke(map,
								new Object[] { filed });
						// 注入到对象中相应的属性
						if (temp != null) {
							setMethod.invoke(obj, new Object[] { temp });
						}
					}
				}

			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从请求MAP中生成对象
	 * @param obj　目标对象
	 * @param map　请求值MAP
	 * @return
	 */
	public static Object convertObject(Object obj, RequestMap map) {
		// 属性的名称及类型
		Map<String, Class> fileds = getFilds(obj.getClass());
		// 方法名称及方法
		Map<String, Method> methods = getMethods(obj.getClass());
		// 返回类型及方法
		Map<Class, Method> returns = getMethodsReturn(map.getClass());

		try {
			for (Iterator it = fileds.entrySet().iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();
				// 属性名称
				String filed = (String) entry.getKey();
				// 属性类型
				Class type = (Class) (entry.getValue());
				// 转换成SET方法(首字母大写)
				StringBuffer sub = new StringBuffer("set");
				sub.append(upFirstChar(filed));
				// SET方法名称
				String setFiled = sub.toString();
				// 获取SET方法
				Method setMethod = methods.get(setFiled);
				if (setMethod != null) {
					// 根据类型找出对应的方法
					Method rMethod = returns.get(type);
					if (rMethod != null) {
						// 从baseForm中取出对应的值
						Object temp = rMethod.invoke(map,
								new Object[] { filed });
						// 注入到对象中相应的属性
						if (temp != null) {
							setMethod.invoke(obj, new Object[] { temp });
						}
					}
				}

			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param map
	 * @param obj
	 * @return
	 */
	public static Object convertObject(RequestMap map, Object obj) {
		// 属性的名称及类型
		Map<String, Class> fileds = getFilds(obj.getClass());
		// 方法名称及方法
		Map<String, Method> methods = getMethods(obj.getClass());
		// 返回类型及方法
		Map<Class, Method> returns = getMethodsReturn(map.getClass());
		try {
			Map<String, Object> rMap = map.getMap();
			for (Iterator<String> it = rMap.keySet().iterator(); it.hasNext();) {
				// 属性名称
				String filed = it.next();
				// 转换成SET方法(首字母大写)
				StringBuffer sub = new StringBuffer("set");
				sub.append(upFirstChar(filed));
				// SET方法名称
				String setFiled = sub.toString();
				// 获取SET方法
				Method setMethod = methods.get(setFiled);

				if (setMethod != null) {
					// 属性类型
					Class type = (fileds.get(filed));
					// 根据类型找出对应的方法
					Method rMethod = returns.get(type);
					if (rMethod != null) {
						// 从baseForm中取出对应的值
						Object temp = rMethod.invoke(map,
								new Object[] { filed });
						// 注入到对象中相应的属性
						if (temp != null) {
							setMethod.invoke(obj, new Object[] { temp });
						}
					}
				}
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 从BaseForm中构建对象
	 * @param objClass 对象类型
	 * @param baseForm　BaseForm
	 * @return 对象实例
	 */
	public static Object convertObject(Class objClass, BaseForm baseForm) {
		// 属性的名称及类型
		Map<String, Class> fileds = getFilds(objClass);
		// 方法名称及方法
		Map<String, Method> methods = getMethods(objClass);
		// 返回类型及方法
		Map<Class, Method> returns = getMethodsReturn(baseForm.getClass());

		try {
			Class cla = Class.forName(objClass.getName());
			Object obj = cla.newInstance();

			for (Iterator it = fileds.entrySet().iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();

				// 属性名称
				String filed = (String) entry.getKey();
				// 属性类型
				Class type = (Class) (entry.getValue());

				// 转换成SET方法(首字母大写)
				StringBuffer sub = new StringBuffer("set");
				sub.append(upFirstChar(filed));
				// SET方法名称
				String setFiled = sub.toString();
				// 获取SET方法
				Method setMethod = methods.get(setFiled);

				if (setMethod != null) {
					// 根据类型找出对应的方法
					Method rMethod = returns.get(type);
					if (rMethod != null) {
						// 从baseForm中取出对应的值
						Object temp = rMethod.invoke(baseForm,
								new Object[] { filed });
						// 注入到对象中相应的属性
						setMethod.invoke(obj, new Object[] { temp != null
								&& !temp.equals("") ? temp : null });
					}
				}

			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 将对象数据项复制到值MAP中（可控制是否加载空值）
	 * @param obj　对象
	 * @param bool　是否加载空值　（true 是；false　否）
	 * @return 值MAP
	 */
	public static Map<String, String> convertParmMap(Object obj, boolean bool) {
		Map<String, String> parmMap = new HashMap<String, String>();
		// 属性的名称及类型
		Field[] fileds = obj.getClass().getDeclaredFields();
		// 方法名称及方法
		Map<String, Method> methods = getMethods(obj.getClass());
		try {
			for (Field field : fileds) {
				// 属性名称
				String filedName = field.getName();
				// 转换成GET方法(首字母大写)
				StringBuffer sub = new StringBuffer("get");
				sub.append(upFirstChar(filedName));
				// GET方法名称
				String setFiled = sub.toString();
				// 获取GET方法
				Method getMethod = methods.get(setFiled);
				if (getMethod != null) {
					// 从baseForm中取出对应的值
					Object o = getMethod.invoke(obj, new Object[0]);
					if (o != null) {
						String temp = String.valueOf(o);
						if (!temp.equals("") || bool) {
							parmMap.put(filedName, temp);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parmMap;
	}

	/**
	 *　将对象的数据项添加到值MAP中
	 * @param obj　对象
	 * @param map　值MAP
	 * @return　值MAP
	 */
	public static Map<String, String> convertParmMap(Object obj, RequestMap map) {
		Map<String, String> parmMap = new HashMap<String, String>();
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				String param = map.getString(field.getName());
				if (param != null && !param.equals("")) {
					parmMap.put(field.getName(), param);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parmMap;
	}

	/**
	 * 得到所有Method及返回值对照Map
	 * 
	 * @param Class
	 *            objClass 当前对象的Class对象
	 * @return Map 对象方法地图(返回类型,方法)
	 */
	public static Map<Class, Method> getMethodsReturn(Class objClass) {
		Map<Class, Method> map = null;
		try {
			// 得到所有的方法
			Method[] methods = objClass.getDeclaredMethods();
			int size = methods.length;
			if (size > 0) {
				map = new HashMap<Class, Method>();
				for (int i = 0; i < size; i++) {
					map.put(methods[i].getReturnType(), methods[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 将对象的属性转换成为对象相应方法的名称(转换在get方法名，类型)
	 */
	public static Map getFildsToSetName(Class objClass) {
		Map<String, Object> maps = null;
		Map<String, Class> map = getFilds(objClass);
		if (map != null) {
			maps = new HashMap<String, Object>();
			for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();
				StringBuffer sub = new StringBuffer("set");
				String str = (String) entry.getKey();
				str = upFirstChar(str);
				sub.append(str);
				maps.put(sub.toString(), entry.getValue());
			}
		}
		return maps;
	}

	/**
	 * 此类实现Model与FormBean之间的转换.
	 * 
	 * @param o
	 *            the object to inspect
	 * @return the Class of the persistable object
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Object getOpposingObject(Object o)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		String name = o.getClass().getName();

		if (o instanceof BaseObject) {
			name = StringUtils.replace(name, ".hibernate.pojo.",
					".webapp.form.");
			if (AopUtils.isCglibProxy(o)) {
				name = name.substring(0, name.indexOf("$$"));
			}
			name += "Form";
		} else {
			name = StringUtils.replace(name, ".webapp.form.",
					".hibernate.pojo.");
			name = name.substring(0, name.lastIndexOf("Form"));
		}

		Class obj = Class.forName(name);

		if (log.isDebugEnabled()) {
			log.debug("returning className: " + obj.getName());
		}

		return obj.newInstance();
	}

	/**
	 * Model与FormBean之间的值互拷
	 * 
	 * @param o
	 *            the object to tranfer properties from
	 * @return converted object
	 */
	public static Object convertObject(Object o) {
		if (o == null) {
			return null;
		}
		Object target = null;

		try {
			target = getOpposingObject(o);
			BeanUtils.copyProperties(target, o);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return target;
	}

	/**
	 * List Pojo与List FormBean之间的值互拷
	 * 
	 * @param o
	 *            the object to tranfer properties from
	 * @return converted object
	 */
	public static List convertLists(List objectList) {
		List<Object> targetList = null;
		if (objectList != null) {
			targetList = new ArrayList<Object>();
			for (Iterator it = objectList.iterator(); it.hasNext();) {
				Object o = it.next();
				if (o == null) {
					return null;
				}
				Object target = convertObject(o);
				targetList.add(target);
			}
		}
		return targetList;
	}
}
