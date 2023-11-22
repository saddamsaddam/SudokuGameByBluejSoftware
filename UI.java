import java.util.Scanner;
import java.io.*;
import javax.swing.JComboBox;
import javax.swing.*;
import java.awt.*;

/**
 * This class provides a text based user interface for the player to interact with the game
 * @author Lauren Scott
 * @version student sample code
 */
public class UI extends JFrame {
    private Sudoku thegame;//this is the game model
    private String menuChoice;//this is the users choice from the menu
    private Scanner reader;//this scanner is used to read the terminal
    /**
     * Constructor for the class UI
     */
    public UI() {
        thegame = new Sudoku();
        reader = new Scanner(System.in);
        JComboBox<String> initialComboBox = new JComboBox<>(new String[]{ "Text-Based UI Implementation", "GUI Implementation"});
            int result = JOptionPane.showOptionDialog(
                    null,
                    initialComboBox,"Select Implementation Type",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null
            );

            if (result == JOptionPane.OK_OPTION) {
                // Get the selected item and create SudokuGUI
                String selectedSize = (String) initialComboBox.getSelectedItem();
                if(selectedSize.equals("Text-Based UI Implementation"))
                   {
                       menuChoice="";
                            while(!menuChoice.equalsIgnoreCase("Q")&&!thegame.checkWin()) {
                                displayGame();
                                menu();
                                menuChoice = getChoice();
                    
                            }
                            if (thegame.checkWin()) {
                                winningAnnouncement();
                            }
                   }
                   else
                   {
                        int width = 600;
                        int height = 600;
                
                        this.setTitle("Sudoku Game");
                        this.setSize(width, height);
                        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        this.setLocationRelativeTo(null);
                        this.add(thegame.GUI(),BorderLayout.CENTER);
                        // Additional GUI setup code goes here
                
                        this.setVisible(true);
                   } 
            } else {
                System.exit(0); // Exit the application if the user cancels
            }
        
    }

    /**
     * Method that outputs an announcement when the user has won the game
     */
    public void winningAnnouncement() {
        System.out.println("Congratulations, you solved the puzzle");
    }

    /**
     * Method that displays the game for the user to play
     */
    public void displayGame() {
        //boardmoves = thegame.getMoves();
        if (thegame.getGameSize() == 9) {
            System.out.println("Col   0 1 2 3 4 5 6 7 8");
            System.out.println("      - - - - - - - - -");
        } else {
            System.out.println("Col   0 1 2 3 ");
            System.out.println("      - - - - ");
        }

        for (int i = 0; i < thegame.getGameSize(); i++) {
            System.out.print("Row "+i+"|");
            for (int c = 0; c < thegame.getGameSize(); c++) {
                if (thegame.getGameSize() == 9) {
                    if (c == 2 || c == 5 || c == 8) {
                        if (thegame.getIndividualMove(i,c).contains("-") ){
                            System.out.print(" " + "|");
                        } else{
                            System.out.print(thegame.getIndividualMove(i,c) + "|");
                        }
                    } else {
                        if (thegame.getIndividualMove(i,c).contains("-") ){
                            System.out.print(" " + ".");
                        } else{
                            System.out.print(thegame.getIndividualMove(i,c) + ".");
                        }
                    }

                } else if (thegame.getGameSize() == 4) {
                    if (c == 1 || c == 3) {
                        if (thegame.getIndividualMove(i,c).contains("-") ){
                            System.out.print(" " + "|");
                        } else{
                            System.out.print(thegame.getIndividualMove(i,c) + "|");
                        }
                    } else {
                        if (thegame.getIndividualMove(i,c).contains("-") ){
                            System.out.print(" " + ".");
                        } else{
                            System.out.print(thegame.getIndividualMove(i,c) + ".");
                        }
                    }

                
                }
            }if (thegame.getGameSize() == 9 && (i == 2 || i == 5|| i == 8)) {
                System.out.println("\n      - - - - - - - - -");

            } else if (thegame.getGameSize() == 9 ){
                System.out.println("\n      .................");

            } else if (thegame.getGameSize() == 4 && (i==1||i==3) ){
                System.out.println("\n      - - - - ");

            } else {
                System.out.println("\n     .........");
            }
        }
    }

    /**
     * Method that displays the menu to the user
     */
    public void menu() {

        System.out.println("Please select an option: \n"
            + "[M] make move\n"
            + "[S] save game\n"
            + "[L] load saved game\n"
            + "[U] undo move\n"
            + "[C] clear game\n"
            + "[Q] quit game\n");

    }

    /**
     * Method that gets the user's choice from the menu and conducts the activities
     * accordingly
     * @return the choice the user has selected
     * 
     */
    public String getChoice() {
        String choice = reader.next();
        if (choice.equalsIgnoreCase("M")) {
            System.out.print("Which row is the cell you wish to fill?  ");
            String row = reader.next();
            System.out.print("Which colum is the cell you wish to fill?  ");
            String col = reader.next();
            System.out.print("Which number do you want to enter?  ");
            String number = reader.next();
            if(!thegame.makeMove(row, col, number)) {
                System.out.println("That cell cannot be changed");
                while (!thegame.makeMove(row, col, number)) {
                    System.out.print("Which row is the cell you wish to fill?  ");
                    row = reader.next();
                    System.out.print("Which colum is the cell you wish to fill?  ");
                    col = reader.next();
                    System.out.print("Which number do you want to enter?  ");
                    number = reader.next();
                }
                //thegame.makeMove(row, col, number);
            }

        } else if (choice.equalsIgnoreCase("S")) {
            saveGame();
        } else if (choice.equalsIgnoreCase("U")) {
            undoMove();
        } else if (choice.equalsIgnoreCase("L")) {
            loadGame();
        } else if (choice.equalsIgnoreCase("C")) {
            clearGame();
        } else if (choice.equalsIgnoreCase("Q")) {
            System.exit(0);
        }
        return choice;
    }

    /**
     * saveGame 
     * To be implemented by student - this method should undo the previous move made in the game, along with the corresponding computer move
     */
    public void saveGame() {
        
        thegame.saveGame();//saddamnvn
       // System.out.println("Code not yet implemented");
        
    }

    /**
     * undoMove 
     * To be implemented by student - this method should undo the previous move made in the game, along with the corresponding computer move
     */
    public void undoMove() {
        thegame.undo();//saddamnvn
       // System.out.println("Code not yet implemented");
    }

    /**
     * loadGame
     * To be implemented by student - this method should load a previous saved game
     */
    public void loadGame() {
         thegame.loadGame();//saddamnvn
       // System.out.println("Code not yet implemented");
    }

    /**
     * clearGame
     * To be implemented by student - this method should clear the game board and any record of moves, to reset the game
     */
    public void clearGame() {
        thegame.clearGame();//saddamnvn
       // System.out.println("Code not yet implemented");
    }

    
    /**
     * The main method within the Java application. 
     * It's the core method of the program and calls all others.
     */
    public static void main(String args[]) {
        UI thisUI = new UI();
    }
}//end of class UI