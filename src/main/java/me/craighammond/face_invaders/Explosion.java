package me.craighammond.face_invaders;

import java.awt.Color;
import java.awt.Graphics;

public class Explosion {
	private double[] x;
	private double[] y;
	private int[] s;
	private int[] vX;
	private int[] vY;
	private double existenceTime;
	private final Color[] colors = {Color.cyan, Color.red, Color.yellow};
	private final int numParticles = 12;
	private Color theColor;
	
	public Explosion() {
		x = new double[numParticles];
		y = new double[numParticles];
		s = new int[numParticles];
		vX = new int[numParticles];
		vY = new int[numParticles];
		
		for(int i = 0; i < numParticles; i++){
			x[i] = 0;
		}
		
		for(int i = 0; i < numParticles; i++){
			y[i] = 0;
		}
		
		for(int i = 0; i < numParticles; i++){
			s[i] = ((int)(Math.random()*8)+1);
		}
		
		for(int i = 0; i < numParticles; i++){
			if(i%3==0)
				vY[i] = -((int)(Math.random()*7)+5);
			
			else if(i%3==1)
				vY[i] = ((int)(Math.random()*7)+5);
			
			else
				vY[i] = 0;
		}
		
		for(int i = 0; i < numParticles; i++){
			if(i<(int)(numParticles/8.0*3))
				vX[i] = ((int)(Math.random()*7)+5);
			
			else if(i>(int)(numParticles/2.0)&&i<(int)(numParticles/8.0*7))
				vX[i] = -((int)(Math.random()*7)+5);
			
			else if(vY[i]==0)
				vX[i] = ((int)(Math.random()*7)+5);
			
			else 
				vX[i] = 0;
		}
		
		existenceTime = 0;
		
		theColor = colors[0];
	}
	
	public Explosion(int hitX, int hitY) {
		x = new double[numParticles];
		y = new double[numParticles];
		s = new int[numParticles];
		vX = new int[numParticles];
		vY = new int[numParticles];
		
		for(int i = 0; i < numParticles; i++){
			x[i] = hitX;
		}
		
		for(int i = 0; i < numParticles; i++){
			y[i] = hitY;
		}
		
		for(int i = 0; i < numParticles; i++){
			s[i] = ((int)(Math.random()*8)+1);
		}
		
		for(int i = 0; i < numParticles; i++){
			if(i%3==0)
				vY[i] = -((int)(Math.random()*7)+5);
			
			else if(i%3==1)
				vY[i] = ((int)(Math.random()*7)+5);
			
			else
				vY[i] = 0;
		}
		
		for(int i = 0; i < numParticles; i++){
			if(i<(int)(numParticles/8.0*3))
				vX[i] = ((int)(Math.random()*7)+5);
			
			else if(i>(int)(numParticles/2.0)&&i<(int)(numParticles/8.0*7))
				vX[i] = -((int)(Math.random()*7)+5);
			
			else if(vY[i]==0)
				vX[i] = ((int)(Math.random()*7)+5);
			
			else 
				vX[i] = 0;
		}
		
		existenceTime = 0;

		theColor = colors[0];
	}
	
	
	
	public void draw(Graphics g){
		g.setColor(theColor);
		for(int i = 0; i < numParticles; i ++){
			g.fillRect((int)x[i] - s[i]/2, (int)y[i] - s[i]/2, s[i], s[i]);
		}
	}

	public boolean isDone(){
		return existenceTime>=3000;
	}

	private int stepCount = 0;
	private final static int WAIT_MILLI = 5;
	public void step() {
		if (stepCount == WAIT_MILLI) {
			for(int i = 0; i < numParticles; i++){
				x[i]+=vX[i]*20/200.0;
			}

			for(int i = 0; i < numParticles; i++){
				y[i]+=vY[i]*20/200.0;
			}

			boolean noChange = false;
			int j = 0;
			while(noChange == false&&j<numParticles){
				if(colors[j].equals(theColor)){
					if(j==colors.length-1){
						theColor = colors[0];
					}
					else
						theColor = colors[j+1];

					noChange = true;
				}

				else j++;
			}

			existenceTime += 5;

			stepCount = 0;
		} else {
			stepCount++;
		}
	}
	
	
}
