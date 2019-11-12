/*
ID: 816007247
Name: Kareem Mohammed
*/

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;


public class TileMap {

    private int x;
    private int y;

    private int tileSize;
    private int [][] map;
    private int mapwidth;
    private int mapheight;

    private BufferedImage tileSet;
    private Tile[][] tiles;

    private int minx;
    private int miny;
    private int maxx = 0;
    private int maxy = 0;
    

    public TileMap(String s, int tileSize){

        this.tileSize = tileSize;

        try{
            BufferedReader br = new BufferedReader(new FileReader(s));
            
            mapwidth = Integer.parseInt(br.readLine());
            mapheight = Integer.parseInt(br.readLine());
            map = new int[mapheight][mapwidth];

            minx = GamePanel.width - mapwidth * tileSize;
            miny = GamePanel.height - mapheight * tileSize;

            String delimiters = "\\s+";
            for(int row=0; row < mapheight; row++){
                String line = br.readLine();
                String[] tokens = line.split(delimiters);
                for(int col = 0; col < mapwidth; col++){

                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }
        }
        catch(Exception e){}


    }

    public void loadTiles(String s){

        try {
            tileSet = ImageIO.read(new File(s));
            int numTilesAcross = (tileSet.getWidth() +1) / (tileSize+1);
            tiles = new Tile[2][numTilesAcross];

            BufferedImage subimage;
            for(int col = 0; col < numTilesAcross; col++){
                subimage = tileSet.getSubimage(
                    col * tileSize + col,
                     0, tileSize, tileSize);

                tiles[0][col] = new Tile(subimage, false);
                subimage = tileSet.getSubimage(
                    col * tileSize + col,
                    tileSize + 1,
                    tileSize,
                    tileSize
                );
                tiles[1][col] = new Tile(subimage, true);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }



    public int getx(){ return x;}
    public int gety(){ return y;}
    public void setx(int i){ 
        x=i;
        if(x < minx){ x = minx;}
        if(x > maxx){ x = maxx;}
    }
    public void sety(int i){ 
        y=i;
        if(y < miny){ y = miny;}
        if(y > maxy){ y = maxy;}
    }

    public int getColTile(int x){
        return x / tileSize;
    }
    public int getRowTile(int y){
        return y / tileSize;
    }
    public int getTile(int row, int col){
        return map[row][col];
    }
    public int getTileSize(){
        return tileSize;
    }
    public boolean isBlocked(int row, int col){

        int rc = map[row][col];
        int r = rc / tiles[0].length;
        int c = rc / tiles[0].length;
        return tiles[r][c].isBlocked();

    }


    public void update(){}

    public void draw(Graphics2D g){

        for(int row = 0; row < mapheight; row++){
            for(int col = 0; col < mapwidth; col++){

                int rc = map[row][col];

                int r = rc / tiles[0].length;
                int c = rc%tiles[0].length;

                g.drawImage(tiles[r][c].getImage(),
                x + col * tileSize,
                y + row * tileSize,
                null);
               
            }

        }

        

    }

}