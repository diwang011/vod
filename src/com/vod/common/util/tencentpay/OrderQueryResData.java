package com.vod.common.util.tencentpay;

/**
 * User: rizenguo
 * Date: 2014/10/22
 * Time: 16:42
 */

/**
 * 提交Post数据给到API之后，API会返回XML格式的数据，这个类用来装这些数据
 */
public class OrderQueryResData
{
    //协议层
    private String return_code = "";
    private String return_msg = "";

    //协议返回的具体数据（以下字段在return_code 为SUCCESS 的时候有返回）
    private String appid = "";
    private String mch_id = "";
    private String nonce_str = "";
    private String sign = "";
    private String result_code = "";
    private String err_code = "";
    private String err_code_des = "";

    //业务返回的具体数据（以下字段在return_code 和result_code 都为SUCCESS 的时候有返回）
    private String openid = "";
    private String trade_type = "";
    private String trade_state = "";
    private String bank_type = "";
    private int total_fee = 0;
    private String transaction_id = "";
    private String out_trade_no = "";
    private String time_end = "";
    private String trade_state_desc = "";

    public String getReturn_code()
    {
        return return_code;
    }

    public void setReturn_code(String return_code)
    {
        this.return_code = return_code;
    }

    public String getReturn_msg()
    {
        return return_msg;
    }

    public void setReturn_msg(String return_msg)
    {
        this.return_msg = return_msg;
    }

    public String getAppid()
    {
        return appid;
    }

    public void setAppid(String appid)
    {
        this.appid = appid;
    }

    public String getMch_id()
    {
        return mch_id;
    }

    public void setMch_id(String mch_id)
    {
        this.mch_id = mch_id;
    }

    public String getNonce_str()
    {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str)
    {
        this.nonce_str = nonce_str;
    }

    public String getSign()
    {
        return sign;
    }

    public void setSign(String sign)
    {
        this.sign = sign;
    }

    public String getResult_code()
    {
        return result_code;
    }

    public void setResult_code(String result_code)
    {
        this.result_code = result_code;
    }

    public String getErr_code()
    {
        return err_code;
    }

    public void setErr_code(String err_code)
    {
        this.err_code = err_code;
    }

    public String getErr_code_des()
    {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des)
    {
        this.err_code_des = err_code_des;
    }

    public String getOpenid()
    {
        return openid;
    }

    public void setOpenid(String openid)
    {
        this.openid = openid;
    }

    public String getTrade_type()
    {
        return trade_type;
    }

    public void setTrade_type(String trade_type)
    {
        this.trade_type = trade_type;
    }

    public String getTrade_state()
    {
        return trade_state;
    }

    public void setTrade_state(String trade_state)
    {
        this.trade_state = trade_state;
    }

    public String getBank_type()
    {
        return bank_type;
    }

    public void setBank_type(String bank_type)
    {
        this.bank_type = bank_type;
    }

    public int getTotal_fee()
    {
        return total_fee;
    }

    public void setTotal_fee(int total_fee)
    {
        this.total_fee = total_fee;
    }

    public String getTransaction_id()
    {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id)
    {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no()
    {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no)
    {
        this.out_trade_no = out_trade_no;
    }

    public String getTime_end()
    {
        return time_end;
    }

    public void setTime_end(String time_end)
    {
        this.time_end = time_end;
    }

    public String getTrade_state_desc()
    {
        return trade_state_desc;
    }

    public void setTrade_state_desc(String trade_state_desc)
    {
        this.trade_state_desc = trade_state_desc;
    }

}
