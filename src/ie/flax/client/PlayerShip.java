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
	int health;
	int score;

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;

	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public PlayerShip(float x, float y, int w, int h) {
		super(x, y, w, h, BodyType.KINEMATIC);
		RootPanel.get().addDomHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				/*
				 * FIXME: Not the correct way to move this body. Do it by
				 * applying force instead.
				 */
				Vec2 vel;
				if (event.getCharCode() == 'd') {
					vel = new Vec2(6, 0);
					if (body.getLinearVelocity().x <= vel.x)
						body.setLinearVelocity(body.getLinearVelocity()
								.add(vel));
				}
				if (event.getCharCode() == 'a') {
					vel = new Vec2(-6, 0);
					if (body.getLinearVelocity().x >= vel.x)
						body.setLinearVelocity(body.getLinearVelocity()
								.add(vel));
				}
				if (event.getCharCode() == 'w') {
					body.setLinearVelocity(new Vec2(0, -6));
					vel = new Vec2(0, -6);
					if (body.getLinearVelocity().y >= vel.y)
						body.setLinearVelocity(body.getLinearVelocity()
								.add(vel));
				}
				if (event.getCharCode() == 's') {
					vel = new Vec2(0, 6);
					if (body.getLinearVelocity().y <= vel.y)
						body.setLinearVelocity(body.getLinearVelocity()
								.add(vel));
				}
				if (event.getCharCode() == ' ') {
					fireBullet();
				}
				if (event.getCharCode() == 'l') {
					setHealth(100);
				}
			}
		}, KeyPressEvent.getType());

		RootPanel.get().addDomHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeEvent().getKeyCode() != ' ')
					body.setLinearVelocity(new Vec2(0, 0));
			}
		}, KeyUpEvent.getType());

		this.body.m_fixtureList.m_filter.groupIndex = 1;
	}

	public void draw() {
		PhysicsInvaders.ctx.setFillStyle("#000000");
		super.draw();
	}

	private void fireBullet() {
		Bullet b = new Bullet((this.pos.x) * PhysicsInvaders.PTM_RATIO
				+ (this.width / PhysicsInvaders.PTM_RATIO / 2), this.pos.y
				* PhysicsInvaders.PTM_RATIO - this.height * 2);
		PhysicsInvaders.listOfObjects.add(b);
	}
}
