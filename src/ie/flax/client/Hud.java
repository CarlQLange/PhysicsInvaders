package ie.flax.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
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
		
		hud.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				event.preventDefault();
			}
		});
		
		hud.addMouseDownHandler(new MouseDownHandler() {
			
			@Override
			public void onMouseDown(MouseDownEvent event) {
				event.preventDefault();
			}
		});
	}

	public void drawString(String s, int x, int y) {
		ctx.clearRect(x - 1, y + 5, ctx.measureText(s).getWidth()+10, -100);
		ctx.strokeText(s, x, y);
	}
}
