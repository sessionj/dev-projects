package egovframework.api.web;

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
import egovframework.api.service.TraceService;

/**
 *  TraceController api - 업무정의
 *  1. 운송정보 제공
 *    . 기본값 (일자 : replace(to_char(add_months(sysdate,-3),'yyyy-mm-dd'),'-','') and replace(to_char(sysdate,'yyyy-mm-dd'),'-','') - 무조건 3개월
 *    . 옵션 : 운송장번호
 *            발화주 전화번호
 *            수화주 전화번호
 *  2.           
 */

/**
  * @FileName : EgovApiController.java
  * @Project : kr.co.ds.api
  * @Date : 2022. 10. 27. 
  * @작성자 : shadow
  * @변경이력 :
  * @프로그램 설명 : 대신 Rest API 
  */

@Controller 
public class TraceController {

	/** EgovApiService */
	@Resource(name = "traceService")
	private TraceService traceService;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	
	/**
	 * 화물 추적 대상 목록을 조회한다 | 운송 목록
	 * 사용자 목록 조회
	 * @param FrontApiDefaultEntity - 조회할 정보가 담긴 VO
	 * @return 화물추적 목록
	 * @exception Exception
	 * 
	 * 옵션 사용 여부 searchUseYn : Y, N : N --> return null;
	 * 옵션 searchOption : 받는분 0 , 보내는분 1 
	 * 옵션 searchKeyword : 전화번호
	 * mybatis(mapper)
	 */
	@RequestMapping(value = "/findUnsongList.json", 
				   method = RequestMethod.POST, 
				   headers = {"Content-type=application/json" }
	/* , produces = "application/text;charset=utf-8" */)
	public @ResponseBody List<UnsongEntity> findUnsongList(@ModelAttribute("fSearchEntity") FrontApiDefaultEntity frontApiDefaultEntity, ModelMap model) throws Exception{
		return traceService.findUnsongList(frontApiDefaultEntity);
	}
	
	/**
	 * 화물 추적 상세 정보를 조회한다 | 상세 정보
	 * @param FrontApiDefaultEntity - 조회할 정보가 담긴 VO
	 * @return 화물추적 목록
	 * @exception Exception
	 *
	 * 옵션 searchOption : 받는분 0 , 보내는분 1 
	 * 옵션 searchKeyword : 전화번호
	 */
	@RequestMapping(value = "/findUnsongArticle.json", 
			   method = RequestMethod.POST, 
			   headers = {"Content-type=application/json" }
	/* , produces = "application/text;charset=utf-8" */)
	public @ResponseBody Map<String, Object> findUnsongArticle(@ModelAttribute("fSearchEntity") FrontApiDefaultEntity entity, ModelMap model) throws Exception {
		/*
		 * Map<String, Object> returnMap = new HashMap<String, Object>();
		 * returnMap.put("unsong", traceService.findUnsongArticle(entity));
		 * returnMap.put("trace", traceService.findTraceArticle(entity));
		 */
		return traceService.findSynthesis(entity);
	}
	
	
	
	
	
	// ibatis(dao)
	@RequestMapping(value = "/shippingTraceList.json", 
			   method = RequestMethod.POST, 
			   headers = {"Content-type=application/json" }
	/* , produces = "application/text;charset=utf-8" */
	)
	public @ResponseBody List<UnsongEntity> selectTraceList(@ModelAttribute("fSearchEntity") FrontApiDefaultEntity frontApiDefaultEntity, ModelMap model) throws Exception{
		return traceService.selectTraceList(frontApiDefaultEntity);
	}
	
}
