package com.technipun.ds;

/**
 * @desc This class defines a Node
 * @author Nipun Balan Thekkummal
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.Iterator;

public class Node {

	// Integer variable for unique nodeID
	public int nodeID;

	// Pointer to parent Node
	private Node parent;

	// Array List carrying the pointers to the child nodes
	protected ArrayList<Node> neigh;
	
	public Node() {
		this.neigh = new ArrayList<Node>();
	}

	// Constructor takes the nodeID and creates an empty ArrayList to store
	// children.
	public Node(int ID) {
		nodeID = ID;
		this.neigh = new ArrayList<Node>();
	}

	// Method to get the children (as an Array List of type Node)
	public ArrayList<Node> getNeigh() {
		if (this.neigh != null) {
			return this.neigh;
		} else {

			return new ArrayList<Node>();
		}
	}

	// Getter for nodeID
	public int getNodeID() {
		return this.nodeID;
	}

	// Setter for nodeID
	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}

	//Getter for parent
	public Node getParent() {
		return this.parent;
	}

	//Method to add child node to a node
	public void addNeigh(Node newNeigh) {
		//Adding a new element to the ArrayList "children"
		this.neigh.add(newNeigh);
		newNeigh.setParent(this);
	}

	//Method to remove all child nodes.
	public void removeAllNeighbours() {
		//Iterating through the child list and removing the reference to its parents
		Iterator<Node> neighItr = neigh.iterator();
		while (neighItr.hasNext()) {
			neighItr.next().setParent(null);
		}
		//Clearing the Arraylist "children" to remove all children"
		this.neigh.clear();
	}

	//Method to remove a particular child referenced by nodeID
	public void removeNeigh(int rmNodeID) {
		//Iterator to check the nodeID in the children Array List and to delete the node if the ID matches
		Iterator<Node> neighItr = neigh.iterator();
		while (neighItr.hasNext()) {
			Node currNode = neighItr.next();
			if (currNode.nodeID == rmNodeID) {
				currNode.setParent(null);
				neighItr.remove();
			}
		}
	}

	private void setParent(Node parent) {
		this.parent = parent;
	}

}
