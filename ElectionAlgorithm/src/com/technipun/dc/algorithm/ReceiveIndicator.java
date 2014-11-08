package com.technipun.dc.algorithm;

/**
 * This class defines a Receive Indicator which serves as the data structure to
 * store wheather message as been received from a neighbour.
 *
 * @author Nipun Balan Thekkummal
 * @version 1.0
 */
public class ReceiveIndicator {

	/**
	 * Instantiates a new receive indicator.
	 *
	 * @param node the node
	 */
	public ReceiveIndicator(WaveProcessNode node) {
		this.node = node;
		this.received = false;
	}

	/** The node. */
	private WaveProcessNode node;

	/**
	 * Gets the node.
	 *
	 * @return the nodeID
	 */
	public WaveProcessNode getNode() {
		return node;
	}

	/**
	 * Sets the node.
	 *
	 * @param node the new node
	 */
	public void setNode(WaveProcessNode node) {
		this.node = node;
	}

	/**
	 * Checks if is received.
	 *
	 * @return the received
	 */
	public boolean isReceived() {
		return received;
	}

	/**
	 * Sets the received.
	 *
	 * @param received            the received to set
	 */
	public void setReceived(boolean received) {
		this.received = received;
	}

	/** The received. */
	private boolean received;

}
