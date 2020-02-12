//The map is at (10,10) and the trap is at (11,25)

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.sound.sampled.*;
import java.util.ArrayList;
import java.awt.Polygon;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;



public class MazeRunner3D extends JPanel implements KeyListener,MouseListener
{
	JFrame frame;
	String[][] maze = new String[15][40];
	int count = 0;
	int posX = 1;
	int posY = 1;
	int x = 100, y = 100;
	int row = 0, col = 0;
	int direction = 2;
	int moveCount = 0;
	int gameOver = 0;
	Clip clip;
	ArrayList<String> walls;
	int mapRow=10;
	int mapCol=10;
	int trapRow=11;
	int trapCol=25;
	boolean ifMapFound=false;
	boolean died=false;

	boolean reg1 = false;
	boolean reg2 = false;
	boolean reg3 = false;
	boolean reg4 = false;
	
	boolean left1 = false;
	boolean left2 = false;
	boolean left3 = false;
	
	boolean right1 = false;
	boolean right2 = false;
	boolean right3 = false;

	public MazeRunner3D()
	{
		setBoard();
		frame=new JFrame();
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize.width,screenSize.height);
		try{
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("spacecadet.wav"));
			clip = AudioSystem.getClip();
			clip.open(audioStream);
		}
		catch(LineUnavailableException lue){}
		catch(UnsupportedAudioFileException uafe){}
		catch(IOException ioe){System.out.println("Hello?");}
	
		//for background music during your game
	
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	
		frame.setVisible(true);
		frame.addKeyListener(this);
		//this.addMouseListener(this);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0,0,1500,1000);

		int yPoint = 150;
		for(int i = 0; i < 3; i++){
			g.setColor(Color.WHITE);
			g.drawRect(50,yPoint+(i*50),750,50);
			g.setColor(Color.DARK_GRAY);
			g.fillRect(50,yPoint+(i*50),850,50);
		}

		yPoint = 500;
		for(int i = 0; i < 3; i++){
			g.setColor(Color.WHITE);
			g.drawRect(50,yPoint+(i*50),750,50);
			g.setColor(new Color(0,75,0));
			g.fillRect(50,yPoint+(i*50),750,50);
		}

		int xPoint = 50;
		yPoint = 200;
		int width = 150;
		int length = 400;
		for(int i = 0; i < 3; i++){
			g.setColor(Color.WHITE);
			g.fillRect(xPoint, yPoint, width, length);
			g.setColor(Color.DARK_GRAY);
			g.drawRect(xPoint, yPoint, width, length);
			xPoint+=width;
			yPoint+=50;
			width-=50;
			length-=100;
		}

		xPoint = 500;
		yPoint = 300;
		width = 50;
		length = 200;
		for(int i = 0; i < 3; i++){
			g.setColor(Color.WHITE);
			g.fillRect(xPoint, yPoint, width, length);
			g.setColor(Color.DARK_GRAY);
			g.drawRect(xPoint, yPoint, width, length);
			xPoint+=width;
			yPoint-=50;
			width+=50;
			length+=100;
		
			int[] c10X={380, 470, 470, 380};
			int[] c10Y={300, 300,500,500};
			g.setColor(Color.DARK_GRAY);
			g.fillPolygon(c10X, c10Y, 4);
		}
		if(ifMapFound)
		{
			g.setColor(Color.BLACK);
		for(int x = 0; x < maze.length; x++)
		{
			for(int y = 0; y < maze[0].length; y++)
			{
				if((maze[x][y]).equals("#"))
				{
					g.setColor(Color.DARK_GRAY);
					g.drawRect(row,col,50,50);
					g.fillRect(row,col,50,50);
				}
				else
				{
					g.setColor(Color.WHITE);
					g.drawRect(row,col,50,50);
					g.fillRect(row,col,50,50);
				}
				if((maze[x][y]).equals("*"))
				{
					g.setColor(Color.CYAN);
					if(posX==11 && posY==25){
						died=true;
					}
					if(died==true){
						g.setColor(Color.RED);
					}
					g.drawRect(row,col,50,50);
					g.fillRect(row,col,50,50);

					if(direction==0)
					{
						g.drawRect(row,col+3,3,3);
						g.fillRect(row,col+3,3,3);
					}
					if(direction==1)
					{
						g.drawRect(row+5,col,3,3);
						g.fillRect(row+5,col,3,3);
					}
					if(direction==2)
					{
						g.drawRect(row+7,col+4,3,3);
						g.fillRect(row+7,col+4,3,3);
					}
					if(direction==3)
					{
						g.drawRect(row+4,col+7,3,3);
						g.fillRect(row+4,col+7,3,3);
					}
				}
				row += 5;
			}
			col += 5;
			row = 0;
		}
	}

		col = 0;
		row = 0;

			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
			g.drawString("Moves: "+moveCount,300,70);

			

			//left wall
			//x,y,width,length
			int[] c1X={50,200,200,50};
			int[] c1Y={150,200,600,650};
			int[] c2X={200, 300, 300, 200};
			int[] c2Y={200, 250, 550,600};
			int[] c3X={300, 380, 380, 300};
			int[] c3Y={250, 300,500,550};
			//right wall
			//x,y,width,length
			int[] c4X={800, 650, 650, 800};
			int[] c4Y={150,200,600,650};
			int[] c5X={650, 550, 550, 650};
			int[] c5Y={200, 250, 550,600};
			int[] c6X={550, 470, 470, 550};
			int[] c6Y={250, 300,500,550};

			//facing
			int[] c7X={50,800,800,50};
			int[] c7Y={150,150,650,650};
			
			int[] c8X={200,650,650,200};
			int[] c8Y={200,200,600,600};

			int[] c9X={300,550,550,300};
			int[] c9Y={250,250,550,550};


			/*g.setColor(Color.WHITE);
			g.fillPolygon(c2X,c2Y,4);
			g.setColor(Color.BLACK);
			g.drawPolygon(c2X,c2Y,4);

			g.setColor(Color.WHITE);
			g.fillPolygon(c3X,c3Y,4);
			g.setColor(Color.BLACK);
			g.drawPolygon(c3X,c3Y,4);

			g.setColor(Color.WHITE);
			g.fillPolygon(c4X,c4Y,4);
			g.setColor(Color.BLACK);
			g.drawPolygon(c4X,c4Y,4);

			g.setColor(Color.WHITE);
			g.fillPolygon(c5X,c5Y,4);
			g.setColor(Color.BLACK);
			g.drawPolygon(c5X,c5Y,4);

			g.setColor(Color.WHITE);
			g.fillPolygon(c6X,c6Y,4);
			g.setColor(Color.BLACK);
			g.drawPolygon(c6X,c6Y,4);
			*/



			/*g.setColor(Color.WHITE);
			g.fillPolygon(c1X,c1Y,4);
			g.setColor(Color.BLACK);
			g.drawPolygon(c1X,c1Y,4);
			*/

			


			if(left1){
				g.setColor(Color.WHITE);
				g.fillPolygon(c2X,c2Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c2X,c2Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c3X,c3Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c3X,c3Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c4X,c4Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c4X,c4Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c5X,c5Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c5X,c5Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c6X,c6Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c6X,c6Y,4);
			}
			if(left2){
				g.setColor(Color.WHITE);
				g.fillPolygon(c1X,c1Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c1X,c1Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c3X,c3Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c3X,c3Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c4X,c4Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c4X,c4Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c5X,c5Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c5X,c5Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c6X,c6Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c6X,c6Y,4);
			}
			if(left3){
				g.setColor(Color.WHITE);
				g.fillPolygon(c1X,c1Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c1X,c1Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c2X,c2Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c2X,c2Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c4X,c4Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c4X,c4Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c5X,c5Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c5X,c5Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c6X,c6Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c6X,c6Y,4);
			}
			if(right1){
				g.setColor(Color.WHITE);
				g.fillPolygon(c1X,c1Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c1X,c1Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c2X,c2Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c2X,c2Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c3X,c3Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c3X,c3Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c5X,c5Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c5X,c5Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c6X,c6Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c6X,c6Y,4);
			}
			if(right2){
				g.setColor(Color.WHITE);
				g.fillPolygon(c1X,c1Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c1X,c1Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c2X,c2Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c2X,c2Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c3X,c3Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c3X,c3Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c4X,c4Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c4X,c4Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c6X,c6Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c6X,c6Y,4);
			}
			if(right3){
				g.setColor(Color.WHITE);
				g.fillPolygon(c1X,c1Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c1X,c1Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c2X,c2Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c2X,c2Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c3X,c3Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c3X,c3Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c4X,c4Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c4X,c4Y,4);
				g.setColor(Color.WHITE);
				g.fillPolygon(c5X,c5Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c5X,c5Y,4);
			}
			if(!(reg1 || left1 || left2 || left3 || right1 || right2 || right3)){
				g.setColor(Color.WHITE);
				g.fillPolygon(c1X,c1Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c1X,c1Y,4);

				g.setColor(Color.WHITE);
				g.fillPolygon(c2X,c2Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c2X,c2Y,4);

				g.setColor(Color.WHITE);
				g.fillPolygon(c3X,c3Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c3X,c3Y,4);

				g.setColor(Color.WHITE);
				g.fillPolygon(c4X,c4Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c4X,c4Y,4);

				g.setColor(Color.WHITE);
				g.fillPolygon(c5X,c5Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c5X,c5Y,4);

				g.setColor(Color.WHITE);
				g.fillPolygon(c6X,c6Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c6X,c6Y,4);
			}
			if(reg1){
				g.setColor(Color.WHITE);
				g.fillPolygon(c7X,c7Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c7X,c7Y,4);
			}
			if(reg2){

				g.setColor(Color.WHITE);
				g.fillPolygon(c8X,c8Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c8X,c8Y,4);
			}
			if(reg3){

				g.setColor(Color.WHITE);
				g.fillPolygon(c9X,c9Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c9X,c9Y,4);
			}
			if(reg4){
				g.setColor(Color.WHITE);
				g.fillPolygon(c1X,c1Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c1X,c1Y,4);

				g.setColor(Color.WHITE);
				g.fillPolygon(c2X,c2Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c2X,c2Y,4);

				g.setColor(Color.WHITE);
				g.fillPolygon(c3X,c3Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c3X,c3Y,4);

				g.setColor(Color.WHITE);
				g.fillPolygon(c4X,c4Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c4X,c4Y,4);

				g.setColor(Color.WHITE);
				g.fillPolygon(c5X,c5Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c5X,c5Y,4);

				g.setColor(Color.WHITE);
				g.fillPolygon(c6X,c6Y,4);
				g.setColor(Color.BLACK);
				g.drawPolygon(c6X,c6Y,4);
			}
			reg1 = false;
			reg2 = false;
			reg3 = false;
			reg4 = false;
			left1 = false;
			left2 = false;
			left3 = false;
			right1 = false;
			right2 = false;
			right3 = false;
			try{
				final BufferedImage image = ImageIO.read(new File("map.png"));
				g.setFont(new Font("Times New Roman", Font.PLAIN, 25));
				if(posX == mapRow && posY == mapCol){
					if(!ifMapFound){
					g.setColor((Color.GREEN).darker());
					g.drawString("PRESS M TO PICK UP", 725, 100);
					g.drawString("MAP!", 825, 125);
					g.drawImage(image, 300, 400, null);
					}
				}else{
					if(!ifMapFound){
					g.setFont(new Font("Times New Roman", Font.PLAIN, 40));
					g.setColor((Color.RED).darker());
					g.drawString("Finding the map might help...", 700, 100);
					}
				}
				if(ifMapFound){
				g.drawImage(image, 775, 50, null);
				}
				}catch(Exception e){
				e.printStackTrace();
				}
			try{
				final BufferedImage trap = ImageIO.read(new File("spiketrap.png"));
				g.setFont(new Font("Times New Roman", Font.PLAIN, 25));
				if(posX == 11 && posY == 24 && direction==2){					
					g.drawImage(trap, 350, 450,200,200,null);
				}
				if(posX == trapRow && posY == trapCol){					
					g.setColor((Color.RED).darker());
					g.drawString("You walked into a trap!", 1025, 300);
					posX=1;
					posY=1;
				}
				}catch(Exception e){
				e.printStackTrace();
				}
				System.out.println("x"+posX);
				System.out.println("y"+posY);	
			if(gameOver==1)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0,0,1500,1000);
				if(moveCount > 200)
				{
					g.setColor(Color.DARK_GRAY);
					g.drawString("Moves: "+moveCount,300,70);
					g.setColor(Color.WHITE);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
					g.drawString("Got lost huh?",350,400);
				}
				else
				{
					g.setColor(Color.DARK_GRAY);
					g.drawString("Moves: "+moveCount,300,70);
					g.setColor(Color.WHITE);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
					g.drawString("Good Work!", 350,400);
				}
			}			
	}

	public void setBoard()
	{


		File name = new File("mazeText.txt");
		int r=0;
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text;
			while( (text=input.readLine())!= null)
			{
				String[] pieces = text.split("");
				for(int a = 0; a < pieces.length; a++)
				{
					maze[count][a] = pieces[a];
				}

				count++;
			}
		}
		catch (IOException io)
		{
			System.err.println("File error");
		}

		//setWalls();
	}
	

	public void setWalls()
	{
		//when you're ready for the 3D part
		//int[] c1X={150,550,450,250};
		//int[] c1Y={50,50,100,100};
		//Ceiling ceiling1=new Polygon(c1X,c1Y,4);  //needs to be set as a global!

		int[] c1X={50,200,200,50};
		int[] c1Y={150,200,600,650};

		int[] c2X={200, 300, 300, 200};
		int[] c2Y={200, 250, 550,600};

		int[] c3X={300, 380, 380, 300};
		int[] c3Y={250, 300,500,550};

		//right wall
		//x,y,width,length
		int[] c4X={800, 650, 650, 800};
		int[] c4Y={150,200,600,650};

		int[] c5X={650, 550, 550, 650};
		int[] c5Y={200, 250, 550,600};

		int[] c6X={550, 470, 470, 550};
		int[] c6Y={250, 300,500,550};

		int[] c7X={50,800,800,50};
		int[] c7Y={150,150,650,650};

	}



	public void keyPressed(KeyEvent e)
	{
		 if (e.getKeyCode()==39)
		 {
		    direction++;
		    if(direction > 3)
		    {
				direction = direction%4;
			}
		    repaint();
		 }
		 else if (e.getKeyCode()==37)
		 {
		     direction--;
		     if(direction < 0)
		     {
				 direction = 4-((-direction)%4);
			 }
		     repaint();
	     }
	     else if (e.getKeyCode()==38)
		 {
             maze[posX][posY] = "x";

             try
             {

			 if((direction==0)&&(posX==12)&&(posY-1==38))
			 {
				gameOver = 1;
			 }
             else if((direction==0)&&(!(maze[posX][posY-1].equals("#")))&&(!(maze[posX][posY-1].equals(null))))
             {
			 	posY--;
			 	moveCount++;
			 }

			 if((direction==1)&&(posX-1==12)&&(posY==38))
			 {
			 	gameOver = 1;
			 }
			 else if((direction==1)&&(!(maze[posX-1][posY].equals("#")))&&(!(maze[posX-1][posY].equals(null))))
			 {
			 	posX--;
			 	moveCount++;
			 }

			 if((direction==2)&&(posX==12)&&(posY+1==38))
			 {
			 	gameOver = 1;
			 }
			 else if((direction==2)&&(!(maze[posX][posY+1].equals("#")))&&(!(maze[posX][posY+1].equals(null))))
			 {
			 	posY++;
			 	moveCount++;
			 }

			 if((direction==3)&&(posX+1==12)&&(posY==38))
			 {
			 	gameOver = 1;
			 }
			  if((direction==3)&&(!(maze[posX+1][posY].equals("#")))&&(!(maze[posX+1][posY].equals(null))))
			 {
			 	posX++;
			 	moveCount++;
			 }

		   	 maze[posX][posY] = "*";
		 	}
		 	catch(ArrayIndexOutOfBoundsException exception)
		 	{
				maze[posX][posY] = "*";
			}
		}
		if(direction==0) //west
		 {
		 	if((posX==1)&&(posY==-1))
		 	{
		 		gameOver = 1;
		 	}
		 	
		 	try
		 	{
		 		if(maze[posX][posY-1].equals("#"))
		 		{
		 			reg1 = true;
		 		}
		 		else if(maze[posX][posY-2].equals("#"))
		 		{
		 			reg2 = true;
		 			if(!maze[posX+1][posY-1].equals("#"))
		 			{
		 				left1 = true;
		 			}
		 			if(!maze[posX-1][posY-1].equals("#"))
		 			{
		 				right1 = true;
		 			}
		 		}
		 		else if(maze[posX][posY-3].equals("#"))
		 		{
		 			reg3 = true;
		 			if(!maze[posX+1][posY-1].equals("#"))
		 			{
		 				left1 = true;
		 			}
		 			if(!maze[posX-1][posY-1].equals("#"))
		 			{
		 				right1 = true;
		 			}
		 			if(!maze[posX+1][posY-2].equals("#"))
		 			{
		 				left2 = true;
		 			}
		 			if(!maze[posX-1][posY-2].equals("#"))
		 			{
		 				right2 = true;
		 			}
		 		}
		 		else if(maze[posX][posY-4].equals("#"))
		 		{
		 			reg4 = true;
		 			if(!maze[posX+1][posY-1].equals("#"))
		 			{
		 				left1 = true;
		 			}
		 			if(!maze[posX-1][posY-1].equals("#"))
		 			{
		 				right1 = true;
		 			}
		 			if(!maze[posX+1][posY-2].equals("#"))
		 			{
		 				left2 = true;
		 			}
		 			if(!maze[posX-1][posY-2].equals("#"))
		 			{
		 				right2 = true;
		 			}
		 			if(!maze[posX+1][posY-3].equals("#"))
		 			{
		 				left3 = true;
		 			}
		 			if(!maze[posX-1][posY-3].equals("#"))
		 			{
		 				right3 = true;
		 			}
		 		}
		 		else
		 		{
		 			if(!maze[posX+1][posY-1].equals("#"))
		 			{
		 				left1 = true;
		 			}
		 			if(!maze[posX-1][posY-1].equals("#"))
		 			{
		 				right1 = true;
		 			}
		 			if(!maze[posX+1][posY-2].equals("#"))
		 			{
		 				left2 = true;
		 			}
		 			if(!maze[posX-1][posY-2].equals("#"))
		 			{
		 				right2 = true;
		 			}
		 			if(!maze[posX+1][posY-3].equals("#"))
		 			{
		 				left3 = true;
		 			}
		 			if(!maze[posX-1][posY-3].equals("#"))
		 			{
		 				right3 = true;
		 			}
		 		}
		 	}
		 	catch(ArrayIndexOutOfBoundsException exception)
		 	{
	
			}
		 }
		 if(direction==1) //north
		 {
		 	try
		 	{
		 		if(maze[posX-1][posY].equals("#"))
		 		{
		 			reg1 = true;
		 		}
		 		else if(maze[posX-2][posY].equals("#"))
		 		{
		 			reg2 = true;
		 			if(!maze[posX-1][posY-1].equals("#"))
		 			{
		 				left1 = true;
		 			}
		 			if(!maze[posX-1][posY+1].equals("#"))
		 			{
		 				right1 = true;
		 			}
		 		}
		 		else if(maze[posX-3][posY].equals("#"))
		 		{
		 			reg3 = true;
		 			if(!maze[posX-1][posY-1].equals("#"))
		 			{
		 				left1 = true;
		 			}
		 			if(!maze[posX-1][posY+1].equals("#"))
		 			{
		 				right1 = true;
		 			}
		 			if(!maze[posX-2][posY-1].equals("#"))
		 			{
		 				left2 = true;
		 			}
		 			if(!maze[posX-2][posY+1].equals("#"))
		 			{
		 				right2 = true;
		 			}
		 		}
		 		else if(maze[posX-4][posY].equals("#"))
		 		{
		 			reg4 = true;
		 			if(!maze[posX-1][posY-1].equals("#"))
		 			{
		 				left1 = true;
		 			}
		 			if(!maze[posX-1][posY+1].equals("#"))
		 			{
		 				right1 = true;
		 			}
		 			if(!maze[posX-2][posY-1].equals("#"))
		 			{
		 				left2 = true;
		 			}
		 			if(!maze[posX-2][posY+1].equals("#"))
		 			{
		 				right2 = true;
		 			}
		 			if(!maze[posX-3][posY-1].equals("#"))
		 			{
		 				left3 = true;
		 			}
		 			if(!maze[posX-3][posY+1].equals("#"))
		 			{
		 				right3 = true;
		 			}
		 		}
		 		else
		 		{
		 			if(!maze[posX-1][posY-1].equals("#"))
		 			{
		 				left1 = true;
		 			}
		 			if(!maze[posX-1][posY+1].equals("#"))
		 			{
		 				right1 = true;
		 			}
		 			if(!maze[posX-2][posY-1].equals("#"))
		 			{
		 				left2 = true;
		 			}
		 			if(!maze[posX-2][posY+1].equals("#"))
		 			{
		 				right2 = true;
		 			}
		 			if(!maze[posX-3][posY-1].equals("#"))
		 			{
		 				left3 = true;
		 			}
		 			if(!maze[posX-3][posY+1].equals("#"))
		 			{
		 				right3 = true;
		 			}
		 		}
		 	}
		 	catch(ArrayIndexOutOfBoundsException exception)
		 	{
	
			}
		 }
		 if(direction==2) //east
		 {
		 	try
		 	{
		 		if(maze[posX][posY+1].equals("#"))
		 		{
		 			reg1 = true;
		 		}
		 		else if(maze[posX][posY+2].equals("#"))
		 		{
		 			reg2 = true;
		 			if(!maze[posX-1][posY+1].equals("#"))
		 			{
		 				left1 = true;
		 			}
		 			if(!maze[posX+1][posY+1].equals("#"))
		 			{
		 				right1 = true;
		 			}
		 		}
		 		else if(maze[posX][posY+3].equals("#"))
		 		{
		 			reg3 = true;
		 			if(!maze[posX-1][posY+1].equals("#"))
		 			{
		 				left1 = true;
		 			}
		 			if(!maze[posX+1][posY+1].equals("#"))
		 			{
		 				right1 = true;
		 			}
		 			if(!maze[posX-1][posY+2].equals("#"))
		 			{
		 				left2 = true;
		 			}
		 			if(!maze[posX+1][posY+2].equals("#"))
		 			{
		 				right2 = true;
		 			}
		 		}
		 		else if(maze[posX][posY+4].equals("#"))
		 		{
		 			reg4 = true;
		 			if(!maze[posX-1][posY+1].equals("#"))
		 			{
		 				left1 = true;
		 			}
		 			if(!maze[posX+1][posY+1].equals("#"))
		 			{
		 				right1 = true;
		 			}
		 			if(!maze[posX-1][posY+2].equals("#"))
		 			{
		 				left2 = true;
		 			}
		 			if(!maze[posX+1][posY+2].equals("#"))
		 			{
		 				right2 = true;
		 			}
		 			if(!maze[posX-1][posY+3].equals("#"))
		 			{
		 				left3 = true;
		 			}
		 			if(!maze[posX+1][posY+3].equals("#"))
		 			{
		 				right3 = true;
		 			}
		 		}
		 		else
		 		{
		 			if(!maze[posX-1][posY+1].equals("#"))
		 			{
		 				left1 = true;
		 			}
		 			if(!maze[posX+1][posY+1].equals("#"))
		 			{
		 				right1 = true;
		 			}
		 			if(!maze[posX-1][posY+2].equals("#"))
		 			{
		 				left2 = true;
		 			}
		 			if(!maze[posX+1][posY+2].equals("#"))
		 			{
		 				right2 = true;
		 			}
		 			if(!maze[posX-1][posY+3].equals("#"))
		 			{
		 				left3 = true;
		 			}
		 			if(!maze[posX+1][posY+3].equals("#"))
		 			{
		 				right3 = true;
		 			}
		 		}
		 	}
		 	catch(ArrayIndexOutOfBoundsException exception)
		 	{
	
			}
		 }
		 if(direction==3) //south
		 {
		 	try
		 	{
		 		if(maze[posX+1][posY].equals("#"))
		 		{
		 			reg1 = true;
		 		}
		 		else if(maze[posX+2][posY].equals("#"))
		 		{
		 			reg2 = true;
		 			if(!maze[posX+1][posY+1].equals("#"))
		 			{
		 				left1 = true;
		 			}
		 			if(!maze[posX+1][posY-1].equals("#"))
		 			{
		 				right1 = true;
		 			}
		 		}
		 		else if(maze[posX+3][posY].equals("#"))
		 		{
		 			reg3 = true;
		 			if(!maze[posX+1][posY+1].equals("#"))
		 			{
		 				left1 = true;
		 			}
		 			if(!maze[posX+1][posY-1].equals("#"))
		 			{
		 				right1 = true;
		 			}
		 			if(!maze[posX+2][posY+1].equals("#"))
		 			{
		 				left2 = true;
		 			}
		 			if(!maze[posX+2][posY-1].equals("#"))
		 			{
		 				right2 = true;
		 			}
		 		}
		 		else if(maze[posX+4][posY].equals("#"))
		 		{
		 			reg4 = true;
		 			if(!maze[posX+1][posY+1].equals("#"))
		 			{
		 				left1 = true;
		 			}
		 			if(!maze[posX+1][posY-1].equals("#"))
		 			{
		 				right1 = true;
		 			}
		 			if(!maze[posX+2][posY+1].equals("#"))
		 			{
		 				left2 = true;
		 			}
		 			if(!maze[posX+2][posY-1].equals("#"))
		 			{
		 				right2 = true;
		 			}
		 			if(!maze[posX+3][posY+1].equals("#"))
		 			{
		 				left3 = true;
		 			}
		 			if(!maze[posX+3][posY-1].equals("#"))
		 			{
		 				right3 = true;
		 			}
		 		}
		 		else
		 		{
		 			if(!maze[posX+1][posY+1].equals("#"))
		 			{
		 				left1 = true;
		 			}
		 			if(!maze[posX+1][posY-1].equals("#"))
		 			{
		 				right1 = true;
		 			}
		 			if(!maze[posX+2][posY+1].equals("#"))
		 			{
		 				left2 = true;
		 			}
		 			if(!maze[posX+2][posY-1].equals("#"))
		 			{
		 				right2 = true;
		 			}
		 			if(!maze[posX+3][posY+1].equals("#"))
		 			{
		 				left3 = true;
		 			}
		 			if(!maze[posX+3][posY-1].equals("#"))
		 			{
		 				right3 = true;
		 			}
		 		}
		 	}
		 	catch(ArrayIndexOutOfBoundsException exception)
		 	{
	
			}
		 }
		 if(e.getKeyCode() == 77 && posX == mapRow && posY == mapCol)
		 {
			 ifMapFound=true;
		 }


		     repaint();
		 
		
	}
	public void keyReleased(KeyEvent e)
	{
	}
	public void keyTyped(KeyEvent e)
	{
	}
	public void mouseClicked(MouseEvent e)
	{
	}
	public void mousePressed(MouseEvent e)
	{
	}
	public void mouseReleased(MouseEvent e)
	{
	}
	public void mouseEntered(MouseEvent e)
	{
	}
	public void mouseExited(MouseEvent e)
	{
	}
	public static void main(String args[])
	{
		MazeRunner3D app=new MazeRunner3D();
	}
}