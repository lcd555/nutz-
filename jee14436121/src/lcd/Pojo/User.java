package lcd.Pojo;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
import java.io.Serializable;

/**
 * user
 * @author otom31
 * @date 2017-03-03
 */
 @Table("user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * user
	 */
	public static final String REF="User";
	/**
	 * uid
	 */
	 @Name
	private java.lang.String uid;
	/**
	 * uname
	 */
	 @Column
	private java.lang.String uname;
	/**
	 * dpt
	 */
	 @Column
	private java.lang.String dpt;
	@Column
	private java.lang.String role;
	 public java.lang.String getRole() {
		return role;
	}
	public void setRole(java.lang.String role) {
		this.role = role;
	}

	@Column
		private java.lang.String password;
	public java.lang.String getPassword() {
		return password;
	}
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	public java.lang.String getUid(){
		return this.uid;
	}
	/**
	 * @param uid :  uid 
	 */
	public void setUid(java.lang.String uid){
		this.uid	= uid;
	}
	/**
	 * @return uname :  uname 
	 */
	public java.lang.String getUname(){
		return this.uname;
	}
	/**
	 * @param uname :  uname 
	 */
	public void setUname(java.lang.String uname){
		this.uname	= uname;
	}
	/**
	 * @return dpt :  dpt 
	 */
	public java.lang.String getDpt(){
		return this.dpt;
	}
	/**
	 * @param dpt :  dpt 
	 */
	public void setDpt(java.lang.String dpt){
		this.dpt	= dpt;
	}
	

}