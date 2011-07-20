package ie.flax.client;

import java.util.ArrayList;
import java.util.Date;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PhysicsInvaders implements EntryPoint {
    public static World world;
    public static DrawingManager dm;
    public static InvaderManager im;
    
    public static final int PTM_RATIO = 30; // 30 pixels to every metre, change
                                            // this later on maybe.
    public static final int PTP_RATIO = 5; // 5 pixels to every "pixel"
    public static final float FRAMES_PER_SECOND = 60.0f;

    public static ArrayList<IGameObject> listOfObjects = new ArrayList<IGameObject>();
    public boolean endGame = false;

    private TimerCallback cb;

    @Override
    public void onModuleLoad() {
        FLog.init();
        dm = new DrawingManager(true, true, Window.getClientWidth() / 4, 0,
                Window.getClientWidth() / 2, Window.getClientHeight());

        RootPanel.get().addDomHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getCharCode() == 'q') {
                    endGame = !endGame;
                    gameLoop();
                }
            }
        }, KeyPressEvent.getType());
        initGame();

        gameLoop();
    }

    
    private void gameLoop() {
    	cb = new TimerCallback() {
			double t = 0.0;
			float dt = 0.01f;
			
			double currentTime = new Date().getTime();; //lowres
			double accumulator = dt;
			
			@Override
			public void fire() {
				double newTime = new Date().getTime();
				double frameTime = newTime - currentTime;
				if (frameTime > 1.0/FRAMES_PER_SECOND) frameTime = 1.0/FRAMES_PER_SECOND;
				currentTime = newTime;
				
				accumulator+=frameTime;
				
				while (accumulator>= dt){
					world.step(dt, 8, 8);
					t+=dt;
					accumulator-=dt;
				}
				
				if (update()) requestAnimationFrame(this);
				dm.draw();
				
				
			}
		};
    	/*
        cb = new TimerCallback() {

            @Override
            public void fire() {
                long oldTime = new Date().getTime();
            	
                world.step(1.0f / FRAMES_PER_SECOND, 2, 2);
                
                if (update()) {
                    requestAnimationFrame(cb);
                }
                dm.draw();

                long newTime = new Date().getTime();
                FLog.info(newTime - oldTime + "");
            }
        };
        */
        requestAnimationFrame(cb);
    }

    private void initGame() {
        initWorld();
        PhysicsInvaders.world.drawDebugData();
        listOfObjects.add(new PlayerShip(300, 650));
        // listOfObjects.add(new BasicInvader(200, 50, 100, 100));

        im = new InvaderManager();
        
        im.update();

    }

    private void initWorld() {
        Vec2 g = new Vec2(0, 10);
        world = new World(g, true);
        world.setAutoClearForces(true);
        setupWalls();
    }

    /*
     * This code snippet taken from the ForPlay source, under Apache.
     */
    private native void requestAnimationFrame(TimerCallback callback) /*-{
		var fn = function() {
			callback.@ie.flax.client.TimerCallback::fire()();
		};
		if ($wnd.requestAnimationFrame) {
			$wnd.requestAnimationFrame(fn);
		} else if ($wnd.mozRequestAnimationFrame) {
			$wnd.mozRequestAnimationFrame(fn);
		} else if ($wnd.webkitRequestAnimationFrame) {
			$wnd.webkitRequestAnimationFrame(fn);
		} else {
			$wnd.setTimeout(fn, 1000/@ie.flax.client.PhysicsInvaders::FRAMES_PER_SECOND);
		}
    }-*/;


    private void setupWalls() {
        // bottom
        listOfObjects.add(new Wall(0, dm.getCanvasHeight(),
                dm.getCanvasWidth() * 2, 1, BodyType.STATIC));
        // left
        listOfObjects.add(new Wall(0, 0, 1, dm.getCanvasHeight() * 2,
                BodyType.STATIC));
        // right
        listOfObjects.add(new Wall(dm.getCanvasWidth(), 0, 1, dm
                .getCanvasHeight() * 2, BodyType.STATIC));
    }

    private boolean update() {
    	im.update();
    	
        for (IGameObject i : listOfObjects) {
            i.update();
        }

        return !endGame;
    }
}
