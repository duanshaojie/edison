package cn.duanshaojie.wechat.pay;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.duanshaojie.util.httppost.HttpUtil;
import cn.duanshaojie.util.xml.XMLUtil;

/**
 * 
 * <b>类名：</b>WechatPay.java<br>
 * <p><b>标题：</b>微信扫码支付1</p>
 * <p><b>描述：</b>TODO</p>
 * @author <font color='blue'>edison_dsj@163.com</font>
 * @date 2017年12月5日
 *
 * <p><b>桃之夭夭,灼灼其华</b></p>
 */
public class WechatPay {
	private static final Logger logger = LoggerFactory.getLogger(WechatPay.class);
	
	private String UFDODER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	private String APP_ID="";
	private String MCH_ID="";
	private String API_KEY="";
	private String NOTIFY_URL = "";//回调地址
	
	public String createPayment(){
		String quotationNumber = createUUID();
        String nonce_str = createNonceStr();
        String spbill_create_ip = PayCommonUtil.getConfigIp();  
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();  
        packageParams.put("appid", APP_ID);  
        packageParams.put("mch_id", MCH_ID);  
        packageParams.put("nonce_str", nonce_str);  
        packageParams.put("body", "瓜子花生巧克力");  //商品名称
        packageParams.put("out_trade_no", quotationNumber); //本地生成的唯一订单号 
        packageParams.put("total_fee", "1"); //实际价格 - 分为单位 
        packageParams.put("spbill_create_ip", spbill_create_ip);  //发起支付的ip
        packageParams.put("notify_url", NOTIFY_URL);  
        packageParams.put("trade_type", "NATIVE");  
  
        String sign = PayCommonUtil.createSign("UTF-8", packageParams,API_KEY);  
        packageParams.put("sign", sign);//验签
        
        String requestXML = PayCommonUtil.getRequestXml(packageParams);  
        logger.info("请求的request = {}",requestXML);
        String resXml = HttpUtil.postData(UFDODER_URL, requestXML);
        logger.info("请求的response = {}",resXml);
        
        Map map = null;
		try {
			map = XMLUtil.doXMLParse(resXml);
		} catch (Exception e) {
			logger.error("XML解析错误");
		} 
        return (String) map.get("code_url");//二位码地址，此处只需生成二维码即可
	}

	private String createUUID() {
		String machineId = "Y"+1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        return machineId + String.format("%015d", hashCodeV);
	}

	private String createNonceStr() {
        String currTime = PayCommonUtil.getCurrTime();  
        String strTime = currTime.substring(8, currTime.length());  
        String strRandom = PayCommonUtil.buildRandom(4) + "";		
        return strTime + strRandom;
	}
}
