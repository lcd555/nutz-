package lcd.Pojo;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import java.io.Serializable;
import java.util.List;

/**
 * contest
 * @author otom3
 * @date 2017-04-17
 */
 @Table("contest")
public class Contest  {
	/**
	 * contest
	 */
	   @Many(field = "ksid")
	    private List<Userpk> userpks;
	 @Name
	private java.lang.String id;//id
	 @Column
	private java.lang.String title;//title
	 @Column
	private java.lang.String intro;//intro
	 @Column
	private java.lang.String bjs;//bjs
	 @Column
	private java.lang.String kmcode;//kmcode
	 @Column
	private java.lang.String kstype;//kstype
	 @Column
	private java.lang.String kch;//kch
	 @Column
	private java.lang.Integer pc;//pc
	 @Column
	private java.sql.Timestamp starttime;//starttime
	 @Column
	private java.sql.Timestamp endtime;//endtime
	 @Column
	private java.lang.String cruser;//cruser
	 @Column
	private java.lang.String chkid;//chkid
	 @Column
	private java.lang.String handleid;//handleid
	 @Column
	private java.lang.String status;//status
	 @Column
	private java.lang.String sentp;//sentp
	 @Column
	private java.lang.String hsp;//hsp
	public java.lang.String getId(){
		return this.id;
	}
	public void setId(java.lang.String id){
		this.id	= id;
	}
	public java.lang.String getTitle(){
		return this.title;
	}
	public void setTitle(java.lang.String title){
		this.title	= title;
	}
	public java.lang.String getIntro(){
		return this.intro;
	}
	public void setIntro(java.lang.String intro){
		this.intro	= intro;
	}
	public java.lang.String getBjs(){
		return this.bjs;
	}
	public void setBjs(java.lang.String bjs){
		this.bjs	= bjs;
	}
	public java.lang.String getKmcode(){
		return this.kmcode;
	}
	public void setKmcode(java.lang.String kmcode){
		this.kmcode	= kmcode;
	}
	public java.lang.String getKstype(){
		return this.kstype;
	}
	public void setKstype(java.lang.String kstype){
		this.kstype	= kstype;
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
	public java.lang.String getCruser(){
		return this.cruser;
	}
	public void setCruser(java.lang.String cruser){
		this.cruser	= cruser;
	}
	public java.lang.String getChkid(){
		return this.chkid;
	}
	public void setChkid(java.lang.String chkid){
		this.chkid	= chkid;
	}
	public java.lang.String getHandleid(){
		return this.handleid;
	}
	public void setHandleid(java.lang.String handleid){
		this.handleid	= handleid;
	}
	public java.lang.String getStatus(){
		return this.status;
	}
	public void setStatus(java.lang.String status){
		this.status	= status;
	}
	public java.lang.String getSentp(){
		return this.sentp;
	}
	public void setSentp(java.lang.String sentp){
		this.sentp	= sentp;
	}
	public java.lang.String getHsp(){
		return this.hsp;
	}
	public void setHsp(java.lang.String hsp){
		this.hsp	= hsp;
	}
	public List<Userpk> getUserpks(){
		return this.userpks;
	}
	public void setUserpks(List<Userpk> userpks2) {
		this.userpks=userpks2;
		
	}
}