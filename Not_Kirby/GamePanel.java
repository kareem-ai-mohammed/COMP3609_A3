/*
ID: 816007247
Name: Kareem Mohammed
*/

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements Runnable, KeyListener{

    public static final int width = 400;
    public static final int height = 400;

    private Thread thread;
    private boolean running;

    private BufferedImage image;
    private Graphics2D g;
    
    private int FPS = 30;
    private int targetTime = 1000/FPS;

    private TileMap tileMap;
    private Player player;

    public GamePanel(){

        super();
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();

    }

    public void addNotify(){

        super.addNotify();
        if(thread == null){

            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }

    public void run(){

        init();
        long startTime;
        long urdTime;
        long waitTime;

        while(running){

            startTime = System.nanoTime();

            update();
            render();
            draw();

            urdTime = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - urdTime;
            try{
                Thread.sleep(waitTime);
            }
            catch(Exception e){}
        }
    }

    public void init(){

        running = true;

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        tileMap = new TileMap("map.txt", 32);
        tileMap.loadTiles("graphics/tileset.gif");
        player = new Player(tileMap);
        player.setx(50);
        player.sety(50);



    }


    private void update(){

        tileMap.update();
        player.update();  
    }

    private void render(){

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        tileMap.draw(g);
        player.draw(g);
    }

    private void draw(){

        Graphics g2 = getGraphics();
        g2.drawImage(image,0,0,null);
        g2.dispose();
    }

    public void keyTyped(KeyEvent key){}

    public void keyPressed(KeyEvent key){
        int code = key.getKeyCode();
        if(code == KeyEvent.VK_LEFT){
            player.setLeft(true);
        }
        if(code == KeyEvent.VK_RIGHT){
            player.setRight(true);
        }
        if(code == KeyEvent.VK_UP){
            player.setJumping(true);
        }
    }

    public void keyReleased(KeyEvent key){
        int code = key.getKeyCode();
        if(code == KeyEvent.VK_LEFT){
            player.setLeft(false);
        }
        if(code == KeyEvent.VK_RIGHT){
            player.setRight(false);
        }
    }




}