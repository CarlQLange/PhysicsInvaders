package ie.flax.client;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import com.google.gwt.canvas.dom.client.Context2d;

public class PhysicsObject implements IGameObject {
    /*
     * These hold the PIXEL VALUES. Divide them by the PTM ratio before passing
     * them to box2d ANYWHERE
     */
    Vec2 pos = new Vec2(0, 0);
    float width = 0;
    float height = 0;
    Body body;
    PolygonShape shape;
    float angle = 0.0f;

    public PhysicsObject(float x, float y, int w, int h, BodyType t) {
        pos.x = x;
        pos.y = y;
        height = h;
        width = w;

        shape = new PolygonShape();
        shape.setAsBox((width / 2) / PhysicsInvaders.PTM_RATIO, (height / 2)
                / PhysicsInvaders.PTM_RATIO);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 100;
        fd.friction = 0.9f;
        fd.restitution = 0.1f;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos.x / PhysicsInvaders.PTM_RATIO, pos.y
                / PhysicsInvaders.PTM_RATIO);
        bodyDef.type = t;

        body = PhysicsInvaders.world.createBody(bodyDef);
        body.createFixture(fd);
    }

    public void draw() {
        drawVertices(this.shape);
    }

    public void update() {
        pos = body.getPosition();
    }

    protected void drawVertices(PolygonShape shape2) {
        Context2d $ = PhysicsInvaders.ctx; // I am a lazy, lazy man.
        // $.moveTo(this.pos.x, this.pos.y);
        $.save();
        $.translate(pos.x * PhysicsInvaders.PTM_RATIO, pos.y
                * PhysicsInvaders.PTM_RATIO);
        $.rotate(body.getAngle());
        $.scale(PhysicsInvaders.PTM_RATIO, PhysicsInvaders.PTM_RATIO);

        $.beginPath();
        $.moveTo(0, 0);
        Vec2[] vec2 = shape.getVertices();
        for (int i = 0; i < shape2.getVertexCount(); i++) {
            $.lineTo(vec2[i].x, vec2[i].y);
        }
        $.lineTo(vec2[0].x, vec2[0].y);
        $.closePath();
        $.fill();
        $.restore();
    }
}
