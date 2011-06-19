package ie.flax.client;

import org.jbox2d.dynamics.BodyType;

import com.google.gwt.canvas.dom.client.Context2d;

public class Wall extends GameObject {

    public Wall(float x, float y, int w, int h, BodyType t) {
        super(x, y, w, h, t);
    }

    @Override
    public void draw() {
        Context2d $ = PhysicsInvaders.ctx;
        $.setFillStyle("#BBAA00");
        super.draw();
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

}
