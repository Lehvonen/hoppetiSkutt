
package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Obj_Sword  extends SuperObject{

    public Obj_Sword(){

        name = "Door";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/Sword.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
        collision = false;
    }


}
