package lcd.Pojo;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import java.sql.Timestamp;
import java.util.Date;

/**
 * news
 * @author otom3
 * @date 2017-03-30
 */
 @Table("news")
public class News  {
	/**
	 * news
	 */
	 @Id
	private java.lang.Integer id;//id
	 @Column
	private java.lang.Integer classid;//classid
	 @Column
	private java.lang.String bjs;//bjs
	 @Column
	private java.lang.String title;//title
	 @Column
	private java.lang.String pic;//pic
	 @Column
	private java.lang.String istop;//istop
	 @Column
	private Date tjdate;//tjdate
	 @Column
	private java.lang.String cruser;//cruser
	 @Column
	private java.lang.Integer hitnum;//hitnum
	 @Column
	private java.lang.String content;//content
	public java.lang.Integer getId(){
		return this.id;
	}
	public void setId(java.lang.Integer id){
		this.id	= id;
	}
	public java.lang.Integer getClassid(){
		return this.classid;
	}
	public void setClassid(java.lang.Integer classid){
		this.classid	= classid;
	}
	public java.lang.String getBjs(){
		return this.bjs;
	}
	public void setBjs(java.lang.String bjs){
		this.bjs	= bjs;
	}
	public java.lang.String getTitle(){
		return this.title;
	}
	public void setTitle(java.lang.String title){
		this.title	= title;
	}
	public java.lang.String getPic(){
		return this.pic;
	}
	public void setPic(java.lang.String pic){
		this.pic	= pic;
	}
	public java.lang.String getIstop(){
		return this.istop;
	}
	public void setIstop(java.lang.String istop){
		this.istop	= istop;
	}
	public Date getTjdate(){
		return this.tjdate;
	}
	public void setTjdate(Date date){
		this.tjdate	=  date;
	}
	public java.lang.String getCruser(){
		return this.cruser;
	}
	public void setCruser(java.lang.String cruser){
		this.cruser	= cruser;
	}
	public java.lang.Integer getHitnum(){
		return this.hitnum;
	}
	public void setHitnum(java.lang.Integer hitnum){
		this.hitnum	= hitnum;
	}
	public java.lang.String getContent(){
		return this.content;
	}
	public void setContent(java.lang.String content){
		this.content	= content;
	}
}