package com.technipun.dc.algorithm;


import java.util.ArrayList;

import com.technipun.ds.Node;

public class Tree<T> {

	private ArrayList<T> treeNodes;

	public Tree() {
		treeNodes = new ArrayList<T>();
	}

	public void addNode(T node) {
		treeNodes.add(node);
	}

	public void removeNode(T node) {
		treeNodes.remove(node);
	}
	
	public ArrayList<T> getAllNodes()
	{
		return treeNodes;
	}
	
}
