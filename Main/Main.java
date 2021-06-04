import peopleEntity.*;
import map.*;
import weapon.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;

public class Main extends Application{
	List<Entity> player = new ArrayList<>();
	List<Piece> map = new ArrayList<>();
	List<Entity> obstacle = new ArrayList<>();
	int Map_X = 500;
	int Map_Y = 10;
	public double p_x = 650;
	public double p_y = 880;
	public double p2_x = 1470;
	public double p2_y = 100;


	int mapRaw_count = 0;
	public static Player p;
	public static Player p2;
	public static Robot r1;
	public static Robot2 r2;

	public Main() throws FileNotFoundException{
	      p = new Player(p_x,p_y,1);
		  p2 = new Player(p2_x, p2_y,2);
		  r1 = new Robot(520, 870);
		  r2 = new Robot2(1590, 80);

	}
	
	 public static void main(String args[]) throws FileNotFoundException{
	      launch(args);
	   }

	@Override
	public void start(Stage stage) throws Exception {
		Group root = new Group();
		Scene scene = new Scene(root, 1920, 1080);
	      scene.setOnKeyPressed(ke -> {
	          if (ke.getCode() == KeyCode.LEFT) p.Leftpress = true;
	          else if (ke.getCode() == KeyCode.RIGHT ) p.Rightpress = true;
	          else if (ke.getCode() == KeyCode.UP ) p.Up = true;
	          else if (ke.getCode() == KeyCode.DOWN ) p.Down = true;
			  else if (ke.getCode() == KeyCode.ENTER) {
				  if(!p.alreayFired){
					p.fire = true;
					p.alreayFired = true;
				  } 
				}else if (ke.getCode()==KeyCode.A) p2.Leftpress = true;
				else if (ke.getCode()==KeyCode.D) p2.Rightpress = true;
				else if (ke.getCode()==KeyCode.W) p2.Up = true;
				else if (ke.getCode()==KeyCode.S) p2.Down = true;
				else if (ke.getCode() == KeyCode.SPACE) {
					if(!p2.alreayFired){
					  p2.fire = true;
					  p2.alreayFired = true;
					} 
				}else if(ke.getCode()==KeyCode.R){	//reset location if 
					for(Entity p: player){
						for(Entity b: obstacle){
							if(p.hitbox.intersects(b.hitbox.getBoundsInLocal()))
								p.setPos(950, 850);
						}
					}
				}
				
			  
	       });
	       scene.setOnKeyReleased(ke -> {
	          if (ke.getCode() == KeyCode.LEFT) {
				p.Leftpress = false;
				if(p.Motion[0]<0){
					p.Motion[0] = 0;
				}
			  }
			  else if (ke.getCode() == KeyCode.RIGHT) {
				p.Rightpress = false;
				if(p.Motion[0]>0){
					p.Motion[0] = 0;
				}
			  }
	          else if (ke.getCode() == KeyCode.UP) {
				p.Up = false;
				if(p.Motion[1]>0){
					p.Motion[1] = 0;
				}
			  }
	          else if (ke.getCode() == KeyCode.DOWN) {
				p.Down = false;
				if(p.Motion[1]<0){
					p.Motion[1] = 0;
				}
			  }else if (ke.getCode() == KeyCode.ENTER) {
				p.fire = false;
				p.alreayFired = false;
			  }else if (ke.getCode() == KeyCode.A) {
				p2.Leftpress = false;
				if(p2.Motion[0]<0){
					p2.Motion[0] = 0;
				}
			  }
			  else if (ke.getCode() == KeyCode.D) {
				p2.Rightpress = false;
				if(p2.Motion[0]>0){
					p2.Motion[0] = 0;
				}
			  }
	          else if (ke.getCode() == KeyCode.W) {
				p2.Up = false;
				if(p2.Motion[1]>0){
					p2.Motion[1] = 0;
				}
			  }
	          else if (ke.getCode() == KeyCode.S) {
				p2.Down = false;
				if(p2.Motion[1]<0){
					p2.Motion[1] = 0;
				}
			  }else if (ke.getCode() == KeyCode.SPACE) {
				p2.fire = false;
				p2.alreayFired = false;
			  }
	       });

		stage.setFullScreen(true);
	    stage.setTitle("test");
	    stage.setScene(scene);
	    stage.show();
		player.add(p);
		player.add(p2);
		player.add(r1);
		player.add(r2);
		//build map
		for(String col: Map.map1){
			for(int i=0;i<col.length();i++){
				if(col.charAt(i)=='1'){
					Bound bound = new Bound(Map_X +80*i, Map_Y+80*mapRaw_count);
					obstacle .add(bound);
					root.getChildren().addAll(bound.bound,bound.hitbox);
				}else if(col.charAt(i)=='0'){
					Piece pieces = new Piece(Map_X +80*i, Map_Y+80*mapRaw_count);
					map.add(pieces);
					root.getChildren().addAll(pieces.ground,pieces.block);
				}else if(col.charAt(i)=='2'){
					Obstacle1 obs = new Obstacle1(Map_X +80*i, Map_Y+80*mapRaw_count);
					obstacle.add(obs);
					Piece pieces = new Piece(Map_X +80*i, Map_Y+80*mapRaw_count);
					map.add(pieces);
					root.getChildren().addAll(pieces.ground,pieces.block);
					root.getChildren().addAll(obs.obstacle,obs.hitbox);
				}else if(col.charAt(i)=='3'){
					obstacle_wood obs = new obstacle_wood(Map_X +80*i, Map_Y+80*mapRaw_count);
					obstacle .add(obs);
					Piece pieces = new Piece(Map_X +80*i, Map_Y+80*mapRaw_count);
					map.add(pieces);
					root.getChildren().addAll(pieces.ground,pieces.block);
					root.getChildren().addAll(obs.obstacle,obs.hitbox);
				}
			}
			mapRaw_count++;
		}
		
		player.forEach(p -> root.getChildren().addAll(p.player,p.hitbox));
		

		

		
	    AnimationTimer mainloop = new AnimationTimer() {
	         @Override
	         public void handle(long t) {
				Entity.setScreenSize(stage.getWidth(),stage.getHeight());
				map.forEach(m -> m.act());
				obstacle.forEach(o -> {try {o.act();} catch (FileNotFoundException e1) {e1.printStackTrace();}});
				player.forEach(p -> {try {p.act();} catch (FileNotFoundException e1) {e1.printStackTrace();}});
				// RotateTransition rt1 = new RotateTransition(Duration.millis(3000), p.player);
				// RotateTransition rt2 = new RotateTransition(Duration.millis(3000), p2.player);
				FadeTransition ft1 = new FadeTransition(Duration.seconds(0.1), p.player);
				FadeTransition ft2 = new FadeTransition(Duration.seconds(0.1), p2.player);
				

				//collide with bound
				for(Entity p: player){

					if(p.getMy()>0){
						for(Entity b: obstacle ){
							if(p.hitbox.intersects(b.hitbox.getBoundsInLocal())){
								System.out.println("hit up");
								p.collidev = 1;
								p.setPos(p.getX(), p.getY()-10);
							}
						}
					}else if(p.getMy()<0){
						for(Entity b: obstacle ){
							if(p.hitbox.intersects(b.hitbox.getBoundsInLocal())){
								System.out.println("hit down");
								p.collidev = 2;
								p.setPos(p.getX(), p.getY()+10);
							}
						}
					}else if(p.getMx()>0){
						for(Entity b: obstacle) {
							
								if(p.hitbox.intersects(b.hitbox.getBoundsInLocal())){
									System.out.println("hit right");
									p.collideh = 1;
									p.setPos(p.getX()-10, p.getY());
								}
							
						}
					}else if(p.getMx()<0){
						for(Entity b: obstacle ){
							if(p.hitbox.intersects(b.hitbox.getBoundsInLocal())){
								System.out.println("hit left");
								p.collideh = 2;
								p.setPos(p.getX()+10, p.getY());
							}
						}
					}
					//shoot
					if(p.fire){
						try {
							System.out.println("shoot");
							if(p.player.getImage()==(p.turnright)){
						loop1: for(int i=0;i<50;i++){
								Laser la = new Laser(p.getX()+20*(i+1),p.getY(),true);
								for(Entity b: obstacle){
									if(la.hitbox.intersects(b.hitbox.getBoundsInLocal())) break loop1;
								}
								p.laserList.add(la);
								root.getChildren().addAll(la.weapon,la.hitbox);
								p.fireNum+=2;
								
							}
							}else if(p.player.getImage()==(p.turnleft)){
						loop1: for(int i=0;i<50;i++){
									Laser la = new Laser(p.getX()-20*(i+1),p.getY(),true);
									for(Entity b: obstacle){
										if(la.hitbox.intersects(b.hitbox.getBoundsInLocal())) break loop1;
									}
									p.laserList.add(la);
									root.getChildren().addAll(la.weapon,la.hitbox);
									p.fireNum+=2;
								}
							}else if(p.player.getImage()==(p.turnup)){
						loop1: for(int i=0;i<50;i++){
									Laser la = new Laser(p.getX(),p.getY()+20*(i+1),false);
									for(Entity b: obstacle){
										if(la.hitbox.intersects(b.hitbox.getBoundsInLocal())) break loop1;
									}
									p.laserList.add(la);
									root.getChildren().addAll(la.weapon,la.hitbox);
									p.fireNum+=2;
								}
							}else if(p.player.getImage()==(p.playerImage)){
						loop1: for(int i=0;i<50;i++){
									Laser la = new Laser(p.getX(),p.getY()-20*(i+1),false);
									for(Entity b: obstacle){
										if(la.hitbox.intersects(b.hitbox.getBoundsInLocal())) break loop1;
									}
									p.laserList.add(la);
									root.getChildren().addAll(la.weapon,la.hitbox);
									p.fireNum+=2;
								}
							}
							p.fire = false;
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}else{
						if(p.fireNum!=0){
							while(p.fireNum>0){
								root.getChildren().remove(root.getChildren().size()-1);
								p.laserList.clear();
								p.fireNum--;
								System.out.println(p.fireNum);
								System.out.println(root.getChildren().size());
							}
						}
						
					}
				}
					//robot shoot
					if(r1.getY()%40==0){
						robotShootLoop: for(int i=0;i<50;i++){
							try {
								Laser la = new Laser(r1.getX()+20*(i+1),r1.getY(),true);
								for(Entity b: obstacle){
									if(la.hitbox.intersects(b.hitbox.getBoundsInLocal())) break robotShootLoop;
								}
								r1.laserList.add(la);
								root.getChildren().addAll(la.weapon,la.hitbox);
								r1.fireNum+=2;
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
										
						}
					}else{
						if(r1.fireNum!=0){
							while(r1.fireNum>0){
								root.getChildren().remove(root.getChildren().size()-1);
								r1.laserList.clear();
								r1.fireNum--;
								System.out.println(r1.fireNum);
								System.out.println(root.getChildren().size());
							}
						}
					}
					//robot2 shoot
					if(r2.getY()%40==0){
						robotShootLoop: for(int i=0;i<50;i++){
							try {
								Laser la = new Laser(r2.getX()-30-20*(i+1),r2.getY(),true);
								for(Entity b: obstacle){
									if(la.hitbox.intersects(b.hitbox.getBoundsInLocal())) break robotShootLoop;
								}
								r2.laserList.add(la);
								root.getChildren().addAll(la.weapon,la.hitbox);
								r2.fireNum+=2;
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
										
						}
					}else{
						if(r2.fireNum!=0){
							while(r2.fireNum>0){
								root.getChildren().remove(root.getChildren().size()-1);
								r2.laserList.clear();
								r2.fireNum--;
								System.out.println(r2.fireNum);
								System.out.println(root.getChildren().size());
							}
						}
					}

//laser collide with player and cause inject
				for(Laser la: p.laserList){
					if(!p2.superMode){
						if(la.hitbox.intersects(p2.hitbox.getBoundsInLocal())){
							p2.inject = true;
							p2.superMode = true;
							p2.animation = true;
						}
					}
				}
				for(Laser la: p2.laserList){
					if(!p.superMode){
						if(la.hitbox.intersects(p.hitbox.getBoundsInLocal())){
							p.inject = true;
							p.superMode = true;
							p.animation = true;
						}
					}
				}
				for(Laser la: r1.laserList){
					if(!p.superMode){
						if(la.hitbox.intersects(p.hitbox.getBoundsInLocal())){
							p.inject = true;
							p.superMode = true;
							p.animation = true;
						}
					}
					if(!p2.superMode){
						if(la.hitbox.intersects(p2.hitbox.getBoundsInLocal())){
							p2.inject = true;
							p2.superMode = true;
							p2.animation = true;
						}
					}
				}
				for(Laser la: r2.laserList){
					if(!p.superMode){
						if(la.hitbox.intersects(p.hitbox.getBoundsInLocal())){
							p.inject = true;
							p.superMode = true;
							p.animation = true;
						}
					}
					if(!p2.superMode){
						if(la.hitbox.intersects(p2.hitbox.getBoundsInLocal())){
							p2.inject = true;
							p2.superMode = true;
							p2.animation = true;
						}
					}
				}

				if(p.animation||p2.animation){
					p.animation = false;
					p2.animation = false;
					if(p.inject && p2.inject){
						ft1.setFromValue(0.4);
						ft1.setToValue(1.0);
						ft1.setCycleCount(10);
						ft2.setFromValue(0.4);
						ft2.setToValue(1.0);
						ft2.setCycleCount(10);
						ft1.setOnFinished(e -> {p.superMode = false; });
						ft2.setOnFinished(e -> {p2.superMode = false; });
						ft1.play();
						ft2.play();
						
					}else if(p.inject && !p2.inject){
						ft1.setFromValue(0.4);
						ft1.setToValue(1.0);
						ft1.setCycleCount(10);
						ft1.setOnFinished(e -> {p.superMode = false; });		
						ft1.play();
					}else if(p2.inject && !p.inject){
						ft2.setFromValue(0.4);
						ft2.setToValue(1.0);
						ft2.setCycleCount(10);
						ft2.setOnFinished(e -> {p2.superMode = false;});
						ft2.play();
					}
				}
				
	
	         }
	      };
	      mainloop.start();

	    
	}

}
