package com.technipun.dc.algorithm;

/**
 * The Class MessageHeader. Defines a message header object
 *
 * @author Nipun Balan Thekkummal
 * @version 1.0
 */


public class MessageHeader {
	
	
	/** The sender. */
	private WaveProcessNode sender;
	
	/** The receiver. */
	private WaveProcessNode receiver;
	
	/** The msg type, whether a token or a wake up call*/
	private MessageType msgType;
	
	/**
	 * Gets the sender.
	 *
	 * @return the sender
	 */
	public WaveProcessNode getSender() {
		return sender;
	}
	
	/**
	 * Sets the sender.
	 *
	 * @param sender the new sender
	 */
	public void setSender(WaveProcessNode sender) {
		this.sender = sender;
	}
	
	/**
	 * Gets the receiver.
	 *
	 * @return the receiver
	 */
	public WaveProcessNode getReceiver() {
		return receiver;
	}
	
	/**
	 * Sets the receiver.
	 *
	 * @param receiver the new receiver
	 */
	public void setReceiver(WaveProcessNode receiver) {
		this.receiver = receiver;
	}
	
	/**
	 * Gets the msg type.
	 *
	 * @return the msgType
	 */
	public MessageType getMsgType() {
		return msgType;
	}
	
	/**
	 * Sets the msg type.
	 *
	 * @param msgType the msgType to set
	 */
	public void setMsgType(MessageType msgType) {
		this.msgType = msgType;
	}
	
}
