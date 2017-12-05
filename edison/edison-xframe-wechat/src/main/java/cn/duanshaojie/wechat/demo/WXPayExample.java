package cn.duanshaojie.wechat.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.wxpay.sdk.WXPay;

import cn.duanshaojie.wechat.demo.MyConfig;
/**
 * 
 * <b>类名：</b>WXPayExample.java<br>
 * <p><b>标题：</b>微信扫码支付2</p>
 * <p><b>描述：</b>需要pom文件引入（此方法为官方SDK）</p>
 * @author <font color='blue'>edison_dsj@163.com</font>
 * @date 2017年12月5日
 *
 * <p><b>桃之夭夭,灼灼其华</b></p>
 */
public class WXPayExample {
	public static void main(String[] args) {
        MyConfig config = null;
        WXPay wxpay = null;
		try {
			config = new MyConfig();
			wxpay = new WXPay(config);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

        Map<String, String> data = new HashMap<String, String>();
        data.put("body", "腾讯充值中心-QQ会员充值");
        data.put("out_trade_no", createUUID());
        data.put("device_info", "");
        data.put("fee_type", "CNY");
        data.put("total_fee", "1");
        data.put("spbill_create_ip", "123.12.12.123");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        data.put("product_id", "12");

        try {
            Map<String, String> resp = wxpay.unifiedOrder(data);
            String payUrl = resp.get("code_url");//生成二维码即可
            System.out.println(payUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private static String createUUID() {
		String machineId = "Y"+1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        return machineId + String.format("%015d", hashCodeV);
	}
}
