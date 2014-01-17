package com.hatc.common.web.filter;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.hatc.common.servicestub.ReqIdentity;

public class SessionListen  implements HttpSessionListener 
{
	  private Logger logger = Logger.getLogger(SessionListen.class);
	  private int sessionCount = 0;
	
	  public SessionListen() {
	  }

	  public void sessionCreated(HttpSessionEvent se) {
	    /**@todo Implement this javax.servlet.http.HttpSessionListener method*/
		  HttpSession session = se.getSession();
		  String sessionID = session.getId();
		  long sessionCreationTime = session.getCreationTime();
		  sessionCount++;
		  if(logger.isDebugEnabled())
			  logger.debug("    sessionCreated :" + sessionCount + ",sessionID="+sessionID + ",CreationTime="+ sessionCreationTime);
		 
	  }

	  public void sessionDestroyed(HttpSessionEvent se) {
	    String UserID = "";
	    
	    try {
	      HttpSession session = se.getSession();
	      ReqIdentity reqIdentity =  null;
	      if(session.getAttribute("fIdentity")!=null)
	          reqIdentity = (ReqIdentity)session.getAttribute("fIdentity");
	      String sessionID = session.getId();
		  long sessionCreationTime = session.getCreationTime();
		  
		  sessionCount--;
		  if(logger.isDebugEnabled())
		  {
		  logger.debug("    sessionDestroyed "+  sessionCount + ",sessionID="+sessionID + ",CreationTime="+sessionCreationTime);
		  if(reqIdentity!=null)
		  {
			  logger.info("    sessionDestroyed "+  sessionCount + "userId="+reqIdentity.getUserId());				 
		  }
		  }
	    }	    
	    catch (Exception e) 
	    {
	      e.printStackTrace();
	      logger.error(e.getMessage(),e);
	    }
	  }
	}

