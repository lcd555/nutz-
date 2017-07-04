package lcd.module;









import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import lcd.Pojo.Loginlog;
import lcd.Pojo.User;
import lcd.qt.BaseSrv;
import lcd.qt.LoginFilter;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/user")
@Filters({@By(type=LoginFilter.class)})
public class UserModule extends BaseSrv {   //实现对用户信息的增删改查
      @Inject
      protected Dao dao;
      //查--登录
      @At
      @Ok("raw")
      @Filters()
      public String doLogin(@Param("uid")String uid, @Param("pwd") String pwd,
                  HttpSession session,HttpServletRequest req){

          
        User user = dao.fetch(User.class,uid);
       
        if (user==null){ return "考号不存在！";}
        String p =pwd;
        if (!p.equals(user.getPassword())) {return "密码不正确";
        }   ////登录成功后...记录登录日志
        String rString=user.getRole();
        session.setAttribute("me", user);
        
     if(rString.equals("1"))
          	
        { return "1";}
        return "5";
        
       //Loginlog log1 = new Loginlog();
       
        //log1.setUid(user.getUid());
        //log1.setLoginip(getIpAddr(req));
        //log1.setLogintime(new Date());
        
        
        //dao.insert(log1);
     
       
        
        
       
        
      
        
      }
      @At
      @Ok("beetl:web/index.html")//考生登录成功，跳转到考试信息页面
      public void goIndex(HttpSession session,HttpServletRequest req){
            User loginuser=(User)session.getAttribute("me");
        req.setAttribute("user", loginuser);
      }
      @At
      @Ok("beetl:web/admin.html")
      public void goAdmin(HttpSession session,HttpServletRequest req){
          User loginuser=(User)session.getAttribute("me");
          req.setAttribute("user", loginuser);
          }
      @At
      @Ok(">>:/")//登出系统
      public void doLogout(HttpSession session) {
          session.invalidate();
      }
      
      
      
     // 用户添加

      @At
      @Ok("beetl:web/userAdd.html")
      public void goAdd(HttpSession session,HttpServletRequest req){
      }
      @At
      @Ok("raw")
      public String saveAdd(@Param("..")User user,HttpSession session,HttpServletRequest req){
            try{
                  String pwd=user.getPassword();
                 
                  user.setPassword(pwd);
                  dao.insert(user);
            }catch(Exception e){
                  e.printStackTrace();
                  return "添加失败";
            }          
            return "true";
      }
//用户删除

      @At
      @Ok("raw")//删除指定uid的单个用户
      public String doDel1(@Param("uid")String uid,HttpSession session,HttpServletRequest req){
            User user=(User)(session.getAttribute("me"));
            if(user.getUid().equals(uid))return "false";
            if(dao.delete(User.class,uid)>0)return "true";
            return "false";
      }
      @At
      @Ok("raw")//删除指定uid的多个用户
      public String doDelN(@Param("uids")String uids,HttpSession session,HttpServletRequest req){
            //User user=(User)(session.getAttribute("me"));
            String[] s=uids.split(",");
            if(dao.clear(User.class, Cnd.where("uid", "in", s))>0)return "true";
            return "false";
      }
//用户修改

      @At
      @Ok("beetl:web/useredit.html")
      public void goEdit(@Param("uid")String uid,HttpSession session,HttpServletRequest req){
                  //Cnd cnd=Cnd.where("uid","=", uid);
                  User user=dao.fetch(User.class,uid);
                  req.setAttribute("user", user);
      }
      @At
      @Ok("raw")
      public String saveEdit(@Param("..")User user,HttpSession session,HttpServletRequest req){
            try{
                  String pwd=user.getPassword();
                  if(pwd.length()>0){//用户输入了新密码
                        
                        user.setPassword(pwd);
                  }else user.setPassword(null);//保留原密码
                  if(dao.updateIgnoreNull(user)==1)return "true";
                  else return "修改失败";
            }catch(Exception e){
                  e.printStackTrace();
                  return  "修改失败";
            }
      }
//查
      @At
      @Ok("beetl:web/userlist.html")//跳转到用户信息列表
      public void goList(HttpSession session,HttpServletRequest req){
      }
      @At
      @Ok("raw")//分页查询指定用户或所有用户
      public String listUser(@Param("page") int curPage, @Param("rows") int pageSize,
                         @Param("s_name") String s_name, HttpSession session) {
          Criteria cri = Cnd.cri(); 
          if (!Strings.isBlank(s_name)) {
              cri.where().andLike("uid", s_name).orLike("uname", s_name);
          }
          else cri.where().andEquals("1", 1);
          cri.getOrderBy().asc("uid");
          return listPageJson(dao, User.class, curPage,pageSize, cri);
      }
      
      
      ///查看登录日志
      @At
      @Ok("beetl:web/loglist.html")
      public void goLog() {         
      }
      @At
      @Ok("raw")//分页查询所有登录日志
      public String listLog(@Param("page") int curPage,@Param("rows") int pageSize) {
            Cnd cnd=(Cnd) Cnd.where("1","=","1").desc("logintime");
            return listPageJson(dao, Loginlog.class, curPage, pageSize, cnd);
      }
      //ip
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
      
      

      
}



