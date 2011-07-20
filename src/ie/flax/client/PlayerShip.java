package ie.flax.client;


import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;

/*
 * TODO At some point you're gonna wanna change this from directly inheriting
 * PhysicsObject. However, this works for the moment. On top of that, adding
 * another like 70 pixel blocks? Probably not great from a performance point of
 * view.
 */
public class PlayerShip implements IGameObject {
    int health;
    int score;
    Vec2 pos;
    PixelSprite ps;
    boolean together = true;
	private int numberOfBullets = 0;

    public PlayerShip(float x, float y) {
        // super(x, y, w, h, BodyType.KINEMATIC);
        ps = new PixelSprite(new Vec2(x, y), -3, false, "#000000", new int[][] {
                { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0 },
                { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } });

        Contact c;
        for (PixelBlock i : ps.listOfPixelBlocks) {
            if (i.body.getContactList() != null) {
                c = i.body.getContactList().contact;
                if (c.isTouching()) {
                    setHealth(getHealth() - 1);
                }
            }
        }
        for (PixelBlock i : ps.listOfPixelBlocks) {
            i.body.setType(BodyType.DYNAMIC);
            i.aggressiveSleep = false;
            //for (PixelBlock j : ps.listOfPixelBlocks) {
//
  //          }
        }

        pos = new Vec2(x, y);

        RootPanel.get().addDomHandler(new KeyPressHandler() {

            @Override
            public void onKeyPress(KeyPressEvent event) {
                /*
                 * FIXME: Not the correct way to move this body. Do it by
                 * applying force instead.
                 */
                if (event.getCharCode() == 'd') {
                    setVelocity(new Vec2(6, 0));
                }
                if (event.getCharCode() == 'a') {
                    setVelocity(new Vec2(-6, 0));

                }
                if (event.getCharCode() == 'w') {
                    setVelocity(new Vec2(0, -6));
                }
                if (event.getCharCode() == 's') {
                    setVelocity(new Vec2(0, 6));
                }
                if (event.getCharCode() == ' ') {
                    fireBullet();
                }
                if (event.getCharCode() == 'l') {
                    setHealth(100);
                }
            }
        }, KeyPressEvent.getType());

        RootPanel.get().addDomHandler(new KeyUpHandler() {

            @Override
            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeEvent().getKeyCode() != ' ') {
                    for (PixelBlock i : ps.listOfPixelBlocks) {
                        i.body.setLinearVelocity(new Vec2(0, 0));
                    }
                }
            }
        }, KeyUpEvent.getType());
        
        Timer t = new Timer() {
			
			@Override
			public void run() {
				numberOfBullets--;
			}
		};
		t.scheduleRepeating(500);
    }

    @Override
    public void draw() {
        ps.draw();
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }

    public void setHealth(int health) {
        this.health = health;
        PhysicsInvaders.dm.getHud().drawString(health + "", 50, 500);
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void update() {
        ps.update();
        pos = ps.listOfPixelBlocks.get(0).pos;
        for (PixelBlock i : ps.listOfPixelBlocks) {
            // -250ish
            i.body.applyForce(
                    PhysicsInvaders.world.getGravity().negate()
                            .mul(i.body.getMass()), i.body.getPosition());
        }

        /*
         * OH GOD WTF IS THIS
         */
        // if (health > 0) {
        Contact c;
        for (PixelBlock i : ps.listOfPixelBlocks) {
            if (i.body.getContactList() != null) {
                c = i.body.getContactList().contact;
                if (c.isTouching()) {
                    setHealth(getHealth() - 1);
                }
            }
        }
        // }
    }

    private void fireBullet() {
    	if (numberOfBullets  < 50){
        Bullet b = new Bullet(((pos.x) * PhysicsInvaders.PTM_RATIO)
                + (ps.width / PhysicsInvaders.PTM_RATIO / 2),
                (pos.y * PhysicsInvaders.PTM_RATIO) - (ps.height * 2));
        PhysicsInvaders.listOfObjects.add(b);
        numberOfBullets+=3;
    	}
    }

    /*
     * This method only serves to clean up the onKeyPress above.
     */
    private void setVelocity(Vec2 vel) {
        for (PixelBlock i : ps.listOfPixelBlocks) {
            // i.body.setLinearVelocity(vel);
            i.body.setLinearVelocity(i.body.getLinearVelocity().add(vel));
        }
    }
}
