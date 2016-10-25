package cn.coselding.hamster.filter;

import cn.coselding.hamster.filter.support.BufferedResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**将http响应进行压缩后输出的过滤器
 * ！！！实际开发千万注意大文件操作有可能是内存爆满！！！
 * @author 宇强
 *
 */
public class GzipFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
						 FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		//设置缓冲response
		BufferedResponse myResponse = new BufferedResponse(response);
		chain.doFilter(request, myResponse);
		//捕获response中的缓冲
		byte[] buffer = myResponse.getBuffer();
		//将response中的数据进行压缩
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		GZIPOutputStream gout = new GZIPOutputStream(bout);
		gout.write(buffer);
		gout.close();
		//得到压缩后的数据
		byte[] gzip = bout.toByteArray();
		//设置压缩响应头
		response.setContentLength(gzip.length);
		response.setHeader("content-encoding", "gzip");
		//输出压缩后数据
		response.getOutputStream().write(gzip);
		response.getOutputStream().close();
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
