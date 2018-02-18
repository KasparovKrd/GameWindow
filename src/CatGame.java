import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class CatGame extends JFrame {

    private static CatGame game_window;
    private static long last_frame;
    private static Image background;
    private static Image cat;
    private static Image food;
    private static Image good;
    private static Image game_over;
    private static Image game_over2;
    private static float cat_live = 350;
    private static float speed = 20;



    public static void main(String[] args){
        try {
            background = ImageIO.read(CatGame.class.getResourceAsStream("back.jpg"));
            cat = ImageIO.read(CatGame.class.getResourceAsStream("bad.png"));
            food = ImageIO.read(CatGame.class.getResourceAsStream("food.png"));
            good = ImageIO.read(CatGame.class.getResourceAsStream("good.png"));
            game_over = ImageIO.read(CatGame.class.getResourceAsStream("game_over.png"));
            game_over2 = ImageIO.read(CatGame.class.getResourceAsStream("game_over2.png"));
        } catch (IOException exc){
            System.out.println("Неверное расплоложение фаилов");
        }

        game_window = new CatGame();
        game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_window.setLocation(100,100);
        game_window.setSize(1200, 600);
        game_window.setResizable (false);
        last_frame = System.nanoTime();
        GameField game_field = new GameField();
        game_window.add(game_field);
        game_window.setVisible(true);
    }


    private static void Render(Graphics g) {
        long current_time = System.nanoTime();
        float delta_time = (current_time - last_frame)* 0.000000001f;
        last_frame = current_time;
        cat_live = cat_live - speed*delta_time;
        g.drawImage(background, 0, 0, null);
        g.drawImage(food, 200,0, null);
        if (cat_live>= 100) {
            g.drawImage(good, 770, 110, null);
            g.setColor(new Color(120, 255, 70));
            g.fillRect(800, 500, (int) cat_live, 30);
        } else if(cat_live>= 0) {
            g.drawImage(cat, 670, 0, null);
            g.setColor(new Color(255, 95, 90));
            g.fillRect(800, 500, (int) cat_live, 30);
        }
    }

    private static class GameField extends JPanel{
        @Override
        protected void paintComponent (Graphics g){
            super.paintComponent(g);
            Render(g);
            repaint();
            if (cat_live<-1){
                g.drawImage(background, 0, 0, null);
                g.drawImage(game_over, 100,30, null);               //Game over готов!!!
                g.drawImage(game_over2, 620,100, null);
            }
        }

    }
}
