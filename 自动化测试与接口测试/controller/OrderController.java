package controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.assertj.core.error.ShouldBeEqualToIgnoringFields;
import org.hibernate.sql.Delete;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runner.RunWith;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.WebApplicationContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.htwz.BmWechatBooterApplication;
import com.htwz.business.tickeorder.dto.OrderBaseConfigDTO;
import com.htwz.business.tickeorder.dto.OrderOffUseConfigDTO;
import com.htwz.business.tickeorder.dto.OrderPointConfigDTO;
import com.htwz.business.tickeorder.dto.OrderRecordDTO;
import com.htwz.business.tickeorder.dto.OrderRecordVisitorDTO;
import com.htwz.business.tickeorder.dto.OrderTicketConfigDTO;
import com.htwz.business.tickeorder.entity.OrderBaseConfig;
import com.htwz.common.controller.HtControllerAdvice;
import com.htwz.common.exception.HtRunningException;
import com.htwz.common.param.QueryParams;
import com.mysql.jdbc.Util;



/**
 * @author qiujunda
 * @date 2019年1月14日
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=BmWechatBooterApplication.class)
public class OrderController {
	private MockMvc mockMvc;
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
	/**
	 * @author qiujunda
	 * @date 2019年1月14日
	 * @description 测试查询当前用户或者公司的预约配置信息
	 * @throws Exception
	 */
	public void orderBaseConfigGet(OrderBaseConfigDTO dto,boolean valid) throws Exception {
		MvcResult result = mockMvc.perform(post("/order/baseConfig/get")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(JSONObject.toJSONString(dto))
				)
				.andExpect(status().isOk())// 模拟向testRest发送get请求
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
				.andReturn();// 返回执行请求的结果
		String resultStr=result.getResponse().getContentAsString();
		System.out.println("orderBaseConfigGet--------------"+resultStr);
		Map resultMap=(Map) JSON.parse(resultStr);
		int code=(int)resultMap.get("code");
		OrderBaseConfig orderBaseConfig=new OrderBaseConfig();
		BeanUtils.populate(orderBaseConfig, (Map<String, ? extends Object>) resultMap.get("datas"));
//		System.out.println(orderBaseConfig.getId()+"-"+orderBaseConfig.getNeededInfos()+"-"+orderBaseConfig.getOrderDelayDays()+"-"+orderBaseConfig.getOrderDesStatus()+"-"+orderBaseConfig.getOrderStatus()+"-"+orderBaseConfig.getOrderLimitedPersonNum());
		Assert.assertTrue("成功", code==1001);
		if(valid) {
			Assert.assertTrue("成功", dto.getId().equals(orderBaseConfig.getId()));
			Assert.assertTrue("成功", dto.getMsgTemplate().equals(orderBaseConfig.getMsgTemplate()));
			Assert.assertTrue("成功", dto.getNeededInfos().equals(orderBaseConfig.getNeededInfos()));
			Assert.assertTrue("成功", dto.getOrderDes().equals(orderBaseConfig.getOrderDes()));
			Assert.assertTrue("成功", dto.getOrderDelayDays()==orderBaseConfig.getOrderDelayDays());
			Assert.assertTrue("成功", dto.getOrderDesStatus()==orderBaseConfig.getOrderDesStatus());
			Assert.assertTrue("成功", dto.getOrderLimitedPersonNum()==orderBaseConfig.getOrderLimitedPersonNum());
			Assert.assertTrue("成功", dto.getOrderStatus()==orderBaseConfig.getOrderStatus());
		}
	}
	/**
	 * @author qiujunda
	 * @date 2019年1月15日
	 * @description 保存预约配置信息
	 */
	public void updateOrderBaseConfig(OrderBaseConfigDTO dto) throws Exception{
	
		
		MvcResult result = mockMvc.perform(post("/order/baseConfig/update")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(JSONObject.toJSONString(dto))
				)
				.andExpect(status().isOk())// 模拟向testRest发送get请求
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
				.andReturn();// 返回执行请求的结果
		String resultStr=result.getResponse().getContentAsString();
		System.out.println("updateOrderBaseConfig--------------"+resultStr);
		Map resultMap=(Map) JSON.parse(resultStr);
		int code=(int)resultMap.get("code");
		Assert.assertTrue("成功", code==1001);
	}
	/**
	 * @author zhangqing
	 * @date 2019年1月15日
	 * @description 重置票据池
	 * @throws Exception
	 */
	public void resetTicketPool() throws Exception{
		MvcResult result = mockMvc.perform(post("/order/baseConfig/reset")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				//.content(JSONObject.toJSONString(dto))
				)
				.andExpect(status().isOk())// 模拟向testRest发送get请求
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
				.andReturn();// 返回执行请求的结果
		String resultStr=result.getResponse().getContentAsString();
		System.out.println("resetTicketPool--------------"+resultStr);
		Map resultMap=(Map) JSON.parse(resultStr);
		int code=(int)resultMap.get("code");
		Assert.assertTrue("成功", code==1001);
	}
	/**
	 * @author zhangqing
	 * @date 2019年1月15日
	 * @description 添加闭馆日期
	 * @throws Exception
	 */
	public void addOffDate(OrderOffUseConfigDTO offdto) throws Exception{
		
		MvcResult result = mockMvc.perform(post("/order/offUseConfig/add")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(JSON.toJSONString(offdto)))
				.andExpect(status().isOk())
				.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		System.out.println("addOffDate------"+resultStr);
		Map resultMap = (Map)JSON.parse(resultStr);
		int code = (int)resultMap.get("code");
		Assert.assertTrue("成功",code==1001);
	}
	/**
	 * 
	 *@author zhangqing
	 *@date 2019年1月15日
	 *@description 删除闭錧日期
	 *@throws Exception
	 */
	public void delOffDate(String id) throws Exception {
		//String id = "8aa781b2685053bb01685053cba60000";
	
//		MvcResult result = mockMvc.perform(post("/order/offUseConfig/delete")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(JSON.toJSONString(id)))
//				.andExpect(status().isOk())
//				.andReturn();
		MvcResult result = mockMvc.perform(post("/order/offUseConfig/delete").param("id", id))
//				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//				.content(JSON.toJSONString(id)))
				.andExpect(status().isOk())
				.andReturn();
				
		String resultStr = result.getResponse().getContentAsString();
		System.out.println("delOffDate()-------"+resultStr);
		Map resultMap = (Map)JSON.parse(resultStr);
		int code = (int)resultMap.get("code");
		Assert.assertTrue("成功",code==1001);
	}
	/**
	 * 
	 *@author zhangqing
	 *@date 2019年1月15日
	 *@description 添加预约场次
	 *@throws Exception
	 */
	public void addAppointment(OrderPointConfigDTO dto) throws Exception{
	
		MvcResult result = mockMvc.perform(post("/order/pointConfig/add")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(JSON.toJSONString(dto)))
				.andExpect(status().isOk())
				.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		System.out.println("addAppointment()-------"+resultStr);
		Map resultMap = (Map)JSON.parse(resultStr);
		int code = (int)resultMap.get("code");
		Assert.assertTrue("成功",code==1001);
		
	}
	/**
	 * 
	 *@author zhangqing
	 *@date 2019年1月15日
	 *@description 
	 *@throws 修改预约场次
	 */
	public void updateAppointment(OrderPointConfigDTO dto) throws Exception{
		
		MvcResult result = mockMvc.perform(post("/order/pointConfig/update")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(JSON.toJSONString(dto)))
				.andExpect(status().isOk())
				.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		System.out.println("updateAppointment()-------"+resultStr);
		Map resultMap = (Map)JSON.parse(resultStr);
		int code = (int)resultMap.get("code");
		Assert.assertTrue("成功",code==1001);
	}
	/**
	 * 
	 *@author zhangqing
	 *@date 2019年1月15日
	 *@description 删除预约场次
	 *@throws Exception
	 */
	public void deleteAppointment(String id) throws Exception{
		MvcResult result = mockMvc.perform(post("/order/pointConfig/delete").param("id", id))
				.andExpect(status().isOk())
				.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		System.out.println("deleteAppointment------"+resultStr);
		Map resultMap =(Map) JSON.parse(resultStr);
		int code = (int)resultMap.get("code");
		Assert.assertTrue("成功",code==1001);
	}
	/**
	 * 
	 *@author zhangqing
	 *@date 2019年1月15日
	 *@description 增加预约票型
	 *@throws Exception
	 */
	public void addTicket(OrderTicketConfigDTO dto) throws Exception{
		
		MvcResult result = mockMvc.perform(post("/order/ticketConfig/add")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(JSON.toJSONString(dto)))
				.andExpect(status().isOk())
				.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		System.out.println("addTicket()-------"+resultStr);
		Map resultMap = (Map)JSON.parse(resultStr);
		int code = (int)resultMap.get("code");
		Assert.assertTrue("成功",code==1001);
	}
	/**
	 * 
	 *@author zhangqing
	 *@date 2019年1月15日
	 *@description 删除预约票型
	 *@throws Exception
	 */
	public void delTicket(String id ) throws Exception{
		
		MvcResult result = mockMvc.perform(post("/order/ticketConfig/delete").param("id", id))
				.andExpect(status().isOk())
				.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		System.out.println("delTicket------"+resultStr);
		Map resultMap = (Map)JSON.parse(resultStr);
		int code = (int)resultMap.get("code");
		Assert.assertTrue("成功",code==1001);
	}
	/**
	 * 
	 *@author zhangqing
	 *@date 2019年1月15日
	 *@description 查询预约票型
	 *@throws Exception
	 */
	public void qeuryTicket() throws Exception{
		MvcResult result = mockMvc.perform(post("/order/ticketConfig/query")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				//.content(JSON.toJSONString(dto)))
				)
				.andExpect(status().isOk())
				.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		System.out.println("qeuryTicket()-------"+resultStr);
		Map resultMap = (Map)JSON.parse(resultStr);
		int code = (int)resultMap.get("code");
		Assert.assertTrue("成功",code==1001);
	}
	/**
	 * 
	 *@author zhangqing
	 *@date 2019年1月15日
	 *@description 分页查询订单信息
	 *@throws Exception
	 */
	public void queryOrderRecordPage(QueryParams dto) throws Exception{
		
		MvcResult result = mockMvc.perform(post("/order/record/queryPage")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(JSON.toJSONString(dto)))
				.andExpect(status().isOk())
				.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		System.out.println("queryOrderRecordPage()-------"+resultStr);
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
	public  void junitGet(String url,String method) throws Exception {
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
	public  void  junitByString(String id,String url,String method)throws Exception{
		MvcResult result = mockMvc.perform(post(url).param("id", id))
				.andExpect(status().isOk())
				.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		System.out.println(method+"------"+resultStr);
		Map resultMap = (Map)JSON.parse(resultStr);
		int code = (int)resultMap.get("code");
		Assert.assertTrue("成功",code==1001);
	}
	
	@Test
	@Rollback(false)
	@Transactional
	public void testOrderController()throws Exception {
		/*----------配置接口参数-------*/
		//预约配置接口 OrderBaseConfigController
		OrderBaseConfigDTO dto=new OrderBaseConfigDTO();
		dto.setId("10001");dto.setMsgTemplate("修改后的模板");dto.setNeededInfos("1000");
		dto.setOrderDelayDays(7);dto.setOrderDes("测试描述");dto.setOrderDesStatus(1);
		dto.setOrderLimitedPersonNum(10);dto.setOrderStatus(1);
		//预约暂停歇业配置接口 OrderOffUseConfigController
		OrderOffUseConfigDTO offdto = new OrderOffUseConfigDTO();
		offdto.setId("10001");
		offdto.setOffDate("2019-02-01");
		offdto.setWeekAlias("周五");
		//删除歇业Id
		String offId = "8aa781b26850a5be016850a5d1300000";
		//预约场次
		OrderPointConfigDTO pointDto = new OrderPointConfigDTO();
		pointDto.setPointName("午夜场");
		pointDto.setBeginTime("22:00:00");
		pointDto.setEndTime("23:30:00");
		pointDto.setBaseConfigId("10001");
		pointDto.setId("8aa781b26854999501685499a91b0002");
		//删除预约场次Id
		String pointId = "8aa781b26854999501685499a91b0002";
		//预约票型配置
				OrderTicketConfigDTO ticketDto = new OrderTicketConfigDTO();
				ticketDto.setTicketName("大人票");
				ticketDto.setBaseConfigId("10001");
				//删除预约票型id
				String ticketId = "8aa781b26850a5be016850a5d1510002";
				
				
		/*-------订单接口参数------*/
		//预约下单查询
		QueryParams qParamsDto = new QueryParams();
		OrderRecordDTO recode = new OrderRecordDTO();
		qParamsDto.setPage(1);
		qParamsDto.setSize(10);
		qParamsDto.setParams(recode);
		
		
		/*-------微信接口参数---------*/
		//微信端查询可预约票据池信息参数
		String getOrderPoolUrl = "/wechat/getPool";
		String getOrderPoolMethod = "getOrderPool";
		//根据票据池id获取场次信息参数
		String getOrderPointPoolUrl = "/wechat/getPointPool";
		String getOrderPointPoolId = "1";
		String getOrderPointPoolMethod = "getOrderPointPool";
		//微信下单预约参数
		OrderRecordDTO orderRecordDTO = new OrderRecordDTO();
		orderRecordDTO.setOrderUserAccount("孙悟空");
		orderRecordDTO.setOrderUserPhone("13540404872");
		orderRecordDTO.setOrderDate("2019-02-01");
		orderRecordDTO.setBaseConfigId("10001");
		orderRecordDTO.setVisitorName("老孙");
		orderRecordDTO.setVisitorNeededInfoType("身份证");
		orderRecordDTO.setVisitorNeededInfo("510113199103035918");
		orderRecordDTO.setTicketConfigId("8aa781b268546c9a0168546cabea0000");
		orderRecordDTO.setStatus(1);
		orderRecordDTO.setOrderBeginTime("09:00");
		orderRecordDTO.setOrderEndTime("18:00");
	
		OrderRecordVisitorDTO orderRecordVisitorDTO = new OrderRecordVisitorDTO();
		orderRecordVisitorDTO.setVisitorNeededInfoType("身份证");
		orderRecordVisitorDTO.setVisitorNeededInfo("510113199103035918");
		orderRecordVisitorDTO.setTicketConfigId("8aa781b268546c9a0168546cabea0000");
		List<OrderRecordVisitorDTO> visitorList = new ArrayList<>();
		visitorList.add(orderRecordVisitorDTO);
		orderRecordDTO.setVisitors(visitorList);
		//orderRecordDTO.setVisitors(orderRecordVisitorDTO);
		String wechatOrderMethod = "wechatOrder";
		String wechatOrdeUrl = "/wechat/order";
		
//		/*-------配置接口测试---------*/
//		//预约配置接口
//		orderBaseConfigGet(dto,false);
//		updateOrderBaseConfig(dto);
//		orderBaseConfigGet(dto,true);
//		resetTicketPool();
//		//预约暂停歇业配置接口 OrderOffUseConfigController		
//		addOffDate(offdto);
//		delOffDate(offId);
//		//预约场次配置接口 OrderPointConfigController
		//addAppointment(pointDto);
//		updateAppointment(pointDto);
//		deleteAppointment(pointId); 
//		//预约票型配置接口OrderTicketConfigController
//		addTicket(ticketDto); //添加票型不能添加预约配置id
//		delTicket(ticketId);
		//qeuryTicket();
//
//		/*---------订单接口测试----------*/
//		//预约下单查询接口 OrderRecordController
//		queryOrderRecordPage(qParamsDto);
//		
		
		/*----------微信接口测试----------*/
		//微信端查询可预约票据池信息getOrderPool
		junitGet(getOrderPoolUrl, getOrderPoolMethod);
		//根据票据池id获取场次信息 getOrderPointPool  fail
		// junitByString(getOrderPointPoolId, getOrderPointPoolUrl, getOrderPointPoolMethod); //没有内容
		//微信端下单预约 order  fail
		//junitByJson(orderRecordDTO, "/wechat/order", wechatOrderMethod);
		//微信端取消预约单 FAIL
		//junitByString("1", "/wechat/orderCancel", "wechatOrderCancel");
		//微信预约单列表
		//微信预约单详情
		//junitByString("1", "/wechat/orderDetails", "wechatOrderDetails");
	}
}
