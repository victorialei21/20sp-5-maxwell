import javax.swing.*;
import java.awt.*;


public class MaxwellGame extends JFrame {
	
	private static final long serialVersionUID = 1L;
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
		
		//add menu panel
		menuPanel = new MenuPanel();
		add(menuPanel, BorderLayout.NORTH);
		
		//add game panel
		gamePanel = new GamePanel();
		add(gamePanel, BorderLayout.CENTER);
		
		//set size of window
	    setSize( 800, 800 );
	    setResizable(false);
	    setVisible( true ); 

	}//end MaxwellGame() constructor
	
}//end class MaxwellGame