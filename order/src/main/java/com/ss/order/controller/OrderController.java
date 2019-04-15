package com.ss.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
	
	@Value("${env}")
	private String env;
	
	@GetMapping("getEnv")
	public String env() {
		return env;
	}
	
	@GetMapping("getOrder")
	public String getOrder() {
		System.out.println("request order server");
		return "hello spring cloud !!";
	}
	
	@GetMapping("getOrder1/{id}")
	public String getOrder1(@PathVariable("id") int id) {
		return "value : " + id;
	}
	
	@GetMapping("getOrderHystrix/{id}")
	public String getOrderHystrix(@PathVariable("id")String id) {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "hystrix : " + id;
	}
	
	

}
