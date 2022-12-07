package egovframework.api.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * @FileName  : EgovApiDefaultEntity.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 10. 27.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 대신 > 문자 서버 대응 entity
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmsEntity implements Serializable{

	private static final long serialVersionUID = 958838578081269359L;
	
	private String seqno = "";
	private String dest_no = "";
	private String call_back = "";
	private String msg_contents = "";
	private String msg_instm = "";
	private String sendreq_time = "";
	private String mobsend_time = "";
	private String repmsg_recvtm = "";
	private String status_code = "";
	private String mob_company = "";
	private String title_str = "";
	private String msg_type = "";
	private String bill_id = "";
	private String agencycode = "";
	private String customernm = "";
	private String result_code = "";
	public String getSeqno() {
		return seqno;
	}
	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}
	public String getDest_no() {
		return dest_no;
	}
	public void setDest_no(String dest_no) {
		this.dest_no = dest_no;
	}
	public String getCall_back() {
		return call_back;
	}
	public void setCall_back(String call_back) {
		this.call_back = call_back;
	}
	public String getMsg_contents() {
		return msg_contents;
	}
	public void setMsg_contents(String msg_contents) {
		this.msg_contents = msg_contents;
	}
	public String getMsg_instm() {
		return msg_instm;
	}
	public void setMsg_instm(String msg_instm) {
		this.msg_instm = msg_instm;
	}
	public String getSendreq_time() {
		return sendreq_time;
	}
	public void setSendreq_time(String sendreq_time) {
		this.sendreq_time = sendreq_time;
	}
	public String getMobsend_time() {
		return mobsend_time;
	}
	public void setMobsend_time(String mobsend_time) {
		this.mobsend_time = mobsend_time;
	}
	public String getRepmsg_recvtm() {
		return repmsg_recvtm;
	}
	public void setRepmsg_recvtm(String repmsg_recvtm) {
		this.repmsg_recvtm = repmsg_recvtm;
	}
	public String getStatus_code() {
		return status_code;
	}
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	public String getMob_company() {
		return mob_company;
	}
	public void setMob_company(String mob_company) {
		this.mob_company = mob_company;
	}
	public String getTitle_str() {
		return title_str;
	}
	public void setTitle_str(String title_str) {
		this.title_str = title_str;
	}
	public String getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}
	public String getBill_id() {
		return bill_id;
	}
	public void setBill_id(String bill_id) {
		this.bill_id = bill_id;
	}
	public String getAgencycode() {
		return agencycode;
	}
	public void setAgencycode(String agencycode) {
		this.agencycode = agencycode;
	}
	public String getCustomernm() {
		return customernm;
	}
	public void setCustomernm(String customernm) {
		this.customernm = customernm;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	
}
