import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

    //declaring the screen width size
    static final int SCREEN_WIDTH = 600;
    //declaring the screen height size
    static final int SCREEN_HEIGHT = 600;
    //making the size of the objects in the game(Apples and snake)
    static final int UNIT_SIZE = 25;
    //how many objects we want on the screen
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    //making a delay for the game. to show how fast the game is running
    static final int DELAY = 75;

    //making 2 arrays that hold the x-y coordinates of the snake body
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    //our beggining body part amount
    int bodyParts = 6;

    //declaring how many apples eaten
    int applesEaten = 0;

    //setting the coordinates of the apples
    int appleX;
    int appleY;

    //setting the starting direction of the snake
    char direction = 'R';

    //to see if the game is running
    boolean running = false;

    //making a timer
    Timer timer;
    //making an instance class for random
    Random random;


    //making a constructor for GamePanel
    Gamepanel(){
        random = new Random();
        
        //setting the size of the game panel
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        
        //setting the background color
        this.setBackGround(Color.black);

        this.setFocusable(true);
        //adding our keylistener
        this.add(keylistener(new MykeyAdapter()));

        //call on the start Game method

    }

    //method to start the game
    public void startGame(){
        //call the new apple method to make an apple on the screen
        newApple();
        //set the game to running cuz we are starting a new game
        running = true;
        //making a timer
        timer = new Timer(DELAY, this);
        timer.start();
    }
    //method to display the graphics
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    //drawing the snake onto our screen
    public void draw(Graphics g){
        if(running){
            //letting the game have a grid for viewers to understand
            for(int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++){
                //lines for the x-axis of the screen
                g.drawLine(i * UNIT_SIZE,0, i * UNIT_SIZE, SCREEN_HEIGHT);
                //lines for the y-axis of the screen
                g.drawLine(0,i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            //drawing the apple
            //setting the color of the apple
            g.setColor(Color.red);
            //giving the apple an oval shape and the coordinates of applex and appley with appropriate unitSize
            g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);

            //drawing the body of the snake
            for(int i = 0; i < bodyParts; i++){
                //dealing with the head of our snake
                if(i == 0){
                    g.set(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                //dealing with the body of our snake
                else{
                    g.set(new Color(45,180,0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            //text for drawing the current score
            g.setColor(Color.red);
            //setting the font
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            //lineing up the font to the middle of the screen
            FontMetrics metrics = getFontMetrics(g.getFont());
            //placing it on the center of the screen
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }
    //populate the game with apples
    public void newApple(){
        //generating the coordinates for a new apple
        //appears somewhere on the x-axis
        appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        //letting the apple appear somewhere on the y-axis
        appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;

    }
    //giving the snake move capabilites
    public void move(){
        //for loop to itterate through all the body parts of the snake
        for(int i = bodyParts; i > 0; i--){
            //shifting the body parts of our snake around
            //shiftint it by one spot
            x[i] = x[i - 1];
            y[i] = y[i - 1];

            //make a direction varaible for the controls
            switch(direction){
                //the direction of the snake going up
                case 'U':
                    y[0] = y[0] - UNIT_SIZE;
                    break;
                //the direction of the snake going down
                case 'D':
                    y[0] = y[0] + UNIT_SIZE;
                    break;
                //the direction of the snake going to theleft
                case 'L':
                    x[0] = x[0] - UNIT_SIZE;
                    break;
                //the direction of the snake going to the right
                case 'R':
                    x[0] = x[0] + UNIT_SIZE;
                    break;
            }
        }
    }
    //method to check if the apple is eaten by the snake
    public void checkApple(){
        //checking if the snake has going over the apple
        if((x[0] == appleX) && (y[0] == appleY)){
            //increasing the body parts when the apple is eaten
            bodyParts++;
            //the score counter to see total apples eaten
            applesEaten++;
            //generating a new apple at a random location
            newApple();
        }

    }
    //checking for snake collisions
    public void checkCollisions(){
        //to check if the head of our snake collids with the body
        for(int i = bodyParts; i > 0; i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                //triggers a game over
                running = false;
            }
        }
        //check if head touches the left border
        if(x[0] < 0){
            running = false;
        }
        //check if the head touchs the right border
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }
        //check if head touches top border
        if(y[0] < 0){
            running = false;
        }
        //check if head is touching bottom border
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }

        //stopping the timmer
        if(!running){
            timer.stop();
        }
    }
    //game over method when the player loser
    public void gameOver(Graphics g){
        //the game over text
        g.setColor(Color.red);
        //setting the font
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        //lineing up the font to the middle of the screen
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        //placing it on the center of the screen
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

        //text for drawing the current score
        g.setColor(Color.red);
        //setting the font
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        //lineing up the font to the middle of the screen
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        //placing it on the center of the screen
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
    }
    @Override
    public void actionPerformed(ActionEvent e){
        //check if our game is running
        if(running){
            //we then want to move our snake
            move();
            //check if we ran into an apple
            checkApple();
            //check for collisions
            checkCollisions();
        }
        //if our game is no longer running
        repaint();
    }
    //controling the snake
    public class MykeyAdapter extends keyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                //moving snake with arrow keys
                //the player is going to the left
                case KeyEvent.VK_LEFT: 
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                //the player is letting the snake go right
                case KeyEvent.VK_RIGHT: 
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                //the snake is moving the snake up
                case KeyEvent.VK_UP: 
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                //moving the snake down
                case KeyEvent.VK_DOWN: 
                    if(direction != 'U'){
                        direction = 'D';
                    }
                        break;
            }
        }
    }
}
