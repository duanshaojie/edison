package cn.duanshaojie.web.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class JieTestResource {
	
	@RequestMapping("qrcode")
	@ResponseBody
	public String createQRCode(HttpServletRequest request,@RequestParam String content){
		return content;
	}
}
