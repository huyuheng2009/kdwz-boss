package com.yogapay.boss.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;


/**
 * 
 * @author 
 * 
 */
public final class JsonUtil
{

    /**
     * 
     * @param jsonString
     * @return
     * @throws UrlResponseException
     */


    private static void setDataFormat2JAVA()
    {
        // 设定日期转换格式
        JSONUtils.getMorpherRegistry().registerMorpher(
                new DateMorpher(new String[]
                { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" }));
    }

    public static Object getProperty(String jsonString, String property)
    {
        JSONObject jsonObject = null;
        try
        {
            setDataFormat2JAVA();
            jsonObject = JSONObject.fromObject(jsonString);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return jsonObject.get(property);
    }

    /**
     * 从一个JSON 对象字符格式中得到一个java对象，形如： {"id" : idValue, "name" : nameValue,
     * "aBean" : {"aBeanId" : aBeanIdValue, ...}}
     * 
     * @param object
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object getDTO(String jsonString, Class clazz)
    {
        JSONObject jsonObject = null;
        try
        {
            setDataFormat2JAVA();
            jsonObject = JSONObject.fromObject(jsonString);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return JSONObject.toBean(jsonObject, clazz);
    }

    /**
     * 从一个JSON 对象字符格式中得到一个java对象，其中beansList是一类的集合，形如： {"id" : idValue, "name" :
     * nameValue, "aBean" : {"aBeanId" : aBeanIdValue, ...}, beansList:[{}, {},
     * ...]}
     * 
     * @param jsonString
     * @param clazz
     * @param map
     *                集合属性的类型 (key : 集合属性名, value : 集合属性类型class) eg:
     *                ("beansList" : Bean.class)
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object getDTO(String jsonString, Class clazz, Map map)
    {
        JSONObject jsonObject = null;
        try
        {
            setDataFormat2JAVA();
            jsonObject = JSONObject.fromObject(jsonString);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return JSONObject.toBean(jsonObject, clazz, map);
    }

    /**
     * 从一个JSON数组得到一个java对象数组，形如： [{"id" : idValue, "name" : nameValue}, {"id" :
     * idValue, "name" : nameValue}, ...]
     * 
     * @param object
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getDTOArray(String jsonString, Class clazz)
    {
        setDataFormat2JAVA();
        JSONArray array = JSONArray.fromObject(jsonString);
        Object[] obj = new Object[array.size()];
        for (int i = 0; i < array.size(); i++)
        {
            JSONObject jsonObject = array.getJSONObject(i);
            obj[i] = JSONObject.toBean(jsonObject, clazz);
        }
        return obj;
    }

    /**
     * 从一个JSON数组得到一个java对象数组，形如： [{"id" : idValue, "name" : nameValue}, {"id" :
     * idValue, "name" : nameValue}, ...]
     * 
     * @param object
     * @param clazz
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] getDTOArray(String jsonString, Class clazz, Map map)
    {
        setDataFormat2JAVA();
        JSONArray array = JSONArray.fromObject(jsonString);
        Object[] obj = new Object[array.size()];
        for (int i = 0; i < array.size(); i++)
        {
            JSONObject jsonObject = array.getJSONObject(i);
            obj[i] = JSONObject.toBean(jsonObject, clazz, map);
        }
        return obj;
    }

    /**
     * 从一个JSON数组得到一个java对象集合
     * 
     * @param object
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List getDTOList(String jsonString, Class clazz)
    {
        setDataFormat2JAVA();
        JSONArray array = JSONArray.fromObject(jsonString);
        List list = new ArrayList();
        for (Iterator iter = array.iterator(); iter.hasNext();)
        {
            JSONObject jsonObject = (JSONObject) iter.next();
            list.add(JSONObject.toBean(jsonObject, clazz));
        }
        return list;
    }

    /**
     * 从一个JSON数组得到一个java对象集合，其中对象中包含有集合属性
     * 
     * @param object
     * @param clazz
     * @param map
     *                集合属性的类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List getDTOList(String jsonString, Class clazz, Map map)
    {
        setDataFormat2JAVA();
        JSONArray array = JSONArray.fromObject(jsonString);
        List list = new ArrayList();
        for (Iterator iter = array.iterator(); iter.hasNext();)
        {
            JSONObject jsonObject = (JSONObject) iter.next();
            list.add(JSONObject.toBean(jsonObject, clazz, map));
        }
        return list;
    }

    /**
     * 从json HASH表达式中获取一个map，该map支持嵌套功能 形如：{"id" : "johncon", "name" : "小强"}
     * 注意commons
     * -collections版本，必须包含org.apache.commons.collections.map.MultiKeyMap
     * 
     * @param object
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map getMapFromJson(String jsonString)
    {
        setDataFormat2JAVA();
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        Map map = new HashMap();
        for (Iterator iter = jsonObject.keys(); iter.hasNext();)
        {
            String key = (String) iter.next();
            map.put(key, jsonObject.get(key));
        }
        return map;
    }

    /**
     * 从json数组中得到相应java数组 json形如：["123", "456"]
     * 
     * @param jsonString
     * @return
     */
    public static Object[] getObjectArrayFromJson(String jsonString)
    {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        return jsonArray.toArray();
    }

    public static String toJson(Object v)
    {
        return toJson(v, "");
    }

    /**
     * 将java对象转成JSON字符串，形如： [{"id" : idValue, "name" : nameValue}, {"id" :
     * idValue, "name" : nameValue}, ...]
     * 
     * @param v
     *                java对象
     * @param datePattern
     *                默认格式为："yyyy-MM-dd HH:mm:ss"
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String toJson(Object v, String datePattern)
    {
        JsonConfig jsonConfig = new JsonConfig();
        if (!"".equals(datePattern) && null != datePattern)
        {
            jsonConfig.registerJsonValueProcessor(Date.class,
                    new JsonDateValueProcessor(datePattern));
            jsonConfig.registerJsonValueProcessor(Timestamp.class,
                    new JsonDateValueProcessor(datePattern));
        }
        else
        {
            jsonConfig.registerJsonValueProcessor(Date.class,
                    new JsonDateValueProcessor());
            jsonConfig.registerJsonValueProcessor(Timestamp.class,
                    new JsonDateValueProcessor());
        }
        jsonConfig.registerJsonValueProcessor(java.sql.Date.class,
                new JsonDateValueProcessor());
        jsonConfig.registerJsonValueProcessor(Integer.class,
                new JsonDateValueProcessor());
        
        jsonConfig.registerJsonValueProcessor(Long.class,
                new JsonDateValueProcessor());
        
        jsonConfig.registerJsonValueProcessor(Float.class,
                new JsonDateValueProcessor());
        
        jsonConfig.registerJsonValueProcessor(Double.class,
                new JsonDateValueProcessor());
        
        jsonConfig.registerJsonValueProcessor(Short.class,
                new JsonDateValueProcessor());
        if ((v instanceof Object[]) || (v instanceof int[])
                || (v instanceof short[]) || (v instanceof boolean[])
                || (v instanceof long[]) || (v instanceof float[])
                || (v instanceof Collection) || (v instanceof Vector))
        {
            JSONArray jsonArray = JSONArray.fromObject(v, jsonConfig);
            return jsonArray.toString();
        }
        JSONObject json = JSONObject.fromObject(v, jsonConfig);
        return json.toString();
    }

    public static Object toJSON(Object v, JsonConfig jsonConfig)
            throws Exception
    {
        if ((v instanceof Object[]) || (v instanceof int[])
                || (v instanceof short[]) || (v instanceof boolean[])
                || (v instanceof long[]) || (v instanceof float[])
                || (v instanceof Collection) || (v instanceof Vector))
        {
            net.sf.json.JSONArray jsonArray = JSONArray.fromObject(v,
                    jsonConfig);
            return jsonArray.toString();
        }

        net.sf.json.JSONObject json = JSONObject.fromObject(v, jsonConfig);
        return json.toString();
    }

    public static void main(String[] args)
    {

    	
    	List<String> phone = new ArrayList<String>() ;
    	
    	phone.add("123456") ;
    	phone.add("546123") ;
    	
    	System.out.println(JsonUtil.toJson(phone));
    	
    	 Object [] ph = getObjectArrayFromJson(JsonUtil.toJson(phone)) ;
    	/*String [] phones = (String[]) getObjectArrayFromJson(JsonUtil.toJson(phone)) ;
    	for (int i = 0; i < phones.length; i++) {
			System.out.println(phones[i]);
		}
    	*/
    	
    	System.out.println("*************");
    	
    	Map<String, Object> map = new HashMap<String, Object>() ;
    	map.put("12312312", JsonUtil.toJson(phone)) ;
    	System.out.println(JsonUtil.toJson(map));
    	
    	Map<String, Object> retMap = getMapFromJson(JsonUtil.toJson(map)) ;
    	
    	System.out.println(retMap.get("12312312"));
    	
    	
    	
    	
    }

}
