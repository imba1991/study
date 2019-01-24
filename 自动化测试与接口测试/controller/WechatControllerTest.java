package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.collections.functors.FalsePredicate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.htwz.BmWechatBooterApplication;
import com.htwz.business.tickeorder.dto.OrderRecordDTO;
import com.htwz.business.tickeorder.dto.OrderRecordVisitorDTO;
import com.htwz.business.wechat.order.controller.WechatOrderController;
import com.htwz.common.controller.HtControllerAdvice;
@RunWith(SpringRunner.class)
@SpringBootTest(classes=BmWechatBooterApplication.class)
public class WechatControllerTest extends WechatOrderController {
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
	

	
	

	
	@Override
	public String getOrderPool() {
		// TODO Auto-generated method stub
		return super.getOrderPool();
	}
	
	




	@Override
	public String order(OrderRecordDTO dto) {
		// TODO Auto-generated method stub
		return super.order(dto);
	}


	@Override
	public String getOrderPointPool(String poolId) {
		// TODO Auto-generated method stub
		return super.getOrderPointPool(poolId);
	}



	@Override
	public String orderCancel(String orderRecordId) {
		// TODO Auto-generated method stub
		return super.orderCancel(orderRecordId);
	}






	@Override
	public String orderList(Integer page, Integer size) {
		// TODO Auto-generated method stub
		return super.orderList(page, size);
	}






	@Override
	public String orderDatails(String orderRecordId) {
		// TODO Auto-generated method stub
		return super.orderDatails(orderRecordId);
	}






	@Test
	@Rollback()
	@Transactional
	public void testWechatController() throws Exception {
		//微信下单预约参数
				OrderRecordDTO orderRecordDTO = new OrderRecordDTO();
//				orderRecordDTO.setOrderUserAccount("孙悟空");
//				orderRecordDTO.setOrderUserPhone("13540404872");
				orderRecordDTO.setOrderDate("2019-01-29");
				orderRecordDTO.setBaseConfigId("10001");
//				orderRecordDTO.setVisitorName("老孙");
//				orderRecordDTO.setVisitorNeededInfoType("身份证");
//				orderRecordDTO.setVisitorNeededInfo("510113199103035918");
//				orderRecordDTO.setTicketConfigId("8aa781b268546c9a0168546cabea0000");
//				orderRecordDTO.setStatus(1);
//				orderRecordDTO.setOrderBeginTime("09:00");
//				orderRecordDTO.setOrderEndTime("18:00");
				orderRecordDTO.setTicketPointPoolId("8aa781b268739154016873916e350013");
				OrderRecordVisitorDTO orderRecordVisitorDTO = new OrderRecordVisitorDTO();
//				orderRecordVisitorDTO.setVisitorNeededInfoType("身份证");
//				orderRecordVisitorDTO.setVisitorNeededInfo("510113199103035918");
//				orderRecordVisitorDTO.setTicketConfigId("8aa781b268546c9a0168546cabea0000");
				orderRecordVisitorDTO.setVisitorName("老王");
				orderRecordVisitorDTO.setVisitorNeededInfo("511028198711100512");
				orderRecordVisitorDTO.setVisitorNeededInfoType("身份证");
				orderRecordVisitorDTO.setTicketConfigId("8aa781b268549cdb0168549cef9b0002");
				List<OrderRecordVisitorDTO> visitorList = new ArrayList<>();
				visitorList.add(orderRecordVisitorDTO);
				orderRecordDTO.setVisitors(visitorList);
				
				
				System.out.println("getOrderPool---------"+getOrderPool());
				//System.out.println("order--------"+order(orderRecordDTO));
			
				System.out.println("getOrderPointPool-------"+getOrderPointPool("8aa781b268739154016873916e300011"));
				System.out.println("orderList------"+orderList(1, 10));
				System.out.println("orderDatails------"+orderDatails("8aa781b26874ad33016874ad45530000"));
				//System.out.println("orderCancel------"+orderCancel("8aa781b26874ad33016874ad45530000"));
	}
}
