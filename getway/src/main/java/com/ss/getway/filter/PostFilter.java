package com.ss.getway.filter;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;


/**
 * post filter 后置过滤器
 * 过滤后返回一个cookie
 * @author Administrator
 *
 */
@Component
public class PostFilter extends ZuulFilter{

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 业务逻辑
	 */
	@Override
	public Object run() {
		// TODO Auto-generated method stub
		RequestContext currentContext = RequestContext.getCurrentContext();
		HttpServletResponse response = currentContext.getResponse();
		Cookie cookie = new Cookie("random",UUID.randomUUID().toString());
		cookie.setMaxAge(24 * 60 * 60);
		response.addCookie(cookie);
		return null;
	}

	/**
	 * filter类型
	 */
	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return FilterConstants.POST_TYPE;
	}

	/**
	 * filter优先级
	 */
	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
	}

}
