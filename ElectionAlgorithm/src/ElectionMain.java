import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.technipun.dc.algorithm.ProcessNode;

public class ElectionMain {

	private static ArrayList<ProcessNode> treenodes;

	public static void main(String args[]) {
		treenodes = new ArrayList<ProcessNode>();
		boolean diffuse = false;
		ProcessNode n0 = new ProcessNode(0);
		ProcessNode n1 = new ProcessNode(1);
		ProcessNode n2 = new ProcessNode(2);
		ProcessNode n3 = new ProcessNode(3);
		ProcessNode n4 = new ProcessNode(4);
		ProcessNode n5 = new ProcessNode(5);
		ProcessNode n6 = new ProcessNode(6);
		ProcessNode n7 = new ProcessNode(7);

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
		n2.setInitiator(true);
		// n0.setCandidate(true);
		// n3.setCandidate(true);
		n6.setCandidate(true);

		Iterator<ProcessNode> nodeItr = treenodes.iterator();
		while (nodeItr.hasNext()) {
			nodeItr.next().init(diffuse);
		}

		Random randomGenerator = new Random();
		int i;
		int decideCount = 0;
		for (i = 0; i < treenodes.size() * 100; i++) {
			int j = randomGenerator.nextInt(treenodes.size());
			System.out.println(j);
			boolean decided = treenodes.get(j).doElectionStep();
			if (decided) {
				decideCount++;
			}
			if (diffuse && decideCount == treenodes.size() || !diffuse
					&& decideCount == 2) {
				break;
			}
		}
	}

}
