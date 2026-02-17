package entity;

import main.AssetSetter;
import main.GamePanel;
import main.KeyHandler;
import main.PlayerTp;
import tile.TileManager;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;



    public final int screenX;
    public final int screenY;

    int animSpeed = 10;
    int sprintSpeed;
    int currPosX = 0;
    int currPosY = 0;


    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize);
        screenY = gp.screenHeight/2 - (gp.tileSize);

        solidArea = new Rectangle();
        solidArea.x = 40;
        solidArea.y = 70;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 45;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues (){
        worldX = gp.tileSize*8;
        worldY = gp.tileSize*6;
        speed = 5;
        direction = "downNone";
        action = "none";
    }

    public void getPlayerImage() {

        try {
            P_Up_1 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_Up_1.png"));
            P_Up_2 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_Up_2.png"));
            P_Down_1 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_Down_1.png"));
            P_Down_2 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_Down_2.png"));
            P_Right_1 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_Right_1.png"));
            P_Right_2 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_Right_2.png"));
            P_Left_1 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_Left_1.png"));
            P_Left_2 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_Left_2.png"));
            P_Def_1 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_Def_1.png"));
            P_Def_2 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_Def_2.png"));
            P_UpDef_1 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_UpDef_1.png"));
            P_UpDef_2 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_UpDef_2.png"));
            P_PunchRight_1 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_PunchRight_1.png"));
            P_PunchRight_2 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_PunchRight_2.png"));
            P_PunchLeft_1 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_PunchLeft_1.png"));
            P_PunchLeft_2 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_PunchLeft_2.png"));
            P_RightIdle_1 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_RightIdle_1.png"));
            P_RightIdle_2 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_RightIdle_2.png"));
            P_LeftIdle_1 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_LeftIdle_1.png"));
            P_LeftIdle_2 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_LeftIdle_2.png"));
            P_UpPunch_1 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_UpPunch_1.png"));
            P_UpPunch_2 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_UpPunch_2.png"));
            P_DownPunch_1 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_DownPunch_1.png"));
            P_DownPunch_2 = ImageIO.read(getClass().getResourceAsStream("/res/player/P_DownPunch_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void update (){
        boolean isMoving = false;

        if(keyH.shiftPressed){
            sprintSpeed = 2;
        }
        if(!keyH.shiftPressed){
            sprintSpeed = 0;
        }
        if(keyH.spacePressed){
            if(Objects.equals(direction, "left") || Objects.equals(direction, "leftNone")){
                action = "punchLeft";
            }else if (Objects.equals(direction, "right") || Objects.equals(direction, "rightNone")){
                action = "punchRight";
            }else if (Objects.equals(direction, "up") || Objects.equals(direction, "upNone")){
                action = "punchUp";
            }else if (Objects.equals(direction, "down") || Objects.equals(direction, "downNone")){
                action = "punchDown";
            }
        }
        if(!keyH.spacePressed){
            action = "none";
        }
        if(Objects.equals(direction, "up") || Objects.equals(direction, "upNone")){
            direction = "upNone";
        }else if(Objects.equals(direction, "down") || Objects.equals(direction, "downNone")){
            direction = "downNone";
        }else if(Objects.equals(direction, "left") || Objects.equals(direction, "leftNone")){
            direction = "leftNone";
        }else if(Objects.equals(direction, "right") || Objects.equals(direction, "rightNone")){
            direction = "rightNone";
        }

        if(Objects.equals(action, "none") && (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed)){
            isMoving = true;
            if(keyH.upPressed){
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            }else if (keyH.leftPressed) {
                direction = "left";
            }else{
                direction = "right";
            }
        }
        //ljud när man går
        if(isMoving && !gp.walkSound.clip.isRunning()){
            gp.walkSound.loop();
        } else if(!isMoving){
            gp.walkSound.stop();
        }


        //checkar tile collision
        collisionOn = false;
        gp.cChecker.checkTile(this);

        //checkar objeck collision
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);


        //Om collision e false man kan röra på sig och uppdaterar currPosX och currPosY
        if(!collisionOn){
            currPosX = worldX;
            currPosY = worldY;
            switch (direction){
                case "up": worldY -= speed + sprintSpeed; break;
                case "down": worldY += speed + sprintSpeed; break;
                case "left": worldX -= speed + sprintSpeed; break;
                case "right": worldX += speed + sprintSpeed; break;
            }
        }
        spriteCounter++;
        if(spriteCounter > animSpeed-(sprintSpeed*2)){
            if(spriteNum == 1){
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;

        }
    }
    public void pickUpObject(int i){
        if (i != 999 && Objects.equals(gp.obj[i].name, "DoorOpen")) {
            if(PlayerTp.tp(currPosX, currPosY)[0] != 0 && PlayerTp.tp(currPosX, currPosY)[1] != 0){
                movePlayer(PlayerTp.tp(currPosX, currPosY));
            }

        }else if(i != 999){
            gp.obj[i] = null;
        }
    }

    public void movePlayer(int[] xAndy){
        worldX = xAndy[0];
        worldY = xAndy[1];
    }
    public void draw(Graphics2D g2){

        BufferedImage image = null;
        if(Objects.equals(action, "none")){
            switch (direction){
                case "leftNone":
                    if(spriteNum == 1){
                        image = P_LeftIdle_1; //för walking animationin
                    }
                    if(spriteNum == 2){
                        image = P_LeftIdle_2;
                    }
                    break;
                case "rightNone":
                    if(spriteNum == 1){
                        image = P_RightIdle_1; //för walking animationin
                    }
                    if(spriteNum == 2){
                        image = P_RightIdle_2;
                    }
                    break;
                case "upNone":
                    if(spriteNum == 1){
                        image = P_UpDef_1; //för walking animationin
                    }
                    if(spriteNum == 2){
                        image = P_UpDef_2;
                    }
                    break;
                case "downNone":
                    if(spriteNum == 1){
                        image = P_Def_1; //för walking animationin
                    }
                    if(spriteNum == 2){
                        image = P_Def_2;
                    }
                    break;
                case "up":
                    if(spriteNum == 1){
                        image = P_Up_1; //för walking animationin
                    }
                    if(spriteNum == 2){
                        image = P_Up_2;
                    }
                    break;
                case "down":
                    if(spriteNum == 1){
                        image = P_Down_1; //för walking animationin
                    }
                    if(spriteNum == 2){
                        image = P_Down_2;
                    }
                    break;
                case "left":
                    if(spriteNum == 1){
                        image = P_Left_1; //för walking animationin
                    }
                    if(spriteNum == 2){
                        image = P_Left_2;
                    }
                    break;
                case "right":
                    if(spriteNum == 1){
                        image = P_Right_1; //för walking animationin
                    }
                    if(spriteNum == 2){
                        image = P_Right_2;
                    }
                    break;
            }
        } else{
            switch (action){
                case "punchRight":
                    if(spriteNum == 1){
                        image = P_PunchRight_1; //för slag animationin
                    }
                    if(spriteNum == 2){
                        image = P_PunchRight_2;
                    }
                    break;
                case "punchLeft":
                    if(spriteNum == 1){
                        image = P_PunchLeft_1; //för slag animationin
                    }
                    if(spriteNum == 2){
                        image = P_PunchLeft_2;
                    }
                    break;
                case "punchUp":
                    if(spriteNum == 1){
                        image = P_UpPunch_1; //för slag animationin
                    }
                    if(spriteNum == 2){
                        image = P_UpPunch_2;
                    }
                    break;
                case "punchDown":
                    if(spriteNum == 1){
                        image = P_DownPunch_1; //för slag animationin
                    }
                    if(spriteNum == 2){
                        image = P_DownPunch_2;
                    }
                    break;
            }
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize+30, gp.tileSize+30, null);
    }

}
