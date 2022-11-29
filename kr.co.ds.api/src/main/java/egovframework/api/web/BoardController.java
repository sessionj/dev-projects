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
 * @FileName  : BoardController.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 11. 28.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 각종 게시판 데이터 return 
 * 				  게시판 Index 1 : 공지사항
 * 				  게시판 Index 2 : 터미널 공지 
 */
@Controller 
public class BoardController {

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	/** 게시판 목록 */
	@RequestMapping(value = "/board/list.json", method = RequestMethod.GET, headers = {"Content-type=application/json" })
	public @ResponseBody List<UnsongEntity> getBoardList(@ModelAttribute("fSearchEntity") FrontApiDefaultEntity frontApiDefaultEntity, ModelMap model) throws Exception{
		return null;
	}
	
	/** 게시판 뷰 */
	@RequestMapping(value = "/board/view.json", method = RequestMethod.GET, headers = {"Content-type=application/json" })
	public @ResponseBody List<UnsongEntity> getBoardView(@ModelAttribute("fSearchEntity") FrontApiDefaultEntity frontApiDefaultEntity, ModelMap model) throws Exception{
		return null;
	}
	
	/** 게시판 - 게시물 등록 */
	@RequestMapping(value = "/board/create.json", method = RequestMethod.POST, headers = {"Content-type=application/json" })
	public @ResponseBody List<UnsongEntity> isBoardCreate(@ModelAttribute("fSearchEntity") FrontApiDefaultEntity frontApiDefaultEntity, ModelMap model) throws Exception{
		return null;
	}
	
	/** 게시판 - 게시물 삭제 */
	@RequestMapping(value = "/board/delete.json", method = RequestMethod.GET, headers = {"Content-type=application/json" })
	public @ResponseBody List<UnsongEntity> setBoardDelete(@ModelAttribute("fSearchEntity") FrontApiDefaultEntity frontApiDefaultEntity, ModelMap model) throws Exception{
		return null;
	}
	
	/** 게시판 - 게시물 수정*/
	@RequestMapping(value = "/board/getBoard-1/modify.json", method = RequestMethod.POST, headers = {"Content-type=application/json" })
	public @ResponseBody List<UnsongEntity> setBoardModify(@ModelAttribute("fSearchEntity") FrontApiDefaultEntity frontApiDefaultEntity, ModelMap model) throws Exception{
		return null;
	}
}
