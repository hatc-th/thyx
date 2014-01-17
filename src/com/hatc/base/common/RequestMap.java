package com.hatc.base.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 业务处理值MAP（输入参数）<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class RequestMap implements Serializable
{
	/** 
	 * 
	 */
	private static final long serialVersionUID = -479679950552823366L;

	/** 返回路径 */
	public static final String REQUEST_BACK = "request_back";

	/** 请求路径及参数 */
	public static final String REQUEST_URL = "request_url";

	/** 工程绝对路径 */
	public static final String PROJECT_URL = "project_url";

	/** 访问者IP地址 */
	public static final String REQUEST_IP = "request_ip";

	/** 本机IP参数 */
	public static final String LOCAL_IP = "local_ip";

	/** 本机服务端口 */
	public static final String LOCAL_PORT = "local_port";

	/** 返回路径 */
	public static final String USER_AGENT = "User-Agent";
	
	/** 返回路径 */
	public static final String UPLOAD_FILE_LIST = "upload_file_list";

	/** 返回路径 */
	public static String PROJECT_PATH;
	
	/** 值Map */
	private Map<String, Object> map = new HashMap<String, Object>();

	public RequestMap() {
	}

	@SuppressWarnings("unchecked")
	public void putAll(Map map) {
		this.map.putAll(map);
	}

	public Map<String, Object> getMap() {
		return this.map;
	}

	public Object getObject(String key) {
		return this.map.get(key);
	}

	public String[] getStringArray(String key) {
		String[] obj = null;
		try {
			obj = (String[]) getObject(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj != null ? obj : null;
	}

	public String getString(String key) {
		String[] str = getStringArray(key);
		return str != null && str.length > 0 ? (str[0].equals("") ? null : str[0].trim()) : null;
	}

	// 以对象类型返回指定的值
	public Boolean getBoolean(String key) {
		try {
			return new Boolean(getString(key));
		} catch (Exception e) {
			return null;
		}
	}

	public Byte getByte(String key) {
		try {
			return new Byte(getString(key));
		} catch (Exception e) {
			return null;
		}
	}

	public Double getDouble(String key) {
		try {
			return new Double(getString(key));
		} catch (Exception e) {
			return null;
		}
	}

	public Float getFloat(String key) {
		try {
			return new Float(getString(key));
		} catch (Exception e) {
			return null;
		}
	}

	public Integer getInteger(String key) {
		try {
			return new Integer(getString(key));
		} catch (Exception e) {
			return null;
		}
	}

	public Date getDate(String key) {
		Date d = null;
		try {
			String value = this.getString(key);
			if (value != null && !value.equals("")) {
				SimpleDateFormat sdf = value.length() > 10 ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") : new SimpleDateFormat("yyyy-MM-dd");
				d = sdf.parse(value);
			} else {
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	public Long getLong(String key) {
		try {
			return new Long(getString(key));
		} catch (Exception e) {
			return null;
		}
	}

	public Short getShort(String key) {
		try {
			return new Short(getString(key));
		} catch (Exception e) {
			return null;
		}
	}

	// 基本类型
	public boolean getboolean(String key) {
		try {
			return Boolean.getBoolean(getString(key));
		} catch (Exception e) {
			return false;
		}
	}

	public byte getbyte(String key) {
		try {
			return Byte.parseByte(getString(key));
		} catch (Exception e) {
			return 0;
		}
	}

	public double getdouble(String key) {
		try {
			return Double.parseDouble(getString(key));
		} catch (Exception e) {
			return 0;
		}
	}

	public float getfloat(String key) {
		try {
			return Float.parseFloat(getString(key));
		} catch (Exception e) {
			return 0;
		}
	}

	public int getinteger(String key) {
		try {
			return Integer.parseInt(getString(key));
		} catch (Exception e) {
			return 0;
		}
	}

	public long getlong(String key) {
		try {
			return Long.parseLong(getString(key));
		} catch (Exception e) {
			return 0;
		}
	}

	public short getshort(String key) {
		try {
			return Short.parseShort(getString(key));
		} catch (Exception e) {
			return 0;
		}
	}

	public void addParameter(String key, Object obj) {
		if (String.class.isInstance(obj)) {
			map.put(key, new String[] { (String) obj });
		} else {
			map.put(key, obj);
		}
	}

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
	}

}
