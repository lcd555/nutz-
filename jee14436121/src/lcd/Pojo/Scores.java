package lcd.Pojo;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * socres
 * @author otom3
 * @date 2017-04-17
 */
 @Table("scores")
public class Scores  {
	/**
	 * socres
	 */
	 @Id
	private java.lang.Integer id;//id
	 @Column
	private java.lang.String ksid;//ksid
	 @Column
	private java.lang.String uid;//uid
	 @Column
	private java.lang.Float score;//score
	 @Column
	private java.lang.Float score1;//score1
	 @Column
	private java.lang.Float score2;//score2
	 @Column
	private java.lang.Float score3;//score3
	 @Column
	private java.lang.Float score4;//score4
	 @Column
	private java.lang.Float score5;//score5
	 @Column
	private java.lang.Float score6;//score6
	 @Column
	private java.lang.Float score7;//score7
	 @Column
	private java.lang.String paper;//paper
	 @Column
	private java.lang.Integer donum;//donum
	 @Column
	private java.util.Date jjtime;//jjtime
	public java.lang.Integer getId(){
		return this.id;
	}
	public void setId(java.lang.Integer id){
		this.id	= id;
	}
	public java.lang.String getKsid(){
		return this.ksid;
	}
	public void setKsid(java.lang.String ksid){
		this.ksid	= ksid;
	}
	public java.lang.String getUid(){
		return this.uid;
	}
	public void setUid(java.lang.String uid){
		this.uid	= uid;
	}
	public java.lang.Float getScore(){
		return this.score;
	}
	public void setScore(java.lang.Float score){
		this.score	= score;
	}
	public java.lang.Float getScore1(){
		return this.score1;
	}
	public void setScore1(java.lang.Float score1){
		this.score1	= score1;
	}
	public java.lang.Float getScore2(){
		return this.score2;
	}
	public void setScore2(java.lang.Float score2){
		this.score2	= score2;
	}
	public java.lang.Float getScore3(){
		return this.score3;
	}
	public void setScore3(java.lang.Float score3){
		this.score3	= score3;
	}
	public java.lang.Float getScore4(){
		return this.score4;
	}
	public void setScore4(java.lang.Float score4){
		this.score4	= score4;
	}
	public java.lang.Float getScore5(){
		return this.score5;
	}
	public void setScore5(java.lang.Float score5){
		this.score5	= score5;
	}
	public java.lang.Float getScore6(){
		return this.score6;
	}
	public void setScore6(java.lang.Float score6){
		this.score6	= score6;
	}
	public java.lang.Float getScore7(){
		return this.score7;
	}
	public void setScore7(java.lang.Float score7){
		this.score7	= score7;
	}
	public java.lang.String getPaper(){
		return this.paper;
	}
	public void setPaper(java.lang.String paper){
		this.paper	= paper;
	}
	public java.lang.Integer getDonum(){
		return this.donum;
	}
	public void setDonum(java.lang.Integer donum){
		this.donum	= donum;
	}
	public java.util.Date getJjtime(){
		return this.jjtime;
	}
	public void setJjtime(Date jjtime){
		this.jjtime	= jjtime;
	}
}