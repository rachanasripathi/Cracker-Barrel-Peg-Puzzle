import java.util.ArrayList;
import java.util.Date;
import javafx.util.Pair;
import java.util.Arrays;
 
public class cracker_puzzle
{ 
 private int size_pattern=0;
 private Integer printed;
 private Integer type_printed = 0;
 private Integer[] cells;
 private Integer[][] patterns = new Integer[20000][15];
 
 // Moves in both direction.
 private final static int[][] MOVES = new int[][]
 { 
 new int[] { 0, 1, 3 },
 new int[] { 0, 2, 5 },
 new int[] { 1, 3, 6 },
 new int[] { 1, 4, 8 },
 new int[] { 2, 4, 7 },
 new int[] { 2, 5, 9 },
 new int[] { 3, 4, 5 },
 new int[] { 3, 6, 10 },
 new int[] { 3, 7, 12 },
 new int[] { 4, 7, 11 },
 new int[] { 4, 8, 13 },
 new int[] { 5, 8, 12 },
 new int[] { 5, 9, 14 },
 new int[] { 6, 7, 8 },
 new int[] { 7, 8, 9 },
 new int[] { 10, 11, 12 },
 new int[] { 11, 12, 13 },
 new int[] { 12, 13, 14 },

// Reverse operations.
 new int[] { 3, 1, 0 },
 new int[] { 5, 2, 0 },
 new int[] { 6, 3, 1 },
 new int[] { 8, 4, 1 },
 new int[] { 7, 4, 2 },
 new int[] { 9, 5, 2 },
 new int[] { 5, 4, 3 },
 new int[] { 10, 6, 3 },
 new int[] { 12, 7, 3 },
 new int[] { 11, 7, 4 },
 new int[] { 13, 8, 4 },
 new int[] { 12, 8, 5 },
 new int[] { 14, 9, 5 },

 new int[] { 8, 7, 6 },
 new int[] { 9, 8, 7 },
 new int[] { 12, 11, 10 },
 new int[] { 13, 12, 11 },
 new int[] { 14, 13, 12 }
 };

// Check id the pattern is already visited like in yield method in python.
public int check(Integer[] cell) {
    for(int r=0;r < this.size_pattern; r++) {
        int result = 1;
        for(int s=0;s<15;s++) {
            if(cell[s] != this.patterns[r][s])
                result = 0;
        }
        if (result == 1)
            return 1;
    }
    return 0;
}

// Add pattern to list.
public void add(Integer[] cell) {
    this.patterns[this.size_pattern] = Arrays.copyOf(cell,15);
    this.size_pattern++;
}

// define move.
 public void move(int k, Integer[] cells, Integer[][] steps, int size, Integer[] moves, int move_size) {
    Integer modify = k;
    Integer[] cells_copy = new Integer[cells.length];
    for (int r = 0; r < MOVES.length; r++) {
            if(cells[MOVES[r][0]]==0 &&
                cells[MOVES[r][1]]==1 &&
                cells[MOVES[r][2]]==1) {
                cells_copy = Arrays.copyOf(cells,15);
                cells_copy[MOVES[r][0]] = 1;
                cells_copy[MOVES[r][1]] = 0;
                cells_copy[MOVES[r][2]] = 0;
                modify = k - 1;
            }
            if (modify == k - 1 && check(cells_copy) != 1){
                moves[move_size] = r;
                move_size++;
                steps[size] = Arrays.copyOf(cells_copy,15);
                this.solve(k-1, cells_copy,steps,size+1, moves, move_size);
                add(cells_copy);
            }
            modify = k;
    }
 }

// Define method to solve puzzle.
public void solve(int k, Integer[] cells, Integer[][] steps, int size,
    Integer[] moves, int move_size) {
    Pair<Integer, Integer[]> p;
    if (k < 2 && this.printed != 1) {
        if (print_type == 1) {
            for(int r=0;r<size;r++)
                this.print(steps[r]);
        } else {
            for(int r=0;r<move_size;r++)
                System.out.println("( "+this.MOVES[moves[r]][0]+", "+this.MOVES[moves[r]][1]+", "+this.MOVES[moves[r]][2]+" )");
                this.print_cell(cells);
        }
        this.printed =1;
    } else {
        move(k, cells, steps, size, moves, move_size);
    }
}

// initialize method.
 public void init(int index) {
    this.cells =  new Integer[15];
    this.printed = 0;
    for(int r=0;r<15;r++){  
        if(index == r){
            cells[r] = 0;
        } else {
            cells[r] = 1;
        }
    }
 }

// Print in formate.
 public void print(Integer[] cells) {
     int count = 0;
     int check = 1;
     System.out.println("");
     for(int r=0;r<15;r++) {
        System.out.print(cells[r]);
        count++;
        if(count == check) {
            check++;
            count=0;
            System.out.println("");
        }
     }
 }

  public void print_cell(Integer[] cells) {
     System.out.println("");
     System.out.print("{14 (");
     for(int r=0;r<15;r++) {
        System.out.print(cells[r]+ " ");
     }
     System.out.print(")");
     System.out.println();
 }

 public static void main(String[] args) {
    // equivalent to go().
    for(int r=0;r<15;r++) {
        cracker cra = new cracker();
        cra.type_printed = 1; // print go() output
        System.out.println("======="+r+"=======");
        cra.init(r);
        cra.print(cra.cells);
        Integer[][] steps = new Integer[50][15];
        Integer[] moves = new Integer[50];
        cra.solve(14, cra.cells, steps, 0, moves, 0);
     }

    // equivalent to terse().
    for(int r=0;r<15;r++) {
        cracker cra = new cracker();
        cra.type_printed = 0; // print terse() output
        System.out.println("======="+r+"=======");
        cra.init(r);
        cra.print_cell(cra.cells);
        Integer[][] steps = new Integer[50][15];
        Integer[] moves = new Integer[50];
        cra.solve(14, cra.cells, steps, 0, moves, 0);
     }
 }
}