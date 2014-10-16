import java.util.ArrayList;

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
		
		n0.addNeigh(n1);
		n1.addNeigh(n2);
		n1.addNeigh(n3);
		n2.addNeigh(n4);
		n2.addNeigh(n5);
		n2.addNeigh(n6);
		n3.addNeigh(n7);
		
		
		
		n0.init();
		n1.init();
		n2.init();
		n3.init();
		n4.init();
		n5.init();
		n6.init();
		n7.init();
		
		n0.executeStep();
		n1.executeStep();
		n2.executeStep();
		n4.executeStep();
		n5.executeStep();
		n6.executeStep();
		n2.executeStep();
		n1.executeStep();
		n3.executeStep();
		n7.executeStep();
		n6.executeStep();
		n6.executeStep();
		n3.executeStep();
		n1.executeStep();
		n0.executeStep();
		n1.executeStep();
		n7.executeStep();
		n2.executeStep();
		n4.executeStep();
		n5.executeStep();
		n6.executeStep();
		
	}

}
