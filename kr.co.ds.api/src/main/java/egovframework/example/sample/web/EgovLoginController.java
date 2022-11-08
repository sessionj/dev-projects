package egovframework.example.sample.web;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.example.sample.service.EgovSampleService;

public class EgovLoginController {

	/** EgovSampleService */
	@Resource(name = "sampleService")
	private EgovSampleService sampleService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	public boolean isAuthenticated() {
		
		return false;
		
	}
}
