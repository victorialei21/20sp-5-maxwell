import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MenuPanel extends JPanel implements ActionListener {
	
	JButton reset = new JButton("Reset Game");
	JButton moreBalls = new JButton("Add More Particles");
	
	public MenuPanel() {
		setBackground(Color.BLUE);
		Dimension size = getPreferredSize();
		size.height = 100;
		setPreferredSize(size);
		
		setBorder(BorderFactory.createTitledBorder("Menu"));
		
		add(reset);
		add(moreBalls);
		
		reset.addActionListener(this);
		moreBalls.addActionListener(this);
		
	}//end MenuPanel() constructor

	@Override
	public void actionPerformed(ActionEvent e) {
		if ( e.getSource()==reset ) { 
			JOptionPane.showConfirmDialog(null, "This game will reset", "Game Message", JOptionPane.OK_CANCEL_OPTION);
			resetGame();
	    }
		else if ( e.getSource()==moreBalls) {
		
		}
	}//end actionPerformed()
	
	
	public void resetGame() {
		
	}//end resetGame()
}
