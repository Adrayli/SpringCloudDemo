package com.ss.user.controlller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.ss.user.feign.UserFeign;

@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
public class UserController {

	/**
	 * 1提供远程调用 http
	 */
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 负载均衡API，默认为轮询模式
	 */
	@Autowired
	private LoadBalancerClient client;

	/**
	 * 远程调用Feign自定义接口
	 */
	@Autowired
	private UserFeign userFeign;

	@GetMapping("user")
	public String getUser() {

//		HystrixCommandProperties
		
		ServiceInstance choose = client.choose("order"); // 选择服务的名称spring.application.name

		String host = choose.getHost();
		int port = choose.getPort();

		System.out.println("http://" + host + ":" + port);

		return restTemplate.getForObject("http://" + host + ":" + port + "/getOrder", String.class);
	}

	/**
	 * 远程调用
	 * @return
	 */
	@GetMapping("feign")
	public String feign() {
		return userFeign.getOrder();
	}

	/**
	 * 带参数的远程调用
	 * @param id
	 * @return
	 */
	@GetMapping("feignValue/{id}")
	public String feignValue(@PathVariable("id") int id) {
		return userFeign.getOrder1(id);
	}

	/**
	 * 熔断机制
	 * 配置信息可参考：HystrixCommandProperties
	 * 此处可认为是一个服务降级
	 * @param id
	 * @return
	 */
//	@HystrixCommand(fallbackMethod = "getFeignFallBack",
//			commandProperties = {
//						@HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
//						@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),	//最小请求数
//						@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),	//休眠时间窗
//						@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")	//出现错误的百分率
//					}
//				)
	@HystrixCommand
	@GetMapping("hystrixDemo/{id}")
	public String hystrixMethod(@PathVariable("id") String id) {
		return userFeign.getOrderHystrix(id);
	}

	private String getFeignFallBack(String id) {
		return "hystrix fall back ...";
	}
	
	private String defaultFallback() {
		return "服务繁忙，请稍后再试";
	}

}
