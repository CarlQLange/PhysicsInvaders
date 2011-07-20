package ie.flax.client;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;

import com.google.gwt.user.client.Window;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class BasicInvader implements IGameObject {

    PixelSprite ps;
    InvaderManager im = PhysicsInvaders.im;
    float speed;
    boolean destroyed = false;
    
    public BasicInvader(float x, float y, double speed) {
        int group = -1;
        this.speed = (float) speed;

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
/*
        int[][] spr = new int[][]{
        		{1,1,1},
        		{1,1,1},
        		{1,1,1}
        };
*/
        ps = new PixelSprite(new Vec2(x, y), group, true, "#000000", spr);
        
        for (PixelBlock i : ps.listOfPixelBlocks) {
			i.body.m_mass = 500;
		}
    }

    @Override
    public void draw() {
        ps.draw();
    }

    @Override
    public void update() {
        // for each pixel block, update their positions
        ps.update();
            Contact c;
            for (PixelBlock i : ps.listOfPixelBlocks) {
                if (i.body.getContactList() != null) {
                    c = i.body.getContactList().contact;
                    if (c.isTouching()) {
                        if (!destroyed){
                    	i.body.m_fixtureList.m_filter.groupIndex = 1;
                        im.destroyInvader(this);
                        destroyed = true;
                        }
                    }
                }
                i.body.setLinearVelocity((new Vec2(0, speed)));
                i.aggressiveSleep = false;
            }
        
    }
}
