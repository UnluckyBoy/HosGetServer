package com.cloudestudio.hosgetserver.controller;

import com.cloudestudio.hosgetserver.model.MedicineOrderBean;
import com.cloudestudio.hosgetserver.model.OrderBean;
import com.cloudestudio.hosgetserver.service.MedicineService;
import com.cloudestudio.hosgetserver.service.OrderService;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.cloudestudio.hosgetserver.webTools.WebServerResponse;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Class OrderController
 * @Author Create By Matrix·张
 * @Date 2024/11/29 下午1:28
 * 订单控制类
 */
@Controller
@RequestMapping("/OrderApi")
public class OrderController {
    @Autowired
    private OrderService orderService;

    private static final Gson gson=new Gson();

    /***********************查询逻辑:MySql库********************/
    /**
     * 销售未出库查询
     * @param response
     * @throws IOException
     */
    @RequestMapping("/queryOrderOutWareHouse")
    public void queryOrderOutWareHouse(HttpServletResponse response) throws IOException {
        List<MedicineOrderBean> requestList=orderService.queryOrderOutWareHouse();
        response.setContentType("application/json;charset=UTF-8");
        if (requestList.isEmpty()) {
            response.getWriter().write(gson.toJson(WebServerResponse.failure("未存在销售未出库!")));
        }else{
            Map<String,Object> responseMap=new HashMap<>();
            responseMap.put("orderUnOutList", requestList);
            System.out.println(TimeUtil.GetTime(true)+" 销售未出库："+responseMap);
            response.getWriter().write(gson.toJson(WebServerResponse.success("查询成功",responseMap)));
        }
    }

    /**
     * 销售出库
     * @param orderUid
     * @param medicineCode
     * @param batchNumber
     * @param response
     * @throws IOException
     */
    @RequestMapping("/upOrderOutWareHouse")
    public void upOrderOutWareHouse(@RequestParam("orderUid") String orderUid,
                                    @RequestParam("medicineCode") String medicineCode,
                                    @RequestParam("batchNumber") String batchNumber,
                                    HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("order_uid",orderUid);
        requestMap.put("medicine_code",medicineCode);
        requestMap.put("medicine_batch_number",batchNumber);
        System.out.println(TimeUtil.GetTime(true)+" 销售出库请求参数:"+requestMap);
        boolean result=orderService.upOrderOutWareHouse(requestMap);
        if(result){
            System.out.println(TimeUtil.GetTime(true)+" 销售出库成功！参数:"+requestMap);
            response.getWriter().write(gson.toJson(WebServerResponse.success("销售出库成功！")));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" 销售出库失败！参数:"+requestMap);
            response.getWriter().write(gson.toJson(WebServerResponse.failure("销售出库异常!")));
        }
    }

    /**
     * 查询销售数量
     * @param queryType
     * @param response
     * @throws IOException
     */
    @RequestMapping("/querySell")
    public void querySell(@RequestParam("queryType") String queryType,HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        List<MedicineOrderBean> medicineOrderBeanList = switch (queryType) {
            case "Today" -> orderService.queryCurrentDaySell();
            case "Yesterday" -> orderService.queryYesterdaySell();
            case "LastWeek" -> orderService.queryLastWeekSell();
            case "LastMonth" -> orderService.queryLastMonthSell();
            case "CurrentYear" -> orderService.queryCurrentYearSell();
            case "AllSell" -> orderService.queryAllSell();
            default -> null;
        };
        if(medicineOrderBeanList!=null){
            if(medicineOrderBeanList.isEmpty()){
                System.out.println(TimeUtil.GetTime(true)+" 销售金额查询成功！查询参数:"+queryType+" 结果为空:"+medicineOrderBeanList);
                response.getWriter().write(gson.toJson(WebServerResponse.success("未发生销售数据！",medicineOrderBeanList)));
            }else{
                System.out.println(TimeUtil.GetTime(true)+" 销售金额查询成功！查询参数:"+queryType+" 结果:"+medicineOrderBeanList);
                response.getWriter().write(gson.toJson(WebServerResponse.success("销售金额查询成功！",medicineOrderBeanList)));
            }
        }else{
            System.out.println(TimeUtil.GetTime(true)+" 销售金额查询异常！查询参数:"+queryType+" 结果:"+medicineOrderBeanList);
            response.getWriter().write(gson.toJson(WebServerResponse.failure("销售金额查询异常!")));
        }
    }

    @RequestMapping("/queryAmount")
    public void queryAmount(@RequestParam("queryType") String queryType,HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        OrderBean requestOrderBean = switch (queryType) {
            case "Today" -> orderService.queryCurrentDayAmount();
            case "Yesterday" -> orderService.queryYesterdayAmount();
            case "LastWeek" -> orderService.queryLastWeekAmount();
            case "LastMonth" -> orderService.queryLastMonthAmount();
            case "CurrentYear" -> orderService.queryCurrentYearAmount();
            case "AllSell" -> orderService.queryAllAmount();
            default -> null;
        };
        if(requestOrderBean!=null){
            System.out.println(TimeUtil.GetTime(true)+" 销售金额查询成功！查询参数:"+queryType+" 结果:"+requestOrderBean);
            response.getWriter().write(gson.toJson(WebServerResponse.success("销售金额查询成功！",requestOrderBean)));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" 销售金额查询异常！查询参数:"+queryType+" 结果:"+requestOrderBean);
            response.getWriter().write(gson.toJson(WebServerResponse.failure("销售金额查询异常!")));
        }
    }
    /***********************查询逻辑:MySql库********************/
}
