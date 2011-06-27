package ie.flax.client;

import org.jbox2d.dynamics.BodyType;

public class SuperPixelBlock extends PixelBlock {

	public Power pow;

	public enum Power {
		AddMass, AddSpeed, RepelShield, ForceField
	}

	public SuperPixelBlock(float x, float y, int w, int h, BodyType t, Power p) {
		super(x, y, w, h, null, t);

		pow = p;
		switch (pow) {
		case AddMass:
			colour = "#FF0000";
			break;
		case AddSpeed:
			colour = "#00FF00";
			break;
		case RepelShield:
			colour = "#00FFFF";
			break;
		case ForceField:
			colour = "#FF00FF";
			break;
		}

	}

	@Override
	public void draw() {

		super.draw();
	}
}
