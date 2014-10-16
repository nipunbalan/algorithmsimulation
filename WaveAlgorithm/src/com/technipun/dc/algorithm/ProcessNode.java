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

	private enum Status {
		WAITING, HAS_SILENT_NEIGH, DECIDED, TOKEN_SENT
	};

	private Status status;

	public void addMessage(Message msg) {
		messageQueue.add(msg);
	}

	public void init() {

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
					System.out.println("Process Node[" + this.nodeID
							+ "] Received Token from Process Node["
							+ sender.nodeID + "]");

				}

			} else {
				break;
			}
		}
		this.silentNeigh = findSilentNeighbour();
		if (this.status != Status.TOKEN_SENT) {
			if (silentNeigh != null) {
				this.status = Status.HAS_SILENT_NEIGH;
				if (this.status != Status.TOKEN_SENT) {
					System.out.println("Process Node[" + this.nodeID
							+ "] found its silent neighbour:Process Node["
							+ silentNeigh.nodeID + "]");
					send(silentNeigh, MessageType.TOKEN);
					this.status = Status.TOKEN_SENT;
					System.out
							.println("Process Node["
									+ this.nodeID
									+ "] sent token to its silent neighbour:Process Node["
									+ silentNeigh.nodeID + "]");
				}
			}
		}
		ProcessNode sender = receiveToken();
		if (sender != null && silentNeigh != null && sender == silentNeigh) {
			System.out.println("Process Node[" + this.nodeID
					+ "] Received Token from Silent Neighbour Process Node["
					+ sender.nodeID + "]");
			System.out.println("Process Node[" + this.nodeID + "] Decides");
			this.status = Status.DECIDED;
			defuse();
		}
	}
	
	public void defuse()
	{
		Iterator<Node> neighItr = neigh.iterator();
		while(neighItr.hasNext())
		{
			ProcessNode node=(ProcessNode) neighItr.next();
			if(node!=silentNeigh)
			{
				this.send(node, MessageType.TOKEN);
			}
			
		}
	}
	

}
