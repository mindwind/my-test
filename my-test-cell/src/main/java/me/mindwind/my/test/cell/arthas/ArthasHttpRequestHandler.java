package me.mindwind.my.test.cell.arthas;

import java.util.Date;
import java.util.List;
import java.util.Map;

import me.mindwind.my.test.cell.DataBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.craft.atom.io.Channel;
import org.craft.atom.protocol.http.HttpConstants;
import org.craft.atom.protocol.http.HttpHeaders;
import org.craft.atom.protocol.http.model.Cookie;
import org.craft.atom.protocol.http.model.HttpHeader;
import org.craft.atom.protocol.http.model.HttpHeaderType;
import org.craft.atom.protocol.http.model.HttpRequest;
import org.craft.atom.protocol.http.model.HttpResponse;
import org.craft.cell.arthas.api.HttpResponses;
import org.craft.cell.arthas.spi.HttpRequestHandler;

/**
 * @author mindwind
 * @version 1.0, Mar 20, 2013
 */
public class ArthasHttpRequestHandler implements HttpRequestHandler {
	
	private static final Log LOG = LogFactory.getLog(ArthasHttpRequestHandler.class);
	private boolean keepAlive = false;

	public ArthasHttpRequestHandler(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	@Override
	public void handle(Channel<HttpResponse> channel, HttpRequest request) throws Exception {
		// http://localhost:8118/watch/watchWaiterGroup.action?token=chat-watch-6167439573535479708L&waiterType=jd&groupId=3&test=测试&test=tt1
		// get request parameters
		String test = request.getParameter("test");
		Map<String, List<String>> paras = request.getParameterMap();
		if (LOG.isDebugEnabled()) {
			LOG.debug("test=" + test);
			LOG.debug("paras=" + paras);
		}
		
		// get cookie
		Cookie cookie = request.getCookie("test-cookie");
		if (LOG.isDebugEnabled() && cookie != null) {
			LOG.debug("request-cookie=" + cookie.toHttpString());
		}
		
		HttpResponse response = HttpResponses.new200Response(DataBuilder.buildString(1024));
		if (keepAlive) {
			HttpHeader keepAliveHeader = HttpHeaders.newKeepAliveHeader(HttpConstants.KEEP_ALIVE_OPTIONS);
			response.addHeader(keepAliveHeader);
		} else {
			response.removeFirstHeader(HttpHeaderType.CONNECTION.getName());
			response.addHeader(HttpHeaders.newConnectionHeader(false));
		}
		
		// plant cookie
		Cookie c = new Cookie("test-cookie", "1qaz2wsx3edc");
		c.setMaxAge(3600);
		c.setExpires(new Date(System.currentTimeMillis() + 3600 * 1000));
		response.addCookie(c);
		
		channel.write(response);
	}

}
