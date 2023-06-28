import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {
    private static final int ROWS=4;
    private static final int COLS=4;
    private JFrame frame = null;
    private GamePanel panel = null;
    private Card[][] cards = new Card[ROWS][COLS];
    private String gameFlag = "start";

    public GamePanel(JFrame frame){
        this.setLayout(null);
        this.setOpaque(false);
        this.frame = frame;
        this.panel = this;

        createMenu();

        createCard();

        createRandomNum();

        createKeyListener();

    }

    private void createKeyListener() {
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!"start".equals(gameFlag)){
                    return;
                }
                int key =e.getKeyCode();
                switch (key){
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        moveCard(1);
                        break;

                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        moveCard(2);
                        break;

                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        moveCard(3);
                        break;

                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        moveCard(4);
                        break;
                }
            }
        };
        frame.addKeyListener(keyAdapter);
    }

    private void moveCard(int dir){
        clearCard();

        if (dir == 1){
            moveCardUp();
        }else if (dir == 2){
            moveCardRight();
        }else if (dir == 3){
            moveCardDown();
        }else if (dir == 4){
            moveCardLeft();
        }

        createRandomNum();
        repaint();
    }

    private void clearCard() {
        Card card;
        for(int i = 0; i < ROWS ; i++) {
            for (int j = 0; j < COLS; j++) {
                card = cards[i][j];
                card.setMerge(false);
            }
        }
    }

    private void moveCardUp() {
        Card card;
        for(int i = 1; i < ROWS ; i++){
            for(int j = 0 ; j < COLS ; j++){
                card = cards[i][j];

                if (card.getNum() != 0){
                    card.moveUp(cards);
                }
            }
        }
    }

    private void moveCardRight() {
        Card card;
        for(int i = 0; i < ROWS ; i++){
            for(int j = COLS - 2 ; j >= 0 ; j--){
                card = cards[i][j];

                if (card.getNum() != 0){
                    card.moveRight(cards);
                }
            }
        }
    }

    private void moveCardDown() {
        Card card;
        for(int i = ROWS - 2; i >= 0 ; i--){
            for(int j = 0 ; j < COLS ; j++){
                card = cards[i][j];

                if (card.getNum() != 0){
                    card.moveDown(cards);
                }
            }
        }
    }

    private void moveCardLeft() {
        Card card;
        for(int i = 0; i < ROWS ; i++){
            for(int j = 1 ; j < COLS ; j++){
                card = cards[i][j];

                if (card.getNum() != 0){
                    card.moveLeft(cards);
                }
            }
        }
    }

    private void createRandomNum() {
        int num = 0;
        Random random = new Random();
        int n = random.nextInt(5);
        if(n==0){
            num = 4;
        }else{
            num = 2;
        }
        if(cardIsFull()){
            return;
        }
        Card card = getRandomCard(random);
        if(card != null){
            card.setNum(num);
        }
    }

    private boolean cardIsFull() {
        Card card;
        for(int i = 0 ; i < ROWS ; i++ ){
            for(int j = 0 ; j < COLS ; j++){
                card = cards[i][j];
                if (card.getNum() == 0){
                    return false;
                }
            }
        }
        return true;
    }

    private Card getRandomCard(Random random) {
        int i = random.nextInt(ROWS);
        int j = random.nextInt(COLS);
        Card card = cards[i][j];

        if(card.getNum() == 0){
            return card;
        }
        return getRandomCard(random);
    }

    private void createCard() {
        Card card;
        for(int i = 0 ; i < ROWS ; i++ ){
            for(int j = 0 ; j < COLS ; j++){
                card = new Card(i,j);
                cards[i][j] = card;
            }
        }
    }

    @Override
    public  void paint(Graphics g){
        super.paint(g);
        drawCard(g);
    }

    private void drawCard(Graphics g) {
        Card card;
        for(int i = 0 ; i < ROWS ; i++ ){
            for(int j = 0 ; j < COLS ; j++){
                card = cards[i][j];
                card.draw(g);
            }
        }
    }

    private Font createFont(){
        return new Font("Yu Mincho",Font.BOLD,16);
    }

    private void createMenu() {
        Font tFont = createFont();

        JMenuBar jmb = new JMenuBar();

        JMenu jm1 = new JMenu("Game");
        jm1.setFont(tFont);
        JMenuItem jmi1 = new JMenuItem("New Game");
        JMenuItem jmi2 = new JMenuItem("Exit");
        jmi1.setFont(tFont);
        jmi2.setFont(tFont);

        jm1.add(jmi1);
        jm1.add(jmi2);

        JMenu jm2 = new JMenu("Help");
        jm2.setFont(tFont);

        JMenuItem jmi3 = new JMenuItem("Operation Assistant");
        JMenuItem jmi4 = new JMenuItem("Victory Conditions");
        jmi3.setFont(tFont);
        jmi4.setFont(tFont);

        jm2.add(jmi3);
        jm2.add(jmi4);

        jmb.add(jm1);
        jmb.add(jm2);

        frame.setJMenuBar(jmb);

        jmi1.addActionListener(this);
        jmi2.addActionListener(this);
        jmi3.addActionListener(this);
        jmi4.addActionListener(this);

        jmi1.setActionCommand("restart");
        jmi2.setActionCommand("exit");
        jmi3.setActionCommand("help");
        jmi4.setActionCommand("victory");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if("restart".equals(command)){
            restart();
        }else if("exit".equals(command)){
            Object[] options ={"YES","NO"};
            int res = JOptionPane.showOptionDialog(this,"Are you sure you want to quit the game?","",JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
            if(res==0){
                System.exit(0);
            }
        }else if("help".equals(command)){
            JOptionPane.showMessageDialog(null,"Move by keyboard arrow keys and the same numbers will merge!","MESSAGE!",JOptionPane.INFORMATION_MESSAGE);
        }else if("victory".equals(command)){
            JOptionPane.showMessageDialog(null,"Get the number 2048 to win","MESSAGE!",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void restart() {
        frame.dispose();
        GameFrame frameNew = new GameFrame();
        GamePanel panel = new GamePanel(frameNew);
        frameNew.add(panel);
        frameNew.setVisible(true);

    }
}
