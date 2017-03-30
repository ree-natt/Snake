package main.com.ree;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by ree-natt on 29.03.17.
 */
public class Room {
    private int width;
    private int height;
    private Snake snake;
    private Mouse mouse;
    static Room game;

    public Room(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
    }

    public static void main (String[] args) {
        game = new Room(20, 20, new Snake(5,5));
        game.snake.setDirection(SnakeDirection.DOWN);
        game.createMouse();
        game.run();

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public void run() {
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        while (snake.isAlive()) {
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();

                if (event.getKeyChar() == 'q') return;

                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    snake.setDirection(SnakeDirection.LEFT);

                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    snake.setDirection(SnakeDirection.RIGHT);

                else if (event.getKeyCode() == KeyEvent.VK_UP)
                    snake.setDirection(SnakeDirection.UP);

                else if (event.getKeyCode() == KeyEvent.VK_DOWN)
                    snake.setDirection(SnakeDirection.DOWN);
            }
            snake.move(1, 1);
            print();
            sleep();
        }
        System.out.println("Game Over!");
    }

    public void print() {
        int[][] matrix = new int[height][width];
        ArrayList<SnakeSection> sections = new ArrayList<SnakeSection>(snake.getSections());
        for (SnakeSection snakeSection : sections) {
            matrix[snakeSection.getY()][snakeSection.getX()] = 1;
        }
        //Рисуем голову змеи (4 - если змея мертвая)
        matrix[snake.getY()][snake.getX()] = snake.isAlive() ? 2 : 4;
        //Рисуем мышь
        matrix[mouse.getY()][mouse.getX()] = 3;

        String[] symbols = {" . ", " x ", " X ", "^_^", "RIP"};
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(symbols[matrix[y][x]]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public void createMouse() {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);
        mouse = new Mouse(x, y);
    }

    public void eatMouse() {
        createMouse();
    }

    private static int[] leveDelay = {1000, 600, 550, 500, 480, 460, 440, 420, 400, 380, 360, 340, 320, 300, 285, 270};
    public void sleep() {
        try{
            int level = snake.getSections().size();
            int delay = level < 15 ? leveDelay[level] : 250;
            Thread.sleep(delay);
        } catch (InterruptedException e) {

        }

    }

}
