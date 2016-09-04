package com.h2hyun37.simplewas.httpServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PortListener implements Runnable, DisposableBean {

	private static Logger logger = LoggerFactory.getLogger(PortListener.class);

	@Autowired
	private Dispatcher dispatcher;

	private int port = 8088; // default port;

	private ServerSocket serverSocket;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public void run() {

		logger.info("Starting PortListener thread(port = {})", port);

		try {
			serverSocket = new ServerSocket(port);
			logger.info("Accepting connections on port " + port);

			while (true) {
				// blocking...
				logger.info("waiting requests on port " + port);
				Socket socket = serverSocket.accept();

				// STEP 1. request에서 hostname 확인
				InetAddress address = socket.getInetAddress();
				String hostname = address.getHostName();
				logger.info("accepted request : hostname = {} , port = {}", hostname, port);

				// STEP 2. dispatcher 에게 request 위임
				try {
					logger.info("trying to delegate request to dispatcher...");
					dispatcher.delegationToDispatcher(hostname, port, socket);
					logger.info("delegation finished");
				} catch (Exception e) {
					logger.info("delegation failed");
					e.printStackTrace();
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	@Override
	public void destroy() throws Exception {
		logger.info("destroy PortListener thread");
		logger.info("closing port {}", port);

		serverSocket.close();
	}

}
