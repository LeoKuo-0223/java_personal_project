package weapon;
import peopleEntity.Entity;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Laser extends Entity{
    public ImageView weapon;
    public Image playerImage;
    public Image laserH;
    public Image laserV;

	public Laser(double x,double y,boolean VerOrHor) throws FileNotFoundException {
        laserH = new Image(new FileInputStream("picture/laserBlueHorizontal.png"));
        laserV = new Image(new FileInputStream("picture/laserBlueVertical.png"));
        if(VerOrHor){
            weapon = new ImageView(laserH);
            setSize(20,70);
        }else{
            weapon = new ImageView(laserV);
            setSize(70,20);
        }
        hitbox = new Rectangle();
        hitbox.setFill(Color.TRANSPARENT);
        hitbox.setStroke(Color.TRANSPARENT);
        hitbox.setStrokeWidth(2);
        setPos(x,y);
	}
    @Override
    public void setPos(double x,double y){
    Pos[0] = x;
    Pos[1] = y;

    hitbox.setWidth(20*ratio[0]);
    hitbox.setHeight(20*ratio[1]);
    hitbox.setX((Pos[0]-(Width)/2+20)*ratio[0]); 
    hitbox.setY((1080-Pos[1]-(Height-25))*ratio[1]);

    weapon.setFitWidth(Width*ratio[0]);
    weapon.setFitHeight(Height*ratio[1]);
    weapon.setX((Pos[0]-Width/2)*ratio[0]); 
    weapon.setY((1080-Pos[1]-Height)*ratio[1]);

    }
    @Override
    public void act(){
        setPos(getX(),getY());
    }
}