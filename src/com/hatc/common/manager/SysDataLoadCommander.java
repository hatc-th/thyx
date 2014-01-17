package com.hatc.common.manager;

import java.util.HashMap;
import java.util.Date;
import com.hatc.base.utils.DateUtil;
/**
 * ϵͳ���ݶ�ʱ���ع���ӿ�: 
 * 
 *    ����ӿ�:ֻ�Ƕ����ṩ�¼��ӿ�. 
 *            �ڲ�����Ĺ���: ���յ��¼���,���뵽ָ������. ��¼��ǰʱ��.requestTime 
 *                         ��ʱɨ��ָ������, �����ظ���ָ��(Ŀǰֻ��һ��ָ��), ִ��ָ��
 *                            ���ִ��ָ��ɹ�,�򽫸�ָ��ӻ��������. 
 *                            ���ִ��ʧ��,��¼����־.  �´λ����¶�ȡ. 
 * @author ningliyu
 *
 */
public class SysDataLoadCommander
{
    private static 	SysDataLoadCommander instance = null;
    private static  HashMap commandMap = new HashMap();
    //ͨ����������ֹ key �ظ�
    private static  int count = 0;
    private SysDataLoadCommander()
    {
    	
    }
    public static SysDataLoadCommander getInstance()
    {
    	if(instance==null)
    		instance = new SysDataLoadCommander();
    	return instance;
    }
    
    public void sendCommand()    		
    {
    	String command="loadConfig";
    	Date date = new Date();
    	String times = DateUtil.getDateTimeString(date);
    	String key = command+ "_" + times + "_" + count;
    	count++;
    	commandMap.put(key, date);    	
    }
    
    public HashMap getCommandMap()    		
    {
    	return commandMap;
    }
    
    public void removeCommandMap(String inKey)    		
    {
    	commandMap.remove(inKey);
    } 
}
