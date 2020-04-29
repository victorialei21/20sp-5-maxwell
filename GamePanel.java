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
	public static boolean doorIsClosed = true;

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
	
	@Override
	public void mousePressed(MouseEvent e) {
		doorIsClosed = false;
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
			x = Math.random() * 400 + 100;
			y = Math.random() * 400 + 100;
			vx = .7 * 100 - 50;
			vy = .7 * 100 - 50;
		}
		else if(color.equals("red")) {
			this.color = "red";
			x = Math.random() * 400 + 100;
			y = Math.random() * 400 + 100;
			vx = .8 * 150 - 50;
			vy = .8 * 150 - 50;
		}
	}//end Ball(Sting) constructor
	
	
	public Ball( String color, boolean toTheRight )
	{
		timer = new Timer((int)(1000 * deltat), this);
		timer.restart();
		if(color.equals("blue") ) {
			this.color = "blue";
			vx = .7 * 100 - 50;
			vy = .7 * 100 - 50;
		}
		else if(color.equals("red")) {
			this.color = "red";
			vx = .8 * 125 - 50;
			vy = .8 * 125 - 50;
		}
		
		if(toTheRight==true) {
			x = Math.random() * (750-450) + 450;
			y = Math.random() * (750-150) + 150;
		}
		else if(toTheRight==false) {
			x = Math.random() * (350-25) + 25;
			y = Math.random() * (750-150) + 150;
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
		if ( y<0 ) { vy *= -1; }
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

