package ie.flax.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.logging.client.ConsoleLogHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PhysicsInvaders implements EntryPoint {
    public static World world;
    public static DrawingManager dm;
    public static final int PTM_RATIO = 30; // 30 pixels to every metre, change
                                            // this later on maybe.
    public static final int PTP_RATIO = 5; // 5 pixels to every "pixel"
    final float FRAMES_PER_SECOND = 100.0f;

    public static ArrayList<IGameObject> listOfObjects = new ArrayList<IGameObject>();
    public boolean endGame = false;

    private TimerCallback cb;

    @Override
    public void onModuleLoad() {

        dm = new DrawingManager(false, true, Window.getClientWidth() / 4, 0,
                Window.getClientWidth() / 2, Window.getClientHeight());

        RootPanel.get().addDomHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getCharCode() == 'q') {
                    endGame = !endGame;
                    // gameLoop();
                    gameLoopReqAnim();
                }
            }
        }, KeyPressEvent.getType());
        initGame();
        // gameLoop();

        gameLoopReqAnim();
    }

    private void gameLoop() {
        Timer timer = new Timer() {
            // float start;
            // float elapsedTime = 0;

            @Override
            public void run() {
                world.step(1.0f / FRAMES_PER_SECOND, 10, 10);
                if (update() == false) {
                    cancel();
                }
                dm.draw();
            }

        };
        timer.scheduleRepeating(1000 / (int) FRAMES_PER_SECOND);
    }

    private void gameLoopReqAnim() {
        cb = new TimerCallback() {

            @Override
            public void fire() {
                long oldTime = new Date().getTime();

                world.step(1.0f / FRAMES_PER_SECOND, 1, 1);
                if (update()) {
                    requestAnimationFrame(cb);
                }
                dm.draw();

                long newTime = new Date().getTime();
                ConsoleLogHandler f = new ConsoleLogHandler();
                f.publish(new LogRecord(Level.INFO, (newTime - oldTime) + ""));
            }
        };
        requestAnimationFrame(cb);
    }

    private void initGame() {
        initWorld();

        listOfObjects.add(new PlayerShip(300, 650));
        // listOfObjects.add(new BasicInvader(200, 50, 100, 100));

        setupInvadersDebug();

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
			// 20ms => 50fps
			$wnd.setTimeout(fn, 20);
		}
    }-*/;

    private void setupInvadersDebug() {
        int x = 100;
        int y = 50;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                BasicInvader bi = new BasicInvader(x, y);
                listOfObjects.add(bi);
                x += 90;
            }
            x = 100;
            y += 170;
        }
    }

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
        for (IGameObject i : listOfObjects) {
            i.update();
        }

        return !endGame;
    }
}
