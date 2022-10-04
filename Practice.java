import java.util.Arrays;
import java.util.ArrayList;
public class Practice {
    public static void main(String[] args) {
              //Setup
                  int[][] board = {
                          { 8, 0, 0, 0, 0, 0, 0, 0, 0 },    // 0 \\
                          { 0, 0, 3, 6, 0, 0, 0, 0, 0 },    // 1 \\
                          { 0, 7, 0, 0, 9, 0, 2, 0, 0 },    // 2 \\
                          { 0, 5, 0, 0, 0, 7, 0, 0, 0 },    // 3 \\
                          { 0, 0, 0, 0, 4, 5, 7, 0, 0 },    // 4 \\
                          { 0, 0, 0, 1, 0, 0, 0, 3, 0 },    // 5 \\
                          { 0, 0, 1, 0, 0, 0, 0, 6, 8 },    // 6 \\
                          { 0, 0, 8, 5, 0, 0, 0, 1, 0 },    // 7 \\
                          { 0, 9, 0, 0, 0, 0, 4, 0, 0 }};   // 8 \\
                         // 0  1  2  3  4  5  6  7  8 \\

                  int x=0;
                  int y=0;
                  int count=-1;
                  int btBox=count;
                  int maxAVcount=0;
                  ArrayList<Integer> AV = new ArrayList<Integer>();
                  ArrayList<Integer> AVcount = new ArrayList<Integer>(); 
//                ArrayList<Integer> maxAVcount = new ArrayList<Integer>(); 
                  ArrayList<Integer> unsCoords = new ArrayList<Integer>();  
        
        //record open spaces
              ArrayList<Integer> openRC = new ArrayList<Integer>();
              ArrayList<Integer> openCC = new ArrayList<Integer>();
              ArrayList<String> spacer = new ArrayList<String>();
                  for (int i=0; i<9; i++) {
                      for (int j=0; j<9; j++) {
                          if(board[i][j]==0) {
                              openRC.add(i);
                              openCC.add(j);
                  }}}
                  for (int i=0; i<openRC.size(); i++) {
                      if(i%5==0) {
                          spacer.add("/");
                      }else {
                          spacer.add(" ");
                      }
                  }
                  for (int i=0; i<9; i++) {
                    System.out.println(Arrays.toString(board[i]));
                    for (int j=0; j<9; j++) {
                    }
                }
                System.out.println();
                System.out.println("                         "+spacer);
                System.out.println("Open Row Coordinates:    "+openRC);
                System.out.println("Open Column Coordinates: "+openCC+"\n");
        //run until solved
            while(x==0) {    
            //iteration
                for(int row=0; row<9; row++) {
                for(int column=0; column<9; column++) {
                //determine available numbers
                    if(board[row][column]==0) {
                        if(y==0) {
                            count++;
                            btBox=count;
                            if(!unsCoords.contains(count)) {
                                  AVcount.add(0);
                            } 
                        }
                        
                        AV=AVfinder(board, row, column);
                        System.out.println("("+(row+1)+", "+(column+1)+")");
                        System.out.println("Count: "+count);
                        System.out.println("btBox: "+btBox);
                        System.out.println("AV: "+AV);
                        System.out.println("AVcount: "+AVcount);
                    //select number from the available numbers
                        if(!AV.isEmpty()) {
                            
                                     board[row][column]=AV.get(AVcount.get(btBox));
                                    AVcount.set(btBox,AVcount.get(btBox)+1);
                                    btBox++;
                                    
                        //unlock count
                             if((y==1)&&(board[openRC.get(count)][openCC.get(count)]!=0)) {
                                 unsCoords.clear();
                                 y=0;
                                 System.out.println("Count unlocked");
                             }
                        }else {
                        //No available numbers
                            System.out.println("No available numbers\n");                                                        
                        //lock current count
                            y=1;
                        //backtrack
                            btBox=btBox-1;
                            System.out.println("Backtracked to "+btBox);
                        //record current unsolved boxes
                            if(!unsCoords.contains(btBox)) {
                                unsCoords.add(btBox);
                            }
                        //reset boxes
                            board[openRC.get(btBox)][openCC.get(btBox)]=0;
                            
                        //redetermine data    
                            AV=AVfinder(board,openRC.get(btBox),openCC.get(btBox));
                            maxAVcount=AV.size();
                            
                            while(AVcount.get(btBox)>=maxAVcount) {
                                System.out.println("Hit Max AVcount");
                                  
                                for(int reset=btBox; reset<count; reset++) {
                                    board[openRC.get(reset)][openCC.get(reset)]=0;
                                }
                                    
                                    AV=AVfinder(board,openRC.get(btBox),openCC.get(btBox));
                                    maxAVcount=AV.size();
                                    
                                    if(AVcount.get(btBox)<maxAVcount) {
                                        break;
                                    }else {
                                        AVcount.set(btBox,0);
                                        btBox=btBox-1;
                                        System.out.println("Backtracked to "+btBox);
                                    }
                            }
                            System.out.println("AVcount: "+AVcount.get(btBox));
                            System.out.println("maxAVcount: "+maxAVcount);
                            
                        //reset coordinates
                            row=openRC.get(btBox-1);
                            column=openCC.get(btBox-1);
                        }
                        
                        
                        
                        
                        
                        
                    //printer
                        x=1;
                        for (int i=0; i<9; i++) {
                            System.out.print(Arrays.toString(board[i])+"\n");
                            for (int j=0; j<9; j++) {
                                if(board[i][j]==0) {
                                    x=0;
                                }else {
                                    //System.out.print("SOLVED");
                                }
                            }
                        }
                    }          
                }                    
            }
        }    
    }
    public static ArrayList<Integer> AVfinder(int[][] x, int row, int column) {
            ArrayList<Integer> nums = new ArrayList<Integer>();
            for(int i=1; i<=9; i++) nums.add(i);
            ArrayList<Integer> AV = new ArrayList<Integer>();                   //    All Available numbers
            ArrayList<Integer> UAV = new ArrayList<Integer>();                  //    All Unavailable numbers
            int[] rowScan = x[row];
            int[] columnScan = {x[0][column], x[1][column], x[2][column], x[3][column], x[4][column], x[5][column], x[6][column], x[7][column], x[8][column]};            
            AV.clear(); AV.addAll(nums);
            for(int i=0; i<9; i++) {        
                  if(rowScan[i]!=0) {
                      UAV.add(rowScan[i]);
                  }
                  if(columnScan[i]!=0) {
                      UAV.add(columnScan[i]);
                  }
                  AV.removeAll(UAV); UAV.clear();
              }
            return AV;
    }
}
