import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;



public class Game2048 extends JPanel {
	
	private static final long serialVersionUID = 1L;
	static int N=4;
	static int a[][] = {{0,0,0,0},
		 				{0,0,0,0},
		 				{0,0,0,0},
		 				{0,0,0,0}};
	
	static int flag=1;
	static int previous[][] =new int[N][N];
	static long previousScore;
	static long score;
	boolean won=true;
	boolean lose=true;
	public Game2048(){
		score = 0;
		setFocusable(true);
	    addKeyListener(new KeyAdapter() {
	      @Override
	      public void keyPressed(KeyEvent e) {
	        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
	          System.exit(0);
	        }
	        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            	setPreviousState();
              leftClick();
              break;
            case KeyEvent.VK_RIGHT:
            	setPreviousState();
             rigthClick();
              break;
            case KeyEvent.VK_DOWN:
            	setPreviousState();
              downClick();
              break;
            case KeyEvent.VK_UP:
              setPreviousState();
              upClick();
              break;
            case KeyEvent.VK_BACK_SPACE:
            	printPreviousState();
            	break;
          }
	        repaint();
	      }
	    });	
	}
	@Override
	  public void paint(Graphics g) {
	    super.paint(g);
	    g.setColor(new Color(0xbbada0));
	    g.fillRect(0, 0, this.getSize().width, this.getSize().height);
	    int x=20,y=20;
	   for(int i=0;i<N;i++){
		   for(int j=0;j<N;j++){
			   drawRectangle(g,x,y,a[i][j]);
			   x = x+110;
		   }
		   y = y+110;
		   x=20;
	   }
	   printScore(g, x+100, y+50);
	}
	//***********************strore Previous state***************************************************
	void setPreviousState(){
		for(int i=0;i<N;i++)
			for(int j=0;j<N;j++)
				previous[i][j] = a[i][j];
		previousScore = score;
	}
	void printPreviousState(){
		for(int i=0;i<N;i++)
			for(int j=0;j<N;j++)
				a[i][j] = previous[i][j];
		score = previousScore;
	}
	
	//*********************************************************************Creating tiles***************
	 void drawRectangle(Graphics g1,int x,int y,int value){
		int width = 90,height=90,curve=25;
		Graphics2D g = ((Graphics2D) g1);
	    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
	    g.setColor(Tile.getBackground(value));
	    g.fillRoundRect(x,y,width,height,curve,curve);
	    g.setColor(Tile.getForeground(value));
	    
	    final int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
	    final Font font = new Font("Arial", Font.BOLD, size);
	    g.setFont(font);

	    String s = String.valueOf(value);
	    final FontMetrics fm = getFontMetrics(font);
	    final int w = fm.stringWidth(s);
	    final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

	    if (value != 0)
	      g.drawString(s, x + (width - w) / 2, y + width - (width - h) / 2 - 2);
		
	}
	//*************************************************************Showing Score*********************
	 void printScore(Graphics g1,int x,int y){
		 Graphics2D g = (Graphics2D) g1;
		 g.setBackground(Color.BLACK);
		 g.setColor(Color.DARK_GRAY);
		 String s = "Score is:"+String.valueOf(score);
		   if(isWon() && won==true){
			   final Font font = new Font("Arial", Font.BOLD, 30);
			   g.setFont(font);
			   g.drawString("You Won! still you can continue :)", 10, y);
			   won=false;
		   }
		   else if(isLose() && lose==true){
			   final Font font = new Font("Arial", Font.BOLD, 30);
			   g.setFont(font);
			   g.drawString("You Lost the game! Press any", 50, y);
			   g.drawString(" cursor key to contiue :(", 100, y+40);
			   for(int i=0;i<N;i++)
				   for(int j=0;j<N;j++)
					   a[i][j]=previous[i][j]=0;
			   score=previousScore=0;
			   randomGenrator();
			   flag=1;
		   }
		   else{
			    Font font = new Font("Arial", Font.BOLD, 50);
			    g.setFont(font);
			    g.drawString(s, x, y);
			    font = new Font("Arial", Font.BOLD, 15);
			    if(flag==1){
			    g.setFont(font);
			    g.drawString("Backspace for last state", x+150, y+20);
			    g.drawString("Esc for exit", x+150, y+40);
			    g.drawString("Else Arrow Keys", x+150, y+60);
			    flag=0;
			    }
		   }
	 }
	 
	 boolean isWon(){
		 for(int i=0;i<N;i++)
			 for(int j=0;j<N;j++)
				 if(a[i][j]==2048)
					 return true;
		 
		 return false;
	 }
	 boolean isLose(){
		 for(int i=0;i<N;i++)
			 for(int j=1;j<N;j++)
				 if(a[i][j-1]==a[i][j] || a[i][j]==0)
					 return false;

		 for(int i=0;i<N;i++)
			 for(int j=1;j<N;j++)
				 if(a[j-1][i]==a[j][i])
					 return false;

		 return true;
	 }
	//*************************************************************RightClick***********************
	static void rigthClick(){
		ArrayList<Integer> al = new ArrayList<Integer>();
		boolean stateChange = false;
		for(int i=0;i<N;i++){
			boolean flag=false;
			for(int j=N-1;j>=0;j--){
				if(a[i][j]!=0){
					if(!al.isEmpty()){
						if(al.get(al.size()-1)==a[i][j] && flag==true){
							flag=false;
							int temp =2* al.get(al.size()-1);
							al.remove(al.size()-1);
							al.add(temp);
							score+=temp;
						}
						else{
							flag=true;
							al.add(a[i][j]);
						}
					}
					else{
						flag=true;
						al.add(a[i][j]);
					}
				}
			}
			int k=0;
			for(int j=N-1;j>=0;j--,k++){
				int temp = k<al.size()?al.get(k):0;
				if(stateChange==false && a[i][j]!=temp)
					stateChange=true;
				a[i][j] = temp;
			}
			al.clear();
		}
		if(stateChange)
			randomGenrator();
		
	}
	//*************************************************************LiftClick***********************
	static void leftClick(){
		ArrayList<Integer> al = new ArrayList<Integer>();
		boolean stateChange = false;
		for(int i=0;i<N;i++){
			boolean flag=false;
			for(int j=0;j<N;j++){
				if(a[i][j]!=0){
					if(!al.isEmpty()){
						if(al.get(al.size()-1)==a[i][j] && flag==true){
							flag=false;
							int temp =2* al.get(al.size()-1);
							al.remove(al.size()-1);
							al.add(temp);
							score+=temp;
						}
						else{
							flag=true;
							al.add(a[i][j]);
						}
					}
					else{
						flag=true;
						al.add(a[i][j]);
					}
				}
			}
			int k=0;
			for(int j=0;j<N;j++,k++){
				int temp = k<al.size()?al.get(k):0;
				if(stateChange==false && a[i][j]!=temp)
					stateChange=true;
				a[i][j] = temp;
			}
			al.clear();
		}
		if(stateChange)
			randomGenrator();
	}
	//*************************************************************UpClick***********************
	static void upClick(){
		ArrayList<Integer> al = new ArrayList<Integer>();
		boolean stateChange = false;
		for(int i=0;i<N;i++){
			boolean flag=false;
			for(int j=0;j<N;j++){
				if(a[j][i]!=0){
					if(!al.isEmpty()){
						if(al.get(al.size()-1)==a[j][i] && flag==true){
							flag=false;
							int temp =2* al.get(al.size()-1);
							al.remove(al.size()-1);
							al.add(temp);
							score+=temp;
						}
						else{
							flag=true;
							al.add(a[j][i]);
						}
					}
					else{
						flag=true;
						al.add(a[j][i]);
					}
				}
			}
			int k=0;
			for(int j=0;j<N;j++,k++){
				int temp = k<al.size()?al.get(k):0;
				if(stateChange==false && a[j][i]!=temp)
					stateChange=true;
				a[j][i] = temp;
			}
			al.clear();
		}
		if(stateChange)
			randomGenrator();
	}
	
	//*************************************************************DownClick***********************
	static void downClick(){
		ArrayList<Integer> al = new ArrayList<Integer>();
		boolean stateChange = false;
		for(int i=0;i<N;i++){
			boolean flag=false;
			for(int j=N-1;j>=0;j--){
				if(a[j][i]!=0){
					if(!al.isEmpty()){
						if(al.get(al.size()-1)==a[j][i] && flag==true){
							flag=false;
							int temp =2* al.get(al.size()-1);
							al.remove(al.size()-1);
							al.add(temp);
							score+=temp;
						}
						else{
							flag=true;
							al.add(a[j][i]);
						}
					}
					else{
						flag=true;
						al.add(a[j][i]);
					}
				}
			}
			int k=0;
			for(int j=N-1;j>=0;j--,k++){
				int temp = k<al.size()?al.get(k):0;
				if(stateChange==false && a[j][i]!=temp)
					stateChange=true;
				a[j][i] = temp;
				}
			al.clear();
		}
		if(stateChange)
			randomGenrator();
	}
	
	//*************************************************************RandomGenrate***********************
	static void randomGenrator(){
		int temp = Math.random()>=0.9?4:2;
		System.out.println();
		boolean locationFound = false;
        while(!locationFound) {
            int x = new Random().nextInt(4);
            int y = new Random().nextInt(4);
            if (a[x][y]==0) {
                a[x][y] = temp;
                locationFound = true;
            }
        }
	}
//************************************************************Tile Class**********
	  static class Tile {
	    public static Color getForeground(int value) {
	      return value < 16 ? new Color(0x776e65) :  new Color(0xf9f6f2);
	    }

	    public static Color getBackground(int value) {
	      switch (value) {
	        case 2:    return new Color(0xeee4da);
	        case 4:    return new Color(0xede0c8);
	        case 8:    return new Color(0xf2b179);
	        case 16:   return new Color(0xf59563);
	        case 32:   return new Color(0xf67c5f);
	        case 64:   return new Color(0xf65e3b);
	        case 128:  return new Color(0xedcf72);
	        case 256:  return new Color(0xedcc61);
	        case 512:  return new Color(0xedc850);
	        case 1024: return new Color(0xedc53f);
	        case 2048: return new Color(0xedc22e);
	        case 4096: return new Color(0xfdc98e);
	      }
	      return new Color(0xcdc1b4);
	    }
	  }
	public static void main(String[] args) {
		JFrame game = new JFrame();
	    game.setTitle("2048 Game");
	    game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    game.setSize(500, 600);
	    game.setResizable(false);
	    game.add(new Game2048());
	    game.setLocationRelativeTo(null);
	    game.setVisible(true);
	    randomGenrator();
        randomGenrator();
	}
}
