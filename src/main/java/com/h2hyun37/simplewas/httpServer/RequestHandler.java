package com.h2hyun37.simplewas.httpServer;

import java.io.File;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class RequestHandler {

	public String name;

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	private String docRoot;

	private File docRootFile;

	public String getDocRoot() {
		return docRoot;
	}

	public void setDocRoot(String docRoot) {
		this.docRoot = docRoot;
		docRootFile = new File(this.docRoot);
	}

	public void processRequest(Socket request) {

		taskExecutor.execute(new RequestProcessor(docRootFile, request));
	}

	public void shutdownExecutor() {
		taskExecutor.shutdown();
	}


}