package egovframework.trace.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * @FileName  : EgovApiDefaultEntity.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 10. 27.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 대신 > 사내게시판
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BulletinBoard implements Serializable{

	private static final long serialVersionUID = 958838578081269359L;
	
}
