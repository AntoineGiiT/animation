package animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class GraphicsDemo extends JPanel implements ActionListener{
	
	int x = 0;
	int y = 0;
	int velocityX = 1;
	int velocityY = 1;
	Color wall = new Color(153,153,102);
	Color door = new Color(128,43,0);
	Color floor = new Color(230,115,0);
	Color stairs = new Color(51,102,204);

	Timer timer = new Timer(10,this); // 1000 est le nombre de millisecondes dans une seconde, on peut modifier 
	// pour accélrer ou ralentir le temps..
	double seconds = 0.;
	
	public GraphicsDemo() {
		timer.start();	
	}
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		this.setBackground(Color.BLACK);
		Salle grille1 = new Salle(20,20);
		Salle grille2 = new Salle(20,20);
		grille1.borderWall();
		grille2.borderWall();
		grille1.insertDoor(0, 19);
		grille2.insertDoor(19, 0);
		Salle grille = Salle.joinSallesDessous(grille1,grille2,0,19,19,0);
		int ligne = grille.getLigne();
		int colonne = grille.getColonne();
		for(int i = 0; i<colonne; i++) {
			for(int j = 0; j<ligne; j++) {
				if(grille.getCellule(j, i).getType().equals("    ")) {
					g.setColor(Color.BLACK);
				}
				if(grille.getCellule(j, i).getType().equals("void")) {
					g.setColor(floor);
				}
				if(grille.getCellule(j, i).getType().equals("door")){
					g.setColor(door);
				}
				if(grille.getCellule(j, i).getType().equals("stai")){
					g.setColor(stairs);
				}
				if(grille.getCellule(j, i).getType().equals("wall")) {
					g.setColor(wall);
				}
				g.fillRect(i*10, j*10, 10, 10);
			}
		}
		Graphics2D g2D = (Graphics2D) g;
		g2D.setColor(Color.LIGHT_GRAY);
		g2D.fillOval(x, y, 10, 10);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		x = x + velocityX;
		y = y + velocityY;
		repaint();
		
		seconds = seconds + 0.1;
		//if(evacationDone) {
		if(seconds>100.1) {
			timer.stop();
			System.out.println("L'évacuation s'est faite en " + seconds*0.1 + "secondes");
		}
	}
}
