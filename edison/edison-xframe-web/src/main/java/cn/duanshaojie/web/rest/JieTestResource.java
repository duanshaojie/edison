package cn.duanshaojie.web.rest;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.duanshaojie.util.mail.MailUtils;
import cn.duanshaojie.util.qcode.QRCodeUtil;

@RestController
@RequestMapping("test")
public class JieTestResource {
	private static final Logger logger = LoggerFactory.getLogger(JieTestResource.class);
	
	@Autowired
	private MailUtils mail;
	
	@RequestMapping("qrcode")
	@ResponseBody
	public void createQRCode(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam String content){
		try {
			BufferedImage bi = QRCodeUtil.encode1(content, "", "", false);
			bi.flush();
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
			ImageIO.write(bi, "png", imOut);
			InputStream fis = new ByteArrayInputStream(bs.toByteArray());
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			response.reset();
			response.addHeader("Content-Length", "" + buffer.length);
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("image/jpeg");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("sendMail")
	@ResponseBody
	public void sendMail(){
		String address = "1255889018@qq.com";
		try {
			mail.sendMali("测试", address, "<html><body><h3>测试成功!</h3></body></html>", null, null);
		} catch (Exception e) {
			logger.error("邮件发送错误");
		}
	}
}
