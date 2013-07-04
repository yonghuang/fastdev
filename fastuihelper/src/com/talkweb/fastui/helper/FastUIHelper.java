package com.talkweb.fastui.helper;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.talkweb.fastui.helper.rule.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * FastUI V1.5版本JOSN格式转换工具类
 * 
 * @author: 2013-05-31 黄勇
 */
public class FastUIHelper  {

    private static final Logger logger = LoggerFactory.getLogger(FastUIHelper.class);
 
    /**
     * 初始化表单数据。V1.5及以上推荐用法，V1.5版本数据格式
     * @param attributeName 属性名称
     * @param data 属性初始化数据
     * @return map对象，数据格式如：{id:attributeName,data:data}
     * **/
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map initformAttribute(String attributeName,Object data){
        Map attributeMap = new HashMap();
        attributeMap.put("id", attributeName);
        attributeMap.put("data", data);
        return attributeMap;
    }
    /**
     * 用来格式化下拉列表控件数据
     * @param list 要格式化的数组
     * @param select 属性映射关系
     * @return List<Map> 格式化的数据
     * */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<Map> initSelectData(List list,RuleSelect select) throws RuntimeException{
        if(select!=null)
        {
            HashMap hm = new HashMap();
            if(select.getText()!=null)
                hm.put(select.getText(), "text");
            if(select.getValue()!=null)
                hm.put(select.getValue(), "value");
            return setAttributeValue(list, hm);
        }else
            return null;
    }       
    
    /**
     * 给对象属性添加统一前缀
     * @param o 要处理的对象
     * @param prefixName 前缀名称
     * @return map 处理后的结果
     * **/      
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map addPrefix(Object o,String prefixName){
        Map attributeMap = new HashMap();
        PropertyDescriptor[] properties;
        try
        {
            properties = java.beans.Introspector.getBeanInfo(o.getClass()).getPropertyDescriptors();
        } catch (IntrospectionException e)
        {
            logger.error("FastDev框架获取bean信息失败！", e);
            throw new RuntimeException(e);
        }
        for (PropertyDescriptor field : properties) {
            String fieldName = field.getName();
            Object value = getFieldValueByName(o, fieldName);
            attributeMap.put(prefixName+fieldName, value);                                  
        }       
        return attributeMap;
    }
    
    /**
     * 用来格式化导航控件数据
     * @param list 要格式化的数组
     * @param navigation 属性映射关系
     * @return List<Map> 格式化的数据
     * */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<Map> initNavigationData(List list,RuleNavigation navigation){
        if(navigation!=null)
        {
            HashMap hm = new HashMap();
            if(navigation.getText()!=null)
                hm.put(navigation.getText(), "text");
            if(navigation.getGroup()!=null)
                hm.put(navigation.getGroup(), "pid");
            if(navigation.getId()!=null)
                hm.put(navigation.getId(), "id");
            if(navigation.getUrl()!=null)
                hm.put(navigation.getUrl(), "url");
            if(navigation.getTarget()!=null)
                hm.put(navigation.getTarget(), "target");
            if(navigation.getIco()!=null)
                hm.put(navigation.getIco(), "ico");
            if(navigation.getNum()!=null)
                hm.put(navigation.getNum(), "num");
            return setAttributeValue(list, hm);
        }else
            return null;
    }   
    
    /**
     * 格式化Datagrid控件数据
     * 
     * @param list
     *            要格式化的数据
     * @param pagination
     *            分页对象
     * */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map formatDatagirdData(List list, Pagination pagination) {
        Map m = new HashMap();
        m.put("data", list);
        HashMap info = new HashMap();
        info.put("total", pagination.getAllPage());
        info.put("page", pagination.getCurrPage());
        info.put("records", pagination.getCount());
        info.put("pageSize", pagination.getSize());
        m.put("info", info);
        return m;
    }
    
    /**
     * 用来格式化树控件数据
     * @param list 要格式化的数组
     * @param attributes 属性映射关系，必须按id,val,pid的顺序依次添加映射关系
     * @return List<Map> 格式化的数据
     * */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<Map> initTreeData(List list,String[] attributes){
        if(attributes!=null)
        {
            HashMap hm = new HashMap();
            if(attributes.length>0&&attributes[0]!=null)
                hm.put(attributes[0], "id");
            if(attributes.length>1&&attributes[1]!=null)
                hm.put(attributes[1], "val");
            if(attributes.length>2&&attributes[2]!=null)
                hm.put(attributes[2], "pid");   
            return setAttributeValue(list, hm);
        }else
            return null;
    }
    
    /**
     * 用来格式化树控件数据
     * @param list 要格式化的数组
     * @param wtree 属性映射关系
     * @return List<Map> 格式化的数据
     * */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<Map> initTreeData(List list,RuleTree wtree){
        if(wtree!=null)
        {
            HashMap hm = new HashMap();
            if(wtree.getId()!=null)
                hm.put(wtree.getId(), "id");
            if(wtree.getVal()!=null)
                hm.put(wtree.getVal(), "val");
            if(wtree.getPid()!=null)
                hm.put(wtree.getPid(), "pid");  
            if(wtree.getChk()!=null)
                hm.put(wtree.getChk(), "chk");
            if(wtree.getAsyn()!=null)
                hm.put(wtree.getAsyn(), "asyn");
            if(wtree.getMap()!=null && wtree.getMap().size()>0)
                for(Object o:wtree.getMap().keySet())
                {
                    hm.put(o, wtree.getMap().get(o));
                }
            return setAttributeValue(list, hm);
        }else
            return null;
    }
    
    /**
     * 用来处理属性映射
     * @param list 需要处理的数组
     * @param m 映射关系，为map对像，key为原始属性名称，value为映射后的属性名称
     * @throws IntrospectionException 
     * **/
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<Map> setAttributeValue(List list,Map m) {
        List<Map> l = null;
        if(list!=null){
            l = new ArrayList<Map>();
            for(Object o:list.toArray())
            {
                Map attributeMap = new HashMap();
                if(o instanceof Map)
                {
                    Map map = (Map)o;
                    boolean bl = false;
                    Object k = null;
                    for(Object key:map.keySet())
                    {
                        bl = false;
                        //考虑到通过service取回来的属性名都是大写的字符串，所以不用containsKey方法来判断是否存在key值，改为统一转小写后比较key值
                        for(Object key2:m.keySet())
                        {
                            if(key2.toString().toLowerCase().equals(key.toString().toLowerCase())){
                                bl = true;
                                k = key2;
                                break;
                            }
                        }
                        if(bl){
                            Object mappingName = m.get(k);
                            attributeMap.put(mappingName, map.get(key));
                        }
                    }                   
                }else{
                    
                    PropertyDescriptor[] properties;
                    try
                    {
                        properties = java.beans.Introspector.getBeanInfo(o.getClass()).getPropertyDescriptors();
                    } catch (IntrospectionException e)
                    {
                        logger.error("FastUI框架获取bean信息失败！", e);
                        throw new RuntimeException(e);
                    }
                    for (PropertyDescriptor field : properties) {
                        String fieldName = field.getName();
                        if(m.containsKey(fieldName)){
                            Object value = getFieldValueByName(o, fieldName);
                            Object mappingName = m.get(fieldName);                      
                            attributeMap.put(mappingName, value);                       
                        }
                    }   
                }
                if(attributeMap.size()>0)
                    l.add(attributeMap);
            }
        }       
        return l;
    }
    
    /**
     * 过滤所有空值 (要求对象的方法遵循getter/setter模式)
     * @param o
     */
    public static Object excludeNullProperties(Object o) {
        try {
            
            PropertyDescriptor[] properties = java.beans.Introspector.getBeanInfo(o.getClass())
                    .getPropertyDescriptors();
            for (PropertyDescriptor field : properties) {
                String fieldName = field.getName();
                
                Object value = getFieldValueByName(o, fieldName);
                if(value != null) {
                    String str = value + "";
                    if(str.equals("") || str.equals("null")) {
                        setFieldValueByName(o,fieldName, null);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return o;       
    }
    /**
     * 使用反射根据属性名称获取属性值 (要求方法遵循getter/setter模式)
     * */
    private static Object getFieldValueByName(Object o,String fieldName) {  
        try {  
            String firstLetter = fieldName.substring(0, 1).toUpperCase();  
            String getter = "get" + firstLetter + fieldName.substring(1);  
            Method method = o.getClass().getMethod(getter, new Class[] {});  
            Object value = method.invoke(o, new Object[] {});  
            return value;  
        } catch (Exception e) {  
            logger.error(e.getMessage(),e);
            return null;  
        }  
    }   
    /**
     * 使用反射根据属性名称设置属性值 (要求方法遵循getter/setter模式)
     * */
    private static void setFieldValueByName(Object o,String fieldName,Object val) {  
        try {  
            String firstLetter = fieldName.substring(0, 1).toUpperCase();  
            String getter = "set" + firstLetter + fieldName.substring(1);  
            Method method = o.getClass().getMethod(getter, new Class[] {});  
            method.invoke(o, new Object[] {val});  
        } catch (Exception e) {  
            logger.error(e.getMessage(),e);
        }  
    }


    /**
     * 设置警告信息
     * 
     * @param msg
     *            信息内容
     */
    public static Map setWarnMessage(String msg) {
        return setMessage(MessageType.Warn, msg);
    }

    public static Map setWarnMessage(Throwable t) {
        return setWarnMessage(t.getMessage());
    }

    /**
     * 设置成功信息
     * 
     * @param msg
     *            信息内容
     */
    public static Map setOkMessage(String msg) {
        return setMessage(MessageType.Ok, msg);
    }
    
    /**
     * 设置提示信息
     * @param msg
     */
    public static Map setInfoMessage(String msg) {
        return setMessage(MessageType.Info, msg);
    }
    
    /**
     * 设置错误提示信息
     * 
     * @param msg
     *            信息内容
     * **/
    public static Map setErrorMessage(String msg) {
        return setMessage(MessageType.Error, msg);
    }
    
    public static Map setErrorMessage(Throwable t)
    {
        return setErrorMessage(t.getMessage());
    }
    
    public static Map setErrorMessage(String msg, Throwable t)
    {
        return setMessage(MessageType.Error,msg,t.getMessage());
    }
    /**
     * 设置系统异常信息
     * @param msg
     */
    public static Map setSystemMessage(String msg) {
        return setMessage(MessageType.System, msg);
    }
    
    /**
     * 设置系统异常信息
     * @param t
     */
    public static Map setSystemMessage(Throwable t) {
        return setMessage(MessageType.System, t.getMessage());
    }
    /**
     * 设置系统异常信息,带详细的异常信息
     * @param t
     */
    public static Map setSystemMessage(String msg,Throwable t) {
        return setMessage(MessageType.System, msg,getStackTrace(t));
    }
    
    //利用printStackTrace(),格式化堆栈信息
    private static String getStackTrace(Throwable t)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
    /**
     * 设置会话失效/超时信息
     * 
     * @param msg
     *            信息内容
     */
    public static Map setTimeoutMessage(String msg) {
        return setMessage(MessageType.Timeout, msg);
    }

    /**
     * 设置信息
     * 
     * @param status
     *            状态，包括信息（Info）、警告（Warn）、错误（Error）、会话失效/Session超时（Timeout）
     * @param msg
     *            信息内容
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map setMessage(MessageType messageType, String msg) {
        return setMessage(messageType,msg,"");
    }
    
       /**
     * 设置信息,包括异常的堆栈信息
     * 
     * @param status
     *            状态，包括信息（Info）、警告（Warn）、错误（Error）、会话失效/Session超时（Timeout）
     * @param msg
     *            信息内容
     * @param detailMsg
     *            详细信息，如异常的堆栈信息
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map setMessage(MessageType messageType, String msg,String detailmsg) {
        Map m = new HashMap();
        m.put("status", messageType.getType());
        m.put("msg", msg);
        m.put("detailmsg", detailmsg);
        return m;
    }



   
    /**
     * 消息类型
     * 
     * @author: 2012-12-17，范秋海
     */
    public static enum MessageType {
        /**
         * 成功信息
         */
        Ok,

        /**
         * 警告
         */
        Warn {
            @Override
            public String getType() {
                return "warning";
            }
        },

        /**
         * 业务提示信息
         */
        Info,
        
        /**
         * 业务异常
         */
        Error,
        
        /**
         * 系统异常
         */
        System,

        /**
         * 会话失效/超时
         */
        Timeout;
        
        public String getType() {
            return this.name().substring(0, 1).toLowerCase() + this.name().substring(1);
        }
    }
}