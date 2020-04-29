import javax.swing.*;
import java.awt.*;


public class MaxwellGame extends JFrame {
	

	private MenuPanel menuPanel;
	private GamePanel gamePanel;

	public static void main( String[] args )
	{
		new MaxwellGame();
	}//end main
	
	
	public MaxwellGame() {
		setTitle( "Maxwell's Demon" );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLayout(new BorderLayout());
		
		menuPanel = new MenuPanel();
		add(menuPanel, BorderLayout.NORTH);
		
		gamePanel = new GamePanel();
		add(gamePanel, BorderLayout.CENTER);
		
	    setSize( 800, 800 ); 
	    setVisible( true ); 
	    
	    int resolution = Toolkit.getDefaultToolkit().getScreenResolution();
	    System.out.println(resolution);

	}//end MaxwellGame() constructor
	
}//end class MaxwellGame