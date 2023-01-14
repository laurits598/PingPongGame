package PingPong;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import org.jspace.FormalField;
import org.jspace.RemoteSpace;

public class Panel extends JPanel implements Runnable {  
	
	static final int GAME_WIDTH = 1000;
	static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int PADDLE_WIDTH = 25;
	static final int PADDLE_HEIGHT = 100;

	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;
	boolean isPlayerOne;
	private RemoteSpace playerOneMovement;
	private RemoteSpace playerTwoMovement;
	private RemoteSpace ballMovemet;

	Panel(boolean isPlayerOne, RemoteSpace playerOneMovement, RemoteSpace playerTwoMovement, RemoteSpace ballMovement){
		newPaddles();
		this.isPlayerOne = isPlayerOne;
		this.ballMovemet = ballMovement;
		newBall();
		score = new Score(GAME_WIDTH,GAME_HEIGHT);
		this.playerOneMovement = playerOneMovement;
		this.playerTwoMovement = playerTwoMovement;
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(SCREEN_SIZE);
		gameThread = new Thread(this);
		gameThread.start();
		OpponentMoved opponentMoved = new OpponentMoved();
		Thread opponentMovedThread = new Thread(opponentMoved);
		opponentMovedThread.start();
		
		//Player one calculate the ball movement so player 2 has to get it from the remote repo.
		if(!isPlayerOne) {
			BallMoved ballMoved = new BallMoved();
			Thread ballMovedThread = new Thread(ballMoved);
			ballMovedThread.start();
		}
	}

	public void newBall() {
		random = new Random();
		ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER,ballMovemet,isPlayerOne);
	}

	public void newPaddles() {
		paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
		paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);
	}

	public void paint(Graphics g) {
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,0,0,this);
	}

	public void draw(Graphics g) {
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
Toolkit.getDefaultToolkit().sync(); // Improve animation


	}

	public void move() {
		paddle2.move();
		paddle1.move();
		ball.move();
	}

	public void checkCollision() {
		//bounce ball off top & bottom window edges

		if(ball.y <=0) {
			ball.setYDirection(-ball.yVelocity);
		}

		if(ball.y >= GAME_HEIGHT-BALL_DIAMETER) {
			ball.setYDirection(-ball.yVelocity);
		}
		//bounce ball off paddles

		if(ball.intersects(paddle1)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; //optional for more difficulty
			//if(ball.yVelocity>0)
				//ball.yVelocity++; //optional for more difficulty

			//else
				//ball.yVelocity--;
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}

		if(ball.intersects(paddle2)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; //optional for more difficulty

			//if(ball.yVelocity>0)
				//ball.yVelocity++; //optional for more difficulty

			//else
				//ball.yVelocity--;
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}

		//stops paddles at window edges

		if(paddle1.y<=0)
			paddle1.y=0;

		if(paddle1.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
			paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;

		if(paddle2.y<=0)
			paddle2.y=0;

		if(paddle2.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
			paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;

		//give a player 1 point and creates new paddles & ball
		if(ball.x <=0) {
			score.player2++;
			newPaddles();
			newBall();
			//System.out.println("Player 2: "+score.player2);

		}

		if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {
			score.player1++;
			newPaddles();
			newBall();
			//System.out.println("Player 1: "+score.player1);
		}

	}

	public void run() {

		//game loop
		long lastTime = System.nanoTime();
		double amountOfTicks =60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;

		while(true) {
			long now = System.nanoTime();
			delta += (now -lastTime)/ns;
			lastTime = now;

			if(delta >=1) {
				move();
				checkCollision();
				repaint();
				delta--;
			}

		}

	}

	public class AL extends KeyAdapter{
		boolean keyReleased = true;
		public void keyPressed(KeyEvent e) {
			try {
				
				if(isPlayerOne && (e.getKeyCode()==40 || e.getKeyCode()==38)) {
					paddle2.keyPressed(e);
					if(keyReleased) {
						playerOneMovement.put(paddle2.y,e.getKeyCode(),1);
						//System.out.println("playerOnePut");
						keyReleased = false;
					}
				}else if(!isPlayerOne &&  (e.getKeyCode()==87 || e.getKeyCode()==83)){
					paddle1.keyPressed(e);
					if(keyReleased) {
						playerTwoMovement.put(paddle1.y,e.getKeyCode(),1);
						//System.out.println("playerTwoPut");
						keyReleased = false;
					}
				}
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		public void keyReleased(KeyEvent e) {
			try {
				if(isPlayerOne && (e.getKeyCode()==40 || e.getKeyCode()==38)) {
					paddle2.keyReleased(e);
					playerOneMovement.put(paddle2.y,e.getKeyCode(),0);
					//System.out.println("playerOnePut");
					keyReleased = true;
				}else if(!isPlayerOne &&  (e.getKeyCode()==87 || e.getKeyCode()==83)){
					paddle1.keyReleased(e);
					playerTwoMovement.put(paddle1.y,e.getKeyCode(),0);
					//System.out.println("playerTwoPut");
					keyReleased = true;
				}
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
	
	public class OpponentMoved implements Runnable{
		@Override
		public void run() {
			try {
				while(true) {
					if(isPlayerOne) {
						Object[] t = playerTwoMovement.get(new FormalField(Integer.class), new FormalField(Integer.class), new FormalField(Integer.class));
						if((int)t[2]==1) {
							if((int)t[1]==38) {
								paddle1.y = (int)t[0];
								paddle1.setYDirection(-paddle1.speed);
							}else if((int)t[1]==40) {
								paddle1.y = (int)t[0];
								paddle1.setYDirection(paddle1.speed);
							}
						}else if((int)t[2]==0){
							paddle1.setYDirection(0);
							paddle1.y = (int)t[0];
						}
					}else {
						Object[] t1 = playerOneMovement.get(new FormalField(Integer.class), new FormalField(Integer.class), new FormalField(Integer.class));
						if((int)t1[2]==1) {
							if((int)t1[1]==87) {
								paddle2.y = (int)t1[0];
								paddle2.setYDirection(-paddle2.speed);
							}else if((int)t1[1]==83) {
								paddle2.y = (int)t1[0];
								paddle2.setYDirection(paddle2.speed);
							}
						}else if((int)t1[2]==0){
							paddle2.setYDirection(0);
							paddle2.y = (int)t1[0];
						}
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public class BallMoved implements Runnable {

		@Override
		public void run() {
			while(true) {
				try {
					Object[] t = ballMovemet.get(new FormalField(String.class), new FormalField(Integer.class), new FormalField(Integer.class), new FormalField(Integer.class));
					if(((String)t[0]).equalsIgnoreCase("xv")) {
						ball.setLocation((int)t[2], (int)t[3]);
						ball.setXDirectionRemote((int)t[1]);
					}else if(((String)t[0]).equalsIgnoreCase("yv")) {
						ball.setLocation((int)t[2], (int)t[3]);
						ball.setYDirectionRemote((int)t[1]);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}

}  



