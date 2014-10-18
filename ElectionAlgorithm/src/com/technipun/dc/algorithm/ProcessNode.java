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
	private boolean initiator;
	private boolean wStatus;
	private int wNeighCount;
	private boolean candidate;

	/**
	 * @return the candidate
	 */
	public boolean isCandidate() {
		return candidate;
	}

	/**
	 * @param candidate the candidate to set
	 */
	public void setCandidate(boolean candidate) {
		this.candidate = candidate;
	}

	/**
	 * @return the initiator
	 */
	public boolean isInitiator() {
		return initiator;
	}

	/**
	 * @param initiator
	 *            the initiator to set
	 */
	public void setInitiator(boolean initiator) {
		this.initiator = initiator;
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

	private enum Status {
		INIT, WAITING, DECIDED, TOKEN_SENT
	};

	private enum State {
		SLEEP, LEADER, LOST
	}

	private Status status;
	private State state;

	public void addMessage(Message msg) {
		messageQueue.add(msg);
	}

	public void init(boolean runDiffusion) {
		this.status = Status.INIT;
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
			if (!recnode.isReceived()) {
				if (this.status != Status.TOKEN_SENT) {
					System.out.println("Process Node[" + this.nodeID
							+ "] found its silent neighbour:Process Node["
							+ recnode.getNode().nodeID + "]");
				}
				return recnode.getNode();
			}
		}
		return null;
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

	public boolean doStep() {
		if (status == Status.DECIDED) {
			return false;
		}
		System.out.println("[---------Process Node[" + nodeID
				+ "]------------]");
		while (!messageQueue.isEmpty() && getNonRecCount() > 1) {
			this.status = Status.WAITING;
			receiveToken();
		}
		this.silentNeigh = findSilentNeighbour();
		if (this.silentNeigh != null && this.status != Status.TOKEN_SENT) {
			send(this.silentNeigh, MessageType.TOKEN);
			this.status = Status.TOKEN_SENT;
		}
		if (status == Status.TOKEN_SENT) {
			ProcessNode sender = receiveToken();
			if (sender != null && sender == silentNeigh) {
				decide();
				return true;
			}
		}
		return false;
	}

	public void decide() {
		this.status = Status.DECIDED;
		System.out.println("Process Node[" + this.nodeID + "] Decides");
		if (runDiffusion) {
			deffuse();
		}
	}

	public void doElectionStep() {
		if (this.isInitiator()) {
			wakeupNwakeupNeigh();
		}
		while (!messageQueue.isEmpty() && wNeighCount < neigh.size()) {
			boolean received=receiveWakeup();
			if (received)
			{
				wakeupNwakeupNeigh();
			}
		}
		
		if (wNeighCount == neigh.size())
		{
			doStep();
		}
		
		
	}

	private void wakeupNwakeupNeigh() {
		if (!this.wStatus) {
			this.wStatus = true;
			System.out.println("ProcessNode["+nodeID+"] woke up!");
			Iterator<Node> neighItr = neigh.iterator();
			while (neighItr.hasNext()) {
				ProcessNode neighNode = (ProcessNode) neighItr.next();
				send(neighNode, MessageType.WAKEUP);
			}
		}
	}

	private boolean receiveWakeup() {
		Message msg = messageQueue.poll();
		if (msg != null && msg.header.getMsgType() == MessageType.WAKEUP) {
			System.out.println("ProcessNode["+nodeID+"] got wakeup token from ProcessNode["+msg.header.getSender().nodeID+"]");
			wNeighCount++;
			return true;
		} else {
			return false;
		}
	}

}
