package com.technipun.dc.algorithm;

import java.util.ArrayList;

import com.technipun.ds.Node;

public class Tree {

	private ArrayList<Node> treeNodes;

	public Tree() {
		treeNodes = new ArrayList<Node>();
	}

	public void addNode(Node node) {
		treeNodes.add(node);
	}

	public void removeNode(Node node) {
		treeNodes.remove(node);
	}
	
	public void createNode(int nodeID)
	{
		
	}

}
