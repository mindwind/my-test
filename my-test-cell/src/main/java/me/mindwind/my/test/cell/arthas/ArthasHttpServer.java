package me.mindwind.my.test.cell.arthas;

import org.craft.cell.arthas.api.ArthasBootstrap;
import org.craft.cell.arthas.spi.HttpRequestHandler;

/**
 * @author mindwind
 * @version 1.0, Mar 20, 2013
 */
public class ArthasHttpServer {

	public static void main(String[] args) {
		String keepAlive = System.getProperty("keep.alive");
		if (keepAlive == null) {
			keepAlive = "false";
		}
		
		HttpRequestHandler requestHandler = new ArthasHttpRequestHandler(Boolean.parseBoolean(keepAlive));
		ArthasBootstrap bootstrap = new ArthasBootstrap(requestHandler);
//		IoAcceptor acceptor = new NioTcpAcceptor(new ArthasHandler(new ArthasConfig(), requestHandler), new NioAcceptorConfig(), new NioOrderedDirectChannelEventDispatcher());
		try {
			bootstrap.boot();
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
		System.out.println("Arthas http server listening on port = 8118");
	}
}
