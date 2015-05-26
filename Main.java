import java.util.*;

class Main{
	
	public static final int QTY = 50; //200000
	public static final int  SEARCH = 10; //10000
	public static int d; // used in a forced remotion;

    public static void main(String args[]){
		Random generator = new Random();
        long start, aux, uma = 0, media = 0, fim =0, total;
        
       RBTree t = new RBTree(0);
        
        start = System.nanoTime();
        
        for(int i = 0; i < QTY; i++){
			aux = System.nanoTime();
			//t.insert(generator.nextInt(2147483647));
			int j = generator.nextInt(1000);
			t.insert(j);
			System.out.println(j);
			uma = System.nanoTime() - aux;
			media += System.nanoTime() - aux;
			
			if (i==0) { d = j;}; // used in a forced remotion;
		}
        //fim = System.nanoTime()-start;
        media /= QTY;
        //System.out.printf("Time of a single insertion: %.10f\n", uma/ 10e9);
        //System.out.printf("Time of all insertions: %.10f\n", fim/ 10e9);
		System.out.printf("Average insertion time: %.10f\n", media/ 10e9);
		
		media = 0;
		
		for(int i = 0; i < SEARCH; i++){
			aux = System.nanoTime();
			t.search(generator.nextInt(2147483647));
			media += System.nanoTime() - aux;
		}

		//---- 	REMOTION ------
		//t.remove(d); // removes a forced element; 'd' can be replaced for a number
		
		
		//---- 50 NODES---
		//System.out.println("------ I am using:"+ d);
		//t.find50(d).graph(); // creates a new RBTree with the 50 elemens greater than a node;
		
		t.graph();
        
        total = System.nanoTime() - start;
        media /= SEARCH;
		System.out.printf("Average seek time: %.10f\n", media/ 10e9);
		System.out.printf("Total time: %.10f\n", total / 10e9);
		
    }

}
