/**
 * wdk.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.nianien.test.jooq;


import com.nianien.jooq.Match;
import com.nianien.jooq.Operator;

import java.util.Date;

import lombok.Data;


/**
 * 异常货品查询
 *
 * @author zhangyong
 * @date 2021/01/21
 */
@Data
public class GoodsQuery {


    /**
     * 数据id
     */
    private Long id;

    /**
     * 业务标识
     */
    private Integer bizType;

    /**
     * 操作人
     */
    private String modifier;

    /**
     * 环境标识
     */
    @Match(disable = true)
    private String env;

    /**
     * 任务单号
     */
    private String orderCode;

    /**
     * 运单号
     */
    private String mailNo;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 提报时间开始
     */
    @Match(name = "out_submit_time", op = Operator.GE)
    private Date submitTimeBegin;

    /**
     * 提报时间结束
     */
    @Match(name = "out_submit_time", op = Operator.LE)
    private Date submitTimeEnd;

    /**
     * 行业id
     */
    private Long industryId;

    /**
     * 行业名称
     */
    private String industryName;

    /**
     * 发货仓code
     */
    private String srcStoreCode;

    /**
     * 发货仓name
     */
    private String srcStoreName;

    /**
     * 退货仓code
     */
    private String retreatStoreCode;

    /**
     * 退货仓name
     */
    private String retreatStoreName;

    /**
     * 异常货品任务状态
     */
    private Integer bizOrderStatus;

    /**
     * 顾客申诉结论
     */
    private String appealConclusion;


}