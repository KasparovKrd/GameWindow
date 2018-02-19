import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class CatGame extends JFrame {

    private static CatGame game_window;
    private static long last_frame;
    private static Image background;
    private static Image cat;
    private static Image button;
    private static Image good;
    private static Image plate;
    private static Image game_over;
    private static Image game_over2;
    private static float speed = 20;



    public static void main(String[] args){
        try {
            background = ImageIO.read(CatGame.class.getResourceAsStream("back.jpg"));
            cat = ImageIO.read(CatGame.class.getResourceAsStream("bad.png"));
            button = ImageIO.read(CatGame.class.getResourceAsStream("button.png"));
            good = ImageIO.read(CatGame.class.getResourceAsStream("good.png"));
            game_over = ImageIO.read(CatGame.class.getResourceAsStream("game_over.png"));
            game_over2 = ImageIO.read(CatGame.class.getResourceAsStream("game_over2.png"));
            plate = ImageIO.read(CatGame.class.getResourceAsStream("plate1.png"));
        } catch (IOException exc){
            System.out.println("Неверное расплоложение фаилов");
        }

        Cat cat = new Cat("Barsik", 50);
        Plate plate = new Plate(300);


        game_window = new CatGame();
        game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_window.setLocation(100,100);
        game_window.setSize(1200, 600);
        game_window.setResizable (false);
        last_frame = System.nanoTime();
        GameField game_field = new GameField();
        game_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int x = e.getX();
                int y = e.getY();
                boolean is_click = y >=100 && y <= 200 && x >= 230 && x <= 484;
                if (is_click){
                    cat.eat(plate);
                }
            }
        });
        game_window.add(game_field);
        game_window.setVisible(true);
    }


    private static void Render(Graphics g) {
        long current_time = System.nanoTime();
        float delta_time =(current_time - last_frame)* 0.0000000010f;
        last_frame = current_time;
        Cat.cat_live = Cat.cat_live - speed*delta_time ;
        g.drawImage(background, 0, 0, null);
        g.drawImage(plate, 200, 180, null);
        g.drawImage(button, 230,100, null);
        if (Cat.cat_live>= 100) {
            g.drawImage(good, 770, 100, null);
            g.setColor(new Color(120, 255, 70));
            g.fillRect(800, 500, (int) Cat.cat_live, 30);
        } else if(Cat.cat_live>= 0) {
            g.drawImage(cat, 670, 0, null);
            g.setColor(new Color(255, 95, 90));
            g.fillRect(800, 500, (int) Cat.cat_live, 30);
        }

        if (Plate.food>= 100) {
            g.setColor(new Color(120, 255, 70));
            g.fillRect(200, 500, (int) Plate.food, 30);
        } else if(Plate.food>= 0) {
            g.setColor(new Color(255, 95, 90));
            g.fillRect(200, 500, (int) Plate.food, 30);
        }
    }

    private static class GameField extends JPanel{
        @Override
        protected void paintComponent (Graphics g){
            super.paintComponent(g);
            Render(g);
            repaint();
            if (Cat.cat_live<-1){
                g.drawImage(background, 0, 0, null);
                g.drawImage(game_over, 100,30, null);               //Game over готов!!!
                g.drawImage(game_over2, 620,100, null);
            }
        }

    }
}

class Cat {
    private String name;
    private int appetite =  50;
    static float cat_live = 350;
    int h = (int)cat_live;

    Cat(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
    }

    Cat(float cat_live) {
        this.cat_live = cat_live;
    }

    void eat(Plate plate) {
        if(Plate.food>0 && cat_live <= 300){
        plate.dicreaseFood(appetite);
        cat_live = cat_live + appetite;
        }
    }
}

class Plate {
    static int food = 200;

    Plate(int food) {
        this.food = food;
    }

    void dicreaseFood(int food) {
        this.food -= food;
    }
}