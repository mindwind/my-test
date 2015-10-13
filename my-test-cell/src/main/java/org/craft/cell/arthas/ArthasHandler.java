package org.craft.cell.arthas;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.craft.atom.io.AbstractIoHandler;
import org.craft.atom.io.Channel;
import org.craft.atom.protocol.ProtocolException;
import org.craft.atom.protocol.http.HttpRequestDecoder;
import org.craft.atom.protocol.http.HttpResponseEncoder;
import org.craft.atom.protocol.http.model.HttpRequest;
import org.craft.cell.arthas.api.ArthasConfig;
import org.craft.cell.arthas.spi.HttpRequestHandler;

/**
 * @author mindwind
 * @version 1.0, Mar 12, 2013
 */
public class ArthasHandler extends AbstractIoHandler {
	
	private static final Log LOG = LogFactory.getLog(ArthasHandler.class);
	private static final String HTTP_REQUEST_DECODER = "http_request_decoder";
	private static final String HTTP_RESPONSE_CHANNEL = "http_response_channel";
	
	private ArthasConfig config;
	private HttpRequestHandler handler;
	private HttpResponseEncoder encoder;
	
	// ~ ------------------------------------------------------------------------------------------------------------
	
	public ArthasHandler(ArthasConfig arthasConfig, HttpRequestHandler handler) {
		this.config = arthasConfig;
		this.handler = handler;
		this.encoder = new HttpResponseEncoder(config.getCharset());
	}

	// ~ ------------------------------------------------------------------------------------------------------------

	@Override
	public void channelOpened(Channel<byte[]> channel) {
		super.channelOpened(channel);
		
		HttpRequestDecoder decoder = new HttpRequestDecoder(config.getCharset(), config.getBufferSize(), config.getMaxLineLength(), config.getMaxRequestSize());
		HttpResponseChannel responseChannel = new HttpResponseChannel(channel, encoder);
		channel.setAttribute(HTTP_REQUEST_DECODER, decoder);
		channel.setAttribute(HTTP_RESPONSE_CHANNEL, responseChannel);
	}

	@Override
	public void channelIdle(Channel<byte[]> channel) {
		super.channelIdle(channel);
		channel.close();
	}

	@Override
	public void channelRead(Channel<byte[]> channel, byte[] bytes) {
		super.channelRead(channel, bytes);
		
		HttpRequestDecoder decoder = (HttpRequestDecoder) channel.getAttribute(HTTP_REQUEST_DECODER);
		HttpResponseChannel responseChannel = (HttpResponseChannel) channel.getAttribute(HTTP_RESPONSE_CHANNEL);
		try {
			List<HttpRequest> requests = decoder.decode(bytes);
			for (HttpRequest request : requests) {
				if (LOG.isDebugEnabled()) { LOG.debug("Request=\n" + request.toHttpString(config.getCharset())); }
				handler.handle(responseChannel, request);
			}
		} catch (ProtocolException e) {
			LOG.info("Http request protocol error", e);
		} catch (Exception e) {
			LOG.warn("Channel read unexpected error", e);
		}
	}

	@Override
	public void channelWritten(Channel<byte[]> channel, byte[] bytes) {
		super.channelWritten(channel, bytes);
		
		HttpResponseChannel responseChannel = (HttpResponseChannel) channel.getAttribute(HTTP_RESPONSE_CHANNEL);
		if (!responseChannel.isKeepAlive()) {
			responseChannel.close();
			if (LOG.isDebugEnabled()) { LOG.debug("After response close channel=" + channel); }
		}
	}

	@Override
	public void channelThrown(Channel<byte[]> channel, Throwable cause) {
		super.channelThrown(channel, cause);
		channel.close();
	}
	
	
	// ~ ------------------------------------------------------------------------------------------------------------
	

	public ArthasConfig getArthasConfig() {
		return config;
	}

	public void setArthasConfig(ArthasConfig arthasConfig) {
		this.config = arthasConfig;
	}
	
}
