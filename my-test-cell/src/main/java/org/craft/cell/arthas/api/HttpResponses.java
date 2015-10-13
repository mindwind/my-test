package org.craft.cell.arthas.api;

import java.nio.charset.Charset;

import org.craft.atom.protocol.http.HttpHeaders;
import org.craft.atom.protocol.http.model.HttpContentType;
import org.craft.atom.protocol.http.model.HttpEntity;
import org.craft.atom.protocol.http.model.HttpResponse;
import org.craft.atom.protocol.http.model.HttpStatus;
import org.craft.atom.protocol.http.model.HttpStatusLine;
import org.craft.atom.protocol.http.model.HttpVersion;
import org.craft.atom.protocol.http.model.MimeType;

/**
 * Factory and utility methods for {@link HttpResponse}.
 * 
 * @author mindwind
 * @version 1.0, Mar 14, 2013
 */
public class HttpResponses {
	
	/**
	 * Creates a response with status code 200 (OK), use default charset and content type.
	 * 
	 * @param content response content
	 * @return
	 */
	public static HttpResponse new200Response(String content) {
		HttpEntity entity = new HttpEntity(content, HttpContentType.DEFAULT);
		return new200Response(entity);
	}
	
	/**
	 * Creates a response with status code 200 (OK).
	 * 
	 * @param type     mime type
	 * @param charset  content charset
	 * @param content  response content
	 * @return the newly created response
	 */
	public static HttpResponse new200Response(MimeType type, Charset charset, String content) {
		HttpContentType contentType = new HttpContentType(type, charset);
		HttpEntity entity = new HttpEntity(content, contentType);
		return new200Response(entity);
	}
	
	/**
	 * Creates a response with status code 200 (OK).
	 * 
	 * @param entity http entity
	 * @return the newly created response
	 */
	public static HttpResponse new200Response(HttpEntity entity) {
		HttpResponse rsp = new HttpResponse();
		HttpStatusLine statusLine = new HttpStatusLine(HttpVersion.HTTP_1_1, HttpStatus.OK);
		rsp.setStatusLine(statusLine);	
		rsp.setEntity(entity);
		setDefaultHeaders(rsp);
		return rsp;
	}
	
	/**
	 * Creates a response with status code 400 (Bad Request).
	 * 
	 * @return the newly created response
	 */
	public static HttpResponse new400Response() {
		HttpResponse rsp = new HttpResponse();
		HttpStatusLine statusLine = new HttpStatusLine(HttpVersion.HTTP_1_1, HttpStatus.BAD_REQUEST);
		rsp.setStatusLine(statusLine);	
		setDefaultHeaders(rsp);
		return rsp;
	}
	
	/**
	 * Creates a response with status code 404 (Not Found).
	 * 
	 * @return
	 */
	public static HttpResponse new404Response() {
		HttpResponse rsp = new HttpResponse();
		HttpStatusLine statusLine = new HttpStatusLine(HttpVersion.HTTP_1_1, HttpStatus.NOT_FOUND);
		rsp.setStatusLine(statusLine);	
		setDefaultHeaders(rsp);
		return rsp;
	}
	
	/**
	 * Creates a response with status code 500 (Internal Server Error).
	 * 
	 * @return
	 */
	public static HttpResponse new500Response() {
		HttpResponse rsp = new HttpResponse();
		HttpStatusLine statusLine = new HttpStatusLine(HttpVersion.HTTP_1_1, HttpStatus.INTERNAL_SERVER_ERROR);
		rsp.setStatusLine(statusLine);	
		rsp.addHeader(HttpHeaders.newConnectionHeader(true));
		setDefaultHeaders(rsp);
		return rsp;
	}
	
	private static void setDefaultHeaders(HttpResponse rsp) {
		rsp.addHeader(HttpHeaders.newServerHeader(ArthasConfig.SERVER_NAME));
		rsp.addHeader(HttpHeaders.newDateHeader());
		rsp.addHeader(HttpHeaders.newConnectionHeader(true));
		HttpEntity entity = rsp.getEntity();
		if (entity == null) {
			rsp.addHeader(HttpHeaders.newContentLengthHeader(0));
		} else {
			rsp.addHeader(HttpHeaders.newContentLengthHeader(entity.getContent().length));
			rsp.addHeader(HttpHeaders.newContentTypeHeader(entity.getContentType()));
		}
	}
}
