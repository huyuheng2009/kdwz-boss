package com.yogapay.boss.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class DateUtils {
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String formatDate(Date date) {
		if(date==null)
			return "";
		DateFormat sf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		return sf.format(date);
	}
	
	
	public static String formatDate(String date,String pattern) throws ParseException {
		if(date==null)
			return "";
		if (pattern==null) {
			return date ;
		}
		DateFormat sf1 = new SimpleDateFormat(pattern);
		DateFormat sf2 = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		return sf2.format(sf1.parse(date));
	}

	public static Date parseDate(String str) {
		DateFormat sf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		try {
			return sf.parse(str);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String formatDate(Date date, String parrent) {
		if(date==null)
			return "";
		if (parrent == null || "".equals(parrent)) {
			return formatDate(date);
		} else {
			DateFormat sf = new SimpleDateFormat(parrent);
			return sf.format(date);
		}
	}
	
	
	public static String formatDate(Timestamp date, String parrent) {
		if(date==null)
			return "";
		if (parrent == null || "".equals(parrent)) {
			parrent="yyyy-MM-dd";
		}
		DateFormat sf = new SimpleDateFormat(parrent);
		return sf.format(date);
	}
	
	public static Date parseDate(String str, String parrent) {
		if (parrent == null || "".equals(parrent)) 
			return parseDate(str);
		DateFormat sf = new SimpleDateFormat(parrent);
		try {
			return sf.parse(str);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 判断两日期的差是否超过1天
	 * 0为未超过，1为超过
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int diffDateGreaterOneDay(Date date1,Date date2){
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		if(betweenYears>0){
			return 1;
		}else if(betweenYears==0){
			int betweenMonth = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
			if(betweenMonth>0){
				return 1;
			}else if(betweenMonth==0){
				return (c2.get(Calendar.DAY_OF_MONTH) - c1.get(Calendar.DAY_OF_MONTH))>0?1:0;
			}
		}
		return 0;
	}
	
	/**
	 * 计算两时间所相差的天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int diffDate(Date date1, Date date2) {
		int diffDay = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		// 保证第二个时间一定大于第一个时间
		if (c1.after(date2)) {
			c1 = c2;
			c2.setTime(date1);
		}
		int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		diffDay = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
		for (int i = 0; i < betweenYears; i++) {
			c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
			diffDay += c1.getMaximum(Calendar.DAY_OF_YEAR);
		}
		return diffDay;
	}
	
	/**  
	 * 根据出生日期计算年龄  
	 *   
	 * @param birthDay  
	 * @return 未来日期返回0  
	 * @throws Exception  
	 */  
	public static int getAge(Date birthDay){   
	  
	    Calendar cal = Calendar.getInstance();   
	  
	    if (cal.before(birthDay)) {   
	        return 0;   
	    }   
	  
	    int yearNow = cal.get(Calendar.YEAR);   
	    int monthNow = cal.get(Calendar.MONTH);   
	    int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);   
	    cal.setTime(birthDay);   
	  
	    int yearBirth = cal.get(Calendar.YEAR);   
	    int monthBirth = cal.get(Calendar.MONTH);   
	    int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);   
	  
	    int age = yearNow - yearBirth;   
	  
	    if (monthNow <= monthBirth) {   
	        if (monthNow == monthBirth) {   
	            if (dayOfMonthNow < dayOfMonthBirth) {   
	                age--;   
	            }   
	        } else {   
	            age--;   
	        }   
	    }   
	  
	    return age;   
	}   
	  
	/**  
	 * 根据出生日期计算年龄  
	 *   
	 * @param strBirthDay  
	 *            字符串型日期  
	 * @param format  
	 *            日期格式  
	 * @return 未来日期返回0  
	 * @throws Exception  
	 */  
	public static int getAge(String strBirthDay, String format){   
	  
	    DateFormat df = new SimpleDateFormat(format);   
	    Date birthDay = null;
		try {
			birthDay = df.parse(strBirthDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}   
	    return birthDay == null ? 0 : getAge(birthDay);   
	}  
	
	
	public static Date addDate(Date date,String time){
		if(date==null)
			return null;
		if(time==null||"".equals(time)||time.length()<2)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String unit = time.substring(time.length()-1);
		if("y".equalsIgnoreCase(unit)){
			cal.add(Calendar.YEAR, Integer.parseInt(time.substring(0,time.length()-1)));
		}else if("m".equalsIgnoreCase(unit)){
			cal.add(Calendar.MONTH, Integer.parseInt(time.substring(0,time.length()-1)));
		}
		return cal.getTime();
	}
	
	 /** 
	  * 日期加减,d:天,h:小时,m:分钟 
	  *  
	  * @return 
	  */  
	 public static Date addDate(Date date,int d,int h,int m) {  
		 Calendar cal = Calendar.getInstance();
		cal.setTime(date); 
		cal.add(Calendar.DAY_OF_MONTH, d);
		cal.add(Calendar.HOUR_OF_DAY, h);
		cal.add(Calendar.MINUTE, m);
		return cal.getTime() ; 
	 }
	
	
	
	 /** 
	  * 获得昨天日期 
	  *  
	  * @return 
	  */  
	 public static String getYesterday() {  
	     Date date = new Date();  
	     date = new Date(date.getTime() - 1000 * 60 * 60 * 24);  
	     SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd");  
	     return dateFm.format(date);  
	 }
	
	 /**
	  * 获得两日期的时间差
	  * @param date1
	  * @param date2
	  * @param type "hour","min","sec","ms"
	  * @return
	  */
	public static long getDiffTime(String date1,String date2,String type){
		long ret=0;
		try {
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Date begin= dfs.parse(date1);
		    Date end = dfs.parse(date2);
		   long between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒
		   if("ms".equalsIgnoreCase(type)){
			   ret=between*1000;
		   }
		   else if("sec".equalsIgnoreCase(type)){
			   ret=between;
		   }
		   else if("min".equalsIgnoreCase(type)){
			   ret=between/60;
		   }
		   else if("hour".equalsIgnoreCase(type)){
			   ret=between/(60*60);
		   }

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ret;
	} 
	
	 /**
	  * 获得两日期的时间差
	  * @param date1
	  * @param date2
	  * @param type "hour","min","sec","ms"
	  * @return
	  */
	public static long getDiffTime(Date begin,Date end,String type){
		long ret=0;
		try {

		   long between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒
		   if("ms".equalsIgnoreCase(type)){
			   ret=between*1000;
		   }
		   else if("sec".equalsIgnoreCase(type)){
			   ret=between;
		   }
		   else if("min".equalsIgnoreCase(type)){
			   ret=between/60;
		   }
		   else if("hour".equalsIgnoreCase(type)){
			   ret=between/(60*60);
		   }

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
    
    /**
     * 获得今天是一周的周几
     * @return
     */
	public static String getCurDayOfWeek(){
		// 获得今天是一周的周几，从周日(0)开始
		GregorianCalendar obj = new GregorianCalendar();
		obj.setTime(new java.util.Date());
		int week = obj.get(GregorianCalendar.DAY_OF_WEEK) - 1;
		String dayOfWeek = "";
		switch (week) {
		case 0:
			dayOfWeek = "周日";
			break;
		case 1:
			dayOfWeek = "周一";
			break;
		case 2:
			dayOfWeek = "周二";
			break;
		case 3:
			dayOfWeek = "周三";
			break;
		case 4:
			dayOfWeek = "周四";
			break;
		case 5:
			dayOfWeek = "周五";
			break;
		case 6:
			dayOfWeek = "周六";
			break;
		default:
			break;
		}
		return dayOfWeek;
    }

	/**
	 * 返回传入日期的星期
	 * @param s
	 * @return
	 */
	public static String getDayOfWeek(String s) {
		final String dayNames[] = { "周日", "周一", "周二", "周三", "周四", "周五","周六" };
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		try {
			date = sdfInput.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek < 0){
			dayOfWeek = 0;
		}
		return (dayNames[dayOfWeek]);
	}
	
     /**
     * 获取今天的日期，格式自定
     * 
     * @param pattern -
     *                设定显示格式
     * @return String - 返回今天的日期
     */
    public static String getToday(String pattern)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        DateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }
    
    /**
     * 根据原来的时间（Date）获得相对偏移 N 月的时间（Date）
     * 
     * @param protoDate
     *                原来的时间（java.util.Date）
     * 
     * @param dateOffset
     *                （向前移正数，向后移负数）
     * 
     * @return 时间（java.util.Date）
     */
    public static Date getOffsetMonthDate(Date protoDate, int monthOffset)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(protoDate);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - monthOffset);
        return cal.getTime();
    }

    /**
     * 根据原来的时间（Date）获得相对偏移 N 天的时间（Date）
     * 
     * @param protoDate
     *                原来的时间（java.util.Date）
     * 
     * @param dateOffset
     *                （向前移正数，向后移负数）
     * 
     * @return 时间（java.util.Date）
     */
    public static Date getOffsetDayDate(Date protoDate, int dateOffset)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(protoDate);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)
                - dateOffset);
        return cal.getTime();
    }
	
    public static Date getBeginDate(String key)
    {
    	Date nowDate = new Date() ;
    	if (StringUtils.isEmptyWithTrim(key)) {
			return nowDate ;
		}
    	int dayKey = Integer.parseInt(key.substring(0, 1)) ;
    	int timeKey = Integer.parseInt(key.substring(1, key.length())) ;
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String dateString = sdf.format(nowDate) ;
		switch (dayKey) {
		case 0:
			dateString = sdf.format(nowDate);
			break;
		case 1:
			dateString = sdf.format(addDate(nowDate, 1, 0, 0));
			break;
		case 2:
			dateString = sdf.format(addDate(nowDate, 2, 0, 0));
			break;
		default:
			dateString = sdf.format(nowDate);
			break;
		}
		String timeString = "08:00:00" ;
		switch (timeKey) {
		case 0:
			timeString = "00:00:00";
			break;
		case 1:
			timeString = "06:00:00";
			break;
		case 2:
			timeString = "08:00:00";
			break;
		case 3:
			timeString = "10:00:00";
			break;
		case 4:
			timeString = "12:00:00";
			break;
		case 5:
			timeString = "14:00:00";
			break;
		case 6:
			timeString = "16:00:00";
			break;
		case 7:
			timeString = "18:00:00";
			break;
		default:
			timeString = "08:00:00";
			break;
		}
    Date beginDate = parseDate(dateString+" "+timeString) ;	
    return beginDate ;	
    }
    
    public static Date getEndDate(String key)
    {
    	Date nowDate = new Date() ;
    	if (StringUtils.isEmptyWithTrim(key)) {
			return addDate(nowDate, 0,2,0) ;
		}
    	int dayKey = Integer.parseInt(key.substring(0, 1)) ;
    	int timeKey = Integer.parseInt(key.substring(1, key.length())) ;
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String dateString = sdf.format(nowDate) ;
		switch (dayKey) {
		case 0:
			dateString = sdf.format(nowDate);
			break;
		case 1:
			dateString = sdf.format(addDate(nowDate, 1, 0, 0));
			break;
		case 2:
			dateString = sdf.format(addDate(nowDate, 2, 0, 0));
			break;
		default:
			dateString = sdf.format(nowDate);
			break;
		}
		String timeString = "09:59:59";
		switch (timeKey) {
		case 0:
			timeString = "05:59:59";
			break;
		case 1:
			timeString = "07:59:59";
			break;
		case 2:
			timeString = "09:59:59";
			break;
		case 3:
			timeString = "11:59:59";
			break;
		case 4:
			timeString = "13:59:59";
			break;
		case 5:
			timeString = "15:59:59";
			break;
		case 6:
			timeString = "17:59:59";
			break;
		case 7:
			timeString = "23:59:59";
			break;
		default:
			timeString = "09:59:59";
			break;
		}
    Date endDate = parseDate(dateString+" "+timeString) ;	
    return endDate ;	
    }
    
    
   
    public static boolean bofore(Date date) {
		return new Date().before(date);
	}
  //获取上个月格式yyyy-MM
  	public static String getLastMonth(){
  		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
  		Calendar cal = Calendar.getInstance();
  		cal.set(Calendar.DAY_OF_MONTH, 1); 
  		cal.add(Calendar.DATE, -1);
  		 Date date = cal.getTime();
          return sdf.format(date);
  	}
  	
  	
  //获取上个月格式yyyy-MM
  	public static String getLastMonth(Date curDate){
  		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
  		Calendar cal = Calendar.getInstance();
  		 cal.setTime(curDate);
  		 cal.set(Calendar.DAY_OF_MONTH, 1); 
  		 cal.add(Calendar.DATE, -1);
  		 Date date = cal.getTime();
          return sdf.format(date);
  	}
    
    
	
         public static void main(String[] args) throws Exception{
        	 System.out.println(formatDate(getBeginDate("03")));
         }

}
