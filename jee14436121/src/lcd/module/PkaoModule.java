package lcd.module;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lcd.Pojo.Contest;
import lcd.Pojo.Scores;
import lcd.Pojo.Tik;
import lcd.Pojo.User;
import lcd.Pojo.Userpk;
import lcd.qt.BaseSrv;
import lcd.qt.DateUtil;
import lcd.qt.LoginFilter;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;



@IocBean
@At("/pkao")
@Filters({@By(type=LoginFilter.class)})
public class PkaoModule extends BaseSrv{
	@Inject
    protected Dao dao;
	
	@At
    @Ok("beetl:web/pkaoadd.html")
    public void goAdd(HttpSession session,HttpServletRequest req){
    }
	
	 @At
     @Ok("raw")
     public String saveAdd(@Param("..")Contest test,@Param("ctnarr")String ctnArr,@Param("kbarr")String kbArr,
                 HttpSession session,HttpServletRequest req){
           try{
                 String ksid=DateUtil.getLrwTID();
                 test.setId(ksid);
                 //为指定考生、指定科目...随机抽题
                 String bjs=test.getBjs();
                 String[] bjss=bjs.split(",");
                 List<User> users;//考生
                 if(bjss[0].equals("全部"))
                       users=dao.query(User.class, Cnd.where("role", "=", "5"));
                 else{
                       bjs="";int i=0;
                       for(;i<bjss.length-1;i++)
                              bjs+="'"+bjss[i]+"',";
                       bjs+="'"+bjss[i]+"'";
                       users=dao.query(User.class, Cnd.where("dpt", "in", bjs).and("role", "=", "5"));
                 }
                 int T1=users.size();//考生人数
                 String km=test.getKmcode();//科目
                 String[] ctnArrs=ctnArr.split(",");//各知识块抽题数量
                 String[] kbArrs=kbArr.split(",");//各知识块编码
                 int kbnum=kbArrs.length;
                 String[] tiids=new String[kbnum];//各知识块的题号序列
                 for(int i=0;i<kbnum;i++){
                       List<Tik> tiks=dao.query(Tik.class, Cnd.where("kbcode", "=", kbArrs[i]));
                       tiids[i]="";
                       for(int j=0;j<tiks.size();j++) {
                              tiids[i]+=tiks.get(j).getId()+",";
                       }
                 }
                 List<Userpk> userpks=new ArrayList<Userpk>();
                 for(int i=0;i<T1;i++){//逐个考生抽题
                       User user=users.get(i);
                       Userpk userpk=new Userpk();
                       userpk.setKsid(ksid);
                       userpk.setUid(user.getUid());
                       String ckid="";
                       for(int j=0;j<kbnum;j++){//逐知识块抽题]
                    	   int ticount=Integer.parseInt(ctnArrs[j]);
                    	   if(ticount<=0){continue;}
                              if(j>0) ckid+=",";
                              
                              ckid+=getRandId3(tiids[j], ticount);//随机抽题的题号列表，逗号分隔,末尾无逗号
                       }
                       userpk.setKemu(km);
                       userpk.setChkid(ckid);
                       userpk.setStarttime(test.getStarttime());
                       userpk.setEndtime(test.getEndtime());
                       userpks.add(userpk);
                 }
                 ////////////////////////////////////////
                 test.setUserpks(userpks);
                 dao.insertWith(test, "userpks");//关联插入主从表排考信息
           }catch(Exception e){
                 e.printStackTrace();
                 return "添加失败";
           }          
           return "true";
     }
	 
	 @At
	 @Ok("beetl:web/pkaoedit.html")
	 public void goEdit(@Param("id")int id,HttpSession session ,HttpServletRequest request){
		 Userpk userpk=dao.fetch(Userpk.class,id);
		 request.setAttribute("userpk", userpk);
	 }
	 
	 @At
	 @Ok("raw")
	 public String saveEdit(@Param ("..")Contest test,HttpSession session,HttpServletRequest request){
		 if(dao.updateIgnoreNull(test)==1)return "true";
		 else {
			return "修改失败";
		}
		 
	 }
	 
	 
	 @At
     @Ok("raw")
     public String doDel1(@Param("id")String id,HttpSession session,HttpServletRequest req){
           Contest contest=dao.fetch(Contest.class, id);
           List<Userpk> userpks=dao.query(Userpk.class, Cnd.where("ksid", "=", id));
           contest.setUserpks(userpks);
           if(dao.deleteWith(contest,"userpks")>0)return "true";//主从表关联删除
           return "false";
     }
	 @At
     @Ok("raw")//
     public String getBjs(HttpServletRequest req, HttpSession session){
           List<String> array=new ArrayList<String>();
           Sql sql = Sqls.create("select distinct dpt from user where role='5'");
           sql.setCallback(new SqlCallback() {
             public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
                  List<String> array0=new ArrayList<String>();
                 while (rs.next()){
                        array0.add(rs.getString("dpt"));
                 }
                 return array0;
             }
         });
         dao.execute(sql);
         array=sql.getList(String.class);
         return strtree2(array,req,session);
     }
	 
	 
	 @At
     @Ok("raw")//
     public String getTiCnt(@Param("kmcode")String kmcode,HttpServletRequest req, HttpSession session){
           List<Object> array=new ArrayList<Object>();
           Sql sql = Sqls.create("select kbcode,count(*) as cnt from tik where kmcode='"+kmcode+"' group by kbcode");
           sql.setCallback(new SqlCallback() {
             public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
                  List<Object> array0=new ArrayList<Object>();
                  Map<String, Object> jsonobj;
                 while (rs.next()){
                 jsonobj=new HashMap<String, Object>();
                        jsonobj.put("kbcode", rs.getString("kbcode"));
                        jsonobj.put("cnt", rs.getInt("cnt"));
                        array0.add(jsonobj);
                 }
                 return array0;
             }
         });
         dao.execute(sql);
         array=sql.getList(Object.class);
         return Json.toJson(array);
     }
	 
	 
	 @At
     @Ok("beetl:web/pkaolist.html")//
     public void goList(HttpSession session,HttpServletRequest req){
     }
	 
   @At
   @Ok("raw")//分页查询指定考试或所有考试（后台）
   public String listContest(@Param("page") int curPage, @Param("rows") int pageSize,
                      @Param("s_name") String s_name, HttpSession session) {
       Criteria cri = Cnd.cri(); 
       if (!Strings.isBlank(s_name)) {
           cri.where().andLike("id", s_name).orLike("title", s_name);
       }
       else cri.where().andEquals("1", 1);
       cri.getOrderBy().desc("id");
       return listPageJson(dao, Contest.class, curPage,pageSize, cri);
   }
   
   
   @At
   @Ok("raw")//
   public int getCount(HttpSession session,HttpServletRequest req){
         User user=(User)session.getAttribute("me");
     Criteria cri = Cnd.cri(); 
     cri.where().andLike("bjs", user.getDpt());
         return dao.count(Contest.class,cri);
   }
 @At
 @Ok("raw")//分页查询考试（登录考生的）

 public String listMyContest(@Param("page") int curPage, @Param("rows") int pageSize,
                    HttpSession session) {
   User user=(User)session.getAttribute("me");
     Criteria cri = Cnd.cri(); 
     cri.where().andLike("bjs", user.getDpt());
     cri.getOrderBy().desc("starttime");
     return listPageJson(dao, Contest.class, curPage,pageSize, cri);
}  
 
 @At
 @Ok("beetl:web/pkaoread.html")//读取指定id的考试信息或成绩信息
 public void getPkao(@Param("id")String id,HttpSession session,HttpServletRequest req){
         Contest pkao = dao.fetch(Contest.class, id);
         Date date=new Date();
         User user=(User)session.getAttribute("me");
         Scores scores=dao.fetch(Scores.class, Cnd.where("ksid", "=", id).and("uid", "=", user.getUid()));
         if(pkao.getEndtime().getTime()<date.getTime() || !(scores==null)){//考试结束(有成绩或超出了考试时间范围)了，只能查看成绩
                if(scores==null) req.setAttribute("ksid", id);//缺考
                else req.setAttribute("scores", scores);//有成绩
                req.setAttribute("ksing", 2);
         }else{
                if(pkao.getStarttime().getTime()<date.getTime()){//考试正在进行中...
                      req.setAttribute("ksid", id);
                      req.setAttribute("ksing", 1);
                }else{//考试未开始...
                      req.setAttribute("ksing", 0);
                }
                req.setAttribute("pkao", pkao);
         }
   }   
 
 
 
	 
}
