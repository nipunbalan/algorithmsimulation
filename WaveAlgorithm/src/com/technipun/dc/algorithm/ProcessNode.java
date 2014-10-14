package com.technipun.dc.algorithm;

import java.util.ArrayList;
import java.util.Iterator;

import com.technipun.ds.MessageQueue;
import com.technipun.ds.Node;

public class ProcessNode extends Node {

	public ProcessNode() {
	}

	private ArrayList<ReceiveIndicator> receiveVector;

	private MessageQueue messageQueue;
	
	private enum status{WAITING,HAS_SILENT_NEIGH,SEND_TOKEN,DECIDED};

	public void addMessage(Message msg) {
		messageQueue.add(msg);
	}

	public void init() {
		receiveVector = new ArrayList<ReceiveIndicator>();
		Iterator<Node> neighItr = neigh.iterator();
		while (neighItr.hasNext())
			receiveVector.add(new ReceiveIndicator((ProcessNode) neighItr
					.next()));
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

	private int getRecCount() {
		int recCount = 1;
		Iterator<ReceiveIndicator> recItr = receiveVector.iterator();
		while (recItr.hasNext()) {
			ReceiveIndicator recnode = recItr.next();
			if (recnode.isReceived())
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
		init();
		while (getRecCount() > 1) {

			ProcessNode sender = receiveToken();
			if (sender != null) {
				System.out.println("Process Node[" + this.nodeID
						+ "] Received Token from Process Node[" + sender.nodeID
						+ "]");
			} else {
				try {
					wait(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
		ProcessNode silentNeigh =findSilentNeighbour();
		System.out.println("Process Node[" + this.nodeID
				+ "] found its silent neighbour:Process Node["+silentNeigh.nodeID+"]");
		send(silentNeigh,MessageType.TOKEN);
	}
	
}
