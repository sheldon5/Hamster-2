package cn.coselding.hamster.email;

import cn.coselding.hamster.domain.Article;
import cn.coselding.hamster.domain.Comment;
import cn.coselding.hamster.domain.Guest;
import cn.coselding.hamster.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

/*
邮件发送工具类
 */
public class JavaMailWithAttachment {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private MimeMessage message;
	private Session session;
	private Transport transport;
	private String mailHost = "";
	private String sender_username = "";
	private String sender_password = "";
	private String mine = "";
	private Properties properties;
	private Config config;

	/*
         * 初始化方法
         */
	public JavaMailWithAttachment(boolean debug, String configFilename,Config config) {
		InputStream in = JavaMailWithAttachment.class.getClassLoader()
				.getResourceAsStream(configFilename);
		try {
			properties = new Properties();
			properties.load(in);
			this.mailHost = properties.getProperty("mail.smtp.host");
			this.sender_username = properties
					.getProperty("mail.sender.username");
			this.sender_password = properties
					.getProperty("mail.sender.password");
			this.config=config;
			this.mine = config.getUserEmail();
			in.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		session = Session.getInstance(properties);
		session.setDebug(debug);// 开启后有调试信息
		message = new MimeMessage(session);
		logger.info("JavaEmail组件初始化成功...");
	}

	/**
	 * 发送邮件
	 *
	 * @param subject
	 *            邮件主题
	 * @param sendHtml
	 *            邮件内容
	 * @param receiveUser
	 *            收件人地址
	 * @param attachment
	 *            附件
	 */
	public boolean doSendHtmlEmail(String subject, String sendHtml,
								String receiveUser,File attachment) {
		try {
			// 发件人
			InternetAddress from = new InternetAddress(sender_username);
			message.setFrom(from);

			// 收件人
			InternetAddress to = new InternetAddress(receiveUser);
			message.setRecipient(Message.RecipientType.TO, to);

			// 邮件主题
			// message.setSubject(subject);
			message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));

			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();

			// 添加邮件正文
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(sendHtml, "text/html;charset=UTF-8");
			multipart.addBodyPart(contentPart);

			// 添加附件的内容
			if (attachment != null) {
				BodyPart attachmentBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(attachment);
				attachmentBodyPart.setDataHandler(new DataHandler(source));

				// 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
				// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
				// sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
				// messageBodyPart.setFileName("=?GBK?B?" +
				// enc.encode(attachment.getName().getBytes()) + "?=");

				// MimeUtility.encodeWord可以避免文件名乱码
				attachmentBodyPart.setFileName(MimeUtility
						.encodeWord(attachment.getName()));
				multipart.addBodyPart(attachmentBodyPart);
			}

			// 将multipart对象放到message中
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();

			transport = session.getTransport("smtp");
			// smtp验证，就是你用来发邮件的邮箱用户名密码
			transport.connect(mailHost, sender_username, sender_password);
			// 发送
			transport.sendMessage(message, message.getAllRecipients());

			logger.info("send success!");
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//生成邮件内容
	public String getContent(String gname,String title,String articleUrl,String notRssUrl,boolean isNew){
		String operation = null;
		if(isNew)
			operation = "发表";
		else operation = "修改";
		String content = "<!doctype html>\n" +
				"<html>\n" +
				"<head>\n" +
				"<meta charset=\"utf-8\">\n" +
				"<title>Coselding博客订阅通知</title>\n" +
				"</head>\n" +
				"<p>尊敬的"+gname+",您好！</p>\n" +
				"<p>Coselding博客最新"+operation+"了《"+title+"》文章，如果感兴趣的可以<a href=\""+articleUrl+"\">点击此链接进行阅读</a>。</p>\n" +
				" <p>如需取消订阅，可<a href=\""+notRssUrl+"\">点击此链接取消订阅</a>。</p>\n" +
				"<body>\n" +
				"</body>\n" +
				"</html>";
		return content;
	}

	//发送订阅用户通知
	public void sendRSS(final Article article, final List<Guest> guests, final String contextPath, final boolean isNew){
		//延时操作，开启子线程
		new Thread(new Runnable() {
			public void run() {
				for(Guest guest:guests){
					String articleUrl = config.getServerHost()+contextPath+article.getStaticURL()+".html";
					String notRssUrl = config.getServerHost()+contextPath+"/rss.jsp/no?email="+guest.getGemail();
					String content = getContent(guest.getGname(),article.getTitle(),articleUrl,notRssUrl,isNew);
					boolean result = doSendHtmlEmail("Coselding博客",content,guest.getGemail(),null);
					if(!result){
						logger.error("用户："+guest+" 的邮件发送失败！");
					}else{
						logger.info("用户："+guest+" 的邮件发送成功");
					}
				}
			}
		}).start();
	}

	public void sendCommentNotice(final Guest guest, final Comment comment,final String contextPath){
		//延时操作，开启子线程
		new Thread(new Runnable() {
			public void run() {
				String url = config.getServerHost()+contextPath+"/manage/comment/wait";
				String content = getNoticeContent(guest,comment.getComcontent(),url);
				boolean result = doSendHtmlEmail("Coselding博客留言通知",content,mine,null);
				if(!result){
					logger.error("留言通知的邮件发送失败！");
				}else {
					logger.info("留言通知的邮件发送成功！");
				}
			}
		}).start();
	}

	//得到用户留言通知邮件通知
	public String getNoticeContent(Guest guest,String comcontent,String url){
		String content = "<!DOCTYPE html>\n" +
				"<html>\n" +
				"<head>\n" +
				"\t<title>Coselding博客留言通知</title>\n" +
				"</head>\n" +
				"<body>\n" +
				"\t<p>用户："+guest.getGname()+" ,邮箱："+guest.getGemail()+"&nbsp;的用户给您留了言。</p>\n" +
				"\t<p>留言内容为："+comcontent+"</p>\n" +
				"\t<p>若需查看详细信息，<a href=\""+url+"\">点击此链接可查看</a>。</p>\n" +
				"</body>\n" +
				"</html>";
		return content;
	}

	public static void main(String[] args) {
//		JavaMailWithAttachment se = new JavaMailWithAttachment(true,"MailServer.properties");
//		File affix = new File("D:\\1.txt");
//		se.doSendHtmlEmail("邮件主题", "邮件内容", "1098129797@qq.com", affix);
	}

}