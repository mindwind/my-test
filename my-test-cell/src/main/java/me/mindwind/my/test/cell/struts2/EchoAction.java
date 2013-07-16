package me.mindwind.my.test.cell.struts2;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.mindwind.my.test.cell.DataBuilder;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author mindwind
 * @version 1.0, Mar 21, 2013
 */
public class EchoAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = -3507831021113210963L;
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public String echo() throws Exception {
		String data = DataBuilder.buildString(1024);
		response(data);
		return null;
	}
	
	protected void response(String str) {
		response.setContentType("text/html; charset=utf-8");
		response.setHeader("Connection", "close");
		response.setContentLength(str.getBytes(Charset.forName("utf-8")).length);
		
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.print(str);
			writer.flush();
			response.flushBuffer();
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		} finally {
			if (writer != null) { writer.close(); }
		}
	}

}
