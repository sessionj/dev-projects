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

import egovframework.api.entity.AgencyCodeEntity;
import egovframework.api.entity.FrontApiDefaultEntity;
import egovframework.api.entity.MasterCodeEntity;
import egovframework.api.entity.UnsongEntity;
import egovframework.api.service.CodeManagementService;

/**
 * 
 * @FileName  : BoardController.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 11. 28.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 대신 코드 관리 컨트롤러
 */
@Controller 
public class CodeManagerController {

	/** EgovApiService */
	@Resource(name = "codeManagementService")
	private CodeManagementService codeManagementService;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	/** 코드관리 목록 [mast] */
	@RequestMapping(value = "/code/master/list.json", method = RequestMethod.GET, headers = {"Content-type=application/json" })
	public @ResponseBody List<MasterCodeEntity> getMasterCodeList(@ModelAttribute("fSearchEntity") FrontApiDefaultEntity frontApiDefaultEntity, ModelMap model) throws Exception{
		return codeManagementService.findMastCodeList(frontApiDefaultEntity);
	}
	
	/** 코드관리 목록 [agency]*/
	@RequestMapping(value = "/code/agency/list.json", method = RequestMethod.GET, headers = {"Content-type=application/json" })
	public @ResponseBody List<AgencyCodeEntity> getAgencyCodeList(@ModelAttribute("fSearchEntity") FrontApiDefaultEntity frontApiDefaultEntity, ModelMap model) throws Exception{
		return codeManagementService.findAgencyCodeList(frontApiDefaultEntity);
	}
	
	/** 코드관리 목록 [car]*/
	@RequestMapping(value = "/code/car/list.json", method = RequestMethod.GET, headers = {"Content-type=application/json" })
	public @ResponseBody List<AgencyCodeEntity> getCarCodeList(@ModelAttribute("fSearchEntity") FrontApiDefaultEntity frontApiDefaultEntity, ModelMap model) throws Exception{
		return codeManagementService.findAgencyCodeList(frontApiDefaultEntity);
	}
	
	/** 코드관리 뷰 */
	@RequestMapping(value = "/code/view.json", method = RequestMethod.GET, headers = {"Content-type=application/json" })
	public @ResponseBody List<UnsongEntity> getBoardView(@ModelAttribute("fSearchEntity") FrontApiDefaultEntity frontApiDefaultEntity, ModelMap model) throws Exception{
		return null;
	}
	
	/** 코드관리 - 등록 */
	@RequestMapping(value = "/code/create.json", method = RequestMethod.POST, headers = {"Content-type=application/json" })
	public @ResponseBody List<UnsongEntity> isBoardCreate(@ModelAttribute("fSearchEntity") FrontApiDefaultEntity frontApiDefaultEntity, ModelMap model) throws Exception{
		return null;
	}
	
	/** 코드관리 - 게시물 삭제 */
	@RequestMapping(value = "/code/delete.json", method = RequestMethod.GET, headers = {"Content-type=application/json" })
	public @ResponseBody List<UnsongEntity> isBoardDelete(@ModelAttribute("fSearchEntity") FrontApiDefaultEntity frontApiDefaultEntity, ModelMap model) throws Exception{
		return null;
	}
	
	/** 코드관리 - 수정*/
	@RequestMapping(value = "/code/modify.json", method = RequestMethod.POST, headers = {"Content-type=application/json" })
	public @ResponseBody List<UnsongEntity> isBoardModify(@ModelAttribute("fSearchEntity") FrontApiDefaultEntity frontApiDefaultEntity, ModelMap model) throws Exception{
		return null;
	}
}
