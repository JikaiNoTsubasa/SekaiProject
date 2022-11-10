package fr.triedge.sekai.client.controller;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{

	  private static final long serialVersionUID = 1L;

	  public static final int WIDTH = 1920;
	  public static final int HEIGHT = WIDTH * 9 / 16; 
	  public static final String TITLE = "YOUR GAMES NAME";
	  public static final int TICKSPERS = 120;
	  public static final boolean ISFRAMECAPPED = false;


	  public static JFrame frame;

	  private Thread thread;
	  private boolean running = false;

	  public int frames;
	  public int lastFrames;
	  public int ticks;

	  public Game(){
	      Dimension size = new Dimension(WIDTH, HEIGHT);
	      setPreferredSize(size);
	      setMaximumSize(size);
	      setMinimumSize(size);
	  }

	  public void render(){
	      frames++;
	      BufferStrategy bs = getBufferStrategy();
	      if (bs == null){
	          createBufferStrategy(2);
	          return;
	      }
	      Graphics g = bs.getDrawGraphics();
	      g.setColor(new Color(79,194,232));
	      g.fillRect(0, 0, getWidth(), getHeight());
	      //Call your render funtions from here
	      

	      g.setColor(Color.BLACK);
	      g.fillRect(120,70,35,90);

	      g.dispose();
	      bs.show();
	  }

	  public void tick(){
	  }

	  public synchronized void start(){
	      if(running) return;
	      running = true;
	      thread = new Thread(this, "Thread");
	      thread.start();
	  }

	  public synchronized void stop(){
	      if(!running) return;
	      running = false;
	      try {
	          System.exit(1);
	          frame.dispose();
	          thread.join();
	      } catch (InterruptedException e) {
	          e.printStackTrace();
	      }
	  }

	  public void init(){

	  }

	  public void run() {
	      init();
	      //Tick counter variable
	      long lastTime = System.nanoTime();
	      //Nanoseconds per Tick
	      double nsPerTick = 1000000000D/TICKSPERS;
	      frames = 0;
	      ticks = 0;
	      long fpsTimer = System.currentTimeMillis();
	      double delta = 0;
	      boolean shouldRender;
	      while(running){
	          shouldRender = !ISFRAMECAPPED;
	          long now = System.nanoTime();
	          delta += (now - lastTime) / nsPerTick;
	          lastTime = now;
	          //if it should tick it does this
	          while(delta >= 1 ){
	              ticks++;
	              tick();
	              delta -= 1;
	              shouldRender = true;
	          }
	          if (shouldRender){
	          render();
	          }
	          if (fpsTimer < System.currentTimeMillis() - 1000){
	              System.out.println(ticks +" ticks, "+ frames+ " frames");
	              ticks = 0;
	              lastFrames = frames;
	              frames = 0;
	              fpsTimer = System.currentTimeMillis();
	          }
	      }
	  }

	  public static void main(String[] args){
	      Game game = new Game();
	      frame = new JFrame(TITLE);
	      frame.add(game);
	      frame.pack();
	      frame.setResizable(false);
	      frame.setLocationRelativeTo(null);
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.setVisible(true);
	      game.start();
	  }

	}
