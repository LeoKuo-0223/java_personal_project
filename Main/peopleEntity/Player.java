package peopleEntity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Entity{
    public boolean animation ;
	public Player(double x,double y,int role) throws FileNotFoundException {
        if(role ==1){
            playerImage = new Image(new FileInputStream("picture/player_01.png"));
            player = new ImageView(playerImage);
            turnright =new Image(new FileInputStream("picture/player_12.png"));
            turnleft =new Image(new FileInputStream("picture/player_14.png"));
            turnup =new Image(new FileInputStream("picture/player_02.png"));
            setSize(100,100);
        }
        else if(role ==2){
            playerImage = new Image(new FileInputStream("picture/character_zombie_cheer0.png"));
            player = new ImageView(playerImage);
            turnleft = new Image(new FileInputStream("picture/character_zombie_Left.png"));
            turnright =new Image(new FileInputStream("picture/character_zombie_walk0.png"));
            turnup =new Image(new FileInputStream("picture/character_zombie_back.png"));
            setSize(100,90);
        }
		
        player.setSmooth(true);
        hitbox = new Rectangle();
        hitbox.setFill(Color.TRANSPARENT);
        hitbox.setStroke(Color.TRANSPARENT);
        hitbox.setStrokeWidth(2);
        animation = false;
        
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

    hitbox.setWidth((Width-40)*ratio[0]);
    hitbox.setHeight((Height-40)*ratio[1]);
    hitbox.setX((Pos[0]-(Width-40)/2)*ratio[0]); 
    hitbox.setY((1080-Pos[1]-(Height-30))*ratio[1]);
   }
   @Override
   public void act(){

    if(inject){
        inject = false;
        setPos(1000, 480);
        Motion[0] = 0;
        Motion[1] = 0;
    }else{
        if(collidev==1&& !Leftpress && !Rightpress && !Down){
            Motion[0] = 0;
            Motion[1] = 0;
         }
         else if(collidev==2 && !Leftpress && !Rightpress && !Up){
            Motion[0] = 0;
            Motion[1] = 0;
         }else if(collideh==1 && !Leftpress && !Down && !Up ){
            Motion[0] = 0;
            Motion[1] = 0;
         }else if(collideh==2 && !Rightpress && !Down && !Up){
            Motion[0] = 0;
            Motion[1] = 0;
         }
         else{
             collidev=0;
             collideh=0;
             if(Rightpress||Leftpress||Up||Down){
                if(Rightpress == true) {
                    Motion[0] = 8;
                    Motion[1] = 0;
                    player.setImage(turnright);
                }
                if(Leftpress == true) {
                    Motion[0] = -8;
                    Motion[1] = 0;
                    player.setImage(turnleft);
                }
                if(Up == true) {
                    Motion[1] = 8;
                    Motion[0] = 0;
                    player.setImage(turnup);
                }
                if(Down == true) {
                    Motion[1] = -8;
                    Motion[0] = 0;
                    player.setImage(playerImage);
                }
             }else{
                 Motion[0] = 0;
                 Motion[1] = 0;
             }
    
            
           
         }
          setPos(getX()+(Motion[0]),getY()+(Motion[1]));
    }


   }
  

}
