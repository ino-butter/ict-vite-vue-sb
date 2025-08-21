package com.example.springVueTest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test2Controller {
	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@RequestMapping(value="/bring", method=RequestMethod.GET)
	public List<String> bring(){
		log.info("bring진입");
		
		List<String> list = new ArrayList<String>();
		
		list.add("스프링 부트에서 보낸 데이터1");
		list.add("스프링 부트에서 보낸 데이터2");
		list.add("스프링 부트에서 보낸 데이터3");
		return list;
	}
}