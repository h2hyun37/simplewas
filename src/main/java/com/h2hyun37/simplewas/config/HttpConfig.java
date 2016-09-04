package com.h2hyun37.simplewas.config;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.h2hyun37.simplewas.config.HttpConfigVO.VirtualHost;

@Component
public class HttpConfig {

	private final Logger logger = LoggerFactory.getLogger(HttpConfig.class);

	@Value("classpath:httpConfig/config.json")
	private Resource httpConfig;

	private File httpConfigFile;

	private ObjectMapper mapper;

	private HttpConfigVO value;

	// public int getHttpPort() {
	// return value.port;
	// }

	public VirtualHost[] getVirtualHost() {
		return value.getVirtualHost();
	}

	@PostConstruct
	public void initHttpConfig() {

		logger.info("start init http configuration...");

		try {
			httpConfigFile = httpConfig.getFile();
			logger.info("config file : {}", httpConfigFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		mapper = new ObjectMapper(); // create once, reuse

		try {
			value = mapper.readValue(httpConfigFile, HttpConfigVO.class);

			// logger.info("HTTP port : {}", value.port); // 각 vhost 별로 listen port를 지정하므로 주석처
			for (VirtualHost vhost : value.getVirtualHost()) {
				printVhostInfo(vhost);
			}

			logger.info("finish init http configuration");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void printVhostInfo(VirtualHost vhost) {
		logger.info("    [VirtualHost]");
		logger.info("        host : {}", vhost.host);
		logger.info("        port : {}", vhost.port);
		logger.info("        docRoot : {}", vhost.docRoot);
		logger.info("        errorDocument - 403 : {}", vhost.errorDocument.error403);
		logger.info("        errorDocument - 404 : {}", vhost.errorDocument.error404);
		logger.info("        errorDocument - 500 : {}", vhost.errorDocument.error500);

	}

}
