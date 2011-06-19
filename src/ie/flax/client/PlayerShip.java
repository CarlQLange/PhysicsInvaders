package ie.flax.client;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.RootPanel;

/*
 * TODO At some point you're gonna wanna change this from directly inheriting
 * PhysicsObject. However, this works for the moment. On top of that, adding
 * another like 70 pixel blocks? Probably not great from a performance point of
 * view.
 */
public class PlayerShip extends PhysicsObject {
    public PlayerShip(float x, float y, int w, int h) {
        super(x, y, w, h, BodyType.KINEMATIC);
        RootPanel.get().addDomHandler(new KeyPressHandler() {

            @Override
            public void onKeyPress(KeyPressEvent event) {
                /*
                 * FIXME: Not the correct way to move this body. Do it by
                 * applying force instead.
                 */
                if (event.getCharCode() == 'd') {
                    body.setLinearVelocity(new Vec2(3, 0));
                } else if (event.getCharCode() == 'a') {
                    body.setLinearVelocity(new Vec2(-3, 0));
                } else if (event.getCharCode() == 'w') {
                    body.setLinearVelocity(new Vec2(0, -3));
                } else if (event.getCharCode() == 's') {
                    body.setLinearVelocity(new Vec2(0, 3));
                } else if (event.getCharCode() == ' ') {
                    fire();
                }
            }
        }, KeyPressEvent.getType());

        RootPanel.get().addDomHandler(new KeyUpHandler() {

            @Override
            public void onKeyUp(KeyUpEvent event) {
                body.setLinearVelocity(new Vec2(0, 0));
            }
        }, KeyUpEvent.getType());

        this.body.m_fixtureList.m_filter.groupIndex = 1;
    }

    public void draw() {
        PhysicsInvaders.ctx.setFillStyle("#000000");
        super.draw();
    }

    private void fire() {
        Bullet b = new Bullet((this.pos.x) * PhysicsInvaders.PTM_RATIO
                + (this.width / PhysicsInvaders.PTM_RATIO / 2), this.pos.y
                * PhysicsInvaders.PTM_RATIO - this.height);
        PhysicsInvaders.listOfObjects.add(b);
    }
}
