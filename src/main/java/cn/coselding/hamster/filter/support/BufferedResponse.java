package cn.coselding.hamster.filter.support;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 解决全站http响应的gzip压缩问题，
 * Response增强
 *
 * @author 宇强
 *
 */
public class BufferedResponse extends HttpServletResponseWrapper {

	/**
	 * 缓冲流
	 */
	private ByteArrayOutputStream baos;
	/**
	 * 缓冲writer
	 */
	private PrintWriter writer;
	private HttpServletResponse response;

	public BufferedResponse(HttpServletResponse response) {
		super(response);
		this.response = response;
		this.baos = new ByteArrayOutputStream();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		//将原来输出到浏览器的数据先输出到缓存中，便于后续处理
		//实际开发千万注意大文件操作有可能是内存爆满
		return new MyServletOutputStream(baos);
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		//指定特定的编码将数据输出到缓冲中
		writer = new PrintWriter(new OutputStreamWriter(baos,
				response.getCharacterEncoding()));
		return writer;
	}

	public byte[] getBuffer() {
		try {
			// 确保writer中的数据都刷到底层流
			if (writer != null)
				writer.close();
			//将缓冲中的数据刷新取出
			if (baos != null) {
				baos.flush();
				return baos.toByteArray();
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

/**重写缓冲输出流
 * @author 宇强
 *
 */
class MyServletOutputStream extends ServletOutputStream {

	/**
	 * 捕获输出的缓冲流
	 */
	private ByteArrayOutputStream baos;

	public MyServletOutputStream(ByteArrayOutputStream baos) {
		this.baos = baos;
	}

	@Override
	public void write(int b) throws IOException {
		baos.write(b);
	}
}
