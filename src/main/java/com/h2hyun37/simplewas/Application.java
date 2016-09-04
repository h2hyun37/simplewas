package com.h2hyun37.simplewas;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.h2hyun37.simplewas.httpServer.PortListener;

public class Application {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/ApplicationContext.xml");

		PortListener listen8080 = (PortListener) context.getBean("portListener");
		PortListener listen8090 = (PortListener) context.getBean("portListener");


		listen8080.setPort(8080);
		listen8090.setPort(8090);

		Thread t1 = new Thread(listen8080);
		Thread t2 = new Thread(listen8090);

		t1.start();
		t2.start();

		try {
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// HttpServer2 server = (HttpServer2) context.getBean("httpServer2");
		//
		// HttpConfig config = (HttpConfig) context.getBean("httpConfig");
		//
		// int port = config.getHttpPort();
		// VirtualHost[] virtualHosts = config.getVirtualHost();
		//
		// for (VirtualHost vHost : virtualHosts) {
		//
		// }

		// server2.printMessages();

		System.out.println("end");

	}


}
