
public class Bullet {
    private int x;
    private int y;
    private int speed = 2;
    private int direction;

    public Bullet() {
        setX(-100);
        setY(-100);
    }
    public void destroy(){
        setX(-100);
        setY(-100);
    }

    public void updateX(int step){
        this.x += step;
    }
    public void updateY(int step){
        this.y += step;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
