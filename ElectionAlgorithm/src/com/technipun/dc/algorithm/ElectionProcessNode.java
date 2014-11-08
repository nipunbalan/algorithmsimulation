package com.technipun.dc.algorithm;

import java.util.Iterator;
import com.technipun.ds.Node;

/**
 * This class defines a Election Process node extending the Wave Process node.
 *
 * @author Nipun Balan Thekkummal
 * @version 1.0
 */

public class ElectionProcessNode extends WaveProcessNode {

	/** The initiator. */
	private boolean initiator;

	/** The wake up status. True, if woken up else false. */
	private boolean wStatus;

	/**
	 * The number of awake neighbours (number of neighbours from which it got
	 * wake up token.
	 */
	private int wNeighCount;

	/** The flag indicating whether teh node is a candidate */
	private boolean candidate;

	/** The current leading candidate. */
	private WaveProcessNode leadingCandidate;

	/**
	 * The Enum State.
	 */
	private enum State {

		/** The sleep. */
		SLEEP,
		/** The leader. */
		LEADER,
		/** The lost. */
		LOST
	}

	/** The state. */
	private State state;

	/**
	 * Instantiates a new election process node.
	 *
	 * @param nodeID
	 *            the node id
	 */
	public ElectionProcessNode(int nodeID) {
		super(nodeID);
	}

	/**
	 * Checks if is candidate.
	 *
	 * @return the candidate
	 */
	public boolean isCandidate() {
		return candidate;
	}

	/**
	 * Sets the candidate.
	 *
	 * @param candidate
	 *            the candidate to set
	 */
	public void setCandidate(boolean candidate) {
		this.candidate = candidate;
	}

	/**
	 * Checks if is initiator.
	 *
	 * @return the initiator
	 */
	public boolean isInitiator() {
		return initiator;
	}

	/**
	 * Sets the initiator.
	 *
	 * @param initiator
	 *            the initiator to set
	 */
	public void setInitiator(boolean initiator) {
		this.initiator = initiator;
	}

	/**
	 * Initializes the wave process node. overriding the wave process
	 * initialization
	 *
	 * @param runDiffusion
	 *            the run diffusion flag
	 */
	@Override
	public void init(boolean runDiffusion) {
		// Initializes the super class, wave process node
		super.init(runDiffusion);
		// Setting wake up status to false
		wStatus = false;

		// Setting awake neighbour count to 0
		wNeighCount = 0;

		this.state = State.SLEEP;
		// Setting leading Candidate variable to null and then to itself if its
		// a candidate.
		this.leadingCandidate = null;
		if (this.isCandidate()) {
			this.leadingCandidate = this;
		}
	}

	/**
	 * Decide action for election process. Overrides the wave process decision
	 * method.
	 */
	@Override
	public void decide() {

		// If leading Candidate at the point of decision is it self, it the
		// state as Leader. Else Lost. If its not a candidate, mark the state
		// as lost
		if (this.leadingCandidate == this) {
			this.state = State.LEADER;
			System.out.println("Decides itself as leader");
		} else if (this.isCandidate()) {
			this.state = State.LOST;
		} else {
			this.state = State.SLEEP;
		}
		// Mark the status as decided
		this.status = Status.DECIDED;

		// Display neccessary console messages
		if (this.state != State.LEADER) {
			if (leadingCandidate == null) {
				System.out.println("Decides No Leader");
			} else {
				System.out.println("Decides ProcessNode["
						+ leadingCandidate.nodeID + "] as leader");
			}
		}

		if (runDiffusion) {
			deffuse();
		}
	}

	/**
	 * Wakeup itself and wakeup the neighbouring nodes
	 */
	private void wakeupNwakeupNeigh() {
		if (!this.wStatus) {
			this.wStatus = true;
			System.out.println("Woke up!");
			System.out.println("Flooding <wakeup> to neighbours");
			Iterator<Node> neighItr = neigh.iterator();
			while (neighItr.hasNext()) {
				ElectionProcessNode neighNode = (ElectionProcessNode) neighItr
						.next();
				send(neighNode, MessageType.WAKEUP);
				this.hadSendAction = true;
			}
		}
	}

	/**
	 * Receive wakeup token. Checks the queue for wake up tokens only. If any
	 * normal token is found, its pushed into token queue
	 *
	 * @return true, if successful
	 */
	private boolean receiveWakeup() {
		Message msg = messageQueue.poll();
		if (msg != null && msg.header.getMsgType() == MessageType.WAKEUP) {
			System.out.println("Got wakeup token from ProcessNode["
					+ msg.header.getSender().nodeID + "]");
			wNeighCount++;
			this.hadReceiveAction = true;
			return true;
		} else if (msg != null && msg.header.getMsgType() == MessageType.TOKEN) {
			tokenQueue.add(msg);
		}
		return false;
	}

	/**
	 * Election logic. This can be changed as per the logic required for
	 * election. In this case, candidate with lowest nodeID wins
	 *
	 * @param ldCandidate
	 *            the ld candidate
	 * @param message
	 *            the message
	 * @return the election process node
	 */
	private ElectionProcessNode elect(ElectionProcessNode ldCandidate,
			Message message) {
		ElectionProcessNode neighCandidNode = (ElectionProcessNode) message
				.getMessage();
		// If message contains no node id. returns the current leading candidate
		// as candidate.
		if (neighCandidNode == null) {
			return ldCandidate;
		}
		// if current leading candidate variable is null or received candidate
		// is less than current candidate, return received candidate
		if (ldCandidate == null || neighCandidNode.nodeID <= ldCandidate.nodeID) {
			return neighCandidNode;
		} else {
			return ldCandidate;
		}
	}

	/**
	 * Send token for election process. Overrides the Wave process' method
	 *
	 * @param recepient
	 *            the recepient
	 * @param messageType
	 *            the message type
	 */
	@Override
	protected void sendToken(WaveProcessNode recepient, MessageType messageType) {

		String leadingNodeStr;
		if (this.leadingCandidate == null) {
			leadingNodeStr = "Null";
		} else {
			leadingNodeStr = "Node[" + this.leadingCandidate.nodeID + "]";
		}

		System.out.println("Sending token <tok," + leadingNodeStr
				+ "> to neighbour ProcessNode[" + recepient.nodeID + "]");
		send(recepient, messageType, (WaveProcessNode) this.leadingCandidate);
	}

	/**
	 * Do process. Method which processes the message. In this case, executes
	 * the election method. Overrides the dummy do process in wave process node
	 *
	 * @param msg
	 *            the message
	 */
	@Override
	protected void doProcess(Message msg) {
		if (msg != null) {
			leadingCandidate = elect((ElectionProcessNode) leadingCandidate,
					msg);
		}
	}

	/**
	 * Executes the election process step. Implements election algorithm logic
	 *
	 * @return true, if successful
	 */
	public boolean doElectionStep() {
		// this.hadReceiveAction=false;
		// this.hadReceiveAction=false;

		// If the node is an initiator. Wake up and wakeup neighbours
		if (this.isInitiator()) {
			wakeupNwakeupNeigh();
		}
		if (!wStatus) {
			System.out.println("Is sleeping");
		}
		// If message queue is not empty and node hasn't received wakeup from
		// all neighbours, receive the wake up call
		while (!messageQueue.isEmpty() && wNeighCount < neigh.size()) {
			boolean received = receiveWakeup();
			if (received) {
				// Waking up neighbours
				wakeupNwakeupNeigh();
			}
		}

		// If it received wake up from all neighbours, execute wave algorithm(
		// with election logic )
		if (wNeighCount == neigh.size()) {
			return doWaveStep();
		}
		return false;
	}

}
