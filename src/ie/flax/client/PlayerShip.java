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
public class PlayerShip implements IGameObject {
	int health;
	int score;
	Vec2 pos;
	PixelSprite ps;

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

	public PlayerShip(float x, float y) {
		// super(x, y, w, h, BodyType.KINEMATIC);
		ps = new PixelSprite(new Vec2(x, y), 1, new int[][] {
				{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0 },
				{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } });

		for (PixelBlock i : ps.listOfPixelBlocks) {
			i.body.setType(BodyType.DYNAMIC);
			i.aggressiveSleep = false;
		}

		pos = new Vec2(x, y);

		RootPanel.get().addDomHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				/*
				 * FIXME: Not the correct way to move this body. Do it by
				 * applying force instead.
				 */
				if (event.getCharCode() == 'd') {
					setVelocity(new Vec2(6, 0));
				}
				if (event.getCharCode() == 'a') {
					setVelocity(new Vec2(-6, 0));

				}
				if (event.getCharCode() == 'w') {
					setVelocity(new Vec2(0, -6));
				}
				if (event.getCharCode() == 's') {
					setVelocity(new Vec2(0, 6));
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
					for (PixelBlock i : ps.listOfPixelBlocks)
						i.body.setLinearVelocity(new Vec2(0, 0));
			}
		}, KeyUpEvent.getType());
	}

	public void draw() {
		PhysicsInvaders.ctx.setFillStyle("#000000");
		ps.draw();
	}

	public void update() {
		ps.update();
		this.pos = ps.listOfPixelBlocks.get(0).pos;
		for (PixelBlock i : ps.listOfPixelBlocks) {
			// -250ish
			i.body.applyForce(
					PhysicsInvaders.world.getGravity().negate()
							.mul(i.body.getMass()), i.body.getPosition());
		}
	}

	private void fireBullet() {
		PhysicsInvaders.ctx.setFillStyle("#F0F000");
		Bullet b = new Bullet((this.pos.x) * PhysicsInvaders.PTM_RATIO
				+ (this.ps.width / PhysicsInvaders.PTM_RATIO / 2), this.pos.y
				* PhysicsInvaders.PTM_RATIO - this.ps.height * 2);
		PhysicsInvaders.listOfObjects.add(b);
	}

	/*
	 * This method only serves to clean up the onKeyPress above.
	 */
	private void setVelocity(Vec2 vel) {
		for (PixelBlock i : ps.listOfPixelBlocks) {
			// i.body.setLinearVelocity(vel);
			i.body.setLinearVelocity(i.body.getLinearVelocity().add(vel));
		}
	}
}
