package entity;

import java.awt.Graphics2D;

import main.Panel;

public class Boost extends Entity {

	public boolean colided;
    public boolean active;
    public int duration;

    public Boost(Panel gp) {

        super(gp);
    }    

    public void draw(Graphics2D g2) {}

    public void update() {}
}