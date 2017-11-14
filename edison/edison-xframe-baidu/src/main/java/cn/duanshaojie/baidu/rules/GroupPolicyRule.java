package cn.duanshaojie.baidu.rules;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.duanshaojie.baidu.service.BaiduOcrRule;

@Service("groupPolicyRule")
public class GroupPolicyRule implements BaiduOcrRule {
	
	private static final Logger logger = LoggerFactory.getLogger(GroupPolicyRule.class);
	
	@Override
	public Object getRuleMessage(JSONObject obj, Object array) {
		logger.info("BaiduOcrRule --> GroupPolicyRule-->begin");
		JSONObject result = new JSONObject();
		JSONArray words = (JSONArray) obj.get("words_result");
		for(int i=0;i<words.length();i++){
			JSONObject word = words.getJSONObject(i); 
			JSONObject location =  (JSONObject) word.get("location");
			String value = word.get("words").toString();
			String left = location.get("left").toString();
			String top = location.get("top").toString();
			String width = location.get("width").toString();
			String height = location.get("height").toString();
			if(Integer.valueOf(left)>517&&Integer.valueOf(top)>366
					&&Integer.valueOf(top)<366+Integer.valueOf(height)
					&&Integer.valueOf(left)<517+Integer.valueOf(width)){
				result.put("投保人", value);
			}
		}
		return result;
	}
}
