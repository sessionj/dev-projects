package egovframework.example.sample.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
  * @FileName : EgovDeptController.java
  * @Project : kr.co.ds.std
  * @Date : 2022. 10. 11. 
  * @작성자 : shadow
  * @변경이력 :
  * @프로그램 설명 :
  */

@Controller
public class EgovDeptController {
	
	@RequestMapping(value = "/deptWrite.do")
	public String deptWrite() {
		return "dept/deptWrite";
	}
}