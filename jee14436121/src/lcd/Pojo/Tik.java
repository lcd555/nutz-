package lcd.Pojo;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * tik
 * @author otom3
 * @date 2017-04-09
 */
 @Table("tik")
public class Tik  {
	/**
	 * tik
	 */
	 @Id
	private java.lang.Integer id;//id
	 @Column
	private java.lang.Integer gid;//gid
	 @Column
	private java.lang.Integer tiid;//tiid
	 @Column
	private java.lang.String kmcode;//kmcode
	 @Column
	private java.lang.String kbcode;//kbcode
	 @Column
	private java.lang.String qtcode;//qtcode
	 @Column
	private java.lang.String qteasy;//qteasy
	 @Column
	private java.lang.Float fenvalue;//fenvalue
	 @Column
	private java.lang.String qname;//qname
	 @Column
	private java.lang.String qnote;//qnote
	 @Column
	private java.lang.String qans;//qans
	 @Column
	private java.lang.String ansa;//ansA
	 @Column
	private java.lang.String ansb;//ansB
	 @Column
	private java.lang.String ansc;//ansC
	 @Column
	private java.lang.String ansd;//ansD
	 @Column
	private java.lang.String anse;//ansE
	 @Column
	private java.lang.String ansf;//ansF
	 @Column
	private java.lang.String ansg;//ansG
	 @Column
	private java.lang.String ansh;//ansH
	 @Column
	private java.lang.String ansi;//ansI
	public java.lang.Integer getId(){
		return this.id;
	}
	public void setId(java.lang.Integer id){
		this.id	= id;
	}
	public java.lang.Integer getGid(){
		return this.gid;
	}
	public void setGid(java.lang.Integer gid){
		this.gid	= gid;
	}
	public java.lang.Integer getTiid(){
		return this.tiid;
	}
	public void setTiid(java.lang.Integer tiid){
		this.tiid	= tiid;
	}
	public java.lang.String getKmcode(){
		return this.kmcode;
	}
	public void setKmcode(java.lang.String kmcode){
		this.kmcode	= kmcode;
	}
	public java.lang.String getKbcode(){
		return this.kbcode;
	}
	public void setKbcode(java.lang.String kbcode){
		this.kbcode	= kbcode;
	}
	public java.lang.String getQtcode(){
		return this.qtcode;
	}
	public void setQtcode(java.lang.String qtcode){
		this.qtcode	= qtcode;
	}
	public java.lang.String getQteasy(){
		return this.qteasy;
	}
	public void setQteasy(java.lang.String qteasy){
		this.qteasy	= qteasy;
	}
	public java.lang.Float getFenvalue(){
		return this.fenvalue;
	}
	public void setFenvalue(java.lang.Float fenvalue){
		this.fenvalue	= fenvalue;
	}
	public java.lang.String getQname(){
		return this.qname;
	}
	public void setQname(java.lang.String qname){
		this.qname	= qname;
	}
	public java.lang.String getQnote(){
		return this.qnote;
	}
	public void setQnote(java.lang.String qnote){
		this.qnote	= qnote;
	}
	public java.lang.String getQans(){
		return this.qans;
	}
	public void setQans(java.lang.String qans){
		this.qans	= qans;
	}
	public java.lang.String getAnsa(){
		return this.ansa;
	}
	public void setAnsa(java.lang.String ansa){
		this.ansa	= ansa;
	}
	public java.lang.String getAnsb(){
		return this.ansb;
	}
	public void setAnsb(java.lang.String ansb){
		this.ansb	= ansb;
	}
	public java.lang.String getAnsc(){
		return this.ansc;
	}
	public void setAnsc(java.lang.String ansc){
		this.ansc	= ansc;
	}
	public java.lang.String getAnsd(){
		return this.ansd;
	}
	public void setAnsd(java.lang.String ansd){
		this.ansd	= ansd;
	}
	public java.lang.String getAnse(){
		return this.anse;
	}
	public void setAnse(java.lang.String anse){
		this.anse	= anse;
	}
	public java.lang.String getAnsf(){
		return this.ansf;
	}
	public void setAnsf(java.lang.String ansf){
		this.ansf	= ansf;
	}
	public java.lang.String getAnsg(){
		return this.ansg;
	}
	public void setAnsg(java.lang.String ansg){
		this.ansg	= ansg;
	}
	public java.lang.String getAnsh(){
		return this.ansh;
	}
	public void setAnsh(java.lang.String ansh){
		this.ansh	= ansh;
	}
	public java.lang.String getAnsi(){
		return this.ansi;
	}
	public void setAnsi(java.lang.String ansi){
		this.ansi	= ansi;
	}
}