package com.hatc.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hatc.base.service.impl.BaseManager;
import com.hatc.common.service.LogService;

public class LogServiceImpl extends BaseManager  implements LogService {

	Logger log = LoggerFactory.getLogger(LogServiceImpl.class);
	
	public void log(String message) {
		log.info(message);
	}

	public void sysLog(String message) {
		log.info(message);
	}

}
