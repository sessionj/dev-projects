package egovframework.api.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * @FileName  : EgovApiDefaultEntity.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 10. 27.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 코드관리 Entity [codeMast]
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MasterCodeEntity implements Serializable{

	private static final long serialVersionUID = 958838578081269357L;
	
	private String code_type = "";
	private String code_code = "";
	private String code_fname  = "";
	private String code_used = "";
	private String code_date = "";
	private String code_uid = "";
	private String code_gbn = "";
	
	AgencyCodeEntity agencyCodeEntity;
	CarCodeEntity carCodeEntity;
	
	public String getCode_type() {
		return code_type;
	}

	public void setCode_type(String code_type) {
		this.code_type = code_type;
	}

	public String getCode_code() {
		return code_code;
	}

	public void setCode_code(String code_code) {
		this.code_code = code_code;
	}

	public String getCode_fname() {
		return code_fname;
	}

	public void setCode_fname(String code_fname) {
		this.code_fname = code_fname;
	}

	public String getCode_used() {
		return code_used;
	}

	public void setCode_used(String code_used) {
		this.code_used = code_used;
	}

	public String getCode_date() {
		return code_date;
	}

	public void setCode_date(String code_date) {
		this.code_date = code_date;
	}

	public String getCode_uid() {
		return code_uid;
	}

	public void setCode_uid(String code_uid) {
		this.code_uid = code_uid;
	}

	public String getCode_gbn() {
		return code_gbn;
	}

	public void setCode_gbn(String code_gbn) {
		this.code_gbn = code_gbn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public AgencyCodeEntity getAgencyCodeEntity() {
		return agencyCodeEntity;
	}

	public void setAgencyCodeEntity(AgencyCodeEntity agencyCodeEntity) {
		this.agencyCodeEntity = agencyCodeEntity;
	}

	public CarCodeEntity getCarCodeEntity() {
		return carCodeEntity;
	}

	public void setCarCodeEntity(CarCodeEntity carCodeEntity) {
		this.carCodeEntity = carCodeEntity;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
		
}
