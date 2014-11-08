package com.technipun.ds;

/**
 * This class defines a Node which is the base for WaveProcessNode.
 *
 * @author Nipun Balan Thekkummal
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The Class Node which is the base class for process nodes.
 */
public class Node {

	/** Integer variable for unique nodeID */
	public int nodeID;

	/** Pointer to parent Node */
	private Node parent;

	/** Array List carrying the pointers to the child nodes */
	protected ArrayList<Node> neigh;

	/**
	 * Constructor method
	 */
	public Node() {
		this.neigh = new ArrayList<Node>();
	}

	//
	/**
	 * Constructor takes the nodeID and creates an empty ArrayList to store neighbour.
	 *
	 * @param ID
	 *            The node ID for the new node
	 */
	public Node(int ID) {
		nodeID = ID;
		this.neigh = new ArrayList<Node>();
	}

	/**
	 * Method to get the neighboring nodes (as an ArrayList of type Node)
	 *
	 * @return The Array list of neighboring nodes
	 */
	public ArrayList<Node> getNeigh() {
		if (this.neigh != null) {
			return this.neigh;
		} else {
			return new ArrayList<Node>();
		}
	}

	/**
	 * Gets the NodeID
	 *
	 * @return The node ID
	 */
	public int getNodeID() {
		return this.nodeID;
	}

	/**
	 * Sets the NodeID.
	 *
	 * @param nodeID
	 *            the new node id
	 */
	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}

	/**
	 * Gets the parent node.
	 *
	 * @return the parent
	 */
	public Node getParent() {
		return this.parent;
	}

	/**
	 * Adds a neighbor to the node.
	 *
	 * @param newNeigh
	 *            The new neighboring node
	 */
	public void addNeigh(Node newNeigh) {
		if (!isNeighbour(newNeigh)) {
			this.neigh.add(newNeigh);
			newNeigh.addNeigh(this);
			newNeigh.setParent(this);
		}

	}

	/**
	 * Removes the all neighbors.
	 */
	public void removeAllNeighbours() {
		Iterator<Node> neighItr = neigh.iterator();
		while (neighItr.hasNext()) {
			neighItr.next().setParent(null);
		}
		this.neigh.clear();
	}

	/**
	 * Removes the neighbor with given node ID.
	 *
	 * @param rmNodeID
	 *            the nodeID of the node to be removed
	 */
	public void removeNeigh(int rmNodeID) {
		Iterator<Node> neighItr = neigh.iterator();
		while (neighItr.hasNext()) {
			Node currNode = neighItr.next();
			if (currNode.nodeID == rmNodeID) {
				currNode.setParent(null);
				neighItr.remove();
			}
		}
	}

	/**
	 * Sets the parent of the node.
	 *
	 * @param parent
	 *            the new parent
	 */
	private void setParent(Node parent) {
		this.parent = parent;
	}

	/**
	 * Checks if the given node is a neighbor.
	 *
	 * @param node
	 *            the node
	 * @return true, if is neighbor
	 */
	private boolean isNeighbour(Node node) {
		return neigh.contains(node);
	}

}
