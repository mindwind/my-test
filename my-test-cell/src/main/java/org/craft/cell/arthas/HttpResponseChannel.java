package org.craft.cell.arthas;

import java.net.SocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.craft.atom.io.AbstractChannel;
import org.craft.atom.io.Channel;
import org.craft.atom.protocol.ProtocolException;
import org.craft.atom.protocol.http.HttpConstants;
import org.craft.atom.protocol.http.HttpResponseEncoder;
import org.craft.atom.protocol.http.model.HttpHeader;
import org.craft.atom.protocol.http.model.HttpHeaderType;
import org.craft.atom.protocol.http.model.HttpResponse;

/**
 * Channel transmit {@link HttpResponse}
 * 
 * @author mindwind
 * @version 1.0, Mar 14, 2013
 */
public class HttpResponseChannel extends AbstractChannel implements Channel<HttpResponse> {
	
	private static final Log LOG = LogFactory.getLog(HttpResponseChannel.class);
	private HttpResponseEncoder encoder;
	private Channel<byte[]> innerChannel;
	private boolean keepAlive = true;
	
	// ~ ------------------------------------------------------------------------------------------------------------

	
	HttpResponseChannel(Channel<byte[]> innerChannel, HttpResponseEncoder encoder) {
		this.innerChannel = innerChannel;
		this.encoder = encoder;
	}

	
	// ~ ------------------------------------------------------------------------------------------------------------

	@Override
	public boolean write(HttpResponse response) {
		byte[] bytes = null;
		try {
			bytes = encoder.encode(response);
		} catch (ProtocolException e) {
			LOG.warn("Http response write error!", e);
		}
		
		HttpHeader connectionHeader = response.getFirstHeader(HttpHeaderType.CONNECTION.getName());
		if (HttpConstants.CONNECTION_CLOSE.equalsIgnoreCase(connectionHeader.getValue())) {
			keepAlive = false;
		}
		
		if (LOG.isDebugEnabled()) { LOG.debug("Response=\n" + response.toHttpString(encoder.getCharset())); }
		
		return innerChannel.write(bytes);
	}

	@Override
	public void close() {
		super.close();
		innerChannel.close();
	}
	
	@Override
	public SocketAddress getRemoteAddress() {
		return innerChannel.getRemoteAddress();
	}

	@Override
	public SocketAddress getLocalAddress() {
		return innerChannel.getLocalAddress();
	}

	boolean isKeepAlive() {
		return keepAlive;
	}

}
