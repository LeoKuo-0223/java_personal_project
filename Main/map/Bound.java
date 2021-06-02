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
public class Bound extends Entity{
    public ImageView bound;
    public Image boundImage;
    
    public Bound(double x,double y) throws FileNotFoundException{
        boundImage = new Image(new FileInputStream("picture/block_01.png"));
        bound = new ImageView(boundImage);
        hitbox = new Rectangle();
        hitbox.setFill(Color.TRANSPARENT);
        hitbox.setStroke(Color.LIGHTGREEN);
        hitbox.setStrokeWidth(2);
        setSize(80,80);
        setPos(x, y);
    }
    @Override
    public void setPos(double x,double y){
    Pos[0] = x;
    Pos[1] = y;
    bound.setFitWidth(Width);
    bound.setFitHeight(Height);
    bound.setX((Pos[0]-Width/2)); 
    bound.setY((1080-Pos[1]-Height));

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
