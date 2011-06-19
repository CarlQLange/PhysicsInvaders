package ie.flax.client;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;

public class BasicInvader extends GameObject {

    PixelSprite ps;
    boolean together = true;

    public BasicInvader(float x, float y, int w, int h) {
        super(x, y, w, h, BodyType.DYNAMIC);
        PhysicsInvaders.world.destroyBody(this.body);
        // create pixelblocks
        // listOfPixelBlocks.add(new PixelBlock(x, y, w, h, BodyType.DYNAMIC));
        // Vec2 posPS; = pos.mul(0.5f);
        int group = -1;

        // fuck yeah ultra retro all up in this bitch
        ps = new PixelSprite(pos, width, height, group, new int[][] {
                { 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 },
                { 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
                { 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
                { 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1 },
                { 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0 } });
    }

    @Override
    public void draw() {
        ps.draw("#00FF00");
        // don't draw primary body, it doesn't exist
    }

    @Override
    public void update() {
        // for each pixel block, update their positions
        ps.update();
        if (together == false) {
            for (PixelBlock i : ps.listOfPixelBlocks) {
                i.body.m_fixtureList.m_filter.groupIndex = 1;
            }
        }

        if (together == true) {
            Contact c;
            for (PixelBlock i : ps.listOfPixelBlocks) {
                if (i.body.getContactList() != null) {
                    c = i.body.getContactList().contact;
                    if (c.isTouching()) {
                        together = false;
                    }
                }
            }
        }

        // super.update();
    }
}
