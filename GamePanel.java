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
	int ballCount;
	Ball[] balls;
	static Vector<Ball> ballList = new Vector<Ball>(); 
	int middleLine, ballsToAdd = 4;
	public static boolean doorIsClosed = true;

	double deltat = 0.1;
	
	public GamePanel() {
		ballCount = 10;
		balls = new Ball[100000];
		
		timer = new Timer((int)(1000 * deltat), this);
		timer.start();
		
		for ( int i=0; i<ballCount; i++ ) { 
			//balls[i] = new Balls(); 
			ballList.addElement(new Ball());
		}
		
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
		g.setColor(Color.BLUE);
		g.drawLine(w/2, 250, w/2, 400);
		
		for ( int i=0; i<ballCount; i = i + 2 ) { ballList.elementAt(i).drawBlue(g); }
		for ( int i=1; i<ballCount; i = i + 2 ) { ballList.elementAt(i).drawRed(g); }

	}
	
	public void moveAll() {
		for ( int i=0; i<ballCount; i++ ) { ballList.elementAt(i).move(deltat); }
	}
	
	public void addBalls() {
		for ( int i=0; i<ballsToAdd; i++ ) { 
			ballList.addElement(new Ball());
		}
	}//end addBalls()
	
	@Override
	public void mousePressed(MouseEvent e) {
		doorIsClosed = false;
		slideDoorUp();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		slideDoorDown();
		
	}

	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}

	public void slideDoorUp() {
		
	}//end slideDoorUp()
	
	public void slideDoorDown() {
		
	}//end slideDoorDown()
}

class Ball extends JComponent implements ActionListener {

	Timer timer;
	int ballCount;
	double deltat = 0.1;
	
	double x, y; //x and y position
	double vx, vy;
	double oldx, oldy;
	
	boolean blue = false, red = false;
	
	public Ball( int x1, int y1 )
	{
		timer = new Timer((int)(1000 * deltat), this);
		timer.restart();
		x = x1; y = y1; 
		vx = Math.random() * 100 - 50;
		vy = Math.random() * 100 - 50;
	}
	
	public Ball()
	{
		x = Math.random() * 400 + 100;
		y = Math.random() * 400 + 100;
		vx = Math.random() * 100 - 50;
		vy = Math.random() * 100 - 50;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void move( double deltat ) {
		
		oldx = x; 
		oldy = y;
		x += vx * deltat;
		y += vy * deltat;
		int intX = (int) x;
		int intY = (int) y;
		
		if(new Rectangle(intX, intY, 6, 6).intersects(new Rectangle(400, 0, 1, 250))) {
			vx *= -1;
			vy *= -1;
		}
		else if(new Rectangle(intX, intY, 6, 6).intersects(new Rectangle(400, 400, 1, 400))) {
			
			vx *= -1;				
			vy *= -1;
			
		}
		else if(new Rectangle(intX, intY, 6, 6).intersects(new Rectangle(400, 250, 1, 150))) {
			if(GamePanel.doorIsClosed) {
				vx *= -1;
				vy *= -1;
			}
		}
		
		if ( x<0 ) { vx *= -1; }
		if ( y<0 ) { vy *= -1; }
		if ( x>800 ) { vx *= -1; }
		if ( y>600 ) { vy *= -1; }
		
	}//end move()

	
	public void drawBlue( Graphics g ) {	
		blue = true;
		g.setColor( Color.BLUE );
		g.fillOval( (int)(x-2), (int)(y-2), 6, 6 );
	}
	
	public void drawRed( Graphics g ) {
		red = true;
		g.setColor( Color.RED );
		g.fillOval( (int)(x-2), (int)(y-2), 6, 6 );
	}
}//end Ball class

