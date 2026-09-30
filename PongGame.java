import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PongGame extends JPanel implements ActionListener, KeyListener {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;

    private static final int PADDLE_WIDTH = 15;
    private static final int PADDLE_HEIGHT = 100;
    private static final int BALL_SIZE = 20;

    // Player paddle
    private int playerY = HEIGHT / 2 - PADDLE_HEIGHT / 2;

    // Computer paddle
    private int computerY = HEIGHT / 2 - PADDLE_HEIGHT / 2;

    // Ball
    private int ballX = WIDTH / 2;
    private int ballY = HEIGHT / 2;

    private int ballDX = 5;
    private int ballDY = 5;

    // Score
    private int playerScore = 0;
    private int computerScore = 0;

    // Keyboard
    private boolean wPressed;
    private boolean sPressed;

    private boolean gameOver = false;

    private Timer timer;

    public PongGame() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);

        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(8, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Background
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        // Center line
        g2.setColor(Color.WHITE);

        for (int y = 0; y < HEIGHT; y += 30) {
            g2.fillRect(WIDTH / 2 - 2, y, 4, 15);
        }

        // Player paddle
        g2.setColor(Color.WHITE);

        g2.fillRoundRect(
                30,
                playerY,
                PADDLE_WIDTH,
                PADDLE_HEIGHT,
                10,
                10
        );

        // Computer paddle
        g2.fillRoundRect(
                WIDTH - 45,
                computerY,
                PADDLE_WIDTH,
                PADDLE_HEIGHT,
                10,
                10
        );

        // Ball
        g2.fillOval(
                ballX,
                ballY,
                BALL_SIZE,
                BALL_SIZE
        );

        // Score
        g2.setFont(new Font("Arial", Font.BOLD, 60));

        String score = playerScore + "     " + computerScore;

        int scoreWidth = g2.getFontMetrics().stringWidth(score);

        g2.drawString(
                score,
                (WIDTH - scoreWidth) / 2,
                70
        );

        // Controls
        g2.setFont(new Font("Arial", Font.PLAIN, 16));

        g2.drawString(
                "YOU: W / S",
                30,
                HEIGHT - 20
        );

        g2.drawString(
                "COMPUTER",
                WIDTH - 110,
                HEIGHT - 20
        );

        // Game over
        if (gameOver) {

            g2.setColor(Color.RED);

            g2.setFont(new Font("Arial", Font.BOLD, 50));

            String winner;

            if (playerScore >= 5) {
                winner = "YOU WIN!";
            } else {
                winner = "COMPUTER WINS!";
            }

            int textWidth =
                    g2.getFontMetrics().stringWidth(winner);

            g2.drawString(
                    winner,
                    (WIDTH - textWidth) / 2,
                    HEIGHT / 2
            );

            g2.setColor(Color.WHITE);

            g2.setFont(new Font("Arial", Font.PLAIN, 20));

            String restart = "Press ENTER to Restart";

            int restartWidth =
                    g2.getFontMetrics().stringWidth(restart);

            g2.drawString(
                    restart,
                    (WIDTH - restartWidth) / 2,
                    HEIGHT / 2 + 50
            );
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!gameOver) {

            movePlayer();

            moveComputer();

            moveBall();
        }

        repaint();
    }

    // ---------------- PLAYER CONTROL ----------------

    private void movePlayer() {

        int speed = 7;

        if (wPressed && playerY > 0) {
            playerY -= speed;
        }

        if (sPressed &&
                playerY < HEIGHT - PADDLE_HEIGHT) {

            playerY += speed;
        }
    }

    // ---------------- COMPUTER AI ----------------

    private void moveComputer() {

        int computerSpeed = 5;

        int computerCenter =
                computerY + PADDLE_HEIGHT / 2;

        int ballCenter =
                ballY + BALL_SIZE / 2;

        if (computerCenter < ballCenter - 10) {

            computerY += computerSpeed;
        }

        if (computerCenter > ballCenter + 10) {

            computerY -= computerSpeed;
        }

        // Keep computer inside screen

        if (computerY < 0) {
            computerY = 0;
        }

        if (computerY > HEIGHT - PADDLE_HEIGHT) {
            computerY = HEIGHT - PADDLE_HEIGHT;
        }
    }

    // ---------------- BALL MOVEMENT ----------------

    private void moveBall() {

        ballX += ballDX;
        ballY += ballDY;

        // Top wall
        if (ballY <= 0) {

            ballY = 0;

            ballDY = -ballDY;
        }

        // Bottom wall
        if (ballY >= HEIGHT - BALL_SIZE) {

            ballY = HEIGHT - BALL_SIZE;

            ballDY = -ballDY;
        }

        Rectangle ball = new Rectangle(
                ballX,
                ballY,
                BALL_SIZE,
                BALL_SIZE
        );

        Rectangle player = new Rectangle(
                30,
                playerY,
                PADDLE_WIDTH,
                PADDLE_HEIGHT
        );

        Rectangle computer = new Rectangle(
                WIDTH - 45,
                computerY,
                PADDLE_WIDTH,
                PADDLE_HEIGHT
        );

        // Player collision

        if (ball.intersects(player) && ballDX < 0) {

            ballX = 45;

            ballDX = -ballDX;

            int paddleCenter =
                    playerY + PADDLE_HEIGHT / 2;

            int ballCenter =
                    ballY + BALL_SIZE / 2;

            ballDY =
                    (ballCenter - paddleCenter) / 10;

            if (ballDY == 0) {
                ballDY = 1;
            }
        }

        // Computer collision

        if (ball.intersects(computer) && ballDX > 0) {

            ballX =
                    WIDTH - 45 - BALL_SIZE;

            ballDX = -ballDX;

            int paddleCenter =
                    computerY + PADDLE_HEIGHT / 2;

            int ballCenter =
                    ballY + BALL_SIZE / 2;

            ballDY =
                    (ballCenter - paddleCenter) / 10;

            if (ballDY == 0) {
                ballDY = 1;
            }
        }

        // Ball goes left

        if (ballX < 0) {

            computerScore++;

            checkWinner();

            if (!gameOver) {
                resetBall(-1);
            }
        }

        // Ball goes right

        if (ballX > WIDTH) {

            playerScore++;

            checkWinner();

            if (!gameOver) {
                resetBall(1);
            }
        }
    }

    // ---------------- RESET BALL ----------------

    private void resetBall(int direction) {

        ballX =
                WIDTH / 2 - BALL_SIZE / 2;

        ballY =
                HEIGHT / 2 - BALL_SIZE / 2;

        ballDX = 5 * direction;

        ballDY =
                Math.random() > 0.5 ? 5 : -5;
    }

    // ---------------- WINNER ----------------

    private void checkWinner() {

        if (playerScore >= 5 ||
                computerScore >= 5) {

            gameOver = true;
        }
    }

    // ---------------- RESTART ----------------

    private void restartGame() {

        playerScore = 0;

        computerScore = 0;

        playerY =
                HEIGHT / 2 - PADDLE_HEIGHT / 2;

        computerY =
                HEIGHT / 2 - PADDLE_HEIGHT / 2;

        gameOver = false;

        resetBall(
                Math.random() > 0.5 ? 1 : -1
        );

        requestFocusInWindow();
    }

    // ---------------- KEY PRESSED ----------------

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            wPressed = true;
        }

        if (key == KeyEvent.VK_S) {
            sPressed = true;
        }

        if (key == KeyEvent.VK_ENTER &&
                gameOver) {

            restartGame();
        }
    }

    // ---------------- KEY RELEASED ----------------

    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            wPressed = false;
        }

        if (key == KeyEvent.VK_S) {
            sPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    // ---------------- MAIN ----------------

    public static void main(String[] args) {

        JFrame frame =
                new JFrame("PONG - Java Game");

        PongGame game =
                new PongGame();

        frame.add(game);

        frame.pack();

        frame.setResizable(false);

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        game.requestFocusInWindow();
    }
}