package egovframework.api.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @FileName  : UserAuthenticationEntity.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 10. 27.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 사용자 entity
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAuthenticationEntity implements Serializable{

	private static final long serialVersionUID = 958838578081269355L;
}
