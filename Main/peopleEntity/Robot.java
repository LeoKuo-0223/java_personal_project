package peopleEntity;

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Robot extends Entity {
    public boolean turnaround;
    private final int direction;

    public Robot(double x, double y, String imagePath, int direction) throws FileNotFoundException {
        playerImage = new Image(new FileInputStream(imagePath));
        player = new ImageView(playerImage);
        player.setSmooth(true);
        hitbox = new Rectangle();
        turnaround = false;
        setSize(100, 128);
        setPos(x, y);
        this.direction = direction;
    }

    @Override
    public void setPos(double x, double y) {
        Pos[0] = x;
        Pos[1] = y;
        player.setFitWidth(Width * ratio[0]);
        player.setFitHeight(Height * ratio[1]);
        player.setX((Pos[0] - Width / 2) * ratio[0]);
        player.setY((1080 - Pos[1] - Height) * ratio[1]);
    }

    @Override
    public void act() {
        if (direction == 1) {
            if (getY() >= 80 && !turnaround) {
                setPos(getX(), getY() - 1);
            } else {
                turnaround = true;
            }

            if (turnaround) {
                if (getY() <= 870) {
                    setPos(getX(), getY() + 1);
                } else {
                    turnaround = false;
                }
            }
        } else if (direction == 2) {
            if (getY() <= 870 && !turnaround) {
                setPos(getX(), getY() + 1);
            } else {
                turnaround = true;
            }

            if (turnaround) {
                if (getY() >= 80) {
                    setPos(getX(), getY() - 1);
                } else {
                    turnaround = false;
                }
            }
        }
    }
}
