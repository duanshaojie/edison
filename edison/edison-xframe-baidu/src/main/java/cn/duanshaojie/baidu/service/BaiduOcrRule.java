package cn.duanshaojie.baidu.service;

import org.json.JSONObject;

public interface BaiduOcrRule {
	Object getRuleMessage(JSONObject obj, Object filter);
}
