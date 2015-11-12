import javax.swing.*;
import java.awt.*;

public class ActionField extends JPanel{


    final boolean COLORDED_MODE = false;

    final int BF_WIDTH = 594;
    final int BF_HEIGHT = 594;
    private Tank tank = new Tank(this);
    private Bullet bullet = tank.getBullet();
    private BattleField bf = tank.bf;

    public ActionField()  throws Exception {


        JFrame frame = new JFrame("BATTLE FIELD, DAY 4");
        frame.setLocation(750, 150);
        frame.setMinimumSize(new Dimension(BF_WIDTH, BF_HEIGHT + 22));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }

    public void runTheGame() throws Exception {
		 tank.clean();
//        tank.randomClean();
//		tank.moveRandom();

    }


    public String getQuadrantXY(int v, int h) {
        return (v - 1) * 64 + "_" + (h - 1) * 64;
    }

    public String getQuadrant(int x, int y) {

        x /= 64;
        y /= 64;

        return y + "_" + x;
    }

    public boolean processInterception() {
        String quad = getQuadrant(bullet.getX(), bullet.getY());
        int quadY = Integer.parseInt(quad.substring(0, quad.indexOf("_")));
        int quadX = Integer.parseInt(quad.substring(quad.indexOf("_") + 1));

        if (quadX >= 0 && quadX < 9 && quadY >= 0 && quadY < 9) {
            if (bf.getBattleField()[quadY][quadX].equals("B")) {
                bf.getBattleField()[quadY][quadX] = (" ");
                return true;
            }
        }
        return false;
    }

    public void processMove(Tank tank, int direction) throws Exception {
        int step = 1;
        int covered = 0;

        // check limits x: 0, 513; y: 0, 513
        if ((direction == 1 && tank.getY() == 0) || (direction == 2 && tank.getY() >= 512)
                || (direction == 3 && tank.getX() == 0)
                || (direction == 4 && tank.getX() >= 512)) {
            System.out.println("[illegal move] direction: " + tank.getTankDirection()
                    + " tankX: " + tank.getX() + ", tankY: " + tank.getY());
            return;
        }

        tank.turn(direction);

        while (covered < 64) {
            if (tank.getTankDirection() == 1) {
                tank.updateY(-step);
                tank.printCoordinates(tank.getTankDirection());
            } else if (tank.getTankDirection() == 2) {
                tank.updateY(step);
                tank.printCoordinates(tank.getTankDirection());
            } else if (tank.getTankDirection() == 3) {
                tank.updateX(-step);
                tank.printCoordinates(tank.getTankDirection());
            } else {
                tank.updateX(step);
                tank.printCoordinates(tank.getTankDirection());
            }
            covered += step;

            repaint();
            Thread.sleep(tank.getSpeed());
        }
    }

    public void processTurn(Tank tank, int direction){
        tank.setTankDirection(direction);

    }

    public void processFire(Bullet bullet) throws Exception {
        int step = 1;

        bullet.setX(tank.getX() + 25);
        bullet.setY(tank.getY() + 25);
        while ((bullet.getX() > -14 && bullet.getX() <= 590)
                && (bullet.getY() > -14 && bullet.getY() <= 590)) {
            if (processInterception()) {
                bullet.setX(-100);
                bullet.setY(-100);
            } else if (tank.getTankDirection() == 1) {
                bullet.updateY(-step);
            } else if (tank.getTankDirection() == 2) {
                bullet.updateY(step);
            } else if (tank.getTankDirection() == 3) {
                bullet.updateX(-step);
            } else {
                bullet.updateX(step);
            }
            repaint();
            Thread.sleep(bullet.getSpeed());

        }
    }

    public void processMoveToQuadrant(Tank tank, int v, int h) throws Exception {
        String coordinates = getQuadrantXY(v, h);
        int y = Integer.parseInt(coordinates.substring(0,
                coordinates.indexOf("_")));
        int x = Integer
                .parseInt(coordinates.substring(coordinates.indexOf("_") + 1));

        if (tank.getX() < x) {
            while (tank.getX() != x) {
                tank.move(4);
            }
        } else {
            while (tank.getX() != x) {
                tank.move(3);
            }
        }

        if (tank.getY() < y) {
            while (getY() != y) {
                tank.move(2);
            }
        } else {
            while (tank.getY() != y) {
                tank.move(1);
            }
        }
    }

    public void processMoveRandom(Tank tank) throws Exception {
        while (true) {
            tank.move(tank.random());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int i = 0;
        Color cc;
        for (int v = 0; v < 9; v++) {
            for (int h = 0; h < 9; h++) {
                if (COLORDED_MODE) {
                    if (i % 2 == 0) {
                        cc = new Color(252, 241, 177);
                    } else {
                        cc = new Color(233, 243, 255);
                    }
                } else {
                    cc = new Color(180, 180, 180);
                }
                i++;
                g.setColor(cc);
                g.fillRect(h * 64, v * 64, 64, 64);
            }
        }

        for (int j = 0; j < bf.getBattleField().length; j++) {
            for (int k = 0; k < bf.getBattleField().length; k++) {
                if (bf.getBattleField()[j][k].equals("B")) {
                    String coordinates = getQuadrantXY(j + 1, k + 1);
                    int separator = coordinates.indexOf("_");
                    int y = Integer.parseInt(coordinates
                            .substring(0, separator));
                    int x = Integer.parseInt(coordinates
                            .substring(separator + 1));
                    g.setColor(new Color(0, 0, 255));
                    g.fillRect(x, y, 64, 64);
                }
            }
        }

        g.setColor(new Color(255, 0, 0));
        g.fillRect(tank.getX(), tank.getY(), 64, 64);

        g.setColor(new Color(0, 255, 0));
        if (tank.getTankDirection() == 1) {
            g.fillRect(tank.getX() + 20, tank.getY(), 24, 34);
        } else if (tank.getTankDirection() == 2) {
            g.fillRect(tank.getX() + 20, tank.getY() + 30, 24, 34);
        } else if (tank.getTankDirection() == 3) {
            g.fillRect(tank.getX(), tank.getY() + 20, 34, 24);
        } else {
            g.fillRect(tank.getX() + 30, tank.getY() + 20, 34, 24);
        }

        g.setColor(new Color(255, 255, 0));
        g.fillRect(bullet.getX(), bullet.getY(), 14, 14);
    }


}
