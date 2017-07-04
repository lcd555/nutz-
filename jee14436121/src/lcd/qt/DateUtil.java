package lcd.qt;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class DateUtil {
	static public SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
	static public SimpleDateFormat yyyyMMdd8 = new SimpleDateFormat("yyyyMMdd");
    public static String oraDateFormat = "TO_DATE(?, 'yyyy-MM-dd')";
    public static String oraTimeFormat = "TO_DATE(?, 'yyyy-MM-dd HH24:mi:ss')";
    static public SimpleDateFormat MMddYYYY_HHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static public SimpleDateFormat HHmmss = new SimpleDateFormat("HH:mm:ss");
    static public SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
    static public SimpleDateFormat yyyyMMddHH = new SimpleDateFormat("yyyyMMddHH");
    static public SimpleDateFormat yyMMddHH = new SimpleDateFormat("yyMMddHH");
    static public SimpleDateFormat yyyyMMddHHmm = new SimpleDateFormat("yyyyMMddHHmm");
    static public SimpleDateFormat yyyyMMddE = new SimpleDateFormat("yyyy年MM月dd日E");
    static public SimpleDateFormat yyMMdd = new SimpleDateFormat("yy年MM月dd日");
    static public SimpleDateFormat yyyyMMddStr = new SimpleDateFormat("yyyy年MM月dd日");
    static public SimpleDateFormat yyyyMMddEStr = new SimpleDateFormat("yyyy-MM-dd E");

	/**
     * 将字符串 YYYYMMDD 转化为 Date object
     *
     * @param str
     */
    public static Date str2date(String str) {
        Date result = null;
        try {
            Date udate = yyyyMMdd8.parse(str);
            result = new Date(udate.getTime());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	/*婴儿(0,2),儿童[2,12),成人[12-)
	 * 
	 */
	public static int getAge(Date birthDay) throws Exception { 
        //获取当前系统时间
        Calendar cal = Calendar.getInstance(); 
        //如果出生日期大于当前时间，则抛出异常
        if (cal.before(birthDay)) { 
            throw new IllegalArgumentException( 
                "The birthDay is before Now.It's unbelievable!"); 
        } 
        //取出系统当前时间的年、月、日部分
        int yearNow = cal.get(Calendar.YEAR); 
        int monthNow = cal.get(Calendar.MONTH)+1;//?? 
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); 
         
        //将日期设置为出生日期
        cal.setTime(birthDay); 
        //取出出生日期的年、月、日部分  
        int yearBirth = cal.get(Calendar.YEAR); 
        int monthBirth = cal.get(Calendar.MONTH); 
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH); 
        //当前年份与出生年份相减，初步计算年龄
        int age = yearNow - yearBirth; 
        //当前月份与出生日期的月份相比，如果月份小于出生月份，则年龄上减1，表示不满多少周岁
        if (monthNow <= monthBirth) { 
            //如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
            if (monthNow == monthBirth) { 
                if (dayOfMonthNow < dayOfMonthBirth) age--; 
            }else{ 
                age--; 
            } 
        } 
        //System.out.println("age:"+age); 
        return age; 
    }
	
	
    /**
     * 将sql时间转化为yyyy-MM-dd的字符串时间
     *
     * @param date
     */
    static public String date2str(Date date) {
        if (date == null) return "";
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
	   /**
     * 两个时间相差多少小时
     * @param date1 时间参数 1 格式：1990-01-01 12:00:00
     * @param date2 时间参数 2 格式：2009-01-01 12:00:00
     * @return long 返回值为：xx小时
     */
    public static long getDiffHours(Date date1, Date date2) {
        long hour = 0;
        try {
            long time1 = date1.getTime();
            long time2 = date2.getTime();
            long diff=time2 - time1 ;
            hour = (diff / (60 * 60 * 1000));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return hour;
    }	
	   /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            if(time1<time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day + "天" + hour + "小时" + min + "分" + sec + "秒";
    }
	/**
     * 生成yymmddhhMMssSSS+2位随机数共17位ID     *
     * @return 返回ID
     */
    public static String getOrderID() {//
    	String id0=new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
    	Random random = new Random();
    	id0+= random.nextInt(9)+""+ random.nextInt(9);
        return id0;
    }
	/**
     * 获得UUID形成的32位ID
     *
     * @return 返回ID
     */
    public static String getLrwUID() {//32位十六进制，8-4-4-4-12
        return UUID.randomUUID().toString().replace("-", "");
    }

	private static String digits(long val, int d) {
    	long hi = 1L << (d * 4);
    	return "";
    }
	/**
     * 以62进制（字母加数字）生成最短的19位UUID，UUID
     * 性能几乎翻倍提升
     * @return
     */
    public static String lrwuuid() {
    	UUID uuid = UUID.randomUUID();
    	StringBuilder sb = new StringBuilder();
    	sb.append(digits(uuid.getMostSignificantBits() >> 32, 8));
    	sb.append(digits(uuid.getMostSignificantBits() >> 16, 4));
    	sb.append(digits(uuid.getMostSignificantBits(), 4));
    	sb.append(digits(uuid.getLeastSignificantBits() >> 48, 4));
    	sb.append(digits(uuid.getLeastSignificantBits(), 12));
    	return sb.toString();
    }
    /**
     * 得到当前日期得到当前的日期yyyy-mm-DD
     */
    public static String getToday() {
        return getDateStr(new java.util.Date());
    }
    /**
     * 将时间转化为yyyy-MM-dd的字符串时间
     *
     * @param date
     */
    public static String getDateStr(java.util.Date date) {
        if (date == null) return "";
        try {
            return yyyyMMdd.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 将时间转化为yyyyMMdd的字符串时间
     *
     * @param date
     */
    public static String getDateymd8(java.util.Date date) {
        if (date == null) return "";
        try {
            return yyyyMMdd8.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 将时间转化为yyMMddHH的字符串时间
     *
     * @param date
     */
    public static String getDateymdh8(java.util.Date date) {
        if (date == null) return "";
        try {
            return yyMMddHH.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 获得当前时间形成的15位ID
     *
     * @return 返回ID
     */
    public static String getLrwTID() {
        String  parrten = "yyMMddHHmmssSSS";
        java.text.SimpleDateFormat sdf = new SimpleDateFormat(parrten);
        java.util.Date cday = new java.util.Date();
        return sdf.format(cday);
    }
}
