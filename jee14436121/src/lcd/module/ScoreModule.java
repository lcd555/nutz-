package lcd.module;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lcd.Pojo.Contest;
import lcd.Pojo.Scores;
import lcd.Pojo.Tik;
import lcd.Pojo.User;
import lcd.Pojo.Userpk;
import lcd.Pojo.Userscore;
import lcd.qt.BaseSrv;
import lcd.qt.DateUtil;
import lcd.qt.FileSrv;
import lcd.qt.LoginFilter;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.alibaba.druid.sql.ast.SQLExpr;

@IocBean
@At("/score")
@Filters({@By(type=LoginFilter.class)})
public class ScoreModule extends BaseSrv{
	@Inject
    protected Dao dao;
	SqlExpressionGroup e1;
	@At
    @Ok("beetl:web/examing.html")
    public void goExam(@Param("ksid") String ksid,HttpSession session,HttpServletRequest req){
        User user = (User) session.getAttribute("me");
        Contest test=dao.fetch(Contest.class, ksid);
        int pc=test.getPc();
        long d0=(new Date()).getTime();
        long d1=test.getStarttime().getTime();
        long d2=test.getEndtime().getTime();
        if(d0>d2){
            req.setAttribute("outdate",true);
        }
        else{
        Userpk userpk=dao.fetch(Userpk.class, Cnd.where("ksid","=",ksid).and("uid","=",user.getUid()));
        req.setAttribute("mypkao",userpk);
        if((d2-d1)/1000/60>pc)req.setAttribute("atime",pc);//考试时间范围大于考试时长，在时间范围登录即可，保证一样的考试时长
        else req.setAttribute("atime",(d2-d0)/1000);//考试时间范围==考试时长，在时间范围登录，保证相同的结束时刻。考生迟到登录，真正用于考试的时间少于规定的考试时长
        req.setAttribute("outdate",false);
        }
    }
	
	
	   @At
	      @Ok("raw")
	      public String saveScores(@Param("ids") String ids,@Param("s2") String uda,@Param("ksid") String ksid,HttpSession session) {
	            User u=(User) session.getAttribute("me");
	            String uid=u.getUid();
	            String xm=u.getUname();
	            String bj=u.getDpt();
	            String filename=ksid+"_"+uid;       
	            Contest test=dao.fetch(Contest.class, ksid);
	            //此处应扩展获取单位名称uname，如湖南人文科技学院；获取真正的考试科目名称course，如C语言程序设计，用于显示到网页上
	            String uname="湖南人文科技学院";
	            String course="C语言程序设计";
	            String km=test.getKmcode();         
	            //html头
	            
	            String p00="<html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8' /><title>我的"+course+"试卷</title>";
	            p00+="<style>";
	            p00+="body{font-size:12.0pt;font-family:SimSun;color:black;}";
	            p00+="span.st1{font-size:14.0pt;font-family:SimSun;color:black;}";
	            p00+="span.tscore{font-size:12.0pt;font-family:SimSun;color:red;}";
	            p00+="span.tscore2{font-size:12.0pt;font-family:SimSun;color:blue;}";
	            p00+="span.tscore3{font-size:60.0pt;font-family:Helvetica;font-style: italic;color:red;}";
	            p00+="</style></head>";
	            //禁用了右键及复制
	            p00+="<body onselectstart = 'return false' oncopy='return false'  oncontextmenu='self.event.returnValue=false'>";
	          //试卷头:单位名、课程名、考试名称、考试时间范围、部门、姓名、考号、成绩
	            String p0="";
	            p0 += "<div>";       
	          p0 += "<p><b><span class='st1'>"+uname+"_" + course + "_无纸化考试试卷</span></b></p>";
	          p0+="<table width='800' border='0' cellspacing='0' cellpadding='0'  style='border-bottom: black;border-bottom-width: thick;border-bottom-style: double;'>  <tr>";
	          p0 += "<td width='500' height='41'><b><span class='st1'>&nbsp;&nbsp;————";
	          p0 +=test.getTitle();
	          p0 +="</span></b></td>";
	          p0+="<td width='200' rowspan='3'  align='left' valign='bottom'>";         
	          String p01 = "</td></tr><tr><td>&nbsp;&nbsp;";
	          p01+=DateUtil.date2str(test.getStarttime())+" ~ "+DateUtil.date2str(test.getEndtime())+"</td></tr>";
	          p01 += "<tr><td height='38'><b><span>班级： "+bj+"&nbsp;&nbsp;姓名："+xm+"&nbsp;&nbsp; 考号：" + uid + "&nbsp;&nbsp;成绩：</span></b></td>";
	          p01+="</tr></table>";         
	          //分数列表p1+p2
	          String p1= "<p></p>";//</p><p><span class='line'>======================================================================================================</span></p>";
	          p1 += "<table border='1' cellspacing='0'>";
	          p1 += "<tr><th width='72' scope='col'><span class='tscore2'>题号</span></th>";
	          String p2="<tr><td><span class='ti'>得分</span></td>";
	            //试题及答案
	            Scores score=new Scores();
	            score.setDonum(1);
	            score.setKsid(ksid);
	            score.setUid(uid);
	            score.setJjtime(new Date()); 
	            String paper="",p1_1="",p2_1=""; 
	            Cnd cnd=(Cnd) Cnd.where("kmcode","=",km).and("id","in", ids).asc("instr(',"+ids+",',concat(',',id,','))");
	        List<Tik> list = dao.query(Tik.class, cnd);
	        int chkNum=list.size();
	        String[] u_da=uda.split(",",chkNum);//考生答案,分解出一样数量的考生答案。避免最后连续为空时，没有空答案。
	        String ua,ca,tx="";int re=5;
	        Tik ti1;
	        float fv=0,tscore=0,tiscore=0,tifv=0;
	        int k=0;
	        String tih=bigtino[k];
	            ////////////////核对答案，计算成绩，生成paper主体内容////////////////////////////////////////
	            for (int i = 0; i < chkNum; i++) {
	                  ua = u_da[i];//第i个题的考生答案
	                  ti1=list.get(i);//第i个题
	                  ca =ti1.getQans();//第i个题的参考答案
	                  fv = ti1.getFenvalue();//第i个题的分值                 
	                if(!ti1.getQtcode().equals(tx)){//换题型了，生成大题:题号、题型、大题分值、各小题(题干、选项)、考生答案、参考答案)
	                        if(i>=1){
	                               p2_1+="<div><p><b><span>"+tih +"、"+ questype[Integer.parseInt(tx)]+"("+tifv+"分)</span></b></p>"+p1_1+"</div>";
	                               p1+= "<th width='70' scope='col'><span class='tscore2'>"+tih+"</span></th>";
	                               p2+="<td>" + tiscore + "</td>";
	                               switch(k+1)
	                               {
	                                     case 1:score.setScore1(tiscore);break;
	                                     case 2:score.setScore2(tiscore);break;
	                                     case 3:score.setScore3(tiscore);break;
	                                     case 4:score.setScore4(tiscore);break;
	                                     case 5:score.setScore5(tiscore);break;
	                                     case 6:score.setScore6(tiscore);break;
	                                     case 7:score.setScore7(tiscore);break;
	                               }
	                               tifv=0;p1_1="";tiscore=0;
	                               k++;tih=bigtino[k];
	                        }
	                        tx=ti1.getQtcode();
	                  }
	                  tifv +=fv;//累计题目分值
	                  if (ca.equals(ua)) {//正确答案
	                        re = 1;//正确状态                   
	                        tscore+=fv;//累计总得分
	                        tiscore+=fv;//每题累计得分
	                  } else {
	                        re = 5;
	                  }                 
	                  p1_1 += "<p><span>" + (i+1) + "、" + ti1.getQname() + "</span></p>";
	                  p1_1 += "<p><span>A) " + ti1.getAnsa() + "</span></p>";
	                  p1_1 += "<p><span>B) " + ti1.getAnsb() + "</span></p>";
	                  //如果是判断题，则只有2个选项。所以要判断是否有其它选项。
	                  if(!(ti1.getAnsc()).trim().equals(""))
	                  p1_1 += "<p><span>C) " + ti1.getAnsc() + "</span></p>";
	                  if(!(ti1.getAnsd()).trim().equals(""))
	                  p1_1 += "<p><span>D) " + ti1.getAnsd() + "</span></p>";
	                  p1_1 += "<p><b><span class=tscore2>考生答案：" + ua + "</span></b></p>";
	                  p1_1 += "<p><b><span class=tscore>参考答案：" + ca + "</span></b></p>";
	            }
	            //最后一道大题
	            p2_1+="<div><p><b><span>"+tih +"、"+ questype[Integer.parseInt(tx)]+"("+tifv+"分)</span></b></p>"+p1_1+"</div>";
	            p1+= "<th width='70' scope='col'><span class='tscore2'>"+tih+"</span></th>";
	            p2+="<td>" + tiscore + "</td>";
	            switch(k+1)
	            {
	                  case 1:score.setScore1(tiscore);break;
	                  case 2:score.setScore2(tiscore);break;
	                  case 3:score.setScore3(tiscore);break;
	                  case 4:score.setScore4(tiscore);break;
	                  case 5:score.setScore5(tiscore);break;
	                  case 6:score.setScore6(tiscore);break;
	                  case 7:score.setScore7(tiscore);break;
	            }
	            tifv=0;p1_1="";tiscore=0;
	          p1 += "</tr>";
	            p2 += "</tr></table></div>";
	            /////成绩计算////////////////////////////////////////////////////////////////////////
	            String p3="<span class='tscore3'>";//醒目的总分
	            int cj2=Math.round(tscore*10);
	            int d=cj2%10;//小数位
	            cj2=cj2/10;
	            int a= cj2%10;//个位
	            int b=cj2/10%10;//十位
	            int c=cj2/100;//百位          
	            if(c>0)p3+=c;//百位
	            if(b>0 || c>0)p3+=b;//十位
	            p3+=a;//个位
	            if(d>0)p3+="."+d;//小数位
	            p3+="</span>";
	            String p11="</body></html>";
	            ///////保存试卷//////////////////////////////////////        
	            Map<String, Object> jsonroot = new HashMap<String, Object>();
	            String fname="upload/jjfile/"+filename+".html";
	            String dest = Mvcs.getServletContext().getRealPath("/"+fname);
	        try {          
	            FileOutputStream fos = new FileOutputStream(dest);
	            Writer out = new OutputStreamWriter(fos, "UTF-8");
	            out.write((p00+p0+p3+p01+p1+p2+p2_1+p11)); 
	            out.close();
	            fos.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	            jsonroot.put("msg", "0");
	        }
	  ///////存储成绩、修改考生完成考试的标识//////////////////////////////////////
	        score.setScore(tscore);
	        score.setPaper(fname);//试卷链接
	        //交卷后修改用户信息         
	        try{
	            dao.insert(score) ;
	            jsonroot.put("tscore", tscore);
	            jsonroot.put("msg", "1");
	            session.removeAttribute("me");//交卷成功后，前端跳转到登录页
	        }
	            catch(Exception e) {
	            e.printStackTrace();
	                  jsonroot.put("tscore", 0);
	                  jsonroot.put("msg", "0");
	            }       
	        return Json.toJson(jsonroot);
	      }
	   
	   @At
	      @Ok("raw")
	      public String doDel1(@Param("id")int id,HttpSession session,HttpServletRequest req){
	            if(dao.delete(Scores.class,id)>0)return "true";
	            return "false";
	      }
	      @At
	      @Ok("raw")
	      public String doDelN(@Param("ids")String ids,HttpSession session,HttpServletRequest req){
	            String[] s=ids.split(",");
	            if(dao.clear(Scores.class, Cnd.where("id", "in", s))>0)return "true";
	            return "false";
	      }
	      
	      
	      @At
	      @Ok("raw")
	      public String listChk(@Param("ids") String ids,@Param("km") String km){       
	            if (Strings.isBlank(ids))return "";
	            //MySQL使用 IN 查询取出数据排序问题:默认主键顺序；使用instr,使其保持抽题顺序。
	            Cnd cnd=(Cnd) Cnd.where("kmcode","=",km).and("id","in", ids).asc("instr(',"+ids+",',concat(',',id,','))");
	        List<Tik> list = dao.query(Tik.class, cnd);
	        for(int i=0;i<list.size();i++){
	            list.get(i).setQans("");//参考答案不能传输到前端
	        }
	        Map<String, Object> jsonobj = new HashMap<String, Object>();
	        jsonobj.put("total", dao.count(Tik.class, cnd));
	        jsonobj.put("rows", list);
	        return Json.toJson(jsonobj);
	      }
	
	
	      @At
	      @Ok("beetl:web/scorelist.html")
	      public void goList(@Param("page") int curPage, @Param("rows") int pageSize,HttpSession session,HttpServletRequest req){
	            Criteria cri = Cnd.cri(); 
	        cri.where().andEquals("1", 1);
	        cri.getOrderBy().desc("id");
	            String scorelist=listPageJson(dao, Scores.class, curPage,pageSize, cri);
	            req.setAttribute("scorelist", scorelist);
	            req.setAttribute("curPage", curPage);
	      }
	      
	      @At
	      @Ok("")
	      public void score2xls(HttpServletResponse response,HttpServletRequest req,
	              @Param("ksid") String ksid,@Param("bjs") String bj     ) throws UnsupportedEncodingException{
	              Cnd cnd;
	              bj=new String(bj.getBytes("ISO-8859-1"),"UTF-8");
	              if(bj.equals("全部")) cnd=Cnd.where("ksid","=",ksid).or("ksid","is",null);//查看指定考试号的成绩单
	              else
	            	  e1=Cnd.exps("ksid","=",ksid).or("ksid","is",null);
	            	  cnd=Cnd.where(e1).and("dpt","like", bj);//查看指定考试号+班级的成绩单
	        List<Userscore> scorelist= dao.query(Userscore.class, cnd);
	        try{
	              FileSrv.score2Xls(response, ksid+"_"+bj+"_成绩单", scorelist);
	              }catch(Exception e1){}
	        }
	      @At
	      @Ok("raw")
	       public String listScores(HttpServletRequest req, HttpSession session,
	                    @Param("ksid") String ksid,@Param("bjs") String bj,
	                    @Param("page") int curPage, @Param("rows") int pageSize) {
	              Cnd cnd=null;
	              
				if(bj.equals("全部")){ cnd=Cnd.where("ksid","=",ksid).or("ksid","is",null);//查看指定考试号的成绩单
				}else 
	            	  {e1=Cnd.exps("ksid","=",ksid).or("ksid","is",null);
	            	  cnd=Cnd.where(e1).and("dpt","like", bj);//查看指定考试号+班级的成绩单
	            	  }
	              return listPageJson(dao,Userscore.class, curPage, pageSize, cnd);
	        }
	      @At
	      @Ok("raw")
	      public String getPkaoCbb(HttpServletRequest req, HttpSession session){
	             List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	            List<Contest> tests=dao.query(Contest.class, null);
	            for(int i=0;i<tests.size();i++){
	                  Contest test=tests.get(i);
	                  Map<String, String> jsonobj = new HashMap<String, String>();
	                  jsonobj.put("id", test.getId());
	                  jsonobj.put("text", test.getTitle());
	                  jsonobj.put("desc", test.getBjs());
	                  list.add(jsonobj);
	            }       
	          return Json.toJson(list);
	      }
	      
	
}
