import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

/*
 * 
 */
public class Solution {
	
	/*
	 * Generate initial grid as char[][]
	 */
	static char[][] initialGrid(String[] grid) {
		
		// **** ****
    	int rows = grid.length;

    	// **** ****
		char[][] g = new char[rows][];

		// **** ****
		for (int r = 0; r < rows; r++) {
			
			// **** replace 'O' with '2' ****
			grid[r] = grid[r].replace('O', '2');
			
			// **** convert string to char array ****
			g[r] = grid[r].toCharArray();
		}
		
		// **** return char array grid ****
		return g;
	}
	
	/*
	 * 
	 */
	static char[][] cloneArray(char[][] g) {
		
		// **** ****
		int rows = g.length;
		int cols = g[0].length;
		
		// **** ****
		char[][] gc = new char[rows][cols];
		
		// **** ****
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				gc[r][c] = g[r][c];
		
		// **** ****
		return gc;
	}
		
	/*
	 * Decrement fuse count
	 */
	static char[][] decrementFuses(char[][] g) {
		
		// **** ****
		int rows = g.length;
		int cols = g[0].length;
		
	   	// **** traverse the grid decrementing fuse counts ****
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				
				// **** ****
				if (g[r][c] == '3')
					g[r][c] = '2';
				else if (g[r][c] == '2')
					g[r][c] = '1';
				else if (g[r][c] == '1')
					g[r][c] = '0';
			}
		}

		// **** ****
		return g;
	}
	
	/*
	 * Detonate bombs
	 */
	static char[][] detonateBombs(char[][] g) {
		
		// **** ****
		int rows = g.length;
		int cols = g[0].length;
		
		// **** copy the grid ****
		char[][] cg = cloneArray(g);
		
		// **** traverse the grid detonating bombs ****
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				
				// **** detonate this bomb ****
				if (cg[r][c] == '0') {
										
					// **** up ****
					if (r > 0)
						g[r - 1][c] = '.';
					
					// **** down ****
					if (r < rows - 1)
						g[r + 1][c] = '.';
					
					// **** right ****
					if (c < cols - 1)
						g[r][c + 1] = '.';
					
					// **** left ****
					if (c > 0)
						g[r][c - 1] = '.';
					
					// **** center ***
					g[r][c] = '.';
				}
			}
		}
		
		// **** returned the cloned grid ****
		return g;
	}
	
	/*
	 * plant bombs with 3-second fuse
	 */
	static char[][] plantBombs(char[][] g) {
		
		// **** ****
		int rows = g.length;
		int cols = g[0].length;
	
		// **** traverse the grid ****
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				
				// **** plant bomb (if needed) ****
				if (g[r][c] == '.')
					g[r][c] = '3';
			}
		}

		// **** return updated grid ****
		return g;
	}

	/*
	 * Generate string array from char[][] grid
	 */
	static String[] charToStringArray(char[][] g) {
		
		// **** number of rows in the grid ****
		int rows = g.length;

		// **** allocate the string grid ****
		String[] grid = new String[rows];
		
		// **** make a copy of the input grid ****
		char[][] copy = cloneArray(g);

		// **** generate strings for the grid ****
		for (int r = 0; r < rows; r++) {
			
			// **** ****
			for (int c = 0; c < copy[r].length; c++) {
				if (copy[r][c] >= '1' && copy[r][c] <= '3')
					copy[r][c] = 'O';
				else
					copy[r][c] = '.';
			}
			
			// **** generate this string ****
			grid[r] = new String(copy[r]);
		}

		// **** return the string grid ****
		return grid;
	}	
	
	/*
	 * Return the number for the final grid
	 */
	static int finalGrid(int n) {
		
		// ???? ????
		System.out.println("finalGrid <<< n: " + n);
		
		// **** ****
		if (n <= 5)
			return n;
		else if (n % 2 == 0)
			return 2;
		else if ((n - 3) % 4 == 0)
			return 3;
		else if ((n - 5) % 4 == 0)
			return 5;
		
		// **** ****
		return -1;
	}

	/*
	 * Complete the bomberMan function below.
	 */
    static String[] bomberMan(	int n, 				// the number of seconds to simulate
    							String[] grid) {	// array of strings that represents the grid
    	    	
		// **** build initial grid of characters ****
    	char[][] g = initialGrid(grid);

    	// **** compute the number for the final grid ****
    	int fg = finalGrid(n);
    	
    	// **** to save intermediate grids (as needed) ****
    	char[][] g2 = null;
    	char[][] g3 = null;
    	char[][] g5 = null;

    	// **** set the number of cycles to run ****
    	int maxCycle = n;
    	if (n >= 5)
    		maxCycle = 5;
    	    	
    	// ***** run first few cycles in the simulation ****
    	for (int cycle = 2 ; cycle <= maxCycle; cycle++) {
 
    		// **** decrement fuses [1] ****
	    	g = decrementFuses(g);

	    	// **** plant bombs (3 second fuse) [2] ****
	    	if ((cycle % 2) == 0) {
		    	g = plantBombs(g);
	    	} 	    	

	    	// **** detonate bombs [3] ****
	    	else {
			    g = detonateBombs(g);
	    	}

	    	// **** save the grid (as needed) ****
	    	if (cycle == 2) {
	    		g2 = cloneArray(g);
	    	} else if (cycle == 3) {
	    		g3 = cloneArray(g);
	    	} else if (cycle == 5) {
	    		g5 = cloneArray(g);
	    	}
    	}

    	// **** replace the grid to return ****
    	if (fg == 2)
    		g = g2;
    	else if (fg == 3)
    		g = g3;
    	else if (fg == 5)
    		g = g5;

    	// **** generate strings for this grid ****
    	grid = charToStringArray(g);

    	// **** return grid of strings ****
    	return grid;
    }
    
    
    private static final Scanner scanner = new Scanner(System.in);

    
    /*
     * Edited test scaffolding
     */
    public static void main(String[] args) throws IOException {
    	
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] rcn = scanner.nextLine().split(" ");

        int r = Integer.parseInt(rcn[0]);

        int c = Integer.parseInt(rcn[1]);

        int n = Integer.parseInt(rcn[2]);

        String[] grid = new String[r];

        for (int i = 0; i < r; i++) {
            String gridItem = scanner.nextLine();
            grid[i] = gridItem;
        }

//        // **** loop a few times to display loops ****
//        for (int cycle = 0; cycle <= n; cycle++) {
//        
//        	// ???? ????
//        	System.out.println("main <<< cycle: " + cycle);
//        	
//	        // **** ****
//        	String[] result = bomberMan(cycle, grid);
        	String[] result = bomberMan(n, grid);
	
	        // **** display string array ****
	        for (int i = 0; i < result.length; i++) {
	            bufferedWriter.write(result[i]);
	
	            if (i != result.length - 1) {
	                bufferedWriter.write("\n");
	            }
	        }
	        bufferedWriter.newLine();
	        
//	        // ???? ????
//	        bufferedWriter.newLine();
//	        bufferedWriter.flush();
//        }
        
        // **** ****
        bufferedWriter.close();

        scanner.close();
    }
}
