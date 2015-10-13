package org.craft.cell.arthas.api;

import java.io.IOException;
import java.nio.charset.Charset;

import org.craft.atom.io.Channel;
import org.craft.atom.io.IoAcceptor;
import org.craft.atom.nio.api.NioFactory;
import org.craft.atom.protocol.http.model.HttpRequest;
import org.craft.atom.protocol.http.model.HttpResponse;
import org.craft.cell.arthas.ArthasHandler;
import org.craft.cell.arthas.spi.HttpRequestHandler;

/**
 * Bootstrap for arthas.<p>
 * It assemble appropriate component and startup for serving.
 *
 * @author mindwind
 * @version 1.0, Mar 20, 2013
 */
public class ArthasBootstrap {
	
	private ArthasConfig config;
	private HttpRequestHandler requestHandler;
	
	// ~ ----------------------------------------------------------------------------------------------------------
	
	public ArthasBootstrap(HttpRequestHandler handler) {
		this(new ArthasConfig(), handler);
	}
	
	public ArthasBootstrap(ArthasConfig config, HttpRequestHandler handler) {
		this.config = config;
		this.requestHandler = handler;
	}
	
	// ~ ----------------------------------------------------------------------------------------------------------

	/**
	 * Boot arthas with default {@link IoAcceptor}
	 * 
	 * @throws IOException 
	 */
	public void boot() throws IOException {
		IoAcceptor acceptor = NioFactory.newTcpAcceptor(new ArthasHandler(config, requestHandler));
		boot(acceptor);
	}
	
	/**
	 * Boot arthas with specified {@link IoAcceptor}
	 * 
	 * @param acceptor
	 * @throws IOException
	 */
	public void boot(IoAcceptor acceptor) throws IOException {
		acceptor.bind(config.getBindAddress());
	}
	
	/**
	 * Just for use case demo
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ArthasBootstrap bootstrap = new ArthasBootstrap(new HttpRequestHandler() {
			@Override
			public void handle(Channel<HttpResponse> channel, HttpRequest request) throws Exception {
				HttpResponse response = HttpResponses.new200Response(request.toHttpString(Charset.defaultCharset()));
				channel.write(response);
			}
		});
		
		try {
			bootstrap.boot();
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}
	
}
