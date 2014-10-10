package com.technipun.ds;

import java.util.ArrayList;
import java.util.Iterator;

public class Node {

	private int nodeID;
	private ArrayList<Node> neigh;

	public Node(int nodeID) {
		this.setNodeID(nodeID);
		this.setNeigh(new ArrayList<Node>());
	}

	public int getNodeID() {
		return nodeID;
	}

	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}

	public ArrayList<Node> getNeigh() {
		return neigh;
	}

	public void setNeigh(ArrayList<Node> neigh) {
		this.neigh = neigh;
	}

}
