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

	private enum status {
		WAITING, HAS_SILENT_NEIGH, DECIDED
	};

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

		//while (true) {

			ProcessNode sender = receiveToken();
			if (sender == silentNeigh) {
				System.out
						.println("Process Node["
								+ this.nodeID
								+ "] Received Token from Silent Neighbour Process Node["
								+ sender.nodeID + "]");
				System.out.println("Process Node[" + this.nodeID + "] Decides");
			} 

		//}

	}

}
