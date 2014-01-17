package com.hatc.base.web.form;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.LazyValidatorActionForm;

/**
 * Base ActionForm bean. Used to give child classes readable representation of
 * their properties using toString() method.
 * </p>
 * 
 * <p>
 * <a href="BaseForm.java.html"><i>View Source</i></a>
 * </p>
 */
public class BaseForm extends LazyValidatorActionForm implements Serializable {

	private static final long serialVersionUID = 3257005453799404851L;

	public String getString(String name) {
		try {
			String value = get(name).toString();
			return value != null && !value.trim().equals("") ? value : null;
		} catch (Exception e) {
			return null;
		}
	}

	// 以对象类型返回指定的值
	public Boolean getBoolean(String name) {
		try {
			return new Boolean(getString(name));
		} catch (Exception e) {
			return null;
		}
	}

	public Byte getByte(String name) {
		try {
			return new Byte(getString(name));
		} catch (Exception e) {
			return null;
		}
	}

	public Double getDouble(String name) {
		try {
			return new Double(getString(name));
		} catch (Exception e) {
			return null;
		}
	}

	public Float getFloat(String name) {
		try {
			return new Float(getString(name));
		} catch (Exception e) {
			return null;
		}
	}

	public Integer getInteger(String name) {
		try {
			return new Integer(getString(name));
		} catch (Exception e) {
			return null;
		}
	}

	public Date getDate(String name) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			String value = this.getString(name);
			if (value != null && !value.equals("")) {
				d = sdf.parse(value);
			} else {
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	public Long getLong(String name) {
		try {
			return new Long(getString(name));
		} catch (Exception e) {
			return null;
		}
	}

	public Short getShort(String name) {
		try {
			return new Short(getString(name));
		} catch (Exception e) {
			return null;
		}
	}

	public FormFile getFormFile(String file) {
		try {
			return (FormFile) get(file);
		} catch (Exception e) {
			return null;
		}
	}

	// 基本类型
	public boolean getboolean(String name) {
		try {
			return Boolean.getBoolean(getString(name));
		} catch (Exception e) {
			return false;
		}
	}

	public byte getbyte(String name) {
		try {
			return Byte.parseByte(getString(name));
		} catch (Exception e) {
			return 0;
		}
	}

	public double getdouble(String name) {
		try {
			return Double.parseDouble(getString(name));
		} catch (Exception e) {
			return 0;
		}
	}

	public float getfloat(String name) {
		try {
			return Float.parseFloat(getString(name));
		} catch (Exception e) {
			return 0;
		}
	}

	public int getinteger(String name) {
		try {
			return Integer.parseInt(getString(name));
		} catch (Exception e) {
			return 0;
		}
	}

	public long getlong(String name) {
		try {
			return Long.parseLong(getString(name));
		} catch (Exception e) {
			return 0;
		}
	}

	public short getshort(String name) {
		try {
			return Short.parseShort(getString(name));
		} catch (Exception e) {
			return 0;
		}
	}
}
