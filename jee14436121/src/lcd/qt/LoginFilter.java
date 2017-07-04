package lcd.qt;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import lcd.Pojo.User;

import org.apache.catalina.connector.Request;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.view.RawView;
import org.nutz.mvc.view.ServerRedirectView;





public class LoginFilter implements ActionFilter {
    private final Log log= (Log) Logs.get();
 
      public org.nutz.mvc.View match(ActionContext context) {
    	  
            HttpServletRequest request=context.getRequest();
            try {
				request.setCharacterEncoding("utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            User user = (User) request.getSession().getAttribute("me");
            String contentType=request.getContentType();
      if(Strings.sNull(contentType).contains("application/x-www-form-urlencoded")&&user==null){
            context.getResponse().setHeader("sessionstatus","timeout");
            return (org.nutz.mvc.View) new RawView("");
      }else if (user == null) {
                  ServerRedirectView view = new ServerRedirectView("/error/nologin.jsp");
                  return (org.nutz.mvc.View) view;
            }
            request.setAttribute("me", user);
            String basePath=request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()
                        + Mvcs.getServletContext().getContextPath() + "/";
            request.setAttribute("basePath",basePath );
            return null;
      }

	
}
