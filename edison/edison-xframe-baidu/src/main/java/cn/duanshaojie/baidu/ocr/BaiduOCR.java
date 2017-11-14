package cn.duanshaojie.baidu.ocr;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.baidu.aip.ocr.AipOcr;
import cn.duanshaojie.baidu.service.BaiduOcrRule;
import cn.duanshaojie.util.context.ApplicationContextUtils;

/**
 * <b>类名：</b>BaiduOCR.java<br>
 * <p><b>标题：</b>百度文字识别</p>
 * <p><b>描述：</b>文字识别，带位置</p>
 * @author <font color='blue'>edison_dsj@163.com</font>
 * @date  2017-11 
 * 
 * 桃之夭夭,灼灼其华
 */

@Component
public class BaiduOCR {
	@Value(value="${ocr.appid}")
	private String APP_ID;
	
	@Value(value="${ocr.apikey}")
	private String API_KEY;
	
	@Value(value="${ocr.secretkey}")
	private String SECRET_KEY;
	    
    public Object getMessage(String path,String bean, JSONArray array) throws Exception{
    	 // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
        
        //自定义参数
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("recognize_granularity", "small");//是否定位单字符位置 big:不定位-默认值 ;small:定位

        // 调用接口
        JSONObject res = client.general(path, options);//高精度-accurateGeneral

        BaiduOcrRule rule = ApplicationContextUtils.getBean(bean, BaiduOcrRule.class);
		return rule.getRuleMessage(res, array);
    }
}
