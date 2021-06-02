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
public class Piece extends Entity{
    public ImageView ground;
    public Image groundImage;
    public Rectangle block;


    public Piece(double x,double y) throws FileNotFoundException{
        groundImage = new Image(new FileInputStream("picture/ground_06.png"));
        ground = new ImageView(groundImage);
        block = new Rectangle();

        block.setStroke(Color.TRANSPARENT);
        block.setFill(Color.TRANSPARENT);
        block.setStrokeWidth(2);
        ground.setSmooth(true);
        ground.setVisible(true);
        setSize(80,80);
        setPos(x,y);
    }
    @Override
    public void setPos(double x,double y){
    Pos[0] = x;
    Pos[1] = y;
    ground.setFitWidth(Width);
    ground.setFitHeight(Height);
    ground.setX((Pos[0]-Width/2)); 
    ground.setY((1080-Pos[1]-Height));

    block.setWidth(Width);
    block.setHeight(Height);
    block.setX((Pos[0]-Width/2)); 
    block.setY((1080-Pos[1]-Height));

   }


   @Override
   public void act(){
      setPos(getX(),getY());
   }
}
