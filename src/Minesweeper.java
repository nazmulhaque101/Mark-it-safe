import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;


public class Minesweeper implements ActionListener {
    int G = 0;
    int M = 0;
    long start = 0;
    long end = 0;
    
	JFrame frame = new JFrame("Mark It Safe");
	JPanel panel = new JPanel();
	JFrame frame2 = new JFrame("Mark It Safe");
	
	JLabel label1 = new JLabel("MARK IT SAFE");
	
	
	JButton reset = new JButton("Reset");
	JButton rst = new JButton("Play 8x8");
	JButton rst2 = new JButton("Play 12x12");
	JButton rst3 = new JButton("Play 14x14");
	JButton rst4 = new JButton("EXIT");
	//Image bomb = new Image("bomb.png");
	Container grid = new Container();
	final int MINE = 9999;
	ImageIcon bomb = new ImageIcon("bomb.png");
	ImageIcon hotspot = new ImageIcon("hotspot.png");
	ImageIcon discover = new ImageIcon("discover.png");
	ImageIcon map = new ImageIcon("map.jpg");
	ImageIcon none = new ImageIcon("");
	
	JLabel label = new JLabel("", map, JLabel.CENTER);
	

	public Minesweeper(){
		
		frame2.setSize(720,720);
		frame2.setResizable(false);
		frame2.setIconImage(Toolkit.getDefaultToolkit().getImage(new File("bomb2.png").getAbsolutePath()));
		frame2.setLayout(new BorderLayout());
		frame2.setContentPane(label);
		
		label1.setBounds(130, 10, 400, 90);
	    frame2.add(label1);
	    
		label1.setFont(new Font("Arial", Font.BOLD, 52));
		label1.setForeground(Color.WHITE);
		
		
	    //panel.setBackground(new Color(249, 238, 87));
	    
	    rst.setBounds(250, 100, 150, 150);
	    rst.setFont(new Font("Arial", Font.BOLD, 22));
	    frame2.add(rst);
		rst.addActionListener(this);
		frame2.add(rst2);
	    rst3.setFont(new Font("Arial", Font.BOLD, 22));
	    rst2.setFont(new Font("Arial", Font.BOLD, 22));
	    rst4.setFont(new Font("Arial", Font.BOLD, 22));
		rst2.addActionListener(this);
	    rst2.setBounds(250, 300, 150, 150);
	    frame2.add(rst3);
		rst3.addActionListener(this);
	    rst3.setBounds(250, 500, 150, 150);
	    rst4.setBounds(500, 600, 150, 50);
	    rst4.setBackground(new Color(250, 150, 0));
	    frame2.add(rst4);
		rst4.addActionListener(this);
		
		//panel.setLayout(null);

	    //frame2.getContentPane().add(panel);
	   
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setVisible(true);
	}
	
	JButton[][] buttons = new JButton[20][20];
	int [][] counts = new int [20][20];
	
	public void intro(){
		start = System.currentTimeMillis();
		frame.setSize(720,720);
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(new File("bomb2.png").getAbsolutePath()));
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	    
	    frame.add(reset, BorderLayout.NORTH);
		reset.addActionListener(this);
		
		grid.setLayout(new GridLayout(G,G));

		
		for (int a = 0; a < G; a++) {
			for (int b = 0; b < G; b++) {
				buttons[a][b] = new JButton(discover);
				buttons[a][b].addActionListener(this);
				grid.add(buttons[a][b]);
			}
		}
		frame.add(grid, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		createRandomMines();
	}

	
	public static void main(String[] args)
	{
		new Minesweeper();
	}
	
	public void createRandomMines(){
	//initialize list of random pairs
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int x = 0; x < G; x++) {
			for (int y = 0; y < G; y++) {
				list.add(x*100+y);
			}
		}
		
		counts = new int[G][G];
		for (int a = 0; a < G; a++) {
			int choice = (int)(Math.random() * list.size());
			counts[list.get(choice)/ 100][list.get(choice) % 100] = MINE;
			list.remove(choice);
		}
		
		for (int x = 0; x < G; x++) {
			for (int y = 0; y < G; y++) {
				int neighborcount = 0;
				if(counts[x][y] != MINE){
					if(x>0 && y>0 && counts[x-1][y-1] == MINE){//up left
						neighborcount++;
					}
					if(x<G-1 && y>0 && counts[x+1][y-1] == MINE){//up right
						neighborcount++;
					}
					if(y>0 && counts[x][y-1] == MINE){//up
						neighborcount++;
					}
					
					if(x>0 && counts[x-1][y] == MINE){//left
						neighborcount++;
					}
					if(x<G-1 && counts[x+1][y] == MINE){//right
						neighborcount++;
					}
					if(y<G-1 && counts[x][y+1] == MINE){//down
						neighborcount++;
					}
					if(x>0 && y<G-1 && counts[x-1][y+1] == MINE){//down left
						neighborcount++;
					}
					if(x<G-1 && y<G-1 && counts[x+1][y+1] == MINE){ //down right
						neighborcount++;
					}
				
					counts[x][y] = neighborcount;
				}
			}
		}
	}
	
	public void reset(){
		start = System.currentTimeMillis();
		M = 0;
		for (int x = 0; x < G; x++) {
			for (int y = 0; y < G; y++) {
				buttons[x][y].setEnabled(true);
				buttons[x][y].setText("");
				buttons[x][y].setBackground(null);
				ImageIcon index = new ImageIcon("");
				buttons[x][y].setIcon(index);
			}
		}
		createRandomMines();
	}
	
	public void lostGame(int x1,int y1){
		for (int x = 0; x < G; x++) {
			for (int y = 0; y < G; y++) {
				if(buttons[x][y].isEnabled()){
					if (counts[x][y] != MINE){
						if(counts[x][y] != 0){
							//buttons[x][y].setFont(new Font("Arial", Font.BOLD, 180/G));
							//buttons[x][y].setText(counts[x][y] + "");
						}
						//buttons[x][y].setEnabled(false);
						
					}
					else if(x == x1 && y == y1){
						buttons[x][y].setIcon(hotspot);
					}
					
					else{	
						//buttons[x][y].createImage(arg0, arg1)
						//buttons[x][y].setText("@");
						//buttons[x][y].setBackground(Color.RED);
						buttons[x][y].setIcon(bomb);
						//buttons[x][y].setEnabled(false);
					}
				}
			}
		}
		end = (System.currentTimeMillis() - start)/ 1000;
		int result = JOptionPane.showConfirmDialog(frame,"===========================\n||  Oops!! You stepped on a bomb  ||\n======== Game Over =========\n\nYour Time is:  "+end+" seconds\nIn total you have marked: ( "+M+" ) areas as safe\n\nWant to play again?");
		if (result == JOptionPane.YES_OPTION){
			reset();
		}
		if (result == JOptionPane.NO_OPTION){
			frame.dispose();
		}
		if (result == JOptionPane.CANCEL_OPTION){
			new Minesweeper();
			frame.dispose();
		}
	}
	
	public void reveal(int x, int y)
	{
		if(counts[x][y] != 0){
			buttons[x][y].setIcon(none);
			buttons[x][y].setFont(new Font("Arial", Font.BOLD, 180/G));
			buttons[x][y].setText(counts[x][y]+ "");
			
		}
		buttons[x][y].setIcon(none);
		buttons[x][y].setEnabled(false);
		buttons[x][y].setBackground(new Color(196, 223, 140));
		M++;
		clearZeros(x, y);
		
	}
	
	public void clearZeros(int x, int y){
		if(counts[x][y]==0){
			if(x>0 && y>0 && counts[x-1][y-1] != MINE && buttons[x-1][y-1].isEnabled()){//up left
				reveal(x-1,y-1);
			}
			if(x<G-1 && y>0 && counts[x+1][y-1] != MINE && buttons[x+1][y-1].isEnabled()){//up right
				reveal(x+1,y-1);
			}
			if(y>0 && counts[x][y-1] != MINE && buttons[x][y-1].isEnabled()){//up
				reveal(x,y-1);
			}
			
			if(x>0 && counts[x-1][y] != MINE && buttons[x-1][y].isEnabled()){//left
				reveal(x-1,y);
			}
			if(x<G-1 && counts[x+1][y] != MINE && buttons[x+1][y].isEnabled()){//right
				reveal(x+1,y);
			}
			if(y<G-1 && counts[x][y+1] != MINE && buttons[x][y+1].isEnabled()){//down
				reveal(x,y+1);
			}
			if(x>0 && y<G-1 && counts[x-1][y+1] != MINE && buttons[x-1][y+1].isEnabled()){//down left
				reveal(x-1,y+1);
			}
			if(x<G-1 && y<G-1 && counts[x+1][y+1] != MINE && buttons[x+1][y+1].isEnabled()){ //down right
				reveal(x+1,y+1);
			}
		}
		
		else return;
	}
	
	public void checkwin(){
		boolean won = true;
		for (int x = 0; x < G; x++) {
			for (int y = 0; y < G; y++) {
				if (counts[x][y] != MINE && buttons[x][y].isEnabled()){
					won = false;
				}
			}
		}
		if(won == true){
			end = (System.currentTimeMillis()-start)/1000;
			int result = JOptionPane.showConfirmDialog(frame, "============= Congratulations!! =============\nYou have successfully marked all the safe areas\n\nYour Time is:  "+end+" seconds\nIn total you have marked: ( "+M+" ) areas as safe\n\nWant to play again?");
			
			if (result == JOptionPane.YES_OPTION){
				reset();
			}
			if (result == JOptionPane.NO_OPTION){
				frame.dispose();
			}
			if (result == JOptionPane.CANCEL_OPTION){
				new Minesweeper();
				frame.dispose();
			}
		}
	}
	
	
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(rst)){
			G = 8;
			frame2.dispose();
			intro();
		}
		else if (event.getSource().equals(rst2)){
			G = 12;
			frame2.dispose();
			intro();
		}
		else if(event.getSource().equals(rst3)){
			G = 16;
			frame2.dispose();
			intro();
		}
		else if(event.getSource().equals(rst4)){
			frame2.dispose();
		}

		if (event.getSource().equals(reset)){
			reset();
		}
		else{
			for(int x = 0; x< G; x++ ){
				for (int y = 0; y < G; y++) {
					if(event.getSource().equals(buttons[x][y])){
						if(counts[x][y] == MINE){
							lostGame(x,y);
						}
						
						else{
							if(counts[x][y] == 0){
								//buttons[x][y].setBorderPainted(true);
								//buttons[x][y].setIcon(bomb);
								//buttons[x][y].setEnabled(false);
								clearZeros(x,y);
							}
							
							else{
								M++;
								buttons[x][y].setFont(new Font("Arial", Font.BOLD, 180/G));
								buttons[x][y].setText(counts[x][y] + "");
								buttons[x][y].setEnabled(false);
								//buttons[x][y].setBackground(Color.darkGray);
								buttons[x][y].setBackground(new Color(196, 223,140));
							}
							checkwin();
						}
					}
				}
			}
		}
		
	}
}
