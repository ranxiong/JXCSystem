package com.friday.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.friday.service.impl.StockInServiceImpl;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import com.friday.service.StockInService;

public class RKQueryController implements Controller {

	/**
	 * 查询入库列表
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			Date start = starttime.isEmpty() ? null : Date.valueOf(starttime);
			Date end = endtime.isEmpty() ? null : Date.valueOf(endtime);
			String orderId = request.getParameter("orderid");
			
			StockInService stockInService = new StockInServiceImpl();
			
			List<Object> list = stockInService.queryStockIn(start, end, orderId);
			
			int pagecurrent = 0, pagecount = (list.size()-1) / 10 + 1;
			
			String page = request.getParameter("page");
			
			if (page!=null) {
				pagecurrent = Integer.parseInt(page);
			}
			
			list = list.subList(pagecurrent * 10, (pagecurrent*10 + 10) > list.size() ? list.size() : (pagecurrent*10 + 10));
			
			model.put("result", list);
			
			model.put("starttime", starttime);
			model.put("endtime", endtime);
			model.put("orderId", orderId);
			model.put("pagecurrent", pagecurrent);
			model.put("pagecount", pagecount);
			return new ModelAndView("storage_record_query", model);
		} catch (Exception e) {
			model.put("error", "操作失败");
			e.printStackTrace();
			return new ModelAndView("error", model);
		}
	}

}
