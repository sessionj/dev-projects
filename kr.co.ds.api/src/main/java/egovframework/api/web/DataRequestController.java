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
 * @프로그램 설명 : 각종 질의문 처리
 */
@Controller 
public class DataRequestController {

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	/** EgovApiService */
	//@Resource(name = "traceService")
	//private TraceService traceService;
	
	/** 각종 질의문 처리 */
	/** ex) 특정 일자에 해당하는 노선의 물량 내역 */
	/** ex) 특정 일자에 해당하는 특정 영업소 발송, 도착 물량 */
	/** ex) 화물 운송 실적 자료 */
	/** ex) 쇼핑몰 도착 안내표 */
	/** ex) 영업소 기초자료 */
	/** ex) 기타 등등..  searchCondition 체크*/
	@RequestMapping(value = "/request/data/list.json", method = RequestMethod.GET, headers = {"Content-type=application/json" })
	public @ResponseBody List<UnsongEntity> getBoardList(@ModelAttribute("fSearchEntity") FrontApiDefaultEntity frontApiDefaultEntity, ModelMap model) throws Exception{
		return null;
	}
	
}
