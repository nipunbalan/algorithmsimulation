import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.technipun.dc.algorithm.ElectionProcessNode;
import com.technipun.dc.algorithm.Tree;

public class ElectionMain {

	private static ArrayList<ElectionProcessNode> treenodes;

	private static Tree tree;

	public static void main(String args[]) {
		// treenodes = new ArrayList<ElectionProcessNode>();
		// boolean diffuse = false;
		// ElectionProcessNode n0 = new ElectionProcessNode(0);
		// ElectionProcessNode n1 = new ElectionProcessNode(1);
		// ElectionProcessNode n2 = new ElectionProcessNode(2);
		// ElectionProcessNode n3 = new ElectionProcessNode(3);
		// ElectionProcessNode n4 = new ElectionProcessNode(4);
		// ElectionProcessNode n5 = new ElectionProcessNode(5);
		// ElectionProcessNode n6 = new ElectionProcessNode(6);
		// ElectionProcessNode n7 = new ElectionProcessNode(7);

		treenodes = new ArrayList<ElectionProcessNode>();
		boolean diffuse = true;
		ElectionProcessNode n0 = new ElectionProcessNode(0);
		ElectionProcessNode n1 = new ElectionProcessNode(1);
		ElectionProcessNode n2 = new ElectionProcessNode(2);
		ElectionProcessNode n3 = new ElectionProcessNode(3);
		ElectionProcessNode n4 = new ElectionProcessNode(4);
		ElectionProcessNode n5 = new ElectionProcessNode(5);
		ElectionProcessNode n6 = new ElectionProcessNode(6);
		ElectionProcessNode n7 = new ElectionProcessNode(7);

		treenodes.add(n0);
		treenodes.add(n1);
		treenodes.add(n2);
		treenodes.add(n3);
		treenodes.add(n4);
		treenodes.add(n5);
		treenodes.add(n6);
		treenodes.add(n7);

		n0.addNeigh(n1);
		n1.addNeigh(n2);
		n1.addNeigh(n3);
		n2.addNeigh(n4);
		n2.addNeigh(n5);
		n2.addNeigh(n6);
		n3.addNeigh(n7);
		n0.setInitiator(true);
		// n2.setInitiator(true);
		// // n0.setCandidate(true);
		// n0.setCandidate(true);
		n1.setCandidate(true);
		n2.setCandidate(true);
		n3.setCandidate(true);
		n4.setCandidate(true);
		n5.setCandidate(true);
		n6.setCandidate(true);
		n7.setCandidate(true);
		// n3.setCandidate(true);
		// n6.setCandidate(true);

		Iterator<ElectionProcessNode> nodeItr = treenodes.iterator();
		while (nodeItr.hasNext()) {
			nodeItr.next().init(diffuse);
		}

		Random randomGenerator = new Random();
		int i;
		@SuppressWarnings("unused")
		int decideCount = 0;
		for (i = 0; i < treenodes.size() * 100; i++) {
			int j = randomGenerator.nextInt(treenodes.size());
			// System.out.println(j);
			// boolean decided = treenodes.get(j).doStep();
			boolean decided = treenodes.get(j).doElectionStep();
			if (decided) {
				decideCount++;
			}
			// if (diffuse && decideCount == treenodes.size() || !diffuse
			// && decideCount == 2) {
			// break;
			// }
		}
	}

	public void genRandTree(int nodeCount) {
		
		tree = new Tree();
		int x = nodeCount;
		int y = x - 1;
		int rand;
		int[] myNumbers = new int[x];
		Random randomGenerator = new Random();
		// int i = 0;
		int i = 0;
		int j = 1;

		while (y > 0) {
			if (y > 1) {
				rand = randomGenerator.nextInt(y - 1) + 1;
			} else {
				rand = 1;
				y--;
			}
			y = y - rand;
			System.out.println("Random no." + rand);
			for (int k = rand; k > 0; k--) {
				if (myNumbers[i] != 1) {
					System.out.println("[" + i + "]:-->" + j);
					j++;
				}
			}
			i++;
		}

	}
}
