package map;
import peopleEntity.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.FileInputStream;

public class obstacle_wood extends Entity{
    public ImageView obstacle;
    public Image obstacleImage;
    
    public obstacle_wood(double x,double y) throws FileNotFoundException{
        obstacleImage = new Image(new FileInputStream("picture/crate_02.png"));
        obstacle = new ImageView(obstacleImage);
        hitbox = new Rectangle();
        hitbox.setFill(Color.TRANSPARENT);
        hitbox.setStroke(Color.LIGHTGREEN);
        hitbox.setStrokeWidth(2);
        setSize(80,60);
        setPos(x, y);
    }
    @Override
    public void setPos(double x,double y){
    Pos[0] = x;
    Pos[1] = y;
    obstacle.setFitWidth(Width*ratio[0]);
    obstacle.setFitHeight(Height*ratio[1]);
    obstacle.setX((Pos[0]-Width/2)*ratio[0]); 
    obstacle.setY((1080-Pos[1]-Height)*ratio[1]);

    hitbox.setWidth(Width*ratio[0]);
    hitbox.setHeight(Height*ratio[1]);
    hitbox.setX((Pos[0]-Width/2)*ratio[0]); 
    hitbox.setY((1080-Pos[1]-Height)*ratio[1]);
   }

   @Override
   public void act(){
      setPos(getX(),getY());
   }
}