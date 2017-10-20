package cn.duanshaojie.util.mail;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sun.mail.util.MailSSLSocketFactory;

@Service
public class MailUtils {
	@Value(value = "${email.accountname}")
	private String AccountName;
	@Value(value = "${email.key}")
	private String Key;
	@Value(value = "${email.host}")
	private String Host;
    
    public void sendMali(String subject,String email,String htmlContent,String fielUrl,String picUrl) throws Exception{
		//跟smtp服务器建立一个连接
        Properties p = new Properties();
        // 开启debug调试，以便在控制台查看
        p.setProperty("mail.debug", "true");
        p.setProperty("mail.host", "smtp.mxhichina.com");//指定邮件服务器，默认端口 25
        p.setProperty("mail.smtp.auth", "true");//要采用指定用户名密码的方式去认证
        // 发送邮件协议名称
        p.setProperty("mail.transport.protocol", "smtp");

        // 开启SSL加密，否则会失败
        MailSSLSocketFactory sf = null;
		try {
			sf = new MailSSLSocketFactory();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        sf.setTrustAllHosts(true);
        p.put("mail.smtp.ssl.enable", "true");
        p.put("mail.smtp.ssl.socketFactory", sf);

        // 创建session
        Session session = Session.getInstance(p);

        // 通过session得到transport对象
        Transport ts = session.getTransport();

        // 连接邮件服务器：邮箱类型，帐号，授权码代替密码（更安全）
        ts.connect(Host,AccountName,Key);
        // 后面的字符是授权码，不能用qq密码

        //声明一个Message对象(代表一封邮件),从session中创建
        MimeMessage msg = new MimeMessage(session);
        //邮件信息封装
        //1发件人
        msg.setFrom( new InternetAddress(AccountName) );
        //2收件人
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email) );
        //3邮件内容:主题、内容
        msg.setSubject(subject);

        //添加附件部分
        MimeMultipart mm = new MimeMultipart();

        //邮件内容部分1---文本内容
        MimeBodyPart body0 = new MimeBodyPart(); //邮件中的文字部分
        body0.setContent(htmlContent,"text/html;charset=utf-8");
        mm.addBodyPart(body0);

        //邮件内容部分2---附件1
        if(fielUrl!=null){
            MimeBodyPart body1 = new MimeBodyPart(); //附件1
            FileDataSource fileDataSource =  new FileDataSource(fielUrl);
            body1.setDataHandler(new DataHandler(fileDataSource));//./代表项目根目录下
            body1.setFileName(MimeUtility.encodeText(fileDataSource.getName()));//中文附件名，解决乱码
            mm.addBodyPart(body1);
        }
        //邮件内容部分3---附件2---图片
        if(picUrl!=null){
        	 MimeBodyPart body2 = new MimeBodyPart(); //附件1
             FileDataSource fileDataSource =  new FileDataSource(picUrl);
             body2.setDataHandler(new DataHandler(fileDataSource));//./代表项目根目录下
             body2.setFileName("ceshi.png");
             mm.addBodyPart(body2);
        }
        msg.setContent(mm);
        // 发送邮件
        ts.sendMessage(msg,msg.getAllRecipients());
        ts.close();
    }
}
