package cn.duanshaojie.baidu.rules;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import cn.duanshaojie.baidu.service.BaiduOcrRule;
@Service("defaultRule")
public class DefaultRule implements BaiduOcrRule {

	@Override
	public Object getRuleMessage(JSONObject obj, Object filter) {
		return obj;
	}

}
