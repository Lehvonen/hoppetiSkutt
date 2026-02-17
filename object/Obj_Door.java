package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Obj_Door  extends SuperObject{

    public Obj_Door(){

        name = "Door";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/Door.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
        collision = false;
    }

    public BufferedImage getDoor(){

        return null;
    }

}