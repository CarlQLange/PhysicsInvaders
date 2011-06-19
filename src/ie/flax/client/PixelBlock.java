package ie.flax.client;

import org.jbox2d.dynamics.BodyType;

public class PixelBlock extends GameObject {

    public PixelBlock(float x, float y, int w, int h, BodyType t) {
        super(x, y, w, h, t);
    }

    @Override
    public void draw() {
        PhysicsInvaders.ctx.setFillStyle("#00FF00");
        super.draw();
    }

    @Override
    public void update() {
        // this.pos = body.getPosition().mul(PhysicsInvaders.PTM_RATIO);
        super.update();
    }
}
