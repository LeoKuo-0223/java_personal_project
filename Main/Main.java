import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import map.*;
import peopleEntity.*;
import weapon.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

// Main class for the JavaFX application
public class Main extends Application {
	private static final Integer STARTTIME = 60;
	private static final double PLAYER_INITIAL_X = 650;
    private static final double PLAYER_INITIAL_Y = 880;
    private static final double PLAYER2_INITIAL_X = 1470;
    private static final double PLAYER2_INITIAL_Y = 100;
	private static final int MAP_X = 500;
    private static final int MAP_Y = 10;
	
	private Timeline timeline;
	private Label timerLabel = new Label();
	private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);
	private boolean pause = false;
	private Rectangle button ;
	private Rectangle resetbutton ;
	private StackPane buttonstack;
	private StackPane resetbuttonstack;
	private Text buttonText;
	private Text resetbuttonText ;
	private boolean gameOver;
	private Robot animeRobot;
	private int mapRaw_count = 0;
	public static Player p;
	public static Player p2;
	public static Robot1 r1;
	public static Robot2 r2;
	public Path path = new Path();
	public List<Entity> player = new ArrayList<>();
	public List<Piece> map = new ArrayList<>();
	public List<Entity> obstacle = new ArrayList<>();
	public ImageView startGame;
	public ImageView dead;
	public ImageView gameover;
	public ImageView timeup;
	public double p_x = PLAYER_INITIAL_X;
    public double p_y = PLAYER_INITIAL_Y;
    public double p2_x = PLAYER2_INITIAL_X;
    public double p2_y = PLAYER2_INITIAL_Y;
	
	private Group root;
	private Scene scene;
	private VBox vb;
	private AnimationTimer mainloop;

	// Constructor to initialize images, players, and UI components
	public Main() throws FileNotFoundException {
		initializeImages();
		initializePlayers();
		initializeUIControls();
		
		// Initialize root, scene, and vb
		root = new Group();
		scene = new Scene(root, 1920, 1080);
		vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		vb.setPrefWidth(150);
		vb.setLayoutX(112.5);
		vb.setLayoutY(80);
		vb.setSpacing(100);
	}

	// Method to initialize images
	private void initializeImages() throws FileNotFoundException {
		startGame = new ImageView(new Image(new FileInputStream("picture/ZS3t.gif")));
		startGame.setX(550);
		startGame.setY(400);
		startGame.setFitWidth(480);
		startGame.setFitHeight(320);
		startGame.setVisible(true);

		dead = new ImageView(new Image(new FileInputStream("picture/zYj.gif")));
		dead.setX(550);
		dead.setY(400);
		dead.setFitWidth(480);
		dead.setFitHeight(320);
		dead.setVisible(false);

		timeup = new ImageView(new Image(new FileInputStream("picture/A8Bf-unscreen.gif")));
		timeup.setX(550);
		timeup.setY(400);
		timeup.setFitWidth(400);
		timeup.setFitHeight(400);
		timeup.setVisible(false);

		gameover = new ImageView(new Image(new FileInputStream("picture/QUC8-unscreen.gif")));
		gameover.setX(550);
		gameover.setY(400);
		gameover.setFitWidth(400);
		gameover.setFitHeight(400);
		gameover.setVisible(false);
	}

	// Method to initialize players
	private void initializePlayers() throws FileNotFoundException {
		animeRobot = new Robot();
		gameOver = false;
		p = new Player(p_x, p_y, 1);
		p2 = new Player(p2_x, p2_y, 2);
		r1 = new Robot1(520, 870);
		r2 = new Robot2(1590, 80);
	}

	// Method to initialize UI controls
	private void initializeUIControls() {
		timeline = new Timeline();
		button = new Rectangle();
		resetbutton = new Rectangle();
		timeSeconds.set(STARTTIME);
		buttonstack = new StackPane();
		resetbuttonstack = new StackPane();
		buttonText = new Text("Start");

		buttonText.setFill(Color.RED);
		buttonText.setDisable(true);
		buttonText.setFont(Font.font(null, FontWeight.BOLD, 30));

		resetbuttonText = new Text("Play Again");
		resetbuttonText.setFill(Color.RED);
		resetbuttonText.setFont(Font.font(null, FontWeight.BOLD, 20));
		resetbuttonText.setDisable(true);
		resetbuttonText.setOpacity(0.4);

		button.setHeight(60);
		button.setWidth(150);
		button.setFill(Color.GOLD);
		button.setArcWidth(30);
		button.setArcHeight(30);

		resetbutton.setHeight(60);
		resetbutton.setWidth(150);
		resetbutton.setFill(Color.PALEGREEN);
		resetbutton.setArcWidth(30);
		resetbutton.setArcHeight(30);
		resetbutton.setOpacity(0.4);
		resetbutton.setDisable(true);

		buttonstack.setAlignment(Pos.CENTER);
		buttonstack.setPrefWidth(100);
		buttonstack.setLayoutX(112.5);
		buttonstack.setLayoutY(700);
		buttonstack.getChildren().addAll(button, buttonText);

		resetbuttonstack.setAlignment(Pos.CENTER);
		resetbuttonstack.setPrefWidth(150);
		resetbuttonstack.setLayoutX(112.5);
		resetbuttonstack.setLayoutY(800);
		resetbuttonstack.getChildren().addAll(resetbutton, resetbuttonText);

		timerLabel.textProperty().bind(timeSeconds.asString());
		timerLabel.setTextFill(Color.BLACK);
		timerLabel.setFont(Font.font(50));
	}

	// New method to build the map
	private void buildMap(Group root) throws FileNotFoundException {
		for(String col: Map.map1){
			for(int i=0;i<col.length();i++){
				if(col.charAt(i)=='1'){
					Bound bound = new Bound(MAP_X +80*i, MAP_Y+80*mapRaw_count);
					obstacle.add(bound);
					root.getChildren().addAll(bound.bound,bound.hitbox);
				}else if(col.charAt(i)=='0'){
					Piece pieces = new Piece(MAP_X +80*i, MAP_Y+80*mapRaw_count);
					map.add(pieces);
					root.getChildren().addAll(pieces.ground,pieces.block);
				}else if(col.charAt(i)=='2'){
					Obstacle1 obs = new Obstacle1(MAP_X +80*i, MAP_Y+80*mapRaw_count);
					obstacle.add(obs);
					Piece pieces = new Piece(MAP_X +80*i, MAP_Y+80*mapRaw_count);
					map.add(pieces);
					root.getChildren().addAll(pieces.ground,pieces.block);
					root.getChildren().addAll(obs.obstacle,obs.hitbox);
				}else if(col.charAt(i)=='3'){
					obstacle_wood obs = new obstacle_wood(MAP_X +80*i, MAP_Y+80*mapRaw_count);
					obstacle.add(obs);
					Piece pieces = new Piece(MAP_X +80*i, MAP_Y+80*mapRaw_count);
					map.add(pieces);
					root.getChildren().addAll(pieces.ground,pieces.block);
					root.getChildren().addAll(obs.obstacle,obs.hitbox);
				}
			}
			mapRaw_count++;
		}
	}

	// Method to initialize the main animation loop
	private void initializeMainLoop(Stage stage) {
		mainloop = new AnimationTimer() {
			@Override
			public void handle(long t) {
				Entity.setScreenSize(stage.getWidth(), stage.getHeight());
				map.forEach(m -> m.act());
				obstacle.forEach(o -> {
					try {
						o.act();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				});
				player.forEach(p -> {
					try {
						p.act();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				});
				FadeTransition ft1 = new FadeTransition(Duration.seconds(0.1), p.player);
				FadeTransition ft2 = new FadeTransition(Duration.seconds(0.1), p2.player);
				FadeTransition ftdead = new FadeTransition(Duration.seconds(0.2), dead);
				ftdead.setFromValue(0.4);
				ftdead.setToValue(1.0);
				ftdead.setCycleCount(4);

				//collide with bound
				for(Entity p: player){

					if(p.getMy()>0){
						for(Entity b: obstacle ){
							if(p.hitbox.intersects(b.hitbox.getBoundsInLocal())){
								// System.out.println("hit up");
								p.collidev = 1;
								p.setPos(p.getX(), p.getY()-10);
							}
						}
					}else if(p.getMy()<0){
						for(Entity b: obstacle ){
							if(p.hitbox.intersects(b.hitbox.getBoundsInLocal())){
								// System.out.println("hit down");
								p.collidev = 2;
								p.setPos(p.getX(), p.getY()+10);
							}
						}
					}else if(p.getMx()>0){
						for(Entity b: obstacle) {
							
								if(p.hitbox.intersects(b.hitbox.getBoundsInLocal())){
									// System.out.println("hit right");
									p.collideh = 1;
									p.setPos(p.getX()-10, p.getY());
								}
							
						}
					}else if(p.getMx()<0){
						for(Entity b: obstacle ){
							if(p.hitbox.intersects(b.hitbox.getBoundsInLocal())){
								// System.out.println("hit left");
								p.collideh = 2;
								p.setPos(p.getX()+10, p.getY());
							}
						}
					}
					//shoot
					if(p.fire){
						try {
							// System.out.println("shoot");
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
								// System.out.println(p.fireNum);
								// System.out.println(root.getChildren().size());
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
								// System.out.println(r1.fireNum);
								// System.out.println(root.getChildren().size());
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
								// System.out.println(r2.fireNum);
								// System.out.println(root.getChildren().size());
							}
						}
					}

				//laser collide with player and cause inject
				for(Laser la: p.laserList){
					if(!p2.superMode){
						if(la.hitbox.intersects(p2.hitbox.getBoundsInLocal())){
							dead.setVisible(true);
							ftdead.setOnFinished(e->{
								dead.setVisible(false);
							});
							ftdead.play();
							p2.inject = true;
							p2.superMode = true;
							p2.animation = true;
							p.point++;
							p.pointText.setText(Integer.toString(p.point)+"/10");
							if(p.point == 10) gameOver = true;
						}
					}
				}
				for(Laser la: p2.laserList){
					if(!p.superMode){
						if(la.hitbox.intersects(p.hitbox.getBoundsInLocal())){
							dead.setVisible(true);
							ftdead.setOnFinished(e->{
								dead.setVisible(false);
							});
							ftdead.play();
							p.inject = true;
							p.superMode = true;
							p.animation = true;
							p2.point++;
							p2.pointText.setText(Integer.toString(p2.point)+"/10");
							if(p2.point == 10) gameOver = true;
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
				
				// Handle game over condition
				if(gameOver){
					animeRobot.keyPress(KeyCode.F11);
				}

				// Handle player injections and super mode
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
	}

	// Method to initialize UI elements
	private void initializeUIElements() {
		player.add(p);
		player.add(p2);
		player.add(r1);
		player.add(r2);
		player.forEach(p -> root.getChildren().addAll(p.player, p.hitbox));
		vb.getChildren().addAll(p.faceBase, p2.faceBase, timerLabel);
		root.getChildren().addAll(vb, buttonstack, resetbuttonstack);
		root.getChildren().addAll(p.pointText, p.face, p2.pointText, p2.face);
		root.getChildren().add(startGame);
		root.getChildren().add(dead);
		root.getChildren().add(timeup);
		root.getChildren().add(gameover);
	}

	// Method to set up the start button
	private void setupStartButton() {
		button.setOnMouseClicked(event -> {
			if (pause) {
				mainloop.stop();
				timeline.stop();
				buttonText.setText("Start");
			} else {
				mainloop.start();
				buttonText.setText("Pause");
				startGame.setVisible(false);
				timeline.getKeyFrames().add(
						new KeyFrame(Duration.seconds(STARTTIME + 1),
								new KeyValue(timeSeconds, 0)));
				timeline.setOnFinished(e -> {
					resetbutton.setDisable(false);
					button.setDisable(true);
					button.setOpacity(0.2);
					buttonText.setOpacity(0.2);
					resetbutton.setOpacity(1);
					resetbuttonText.setOpacity(1);
					timeup.setVisible(true);
					mainloop.stop();

				});
				timeline.play();
			}
			pause = !pause;
		});
	}

	// Method to set up the reset button
	private void setupResetButton() {
		resetbutton.setOnMouseClicked(event -> {
			p.setPos(p_x, p_y);
			p2.setPos(p2_x, p2_y);
			p.point = 0;
			p2.point = 0;
			p.pointText.setText(Integer.toString(p.point) + "/10");
			p2.pointText.setText(Integer.toString(p2.point) + "/10");
			p.inject = false;
			p2.inject = false;

			//reset clock
			timeSeconds.set(STARTTIME);
			gameover.setVisible(false);
			timeup.setVisible(false);
			button.setDisable(false);
			resetbutton.setDisable(true);
			button.setOpacity(1);
			buttonText.setOpacity(1);
			resetbutton.setOpacity(0.2);
			resetbuttonText.setOpacity(0.2);
			buttonText.setText("Start");

			pause = !pause;
		});
	}

	// Method to initialize objects on the scene
	private void initializeSceneObjects(Stage stage) {
		Entity.setScreenSize(stage.getWidth(), stage.getHeight());
		map.forEach(m -> m.act());
		obstacle.forEach(o -> {
			try {
				o.act();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		});
		player.forEach(p -> {
			try {
				p.act();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		});
	}

	// Method to set up keyboard controls for players
	private void setupKeyboardControls() {
		scene.setOnKeyPressed(ke -> {
			if (ke.getCode() == KeyCode.LEFT) p.Leftpress = true;
			else if (ke.getCode() == KeyCode.RIGHT) p.Rightpress = true;
			else if (ke.getCode() == KeyCode.UP) p.Up = true;
			else if (ke.getCode() == KeyCode.DOWN) p.Down = true;
			else if (ke.getCode() == KeyCode.ENTER) {
				if (!p.alreayFired) {
					p.fire = true;
					p.alreayFired = true;
				} else if (ke.getCode() == KeyCode.A) p2.Leftpress = true;
				else if (ke.getCode() == KeyCode.D) p2.Rightpress = true;
				else if (ke.getCode() == KeyCode.W) p2.Up = true;
				else if (ke.getCode() == KeyCode.S) p2.Down = true;
				else if (ke.getCode() == KeyCode.SPACE) {
					if (!p2.alreayFired) {
						p2.fire = true;
						p2.alreayFired = true;
					}
				} else if (ke.getCode() == KeyCode.F11) {
					if (gameOver) {
						mainloop.stop();

						//reset clock
						timeline.stop();
						button.setDisable(true);
						resetbutton.setDisable(false);
						button.setOpacity(0.4);
						buttonText.setOpacity(0.4);
						resetbutton.setOpacity(1);
						resetbuttonText.setOpacity(1);
						gameOver = false;
						gameover.setVisible(true);
						pause = !pause;
					}
				}
			}
		});
		scene.setOnKeyReleased(ke -> {
			if (ke.getCode() == KeyCode.LEFT) {
				p.Leftpress = false;
				if (p.Motion[0] < 0) {
					p.Motion[0] = 0;
				}
			} else if (ke.getCode() == KeyCode.RIGHT) {
				p.Rightpress = false;
				if (p.Motion[0] > 0) {
					p.Motion[0] = 0;
				}
			} else if (ke.getCode() == KeyCode.UP) {
				p.Up = false;
				if (p.Motion[1] > 0) {
					p.Motion[1] = 0;
				}
			} else if (ke.getCode() == KeyCode.DOWN) {
				p.Down = false;
				if (p.Motion[1] < 0) {
					p.Motion[1] = 0;
				}
			} else if (ke.getCode() == KeyCode.ENTER) {
				p.fire = false;
				p.alreayFired = false;
			} else if (ke.getCode() == KeyCode.A) {
				p2.Leftpress = false;
				if (p2.Motion[0] < 0) {
					p2.Motion[0] = 0;
				}
			} else if (ke.getCode() == KeyCode.D) {
				p2.Rightpress = false;
				if (p2.Motion[0] > 0) {
					p2.Motion[0] = 0;
				}
			} else if (ke.getCode() == KeyCode.W) {
				p2.Up = false;
				if (p2.Motion[1] > 0) {
					p2.Motion[1] = 0;
				}
			} else if (ke.getCode() == KeyCode.S) {
				p2.Down = false;
				if (p2.Motion[1] < 0) {
					p2.Motion[1] = 0;
				}
			} else if (ke.getCode() == KeyCode.SPACE) {
				p2.fire = false;
				p2.alreayFired = false;
			}
		});
	}

	// Start method to set up the stage and scene
	@Override
	public void start(Stage stage) throws Exception {
		stage.setFullScreen(true);
		stage.setTitle("test");
		stage.setScene(scene);
		stage.show();

		//build map
		buildMap(root);
		
		// Initialize UI elements
		initializeUIElements();
		
		// Initialize main loop
		initializeMainLoop(stage);

		// Initialize objects on the scene
		initializeSceneObjects(stage);

		// Set up start button to control the animation function
		setupStartButton();

		// Set up reset button to reset the game state
		setupResetButton();

		// Set up keyboard controls for players
		setupKeyboardControls();
	}
	// Main method to launch the application
	public static void main(String args[]) throws FileNotFoundException {
		launch(args);
	}
}