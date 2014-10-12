package com.technipun.dc.algorithm;

import com.technipun.ds.Node;

public class MessageHeader {
	
	
	private Node sender;
	private Node receiver;
	private MessageType msgType;
	
	public Node getSender() {
		return sender;
	}
	public void setSender(Node sender) {
		this.sender = sender;
	}
	public Node getReceiver() {
		return receiver;
	}
	public void setReceiver(Node receiver) {
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
