package com.technipun.dc.algorithm;

public class ReceiveIndicator {
	
	public ReceiveIndicator(ProcessNode node) {
		this.node=node;
		this.received=false;
	}

	private ProcessNode node;
	
	/**
	 * @return the nodeID
	 */
	public ProcessNode getNode() {
		return node;
	}
	
	/**
	 * @param nodeID the nodeID to set
	 */
	public void setNode(ProcessNode node) {
		this.node = node;
	}
	
	/**
	 * @return the received
	 */
	public boolean isReceived() {
		return received;
	}
	
	/**
	 * @param received the received to set
	 */
	public void setReceived(boolean received) {
		this.received = received;
	}
	
	private boolean received;
	
}

