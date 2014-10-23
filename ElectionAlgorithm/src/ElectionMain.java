import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.technipun.dc.algorithm.ElectionProcessNode;
import com.technipun.dc.algorithm.WaveProcessNode;
import com.technipun.dc.algorithm.Tree;
import com.technipun.ds.Node;

public class ElectionMain {

	private static ArrayList<WaveProcessNode> waveTreeNodes;
	private static ArrayList<ElectionProcessNode> electionTreeNodes;

	private static Tree<WaveProcessNode> waveTree;
	private static Tree<ElectionProcessNode> electionTree;

	public static void main(String args[]) {

		switch (args[0].toLowerCase()) {
		case "unbal": {
			loadUnBalTree(args[1]);
			break;
		}
		case "bal": {
			loadBalTree(args[1]);
			break;
		}
		case "arb": {
			loadArbTree(args[1]);
			break;
		}
		}

		switch (args[2].toLowerCase().substring(0, 1)) {
		case "y": {
			runAlgorithm(true, args[1]);
			break;
		}
		case "n": {
			runAlgorithm(false, args[1]);
			break;
		}
		}

	}

	// public void genRandTree(int nodeCount) {
	//
	// tree = new Tree();
	// int x = nodeCount;
	// int y = x - 1;
	// int rand;
	// int[] myNumbers = new int[x];
	// Random randomGenerator = new Random();
	// // int i = 0;
	// int i = 0;
	// int j = 1;
	//
	// while (y > 0) {
	// if (y > 1) {
	// rand = randomGenerator.nextInt(y - 1) + 1;
	// } else {
	// rand = 1;
	// y--;
	// }
	// y = y - rand;
	// System.out.println("Random no." + rand);
	// for (int k = rand; k > 0; k--) {
	// if (myNumbers[i] != 1) {
	// System.out.println("[" + i + "]:-->" + j);
	// j++;
	// }
	// }
	// i++;
	// }
	//
	// }

	public static void loadBalTree(String processType) {
		if (processType.equalsIgnoreCase("wave")) {
			waveTree = new Tree<WaveProcessNode>();
			for (int i = 0; i <= 14; i++) {
				// System.out.println(i);
				WaveProcessNode node = new WaveProcessNode(i);
				waveTree.addNode(node);
			}
			waveTreeNodes = waveTree.getAllNodes();
			waveTreeNodes.get(0).addNeigh(waveTreeNodes.get(1));
			waveTreeNodes.get(0).addNeigh(waveTreeNodes.get(2));
			waveTreeNodes.get(1).addNeigh(waveTreeNodes.get(3));
			waveTreeNodes.get(1).addNeigh(waveTreeNodes.get(4));
			waveTreeNodes.get(2).addNeigh(waveTreeNodes.get(5));
			waveTreeNodes.get(2).addNeigh(waveTreeNodes.get(6));
			waveTreeNodes.get(3).addNeigh(waveTreeNodes.get(7));
			waveTreeNodes.get(3).addNeigh(waveTreeNodes.get(8));
			waveTreeNodes.get(4).addNeigh(waveTreeNodes.get(9));
			waveTreeNodes.get(4).addNeigh(waveTreeNodes.get(10));
			waveTreeNodes.get(5).addNeigh(waveTreeNodes.get(11));
			waveTreeNodes.get(5).addNeigh(waveTreeNodes.get(12));
			waveTreeNodes.get(6).addNeigh(waveTreeNodes.get(13));
			waveTreeNodes.get(6).addNeigh(waveTreeNodes.get(14));

		} else if (processType.equalsIgnoreCase("election")) {
			electionTree = new Tree<ElectionProcessNode>();
			for (int i = 0; i <= 14; i++) {
				// System.out.println(i);
				ElectionProcessNode node = new ElectionProcessNode(i);
				electionTree.addNode(node);
			}
			electionTreeNodes = electionTree.getAllNodes();
			electionTreeNodes.get(0).addNeigh(electionTreeNodes.get(1));
			electionTreeNodes.get(0).addNeigh(electionTreeNodes.get(2));
			electionTreeNodes.get(1).addNeigh(electionTreeNodes.get(3));
			electionTreeNodes.get(1).addNeigh(electionTreeNodes.get(4));
			electionTreeNodes.get(2).addNeigh(electionTreeNodes.get(5));
			electionTreeNodes.get(2).addNeigh(electionTreeNodes.get(6));
			electionTreeNodes.get(3).addNeigh(electionTreeNodes.get(7));
			electionTreeNodes.get(3).addNeigh(electionTreeNodes.get(8));
			electionTreeNodes.get(4).addNeigh(electionTreeNodes.get(9));
			electionTreeNodes.get(4).addNeigh(electionTreeNodes.get(10));
			electionTreeNodes.get(5).addNeigh(electionTreeNodes.get(11));
			electionTreeNodes.get(5).addNeigh(electionTreeNodes.get(12));
			electionTreeNodes.get(6).addNeigh(electionTreeNodes.get(13));
			electionTreeNodes.get(6).addNeigh(electionTreeNodes.get(14));

		}

	}

	public static void loadArbTree(String processType) {
		if (processType.equalsIgnoreCase("wave")) {
			waveTree = new Tree<WaveProcessNode>();
			for (int i = 0; i <= 7; i++) {
				// System.out.println(i);
				WaveProcessNode node = new WaveProcessNode(i);
				waveTree.addNode(node);
			}

			waveTreeNodes = waveTree.getAllNodes();
			waveTreeNodes.get(0).addNeigh(waveTreeNodes.get(1));
			waveTreeNodes.get(1).addNeigh(waveTreeNodes.get(2));
			waveTreeNodes.get(1).addNeigh(waveTreeNodes.get(3));
			waveTreeNodes.get(2).addNeigh(waveTreeNodes.get(4));
			waveTreeNodes.get(2).addNeigh(waveTreeNodes.get(5));
			waveTreeNodes.get(2).addNeigh(waveTreeNodes.get(6));
			waveTreeNodes.get(3).addNeigh(waveTreeNodes.get(7));

		} else if (processType.equalsIgnoreCase("election")) {
			electionTree = new Tree<ElectionProcessNode>();
			for (int i = 0; i <= 7; i++) {
				// System.out.println(i);
				ElectionProcessNode node = new ElectionProcessNode(i);
				electionTree.addNode(node);
			}

			electionTreeNodes = electionTree.getAllNodes();
			electionTreeNodes.get(0).addNeigh(electionTreeNodes.get(1));
			electionTreeNodes.get(1).addNeigh(electionTreeNodes.get(2));
			electionTreeNodes.get(1).addNeigh(electionTreeNodes.get(3));
			electionTreeNodes.get(2).addNeigh(electionTreeNodes.get(4));
			electionTreeNodes.get(2).addNeigh(electionTreeNodes.get(5));
			electionTreeNodes.get(2).addNeigh(electionTreeNodes.get(6));
			electionTreeNodes.get(3).addNeigh(electionTreeNodes.get(7));

		}

	}

	public static void loadUnBalTree(String processType) {
		if (processType.equalsIgnoreCase("wave")) {
			waveTree = new Tree<WaveProcessNode>();
			for (int i = 0; i <= 8; i++) {
				// System.out.println(i);
				WaveProcessNode node = new WaveProcessNode(i);
				waveTree.addNode(node);
			}
			waveTreeNodes = waveTree.getAllNodes();
			waveTreeNodes.get(0).addNeigh(waveTreeNodes.get(1));
			waveTreeNodes.get(0).addNeigh(waveTreeNodes.get(2));
			waveTreeNodes.get(1).addNeigh(waveTreeNodes.get(3));
			waveTreeNodes.get(1).addNeigh(waveTreeNodes.get(4));
			waveTreeNodes.get(4).addNeigh(waveTreeNodes.get(5));
			waveTreeNodes.get(4).addNeigh(waveTreeNodes.get(6));
			waveTreeNodes.get(2).addNeigh(waveTreeNodes.get(7));
			waveTreeNodes.get(7).addNeigh(waveTreeNodes.get(8));

		} else if (processType.equalsIgnoreCase("election")) {
			electionTree = new Tree<ElectionProcessNode>();
			for (int i = 0; i <= 8; i++) {
				// System.out.println(i);
				ElectionProcessNode node = new ElectionProcessNode(i);
				electionTree.addNode(node);
			}
			electionTreeNodes = electionTree.getAllNodes();
			electionTreeNodes.get(0).addNeigh(electionTreeNodes.get(1));
			electionTreeNodes.get(0).addNeigh(electionTreeNodes.get(1));
			electionTreeNodes.get(0).addNeigh(electionTreeNodes.get(2));
			electionTreeNodes.get(1).addNeigh(electionTreeNodes.get(3));
			electionTreeNodes.get(1).addNeigh(electionTreeNodes.get(4));
			electionTreeNodes.get(4).addNeigh(electionTreeNodes.get(5));
			electionTreeNodes.get(4).addNeigh(electionTreeNodes.get(6));
			electionTreeNodes.get(2).addNeigh(electionTreeNodes.get(7));
			electionTreeNodes.get(7).addNeigh(electionTreeNodes.get(8));
		}

	}

	public static void runAlgorithm(boolean diffuse, String processType) {
		Random randomGenerator = new Random();
		int totalStepstoCmplete = 0;

		if (processType.equalsIgnoreCase("election")) {

			for (ElectionProcessNode enode : electionTreeNodes) {
				for (Node eneighNode : enode.getNeigh()) {
					System.out
							.println(enode.nodeID + "-->" + eneighNode.nodeID);
				}
			}
			int initiatorCount = 0;
			int randCandCount = randomGenerator.nextInt(electionTreeNodes
					.size() - 1) + 1;
			System.out.println("Tree has " + randCandCount
					+ " candidate(s) (random)");
			int c = randCandCount;
			while (c > 0) {
				int randCandNode = randomGenerator.nextInt(electionTreeNodes
						.size());
				if (!electionTreeNodes.get(randCandNode).isCandidate()) {
					electionTreeNodes.get(randCandNode).setCandidate(true);
					System.out.println("Node[" + randCandNode
							+ "] is choosen as candidate");
					c--;
				}
			}

			while (initiatorCount < 1) {
				Iterator<ElectionProcessNode> treenodeItr = electionTreeNodes
						.iterator();
				while (treenodeItr.hasNext()) {
					ElectionProcessNode node = treenodeItr.next();
					int randnum = randomGenerator.nextInt(2);
					if (node.isCandidate() && randnum == 1) {
						node.setInitiator(true);
						System.out
								.println("Node["
										+ node.nodeID
										+ "] is randomly choosen as election initiator");
						initiatorCount++;

					}
				}
			}

			Iterator<ElectionProcessNode> nodeItr = electionTreeNodes
					.iterator();
			while (nodeItr.hasNext()) {
				nodeItr.next().init(diffuse);
			}

			int i;
			int decideCount = 0;

			for (i = 0; i < electionTreeNodes.size() * 100; i++) {
				int j = randomGenerator.nextInt(electionTreeNodes.size());
				// System.out.println(j);
				// boolean decided = treenodes.get(j).doStep();
				// System.out.println(j);
				boolean decided = electionTreeNodes.get(j).doElectionStep();
				if (decided) {
					decideCount++;
				}
				if ((diffuse && decideCount == electionTreeNodes.size())
						|| !diffuse && decideCount == 2) {

					if (totalStepstoCmplete == 0) {
						totalStepstoCmplete = i + 1;
					}
					// break;
				}
			}
			System.out.println("Algorithm completed in " + totalStepstoCmplete
					+ " iterations");
		} else if (processType.equalsIgnoreCase("wave")) {

			for (WaveProcessNode enode : waveTreeNodes) {
				for (Node eneighNode : enode.getNeigh()) {
					System.out
							.println(enode.nodeID + "-->" + eneighNode.nodeID);
				}
			}
			int i;
			int decideCount = 0;
			Iterator<WaveProcessNode> nodeItr = waveTreeNodes.iterator();
			while (nodeItr.hasNext()) {
				nodeItr.next().init(diffuse);
			}

			for (i = 0; i < waveTreeNodes.size() * 100; i++) {
				int j = randomGenerator.nextInt(waveTreeNodes.size());
				// System.out.println(j);
				// boolean decided = treenodes.get(j).doStep();
				// System.out.println(j);
				boolean decided = waveTreeNodes.get(j).doWaveStep();
				if (decided) {
					decideCount++;
				}
				if ((diffuse && decideCount == waveTreeNodes.size())
						|| !diffuse && decideCount == 2) {
					if (totalStepstoCmplete == 0) {
						totalStepstoCmplete = i + 1;
					}
					// break;
				}
			}
			System.out.println("Algorithm completed in " + totalStepstoCmplete
					+ " iterations");

		}
	}
}
