package com.vod.common.util.alipay;

public enum AlipayTradeOrderStatus
{
    TRADE_ERROR_OMNI, // 非支付宝状态，omni系统中自定义状态
    WAIT_BUYER_PAY,  //等待买家付款
    WAIT_SELLER_SEND_GOODS, //买家已付款，等待卖家发货
    WAIT_BUYER_CONFIRM_GOODS, //卖家已发货，等待买家确认
    TRADE_FINISHED, //交易成功结束
    TRADE_CLOSED, //交易中途关闭（已结束，未成功完成）
    WAIT_SYS_CONFIRM_PAY, //支付宝确认买家银行汇款中，暂勿发货
    WAIT_SYS_PAY_SELLER, //买家确认收货，等待支付宝打款给卖家
    TRADE_REFUSE, //立即支付交易拒绝 
    TRADE_REFUSE_DEALING, //立即支付交易拒绝中 
    TRADE_CANCEL, //立即支付交易取消 
    TRADE_PENDING, //等待卖家收款 
    TRADE_SUCCESS, //支付成功  
    BUYER_PRE_AUTH, //买家已付款（语音支付） 
    COD_WAIT_SELLER_SEND_GOODS, //等待卖家发货（货到付款） 
    COD_WAIT_BUYER_PAY, //等待买家签收付款（货到付款）  
    COD_WAIT_SYS_PAY_SELLER;//签收成功等待系统打款给卖家（货到付款）
}
