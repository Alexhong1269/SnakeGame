import javax.swing.JFrame;

public class GameFrame extends JFrame{

    //making a constructor for the GameFrame
    GameFrame(){
        //making in instance of our GamePanel
        GamePanel panel = new GamePanel();

        //adding our pannel
        this.add(panel);

        //setting the title
        this.setTitle("Snake Game");
        //setting the close operation for exiting the game
        this.setDefualtCloseOperation(JFrame.EXIT_ON_CLOSE);
        //can not resize the GameFrame
        this.setResizable(false);
        //packing all components to the JFrame
        this.pack();
        //making our panel visable
        this.setVisible(true);
        //setting our pannel to be in the middle of our screen
        this.setLocationRelativeTo(null);
    }
    
}
