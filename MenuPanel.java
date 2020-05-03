import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MenuPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	JButton instructions = new JButton("How To Play");
	JButton reset = new JButton("Reset Game");
	JButton moreBalls = new JButton("Add More Particles");
	
	public MenuPanel() {
		//set background
		setBackground(Color.GRAY);
		Dimension size = getPreferredSize();
		size.height = 100;
		setPreferredSize(size);
		setBorder(BorderFactory.createTitledBorder("Menu"));
		setLayout(new FlowLayout());
		
		//add buttons
		add(moreBalls);
		add(reset);
		
		//add action listeners for buttons
		reset.addActionListener(this);
		moreBalls.addActionListener(this);
		
	}//end MenuPanel() constructor

	@Override
	public void actionPerformed(ActionEvent e) {
		//show option pane if reset is clicked to confirm choice
		if ( e.getSource()==reset ) { 
			JOptionPane.showConfirmDialog(null, "Are you sure you want to reset the game?", 
					"Game Message", JOptionPane.OK_CANCEL_OPTION);
			resetGame();
	    }
		//ad four balls if add particles is clicked
		else if ( e.getSource()==moreBalls) {
			moreBalls();
		}
		
	}//end actionPerformed()
	
	public void resetGame() {
		//clear vector of balls
		GamePanel.ballList.clear();
		
		//reset count to 0
		GamePanel.ballCount = 0;
		
	}//end resetGame()
	
	public void moreBalls() {
		
		int ballsToAdd = 4;

		//add one ball of each color to each side
		GamePanel.ballList.addElement(new Ball("blue", true));
		GamePanel.ballList.addElement(new Ball("red", true));
		GamePanel.ballList.addElement(new Ball("blue", false));			
		GamePanel.ballList.addElement(new Ball("red", false));
		
		//increment ball counter for the four balls just added
		for(int i = 0; i < ballsToAdd; i++) { GamePanel.ballCount++; }

	}//end moreBalls()
}//end MenuPanel class