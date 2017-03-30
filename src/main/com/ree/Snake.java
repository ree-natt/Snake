package main.com.ree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ree-natt on 29.03.17.
 */
public class Snake {
    private List<SnakeSection> sections;
    private boolean isAlive;
    private SnakeDirection direction;

    public Snake (int x, int y) {
        sections = new ArrayList<SnakeSection>();
        sections.add(new SnakeSection(x, y));
        isAlive = true;
    }

    public int getX() {
        return sections.get(0).getX();
    }

    public int getY() {
        return sections.get(0).getY();
    }

    public void move(int dx, int dy){
        SnakeSection head = sections.get(0);
        head = new SnakeSection(head.getX() + dx, head.getY() + dy);
        checkBorders(head);
        if (!isAlive) return;

        checkBody(head);
        if (!isAlive) return;

        Mouse mouse = Room.game.getMouse();
        if (head.getX() == mouse.getX() && head.getY() == mouse.getY()) {
            sections.add(0, head);
            Room.game.eatMouse();
        } else {
            sections.add(0, head);
            sections.remove(sections.size() - 1);
        }
    }

    private void checkBody(SnakeSection head) {
        if (sections.contains(head)) {
            isAlive = false;
        }
    }

    private void checkBorders(SnakeSection head) {
        if ((head.getX() < 0 || head.getX() >= Room.game.getWidth() || head.getY() < 0 || head.getY() >= Room.game.getHeight())) {
            isAlive = false;
        }
    }

    public List<SnakeSection> getSections() {
        return sections;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }
}
