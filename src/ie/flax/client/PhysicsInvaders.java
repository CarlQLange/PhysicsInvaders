package ie.flax.client;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PhysicsInvaders implements EntryPoint {
    public static Canvas canvas;
    public static Context2d ctx;
    public static World world;
    public static final int PTM_RATIO = 30; // 30 pixels to every metre, change
                                            // this later on maybe.
    public static final int PTP_RATIO = 15; // 5 pixels to every "pixel"

    final float FRAMES_PER_SECOND = 35.0f;

    public static ArrayList<IGameObject> listOfObjects = new ArrayList<IGameObject>();
    public boolean endGame = false;

    @Override
    public void onModuleLoad() {
        initCanvasAndContext(Window.getClientWidth() - 50,
                Window.getClientHeight() - 50);
        initGame();
        gameLoopTimer();
    }

    private void initGame() {
        initWorld();

        listOfObjects.add(new PlayerShip(300, 650, 90, 30));

        // listOfObjects.add(new BasicInvader(200, 50, 100, 100));

        setupInvadersDebug();

    }

    private void setupInvadersDebug() {
        int x = 100;
        int y = 50;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                BasicInvader bi = new BasicInvader(x, y);
                listOfObjects.add(bi);
                x += 170;
            }
            x = 100;
            y += 170;
        }
    }

    private void initWorld() {
        Vec2 g = new Vec2(0, 10);
        world = new World(g, true);
        setupWalls();
    }

    private void setupWalls() {
        // bottom
        listOfObjects.add(new Wall(0, canvas.getCoordinateSpaceHeight(), canvas
                .getCoordinateSpaceWidth() * 2, 1, BodyType.STATIC));
        // left
        listOfObjects.add(new Wall(0, 0, 1,
                canvas.getCoordinateSpaceHeight() * 2, BodyType.STATIC));
        // right
        listOfObjects.add(new Wall(canvas.getCoordinateSpaceWidth(), 0, 1,
                canvas.getCoordinateSpaceHeight() * 2, BodyType.STATIC));
    }

    private void gameLoopTimer() {
        Timer timer = new Timer() {
            // float start;
            // float elapsedTime = 0;

            @Override
            public void run() {
                world.step(1.0f / FRAMES_PER_SECOND, 10, 10);
                if (update() == false) this.cancel();
                draw();
            }

        };
        timer.scheduleRepeating(1000 / (int) FRAMES_PER_SECOND);
    }

    private void gameLoopReqAnimFram() {
        Animation m = new Animation() {

            @Override
            protected void onUpdate(double progress) {
                world.step((float) 1.0f / FRAMES_PER_SECOND, 10, 10);
                if (update() == false) this.cancel();
                draw();
            }
        };
        m.run(Integer.MAX_VALUE);
    }

    private void draw() {
        ctx.setFillStyle("#FFFF00");
        ctx.fillRect(0, 0, canvas.getCoordinateSpaceWidth(),
                canvas.getCoordinateSpaceHeight());
        for (IGameObject i : listOfObjects) {
            i.draw();
        }
    }

    private boolean update() {
        for (IGameObject i : listOfObjects) {
            i.update();
        }

        return !endGame;
    }

    private void initCanvasAndContext(int w, int h) {
        canvas = Canvas.createIfSupported();

        /*
         * This is necessary to prevent the _huge_ (50%) loss in framerate when
         * the canvas is focused. TODO: file a bug report either to GWT or
         * Chromium.
         */
        canvas.addMouseDownHandler(new MouseDownHandler() {

            @Override
            public void onMouseDown(MouseDownEvent event) {
                event.stopPropagation();
                event.preventDefault();
            }
        });
        canvas.setStyleName("myCanvas");
        canvas.setPixelSize(w, h);

        RootPanel.get().add(canvas);

        ctx = canvas.getContext2d();

        /*
         * For some reason, GWT's implementation of canvas decides to (at least
         * on this machine) scale everything up in the y-axis by 2. Why? I don't
         * know. I can't even find the particular bit of code that does it in
         * the GWT source. I don't know if it's consistent across machines
         * either. Not sure how much of a performance hit this has yet, but
         * hopefully I'll be able to remove it soon. In summary, what the
         * goddamn fuck, GWT?
         */
        // ctx.scale(1, 0.5);
        /*
         * Update: Yeah, you need to explicitly set the coordinate space w&h,
         * otherwise it'll give it values you presumably don't want.
         */
        canvas.setCoordinateSpaceWidth(w);
        canvas.setCoordinateSpaceHeight(h);

        RootPanel.get().addDomHandler(new KeyPressHandler() {

            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getCharCode() == 'q') {
                    endGame = !endGame;
                    gameLoopReqAnimFram();
                }
            }
        }, KeyPressEvent.getType());

    }
}
