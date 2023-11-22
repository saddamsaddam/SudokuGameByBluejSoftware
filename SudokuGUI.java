import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class SudokuGUI extends JFrame {
    private JTextField[][] sudokuGrid;
    private JButton solveButton, saveButton, undoButton, clearButton, loadButton, quitButton;
    private int n;
    Undocheker Undocheker;
    ArrayList<Undocheker>list =new ArrayList() ;

    public SudokuGUI(int n) {
        this.n = n;
        initializeUI();
        // Set the size of the frame
    int width = 600;  // Change this value to your desired width
    int height = 600; // Change this value to your desired height
    // Center the frame on the screen
        setLocationRelativeTo(null);
    setSize(new Dimension(width, height));
    }

    private void initializeUI() {
        sudokuGrid = new JTextField[n][n];
        solveButton = new JButton("Solve");
        saveButton = new JButton("Save");
        undoButton = new JButton("Undo");
        clearButton = new JButton("Clear");
        loadButton = new JButton("Load");
        quitButton = new JButton("Quit");

        // Set up the main frame
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the Sudoku grid
        JPanel sudokuPanel = new JPanel(new GridLayout(n, n, 2, 2));
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                sudokuGrid[row][col] = new JTextField();
                sudokuGrid[row][col].setHorizontalAlignment(JTextField.CENTER);
               // Add your custom mouse listener, action listener, focus listener, and document filter

               mouselistner(sudokuGrid[row][col], row, col,n);
               // sudokuGrid[row][col].getDocument().addDocumentListener(new SudokuDocumentListener(row, col));

                sudokuPanel.add(sudokuGrid[row][col]);
            }
        }

        // Add the Sudoku grid to the main frame
        add(sudokuPanel, BorderLayout.CENTER);

   

        // Add the Save button and set up its action listener
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSudoku();
                
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
                        Undocheker lastItem = list.get(list.size() - 1);
                        System.out.println(lastItem.getRow()+" "+ lastItem.getCol());
                        sudokuGrid[lastItem.getRow()][lastItem.getCol()].setText("");
                        sudokuGrid[lastItem.getRow()][lastItem.getCol()].setBackground(Color.white);
                        list.remove(list.size() - 1);
                       
                        
                    } 
            }
        });

        // Add the Clear button and set up its action listener
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // Clear the Sudoku grid
                    for (int row = 0; row < n; row++) {
                        for (int col = 0; col < n; col++) {
                              sudokuGrid[row][col].setText("");
                             sudokuGrid[row][col].setBackground(Color.white);
                        }
                    }
                  list.clear();
            }
        });

        // Add the Load button and set up its action listener
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSudoku();
            }
        });

        // Add the Quit button and set up its action listener
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });

        // Create a panel for buttons and add them
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(quitButton);

        // Add the button panel to the bottom of the frame
        add(buttonPanel, BorderLayout.SOUTH);

        // Set frame properties
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    
    
    public void mouselistner(JTextField tt,int j,int i,int n)
    {
        tt.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // This method is called when the mouse is clicked
            }

            @Override
            public void mousePressed(MouseEvent e) {
               
                // This method is called when the mouse button is pressed
                
                // This method is called when Enter key is pressed in the JTextField
                 action(tt,j,i,n);
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // This method is called when the mouse button is released
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // This method is called when the mouse enters the component (JTextField)
               // handleMouseEnter();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // This method is called when the mouse exits the component (JTextField)
                 action(tt,j,i,n);
              
            }
           });
         tt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action(tt,j,i,n);

            }
         });
         
          
    }
    
    public void action(JTextField tt,int j,int i,int n)
    {
         // This method is called when Enter key is pressed in the JTextField
                 if(tt.getText().isBlank()|| tt.getText().isEmpty())
                {
                    
                }
                else
                {
         
              boolean checkcolumn= columnckecking(tt.getText(),j,i,n);
              if(checkcolumn)
              {
                  boolean checkrow= rowckecking(tt.getText(),j,i);
                  if(checkrow)
                  {
                      boolean checksubgrid= subgridckecking(tt.getText(),j,i);
                      if(checksubgrid)
                      {
                          //success
                          Undocheker= new Undocheker();
                          Undocheker.setRow(j);
                          Undocheker.setCol(i);
                          list.add(Undocheker);
                          tt.setBackground(Color.gray);
                          if(win()==true)
                          {
                           JOptionPane.showMessageDialog(null," You Are Winner !!!.");
   
                          }
                      }
                      else
                      {
                          tt.setText("");
                          System.out.println(" Data already exist in Subgrid"); 
                          
                      }
                      
                  }
                  else
                  {
                      tt.setText("");
                     System.out.println(" Data already exist in row");
                  }
              }
              else
              {
                   // Reset the text to an empty string if a duplicate value is found in the column
                  
                   tt.setText("");
                  System.out.println(" Data already exist in column");
                  
              }
            }
    }
public boolean win()
{
    for(int i=0;i<n;i++)
    {
        for(int j=0;j<n;j++)
        {
            if(sudokuGrid[i][j].getText().isBlank()||sudokuGrid[i][j].getText().isBlank()) 
                return false;
        }
       
    }
    return true;
}
  
private boolean columnckecking(String text, int row, int col,int n)
{
    
    boolean hh=true;
    for(int i=0;i<n;i++)
    {
       if(i!=row)
       {
           String yu = sudokuGrid[i][col].getText();

           if(text.equals(yu))
           {
              hh=false;
              
           }
           else
           {
               
           }
       }
    }
    if(hh==false)
    return false;
    else
     return true;

}
private boolean rowckecking(String text, int row, int col)
{
    boolean hh=true;
    for(int i=0;i<n;i++)
    {
       if(i!=col)
       {
           String yu = sudokuGrid[row][i].getText();

           if(text.equals(yu))
           {
              hh=false;
           }
           else
           {
               
           }
       }
    }
    if(hh==false)
    return false;
    else
     return true;

}

private boolean subgridckecking(String text, int row, int col)
{
        int currentNumber = Integer.parseInt(text);
        int subgridSize = (int) Math.sqrt(n);
        int startRow = row - row % subgridSize;
        
        int startCol = col - col % subgridSize;
        
        if(n==9){
             for (int i = startRow; i < startRow + 3; i++) {
        for (int j = startCol; j < startCol + 3; j++) {
            
            // Check if the current number is between 1 and 9
            if (currentNumber < 1 || currentNumber > 9) {
                return false; // Invalid number in subgrid
            }
            
            
            if(i!=row && j!=col)
            {
             String yu = sudokuGrid[i][j].getText();
             if(text.equals(yu))
             {
                 return false;
             }
            }
            
        }
    }
        }
        else
        {
            for (int i = startRow; i < startRow + subgridSize; i++) {
        for (int j = startCol; j < startCol + subgridSize; j++) {
            // Check if the current number is between 1 and 4 (for a 4x4 grid)
            if (currentNumber < 1 || currentNumber > 4) {
                return false; // Invalid number in subgrid
            }

            // Skip checking the current cell
            if (i == row && j == col) {
                continue;
            }

            String yu = sudokuGrid[i][j].getText();
            if (text.equals(yu)) {
                return false;
            }
        }
    } 
        }
        
        

    return true; // Subgrid is valid
}



  private void saveSudoku() {
    // Save the current state of the Sudoku grid to a file
        JFileChooser fileChooser = new JFileChooser();
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

              try (PrintWriter writer = new PrintWriter(fileToSave)) {
                  //take input grid size
                     writer.print(Integer.toString(n));
                     writer.println();
                     if(n==9)
                     {
                // Iterate through each subgrid (3x3) and save its data
                for (int startRow = 0; startRow < 9; startRow += 3) {
                    for (int startCol = 0; startCol < 9; startCol += 3) {
                        
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {

                                 if(sudokuGrid[startRow + i][startCol + j].getText().isBlank()|| sudokuGrid[startRow + i][startCol + j].getText().isEmpty())
                                {
                                   writer.print("0");
                                }
                                else
                                {
                                     writer.print(sudokuGrid[startRow + i][startCol + j].getText());
                                     
                                }
                                 if(j!=2)
                                 writer.print(",");
                            }
                              // Move to the next line after each row
                               writer.println();
                        }
                        
                        
                    }
                }
                     }
                     else
                     {
                         // Iterate through each subgrid (2x2 for a 4x4 grid) and save its data
            for (int startRow = 0; startRow < n; startRow += 2) {
                for (int startCol = 0; startCol < n; startCol += 2) {
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            // Check if the cell is empty
                            if (sudokuGrid[startRow + i][startCol + j].getText().isBlank() ||
                                    sudokuGrid[startRow + i][startCol + j].getText().isEmpty()) {
                                writer.print("0");
                            } else {
                                writer.print(sudokuGrid[startRow + i][startCol + j].getText());
                            }

                            // Add a comma unless it's the last column in the subgrid
                            if (j != 1) {
                                writer.print(",");
                            }
                        }

                        // Move to the next line after each row
                        writer.println();
                    }
                }
            }
                     }

                // Provide feedback to the user that the save was successful
                JOptionPane.showMessageDialog(this, "Sudoku saved successfully!", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception, e.g., show an error message to the user
                JOptionPane.showMessageDialog(this, "Error saving Sudoku: " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
            }
        }
}


   
   private void loadSudoku() {
    JFileChooser fileChooser = new JFileChooser();
    int userSelection = fileChooser.showOpenDialog(this);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToLoad = fileChooser.getSelectedFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileToLoad))) {
            // sckip first line(grid size)
            reader.readLine();
            
            if(n==9)
            {
            for (int startRow = 0; startRow < n; startRow += 3) {
                for (int startCol = 0; startCol < n; startCol += 3) {
                    for (int i = 0; i < 3; i++) {
                        String line = reader.readLine();
                        if (line != null && line.length() >= 5) { // Assuming each subgrid has at least 5 characters
                            String[] values = line.split(",");
                            for (int j = 0; j < 3; j++) {
                                String cellValue = values[j].trim();
                                if (!cellValue.equals("0")) {
                                    sudokuGrid[startRow + i][startCol + j].setText(cellValue);
                                } else {
                                    sudokuGrid[startRow + i][startCol + j].setText("");
                                }
                            }
                        }
                    }
                }
            }
            }
            else
            {
                for (int startRow = 0; startRow < n; startRow += 2) {
                for (int startCol = 0; startCol < n; startCol += 2) {
                    for (int i = 0; i < 2; i++) {
                        String line = reader.readLine();
                        if (line != null && line.length() >= 3) { // Assuming each subgrid row has at least 3 characters
                            String[] values = line.split(",");
                            for (int j = 0; j < 2; j++) {
                                String cellValue = values[j].trim();
                                if (!cellValue.equals("0")) {
                                    sudokuGrid[startRow + i][startCol + j].setText(cellValue);
                                } else {
                                    sudokuGrid[startRow + i][startCol + j].setText("");
                                }
                            }
                        }
                    }
                }
            }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



   public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Display the initial JComboBox for grid size selection
            JComboBox<String> initialComboBox = new JComboBox<>(new String[]{"Select Grid Size", "4", "9"});
            int result = JOptionPane.showOptionDialog(
                    null,
                    initialComboBox,
                    "Select Sudoku Grid Size",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null
            );

            if (result == JOptionPane.OK_OPTION) {
                // Get the selected item and create SudokuGUI
                String selectedSize = (String) initialComboBox.getSelectedItem();
                if (selectedSize.equals("4") || selectedSize.equals("9")) {
                    int gridSize = Integer.parseInt(selectedSize);
                    new SudokuGUI(gridSize);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid selection. Please choose a valid grid size.", "Invalid Selection", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                System.exit(0); // Exit the application if the user cancels
            }
        });
    }
}

class Undocheker
{
private int row,col;
// Getter for row
    public int getRow() {
        return row;
    }

    // Setter for row
    public void setRow(int row) {
        this.row = row;
    }

    // Getter for col
    public int getCol() {
        return col;
    }

    // Setter for col
    public void setCol(int col) {
        this.col = col;
    }

    // Override toString method
    @Override
    public String toString() {
        return "Undocheker{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}

