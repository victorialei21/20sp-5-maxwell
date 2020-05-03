import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	Timer timer;
	static int ballCount;
	static Vector<Ball> ballList = new Vector<Ball>(); 
	public static boolean doorIsClosed = true;
	double deltat = 0.1;
	
	public GamePanel() {
		
		//start timer for animation
		timer = new Timer((int)(100 * deltat), this);
		timer.start();
		
		//add mouse listener for door handling
		addMouseListener(this);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//move balls every 0.01 seconds for animation
		if ( e.getSource()==timer ) { moveAll(); }
		repaint();
	}
	
	@Override
	public void paint( Graphics g )
	{
		super.paint(g);
		
		//background
		g.setColor( Color.WHITE );
		int w = getWidth();  
		int h = getHeight();
		g.fillRect( 0, 0, w, h );  // with a big rectangle
		
		//wall
		g.setColor(Color.BLACK);
		g.drawLine(w/2, 45, w/2, 250);
		g.drawLine(w/2, 400, w/2, h);
		
		//temperature displays
		g.drawRect(0, 0, 384, 25);
		g.drawRect(416, 0, 400, 25);
		g.drawString("Temperature: ", 5, 40);
		g.drawString("Temperature: ", 420, 40);
		g.drawLine(0, 45, 800, 45);
		g.drawLine(384, 25, 384, 45);
		g.drawLine(416, 25, 416, 45);
		
		drawTemperature(g);
		
		//door
		if( doorIsClosed==true ) {
			g.setColor(Color.MAGENTA);
			g.drawLine(w/2, 250, w/2, 400);
		}
		
		//balls
		for ( int i=0; i<ballCount; i++ ) { 
			if(ballList.elementAt(i).color.equals("blue")) {ballList.elementAt(i).drawBlue(g); }
			if(ballList.elementAt(i).color.equals("red")) {ballList.elementAt(i).drawRed(g); }
		}

	}
	
	public void moveAll() {
		//moves each individual ball
		for ( int i=0; i<ballCount; i++ ) { ballList.elementAt(i).move(deltat); }
	}
	
	public void drawTemperature( Graphics g ) {
		double tempx;
		int leftBalls = 0, rightBalls = 0;
		int tempLeft = 0, tempRight = 0;
		int blueLeftBalls = 0, redLeftBalls = 0;
		int blueRightBalls = 0, redRightBalls = 0;

		
		for ( int i=0; i<ballCount; i++ ) { 
			//x coordinate of the current ball
			tempx = ballList.elementAt(i).x;
			
			//if in the left chamber
			if(tempx < 400) {
				leftBalls++;
				if(ballList.elementAt(i).color == "blue") {
					blueLeftBalls++;
				}
				else if(ballList.elementAt(i).color == "red") {
					redLeftBalls++;
				}
				//according to invented temperature scale of degrees Maximus
				tempLeft = (12 * blueLeftBalls + 96 * redLeftBalls )/leftBalls;
				g.setColor(Color.ORANGE);
				//scale the temperature times four for better visuals that fills the top bar
				g.fillRect(0, 0, tempLeft*4, 25);
			}
			else if(tempx > 400) {
				rightBalls++;
				if(ballList.elementAt(i).color == "blue") {
					blueRightBalls++;
				}
				else if(ballList.elementAt(i).color == "red") {
					redRightBalls++;
				}
				//according to invented temperature scale of degrees Maximus
				tempRight = (12 * blueRightBalls + 96 * redRightBalls )/rightBalls;
				g.setColor(Color.ORANGE);
				//scale the temperature times four 
				g.fillRect(416, 0, tempRight*4, 25);
			}
		}
		g.setColor(Color.BLACK);
		g.drawString(Integer.toString(tempLeft) + " degrees Maximus", 95, 40);
		g.drawString(Integer.toString(tempRight)+ " degrees Maximus", 510, 40);

		if(redRightBalls == rightBalls) {
			System.out.println("ah!");
		}
		
	}//end drawTemperature()
	
	@Override
	public void mousePressed(MouseEvent e) {
		//if mouse is being pressed, the door is open
		doorIsClosed = false;

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//if the mouse is released, the door closes 
		doorIsClosed = true;		
	}

	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}

}

class Ball extends JComponent {

	private static final long serialVersionUID = 1L;
	Timer timer;
	int ballCount;
	double deltat = 0.1;
	
	double x, y; //x and y position
	double vx, vy; 
	double oldx, oldy;
	
	String color;
	
	public Ball( String color, boolean toTheRight )
	{
		//sets velocity of blue balls
		if(color.equals("blue") ) {
			this.color = "blue";
			vx = 20;
			vy = 20;
		}
		//sets velocity of red balls
		else if(color.equals("red")) {
			this.color = "red";
			vx = 30;
			vy = 30;
		}
		
		//randomly sets balls in right chamber
		if(toTheRight==true) {
			x = Math.random() * (750-425) + 450;
			y = Math.random() * (600-100) + 100;
		}
		//randomly sets ball in left chamber
		else if(toTheRight==false) {
			x = Math.random() * (350-25) + 25;
			y = Math.random() * (600-100) + 100;
		}
	}//end Balls(String, boolean) constructor
	
	public void move( double deltat ) {
		
		oldx = x; 
		oldy = y;
		x += vx * deltat;
		y += vy * deltat;
		int intX = (int) x;
		int intY = (int) y;
		
		//if a ball hits upper portion of the wall
		if(new Rectangle(intX, intY, 8, 8).intersects(new Rectangle(400, 0, 1, 250))) {
			vx *= -1;
			vy *= -1;
		}
		//if a ball hits the lower portion of the wall
		else if(new Rectangle(intX, intY, 8, 8).intersects(new Rectangle(400, 400, 1, 400))) {
			
			vx *= -1;				
			vy *= -1;
			
		}
		//if a ball hits the door when it is closed
		else if(new Rectangle(intX, intY, 8, 8).intersects(new Rectangle(400, 250, 1, 150))) {
			if(GamePanel.doorIsClosed) {
				vx *= -1;
				vy *= -1;
			}
		}
		
		//if a ball hits any of the outer four falls
		if ( x<0 ) { vx *= -1; }
		if ( y<48 ) { vy *= -1; }
		if ( x>794 ) { vx *= -1; }
		if ( y>670 ) { vy *= -1; }
		
	}//end move()
	
	public void drawBlue( Graphics g ) {	
		g.setColor( Color.BLUE );
		g.fillOval( (int)(x-2), (int)(y-2), 8, 8 );
	}
	
	public void drawRed( Graphics g ) {
		g.setColor( Color.RED );
		g.fillOval( (int)(x-2), (int)(y-2), 8, 8 );
	}

}//end Ball class

