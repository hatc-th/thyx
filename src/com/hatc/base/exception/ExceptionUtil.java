package com.hatc.base.exception;
import org.apache.ws.commons.util.Base64;
import org.apache.ws.commons.util.Base64.DecodingException;

import com.hatc.base.contants.SystemConfig;

public class ExceptionUtil 
{
	public static String LINE = SystemConfig.CHAR_LINE;
	
	
	public static String getExceptionMessage(String inPre,Throwable e)
	{
		if(e==null)
			return null;
		StringBuffer sb = new StringBuffer();
		 
		//sb.append( "----Exception:");
		 
		//sb.append( "    e.getClass().getName():  ");
		//sb.append( e.getClass().getName() ); 
		//sb.append( LINE );
	 
		/**if(e instanceof FomsException)
		{
			FomsException ef = (FomsException) e;
			
			sb.append(inPre);
			sb.append("    FomsException begin:");
			sb.append( LINE );
			sb.append("        getFaultcode():");
			sb.append(ef.getFaultcode());
			sb.append( LINE );
						
			sb.append("        getReason():");
			sb.append(ef.getReason());
			sb.append( LINE );
			
			
			sb.append("getMessage():");
			sb.append(ef.getMessage());
			sb.append( LINE );
			 
			sb.append(inPre);
			appendMessage(inPre,sb,ef.getXmlPrcException()); 
			//sb.append("    FomsException end:"); 
		}
		else **/ 
			appendMessage(inPre,sb,e);
		   
		/**
		java.lang.Throwable throwable = e.getCause();	   
		appendMessage("getCause",sb,e);  
		
		if(throwable.getCause()!=null)
		{
			java.lang.Throwable throwable2 = e.getCause();	   
			appendMessage("getCause.getCause",sb,throwable2);
		}
	    **/
		
		//sb.append( LINE );
		//sb.append( "----------------  getExceptionMessage   end  -----------");
		//sb.append( LINE );
		return sb.toString(); 
	}
	
	public static void appendMessage(String inPreMessage,StringBuffer sb, Throwable throwable)
	{
		
	if(throwable!=null)
    {    
		
		sb.append(inPreMessage);		
		sb.append("    : getMessage: ");
		if(throwable.getMessage()!=null)
		{
			try {
				sb.append(new String(Base64.decode(throwable.getMessage())));
			} catch (DecodingException e) {
				sb.append(throwable.getMessage());
			}		
			sb.append( LINE );
		}
		
		/**
		sb.append(inPreMessage);
		sb.append("    : getClass().getName(): ");
		sb.append(throwable.getClass().getName());
		sb.append( LINE ); 
		**/ 
		sb.append( LINE ); 
		StackTraceElement[] stackArray = throwable.getStackTrace();
		
		if(stackArray!=null)
		{
			int size = stackArray.length;
			int i;
			StackTraceElement stack = null;
		 
			//sb.append(": 	stack:");
			//sb.append( LINE );
			for(i=0;i<size;i++)
			{
				stack = stackArray[i];
				/**
				sb.append("getClassName:");
				sb.append(stack.getClassName());
				sb.append( LINE ); 
				sb.append("getFileName:");
				sb.append(stack.getFileName());
				sb.append( LINE ); 
				sb.append("getMethodName:");
				sb.append(stack.getMethodName());
				sb.append( LINE ); 
				sb.append("getLineNumber:");
				sb.append(stack.getLineNumber());
				*/
				//sb.append("    ");
				sb.append(stack.toString());
				sb.append( LINE );
			}
			//sb.append("    stack end:");
			//sb.append( LINE );
		}
		
    	 
    }
	}
 

}
