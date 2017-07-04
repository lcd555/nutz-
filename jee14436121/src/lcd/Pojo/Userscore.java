package lcd.Pojo;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.View;

import java.io.Serializable;
import java.util.Date;

/**
 * VIEW
 * @author otom3
 * @date 2017-05-03
 */
 @View("userscore")
public class Userscore  {
	/**
	 * VIEW
	 */
	 @Name
	private java.lang.String dpt;//dpt
	 @Column
	private java.lang.String uid;//uid
	 @Column
	private java.lang.String uname;//uname
	 @Column
	private java.lang.Float score;//score
	 @Column
	private java.util.Date jjtime;//jjtime
	 @Column
	private java.lang.String ksid;//ksid
	 @Column
	private java.lang.String paper;//paper
	public java.lang.String getDpt(){
		return this.dpt;
	}
	public void setDpt(java.lang.String dpt){
		this.dpt	= dpt;
	}
	public java.lang.String getUid(){
		return this.uid;
	}
	public void setUid(java.lang.String uid){
		this.uid	= uid;
	}
	public java.lang.String getUname(){
		return this.uname;
	}
	public void setUname(java.lang.String uname){
		this.uname	= uname;
	}
	public java.lang.Float getScore(){
		return this.score;
	}
	public void setScore(java.lang.Float score){
		this.score	= score;
	}
	public Date getJjtime(){
		return this.jjtime;
	}
	public void setJjtime(Date jjtime){
		this.jjtime	= jjtime;
	}
	public java.lang.String getKsid(){
		return this.ksid;
	}
	public void setKsid(java.lang.String ksid){
		this.ksid	= ksid;
	}
	public java.lang.String getPaper(){
		return this.paper;
	}
	public void setPaper(java.lang.String paper){
		this.paper	= paper;
	}
}