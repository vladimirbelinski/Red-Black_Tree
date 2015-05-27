import java.util.*;

class Main{

	public static final int QTY = 50; //200000
	public static final int  SEARCH = 10; //10000
	public static int d; // used in a forced remotion;

    public static void main(String args[]){
		Random generator = new Random();
        long start, aux, uma = 0, media_i = 0, media_s= 0, end =0, total;

		RBTree t = new RBTree();

		start = System.nanoTime();

//-------------------------------- INSERTION -----------------------------------------------------------------
        for(int i = 0; i < QTY; i++){
			aux = System.nanoTime();
			int j = generator.nextInt(1000);
			t.insert(j);
			//System.out.println(j); // used to print the value keys in the order they are inserted
			uma = System.nanoTime() - aux;
			media_i += System.nanoTime() - aux;

			if (i==0) d = j; // used in a forced remotion;
		}

        //end = System.nanoTime()-start; // I find the end before because System.out.printf also has a time that influenciates...
		media_i /= QTY;

//------------------------------- SEARCH ---------------------------------------------------------------------
		for(int i = 0; i < SEARCH; i++){
			aux = System.nanoTime();
			t.search(generator.nextInt(2147483647));
			media_s += System.nanoTime() - aux;
		}

		media_s /= SEARCH;
//------------------------------- SEARCH OF 50 ELEMENTS ------------------------------------------------------
		//System.out.println("------ I am using:"+ d);
		//t.find50(d).graph(); // creates a new RBTree with the 50 elemens greater than a node;

//-------------------------------- REMOTION ------------------------------------------------------------------
		//t.remove(d); // removes a forced element; 'd' can be replaced for a number
		//t = t.delete(); // Full deletion of the Red-Black Tree

		t.graph();

        total = System.nanoTime() - start;

		//System.out.printf("Time of the first insertion: %.10f\n", uma/ 10e9);
        //System.out.printf("Time of all insertions: %.10f\n", end/ 10e9);
		System.out.printf("\nAverage insertion time: %.10f\n", media_i/ 10e9);
		System.out.printf("Average seek time: %.10f\n", media_s/ 10e9);
		System.out.printf("Total time: %.10f\n", total / 10e9);
    }
}
