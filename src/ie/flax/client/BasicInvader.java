package ie.flax.client;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;

public class BasicInvader implements IGameObject {

    PixelSprite ps;
    boolean together = true;

    public BasicInvader(float x, float y, int w, int h) {
        int group = -1;

        int[][] spr = new int[][]
        {
        { 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0 },
        { 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 },
        { 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
        { 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0 },
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        { 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
        { 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1 },
        { 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0 } };

        // fuck yeah ultra retro all up in this bitch
        ps = new PixelSprite(new Vec2(x, y), w, h, group, spr);
    }

    public BasicInvader(float x, float y) {
        int group = -1;

        int[][] spr = new int[][]
        {
        { 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0 },
        { 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 },
        { 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
        { 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0 },
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        { 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
        { 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1 },
        { 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0 } };

        ps = new PixelSprite(new Vec2(x, y), group, spr);
    }

    @Override
    public void draw() {
        ps.draw();
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
