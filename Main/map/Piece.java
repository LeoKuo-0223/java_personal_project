package map;

import peopleEntity.*;
import java.io.FileNotFoundException;
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
    ground.setFitWidth(Width*ratio[0]);
    ground.setFitHeight(Height*ratio[1]);
    ground.setX((Pos[0]-Width/2)*ratio[0]); 
    ground.setY((1080-Pos[1]-Height)*ratio[1]);

    block.setWidth(Width*ratio[0]);
    block.setHeight(Height*ratio[1]);
    block.setX((Pos[0]-Width/2)*ratio[0]); 
    block.setY((1080-Pos[1]-Height)*ratio[1]);

   }


   @Override
   public void act(){
      setPos(getX(),getY());
   }
}
