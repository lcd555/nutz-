package lcd.Pojo;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
import java.util.Date;

/**
 * loginlog
 * @author otom3
 * @date 2017-03-29
 */
 @Table("loginlog")
public class Loginlog  {
	/**
	 * loginlog
	 */
	 @Id
	 

	private int id;//id
	 @Name
	private java.lang.String uid;//uid
	 @Column
	private java.lang.String loginip;//loginip
	 @Column
	private java.util.Date logintime;//logintime
	
	public java.lang.Integer getId(){
		return this.id;
	}
	public void setId(java.lang.Integer id){
		this.id	= id;
	}
	public java.lang.String getUid(){
		return this.uid;
	}
	public void setUid(java.lang.String uid){
		this.uid	= uid;
	}
	public java.lang.String getLoginip(){
		return this.loginip;
	}
	public void setLoginip(java.lang.String loginip){
		this.loginip	= loginip;
	}
	public java.util.Date getLogintime(){
		return this.logintime;
	}
	public void setLogintime(Date date){
		this.logintime	=  date;
	}
	
}