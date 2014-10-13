package com.technipun.dc.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

import com.technipun.ds.Node;

public class ProcessNode extends Node {

	public ProcessNode() {
	}
	
	private ArrayList<ReceiveIndicator> receiveVector;
	
	private Queue<Message> messageQueue;
	
	
	public void init() {
		receiveVector = new ArrayList<ReceiveIndicator>();
		messageQueue = new LinkedQueue<Message>();
		Iterator<Node> neighItr = neigh.iterator();
		while (neighItr.hasNext())
			receiveVector.add(new ReceiveIndicator((ProcessNode) neighItr
					.next()));
	}
	
	
	
}
