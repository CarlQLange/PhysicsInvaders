package ie.flax.client;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;

public class PlayerShip extends GameObject {
    public PlayerShip(float x, float y, int w, int h) {
        super(x, y, w, h, BodyType.STATIC);

        PhysicsInvaders.canvas.addKeyPressHandler(new KeyPressHandler() {

            @Override
            public void onKeyPress(KeyPressEvent event) {
                // FIXME This is not the right way to move this body
                // better would be giving it velocity or summat
                // this will do for now.
                float distanceToMoveInPixels = 3.0f;
                if (event.getCharCode() == 'd') {
                    body.setTransform(
                            pos.sub(new Vec2(distanceToMoveInPixels
                                    / -PhysicsInvaders.PTM_RATIO, 0)), 0);
                } else if (event.getCharCode() == 'a') {
                    body.setTransform(
                            pos.sub(new Vec2(distanceToMoveInPixels
                                    / PhysicsInvaders.PTM_RATIO, 0)), 0);
                } else if (event.getCharCode() == 'w') {
                    body.setTransform(
                            pos.sub(new Vec2(0, distanceToMoveInPixels
                                    / PhysicsInvaders.PTM_RATIO)), 0);
                } else if (event.getCharCode() == 's') {
                    body.setTransform(
                            pos.sub(new Vec2(0, distanceToMoveInPixels
                                    / -PhysicsInvaders.PTM_RATIO)), 0);
                }
            }
        });

        this.body.m_fixtureList.m_filter.groupIndex = 1;
    }

    @Override
    public void draw() {

        PhysicsInvaders.ctx.setFillStyle("#000000");
        super.draw();
    }
}
