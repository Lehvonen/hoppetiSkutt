package object;

import javax.imageio.ImageIO;

import java.io.IOException;

public class Obj_DoorOpen extends SuperObject{

    public Obj_DoorOpen(){

        name = "DoorOpen";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/DoorOpen.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
        collision = false;
    }



}