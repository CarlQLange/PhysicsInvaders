package ie.flax.client;

/*
 * If you're making a class that's going to be drawn in-game, it needs to
 * implement this.
 */
public interface IGameObject {
    public void draw();

    public void update();
}
