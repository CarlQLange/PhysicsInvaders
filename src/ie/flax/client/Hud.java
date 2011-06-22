package ie.flax.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.ui.RootPanel;

/*
 * This is an overlaid canvas, updated by event
 * May or may not turn out to be beneficial.
 */
public class Hud {
	Canvas hud;
	Context2d ctx;

	/*
	 * Probably ought to make this a singleton.
	 */
	public Hud(int x, int y, int w, int h) {
		hud = Canvas.createIfSupported();
		hud.setPixelSize(w, h);
		hud.setCoordinateSpaceWidth(w);
		hud.setCoordinateSpaceHeight(h);
		hud.setStylePrimaryName("hud");
		RootPanel.get().add(hud, x, y);
		// set a bunch of canvas and context vars
		ctx = hud.getContext2d();
	}

	public void drawString(String s, int x, int y) {
		ctx.strokeText(s, x, y);
	}
}
