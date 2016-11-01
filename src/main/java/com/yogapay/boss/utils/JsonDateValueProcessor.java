/*
 * @fileName：JsonDateValueProcessor.java    2011-8-18 下午02:19:55
 *
 * Copyright (c) 2011 MSD Technologies, Inc. All rights reserved.
 * <P>Title：<P>
 * <P>Description：<P>
 * <P>Copyright: Copyright (c) 2011 <P>
 * <P>Company: MSD <P>
 * @author zengzp
 * @version 1.0
 *
 */
package com.yogapay.boss.utils;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * <P>
 * Description：
 * </P>
 * 
 * @author zengzp
 * @version 1.0
 */
public class JsonDateValueProcessor implements JsonValueProcessor
{

    /**
     * datePattern
     */
    private String datePattern = "yyyy-MM-dd HH:mm:ss";

    /**
     * JsonDateValueProcessor
     */
    public JsonDateValueProcessor()
    {
        super();
    }
    
    /**
     * @param format
     */
    public JsonDateValueProcessor(String format)
    {
        super();
        this.datePattern = format;
    }

    /**
     * @param value
     * @param jsonConfig
     * @return Object
     */
    @Override
    public Object processArrayValue(Object value, JsonConfig jsonConfig)
    {
        return process(value);
    }

    /**
     * @param key
     * @param value
     * @param jsonConfig
     * @return Object
     */
    @Override
    public Object processObjectValue(String key, Object value,
            JsonConfig jsonConfig)
    {
        return process(value);
    }

    /**
     * process
     * 
     * @param value
     * @return
     */
    private Object process(Object value)
    {
        try
        {
            if (value instanceof Date||value instanceof Timestamp)
            {
                SimpleDateFormat sdf = new SimpleDateFormat(datePattern,
                        Locale.UK);
                return sdf.format((Date) value);
            }
            if (value instanceof java.sql.Date) {
            	 SimpleDateFormat sdf = new SimpleDateFormat(datePattern,
                         Locale.UK);
            	 return sdf.format(new Date(((java.sql.Date)value).getTime()));
			}
            if (value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double || value instanceof Short) {
				return value.toString() ;
			}
            
            
            return value == null ? "" : value.toString();
        }
        catch (Exception e)
        {
            return "";
        }

    }

    /**
     * @return the datePattern
     */
    public String getDatePattern()
    {
        return datePattern;
    }

    /**
     * @param pDatePattern
     *                the datePattern to set
     */
    public void setDatePattern(String pDatePattern)
    {
        datePattern = pDatePattern;
    }

}
