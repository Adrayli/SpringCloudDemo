package com.ss.user.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ss.user.feignHystrix.UserFeignHystrix;


/**
 * 
 * feign接口 参数order：要访问的服务器实例名字，见eureka.application.name
 * 
 * @author AdministratorW
 *
 */
//@FeignClient(name = "order" , fallback = UserFeignHystrix.class)
@FeignClient("order")
public interface UserFeign {

	@GetMapping("getOrder")
	public String getOrder();

	@GetMapping("getOrder1/{id}")
	public String getOrder1(@PathVariable("id") int id);

	@GetMapping("getOrderHystrix/{id}")
	public String getOrderHystrix(@PathVariable("id") String id);

}
