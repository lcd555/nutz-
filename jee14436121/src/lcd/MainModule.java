package lcd;

import org.beetl.ext.nutz.BeetlViewMaker;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.annotation.Views;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

@SetupBy(value=MainSetup.class)
@IocBy(type=ComboIocProvider.class, args={"*js", "ioc/","*anno", "lcd","*tx"})
@Modules(scanPackage=true)
@Views({BeetlViewMaker.class})
public class MainModule {
	
	

}
