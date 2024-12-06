package com.cloudestudio.hosgetserver.controller;

import com.cloudestudio.hosgetserver.model.MedicineAllBean;
import com.cloudestudio.hosgetserver.model.MedicineOrderBean;
import com.cloudestudio.hosgetserver.model.MonthCountBean;
import com.cloudestudio.hosgetserver.model.OrderBean;
import com.cloudestudio.hosgetserver.service.MedicineService;
import com.cloudestudio.hosgetserver.service.OrderService;
import com.cloudestudio.hosgetserver.webTools.StringUtil;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.cloudestudio.hosgetserver.webTools.WebServerResponse;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
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
    @Autowired
    private MedicineService medicineService;

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

    /**
     * 条件查询总销售额
     * @param queryType
     * @param response
     * @throws IOException
     */
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

    @RequestMapping("queryMonthOrder")
    public void queryMonthAllOrder(HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        List<MonthCountBean> monthCountBeanList = orderService.queryMonthAllOrder();
        if(monthCountBeanList!=null){
            System.out.println(TimeUtil.GetTime(true)+" 年订单量详情查询成功！结果:"+monthCountBeanList);
            response.getWriter().write(gson.toJson(WebServerResponse.success("销售金额查询成功！",monthCountBeanList)));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" 销售金额查询异常！结果:null");
            response.getWriter().write(gson.toJson(WebServerResponse.failure("销售金额查询异常!")));
        }
    }

    /**
     * 查询订单
     * @param queryType
     * @param response
     * @throws IOException
     */
    @RequestMapping("/queryTypeOrderInfo")
    public void queryTypeOrderInfo(@RequestParam("queryType") String queryType,HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        List<MedicineOrderBean> requestOrderBeanList = switch (queryType) {
            case "Valid" -> orderService.querySellOrderInfo();
            case "UnValid" -> orderService.queryUnSellOrderInfo();
            default -> null;
        };
        if(requestOrderBeanList!=null){
            System.out.println(TimeUtil.GetTime(true)+" 订单数据查询成功！查询参数:"+queryType+" 结果:"+requestOrderBeanList);
            response.getWriter().write(gson.toJson(WebServerResponse.success("销售金额查询成功！",requestOrderBeanList)));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" 订单数据查询异常！查询参数:"+queryType+" 结果:"+ null);
            response.getWriter().write(gson.toJson(WebServerResponse.failure("订单数据查询异常!")));
        }
    }

    /**
     * 创建订单条目
     * @param medicine_code
     * @param response
     * @throws IOException
     */
    @RequestMapping("createOrderItem")
    public void createOrderItem(@RequestParam("order_uid") String order_uid,
                                @RequestParam("medicine_code") String medicine_code,
                                HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        System.out.println(TimeUtil.GetTime(true)+" 创建订单条目-查询参数:"+medicine_code);
        MedicineAllBean requestOldestMedicine=medicineService.queryOldestMedicWareHouseInfo(medicine_code);
        String createTime=TimeUtil.GetTime(true);
        if(requestOldestMedicine==null){
            System.out.println(TimeUtil.GetTime(true)+" 药剂无库存！查询参数:"+medicine_code);
            response.getWriter().write(gson.toJson(WebServerResponse.failure("药剂:"+medicine_code+" 无库存!")));
        }else{
            //有库存
            System.out.println(TimeUtil.GetTime(true)+" 药剂:"+medicine_code+" 最早入库:"+requestOldestMedicine);
            Map<String,Object> createItemMap=new HashMap<>();
            createItemMap.put("order_uid",order_uid);
            createItemMap.put("medicine_code",medicine_code);
            createItemMap.put("medicine_batch_number",requestOldestMedicine.getMedicine_batch_number());
            String timeTemp=orderService.queryOrderTime(order_uid);
            if(!StringUtil.isEmptyOrNull(timeTemp)){
                //订单已创建--逻辑有问题--待定！！
                OrderBean orderBeanTemp=orderService.queryOrderItem(createItemMap);
                if(orderBeanTemp!=null){
                    //直接更新订单的数量
                    createItemMap.put("order_quantity",Integer.parseInt(orderBeanTemp.getOrder_quantity())+1);
                    boolean upOrderItemQuantity=orderService.updateOrderQuantity(createItemMap);
                    if(upOrderItemQuantity){
                        List<MedicineOrderBean> orderResponseList=orderService.queryOrderAllItem(order_uid);
                        if(orderResponseList==null|| orderResponseList.isEmpty()){
                            System.out.println(TimeUtil.GetTime(true)+" 更新数量——订单:"+order_uid+" 产品:"+medicine_code+"创建成功！但返回异常!");
                            response.getWriter().write(gson.toJson(WebServerResponse.failure("订单:"+order_uid+" 产品:"+medicine_code+"返回异常!")));
                        }else{
                            System.out.println(TimeUtil.GetTime(true)+" 更新数量——订单:"+order_uid+" 产品:"+medicine_code+"创建成功!"+createItemMap);
                            response.getWriter().write(gson.toJson(WebServerResponse.success("订单创建成功!",orderResponseList)));
                        }
                    }else{
                        System.out.println(TimeUtil.GetTime(true)+" 更新数量——订单:"+order_uid+" 产品:"+medicine_code+"创建异常!");
                        response.getWriter().write(gson.toJson(WebServerResponse.failure("订单:"+order_uid+" 产品:"+medicine_code+"创建异常!")));
                    }
                }else{
                    System.out.println(TimeUtil.GetTime(true)+" orderBeanTemp为空！");
                    createItemMap.put("order_time",createTime);
                    createItemMap.put("order_quantity",1);
                }
            }else{
                //订单未创建
                createItemMap.put("order_time",createTime);
                createItemMap.put("order_quantity",1);
                createItemMap.put("order_amount",requestOldestMedicine.getMedicine_retail());

                boolean createKey=orderService.addOrderItem(createItemMap);
                if(createKey){
                    System.out.println("订单:"+order_uid+" 产品:"+medicine_code+"创建成功!");
                    List<MedicineOrderBean> orderResponseList=orderService.queryOrderAllItem(order_uid);
                    if(orderResponseList==null|| orderResponseList.isEmpty()){
                        System.out.println(TimeUtil.GetTime(true)+" 订单:"+order_uid+" 产品:"+medicine_code+"创建成功！但返回异常!");
                        response.getWriter().write(gson.toJson(WebServerResponse.failure("订单:"+order_uid+" 产品:"+medicine_code+"返回异常!")));
                    }else{
                        System.out.println(TimeUtil.GetTime(true)+" 订单:"+order_uid+" 产品:"+medicine_code+"创建成功!"+createItemMap);
                        response.getWriter().write(gson.toJson(WebServerResponse.success("订单创建成功!",orderResponseList)));
                    }
                }else{
                    System.out.println(TimeUtil.GetTime(true)+" 订单:"+order_uid+" 产品:"+medicine_code+"创建异常!");
                    response.getWriter().write(gson.toJson(WebServerResponse.failure("订单:"+order_uid+" 产品:"+medicine_code+"创建异常!")));
                }
            }
        }
    }

    @RequestMapping("/freshOrderStatus")
    public void freshOrderStatus(@RequestParam("order_uid") String order_uid,
                                 @RequestParam("seller") String seller,
                                 @RequestParam("applyType") String applyType,
                                 HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        //String freshBean=orderService.queryOrderTime(order_uid);
        Map<String,Object> freshOrderStatusMap=new HashMap<>();
        freshOrderStatusMap.put("order_uid",order_uid);
        freshOrderStatusMap.put("order_seller",seller);
        freshOrderStatusMap.put("sell_type",applyType);
        System.out.println(TimeUtil.GetTime(true)+" 订单状态更新请求参数:"+freshOrderStatusMap);
        if(StringUtil.isEmptyOrNull(orderService.queryOrderTime(order_uid))){
            System.out.println(TimeUtil.GetTime(true)+" 订单:"+order_uid+"不存在!");
            response.getWriter().write(gson.toJson(WebServerResponse.failure("订单:"+order_uid+"不存在!")));
        }else{
            boolean freshKey=orderService.updateOrderStatus(freshOrderStatusMap);
            if(freshKey){
                System.out.println(TimeUtil.GetTime(true)+" 订单:"+order_uid+"结算完成!");
                response.getWriter().write(gson.toJson(WebServerResponse.success("订单:"+order_uid+"结算完成!")));
            }else{
                System.out.println(TimeUtil.GetTime(true)+" 订单:"+order_uid+"结算异常!");
                response.getWriter().write(gson.toJson(WebServerResponse.failure("订单:"+order_uid+"结算异常!")));
            }
        }
    }
    /***********************查询逻辑:MySql库********************/
}
