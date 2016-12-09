package ch.mh.mirrorx.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import ch.mh.mirrorx.game.GameView;

public class GameThread extends Thread {
	public static final int fps = 30;
    public static final double deltaTime = 1./fps;
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
            startTime = System.currentTimeMillis();
            boolean over = true;
            for (Target t : view.level.targets) {
                t.update();
                if (!t.isFull())
                    over = false;
            }
            view.over = over;
            view.postInvalidate();
            sleepTime = ticksPS - System.currentTimeMillis() + startTime;
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
            } catch (Exception e) {}
        }
	}
	
}
