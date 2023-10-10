package ru.stanislavski.vladimir;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ChickenAvoid extends JPanel implements KeyListener, ActionListener {
    private Player player = new Player(20, 5, 3, 1, 5, 175, 480, 15);
    private ArrayList<CreatureImpl> enemy = new ArrayList<>();
    private Timer timer;
    private boolean gameOver = false;
    private int score = 0;


    public ChickenAvoid() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(10, this);
        timer.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chicken Avoid");
        ChickenAvoid game = new ChickenAvoid();
        frame.add(game);
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(0, 0, 400, 600);
        BufferedImage imgBackground;
        try {
            imgBackground = ImageIO.read(new File("resources/sprite/background.jpg"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(imgBackground, 0, 0, null);

        BufferedImage imgPlayer;
        try {
            imgPlayer = ImageIO.read(new File("resources/sprite/ship.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(imgPlayer, player.getPositionX(), player.getPositionY(), null);

        for (int i = 0; i < enemy.size(); i++) {
            BufferedImage imgMonster;
            try {
                imgMonster = ImageIO.read(new File("resources/sprite/chicken.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            g.drawImage(imgMonster, enemy.get(i).getPositionX(), enemy.get(i).getPositionY(), null);
        }

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.setColor(Color.white);
        g.drawString("Счет: " + score, 10, 30);
        g.setColor(Color.green);
        g.drawString("Аптечки: " + player.getCountHeal(), 10, 50);
        g.setColor(Color.red);
        g.drawString("Здоровье: " + player.getHitPoints(), 10, 70);

        if (gameOver) {
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.PLAIN, 40));
            g.drawString("Конец игры", 100, 300);
            timer.stop();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            for (int i = 0; i < enemy.size(); i++) {
                CreatureImpl monster = enemy.get(i);
                monster.setPositionY(monster.getPositionY() + monster.getSpeed());
                if (monster.getPositionY() >= 600) {
                    enemy.remove(i);
                    score++;
                }
            }
            repaint();
            if (enemy.isEmpty()) {
                spawnEnemy();
            }
            checkCollision();
        }
    }

    private void checkCollision() {
        Rectangle playerBounds = new Rectangle(player.getPositionX() + 13, player.getPositionY() + 13, 50, 50);

        for (int i = 0; i < enemy.size(); i++) {
            CreatureImpl monster = enemy.get(i);
            Rectangle enemyBounds = new Rectangle(monster.getPositionX() + 22, monster.getPositionY() + 21, 20, 20);
            if (playerBounds.intersects(enemyBounds)) {
                while (player.getHitPoints() > 0 && monster.getHitPoints() > 0) {
                    player.attack(monster);
                    if (monster.getHitPoints() > 0) {
                        monster.attack(player);
                    } else {
                        enemy.remove(i);
                    }
                }
                if (player.getHitPoints() <= 0) {
                    gameOver = true;
                    break;
                }

            }
        }
    }

    private void spawnEnemy() {
        Random random = new Random();
        int numEnemies = random.nextInt(4) + 2;
        for (int i = 0; i < numEnemies; i++) {
            CreatureImpl monster = new Monster(10, 1, 1, 1, 1);
            int x;
            if (!enemy.isEmpty()) {
                x = enemy.get(enemy.size() - 1).getPositionX() + (random.nextInt(100) + 30);
            } else {
                x = random.nextInt(175);
            }
            monster.setPositionX(x);
            monster.setPositionY(0);
            monster.setSpeed(5 + score / 20);
            enemy.add(monster);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (!gameOver) {
            if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) && player.getPositionX() > 0) {
                player.setPositionX(player.getPositionX() - player.getSpeed());
            }
            if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) && player.getPositionX() < 310) {
                player.setPositionX(player.getPositionX() + player.getSpeed());
            }
            if (key == KeyEvent.VK_SPACE) {
                player.heal();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}