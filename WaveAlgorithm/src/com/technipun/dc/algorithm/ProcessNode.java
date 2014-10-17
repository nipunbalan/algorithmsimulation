package com.technipun.dc.algorithm;

import java.util.ArrayList;
import java.util.Iterator;

import com.technipun.ds.MessageQueue;
import com.technipun.ds.Node;

public class ProcessNode extends Node {

	public ProcessNode(int nodeID) {
		this.nodeID = nodeID;
		messageQueue = new MessageQueue();
		receiveVector = new ArrayList<ReceiveIndicator>();

	}

	private ArrayList<ReceiveIndicator> receiveVector;

	private MessageQueue messageQueue;
	private ProcessNode silentNeigh;
	private boolean runDiffusion;

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

	private enum Status {
		INIT, WAITING, DECIDED, TOKEN_SENT
	};

	private Status status;

	public void addMessage(Message msg) {
		messageQueue.add(msg);
	}

	public void init(boolean runDiffusion) {
		this.status=Status.INIT;
		this.runDiffusion = runDiffusion;
		Iterator<Node> neighItr = neigh.iterator();
		while (neighItr.hasNext())
			receiveVector.add(new ReceiveIndicator((ProcessNode) neighItr
					.next()));

		System.out.println("Process Node[" + this.nodeID + "] Initiated");

	}

	private ProcessNode receiveToken() {
		Message msg = messageQueue.poll();
		if (msg != null && msg.header.getMsgType() == MessageType.TOKEN) {
			Iterator<ReceiveIndicator> recItr = receiveVector.iterator();
			while (recItr.hasNext()) {
				ReceiveIndicator recnode = recItr.next();
				if (msg.header.getSender() == recnode.getNode()) {
					recnode.setReceived(true);

					if (msg.header.getSender() == silentNeigh) {

						System.out
								.println("Process Node["
										+ this.nodeID
										+ "] Received Token from Silent Neighbour Process Node["
										+ msg.header.getSender().nodeID + "]");

					} else {
						System.out.println("Process Node[" + this.nodeID
								+ "] Received Token from Process Node["
								+ msg.header.getSender().nodeID + "]");
					}

					return msg.header.getSender();
				}
			}
		}
		return null;
	}

	private void send(ProcessNode recepient, MessageType messageType) {
		Message newMsg = new Message();
		newMsg.header.setMsgType(messageType);
		newMsg.header.setSender(this);
		newMsg.header.setReceiver(recepient);
		newMsg.send();

		if (recepient == silentNeigh) {
			System.out.println("Process Node[" + this.nodeID
					+ "] sent token to its silent neighbour:Process Node["
					+ silentNeigh.nodeID + "]");
		}
	}

	private int getNonRecCount() {
		int recCount = 0;
		Iterator<ReceiveIndicator> recItr = receiveVector.iterator();
		while (recItr.hasNext()) {
			ReceiveIndicator recnode = recItr.next();
			if (!recnode.isReceived())
				recCount++;
		}
		return recCount;
	}

	private ProcessNode findSilentNeighbour() {
		if (getNonRecCount() > 1) {
			return null;
		}
		Iterator<ReceiveIndicator> recItr = receiveVector.iterator();
		while (recItr.hasNext()) {
			ReceiveIndicator recnode = recItr.next();
			if (!recnode.isReceived())
				System.out.println("Process Node[" + this.nodeID
						+ "] found its silent neighbour:Process Node["
						+ recnode.getNode().nodeID + "]");
			return recnode.getNode();
		}
		return null;
	}

	public void runWave() {

		while (getNonRecCount() > 1) {

			ProcessNode sender = receiveToken();
			if (sender != null) {
				System.out.println("Process Node[" + this.nodeID
						+ "] Received Token from Process Node[" + sender.nodeID
						+ "]");
			}

		}

		ProcessNode silentNeigh = findSilentNeighbour();
		System.out.println("Process Node[" + this.nodeID
				+ "] found its silent neighbour:Process Node["
				+ silentNeigh.nodeID + "]");
		send(silentNeigh, MessageType.TOKEN);

		// while (true) {

		ProcessNode sender = receiveToken();
		if (sender == silentNeigh) {
			System.out.println("Process Node[" + this.nodeID
					+ "] Received Token from Silent Neighbour Process Node["
					+ sender.nodeID + "]");
			System.out.println("Process Node[" + this.nodeID + "] Decides");
		}

		// }

	}

	public void executeStep() {

		while (!messageQueue.isEmpty()) {
			if (getNonRecCount() > 1) {
				this.status = Status.WAITING;
				ProcessNode sender = receiveToken();
				if (sender != null) {
					// System.out.println("Process Node[" + this.nodeID
					// + "] Received Token from Process Node["
					// + sender.nodeID + "]");

				}

			} else {
				break;
			}
		}
		this.silentNeigh = findSilentNeighbour();
		if (this.status != Status.TOKEN_SENT) {
			if (silentNeigh != null) {
				if (this.status != Status.TOKEN_SENT) {
					// System.out.println("Process Node[" + this.nodeID
					// + "] found its silent neighbour:Process Node["
					// + silentNeigh.nodeID + "]");
					send(silentNeigh, MessageType.TOKEN);
					this.status = Status.TOKEN_SENT;
					// System.out
					// .println("Process Node["
					// + this.nodeID
					// + "] sent token to its silent neighbour:Process Node["
					// + silentNeigh.nodeID + "]");
				}
			}
		}
		ProcessNode sender = receiveToken();
		if (sender != null && silentNeigh != null && sender == silentNeigh) {
			// System.out.println("Process Node[" + this.nodeID
			// + "] Received Token from Silent Neighbour Process Node["
			// + sender.nodeID + "]");
			System.out.println("Process Node[" + this.nodeID + "] Decides");
			this.status = Status.DECIDED;
			deffuse();
		}
	}

	public void deffuse() {
		Iterator<Node> neighItr = neigh.iterator();
		while (neighItr.hasNext()) {
			ProcessNode node = (ProcessNode) neighItr.next();
			if (node != silentNeigh) {
				this.send(node, MessageType.TOKEN);
			}

		}
	}

	public void doStep() {
		switch (this.status) {
		case DECIDED:
			break;
		case TOKEN_SENT:
			ProcessNode sender = receiveToken();
			if (sender != null && sender == silentNeigh)
				decide();
			break;
		default:
			while (!messageQueue.isEmpty() && getNonRecCount() > 1) {
				this.status = Status.WAITING;
				receiveToken();
			}
			this.silentNeigh = findSilentNeighbour();
			if (this.silentNeigh != null && this.status != Status.TOKEN_SENT) {
				send(this.silentNeigh, MessageType.TOKEN);
				this.status = Status.TOKEN_SENT;
			}
			break;

		}

	}

	public void decide() {
		this.status = Status.DECIDED;
		System.out.println("Process Node[" + this.nodeID + "] Decides");
		if (runDiffusion)
		{
			deffuse();
		}
	}

}
