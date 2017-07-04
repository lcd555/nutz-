package lcd;

import lcd.Pojo.User;

import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

public class MainSetup implements Setup{

	public void destroy(NutConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	public void init(NutConfig nc) {
		 Ioc ioc = nc.getIoc();
	        Dao dao = ioc.get(Dao.class);
	        Daos.createTablesInPackage(dao, "lcd", false);
	// 初始化默认根用户
	        if (dao.count(User.class) == 0) {
	            User user = new User();
	            user.setUid("1234");
	            user.setUname("lcd");
	            user.setPassword("1234");
	            user.setDpt("信息学院");
	            user.setRole("1");
	            
	            user.setUid("admin");
	            user.setUname("lcdAdmin");
	            user.setPassword("admin");
	            user.setDpt("信息学院");
	            user.setRole("2");
	           
	            dao.insert(user);
	            
	           
	        }
		
	}

}
