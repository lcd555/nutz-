package lcd.Pojo;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
import java.io.Serializable;

/**
 * userpk
 * @author otom3
 * @date 2017-04-17
 */
 @Table("userpk")
public class Userpk  {
	/**
	 * userpk
	 */
	 @Id
	private java.lang.Integer id;//id
	 @Column
	private java.lang.String uid;//uid
	 @Column
	private java.lang.String kemu;//kemu
	 @Column
	private java.lang.String chkid;//chkid
	 @Column
	private java.lang.String ksid;//ksid
	 @Column
	private java.lang.String handleid;//handleid
	 @Column
	private java.lang.String kch;//kch
	 @Column
	private java.lang.Integer pc;//pc
	 @Column
	private java.lang.String loginip;//loginip
	 @Column
	private java.sql.Timestamp starttime;//starttime
	 @Column
	private java.sql.Timestamp endtime;//endtime
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
	public java.lang.String getKemu(){
		return this.kemu;
	}
	public void setKemu(java.lang.String kemu){
		this.kemu	= kemu;
	}
	public java.lang.String getChkid(){
		return this.chkid;
	}
	public void setChkid(java.lang.String chkid){
		this.chkid	= chkid;
	}
	public java.lang.String getKsid(){
		return this.ksid;
	}
	public void setKsid(java.lang.String ksid){
		this.ksid	= ksid;
	}
	public java.lang.String getHandleid(){
		return this.handleid;
	}
	public void setHandleid(java.lang.String handleid){
		this.handleid	= handleid;
	}
	public java.lang.String getKch(){
		return this.kch;
	}
	public void setKch(java.lang.String kch){
		this.kch	= kch;
	}
	public java.lang.Integer getPc(){
		return this.pc;
	}
	public void setPc(java.lang.Integer pc){
		this.pc	= pc;
	}
	public java.lang.String getLoginip(){
		return this.loginip;
	}
	public void setLoginip(java.lang.String loginip){
		this.loginip	= loginip;
	}
	public java.sql.Timestamp getStarttime(){
		return this.starttime;
	}
	public void setStarttime(java.sql.Timestamp starttime){
		this.starttime	= starttime;
	}
	public java.sql.Timestamp getEndtime(){
		return this.endtime;
	}
	public void setEndtime(java.sql.Timestamp endtime){
		this.endtime	= endtime;
	}
}