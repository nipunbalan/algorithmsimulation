package com.technipun.dc.algorithm;

import java.util.ArrayList;
import java.util.Iterator;

import com.technipun.ds.MessageQueue;
import com.technipun.ds.Node;

public class WaveProcessNode extends Node {

	private ArrayList<ReceiveIndicator> receiveVector;

	protected MessageQueue messageQueue;
	protected MessageQueue tokenQueue;
	protected WaveProcessNode silentNeigh;
	protected boolean runDiffusion;

	protected enum Status {
		INIT, WAITING, DECIDED, TOKEN_SENT
	};

	protected Status status;

	public WaveProcessNode(int nodeID) {
		this.nodeID = nodeID;
		messageQueue = new MessageQueue();
		tokenQueue = new MessageQueue();
		receiveVector = new ArrayList<ReceiveIndicator>();
	}

	/**
	 * @return the runDiffusion
	 */
	public boolean isRunDiffusion() {
		return runDiffusion;
	}

	/**
	 * @param runDiffusion
	 *            the runDiffusion to set
	 */
	public void setRunDiffusion(boolean runDiffusion) {
		this.runDiffusion = runDiffusion;
	}

	public void addMessage(Message msg) {
		messageQueue.add(msg);
	}

	public void init(boolean runDiffusion) {
		this.status = Status.INIT;
		this.runDiffusion = runDiffusion;
		Iterator<Node> neighItr = neigh.iterator();
		while (neighItr.hasNext())
			receiveVector.add(new ReceiveIndicator((WaveProcessNode) neighItr
					.next()));

		System.out.println("ProcessNode[" + this.nodeID + "] Initiated");

	}

	protected Message receiveToken() {
		Message msg;
		if (!tokenQueue.isEmpty()) {
			msg = tokenQueue.poll();
		} else {
			msg = messageQueue.poll();
		}
		if (msg != null && msg.header.getMsgType() == MessageType.TOKEN) {
			Iterator<ReceiveIndicator> recItr = receiveVector.iterator();
			while (recItr.hasNext()) {
				ReceiveIndicator recnode = recItr.next();
				if (msg.header.getSender() == recnode.getNode()) {
					recnode.setReceived(true);

					if (msg.header.getSender() == silentNeigh) {

						System.out
								.println("ProcessNode["
										+ this.nodeID
										+ "] Received Token from Silent Neighbour Process Node["
										+ msg.header.getSender().nodeID + "]");

					} else {
						System.out.println("ProcessNode[" + this.nodeID
								+ "] Received Token from Process Node["
								+ msg.header.getSender().nodeID + "]");
					}

					return msg;
				}
			}
		}
		return null;
	}

	protected void send(WaveProcessNode recepient, MessageType messageType) {
		Message newMsg = new Message();
		newMsg.header.setMsgType(messageType);
		newMsg.header.setSender(this);
		newMsg.header.setReceiver(recepient);
		newMsg.send();
		if (recepient == silentNeigh) {
			System.out.println("ProcessNode[" + this.nodeID
					+ "] sent token to its silent neighbour:Process Node["
					+ silentNeigh.nodeID + "]");
		}
	}

	protected void send(WaveProcessNode recepient, MessageType messageType,
			Object message) {
		Message newMsg = new Message();
		newMsg.header.setMsgType(messageType);
		newMsg.header.setSender(this);
		newMsg.header.setReceiver(recepient);
		newMsg.setMessage(message);
		newMsg.send();
	}

	protected int getNonRecCount() {
		int recCount = 0;
		Iterator<ReceiveIndicator> recItr = receiveVector.iterator();
		while (recItr.hasNext()) {
			ReceiveIndicator recnode = recItr.next();
			if (!recnode.isReceived())
				recCount++;
		}
		return recCount;
	}

	protected WaveProcessNode findSilentNeighbour() {
		if (getNonRecCount() > 1) {
			return null;
		}
		Iterator<ReceiveIndicator> recItr = receiveVector.iterator();
		while (recItr.hasNext()) {
			ReceiveIndicator recnode = recItr.next();
			if (!recnode.isReceived()) {
				if (this.status != Status.TOKEN_SENT) {
					System.out.println("ProcessNode[" + this.nodeID
							+ "] found its silent neighbour:Process Node["
							+ recnode.getNode().nodeID + "]");
				}
				return recnode.getNode();
			}
		}
		return null;
	}
	
	public void decide() {
		this.status = Status.DECIDED;
		System.out.println("ProcessNode[" + this.nodeID + "] Decides");
		if (runDiffusion) {
			deffuse();
		}
	}

	public void deffuse() {
		Iterator<Node> neighItr = neigh.iterator();
		while (neighItr.hasNext()) {
			WaveProcessNode node = (WaveProcessNode) neighItr.next();
			if (node != silentNeigh) {
				sendToken(node, MessageType.TOKEN);
			}
		}
	}
	
	protected void sendToken(WaveProcessNode recepient, MessageType messageType) {
		send(recepient, messageType);
		if (recepient == this.silentNeigh) {
			System.out.println("ProcessNode[" + this.nodeID
					+ "] sent token to its silent neighbour:Process Node["
					+ silentNeigh.nodeID + "]");
		}
	}

	protected void doProcess(Message msg) {

	}


	public boolean doWaveStep() {
		if (status == Status.DECIDED) {
			return false;
		}
		while (!messageQueue.isEmpty() && getNonRecCount() > 1) {
			this.status = Status.WAITING;
			Message msg = receiveToken();
			doProcess(msg);
		}
		this.silentNeigh = findSilentNeighbour();
		if (this.silentNeigh != null && this.status != Status.TOKEN_SENT) {
			sendToken(this.silentNeigh, MessageType.TOKEN);
			this.status = Status.TOKEN_SENT;
		}
		if (status == Status.TOKEN_SENT) {
			Message msg = receiveToken();
			if (msg != null) {
				WaveProcessNode sender = (WaveProcessNode) msg.header
						.getSender();
				doProcess(msg);

				if (sender != null && sender == silentNeigh) {
					decide();
					return true;
				}

			}
		}
		return false;
	}





}
