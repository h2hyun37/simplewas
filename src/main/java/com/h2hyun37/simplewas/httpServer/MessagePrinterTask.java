package com.h2hyun37.simplewas.httpServer;

public class MessagePrinterTask implements Runnable {

	private String message;

	public MessagePrinterTask(String message) {
		this.message = message;
	}

	@Override
	public void run() {
		System.out.println(message);
	}
}
