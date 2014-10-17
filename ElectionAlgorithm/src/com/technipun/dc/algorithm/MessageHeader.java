package com.technipun.dc.algorithm;


public class MessageHeader {
	
	
	private ProcessNode sender;
	private ProcessNode receiver;
	private MessageType msgType;
	
	public ProcessNode getSender() {
		return sender;
	}
	public void setSender(ProcessNode sender) {
		this.sender = sender;
	}
	public ProcessNode getReceiver() {
		return receiver;
	}
	public void setReceiver(ProcessNode receiver) {
		this.receiver = receiver;
	}
	
	/**
	 * @return the msgType
	 */
	public MessageType getMsgType() {
		return msgType;
	}
	/**
	 * @param msgType the msgType to set
	 */
	public void setMsgType(MessageType msgType) {
		this.msgType = msgType;
	}
	
}
