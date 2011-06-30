package ie.flax.client;

import java.util.ArrayList;
import java.util.Collections;

import org.jbox2d.common.Vec2;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;

/*
 * The drawing context is accessed by getContext() and the hud accessed by
 * getHud(). Further, the draw() methods should ask this to draw. This will
 * order the things to be drawn by colour, optimising the hell out of it.
 */
public class DrawingManager {
    private final boolean doubleBuffer;
    private final boolean colourManage;
    private String currentColour;
    private final Hud hud;
    private Canvas drawCanvas;
    private Canvas showCanvas;

    /*
     * To be honest this shouldn't be of pixelblocks, change if moving
     */
    private static ArrayList<PixelBlock> drawList = new ArrayList<PixelBlock>();

    /*
     * This constructor sets up the canvases (hud, and the two canvases for
     * buffering).
     */
    public DrawingManager(boolean doubleBuffer, boolean colourManage, int x,
            int y, int w, int h) {
        this.doubleBuffer = doubleBuffer;
        this.colourManage = colourManage;
        hud = new Hud(x, y, w, h);

        setupCanvas(w, h);
        showCanvas.setStyleName("game");
        RootPanel.get().add(showCanvas, x, y);

        if (!doubleBuffer) {
            drawCanvas = showCanvas;
        }
    }

    public void addToDrawList(PixelBlock objToAdd) {
        if (objToAdd != null) {
            drawList.add(objToAdd);
        }
        if (colourManage) {
            sortDisplayList();
        }
    }

    /*
     * Called in the game's main loop AND NOWHERE ELSE, YA HEAR
     */
    public void draw() {
        // clear all
        getContext().clearRect(0, 0, getCanvasWidth(), getCanvasHeight());
        // go through the display list and draw them to the drawCanvas
        for (PixelBlock i : drawList) {
            if ((i.colour != currentColour) && (colourManage)) {
                currentColour = i.colour;
                drawCanvas.getContext2d().setFillStyle(currentColour);
            }
            drawBlock(drawCanvas, i);
        }

        // swap buffers
        if (doubleBuffer) {
            swapBuffers();
        }
    }

    public int getCanvasHeight() {
        return showCanvas.getCoordinateSpaceHeight();
    }

    public int getCanvasWidth() {
        return showCanvas.getCoordinateSpaceWidth();
    }

    public Hud getHud() {
        return hud;
    }

    private void drawBlock(Canvas cnv, PixelBlock i) {

        Context2d $ = cnv.getContext2d();
        $.save();
        $.translate(i.pos.x * PhysicsInvaders.PTM_RATIO, i.pos.y
                * PhysicsInvaders.PTM_RATIO);
        $.rotate(i.body.getAngle());
        $.scale(PhysicsInvaders.PTM_RATIO, PhysicsInvaders.PTM_RATIO);

        // $.beginPath();
        // $.moveTo(0, 0);
        Vec2[] vec2 = i.shape.getVertices();
        // for (int q = 0; q < i.shape.getVertexCount(); q++) {
        // $.lineTo(vec2[q].x, vec2[q].y);
        // }
        $.fillRect(vec2[0].x - (1.0 / PhysicsInvaders.PTM_RATIO), vec2[0].y
                - (1.0 / PhysicsInvaders.PTM_RATIO), vec2[2].x
                + (2.0 / PhysicsInvaders.PTM_RATIO), vec2[2].y
                + (2.0 / PhysicsInvaders.PTM_RATIO));
        // $.lineTo(vec2[0].x, vec2[0].y);
        // $.closePath();
        // $.fill();
        $.restore();
    }

    private Context2d getContext() {
        return drawCanvas.getContext2d();
    }

    private void setupCanvas(int w, int h) {
        drawCanvas = Canvas.createIfSupported();
        drawCanvas.setPixelSize(w, h);
        drawCanvas.setCoordinateSpaceWidth(w);
        drawCanvas.setCoordinateSpaceHeight(h);
        drawCanvas.addDomHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                event.preventDefault();
            }
        }, ClickEvent.getType());

        showCanvas = Canvas.createIfSupported();
        showCanvas.setPixelSize(w, h);
        showCanvas.setCoordinateSpaceWidth(w);
        showCanvas.setCoordinateSpaceHeight(h);
        showCanvas.addDomHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                event.preventDefault();
            }
        }, ClickEvent.getType());
    }

    private void sortDisplayList() {
        // sorts the display list by colour
        Collections.sort(drawList);
    }

    private void swapBuffers() {
        showCanvas.getContext2d().clearRect(0, 0, getCanvasWidth(),
                getCanvasHeight());
        showCanvas.getContext2d()
                .drawImage(drawCanvas.getCanvasElement(), 0, 0);
    }
}
