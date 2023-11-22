import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JOptionPane;

/**
 * This is the class Sudoku and it handles the functionality of the main game.
 * @author Lauren Scott
 * @version Student Sample Code
 */
public class Sudoku {
    private JTextField[][] sudokuGrid;
    private JButton solveButton, saveButton, undoButton, clearButton, loadButton, quitButton;
    private ArrayList<Slot> list;
    private String[][] solution;//This array stores the solution to the game
    private Slot[][] populatedBoard;//This is the board of moves for the game
    private Scanner reader;//This scanner is used to read the game and level files
    private int gameSize;    //This will be the size of the game
    private String level = "Levels/su1.txt";//This is the level file,changable for easy and hard
    private String levelsaddamnvn = "Levels/su11.txt";//This is the level file,changable for easy
    /**
     * This is the constructor for the class Sudoku
     */
    public Sudoku() {
    
        list=new ArrayList();
        try {
            reader = new Scanner(new File(level));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        gameSize = calculateGameSize();
        solution = new String[gameSize][gameSize];
        populatedBoard = new Slot[gameSize][gameSize];
        readLevelFile();
        loadWinSolution();
    }
    /**
     * This method gets the entire set of moves in the game
     * @return the set of moves
     */
    public Slot[][] getMoves() {
        return populatedBoard;
    }
    /**
     * This method gets an individual cell's state
     * @param row - the row of the move
     * @param col - the column of the move
     * @return The state of that cell
     */
    public String getIndividualMove(int row, int col) {
        return populatedBoard[row][col].getState();
    }
    /**
     * This method reads the game size from the file 
     * @return the size of the puzzle
     */
    public int calculateGameSize() {
        return Integer.parseInt(reader.next());
    }
    /**
     * This method provides access to the gameSize from other classes
     * @return the size of the puzzle
     */
    public int getGameSize() {
        return gameSize;
    }
    /**
     * This method reads the level file to populate the game
     * @return The moves stored in the file
     */
    public Slot[][] readLevelFile() {//all the data transfer to board
    
        while (reader.hasNext()) {
            int row =Integer.parseInt(reader.next());
            int col =Integer.parseInt(reader.next());
            String move = reader.next();
            
            populatedBoard[row][col] = new Slot(col, row, move, false);
            
        }
        return populatedBoard;
    }
    /**
     * This method reads the solution file that corresponds to the level file
     */
    public void loadWinSolution() {
        
        Scanner reader = null;
        try {
            reader = new Scanner(new File("Solutions/su1solution.txt"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        while (reader.hasNext()) {
            int row =Integer.parseInt(reader.next());
            int col =Integer.parseInt(reader.next());
            String move = reader.next();
            solution[row][col] = move;
            
        }
        
    }
    /**
     * This method checks whether the gane has been won
     * @return whether the game has been won
     */
    public Boolean checkWin(){
        for (int i = 0; i<gameSize; i++) {
            for (int c = 0; c <gameSize; c++) {
                if (!populatedBoard[i][c].getState().equals(solution[i][c])) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * This method allows a user to make a move in the game
     * @param row - the row of the move
     * @param col - the column of the move
     * @param number - the number they are wishing to enter in the cell
     * @return whether the move was valid
     */
    public Boolean makeMove(String row, String col, String number){
        int enteredRow = Integer.parseInt(row);
        int enteredCol = Integer.parseInt(col);
        if (populatedBoard[enteredRow][enteredCol].getFillable()){
            list.add(new Slot(enteredCol,enteredRow, populatedBoard[enteredRow][enteredCol].getState()));

            populatedBoard[enteredRow][enteredCol].setState(number);
            if(checkWin())
              {
                  System.out.println("Congratulations, you solved the puzzle");
              }
            return true;
        } else {
            return false;
        }
    }
    public void undo() {
        
        if (!list.isEmpty()) {
                        Slot lastItem = list.get(list.size() - 1);
                        System.out.println(lastItem.getRow()+" "+ lastItem.getCol());
                        populatedBoard[lastItem.getRow()][lastItem.getCol()].setState(lastItem.getState());
                        list.remove(list.size() - 1);
                       
                        
                    } 
    }
    
    public void saveGame()//saddamnvn
    {
                    // Use FileWriter and PrintWriter to write to the file
            try (PrintWriter writer = new PrintWriter(new FileWriter(level))) {
                // Iterate through the populatedBoard and write each Slot's information to the file
                writer.println(gameSize);
                for (int row = 0; row < gameSize; row++) {
                    for (int col = 0; col < gameSize; col++) {
                        Slot slot = populatedBoard[row][col];
                        // Write row, col, and state (value) to the file
                        writer.println(row + " " + col + " " + slot.getState());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        
        for (int i = 0; i<gameSize; i++) {
            for (int c = 0; c <gameSize; c++) {
                System.out.print(populatedBoard[i][c].getState()+" ");
            }
              System.out.println();
        }
    }
    
    public void loadGame() { //saddamnvn
        
        Scanner reader = null;
        try {
            reader = new Scanner(new File(level));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
        // Skip the first line (comment or header)
        reader.nextLine();
    
        // Read the size of the Sudoku grid
        
        while (reader.hasNext()) {
            int row =Integer.parseInt(reader.next());
            int col =Integer.parseInt(reader.next());
            String move = reader.next();
            populatedBoard[row][col] = new Slot(col, row, move, false);
            
        }
        
    }
    
    public void clearGame()//saddamnvn
    {
         // Reset the game board to an empty state
         populatedBoard = new Slot[gameSize][gameSize];
         for (int i = 0; i<gameSize; i++) {
            for (int c = 0; c <gameSize; c++) {
                 populatedBoard[i][c] = new Slot(c, i, "-", false);
            }
        }
    }
    
    public JPanel GUI()//saddamnvn

    {
        JPanel panelh = new JPanel(new BorderLayout());
        
        sudokuGrid = new JTextField[gameSize][gameSize];
        solveButton = new JButton("Solve");
        saveButton = new JButton("Save");
        undoButton = new JButton("Undo");
        clearButton = new JButton("Clear");
        loadButton = new JButton("Load");
        quitButton = new JButton("Quit");
        // Create the Sudoku grid
        JPanel sudokuPanel = new JPanel(new GridLayout(gameSize, gameSize, 2, 2));
        for (int row = 0; row < gameSize; row++) {
            for (int col = 0; col < gameSize; col++) {
                sudokuGrid[row][col] = new JTextField(populatedBoard[row][col].getState());
                sudokuGrid[row][col].setPreferredSize(new Dimension(50,55)); 
                sudokuGrid[row][col].setHorizontalAlignment(JTextField.CENTER);

                sudokuPanel.add(sudokuGrid[row][col]);
                 mouselistner(sudokuGrid[row][col], row, col);
            }
        }

        // Create a panel for buttons and add them
       JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(quitButton);
        
         panelh.add(sudokuPanel, BorderLayout.NORTH);
         panelh.add(buttonPanel, BorderLayout.CENTER);
          // Add the Quit button and set up its action listener
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });
        // Add the Clear button and set up its action listener
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reset the game board to an empty state
                populatedBoard = new Slot[gameSize][gameSize];
               // Clear the Sudoku grid
                    for (int row = 0; row < gameSize; row++) {
                        for (int col = 0; col < gameSize; col++) {
                              sudokuGrid[row][col].setText("-");
                              populatedBoard[row][col] = new Slot(col, row, "-", false);
                              sudokuGrid[row][col].setBackground(Color.white);
                        }
                    }
                  list.clear();
            }
        });
        // Add the Undo button and set up its action listener
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement your undo logic here
                // For simplicity, you may revert the last move or clear the grid
                // Get the last item in the ArrayList
                    if (!list.isEmpty()) {
                        Slot lastItem = list.get(list.size() - 1);
                        sudokuGrid[lastItem.getRow()][lastItem.getCol()].setText(lastItem.getState());
                        sudokuGrid[lastItem.getRow()][lastItem.getCol()].setBackground(Color.white);
                        populatedBoard[lastItem.getRow()][lastItem.getCol()].setState(lastItem.getState());
                        list.remove(list.size() - 1);
                       
                        
                    } 
            }
        });

         // Add the Save button and set up its action listener
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                         // Use FileWriter and PrintWriter to write to the file
            try (PrintWriter writer = new PrintWriter(new FileWriter(level))) {
                // Iterate through the populatedBoard and write each Slot's information to the file
                writer.println(gameSize);
                for (int row = 0; row < gameSize; row++) {
                    for (int col = 0; col < gameSize; col++) {
                        Slot slot = populatedBoard[row][col];
                        // Write row, col, and state (value) to the file
                        writer.println(row + " " + col + " " + slot.getState());
                    }
                }
            } catch (IOException f) {
                f.printStackTrace(); // Handle the exception appropriately
            }
        
                  JOptionPane.showMessageDialog(null, "Saved Successfully in :"+level);
                  
            }
        });
        
        // Add the Load button and set up its action listener
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                             Scanner reader = null;
                    try {
                        reader = new Scanner(new File(level));
                    } catch (FileNotFoundException f) {
                        // TODO Auto-generated catch block
                        f.printStackTrace();
                    }
                
                    // Skip the first line (comment or header)
                    reader.nextLine();
                
                    // Read the size of the Sudoku grid
                    
                    while (reader.hasNext()) {
                        int row =Integer.parseInt(reader.next());
                        int col =Integer.parseInt(reader.next());
                        String move = reader.next();
                        sudokuGrid[row][col].setText(move);
                        populatedBoard[row][col] = new Slot(col, row, move, false);
                        
                    }
            }
        });

       return panelh;
    }
    public void mouselistner(JTextField tt,int j,int i)
    {
         tt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action(tt,j,i);

            }
         });
         
          
    }
    public void action(JTextField tt,int j,int i)
    {
         // This method is called when Enter key is pressed in the JTextField
                 if(tt.getText().isBlank()|| tt.getText().isEmpty()||tt.getText().equals("-"))
                {
                   
                }
                else
                {
                      if( makeMove(Integer.toString(j),Integer.toString(i), tt.getText()))
                      {
                          System.out.println(" Data  inserted");
                          
                          tt.setBackground(Color.gray);
                          if(checkWin())
                          {
                              JOptionPane.showMessageDialog(null, "Congratulations, you solved the puzzle");
                          }
                          
                          
                          
                      }
                      else
                      {
                          tt.setText(populatedBoard[j][i].getState());
                          JOptionPane.showMessageDialog(null, "Data not inserted");
                          
                      }
              
            }
    }
}//end of class Sudoku
