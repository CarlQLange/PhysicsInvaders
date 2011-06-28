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
		ctx.setFont("20pt");
	}

	public void drawString(String s, int x, int y) {
		ctx.clearRect(x - 1, y + 5, 100, -50); // because I can't think of a way
												// to get the rendered size of
												// the string
		ctx.strokeText(s, x, y);
	}
}
