package ie.flax.client;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

public class PixelSprite {
    ArrayList<PixelBlock> listOfPixelBlocks = new ArrayList<PixelBlock>();

    public PixelSprite(Vec2 pos, float w, float h, int group, int[][] map) {
        // height, width of each pixelblock
        int blockHeight = (int) (h / map.length);
        int blockWidth = (int) (w / map[0].length);

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] != 0) {
                    listOfPixelBlocks.add(new PixelBlock(
                            ((pos.x + (blockWidth * j))),
                            ((pos.y + (blockHeight * i))), blockWidth,
                            blockHeight, BodyType.DYNAMIC));
                }
            }
        }

        for (PixelBlock i : listOfPixelBlocks) {
            i.body.m_fixtureList.m_filter.groupIndex = group;
            i.body.setSleepingAllowed(true);
        }
    }

    public void draw(String colour) {
        PhysicsInvaders.ctx.setFillStyle(colour);
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
