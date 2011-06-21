package ie.flax.client;

import org.jbox2d.dynamics.BodyType;

public class PixelBlock extends PhysicsObject {

	public PixelBlock(float x, float y, int w, int h, BodyType t) {
		super(x, y, w, h, t);
	}

	@Override
	public void draw() {
		/*
		 * You would not BELIEVE how bad this is for the framerate. 
		 * DO NOT LEAVE IN FOR FINAL
		 */
		if (!this.body.isAwake())
			PhysicsInvaders.ctx.setFillStyle("#00FF00");
		if (this.body.isAwake())
			PhysicsInvaders.ctx.setFillStyle("#000000");
		super.draw();
	}

	@Override
	public void update() {
		/*
		 * Debug code which tells me which blocks are asleep has shown that not
		 * enough pixelblocks go to sleep when they stop moving - my guess is that
		 * when there are too many blocks colliding with one, there's tiny amounts
		 * of movement. Proposed solution is to do some of my own checking - if
		 * a block has a velocity of less than 0.05 in any direction, put it to sleep.
		 * That code would go here.
		 */
		super.update();
	}
}
