import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TextBesed extends JFrame {
    JTextField jt;int n;
    ArrayList<Integer> rowcolvalue=new ArrayList();
    int [][]sudokuGrid;
        Undocheker Undocheker;

   ArrayList<Undocheker>list =new ArrayList() ;

    public TextBesed(int nm) {
        
        this.n=nm;
        sudokuGrid=new int[n][n];
        // Set the title of the JFrame
        setTitle("Main Frame Example");

        // Create an instance of MainPanel and add it to the JFrame
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create an instance of TopPanel and add it to the top of MainPanel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.gray);
        topPanel.add(new JLabel("Text-Based UI Implementation:"));
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.white);
        if(n==9)
        {
           centerPanel.add(new JLabel("Col         0 1 2 3 4 5 6 7 8"));
         
         centerPanel.add(new JLabel("Row 1|  0 0 0 0 0 0 0 0 0"));
         centerPanel.add(new JLabel("Row 2|  0 0 0 0 0 0 0 0 0"));
         centerPanel.add(new JLabel("Row 3|  0 0 0 0 0 0 0 0 0"));
         centerPanel.add(new JLabel("Row 4|  0 0 0 0 0 0 0 0 0"));
         centerPanel.add(new JLabel("Row 5|  0 0 0 0 0 0 0 0 0"));
         centerPanel.add(new JLabel("Row 6|  0 0 0 0 0 0 0 0 0"));
         centerPanel.add(new JLabel("Row 7|  0 0 0 0 0 0 0 0 0"));
         centerPanel.add(new JLabel("Row 8|  0 0 0 0 0 0 0 0 0"));
         centerPanel.add(new JLabel("Row 9|  0 0 0 0 0 0 0 0 0")); 
        }
        else
        {
          centerPanel.add(new JLabel("Col         0 1 2 3"));
         
         centerPanel.add(new JLabel("Row 1|  0 0 0 0"));
         centerPanel.add(new JLabel("Row 2|  0 0 0 0"));
         centerPanel.add(new JLabel("Row 3|  0 0 0 0"));
         centerPanel.add(new JLabel("Row 4|  0 0 0 0"));
           
        }
        
         
         
         centerPanel.add(choiceoption());
         
         
        //
        
        // Create a JScrollPane and add the bottomPanel to it
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Create an instance of BottomPanel and add it to the bottom of MainPanel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0)); // Set FlowLayout with zero gaps
        bottomPanel.setBackground(Color.gray);
        jt=new JTextField();
        jt.setPreferredSize(new Dimension(800,50));  
        bottomPanel.add(jt);
        mouselistner(jt,centerPanel);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);

        // Set default close operation and size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }
     public void mouselistner(JTextField tt,JPanel centerPanel)
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
              
            }
           });
         tt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             JLabel tj=new JLabel (tt.getText());
             tj.setForeground(Color.BLUE);
             centerPanel.add(tj);
            
             centerPanel.revalidate();
             if (isNumeric(tt.getText())) {
                 if(rowcolvalue.size()<2)
                 {
                     rowcolvalue.add(Integer.parseInt(tt.getText()));
                      tt.setText("");
                 }
                 else
                 {
                   rowcolvalue.add(Integer.parseInt(tt.getText()));
                   boolean checkcolumn= columnckecking(rowcolvalue.get(2),rowcolvalue.get(0),rowcolvalue.get(1));
                   if(checkcolumn)
                   {
                        boolean checkrow= rowckecking(rowcolvalue.get(2),rowcolvalue.get(0),rowcolvalue.get(1));
                        if(checkrow)
                        {
                             boolean checksubgrid= subgridckecking(rowcolvalue.get(2),rowcolvalue.get(0),rowcolvalue.get(1));
                             if(checksubgrid)
                             {
                               sudokuGrid[rowcolvalue.get(0)][rowcolvalue.get(1)]=rowcolvalue.get(2);
                                    Undocheker= new Undocheker();
                                    Undocheker.setRow(rowcolvalue.get(0));
                                    Undocheker.setCol(rowcolvalue.get(1));
                                    list.add(Undocheker);
                                rowcolvalue.clear();
                                    
                                if(win()==true)
                                    {
                                     JOptionPane.showMessageDialog(null," You Are Winner !!!.");

                                    }
                                
                                tt.setText("");  
                             }
                             else
                             {
                                 tt.setText("");
                                 centerPanel.add(new JLabel(" Data already exist in Subgrid"));
                             }
                            
                        }
                        else
                        {
                             tt.setText("");
                            centerPanel.add(new JLabel("Data already exist in row"));
                        }
                       
                       
                   }
                   else
                   {
                        tt.setText("");
                        centerPanel.add(new JLabel("Data already exist in column"));
                   }
                   centerPanel.add(choiceoption());
                   centerPanel.revalidate();
                   System.out.println("true");
                 }
                
                //checker board
                } else {
                    System.out.println("false");
                      switch (tt.getText()) {
            case "M":
                 tt.setText("");
                 makeMove(centerPanel);
                 
                break;
            case "S":
                 tt.setText("");
                saveSudoku();
               
                break;
            case "L":
                 tt.setText("");
                loadSudoku();
                if(n==9)
                 centerPanel.add(new JLabel("Col         0 1 2 3 4 5 6 7 8"));
                else
                    centerPanel.add(new JLabel("Col         0 1 2 3"));
                 for(int i=0;i<n;i++)
                {   String str="Row "+(i+1)+"|  ";
                    for(int j=0;j<n;j++)
                    {
                      //System.out.print(sudokuGrid[i][j]);
                      str=str+ sudokuGrid[i][j]+" "; 
                    }
                    System.out.println(str);
                    centerPanel.add(new JLabel(str));
                   
                }
                 centerPanel.add(choiceoption());
                  centerPanel.revalidate();
                break;
            case "U":
                 tt.setText("");
                 if (!list.isEmpty()) {
                        Undocheker lastItem = list.get(list.size() - 1);
                        System.out.println(lastItem.getRow()+" "+ lastItem.getCol());
                        sudokuGrid[lastItem.getRow()][lastItem.getCol()]=0;
                        list.remove(list.size() - 1);
                       
                        
                    } 
                break;
            case "C":
                 tt.setText("");
                for (int row = 0; row < n; row++) {
                        for (int col = 0; col < n; col++) {
                              sudokuGrid[row][col]=0;
                            
                        }
                    }
                     rowcolvalue.clear();
                break;
            case "Q":
               System.exit(0);
                break;
            default:
               // label = new JLabel(option);
        }
          
                }
             
              
            }
         });
         
          
    }
     public boolean win()
{
    for(int i=0;i<n;i++)
    {
        for(int j=0;j<n;j++)
        {
            if(sudokuGrid[i][j]==0) 
                return false;
        }
       
    }
    return true;
}
     
     
     private boolean subgridckecking(int text, int row, int col)
{
        int currentNumber = text;
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
             int yu = sudokuGrid[i][j];
             if(text==yu)
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

            int yu = sudokuGrid[i][j];
            if (text==yu) {
                return false;
            }
        }
    } 
        }
        
        

    return true; // Subgrid is valid
}
     private boolean rowckecking(int value, int row, int col)
{
    boolean hh=true;
    for(int i=0;i<n;i++)
    {
       if(i!=col)
       {
           int yu = sudokuGrid[row][i];

           if(value==yu)
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
     private boolean columnckecking(int value, int row, int col)
{
    
    boolean hh=true;
    for(int i=0;i<n;i++)
    {
       if(i!=row)
       {
           int yu = sudokuGrid[i][col];

           if(value==yu)
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
     private static boolean isNumeric(String str) {
        try {
            // Attempt to parse the string as a double
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            // If an exception is caught, it's not a valid number
            return false;
        }
    }
     public JPanel choiceoption()
     {
         JPanel centerPanel1 = new JPanel();
         centerPanel1.setLayout(new BoxLayout(centerPanel1, BoxLayout.Y_AXIS));
         centerPanel1.add(new JLabel("Please Select an Option:"));
         
         centerPanel1.add(new JLabel("[M] make move"));
         centerPanel1.add(new JLabel("[S] save game"));
         centerPanel1.add(new JLabel("[L] load saved game"));
         centerPanel1.add(new JLabel("[U] undo move"));
         centerPanel1.add(new JLabel("[C] clear game"));
         centerPanel1.add(new JLabel("[Q] quit game"));
         return centerPanel1;
     }
     public void makeMove(JPanel centerPanel)
     {
        centerPanel.add(new JLabel("Which row and col is the cell you wish to fill with and  the value?"));
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

                                 if(sudokuGrid[startRow + i][startCol + j]==0)
                                {
                                   writer.print("0");
                                }
                                else
                                {
                                     writer.print(sudokuGrid[startRow + i][startCol + j]);
                                     
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
                            if (sudokuGrid[startRow + i][startCol + j]==0) {
                                writer.print("0");
                            } else {
                                writer.print(sudokuGrid[startRow + i][startCol + j]);
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
                                    sudokuGrid[startRow + i][startCol + j]=Integer.parseInt(cellValue);
                                } else {
                                    sudokuGrid[startRow + i][startCol + j]=0;
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
                                    sudokuGrid[startRow + i][startCol + j]=Integer.parseInt(cellValue);
                                } else {
                                    sudokuGrid[startRow + i][startCol + j]=0;
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
                    new TextBesed(gridSize);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid selection. Please choose a valid grid size.", "Invalid Selection", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                System.exit(0); // Exit the application if the user cancels
            }
        });
    }
}
