import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TicTacToe {
    int boardWidth = 900; // 50px for text panel on right
    int boardHeight = 700; //50px for text panel on top //50px for 2nd text panel

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JLabel textLabel1 = new JLabel();
    JPanel textPanel1 = new JPanel();
    JPanel boardPanel = new JPanel();

    JPanel controlPanel = new JPanel(); //Panel for restart buttom.

    JButton[][] board = new JButton[3][3];
    String player1 = "X";
    String player2 = "O";
    String currentplayer = player1;

    boolean gameOver = false;
    int turns = 0;

    TicTacToe(){
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //North text label
        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe Game");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel,BorderLayout.NORTH);

        //South text label
        textLabel1.setBackground(Color.darkGray);
        textLabel1.setForeground(Color.white);
        textLabel1.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel1.setHorizontalAlignment(JLabel.CENTER);
        textLabel1.setText("Player 1's Turn.");
        textLabel1.setOpaque(true);

        textPanel1.setLayout(new BorderLayout());
        textPanel1.add(textLabel1);
        frame.add(textPanel1,BorderLayout.SOUTH);

        //Board panel
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.gray);
        frame.add(boardPanel);

        // Control panel with Restart and Exit buttons
        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.BOLD, 30));
        restartButton.setFocusable(false);

        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 30));
        exitButton.setFocusable(false);

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });

        controlPanel.setLayout(new GridLayout(2, 1, 10, 10)); // Stack buttons vertically
        controlPanel.add(restartButton);
        controlPanel.add(exitButton);
        frame.add(controlPanel, BorderLayout.EAST); // Place on the right side

        for(int r=0 ; r<3 ; r++){
            for(int c=0 ; c<3 ; c++){
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.lightGray);
                tile.setForeground(Color.black);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        if(gameOver){
                            return;
                        }
                        JButton tile = (JButton) e.getSource();
                        if(tile.getText()==""){
                            tile.setText(currentplayer);
                            turns++;
                            checkWinner();
                            if(!gameOver){
                                currentplayer = currentplayer == player1 ? player2 : player1;
                                if(currentplayer== player1){
                                    textLabel1.setText("player 1's Turn." );
                                }
                                else{
                                    textLabel1.setText("player 2's Turn." );
                                }

                            }
                        }
                    }
                });
            }
        }
    }

    void checkWinner(){ 

        //Horizentaly

        for(int r=0 ; r<3 ; r++){
            if(board[r][0].getText() == "") continue; 

            if(board[r][0].getText() == board[r][1].getText() && board[r][1].getText() == board[r][2].getText()){
                for(int i=0 ; i<3; i++){
                    setWinner(board[r][i]);
                }
                gameOver = true;
                return;
            }
        }

        //Vertical
        for(int c=0 ; c<3 ; c++){
            if(board[0][c].getText() == "") continue; 

            if(board[0][c].getText() == board[1][c].getText() && board[1][c].getText() == board[2][c].getText()){
                for(int i=0 ; i<3; i++){
                    setWinner(board[i][c]);
                }
                gameOver = true;
                return;
            }
        }

        //Diagonally
        if(board[0][0].getText() == board[1][1].getText() && board[1][1].getText() == board[2][2].getText() && board[0][0].getText() != ""){
            for(int i=0 ; i<3; i++){
                setWinner(board[i][i]);
            }
            gameOver = true;
            return;
        }

        //Anti Diagonally
        if(board[0][2].getText() == board[1][1].getText() && board[1][1].getText() == board[2][0].getText() && board[0][2].getText() != ""){
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            return;
        }

        if(turns == 9){
            for(int r=0; r<3; r++){
                for(int c=0;c<3;c++){
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
        }

    }

    void setWinner(JButton tile){
        tile.setForeground(Color.green);
        tile.setBackground(Color.black);
        if(currentplayer == player1){
            textLabel1.setText("player 1 is the winner!");
            
        }
        else{
            textLabel1.setText("player 2 is the winner!");
        }
        
    }

    void setTie(JButton tile){
        tile.setForeground(Color.orange);
        tile.setBackground(Color.BLACK);
        textLabel1.setText("Game is Tie!");
    }

    void restartGame() {
        currentplayer = player1;
        gameOver = false;
        turns = 0;
        textLabel1.setText("Player 1's Turn.");

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setBackground(Color.lightGray);
                board[r][c].setForeground(Color.black);
            }
        }
    }

}
