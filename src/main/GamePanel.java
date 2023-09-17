package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	int FPS = 60;
	
	Thread gameThread;
	PlayManager pm;
	public static Sound se = new Sound();
	
	public GamePanel() {
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.black);
		setLayout(null);
		
		addKeyListener(new KeyHandler());
		setFocusable(true);
		
		pm = new PlayManager();
	}
	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while (gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if (delta >= 1) {
				repaint();
				update();
				delta--;
			}
		}
	}
	public void update() {
		
		if (KeyHandler.pause == false && pm.gameOver == false) {
			pm.update();
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		pm.draw(g2);
		
		g2.dispose();
	}
}
