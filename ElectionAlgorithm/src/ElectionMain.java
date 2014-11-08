import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import com.technipun.dc.algorithm.ElectionProcessNode;
import com.technipun.dc.algorithm.WaveProcessNode;
import com.technipun.dc.algorithm.Tree;
import com.technipun.ds.Node;

/**
 * This is the main class containing static main method to start the program.
 *
 * @author Nipun Balan Thekkummal
 * @version 1.0
 */

public class ElectionMain {

	/** The wave tree nodes. */
	private static ArrayList<WaveProcessNode> waveTreeNodes;

	/** The election tree nodes. */
	private static ArrayList<ElectionProcessNode> electionTreeNodes;

	/** The wave tree. */
	private static Tree<WaveProcessNode> waveTree;

	/** The election tree. */
	private static Tree<ElectionProcessNode> electionTree;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String args[]) {

		Scanner user_input = new Scanner(System.in);
		// Displaying the main menu
		while (true) {
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out
					.println("************Distributed Algorithm Simulation*****************");
			System.out.println("	     *******Main Menu********     		");
			System.out.println("1. Load balanced binary tree");
			System.out.println("2. Load unbalanced binary tree");
			System.out.println("3. Load arbitrary tree");
			System.out.println("4. Load auto generated tree");
			System.out.println("5. Run Wave Algorithm with out diffusion");
			System.out.println("6. Run Wave Algorithm with diffusion");
			System.out.println("7. Run Election Algorithm with out diffusion");
			System.out.println("8. Run Election Algorithm with diffusion");
			System.out.println("9. Exit!");
			System.out
					.println("************************************************");
			System.out.println("");
			System.out.println("");
			System.out.print("Enter an option : ");
			String selectedOption = user_input.next();

			if (selectedOption.equalsIgnoreCase("9")) {
				System.out.println("Exiting program!!..");
				break;
			}

			switch (selectedOption.toLowerCase()) {

			// Load balanced binary tree
			case "1": {
				waveTreeNodes = new ArrayList<WaveProcessNode>();
				electionTreeNodes = new ArrayList<ElectionProcessNode>();
				loadBalTree("wave");
				loadBalTree("election");
				System.out.println("Balanced binary tree loaded!");
				for (ElectionProcessNode enode : electionTreeNodes) {
					for (Node eneighNode : enode.getNeigh()) {
						System.out.println(enode.nodeID + "-->"
								+ eneighNode.nodeID);
					}
				}
				break;
			}

			// Load unbalanced binary tree
			case "2": {
				waveTreeNodes = new ArrayList<WaveProcessNode>();
				electionTreeNodes = new ArrayList<ElectionProcessNode>();
				loadUnBalTree("wave");
				loadUnBalTree("election");
				System.out.println("Unbalanced binary tree loaded!");
				for (ElectionProcessNode enode : electionTreeNodes) {
					for (Node eneighNode : enode.getNeigh()) {
						System.out.println(enode.nodeID + "-->"
								+ eneighNode.nodeID);
					}
				}
				break;
			}

			// Load arbitrary tree
			case "3": {
				waveTreeNodes = new ArrayList<WaveProcessNode>();
				electionTreeNodes = new ArrayList<ElectionProcessNode>();
				loadArbTree("wave");
				loadArbTree("election");
				System.out.println("Arbitrary tree loaded!");
				for (ElectionProcessNode enode : electionTreeNodes) {
					for (Node eneighNode : enode.getNeigh()) {
						System.out.println(enode.nodeID + "-->"
								+ eneighNode.nodeID);
					}
				}
				break;
			}

			// Load random binary tree
			case "4": {
				waveTreeNodes = new ArrayList<WaveProcessNode>();
				electionTreeNodes = new ArrayList<ElectionProcessNode>();
				System.out.print("Enter number of nodes: ");
				int nodeCount = Integer.parseInt(user_input.next());
				genRandTree(nodeCount);
				System.out.println("Random tree loaded!");
				for (ElectionProcessNode enode : electionTreeNodes) {
					for (Node eneighNode : enode.getNeigh()) {
						System.out.println(enode.nodeID + "-->"
								+ eneighNode.nodeID);
					}
				}
				break;
			}

			// Run wave algorithm without diffusion.
			case "5": {
				if (waveTreeNodes == null || waveTreeNodes.isEmpty()) {
					System.out.println("Come on... Load a tree first!!.");
					break;
				}
				System.out
						.println("Running Wave Algorithm without diffusion..");
				runAlgorithm(false, "wave");
				break;
			}

			// Run wave algorithm with diffusion.
			case "6": {
				if (waveTreeNodes == null || waveTreeNodes.isEmpty()) {
					System.out.println("Come on... Load a tree first!!.");
					break;
				}
				System.out.println("Running Wave Algorithm with diffusion..");
				runAlgorithm(true, "wave");
				break;
			}

			// Run election algorithm without diffusion.
			case "7": {
				if (waveTreeNodes == null || waveTreeNodes.isEmpty()) {
					System.out.println("Come on... Load a tree first!!.");
					break;
				}
				System.out
						.println("Running Election Algorithm without diffusion..");
				runAlgorithm(false, "election");
				break;
			}

			// Run election algorithm with diffusion.
			case "8": {
				if (waveTreeNodes == null || waveTreeNodes.isEmpty()) {
					System.out.println("Come on... Load a tree first!!.");
					break;
				}
				System.out
						.println("Running Election Algorithm with diffusion..");
				runAlgorithm(true, "election");
				break;
			}

			// Exit the program
			case "9": {
				break;
			}
			default: {
				System.out.println("Invalid option!");
				break;
			}

			}

		}
		user_input.close();

		// switch (args[0].toLowerCase()) {
		// case "unbal": {
		// loadUnBalTree(args[1]);
		// break;
		// }
		// case "bal": {
		// loadBalTree(args[1]);
		// break;
		// }
		// case "arb": {
		// loadArbTree(args[1]);
		// break;
		// }
		// case "rand": {
		// genRandTree(args[1], Integer.parseInt(args[3]));
		// break;
		// }
		// }
		//
		// switch (args[2].toLowerCase().substring(0, 1)) {
		// case "y": {
		// runAlgorithm(true, args[1]);
		// break;
		// }
		// case "n": {
		// runAlgorithm(false, args[1]);
		// break;
		// }
		// }

	}

	// /**
	// * Generate a random tree
	// *
	// * @param processType
	// * the process type
	// * @param nodeCount
	// * the node count
	// */
	// public static void genRandTree2(String processType, int nodeCount) {
	//
	// waveTree = new Tree<WaveProcessNode>();
	// electionTree = new Tree<ElectionProcessNode>();
	// int x = nodeCount;
	// int y = x - 1;
	// int rand;
	// int[] myNumbers = new int[x];
	// Random randomGenerator = new Random();
	// int i = 0;
	// int j = 1;
	//
	// // Generating nodes
	// for (int k = 0; k < nodeCount; k++) {
	// if (processType.equalsIgnoreCase("wave")) {
	//
	// WaveProcessNode node = new WaveProcessNode(k);
	// waveTree.addNode(node);
	// } else if (processType.equalsIgnoreCase("election")) {
	// ElectionProcessNode node = new ElectionProcessNode(k);
	// electionTree.addNode(node);
	// }
	// }
	//
	//
	// if (processType.equalsIgnoreCase("wave")) {
	// waveTreeNodes = waveTree.getAllNodes();
	// } else if (processType.equalsIgnoreCase("election")) {
	// electionTreeNodes = electionTree.getAllNodes();
	// }
	// System.out.println("Generating random tree with " + nodeCount
	// + " nodes");
	// while (y > 0) {
	// if (y > 1) {
	// rand = randomGenerator.nextInt(y - 1) + 1;
	// } else {
	// rand = 1;
	// y--;
	// }
	// y = y - rand;
	// // System.out.println("Random no." + rand);
	// for (int k = rand; k > 0; k--) {
	// if (myNumbers[i] != 1) {
	// if (processType.equalsIgnoreCase("wave")) {
	// waveTreeNodes.get(i).addNeigh(waveTreeNodes.get(j));
	// } else if (processType.equalsIgnoreCase("election")) {
	// electionTreeNodes.get(i).addNeigh(
	// electionTreeNodes.get(j));
	// }
	// // System.out.println("[" + i + "]:-->" + j);
	// j++;
	// }
	// }
	// i++;
	// }
	//
	// }

	/**
	 * Generate a random tree.
	 *
	 * @param nodeCount
	 *            the node count
	 */
	public static void genRandTree(int nodeCount) {

		waveTree = new Tree<WaveProcessNode>();
		electionTree = new Tree<ElectionProcessNode>();
		int x = nodeCount;
		int y = x - 1;
		int rand;
		int[] myNumbers = new int[x];
		Random randomGenerator = new Random();
		int i = 0;
		int j = 1;

		// Generate the nodes
		for (int k = 0; k < nodeCount; k++) {
			WaveProcessNode node = new WaveProcessNode(k);
			waveTree.addNode(node);
			ElectionProcessNode node2 = new ElectionProcessNode(k);
			electionTree.addNode(node2);
		}

		waveTreeNodes = waveTree.getAllNodes();
		electionTreeNodes = electionTree.getAllNodes();

		System.out.println("Generating random tree with " + nodeCount
				+ " nodes");

		// Generating a random number of childen in each iteration and adding
		// them as neighbour
		while (y > 0) {
			if (y > 1) {
				rand = randomGenerator.nextInt(y - 1) + 1;
			} else {
				rand = 1;
				y--;
			}
			y = y - rand;
			// System.out.println("Random no." + rand);
			for (int k = rand; k > 0; k--) {
				if (myNumbers[i] != 1) {

					waveTreeNodes.get(i).addNeigh(waveTreeNodes.get(j));

					electionTreeNodes.get(i).addNeigh(electionTreeNodes.get(j));

					// System.out.println("[" + i + "]:-->" + j);
					j++;
				}
			}
			i++;
		}

	}

	/**
	 * Load balances tree.
	 *
	 * @param processType
	 *            the process type
	 */
	public static void loadBalTree(String processType) {
		if (processType.equalsIgnoreCase("wave")) {
			waveTree = new Tree<WaveProcessNode>();
			for (int i = 0; i <= 14; i++) {
				// System.out.println(i);
				WaveProcessNode node = new WaveProcessNode(i);
				waveTree.addNode(node);
			}
			waveTreeNodes = waveTree.getAllNodes();

			// Adding neighbours statically
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
			// Adding neighbours statically
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

	/**
	 * Load arb tree.
	 *
	 * @param processType
	 *            the process type
	 */
	public static void loadArbTree(String processType) {
		if (processType.equalsIgnoreCase("wave")) {
			waveTree = new Tree<WaveProcessNode>();
			for (int i = 0; i <= 7; i++) {
				// System.out.println(i);
				WaveProcessNode node = new WaveProcessNode(i);
				waveTree.addNode(node);
			}
			// Adding neighbours statically
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
			// Adding neighbours statically
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

	/**
	 * Load un bal tree.
	 *
	 * @param processType
	 *            the process type
	 */
	public static void loadUnBalTree(String processType) {
		if (processType.equalsIgnoreCase("wave")) {
			waveTree = new Tree<WaveProcessNode>();
			for (int i = 0; i <= 8; i++) {
				// System.out.println(i);
				WaveProcessNode node = new WaveProcessNode(i);
				waveTree.addNode(node);
			}
			// Adding neighbours statically
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
			// Adding neighbours statically
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

	/**
	 * Run algorithm.
	 *
	 * @param diffuse
	 *            the diffuse flag
	 * @param processType
	 *            the process type, wave or electrion
	 */
	public static void runAlgorithm(boolean diffuse, String processType) {
		Random randomGenerator = new Random();
		int totalStepstoCmplete = 0;

		if (processType.equalsIgnoreCase("election")) {

			for (ElectionProcessNode enode : electionTreeNodes) {
				for (Node eneighNode : enode.getNeigh()) {
					System.out
							.println(enode.nodeID + "-->" + eneighNode.nodeID);
				}
				enode.setCandidate(false);
				enode.setInitiator(false);
			}

			// If process is an election, generate random candidates and
			// initiators

			int initiatorCount = 0;
			int randCandCount = randomGenerator.nextInt(electionTreeNodes
					.size() - 1) + 1;
			System.out.println("Tree has " + randCandCount
					+ " candidate(s) (random)");
			int c = randCandCount;

			// If process is an election, generate random candidates
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

			// If process is an election, generate random initiators which is a
			// subset of candidates
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
			// int roundCount = 0;

			for (i = 0; i < electionTreeNodes.size() * 100; i++) {

				int j = randomGenerator.nextInt(electionTreeNodes.size() - 1) + 1;
				// System.out.println(j);
				// Generated random number to get the number of process in this
				// round
				System.out
				.println("\n\n");
				System.out
						.println("_____________________________________________________________________________");
				System.out.println("\nRound (" + (i + 1) + ") executing " + j
						+ " processes");
				System.out
						.println("_____________________________________________________________________________");
				for (int k = 1; k <= j; k++) {

					// generating random number to select to node to be executed
					int rand = randomGenerator
							.nextInt(electionTreeNodes.size());
					System.out.println("");
					System.out.println("ProcessNode[" + rand + "]");
					System.out.println("-----------------------------------");
					boolean decided = electionTreeNodes.get(rand)
							.doElectionStep();

					if (decided) {
						decideCount++;
					}
					if ((diffuse && decideCount >= electionTreeNodes.size())
							|| !diffuse && decideCount >= 2) {

						if (totalStepstoCmplete == 0) {
							totalStepstoCmplete = i + 1;
						}
						break;
					}

				}
				if (totalStepstoCmplete != 0) {
					break;
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

			Iterator<WaveProcessNode> nodeItr = waveTreeNodes.iterator();
			while (nodeItr.hasNext()) {
				nodeItr.next().init(diffuse);
			}

			int i;
			int decideCount = 0;
			// int roundCount = 0;

			for (i = 0; i < waveTreeNodes.size() * 100; i++) {
				// generating random number to select to node to be executed
				int j = randomGenerator.nextInt(waveTreeNodes.size() - 1) + 1;
				// System.out.println(j);
				// System.out.println(j);
				System.out
				.println("\n\n");
				System.out
						.println("_____________________________________________________________________________");
				System.out.println("\nRound (" + (i + 1) + ") executing " + j
						+ " processes");
				System.out
						.println("_____________________________________________________________________________");
				for (int k = 1; k <= j; k++) {
					// generating random number to select to node to be executed
					int rand = randomGenerator.nextInt(waveTreeNodes.size());
					System.out.println("");
					System.out.println("ProcessNode[" + rand + "]");
					System.out.println("-----------------------------------");
					boolean decided = waveTreeNodes.get(rand).doWaveStep();
					if (decided) {
						decideCount++;
					}
					if ((diffuse && decideCount >= waveTreeNodes.size())
							|| !diffuse && decideCount >= 2) {

						if (totalStepstoCmplete == 0) {
							totalStepstoCmplete = i + 1;
						}
						break;
					}

				}
				if (totalStepstoCmplete != 0) {
					break;
				}

			}
			System.out.println("Algorithm completed in " + totalStepstoCmplete
					+ " iterations");

		}
	}
}
