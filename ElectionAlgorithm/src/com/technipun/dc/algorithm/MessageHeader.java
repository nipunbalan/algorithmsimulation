package com.technipun.dc.algorithm;


public class MessageHeader {
	
	
	private WaveProcessNode sender;
	private WaveProcessNode receiver;
	private MessageType msgType;
	
	public WaveProcessNode getSender() {
		return sender;
	}
	public void setSender(WaveProcessNode sender) {
		this.sender = sender;
	}
	public WaveProcessNode getReceiver() {
		return receiver;
	}
	public void setReceiver(WaveProcessNode receiver) {
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
