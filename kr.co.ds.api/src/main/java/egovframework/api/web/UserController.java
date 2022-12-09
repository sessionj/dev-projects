package egovframework.api.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.api.entity.FrontApiDefaultEntity;
import egovframework.api.entity.UnsongEntity;
import egovframework.api.service.UserService;

/**
 * 
 * @FileName  : BoardController.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 11. 28.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 로그인 관리 컨트롤러
 */
@Controller 
public class UserController {

	/** EgovApiService */
	@Resource(name = "userService")
	private UserService userService;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	/** 로그인 Key 생성 URL 제공 - tmp 
	 *  페이지에서 직접 인증키 받기[테스트 용도]
	 * */
	@RequestMapping(value = "/tmp/login/access-key.json", method = RequestMethod.GET)
	public String accessKey(@ModelAttribute("fSearchEntity") FrontApiDefaultEntity frontApiDefaultEntity, ModelMap model) throws Exception{
		return "login/accessKey";
	}
	
	/**
	  * @Method Name : generateLoginKey
	  * @작성일 : 2022. 12. 5.
	  * @작성자 : shadow
	  * @변경이력 : 2022. 12. 5.
	  * @Method 설명 : 로그인 인증키 생성[전화번호에 해당하나는 매핑키]
	  * @param frontApiDefaultEntity
	  * @param model
	  * @return
	  * @throws Exception
	 */
	@RequestMapping(value = "/login/access-key.json", method = RequestMethod.POST,headers = {"Content-type=application/json" }/*,produces = "application/text;charset=utf-8" */ )
	public @ResponseBody Map<String, Object> generateLoginKey (@ModelAttribute("fSearchEntity") FrontApiDefaultEntity frontApiDefaultEntity, ModelMap model) throws Exception{
		
		Map<String, Object> map = null;
		map = new HashMap<String, Object>();
		map.put("access-key", userService.authenticationKeyGeneration(frontApiDefaultEntity));
		
		return map;
	}
	
}
