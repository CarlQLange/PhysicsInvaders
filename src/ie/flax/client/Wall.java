package ie.flax.client;

import org.jbox2d.dynamics.BodyType;

public class Wall extends PhysicsObject {

    public Wall(float x, float y, int w, int h, BodyType t) {
        super(x, y, w, h, t);
    }

    @Override
    public void draw() {
        // blank on purpose
    }

    @Override
    public void update() {
        // blank on purpose
    }

}
