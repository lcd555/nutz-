package lcd.qt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lcd.Pojo.Cmenu;
import lcd.Pojo.EasyUITree;
import lcd.Pojo.User;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;

@IocBean
@At("/")
@Filters({@By(type=LoginFilter.class)})
public class MenuTree   {
	 @Inject
     protected Dao dao;

	
	
	//menu
    @At
    @Ok("raw")
    public String menutree(HttpServletRequest req, HttpSession session) {
      User user=(User) session.getAttribute("me");
      String role=user.getRole();
      //父级菜单
        List<Cmenu> menulist=dao.query(Cmenu.class, Cnd.where("id","like","__").asc("id"));//id=01,02,03
        List<EasyUITree> eList = new ArrayList<EasyUITree>();
        if(menulist.size() != 0){
          for (int i = 0; i < menulist.size(); i++) {
                      Cmenu t = menulist.get(i);
                    if(!t.getPermission().contains(role))continue;//无权限,不显示相应菜单
                EasyUITree e = new EasyUITree();
                e.setId(t.getId());
                e.setText(t.getName());
                List<EasyUITree> eList2 = new ArrayList<EasyUITree>();
                List<Cmenu> menu2 = dao.query(Cmenu.class, Cnd.where("id", "like", t.getId() + "__").asc("id")  );
                for (int j = 0; j < menu2.size(); j++) {//二级菜单
                Cmenu t2 = menu2.get(j);
                if(!t2.getPermission().contains(role))continue;
                Map<String,Object> attributes = new HashMap<String, Object>();
                      attributes.put("url", t2.getUrl());
                      attributes.put("role", t2.getPermission());
                      EasyUITree e1 = new EasyUITree();
                      e1.setAttributes(attributes);
                      e1.setId(t2.getId());
                      e1.setText(t2.getName());
                      e1.setState("open");
                      eList2.add(e1);
                }
                e.setChildren(eList2);
                e.setState("closed");
                eList.add(e);
            }
        }
        return Json.toJson(eList);
    }
}
