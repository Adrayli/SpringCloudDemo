package com.ss.getway.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 前置过滤器
 * 案例：权限认证 如果请求的带有token，就允许访问，反之则不能
 * 
 * @author Administrator
 *
 */
@Component
public class TokenFilter extends ZuulFilter{

	/**
	 * 指定的一些filter规则，默认为false，需要设置为返回true，否则不会执行run方法
	 */
	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 这里就是实现业务逻辑的地方
	 */
	@Override
	public Object run() {
		// TODO Auto-generated method stub
		RequestContext currentContext = RequestContext.getCurrentContext();
		HttpServletRequest request = currentContext.getRequest();
		String token = request.getParameter("token");
		if(StringUtils.isEmpty(token)) {
			currentContext.setSendZuulResponse(false);
			//状态码，无权限
			currentContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
		}
		return null;
	}

	/**
	 * 表示当前filter的类型	pre post route error
	 */
	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		//org.springframework.cloud.netflix.zuul.filters.support.FilterConstants包下的前置过滤器
		return FilterConstants.PRE_TYPE;
	}

	/**
	 * 表示当前filter的优先级
	 */
	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
	}

}
