package cn.duanshaojie.web.test;

import java.io.File;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.duanshaojie.baidu.ocr.BaiduOCR;
import cn.duanshaojie.util.tesseract.TesseractUtil;
import cn.duanshaojie.web.app.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class WebUtil {
	
	@Autowired
	private BaiduOCR ocr;
	
	private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);
	@Test
	public void tesseractTest(){
		File f = new File("C:\\Users\\dev\\Desktop\\file.png");
		try {
			String str = TesseractUtil.parsedText(f);
			System.out.println(str);
		} catch (Exception e) {
			System.err.println("错了");
		}
	}
	
	@Test
	public void baiduOcrTest(){
		String path = "C:\\Users\\dev\\Desktop\\file.png";
		JSONObject obj = null;
		try {
			obj = (JSONObject)ocr.getMessage(path, "groupPolicyRule", null);
		} catch (Exception e) {
			logger.error("读取错误",e);
		}
		System.out.println(obj.toString());
	}
}
