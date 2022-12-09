package egovframework.api.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * @FileName  : AgencyCodeEntity.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 10. 27.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 코드관리 Entity [영업소코드]
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestDataEntity implements Serializable{

	private static final long serialVersionUID = 958838578081269356L;

	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
		
}
