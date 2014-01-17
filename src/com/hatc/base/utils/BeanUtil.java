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
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ֵBeanת��������<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class BeanUtil {

	private static Log log = LogFactory.getLog(BeanUtil.class);

	/**
	 * �õ�fields������
	 * 
	 * @param Class
	 *            objClass ��ǰ�����Class����
	 * @return Map �������Ե�ͼ(�������ƣ���������)
	 */
	public static Map<String, Class> getFilds(Class objClass) {
		Map<String, Class> map = null;
		try {
			// �õ����е�����
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
	 * ������ĸ����ר��д
	 */
	public static String upFirstChar(String str) {
		String first = (str.substring(0, 1)).toUpperCase();
		String other = str.substring(1);
		return first + other;
	}

	/**
	 * �õ�����Method����Map
	 * 
	 * @param Class
	 *            objClass ��ǰ�����Class����
	 * @return Map ���󷽷���ͼ(������������)
	 */
	public static Map<String, Method> getMethods(Class objClass) {
		Map<String, Method> map = null;
		try {
			// �õ����еķ���
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
	 * ���󿽱�
	 * @param objClass��Ŀ���������
	 * @param obj1����������
	 * @return��Ŀ�����ʵ��
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
	 * ���󿽱�
	 * @param obj��Ŀ�����
	 * @param obj1����������
	 */
	public static void convertObject(Object obj, Object obj1) {
		// ���Ե����Ƽ�����
		Map<String, Class> fileds = getFilds(obj1.getClass());
		// �������Ƽ�����
		Map<String, Method> methods = getMethods(obj1.getClass());

		try {
			for (Iterator it = fileds.entrySet().iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();
				// ��������
				String filed = (String) entry.getKey();
				// ת����SET����(����ĸ��д)
				StringBuffer sub = new StringBuffer("set");
				sub.append(upFirstChar(filed));
				// ��ȡSET����
				Method setMethod = methods.get(sub.toString());
				// ת����GET����(����ĸ��д)
				StringBuffer gub = new StringBuffer("get");
				gub.append(upFirstChar(filed));
				// ��ȡGET����
				Method getMethod = methods.get(gub.toString());

				if (setMethod != null && getMethod != null) {
					// ��baseForm��ȡ����Ӧ��ֵ
					Object temp = getMethod.invoke(obj1);
					// ע�뵽��������Ӧ������
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
	 * ������MAP�����ɶ���
	 * @param objClass��Ŀ���������
	 * @param map������MAP
	 * @return��Ŀ�����ʵ��
	 */
	public static Object convertObject(Class objClass, RequestMap map) {
		// ���Ե����Ƽ�����
		Map<String, Class> fileds = getFilds(objClass);
		// �������Ƽ�����
		Map<String, Method> methods = getMethods(objClass);
		// �������ͼ�����
		Map<Class, Method> returns = getMethodsReturn(map.getClass());

		try {
			Class cla = Class.forName(objClass.getName());
			Object obj = cla.newInstance();

			for (Iterator it = fileds.entrySet().iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();

				// ��������
				String filed = (String) entry.getKey();
				// ��������
				Class type = (Class) (entry.getValue());

				// ת����SET����(����ĸ��д)
				StringBuffer sub = new StringBuffer("set");
				sub.append(upFirstChar(filed));
				// SET��������
				String setFiled = sub.toString();
				// ��ȡSET����
				Method setMethod = methods.get(setFiled);

				if (setMethod != null) {
					// ���������ҳ���Ӧ�ķ���
					Method rMethod = returns.get(type);
					if (rMethod != null) {
						// ��RequestMap��ȡ����Ӧ��ֵ
						Object temp = rMethod.invoke(map,
								new Object[] { filed });
						// ע�뵽��������Ӧ������
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
	 * ������MAP�����ɶ���
	 * @param obj��Ŀ�����
	 * @param map������ֵMAP
	 * @return
	 */
	public static Object convertObject(Object obj, RequestMap map) {
		// ���Ե����Ƽ�����
		Map<String, Class> fileds = getFilds(obj.getClass());
		// �������Ƽ�����
		Map<String, Method> methods = getMethods(obj.getClass());
		// �������ͼ�����
		Map<Class, Method> returns = getMethodsReturn(map.getClass());

		try {
			for (Iterator it = fileds.entrySet().iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();
				// ��������
				String filed = (String) entry.getKey();
				// ��������
				Class type = (Class) (entry.getValue());
				// ת����SET����(����ĸ��д)
				StringBuffer sub = new StringBuffer("set");
				sub.append(upFirstChar(filed));
				// SET��������
				String setFiled = sub.toString();
				// ��ȡSET����
				Method setMethod = methods.get(setFiled);
				if (setMethod != null) {
					// ���������ҳ���Ӧ�ķ���
					Method rMethod = returns.get(type);
					if (rMethod != null) {
						// ��baseForm��ȡ����Ӧ��ֵ
						Object temp = rMethod.invoke(map,
								new Object[] { filed });
						// ע�뵽��������Ӧ������
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
		// ���Ե����Ƽ�����
		Map<String, Class> fileds = getFilds(obj.getClass());
		// �������Ƽ�����
		Map<String, Method> methods = getMethods(obj.getClass());
		// �������ͼ�����
		Map<Class, Method> returns = getMethodsReturn(map.getClass());
		try {
			Map<String, Object> rMap = map.getMap();
			for (Iterator<String> it = rMap.keySet().iterator(); it.hasNext();) {
				// ��������
				String filed = it.next();
				// ת����SET����(����ĸ��д)
				StringBuffer sub = new StringBuffer("set");
				sub.append(upFirstChar(filed));
				// SET��������
				String setFiled = sub.toString();
				// ��ȡSET����
				Method setMethod = methods.get(setFiled);

				if (setMethod != null) {
					// ��������
					Class type = (fileds.get(filed));
					// ���������ҳ���Ӧ�ķ���
					Method rMethod = returns.get(type);
					if (rMethod != null) {
						// ��baseForm��ȡ����Ӧ��ֵ
						Object temp = rMethod.invoke(map,
								new Object[] { filed });
						// ע�뵽��������Ӧ������
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
	 * ��BaseForm�й�������
	 * @param objClass ��������
	 * @param baseForm��BaseForm
	 * @return ����ʵ��
	 */
	public static Object convertObject(Class objClass, BaseForm baseForm) {
		// ���Ե����Ƽ�����
		Map<String, Class> fileds = getFilds(objClass);
		// �������Ƽ�����
		Map<String, Method> methods = getMethods(objClass);
		// �������ͼ�����
		Map<Class, Method> returns = getMethodsReturn(baseForm.getClass());

		try {
			Class cla = Class.forName(objClass.getName());
			Object obj = cla.newInstance();

			for (Iterator it = fileds.entrySet().iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();

				// ��������
				String filed = (String) entry.getKey();
				// ��������
				Class type = (Class) (entry.getValue());

				// ת����SET����(����ĸ��д)
				StringBuffer sub = new StringBuffer("set");
				sub.append(upFirstChar(filed));
				// SET��������
				String setFiled = sub.toString();
				// ��ȡSET����
				Method setMethod = methods.get(setFiled);

				if (setMethod != null) {
					// ���������ҳ���Ӧ�ķ���
					Method rMethod = returns.get(type);
					if (rMethod != null) {
						// ��baseForm��ȡ����Ӧ��ֵ
						Object temp = rMethod.invoke(baseForm,
								new Object[] { filed });
						// ע�뵽��������Ӧ������
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
	 * ������������Ƶ�ֵMAP�У��ɿ����Ƿ���ؿ�ֵ��
	 * @param obj������
	 * @param bool���Ƿ���ؿ�ֵ����true �ǣ�false����
	 * @return ֵMAP
	 */
	public static Map<String, String> convertParmMap(Object obj, boolean bool) {
		Map<String, String> parmMap = new HashMap<String, String>();
		// ���Ե����Ƽ�����
		Field[] fileds = obj.getClass().getDeclaredFields();
		// �������Ƽ�����
		Map<String, Method> methods = getMethods(obj.getClass());
		try {
			for (Field field : fileds) {
				// ��������
				String filedName = field.getName();
				// ת����GET����(����ĸ��д)
				StringBuffer sub = new StringBuffer("get");
				sub.append(upFirstChar(filedName));
				// GET��������
				String setFiled = sub.toString();
				// ��ȡGET����
				Method getMethod = methods.get(setFiled);
				if (getMethod != null) {
					// ��baseForm��ȡ����Ӧ��ֵ
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
	 *�����������������ӵ�ֵMAP��
	 * @param obj������
	 * @param map��ֵMAP
	 * @return��ֵMAP
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
	 * �õ�����Method������ֵ����Map
	 * 
	 * @param Class
	 *            objClass ��ǰ�����Class����
	 * @return Map ���󷽷���ͼ(��������,����)
	 */
	public static Map<Class, Method> getMethodsReturn(Class objClass) {
		Map<Class, Method> map = null;
		try {
			// �õ����еķ���
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
	 * �����������ת����Ϊ������Ӧ����������(ת����get������������)
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
	 * ����ʵ��Model��FormBean֮���ת��.
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
	 * Model��FormBean֮���ֵ����
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
	 * List Pojo��List FormBean֮���ֵ����
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
