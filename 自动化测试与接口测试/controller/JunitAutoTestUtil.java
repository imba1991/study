package controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.spi.ValidationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.htwz.BmWechatBooterApplication;
import com.htwz.common.controller.HtControllerAdvice;

@Resource
public class JunitAutoTestUtil  {
	
	static MockMvc mockMvc;

	private HtControllerAdvice htControllerAdvice;
	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private MockHttpSession session;
	@Autowired
	private MockHttpServletRequest request;
	@Before
	public void setip() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	public JunitAutoTestUtil() {
		super();
	}

	/**
	 * 
	 *@author zhangqing
	 *@date 2019年1月17日
	 *@description Json测试
	 *@throws Exception
	 */
	public  void junitByJson(Object object,String url,String method) throws Exception{
		MvcResult result = mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(JSONObject.toJSONString(object))
				)
				.andExpect(status().isOk())// 模拟向testRest发送get请求
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
				.andReturn();// 返回执行请求的结果
		String resultStr=result.getResponse().getContentAsString();
		System.out.println(method+"--------------"+resultStr);
		Map resultMap=(Map) JSON.parse(resultStr);
		int code=(int)resultMap.get("code");
		Assert.assertTrue("成功", code==1001);
		
		
	}
	/**
	 * 
	 *@author zhangqing
	 *@date 2019年1月17日
	 *@description String 类型参数
	 *@throws Exception
	 */
	public  void  junitByString(String param,String url,String method)throws Exception{
		MvcResult result = mockMvc.perform(post(url).param("id", param))
				.andExpect(status().isOk())
				.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		System.out.println(method+"------"+resultStr);
		Map resultMap = (Map)JSON.parse(resultStr);
		int code = (int)resultMap.get("code");
		Assert.assertTrue("成功",code==1001);
	}
	/**
	 * 
	 *@author zhangqing
	 *@date 2019年1月17日
	 *@description 无参测试
	 *@throws Exception
	 */
	public static void junitGet( String url, String method) throws Exception {

		System.out.println(url + method);
		MvcResult result = mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				)
				.andExpect(status().isOk())
				.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		System.out.println(method+"-------"+resultStr);
		Map resultMap = (Map)JSON.parse(resultStr);
		int code = (int)resultMap.get("code");
		Assert.assertTrue("成功",code==1001);
		
	}
	
}
