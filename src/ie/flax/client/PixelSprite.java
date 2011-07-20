package ie.flax.client;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

/*
 * TODO: Some really nice stuff with colours in the constructors needs to be
 * done. Could sprite map either (how frickin' cool would that be?) Either way,
 * at the moment, it's a boolean "do I make a body here" thing at the moment.
 * What you could do could be something like passing in an RGB value instead.
 */
public class PixelSprite /*extends PhysicsObject*/ {
	ArrayList<PixelBlock> listOfPixelBlocks = new ArrayList<PixelBlock>();
	int width, height;

	public PixelSprite(Vec2 pos, int group, boolean hasPowerups, String colour,
			int[][] pixelMap) {
		//super(pos.x,pos.x,pixelMap[0].length,pixelMap.length,BodyType.DYNAMIC);

		// height, width of each pixelblock
		int blockHeight = PhysicsInvaders.PTP_RATIO;
		int blockWidth = PhysicsInvaders.PTP_RATIO;
		width = pixelMap[0].length;
		height = pixelMap.length;
		for (int i = 0; i < pixelMap.length; i++) {
			for (int j = 0; j < pixelMap[0].length; j++) {
				if (pixelMap[i][j] != 0) {
					
						/*
						 * Right, here is where the four blocks = one block thing happens
						 */
						/*if (((i-1 > 0) && (j-1 > 0))){
							if ((pixelMap[i][j] == 1) 
									&& (pixelMap[i-1][j] == 1) 
									&& (pixelMap[i][j-1] == 1) 
									&& (pixelMap[i-1][j-1] == 1)){
								listOfPixelBlocks.add(new PixelBlock(
									(pos.x+(blockWidth*j)),
									(pos.y+(blockHeight*i)),
									(blockWidth*2) + 5, (blockHeight*2)+5,
									colour, BodyType.DYNAMIC));
								pixelMap[i][j] = 2;
								pixelMap[i-1][j] = 2;
								pixelMap[i-1][j-1] = 2;
								pixelMap[i][j-1] = 2;
							} 
						}*/
						if (pixelMap[i][j]!=2){
							listOfPixelBlocks.add(new PixelBlock(
									((pos.x + (blockWidth * j))),
									((pos.y + (blockHeight * i))),
									blockWidth, blockHeight,
									colour, BodyType.DYNAMIC));
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
