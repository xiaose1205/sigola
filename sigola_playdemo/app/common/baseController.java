package common;

import play.Play;
import play.data.binding.Global;
import play.mvc.After;
import play.mvc.Before;
import play.mvc.Catch;
import play.mvc.Controller;
import play.mvc.Finally;
import play.mvc.Http;
import play.mvc.Scope.Session;

/**
 * Copyright (C) 2013, UC(优视) All rights reserved
 * 
 * 文件名称： baseController.java 功能:
 * 
 * @author wangjun4
 * @contact wangjun4@ucweb.com
 * @date 2013-5-28
 * 
 */
public class baseController extends Controller {

	 
	@Finally
	static void invocationFinally() {
		System.out.println("@Finally");
	}

	@Catch(value = ArithmeticException.class, priority = 1)
	static void docatch() {
		System.out.println("@Catch");
	}

 
}
