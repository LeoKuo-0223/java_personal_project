package peopleEntity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import weapon.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Entity {
	public Image playerImage;
	protected WritableImage flipimage;
	protected double[] Pos = { 0, 0 };
	public double[] Motion = { 0, 0 };
	protected int Width = 0, Height = 0;
	public int collideh;
	public int collidev;
	public boolean Rightpress = false, Leftpress = false, Up = false, Down = false;
	public ImageView player;
	public Rectangle hitbox;
	public static double[] World = { 0, 0 };
	public static double[] ratio = { 1, 1 };
	public double health_value;
	public Image turnright;
    public Image turnleft;
    public Image turnup;
	public boolean fire = false;
    public boolean alreayFired = false;
	public Laser laser;
	public int fireNum = 0;
	public List<Laser> laserList = new ArrayList<>();
	public boolean inject = false;
	public boolean superMode = false;
	public int point ;
    public Text pointText;
	public ImageView face;
	public Rectangle faceBase;
	public Entity() throws FileNotFoundException{}

	public void setPos(double x,double y){
		Pos[0] = x;
		Pos[1] = y;
		player.setFitHeight(Width*ratio[0]);
		player.setFitHeight(Height*ratio[1]);
		player.setX((Pos[0]-Width/2)*ratio[0]); 
		player.setY((1080-Pos[1]-Height)*ratio[1]);
	}

	public void setSize(int w,int h){
		Width = w;
		Height = h;
	}

	public static void setScreenSize(double x,double y){
		ratio[0]=x/1920;
		ratio[1]=y/1080;
	}

	public double getX(){
		return Pos[0];
	}

	public double getY(){
		return Pos[1];
	}

	public double getW(){
		return Width;
	}
	
	public double getH(){
		return Height;
	}

	public double getMx(){
		return Motion[0];
	}

	public double getMy(){
		return Motion[1];
	}

	public void setMx(double mx){
		Motion[0]=mx;
	}

	public void setMy(double my){
		Motion[1]=my;
	}

	public double cancel(double my){
		if (my<0) my=0;
		return my;
	}

	public WritableImage getFlip(Image img){
		int w=(int)img.getWidth(),h=(int)img.getHeight();
		WritableImage flipimg = new WritableImage(w,h);
		PixelReader pixelReader = img.getPixelReader();
		PixelWriter writer = flipimg.getPixelWriter();

		for(int col=0;col<h;col++){
			for(int row=0;row<w;row++){
			Color color = pixelReader.getColor(row,col);
			writer.setColor(w-row-1, col, color);
			}
		}
		return flipimg;
	}

	public boolean Isinrange(Entity B){
		boolean inrange  = false;
		if(this.getX()-35<B.getX()+B.getW()/2&&this.getX()+35>B.getX()-B.getW()/2){
			inrange = true;
		}
		return inrange;
	}

	public void act() throws FileNotFoundException{}
}
