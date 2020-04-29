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

	Timer timer;
	static int ballCount;
	Ball[] balls;
	static Vector<Ball> ballList = new Vector<Ball>(); 
	int middleLine, ballsToAdd = 4;
	public static boolean doorIsClosed = true, ballsExist = false;

	double deltat = 0.1;
	
	public GamePanel() {
		
		timer = new Timer((int)(1000 * deltat), this);
		timer.start();
		
		addMouseListener(this);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
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
		g.drawLine(w/2, 0, w/2, 250);
		g.drawLine(w/2, 400, w/2, h);
		
		//temperature displays
		g.drawRect(0, 0, 400, 25);
		g.drawRect(400, 0, 400, 25);
		g.drawString("BLUE Temperature: ", 5, 40);
		g.drawString("RED Temperature: ", 405, 40);
		g.drawLine(0, 45, 800, 45);
		
		if(ballsExist) {
			drawTemperature(g);
		}
		
		//door
		if( doorIsClosed==true ) {
			g.setColor(Color.GREEN);
			g.drawLine(w/2, 250, w/2, 400);
		}
		
		for ( int i=0; i<ballCount; i++ ) { 
			if(ballList.elementAt(i).color.equals("blue")) {ballList.elementAt(i).drawBlue(g); }
			if(ballList.elementAt(i).color.equals("red")) {ballList.elementAt(i).drawRed(g); }
		}

	}
	
	public void moveAll() {
		for ( int i=0; i<ballCount; i++ ) { ballList.elementAt(i).move(deltat); }
	}
	
	public void drawTemperature( Graphics g ) {
		double tempx;
		int leftBalls = 0, rightBalls = 0;
		int tempLeft = 0, tempRight = 0;

		for ( int i=0; i<ballCount; i++ ) { 
			
			tempx = ballList.elementAt(i).x;
			
			if(tempx < 400) {
				leftBalls++;
				if(ballList.elementAt(i).color == "blue") {
					tempLeft = (tempLeft + 30)/leftBalls;
				}
				else if(ballList.elementAt(i).color == "red") {
					tempLeft = (tempLeft + 70)/leftBalls;
				}
				g.setColor(Color.ORANGE);
				g.fillRect(0, 0, tempLeft, 25);
			}
			else if(tempx > 400) {
				rightBalls++;
				if(ballList.elementAt(i).color == "blue") {
					tempRight = (tempRight + 30)/rightBalls;
				}
				else if(ballList.elementAt(i).color == "red") {
					tempRight = (tempRight + 70)/rightBalls;
				}
				g.setColor(Color.ORANGE);
				g.fillRect(400, 0, tempRight, 25);
			}
		}
		g.setColor(Color.BLACK);
		g.drawString(Integer.toString(tempLeft) + " degrees Maximus", 130, 40);
		g.drawString(Integer.toString(tempRight)+ " degrees Maximus", 525, 40);

		
	}//end drawTemperature()
	
	@Override
	public void mousePressed(MouseEvent e) {
		doorIsClosed = false;
		System.out.println(e.getX() + ", " + e.getY());

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		doorIsClosed = true;		
	}

	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}

}

class Ball extends JComponent implements ActionListener {

	Timer timer;
	int ballCount;
	double deltat = 0.1;
	
	double x, y; //x and y position
	double vx, vy; 
	double oldx, oldy;
	
	String color;
	
	public Ball(String color)
	{
		if(color.equals("blue")) {
			this.color = "blue";
			x = Math.random() * (500-300) + 300;
			y = Math.random() * (500-300) + 300;
			vx = 20;
			vy = 20;
		}
		else if(color.equals("red")) {
			this.color = "red";
			x = Math.random() * (500-300) + 300;
			y = Math.random() * (500-300) + 300;
			vx = 30;
			vy = 30;
		}
	}//end Ball(Sting) constructor
	
	
	public Ball( String color, boolean toTheRight )
	{
		timer = new Timer((int)(1000 * deltat), this);
		timer.restart();
		if(color.equals("blue") ) {
			this.color = "blue";
			vx = 20;
			vy = 20;
		}
		else if(color.equals("red")) {
			this.color = "red";
			vx = 30;
			vy = 30;
		}
		
		if(toTheRight==true) {
			x = Math.random() * (750-450) + 450;
			y = Math.random() * (600-100) + 100;
		}
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
		
		if(new Rectangle(intX, intY, 8, 8).intersects(new Rectangle(400, 0, 1, 250))) {
			vx *= -1;
			vy *= -1;
		}
		else if(new Rectangle(intX, intY, 8, 8).intersects(new Rectangle(400, 400, 1, 400))) {
			
			vx *= -1;				
			vy *= -1;
			
		}
		else if(new Rectangle(intX, intY, 8, 8).intersects(new Rectangle(400, 250, 1, 150))) {
			if(GamePanel.doorIsClosed) {
				vx *= -1;
				vy *= -1;
			}
		}
		
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

	@Override
	public void actionPerformed(ActionEvent e) { }
}//end Ball class

