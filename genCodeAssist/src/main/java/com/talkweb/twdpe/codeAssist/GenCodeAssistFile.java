package com.talkweb.twdpe.codeAssist;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import org.springframework.core.io.*;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 生成用于FastUI控件库的代码辅助Fastui.sdoc.js文件
 * @author: HuangYong
 * 2013-5-29
 */
public class GenCodeAssistFile
{
    private static final String FILENAME="fastui.sdoc.js";
    
    public static void main(String[] args) { 
        try { 
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources =  resolver.getResources("classpath:/output/*.json");
            //Resource[] resources =  resolver.getResources("output/fastDev.Util.StringUtil.json");
            StringBuilder sb = new StringBuilder();
            sb.append("/**\r\n");
            sb.append(" * "+FILENAME+"是fastUI控件库的代码辅助文件，由工具自动生成。 "+(new Date()).toLocaleString()+"\r\n");
            sb.append(" */\r\n");
            for(int i=0;i<resources.length;i++)
            {
                System.out.println("file"+i+":"+resources[i].getFilename());
                File file =  resources[i].getFile();
                JSONObject jo =JSONObject.parseObject(FileCopyUtils.copyToString(new FileReader(file)));
                
                //添加类定义和类文档
                String className = jo.getString("name");
                if(className==null) throw new Exception("json文件name不能为空！"+resources[i].getFilename());
                className = className.substring(className.lastIndexOf(".")+1);
//                String[] str = name.split(".");
//                name = str[str.length - 1];
                System.out.println(resources[i].getFilename()+" name is ["+className+"]");
                String doc = jo.getString("doc");
                sb.append(formatAnnotation(doc,null,null));
                sb.append(formatClass(className));
                
                //添加方法定义和文档
                JSONArray methods = jo.getJSONObject("members").getJSONArray("method");
                for (int j=0;j<methods.size();j++)
                {
                    JSONObject methodJO = methods.getJSONObject(j);
                    Boolean isPrivate = methodJO.getBoolean("private");
                    if( isPrivate!=null && isPrivate ) continue;
                    
                    String methodName = methodJO.getString("name");
                    String methodDoc = methodJO.getString("doc");
                    JSONArray params = methodJO.getJSONArray("params");
                    String[] paramsNames = new String[params.size()];
                    String[] paramsDocs = new String[params.size()];
                    String rtn = null;
                    if(!methodJO.getJSONObject("return").getString("type").equals("undefined"))
                        rtn = "@return {"+methodJO.getJSONObject("return").getString("type")+"} "+methodJO.getJSONObject("return").getString("doc");
                    for(int m=0;m<params.size();m++)
                    {
                        JSONObject paramJO = params.getJSONObject(m);
                        paramsNames[m] = paramJO.getString("name");
                        String optionalLeft = paramJO.getBoolean("optional")?" [":" ";
                        String optionalRight = paramJO.getBoolean("optional")?"] ":" ";
                        //多参数类型，取起一个类型，加上注释，否则智能提示功能时会显示不出来。
                        String[] type = paramJO.getString("type").split("\\|");
                        String paramsType = paramJO.getString("doc");
                        if(type.length>1) paramsType+=",此参数类型还可以为：";
                        for(int a=1;a<type.length;a++){
                            paramsType+=type[a];
                        }
                        
                        paramsDocs[m] = "@param {"+paramJO.getString("type").split("\\|")[0]+"} "+optionalLeft+paramsNames[m]+optionalRight+paramsType;
                    }
                    sb.append(formatAnnotation(methodDoc, paramsDocs, rtn));
                    sb.append(formatMethod(className, methodName, paramsNames));
                   
                }
                methods = null;
                
                //添加属性定义及文档
                JSONArray properties = jo.getJSONObject("members").getJSONArray("property");
                for(int k=0;k<properties.size();k++)
                {
                    JSONObject propertyJO = properties.getJSONObject(k);
                    Boolean isPrivate = propertyJO.getBoolean("private");
                    if( isPrivate!=null && isPrivate ) continue;
                    
                    String propertyName = propertyJO.getString("name");
                    String defaultValue = propertyJO.getString("default");
                    String[] propertyType = new String[1];
                    propertyType[0] = "@type {"+propertyJO.getString("type")+"}";
                    String propertyDoc = propertyJO.getString("doc");

                    sb.append(formatAnnotation(propertyDoc, propertyType, null));//添加属性文档
                    sb.append(formatProperty(className, propertyName, defaultValue));//添加属性定义
                }
                properties = null;
              
                //添加事件定义及文档
                JSONArray events = jo.getJSONObject("members").getJSONArray("event");
                
                for(int l=0;l<events.size();l++)
                {
                    JSONObject eventJO = events.getJSONObject(l);
                    Boolean isPrivate = eventJO.getBoolean("private");
                    if( isPrivate!=null && isPrivate ) continue;
                                        
                    String eventName = eventJO.getString("name");
                    String eventDoc = eventJO.getString("doc");
                    
                    sb.append(formatAnnotation(eventDoc, null, null));
                    sb.append(formatEvent(className,eventName));
                    
                }
                
                //System.out.println(sb.toString());
                writeFile(".\\src\\main\\resources\\"+FILENAME,sb.toString());
                
            }
        } catch (IOException e) { 
            e.printStackTrace(); 
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    private static String formatEvent(String className,String event){
        return className+".prototype."+event+" = fastDev.noop;";
    }
    private static String formatProperty(String className,String property,String defaultValue){
        if(defaultValue==null) 
            defaultValue = "null";//空对象直接赋值为null
//        else if(defaultValue.equals("true")||defaultValue.equals("false") )
//            defaultValue =defaultValue; //Boolean类型数据直接赋值为true,false
//        else if (defaultValue.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$"))
//            defaultValue =defaultValue;//数字类型数据直接赋值为数字
//        else
//            defaultValue = "\""+defaultValue+"\"";//其他作为字符串类型，加上引号。
        
        return className+".prototype."+property+" = "+defaultValue+";";
    }
    //格式化输出方法定义
    private static String formatMethod(String className,String method,String[] param){
        String elems = "";
        if(param!=null) {
            for(int i=0;i<param.length;i++){
                if(i==0)
                    elems = param[i];
                else
                    elems=elems+", "+param[i];
            }
        }
        return className+".prototype."+method+" = function("+elems+"){};";
    }
    //格式化输出类定义
    private static String formatClass(String className){
        return " function "+className+"(){};";
    }
    //格式化输出注释
    private static String formatAnnotation(String annotation,String[] params,String rtn){
        annotation = annotation.substring(0, annotation.length()-1);//去掉最尾部的/n換行符
        String temp = "\r\n\r\n/**\r\n *"+annotation+"\r\n";
        if(params!=null)
        {
            for(int i=0;i<params.length;i++)
            {
                temp+=" * "+params[i].replaceAll("<p>|</p>|\n", "")+"\r\n";//替換掉<p></p>\n
            }
        }
        if(rtn!=null&&rtn.trim()!="")
            temp+=" * "+rtn.replaceAll("<p>|</p>|\n", "")+"\r\n";//替換掉<p></p>\n
        
        temp+=" */\r\n";
        return temp;
    }
    
    private static void writeFile(String filePath, String sets) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        PrintWriter out = new PrintWriter(fw);
        out.write(sets);
        out.println();
        fw.close();
        out.close();
        
       }
}
