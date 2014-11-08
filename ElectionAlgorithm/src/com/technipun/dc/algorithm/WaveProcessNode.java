package com.technipun.dc.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import com.technipun.ds.MessageQueue;
import com.technipun.ds.Node;

/**
 * This class defines a WaveProcess node.
 *
 * @author Nipun Balan Thekkummal
 * @version 1.0
 */

public class WaveProcessNode extends Node {

	/** The receive vector. */
	private ArrayList<ReceiveIndicator> receiveVector;

	/** The message queue. */
	protected MessageQueue messageQueue;

	/** The token queue. */
	protected MessageQueue tokenQueue;

	/** The silent neigh. */
	protected WaveProcessNode silentNeigh;

	/** The run diffusion. */
	protected boolean runDiffusion;

	/** The had send action. */
	protected boolean hadSendAction;

	/** The had receive action. */
	protected boolean hadReceiveAction;

	/**
	 * Checks if is had send action.
	 *
	 * @return the hadSendAction
	 */
	public boolean isHadSendAction() {
		return hadSendAction;
	}

	/**
	 * Sets the had send action.
	 *
	 * @param hadSendAction
	 *            the hadSendAction to set
	 */
	public void setHadSendAction(boolean hadSendAction) {
		this.hadSendAction = hadSendAction;
	}

	/**
	 * Checks if is had receive action.
	 *
	 * @return the hadReceiveAction
	 */
	public boolean isHadReceiveAction() {
		return hadReceiveAction;
	}

	/**
	 * Sets the had receive action.
	 *
	 * @param hadReceiveAction
	 *            the hadReceiveAction to set
	 */
	public void setHadReceiveAction(boolean hadReceiveAction) {
		this.hadReceiveAction = hadReceiveAction;
	}

	/**
	 * The Enum Status.
	 */
	protected enum Status {

		/** The node is initialized. */
		INIT,
		/** The node is waiting. */
		WAITING,
		/** The node decided. */
		DECIDED,
		/** The token sent. */
		TOKEN_SENT
	};

	/** The status. */
	protected Status status;

	/**
	 * Instantiates a new wave process node.
	 *
	 * @param nodeID
	 *            the node id
	 */
	public WaveProcessNode(int nodeID) {
		this.nodeID = nodeID;
		messageQueue = new MessageQueue();
		tokenQueue = new MessageQueue();
		receiveVector = new ArrayList<ReceiveIndicator>();
	}

	/**
	 * Checks if is run diffusion.
	 *
	 * @return the runDiffusion
	 */
	public boolean isRunDiffusion() {
		return runDiffusion;
	}

	/**
	 * Sets the run diffusion.
	 *
	 * @param runDiffusion
	 *            the runDiffusion to set
	 */
	public void setRunDiffusion(boolean runDiffusion) {
		this.runDiffusion = runDiffusion;
	}

	/**
	 * Adds the message.
	 *
	 * @param msg
	 *            the msg
	 */
	public void addMessage(Message msg) {
		messageQueue.add(msg);
	}

	/**
	 * Initializes the wave process node
	 *
	 * @param runDiffusion
	 *            the run diffusion flag
	 */
	public void init(boolean runDiffusion) {
		// Setting silent neighbor to null
		this.silentNeigh = null;

		// Setting status to initialized
		this.status = Status.INIT;

		// Setting the diffusion option from the init parameter
		this.runDiffusion = runDiffusion;

		// Initializes a new receive vector;
		receiveVector = new ArrayList<ReceiveIndicator>();

		Iterator<Node> neighItr = neigh.iterator();

		// Adding neighbours to the new receive vector
		while (neighItr.hasNext())
			receiveVector.add(new ReceiveIndicator((WaveProcessNode) neighItr
					.next()));

		System.out.println("Initiated");

	}

	/**
	 * Receive any token from the queue
	 *
	 * @return the message
	 */
	protected Message receiveToken() {
		Message msg;
		// if token queue is not empty, first read from token else from the
		// message queue. Token queue is populated by same node when its waiting
		// for wake up call and if it got a token then.
		if (!tokenQueue.isEmpty()) {
			msg = tokenQueue.poll();
		} else {
			msg = messageQueue.poll();
		}
		// Read the message if its a message of type token
		if (msg != null && msg.header.getMsgType() == MessageType.TOKEN) {
			Iterator<ReceiveIndicator> recItr = receiveVector.iterator();
			while (recItr.hasNext()) {
				ReceiveIndicator recnode = recItr.next();
				// Setting the receive indicator in receive Vector
				if (msg.header.getSender() == recnode.getNode()) {
					recnode.setReceived(true);
					// Checking wheather the message was from its silent
					// neighbour
					if (msg.header.getSender() == silentNeigh) {

						System.out
								.println("Received Token from Silent Neighbour Process Node["
										+ msg.header.getSender().nodeID + "]");

					} else {
						System.out.println("Received Token from Process Node["
								+ msg.header.getSender().nodeID + "]");
					}
					return msg;
				}
			}
		}
		return null;
	}

	/**
	 * Send to a node.
	 *
	 * @param recepient
	 *            the recepient
	 * @param messageType
	 *            the message type
	 */
	protected void send(WaveProcessNode recepient, MessageType messageType) {
		this.hadSendAction = true;
		Message newMsg = new Message();
		newMsg.header.setMsgType(messageType);
		newMsg.header.setSender(this);
		newMsg.header.setReceiver(recepient);
		newMsg.send();
		if (recepient == silentNeigh) {
			System.out.println("Sent token to its silent neighbour:Process Node["
					+ silentNeigh.nodeID + "]");
		}
	}

	/**
	 * Send.
	 *
	 * @param recepient
	 *            the recepient
	 * @param messageType
	 *            the message type
	 * @param message
	 *            the message
	 */
	protected void send(WaveProcessNode recepient, MessageType messageType,
			Object message) {
		this.hadSendAction = true;
		Message newMsg = new Message();
		newMsg.header.setMsgType(messageType);
		newMsg.header.setSender(this);
		newMsg.header.setReceiver(recepient);
		newMsg.setMessage(message);
		newMsg.send();
		if (recepient == silentNeigh) {
			System.out.println("Sent token to its silent neighbour:Process Node["
					+ silentNeigh.nodeID + "]");
		}
	}

	/**
	 * Gets the number of nodes from which it didn't receive a token
	 *
	 * @return the number of nodes from which it didn't receive a token
	 */
	protected int getNonRecCount() {
		int recCount = 0;
		// Iterating through the receive vector to count the number of nodes
		// from which token is not received
		Iterator<ReceiveIndicator> recItr = receiveVector.iterator();
		while (recItr.hasNext()) {
			ReceiveIndicator recnode = recItr.next();
			if (!recnode.isReceived())
				recCount++;
		}
		return recCount;
	}

	/**
	 * Find silent neighbour.
	 *
	 * @return the wave process node
	 */
	protected WaveProcessNode findSilentNeighbour() {
		// if there are more than one neighbour from which message is not
		// received, returns null
		if (getNonRecCount() > 1) {
			return null;
		}
		// Iterating through the receive vector get the silent neighbour;
		Iterator<ReceiveIndicator> recItr = receiveVector.iterator();
		while (recItr.hasNext()) {
			ReceiveIndicator recnode = recItr.next();
			if (!recnode.isReceived()) {
				if (this.status != Status.TOKEN_SENT) {
					System.out.println("Found its silent neighbour:Process Node["
							+ recnode.getNode().nodeID + "]");
				}
				return recnode.getNode();
			}
		}
		return null;
	}

	/**
	 * Decide action for wave process.
	 */
	public void decide() {
		this.status = Status.DECIDED;
		System.out.println("Decides");
		if (runDiffusion) {
			deffuse();
		}
	}

	/**
	 * Deffuse action for wave process
	 */
	public void deffuse() {
		Iterator<Node> neighItr = neigh.iterator();
		while (neighItr.hasNext()) {
			WaveProcessNode node = (WaveProcessNode) neighItr.next();
			if (node != silentNeigh) {
				sendToken(node, MessageType.TOKEN);
			}
		}
	}

	/**
	 * Send token for wave process
	 *
	 * @param recepient
	 *            the recepient
	 * @param messageType
	 *            the message type
	 */
	protected void sendToken(WaveProcessNode recepient, MessageType messageType) {
		send(recepient, messageType);
		// if (recepient == this.silentNeigh) {
		// System.out.println("ProcessNode[" + this.nodeID
		// + "] sent token to its silent neighbour:Process Node["
		// + silentNeigh.nodeID + "]");
		// }
	}

	/**
	 * Do process. Dummy function for wave process. Processes extending wave
	 * process can use this method the do processing on the messages (ex.
	 * Election process)
	 *
	 * @param msg
	 *            the msg
	 */
	protected void doProcess(Message msg) {

	}

	/**
	 * Executes the wave step. Implements the main wave algorithm logic
	 *
	 * @return true, if successful
	 */
	public boolean doWaveStep() {
		// If node already decided just return with out performing any action
		if (status == Status.DECIDED) {
			System.out.println("Already decided!");
			return false;
		}

		if (messageQueue.isEmpty() && tokenQueue.isEmpty()) {
			System.out.println("Doesn't having any receive action!");
		}
		// if message que or token que is not empty and number of nodes from
		// which token is not received is more than 1 run the loop
		while ((!messageQueue.isEmpty() || !tokenQueue.isEmpty())
				&& getNonRecCount() > 1) {

			this.status = Status.WAITING;

			// Receive the token
			Message msg = receiveToken();

			// Process the message. Used by objects which extends wave property
			// (ex. election)
			doProcess(msg);
		}
		// Finds the slient neighbour
		this.silentNeigh = findSilentNeighbour();

		// If it found the silent neighbour send token to it.
		if (this.silentNeigh != null && this.status != Status.TOKEN_SENT) {
			sendToken(this.silentNeigh, MessageType.TOKEN);
			this.status = Status.TOKEN_SENT;
		}

		// If it has send a token. Wait for the token.
		if (status == Status.TOKEN_SENT) {
			Message msg = receiveToken();
			if (msg != null) {
				WaveProcessNode sender = (WaveProcessNode) msg.header
						.getSender();
				doProcess(msg);
				
				
				// If message is received and the sender is its silent
				// neighbour, run decide method
				if (sender != null && sender == silentNeigh) {
					decide();
					return true;
				}

			}
		}
		return false;
	}

}
