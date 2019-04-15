package com.ss.user.feignHystrix;

import org.springframework.stereotype.Component;

import com.ss.user.feign.UserFeign;

@Component
public class UserFeignHystrix implements UserFeign{
	@Override
	public String getOrder() {
		return null;
	}
	@Override
	public String getOrder1(int id) {
		return null;
	}
	@Override
	public String getOrderHystrix(String id) {
		return "net work is busy now !!";
	}

}
