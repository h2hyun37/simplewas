package com.h2hyun37.simplewas.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HttpConfigVO {

	public static class VirtualHost {
		public String host;
		public int port;
		public String docRoot;

		public ErrorDocument errorDocument;
	}

	public static class ErrorDocument {

		@JsonProperty("403")
		public String error403;

		@JsonProperty("404")
		public String error404;

		@JsonProperty("500")
		public String error500;
	}

	// public int port; // listen 포트는 각 vhost 별로 지정하므로 여기는 일단 주석처
	private VirtualHost[] virtualHost;

	public void setVirtualHost(VirtualHost[] virtualHost) {
		this.virtualHost = virtualHost;
	}

	public VirtualHost[] getVirtualHost() {
		return virtualHost;
	}

}