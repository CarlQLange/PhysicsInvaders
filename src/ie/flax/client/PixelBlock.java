package ie.flax.client;

import org.jbox2d.dynamics.BodyType;

import com.google.gwt.user.client.Window;

public class PixelBlock extends PhysicsObject implements Comparable<PixelBlock> {

	boolean aggressiveSleep = true;
	boolean shouldDestroy = false;
	public String colour = "#000000";

	public PixelBlock(float x, float y, int w, int h, String colour, BodyType t) {
		super(x, y, w, h, t);

		if (colour != null)
			this.colour = colour;

		PhysicsInvaders.dm.addToDrawList(this);
	}

	@Override
	public void draw() {
		/*
		 * You would not BELIEVE how bad this is for the framerate. DO NOT LEAVE
		 * IN FOR FINAL
		 */
		// if (!this.body.isAwake())
		// PhysicsInvaders.ctx.setFillStyle("#00FF00");
		// if (this.body.isAwake()) PhysicsInvaders.ctx.setFillStyle("#000000");
		super.draw();
	}

	@Override
	public void update() {
		/*
		 * Debug code which tells me which blocks are asleep has shown that not
		 * enough pixelblocks go to sleep when they stop moving - my guess is
		 * that when there are too many blocks colliding with one, there's tiny
		 * amounts of movement. Proposed solution is to do some of my own
		 * checking - if a block has a velocity of less than 0.05 in any
		 * direction, put it to sleep. That code would go here.
		 */
		/*
		 * This is not reusable code. TODO make better.
		 */
			if (this.body.getPosition().y * PhysicsInvaders.PTM_RATIO >= Window
					.getClientHeight() / 3 * 2) { // is pixelblock in bottom
													// third of canvas
				if ((this.body.getLinearVelocity().x <= 0.5)
						&& (this.body.getLinearVelocity().x >= -0.5)) {
					if ((this.body.getLinearVelocity().y <= 0.5)
							&& (this.body.getLinearVelocity().y >= -0.5)) {
						
							if(shouldDestroy) {
								PhysicsInvaders.world.destroyBody(this.body);
								PhysicsInvaders.dm.removeFromDrawList(this);
							}
					}
				}
			}
		
		super.update();
	}

	@Override
	public int compareTo(PixelBlock o) {
		return this.colour.compareTo(o.colour);
	}

	public void setToDestroy() {
		shouldDestroy = true;
	}
}
