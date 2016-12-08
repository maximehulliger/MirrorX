package ch.mh.mirrorx.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import ch.mh.mirrorx.game.GameView;

public class GameThread extends Thread {
	public final int fps = 30;
	public boolean running = true;
	public final GameView view;
	
	public GameThread(GameView view) {
        this.view = view;
	}
	
	@Override
	public void run() {
		int ticksPS = 1000 / fps;
        long startTime;
        long sleepTime;
        while (running) {
               Canvas c = null;
               startTime = System.currentTimeMillis();
               //try {
                SurfaceHolder holder = view.getHolder();

                      //c = holder.lockCanvas();
                      //synchronized (view.getHolder()) {
                          //view.draw(c);
                          view.postInvalidate();
                    	  //c.invalidate();
                      //}
               //} finally {
                      //if (c != null) {
                             //view.getHolder().unlockCanvasAndPost(c);
                      //}
               //}
               sleepTime = ticksPS - System.currentTimeMillis() + startTime;
               try {
                      if (sleepTime > 0)
                             sleep(sleepTime);
               } catch (Exception e) {}
        }
	}
	
}
