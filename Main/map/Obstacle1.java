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

public class Obstacle1 extends Entity{
    public ImageView obstacle;
    public Image obstacleImage;
    
    public Obstacle1(double x,double y) throws FileNotFoundException{
        obstacleImage = new Image(new FileInputStream("picture/crate_05.png"));
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
    obstacle.setFitWidth(Width);
    obstacle.setFitHeight(Height);
    obstacle.setX((Pos[0]-Width/2)); 
    obstacle.setY((1080-Pos[1]-Height));

    hitbox.setWidth(Width);
    hitbox.setHeight(Height);
    hitbox.setX((Pos[0]-Width/2)); 
    hitbox.setY((1080-Pos[1]-Height));
   }

   @Override
   public void act(){
      setPos(getX(),getY());
   }
}
