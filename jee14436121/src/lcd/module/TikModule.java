package lcd.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lcd.Pojo.Tik;
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
@At("/tik")
@Filters({@By(type=LoginFilter.class)})
public class TikModule extends BaseSrv{
	@Inject
    protected Dao dao;
	//增加题目
    @At
    @Ok("beetl:web/tikadd.html")
    public void goAdd(HttpSession session,HttpServletRequest req){
    }
    @At
    @Ok("raw")
    public String saveAdd(@Param("..")Tik tik,HttpSession session,HttpServletRequest req){
          try{

                dao.insert(tik);
          }catch(Exception e){
                e.printStackTrace();
                return "添加失败";
          }          
          return "true";
    }
    //删除指定id的单条题目
    @At
    @Ok("raw")
    public String doDel1(@Param("id")int id,HttpSession session,HttpServletRequest req){
          if(dao.delete(Tik.class,id)>0)return "true";
          return "false";
    }
    @At
    @Ok("raw")//删除指定id的多条题目
    public String doDelN(@Param("ids")String ids,HttpSession session,HttpServletRequest req){
          String[] s=ids.split(",");
          if(dao.clear(Tik.class, Cnd.where("id", "in", s))>0)return "true";
          return "false";
    }
    //改
    @At
    @Ok("beetl:web/tikedit.html")
    public void goEdit(@Param("id")int id,HttpSession session,HttpServletRequest req){
          Tik tik=dao.fetch(Tik.class,id);
          req.setAttribute("tik", tik);
    }
    @At
    @Ok("raw")
    public String saveEdit(@Param("..")Tik tik,HttpSession session,HttpServletRequest req){
          try{

                if(dao.updateIgnoreNull(tik)==1)return "true";
                else return "修改失败";
          }catch(Exception e){
                e.printStackTrace();
                return  "修改失败";
          }
    }
    //查
    @At
    @Ok("raw")//题目数量
    public int getCount(HttpSession session,HttpServletRequest req){
          return dao.count(Tik.class);
    }
    @At
    @Ok("beetl:web/tiklist.html")//跳转到题目信息列表
    public void goList(HttpSession session,HttpServletRequest req){

    }
  @At
  @Ok("raw")//分页查询指定题目或所有题目
  public String listTik(@Param("page") int curPage, @Param("rows") int pageSize,
                     @Param("s_name") String s_name, HttpSession session) {
      Criteria cri = Cnd.cri(); 
      if (!Strings.isBlank(s_name)) {
          cri.where().andLike("qname", s_name);
      }
      else cri.where().andEquals("1", 1);
      //cri.getOrderBy().desc("id");
      return listPageJson(dao, Tik.class, curPage,pageSize, cri);
  }
}
