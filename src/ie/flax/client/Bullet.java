package ie.flax.client;

import org.jbox2d.common.Vec2;

public class Bullet implements IGameObject {

	PixelSprite ps;

	public Bullet(float x, float y) {
		ps = new PixelSprite(new Vec2(x, y), 1, false, "#FF0000", new int[][] {
				{ 1 }, { 1 }, { 1 } });

		for (PixelBlock i : ps.listOfPixelBlocks) {
			i.body.m_mass = 50.0f;
			i.body.setLinearVelocity(new Vec2(0, -20));
			i.aggressiveSleep = true;
			i.body.setBullet(false); // probably should use this but performance
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
