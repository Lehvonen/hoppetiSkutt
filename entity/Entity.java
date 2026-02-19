package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    public int worldX, worldY;
    public int speed;

    //många bilder för walking animation
    public BufferedImage P_Left_1, P_Left_2, P_Right_1, P_Right_2,P_Up_1, P_Up_2, P_Down_1, P_Down_2, P_Def_1, P_Def_2, P_UpDef_1, P_UpDef_2,
                        P_PunchRight_1, P_PunchRight_2, P_PunchLeft_1, P_PunchLeft_2, P_LeftIdle_1, P_LeftIdle_2, P_RightIdle_1, P_RightIdle_2,
                        P_UpPunch_1, P_UpPunch_2, P_DownPunch_1, P_DownPunch_2, P_PickUpRight_1, P_PickUpRight_2;
    public String direction;
    public String action;
    // sprite
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
}
