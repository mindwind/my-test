package org.craft.cell.arthas.api;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;

/**
 * Arthas configuration object
 * 
 * @author mindwind
 * @version 1.0, Mar 12, 2013
 */
public class ArthasConfig {
	
	public static final String SERVER_NAME = "arthas";
	
	// ~ ------------------------------------------------------------------------------------------------------------
	
	/** The default http protocol codec charset: utf-8 */
	private Charset charset = Charset.forName("utf-8");
	
	/** The default http protocol codec buffer size: 2k */
	private int bufferSize = 2048;
	
	/** The max http header line length: 2k */
	private int maxLineLength = 2048;
	
	/** The max http request size: 2M */
	private int maxRequestSize = maxLineLength * 1024;
	
	/** The channel idle timeout in seconds: 120s */
	private int idleTimeout = 120;
	
	/** The bind address: local 8118 port */
	private SocketAddress bindAddress = new InetSocketAddress(8118);
	
	// ~ ------------------------------------------------------------------------------------------------------------

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public int getMaxLineLength() {
		return maxLineLength;
	}

	public void setMaxLineLength(int maxLineLength) {
		this.maxLineLength = maxLineLength;
	}

	public int getMaxRequestSize() {
		return maxRequestSize;
	}

	public void setMaxRequestSize(int maxRequestSize) {
		this.maxRequestSize = maxRequestSize;
	}

	public int getIdleTimeout() {
		return idleTimeout;
	}

	public void setIdleTimeout(int idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

	public SocketAddress getBindAddress() {
		return bindAddress;
	}

	public void setBindAddress(SocketAddress bindAddress) {
		this.bindAddress = bindAddress;
	}
	
}
