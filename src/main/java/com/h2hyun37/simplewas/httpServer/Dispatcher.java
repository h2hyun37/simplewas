package com.h2hyun37.simplewas.httpServer;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.h2hyun37.simplewas.config.HttpConfig;
import com.h2hyun37.simplewas.config.HttpConfigVO.VirtualHost;

@Component
public class Dispatcher {

	private final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

	@Autowired
	ApplicationContext context;

	@Autowired
	private HttpConfig config;

	// Host - ThreadPool mapper
	Map<String, RequestHandler> hostThreadPoolMapper;

	@PostConstruct
	public void initDispatcher() {

		if (hostThreadPoolMapper != null) {
			logger.debug("already set in hostThreadPoolMapper...");
			return;
		}

		logger.info("start initDispatcher()");
		hostThreadPoolMapper = new HashMap<>();

		VirtualHost[] hosts = config.getVirtualHost();

		for (VirtualHost host : hosts) {

			String inetPortAddrss = host.host + ":" + String.valueOf(host.port);
			logger.info("host:port = {}", inetPortAddrss);

			RequestHandler handler = (RequestHandler) context.getBean("requestHandler");
			handler.name = inetPortAddrss;
			handler.setDocRoot(host.docRoot);
			logger.info("set RequestHandler : name = {}, docRoot = {}", handler.name, handler.getDocRoot());

			hostThreadPoolMapper.put(inetPortAddrss, handler);
		}

	}

	public void delegationToDispatcher(String domain, int port, Socket request) throws Exception {

		String inetPortAddrss = domain + ":" + String.valueOf(port);

		RequestHandler handler = hostThreadPoolMapper.get(inetPortAddrss);

		if (handler == null) {
			throw new Exception("illegal hostname and port :" + inetPortAddrss);
		}

		handler.processRequest(request);

	}

}
