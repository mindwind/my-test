package me.mindwind.my.test.cell.comet;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.mindwind.my.test.cell.DataBuilder;

import org.apache.catalina.comet.CometEvent;
import org.apache.catalina.comet.CometProcessor;

/** 
 * @author mindwind
 * @version 1.0, Mar 21, 2013
 */
public class EchoComet extends HttpServlet implements CometProcessor {

	private static final long serialVersionUID = -5270904398903052256L;

	@Override
	public void event(CometEvent event) throws IOException, ServletException {
		HttpServletRequest request = event.getHttpServletRequest();
		HttpServletResponse response = event.getHttpServletResponse();
		
		response.setContentType("text/html; charset=utf-8");
//		response.setHeader("Connection", "close");

		switch (event.getEventType()) {
		case BEGIN:
			begin(event, request, response);
			break;
		case END:
			end(event, request, response);
			break;
		case READ:
			read(event, request, response);
			break;
		case ERROR:
			error(event, request, response);
			break;
		}
	}
	
	private void begin(CometEvent event, HttpServletRequest request, HttpServletResponse response) {
		String content = DataBuilder.buildString(1024);
		response.setContentLength(content.getBytes(Charset.forName("utf-8")).length);
		
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.print(content);
			pw.flush();
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	
	private void end(CometEvent event, HttpServletRequest request, HttpServletResponse response) throws IOException {
		event.close();
	}
	
	private void read(CometEvent event, HttpServletRequest request, HttpServletResponse response) throws IOException {
		event.close();
	}
	
	private void error(CometEvent event, HttpServletRequest request, HttpServletResponse response) throws IOException {
		event.close();
	}

}
