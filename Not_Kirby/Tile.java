/*
ID: 816007247
Name: Kareem Mohammed
*/

import java.awt.*;
import java.awt.image.*;


public class Tile {

    private BufferedImage image;
    private boolean blocked;

    public Tile(BufferedImage image, boolean blocked){

        this.image = image;
        this.blocked = blocked;
    }

    public BufferedImage getImage(){return image;}
    public boolean isBlocked(){return blocked;}
}