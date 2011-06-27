package ie.flax.client;

import ie.flax.client.SuperPixelBlock.Power;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import com.google.gwt.user.client.Random;

/*
 * TODO: Some really nice stuff with colours in the constructors needs to be
 * done. Could sprite map either (how frickin' cool would that be?) Either way,
 * at the moment, it's a boolean "do I make a body here" thing at the moment.
 * What you could do could be something like passing in an RGB value instead.
 */
public class PixelSprite implements IGameObject {
	ArrayList<PixelBlock> listOfPixelBlocks = new ArrayList<PixelBlock>();
	int width, height;

	public PixelSprite(Vec2 pos, int group, boolean hasPowerups, String colour,
			int[][] map) {
		// height, width of each pixelblock
		int blockHeight = PhysicsInvaders.PTP_RATIO;
		int blockWidth = PhysicsInvaders.PTP_RATIO;
		width = map[0].length;
		height = map.length;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] != 0) {
					if ((hasPowerups) && (Random.nextInt(10) == 1)) {
						listOfPixelBlocks.add(new SuperPixelBlock(
								((pos.x + (blockWidth * j))),
								((pos.y + (blockHeight * i))), blockWidth,
								blockHeight, BodyType.DYNAMIC,
								Power.RepelShield));
					} else {
						listOfPixelBlocks.add(new PixelBlock(
								((pos.x + (blockWidth * j))),
								((pos.y + (blockHeight * i))), blockWidth,
								blockHeight, colour, BodyType.DYNAMIC));
					}
				}
			}
		}

		for (PixelBlock i : listOfPixelBlocks) {
			i.body.m_fixtureList.m_filter.groupIndex = group;
			i.body.setSleepingAllowed(true);
		}
	}

	public void draw() {
		for (PixelBlock i : listOfPixelBlocks) {
			i.draw();
		}
	}

	public void update() {
		for (PixelBlock i : listOfPixelBlocks) {
			i.update();
		}
	}
}
