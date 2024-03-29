package egovframework.api.web;

import java.util.List;

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


/**
 * 
 * @FileName  : AddressController.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 11. 28.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 주소를 전송 받아 도착 안내표 return : list
 */
@Controller 
public class AddressController {

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	/** 주소 검색 : 주소 검색 - 도착지 영업소 매핑 자료 반환 */
	@RequestMapping(value = "/searchLoadAddress.json", method = RequestMethod.POST,headers = {"Content-type=application/json" })
	public @ResponseBody List<UnsongEntity> selectHistoryList(@ModelAttribute("fSearchEntity") FrontApiDefaultEntity frontApiDefaultEntity, ModelMap model) throws Exception{
		return null;
	}
}
