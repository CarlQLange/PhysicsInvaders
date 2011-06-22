package ie.flax.client;

import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.common.Vec2;

public class Bullet implements IGameObject {

	PixelSprite ps;

	public Bullet(float x, float y) {
		ps = new PixelSprite(new Vec2(x, y), 1, new int[][] { { 1 }, { 1 },
				{ 1 } });

		MassData md = new MassData();
		md.mass = 100.0f;

		for (PixelBlock i : ps.listOfPixelBlocks) {
			i.body.setMassData(md);
			i.body.setLinearVelocity(new Vec2(0, -20));
			i.aggressiveSleep = false;
		}
	}

	@Override
	public void draw() {
		ps.draw();
	}

	@Override
	public void update() {
		ps.update();
	}

}
