package ie.flax.client;

import org.jbox2d.dynamics.BodyType;

public class PixelBlock extends PhysicsObject {

    public PixelBlock(float x, float y, int w, int h, BodyType t) {
        super(x, y, w, h, t);
    }

    @Override
    public void draw() {
        // PhysicsInvaders.ctx.setFillStyle("#00FF00");
        super.draw();
    }

    @Override
    public void update() {
        super.update();
    }
}
