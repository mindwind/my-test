package me.mindwind.my.test.cell.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.mindwind.my.test.cell.DataBuilder;

/**
 * @author mindwind
 * @version 1.0, Mar 20, 2013
 */
public class EchoServlet extends HttpServlet {

	private static final long serialVersionUID = 5535144991139090839L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse rsp) throws ServletException, IOException {
		doPost(req, rsp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse rsp) throws ServletException, IOException {
		String content = DataBuilder.buildString(1024);
		
		rsp.setContentType("text/html; charset=utf-8");
		rsp.setHeader("Connection", "close");
		rsp.setContentLength(content.getBytes(Charset.forName("utf-8")).length);
		
		PrintWriter pw = null;
		try {
			pw = rsp.getWriter();
			pw.print(content);
			pw.flush();
			rsp.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

}
