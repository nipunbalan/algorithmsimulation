package com.technipun.dc.algorithm;

public class Message {
	
	protected MessageHeader header;
	
	public Message()
	{
		this.header= new MessageHeader();
	}
	
	
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
	
	public void send()
	{
		this.header.getReceiver().addMessage(this);
	}
	
}
