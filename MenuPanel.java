import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MenuPanel extends JPanel implements ActionListener {
	
	JButton instructions = new JButton("How To Play");
	JButton reset = new JButton("Reset Game");
	JButton moreBalls = new JButton("Add More Particles");
	JProgressBar leftTemp = new JProgressBar();
	JProgressBar rightTemp = new JProgressBar();
	
	int ballsToAdd = 4;
	
	public MenuPanel() {
		setBackground(Color.GRAY);
		Dimension size = getPreferredSize();
		size.height = 100;
		setPreferredSize(size);
		
		setBorder(BorderFactory.createTitledBorder("Menu"));
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		add(moreBalls, c);
		
		c.gridx = 4;
		c.gridy = 0;
		add(reset, c);
		
		c.gridx = 0;
		c.gridy = 2;
		add(leftTemp, c);
		
		c.gridx = 4;
		c.gridy = 2;
		add(rightTemp, c);
		
		reset.addActionListener(this);
		moreBalls.addActionListener(this);
		
	}//end MenuPanel() constructor

	@Override
	public void actionPerformed(ActionEvent e) {
		if ( e.getSource()==reset ) { 
			JOptionPane.showConfirmDialog(null, "Are you sure you want to reset the game?", 
					"Game Message", JOptionPane.OK_CANCEL_OPTION);
			resetGame();
	    }
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
			GamePanel.ballList.addElement(new Ball("blue", true));
			GamePanel.ballList.addElement(new Ball("red", true));
			GamePanel.ballList.addElement(new Ball("blue", false));
			GamePanel.ballList.addElement(new Ball("red", false));
			
			for(int i = 0; i < ballsToAdd; i++) { GamePanel.ballCount++; }
			
	}//end moreBalls()
}//end MenuPanel class