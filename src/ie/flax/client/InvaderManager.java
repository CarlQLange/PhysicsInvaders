package ie.flax.client;

import com.google.gwt.user.client.Timer;


public class InvaderManager { 
	private double speed = 8;
	public int invadersOnScreen = 0;
    
	public InvaderManager() {
		
	}
	
	public void update(){
		PhysicsInvaders.dm.getHud().drawString("invaders: " + invadersOnScreen, 20, 500);
		if ((invadersOnScreen >= 0) && (invadersOnScreen < 2)){
			addInvader();
		}
	}
	
	private void addInvader(){
		BasicInvader bi = new BasicInvader((float) (Math.random()*PhysicsInvaders.dm.getCanvasWidth()), (float)Math.random()*250, speed);
		PhysicsInvaders.listOfObjects.add(bi);
		invadersOnScreen++;
	}
	
	public void destroyInvader(final BasicInvader bi){
		Timer t = new Timer(){

			@Override
			public void run() {
				for (PixelBlock i : bi.ps.listOfPixelBlocks) {
					if ((i.body.getLinearVelocity().x < 1) && (i.body.getLinearVelocity().y < 1)){
					PhysicsInvaders.world.destroyBody(i.body);
					PhysicsInvaders.dm.removeFromDrawList(i);
					}
				}
			}
			
		};
		
		t.schedule(10000);
		
		PhysicsInvaders.listOfObjects.remove(bi);
		invadersOnScreen--;
	}
}
