package com.technipun.dc.algorithm;

public class Message {
	
	private MessageHeader header= new MessageHeader();
	
	/**
	 * @return the header
	 */
	public MessageHeader getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(MessageHeader header) {
		this.header = header;
	}

	private Object message;
	
	/**
	 * @return the message
	 */
	public Object getMessage() {
		return message;
	}
	
	/**
	 * @param message the message to set
	 */
	public void setMessage(Object message) {
		this.message = message;
	}
	
}
