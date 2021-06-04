package peopleEntity;

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Robot extends Entity{
    public boolean turnaround;
    public Robot(double x,double y) throws FileNotFoundException{
        playerImage = new Image(new FileInputStream("picture/character_robot_drag.png"));
        player = new ImageView(playerImage);
        player.setSmooth(true);
        hitbox = new Rectangle();
        // hitbox.setFill(Color.TRANSPARENT);
        // hitbox.setStroke(Color.LIGHTGREEN);
        // hitbox.setStrokeWidth(2);
    turnaround = false;
        setSize(100,128);
        setPos(x,y);
    }

    @Override
    public void setPos(double x,double y){
    Pos[0] = x;
    Pos[1] = y;
    player.setFitWidth(Width*ratio[0]);
    player.setFitHeight(Height*ratio[1]);
    player.setX((Pos[0]-Width/2)*ratio[0]); 
    player.setY((1080-Pos[1]-Height)*ratio[1]);

   }
   @Override
   public void act(){
       if(getY()>=80 &&!turnaround){
        setPos(getX(), getY()-1);
       }else turnaround = true;
       
       if(turnaround){
           if(getY()<=870){
            setPos(getX(), getY()+1);
           }else turnaround = false;
       }
       
   }
}
