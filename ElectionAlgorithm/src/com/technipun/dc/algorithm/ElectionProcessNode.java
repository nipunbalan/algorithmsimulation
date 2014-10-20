package com.technipun.dc.algorithm;

import java.util.Iterator;

import com.technipun.ds.Node;

public class ElectionProcessNode extends WaveProcessNode {

	private boolean initiator;
	private boolean wStatus;
	private int wNeighCount;
	private boolean candidate;
	private WaveProcessNode leadingCandidate;

	private enum State {
		SLEEP, LEADER, LOST
	}

	private State state;
	
	
	public ElectionProcessNode(int nodeID) {
		super(nodeID);
	}

	/**
	 * @return the candidate
	 */
	public boolean isCandidate() {
		return candidate;
	}

	/**
	 * @param candidate
	 *            the candidate to set
	 */
	public void setCandidate(boolean candidate) {
		this.candidate = candidate;
	}

	/**
	 * @return the initiator
	 */
	public boolean isInitiator() {
		return initiator;
	}

	/**
	 * @param initiator
	 *            the initiator to set
	 */
	public void setInitiator(boolean initiator) {
		this.initiator = initiator;
	}

	@Override
	public void init(boolean runDiffusion) {
		super.init(runDiffusion);
		this.state = State.SLEEP;
		if (this.isCandidate()) {
			this.leadingCandidate = this;
		}
	}

	@Override
	public void decide() {
		if (this.leadingCandidate == this) {
			this.state = State.LEADER;
			System.out.println("ProcessNode[" + this.nodeID
					+ "] Decides itself as leader");
		} else if (this.isCandidate()) {
			this.state = State.LOST;
		} else {
			this.state = State.SLEEP;
		}
		this.status = Status.DECIDED;
		if (this.state != State.LEADER) {
			if (leadingCandidate == null) {
				System.out.println("ProcessNode[" + this.nodeID
						+ "] Decides No Leader");
			} else {
				System.out.println("ProcessNode[" + this.nodeID
						+ "] Decides ProcessNode[" + leadingCandidate.nodeID
						+ "] as leader");
			}
		}

		if (runDiffusion) {
			deffuse();
		}
	}

	private void wakeupNwakeupNeigh() {
		if (!this.wStatus) {
			this.wStatus = true;
			System.out.println("ProcessNode[" + nodeID + "] woke up!");
			System.out.println("ProcessNode[" + nodeID
					+ "] flooding <wakeup> to neighbours");
			Iterator<Node> neighItr = neigh.iterator();
			while (neighItr.hasNext()) {
				ElectionProcessNode neighNode = (ElectionProcessNode) neighItr
						.next();
				send(neighNode, MessageType.WAKEUP);
			}
		}
	}

	private boolean receiveWakeup() {
		Message msg = messageQueue.poll();
		if (msg != null && msg.header.getMsgType() == MessageType.WAKEUP) {
			System.out.println("ProcessNode[" + nodeID
					+ "] got wakeup token from ProcessNode["
					+ msg.header.getSender().nodeID + "]");
			wNeighCount++;
			return true;
		} else if (msg != null && msg.header.getMsgType() == MessageType.TOKEN) {
			tokenQueue.add(msg);
		}
		return false;
	}

	private ElectionProcessNode elect(ElectionProcessNode ldCandidate,
			Message message) {
		ElectionProcessNode neighCandidNode = (ElectionProcessNode) message
				.getMessage();
		if (neighCandidNode == null) {
			return ldCandidate;
		}
		if (ldCandidate == null || neighCandidNode.nodeID <= ldCandidate.nodeID) {
			return neighCandidNode;
		} else {
			return ldCandidate;
		}
	}

	@Override
	protected void sendToken(WaveProcessNode recepient, MessageType messageType) {
		send(recepient, messageType, (WaveProcessNode) this.leadingCandidate);
		String leadingNodeStr;
		if (this.leadingCandidate == null) {
			leadingNodeStr = "Null";
		} else {
			leadingNodeStr = "Node[" + this.leadingCandidate.nodeID + "]";
		}

		System.out.println("ProcessNode[" + nodeID + "] sending token <tok,"
				+ leadingNodeStr + "> to neighbour ProcessNode["
				+ recepient.nodeID + "]");
	}

	@Override
	protected void doProcess(Message msg) {
		if (msg != null) {
			leadingCandidate = elect((ElectionProcessNode) leadingCandidate,
					msg);
		}
	}

	public boolean doElectionStep() {
		if (this.isInitiator()) {
			wakeupNwakeupNeigh();
		}
		while (!messageQueue.isEmpty() && wNeighCount < neigh.size()) {
			boolean received = receiveWakeup();
			if (received) {
				wakeupNwakeupNeigh();
			}
		}
		if (wNeighCount == neigh.size()) {
			return doWaveStep();
		}
		return false;
	}

}
