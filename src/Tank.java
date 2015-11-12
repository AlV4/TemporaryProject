public class Tank {
    private ActionField af;
    public BattleField bf;
    private Bullet bullet;
    // 1 - top, 2 - bottom, 3 - left, 4 - right
    private int tankDirection = 1;

    private int X = 4 * 64;
    private int Y = 5 * 64;

    private int speed = 5;

    public Tank(ActionField af) {
        this.af = af;
        this.bullet = new Bullet();
        this.bf = new BattleField();
    }

    public Bullet getBullet() {
        return bullet;
    }

    void randomClean() throws Exception {
        while (battleFieldScanner() != 0) {
            tankDirection = random();
            cleanMove(tankDirection);
            move(tankDirection);
            cleanMove(tankDirection);
        }
        if (battleFieldScanner() == 0) {
            System.out
                    .println("**************************************************"
                            + "BRICKS DESTROYED!*************************************************************");
        }
    }

    void clean() throws Exception {

        while (battleFieldScanner() != 0) {
            cleanPoint();

            if (battleFieldScanner() == 0) {
                System.out
                        .println("**************************************************"
                                + "BRICKS DESTROYED!*************************************************************");
                break;
            }
            moveToQuadrant(getY() / 64 + 1, battleFieldScanner());

        }
    }

    void cleanMove(int direction) throws Exception {

        if (lineScanner(direction)) {
            fire();
        }
    }

    void cleanLine() throws Exception {

        while (lineScanner(tankDirection)) {
            fire();

        }
        return;
    }

    void cleanPoint() throws Exception {

        for (int i = 1; i < 5; i++) {
            turn(i);
            while (lineScanner(tankDirection)) {
                fire();
            }
        }
        return;
    }

    int battleFieldScanner() {
        for (int fieldY = 0; fieldY < bf.getBattleField().length; fieldY++) {
            for (int fieldX = 0; fieldX < bf.getBattleField().length; fieldX++)
                if (bf.getBattleField()[fieldY][fieldX].equals("B")) {
                    return fieldX + 1;
                }
        }
        return 0;
    }

    boolean lineScanner(int direction) {
        String checkQuad = af.getQuadrant(getX(), getY());
        int checkY = Integer.parseInt(checkQuad.substring(0,
                checkQuad.indexOf("_")));
        int checkX = Integer.parseInt(checkQuad.substring(checkQuad
                .indexOf("_") + 1));

        if (checkX >= 0 && checkX < 9 && checkY >= 0 && checkY < 9) {
            if (checkDirection(direction, checkX, checkY)) {
                return true;
            }
        }
        return false;

    }

    boolean checkDirection(int direction, int checkX, int checkY) {
        if ((direction == 1 && checkUp(checkX, checkY))
                || (direction == 2 && checkDown(checkX, checkY))
                || (direction == 3 && checkLeft(checkX, checkY))
                || (direction == 4 && checkRight(checkX, checkY))) {
            return true;
        }
        return false;

    }

    boolean checkUp(int checkX, int checkY) {
        for (; checkY >= 0; ) {
            if (bf.getBattleField()[checkY--][checkX].equals("B")) {
                return true;
            }
        }
        return false;
    }

    boolean checkDown(int checkX, int checkY) {
        for (; checkY < 9; ) {
            if (bf.getBattleField()[checkY++][checkX].equals("B")) {
                return true;
            }
        }
        return false;
    }

    boolean checkLeft(int checkX, int checkY) {
        for (; checkX >= 0; ) {
            if (bf.getBattleField()[checkY][checkX--].equals("B")) {
                return true;
            }
        }
        return false;
    }

    boolean checkRight(int checkX, int checkY) {
        for (; checkX < 9; ) {
            if (bf.getBattleField()[checkY][checkX++].equals("B")) {
                return true;
            }
        }
        return false;
    }

    void fire() throws Exception {

        af.processFire(bullet);
    }

    void move(int direction) throws Exception {
       af.processMove(this, direction);
    }

    void printCoordinates(int direction) {
        String dir = "";
        if (direction == 1) {
            dir = "up";
        } else if (direction == 2) {
            dir = "down";
        } else if (direction == 3) {
            dir = "left";
        } else {
            dir = "right";
        }
        System.out.println("[move " + dir + "] direction: " + direction
                + " tankX: " + getX() + ", tankY: " + getY());
    }

    void turn(int direction) {
        af.processTurn(this, direction);
    }

    void moveRandom() throws Exception {
        af.processMoveRandom(this);
    }

    int random() {
        int generator = abs(System.nanoTime() % 4);
        if (generator == 0) {
            return 4;
        } else
            return generator;
    }

    int abs(long l) {
        return (int) l;
    }

    public void moveToQuadrant(int v, int h) throws Exception {
       af.processMoveToQuadrant(this, v, h);
    }

    public int getTankDirection() {
        return tankDirection;
    }

    public void setTankDirection(int tankDirection) {
        this.tankDirection = tankDirection;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void updateX(int step) {
        this.X += step;
    }

    public void updateY(int step) {
        this.Y += step;
    }
}
