import java.util.Arrays;
import java.util.ArrayList;
public class BacktrackingSudokuSolver {
    public static void main(String[] args) {
    //setup
    	int[][] board = {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0 },    // 0 \\
                { 0, 0, 0, 0, 0, 3, 0, 8, 5 },    // 1 \\
                { 0, 0, 1, 0, 2, 0, 0, 0, 0 },    // 2 \\
                { 0, 0, 0, 5, 0, 7, 0, 0, 0 },    // 3 \\
                { 0, 0, 4, 0, 0, 0, 1, 0, 0 },    // 4 \\
                { 0, 9, 0, 0, 0, 0, 0, 0, 0 },    // 5 \\
                { 5, 0, 0, 0, 0, 0, 0, 7, 3 },    // 6 \\
                { 0, 0, 2, 0, 1, 0, 0, 0, 0 },    // 7 \\
                { 0, 0, 0, 0, 4, 0, 0, 0, 9 }};   // 8 \\
               // 0  1  2  3  4  5  6  7  8 \\

        int tries=0;
        int btCount=0;
        int x=0;
        int y=0;
        int btBox=0;
        int maxAVcount=0;
        ArrayList<Integer> AV = new ArrayList<Integer>();
        ArrayList<Integer> AVcount = new ArrayList<Integer>(); 
        ArrayList<Integer> openRC = new ArrayList<Integer>();
        ArrayList<Integer> openCC = new ArrayList<Integer>();
        for(int i=0; i<9; i++) System.out.println(Arrays.toString(board[i]));
	//fill boxes with only one available number
        int k=0;
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                if(board[i][j]==0) {
                	AV=AVfinder(board, i, j); 
                	if(AV.size()==1) {
                		board[i][j]=AV.get(0);
                		k=1;
                		tries++;
                	}
                	if(k==1) {
                		i=0;
                		j=0;
                		k=0;
                	}
                }
            }
    	}
    //record open spaces 
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                if(board[i][j]==0) {
                    openRC.add(i);
                    openCC.add(j);
                    AVcount.add(0);
                }
            }
        }
        if(AVcount.size()==0) {
        	System.out.println("Solved in 1 try!");
        	System.out.println(tries+" boxes filled");
        	System.exit(1);
        }else {
        	System.out.println("\nSolving . . .\n");
        }
	//find the first box with the least amount of available numbers
        int l=10;
        for(int i=0; i<AVcount.size(); i++) {
        	AV=AVfinder(board, openRC.get(i), openCC.get(i));
        	if(AV.size()<l) {
        		l=AV.size();
        		k=i;
        	}	
        }
	//make that box the starting box 
        ArrayList<Integer> temp = new ArrayList<Integer>();
        temp.addAll(openRC.subList(k,openRC.size()));
        temp.addAll(openRC.subList(0,k));
        openRC.clear();
        openRC.addAll(temp);
        temp.clear();
        temp.addAll(openCC.subList(k,openCC.size()));
        temp.addAll(openCC.subList(0,k));
        openCC.clear();
        openCC.addAll(temp);
    //iteration
        for(btBox=0; btBox<AVcount.size(); btBox++) {
            if(y==1) {
                btBox=0;
                y=0;
            }          
        //iPad small brained   
            if(tries>=15000){
                //System.exit(1);
            }
        //determine available numbers    
            AV=AVfinder(board, openRC.get(btBox), openCC.get(btBox));
        //select number from the available numbers and then increase AVcount
            if(!AV.isEmpty()) {     
                 board[openRC.get(btBox)][openCC.get(btBox)]=AV.get(AVcount.get(btBox));
                 AVcount.set(btBox,AVcount.get(btBox)+1);
                 tries++;
            }else {
            //no available numbers
            	btCount++;
            //backtracking algorithm    
                while(x==0) {  
                //reset box    
                    board[openRC.get(btBox)][openCC.get(btBox)]=0;   
                //redetermine data      
                    AV=AVfinder(board,openRC.get(btBox),openCC.get(btBox));
                    maxAVcount=AV.size();
                //determine if current box's count is greater than its max count
                    if(AVcount.get(btBox)<maxAVcount) {
						break; 
                    }else {
                //otherwise keep backtracking
                        AVcount.set(btBox,0);
                        btBox--;
                    }
                }
            //reset coordinates
                btBox--; 
                if(btBox>=0) {
                }else {
                    y=1;
                }                     
            }               
        }
    //printing
        for(int i=0; i<9; i++) System.out.println(Arrays.toString(board[i]));
        System.out.println("\n"+btCount+" iterations tried and\n"+tries+" boxes filled.");
}
//available number finder
    public static ArrayList<Integer> AVfinder(int[][] x, int row, int column) {
    	ArrayList<Integer> AV = new ArrayList<Integer>();                   //    All Available numbers
        ArrayList<Integer> UAV = new ArrayList<Integer>();                  //    All Unavailable numbers
        for(int i=1; i<=9; i++) AV.add(i);
        int[] rowScan = x[row];
        int[] columnScan = {x[0][column], x[1][column], x[2][column], x[3][column], x[4][column], x[5][column], x[6][column], x[7][column], x[8][column]};            
        for(int i=0; i<9; i++) {        
            if(rowScan[i]!=0) {
                UAV.add(rowScan[i]);
            }
            if(columnScan[i]!=0) {
                UAV.add(columnScan[i]);
            }
        }
        ArrayList<Integer> boxScanRows = new ArrayList<Integer>();
        ArrayList<Integer> boxScanColumns = new ArrayList<Integer>();
        int box=0;
        if(row<=2) {
            boxScanRows.addAll(Arrays.asList(1,2,3));
        }else if(row<=5) {
            boxScanRows.addAll(Arrays.asList(4,5,6));
        }else {
            boxScanRows.addAll(Arrays.asList(7,8,9));
        }
        if(column<=2) {
            boxScanColumns.addAll(Arrays.asList(1,4,7));
        }else if(column<=5) {
            boxScanColumns.addAll(Arrays.asList(2,5,8));
        }else {
            boxScanColumns.addAll(Arrays.asList(3,6,9));
        }
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(boxScanRows.get(i)==boxScanColumns.get(j)){
                	box=boxScanRows.get(i);
                }
            }
        }
        if(box==1){
            UAV.addAll(Arrays.asList(x[0][0], x[0][1], x[0][2], x[1][0], x[1][1], x[1][2], x[2][0], x[2][1], x[2][2]));
        }
        if(box==2){
            UAV.addAll(Arrays.asList(x[0][3], x[0][4], x[0][5], x[1][3], x[1][4], x[1][5], x[2][3], x[2][4], x[2][5]));
        }
        if(box==3){
            UAV.addAll(Arrays.asList(x[0][6], x[0][7], x[0][8], x[1][6], x[1][7], x[1][8], x[2][6], x[2][7], x[2][8]));
        }
        if(box==4){
            UAV.addAll(Arrays.asList(x[3][0], x[3][1], x[3][2], x[4][0], x[4][1], x[4][2], x[5][0], x[5][1], x[5][2]));
        }
        if(box==5){
            UAV.addAll(Arrays.asList(x[3][3], x[3][4], x[3][5], x[4][3], x[4][4], x[4][5], x[5][3], x[5][4], x[5][5]));
        }
        if(box==6){
            UAV.addAll(Arrays.asList(x[3][6], x[3][7], x[3][8], x[4][6], x[4][7], x[4][8], x[5][6], x[5][7], x[5][8]));
        }
        if(box==7){
            UAV.addAll(Arrays.asList(x[6][0], x[6][1], x[6][2], x[7][0], x[7][1], x[7][2], x[8][0], x[8][1], x[8][2]));
        }
        if(box==8){
            UAV.addAll(Arrays.asList(x[6][3], x[6][4], x[6][5], x[7][3], x[7][4], x[7][5], x[8][3], x[8][4], x[8][5]));
        }
        if(box==9){
            UAV.addAll(Arrays.asList(x[6][6], x[6][7], x[6][8], x[7][6], x[7][7], x[7][8], x[8][6], x[8][7], x[8][8]));
        }
        AV.removeAll(UAV);
        return AV;
	}
}
