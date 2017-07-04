package lcd.qt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lcd.Pojo.EasyUITree;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.json.Json;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;


public class BaseSrv {
	protected String[] questype={"0","单选题","多选题","不定项选择题","判断题","win基本操作题","上网题","word操作题","excel操作题","ppt操作题","CC程序填空题","CC程序改错题","CC程序设计题","AC基本操作题","AC简单应用题","AC综合应用题","填空题","简答题"};
	protected String[] bigtino={"一","二","三","四","五","六","七","八"};

	/*
	 *    获取Sha256Hash加密密码
	 */
      protected String lrwCode(String password,String salt){
            if(salt==""){
                  /*RandomNumberGenerator rng = new SecureRandomNumberGenerator();
            salt = rng.nextBytes().toBase64();*/
                  salt="abcdefghijklmnopqrstuvwx";
            }
            return new Sha256Hash(password, salt, 1024).toBase64();
      }
      
      //***************************************************
      
      
		/*
		 * 取登录时的IP
		 */
		protected String getIpAddr(HttpServletRequest request) {
	    	String ip="";
	    	ip = request.getHeader("X-Forwarded-For");
			if (null != ip && !"".equals(ip.trim()) && !"unknown".equalsIgnoreCase(ip)) {
				int index = ip.indexOf(',');
				if (index != -1) {return ip.substring(0, index);}
				else {return ip;}
			}
	    	ip = request.getHeader("X-Real-IP");
			if (null != ip && !"".equals(ip.trim()) && !"unknown".equalsIgnoreCase(ip)) {return ip;}
			ip = request.getRemoteAddr();
			if(ip.startsWith("0:")) return "127.0.0.1";//本机ip
			else return ip;
		}
	    /**
	     * 根据查询条件分页,返回封装好的 Easyui.datagrid JSON
	     *
	     * @param dao
	     * @param obj
	     * @param curPage
	     * @param pageSize
	     * @param cnd
	     * @return
	     */
	    public <T> String listPageJson(Dao dao, Class<T> obj, int curPage,
	                                   int pageSize, Condition cnd) {
	        Map<String, Object> jsonobj = new HashMap<String, Object>();
	        Pager pager = dao.createPager(curPage, pageSize);
	        List<T> list = dao.query(obj, cnd, pager);
	        pager.setRecordCount(dao.count(obj, cnd));//记录数需手动设置
	        jsonobj.put("total", pager.getRecordCount());
	        jsonobj.put("rows", list);
	        return Json.toJson(jsonobj);
	    }

	    /**
	     * 根据查询条件分页,返回封装好的 Easyui.datagrid JSON
	     *
	     * @param dao
	     * @param obj
	     * @param cnd
	     * @param pager
	     * @return
	     */
	    public <T> String listPageJson(Dao dao, Class<T> obj, Condition cnd, Pager pager) {
	        Map<String, Object> jsonobj = new HashMap<String, Object>();
	        List<T> list = dao.query(obj, cnd, pager);
	        pager.setRecordCount(dao.count(obj, cnd));//记录数需手动设置
	        jsonobj.put("total", pager.getRecordCount());
	        jsonobj.put("rows", list);
	        return Json.toJson(jsonobj);
	    }
	    /*
	     * 数据封装成Easyui.tree json
	     * 树形数据
	     */
	    	@At
	    	@Ok("raw")
	    	public String strtree(List<String> strlist,HttpServletRequest req, HttpSession session) {
	    	    List<EasyUITree> eList = new ArrayList<EasyUITree>();
	    	    if(strlist.size() != 0){
	    	    	EasyUITree e = new EasyUITree();
    	            e.setId("0");
    	            e.setText("全部");
    	            List<EasyUITree> eList2 = new ArrayList<EasyUITree>();
	    	    	for (int i = 0; i < strlist.size(); i++) {
	    		            EasyUITree e1 = new EasyUITree();
	    		            e1.setId((i+1)+"");
	    		            e1.setText(strlist.get(i));
	    		            e1.setState("open");
	    		            eList2.add(e1);
	    	            }
	    	            e.setChildren(eList2);
	    	            e.setState("closed");
	    	            eList.add(e);
	    	    }
	    	    return Json.toJson(eList);
	    	}	 
		    /*
		     * 数据封装成Easyui.tree json
		     * 树形数据
		     */
		    	@At
		    	@Ok("raw")
		    	public String strtree2(List<String> strlist,HttpServletRequest req, HttpSession session) {
		    	    List<EasyUITree> eList = new ArrayList<EasyUITree>();
		    	    if(strlist.size() != 0){
		    	    	EasyUITree e = new EasyUITree();
	    	            e.setId("全部");
	    	            e.setText("全部");
	    	            List<EasyUITree> eList2 = new ArrayList<EasyUITree>();
		    	    	for (int i = 0; i < strlist.size(); i++) {
		    		            EasyUITree e1 = new EasyUITree();
		    		            e1.setId(strlist.get(i));
		    		            e1.setText(strlist.get(i));
		    		            e1.setState("open");
		    		            eList2.add(e1);
		    	            }
		    	            e.setChildren(eList2);
		    	            e.setState("closed");
		    	            eList.add(e);
		    	    }
		    	    return Json.toJson(eList);
		    	}	    	
		    	  /**
		     	 * 随机指定范围内N个不重复的数
		     	 * 在初始化的无重复待选数组中随机产生一个数放入结果中，
		     	 * 将待选数组被随机到的数，用待选数组(len-1)下标对应的数替换
		     	 * 然后从len-2里随机产生下一个随机数，如此类推
		     	 * @param max  指定范围最大值
		     	 * @param min  指定范围最小值
		     	 * @param n  随机数个数
		     	 * @return int[] 随机数结果集
		     	 */
		     	public static String randomArray(int min,int max,int n){
		     		int len = max-min+1;
		     		
		     		if(max < min || n > len){
		     			return null;
		     		}
		     		
		     		//初始化给定范围的待选数组
		     		int[] source = new int[len];
		             for (int i = min; i < min+len; i++){
		             	source[i-min] = i;
		             }
		             
		             int[] result = new int[n];
		             Random rd = new Random();
		             int index = 0;
		             for (int i = 0; i < result.length; i++) {
		             	//待选数组0到(len-2)随机一个下标
		                 index = Math.abs(rd.nextInt() % len--);
		                 //将随机到的数放入结果集
		                 result[i] = source[index];
		                 //将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
		                 source[index] = source[len];
		             }
		             String randidstr = "";
		        		for(int i=0;i<n-1;i++)randidstr+=result[i]+",";
		        		randidstr+=result[n-1];
		        		return randidstr;
		             //return result;
		     	}
		     	 /**
		     		 * 随机指定范围内N个不重复的数
		     		 * 利用HashSet的特征，只能存放不同的值
		     		 * @param min 指定范围最小值
		     		 * @param max 指定范围最大值
		     		 * @param n 随机数个数
		     		 * @param HashSet<Integer> set 随机数结果集
		     		 */
		        public static void randomSet(int min, int max, int n, HashSet<Integer> set) {
		            if (n > (max - min + 1) || max < min) {
		                return;
		            }
		            for (int i = 0; i < n; i++) {
		                // 调用Math.random()方法
		                int num = (int) (Math.random() * (max - min)) + min;
		                set.add(num);// 将不同的数存入HashSet中
		            }
		            int setSize = set.size();
		            // 如果存入的数小于指定生成的个数，则调用递归再生成剩余个数的随机数，如此循环，直到达到指定大小
		            if (setSize < n) {
		            	randomSet(min, max, n - setSize, set);// 递归
		            }
		        }
		        /**
		       	 * 随机指定范围内N个不重复的数
		       	 * 最简单最基本的方法
		       	 * @param min 指定范围最小值
		       	 * @param max 指定范围最大值
		       	 * @param n 随机数个数
		       	 */
		       	public static int[] randomCommon(int min, int max, int n){
		       		if (n > (max - min + 1) || max < min) {
		                   return null;
		               }
		       		int[] result = new int[n];
		       		int count = 0;
		       		while(count < n) {
		       			int num = (int) (Math.random() * (max - min)) + min;
		       			boolean flag = true;
		       			for (int j = 0; j < n; j++) {
		       				if(num == result[j]){
		       					flag = false;
		       					break;
		       				}
		       			}
		       			if(flag){
		       				result[count] = num;
		       				count++;
		       			}
		       		}
		       		return result;
		       	}
		       	public static String getRandId2(int min, int max, int n){
		       		if (n > (max - min + 1) || max < min) {
		                   return null;
		               }
		       		int[] result = new int[n];
		       		int count = 0;
		       		while(count < n) {
		       			int num = (int) (Math.random() * (max - min)) + min;
		       			boolean flag = true;
		       			for (int j = 0; j < n; j++) {
		       				if(num == result[j]){
		       					flag = false;
		       					break;
		       				}
		       			}
		       			if(flag){
		       				result[count] = num;
		       				count++;
		       			}
		       		}
		       		String randidstr = "";
		       		for(int i=0;i<n-1;i++)randidstr+=result[i]+",";
		       		randidstr+=result[n-1];
		       		return randidstr;
		       	}
		        public static String[] getRandId(int minV, int maxV, int N )//在M（minV~maxV）中抽N个不重复数,N>M时有重复
		        {
		            String randlrw="";
		            int M = maxV - minV + 1;
		            if(M==0){
		            	for(int i=0;i<N;i++)randlrw+="0,";
		            }else{
		    	        while (M < N)//题少人多
		    	        {
		    	            randlrw += randomArray(minV, maxV, M)+",";
		    	            N -= M;
		    	        }
		    	        randlrw +=randomArray(minV, maxV, N );
		            }
		            return randlrw.split(",");
		        }
		        public static String getRandId3(String numlist, int N )//在M（minV~maxV）中抽N个不重复数,N>M时有重复
		        {
		        	String noslist[]=numlist.split(",");//待抽题题号列表
		        	int maxV=noslist.length;//
		        	int minV=1;
		        	String randlrw="";
		            int M = maxV - minV + 1;
		            if(M==0){
		            	for(int i=0;i<N;i++)randlrw+="0,";
		            	return randlrw;
		            }else{
		    	        while (M < N)//题少人多
		    	        {
		    	            randlrw += randomArray(minV, maxV, M)+",";
		    	            N -= M;
		    	        }
		    	        randlrw +=randomArray(minV, maxV, N );
		            }
		            String ctno[]=randlrw.split(",");
		            String randlrw2="";
		            for(int j=0;j<N-1;j++) randlrw2+=noslist[Integer.parseInt(ctno[j])-1]+",";
		            randlrw2+=noslist[Integer.parseInt(ctno[N-1])-1];
		            return randlrw2;
		        }		    
}
