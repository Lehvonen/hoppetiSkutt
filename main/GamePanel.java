package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.sound.sampled.*;
import javax.swing.JPanel;
import java.awt.*;
import java.io.IOException;
import java.net.URL;


public class GamePanel extends JPanel implements Runnable{

    //walk sound
    public Sound walkSound = new Sound();


    //skärm settings
    final int originalTileSize = 16;
    final int scale = 6;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    //world parameter
    public final int maxWorldCol = 64;
    public final int maxWorldRow = 34;

    //FPS
    int FPS = 60;

    //System
    public TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound sound = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread;
    //Entity & Object
    public Player player = new Player(this, keyH);
    public  SuperObject[] obj = new SuperObject[10];


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {

        aSetter.setObject();
        playMusic(2);
        walkSound.setFile(4);
    }

    public void setGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS; // 0.01666666 sek
        double nextDrawTime = System.nanoTime() + drawInterval;


        while(gameThread != null){

            update();

            repaint();



            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }


    public void update() {
    player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        //tile
        tileM.draw(g2);
        //objects
        for(SuperObject i : obj){
            if(i != null){
                i.draw(g2, this);
            }
        }
        //player
        player.draw(g2);

        g2.dispose();
    }

    public void playMusic(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    public void stopMusic(){
        sound.stop();
    }
    public void playSE(int i){
        sound.setFile(i);
        sound.play();
    }
}
