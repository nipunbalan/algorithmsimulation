import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.technipun.dc.algorithm.ProcessNode;


public class WaveMain {
	
	private static ArrayList<ProcessNode> treenodes;
	
	public static void main(String args[])
	{
		treenodes = new ArrayList<ProcessNode>();
		ProcessNode n0=new ProcessNode(0);
		ProcessNode n1=new ProcessNode(1);
		ProcessNode n2=new ProcessNode(2);
		ProcessNode n3=new ProcessNode(3);
		ProcessNode n4=new ProcessNode(4);
		ProcessNode n5=new ProcessNode(5);
		ProcessNode n6=new ProcessNode(6);
		ProcessNode n7=new ProcessNode(7);
		
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
		
		
		
		
		Iterator<ProcessNode> nodeItr = treenodes.iterator();
		while(nodeItr.hasNext())
		{
			nodeItr.next().init(true);
		}
		
		Random randomGenerator = new Random();
		
		for(int i=0;i<treenodes.size()*100;i++ )
		{
			int j = randomGenerator.nextInt(treenodes.size());
			treenodes.get(j).doStep();
		}
		
		

//		n0.doStep();
//		n1.doStep();
//		n2.doStep();
//		n4.doStep();
//		n5.doStep();
//		n6.doStep();
//		n2.doStep();
//		n1.doStep();
//		n3.doStep();
//		n7.doStep();
//		n6.doStep();
//		n6.doStep();
//		n3.doStep();
//		n1.doStep();
//		n0.doStep();
//		n1.doStep();
//		n7.doStep();
//		n2.doStep();
//		n4.doStep();
//		n5.doStep();
//		n6.doStep();
		
		
		
		
		
	}

}
