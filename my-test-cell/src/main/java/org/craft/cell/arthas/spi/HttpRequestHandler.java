package org.craft.cell.arthas.spi;

import org.craft.atom.io.Channel;
import org.craft.atom.protocol.http.model.HttpRequest;
import org.craft.atom.protocol.http.model.HttpResponse;

/**
 * HttpRequestHandler represents a routine for processing of a specific group of HTTP requests.
 * Request handlers are expected to take care of application specific HTTP processing. 
 * The main purpose of a request handler is to generate a response object with a content entity 
 * to be sent back to the client in response to the given request
 * 
 * @author mindwind
 * @version 1.0, Mar 14, 2013
 */
public interface HttpRequestHandler {
	
	/**
	 * Handles the request and produces a response to be sent back to the client.
	 * 
	 * @param channel channel transmit {@link HttpResponse}
	 * @param request the HTTP request.
	 * @throws in case of any processing problem.
	 */
	void handle(Channel<HttpResponse> channel, HttpRequest request) throws Exception;
	
}
