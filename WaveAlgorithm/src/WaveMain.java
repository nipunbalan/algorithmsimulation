import com.technipun.dc.algorithm.ProcessNode;


public class WaveMain {
	
	public static void main(String args[])
	{
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
		
		
		n0.runWave();
		n1.runWave();
		n2.runWave();
		n3.runWave();
		n4.runWave();
		n5.runWave();
		n6.runWave();
		n7.runWave();
		
		
		
		
	}

}
