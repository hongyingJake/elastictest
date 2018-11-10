package com.hongying.oracletest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oracle")
public class OracleController {

	@Autowired
	BrokerMapper mapper;
	
	@GetMapping("/name")
	public String GetbrokerName(String id){
		String name=mapper.GetBrokerName(id);
		System.out.println(name);
		return name;
	}
}
