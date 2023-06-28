import java.awt.*;

public class Card {
    private int x = 0;//x座標
    private int y = 0;//ｙ座標
    private int w = 80;//幅
    private int h = 80;//高さ
    private int i = 0;
    private int j = 0;

    private int start = 10;
    private int num = 0;
    private  boolean merge = false;

    public  Card(int i,int j){
        this.i = i;
        this.j = j;

        cal();
    }

    private  void  cal(){
        this.x = start + j * w + (j + 1)*3;
        this.y = start + i * h + (i + 1)*3;
    }

    public void draw(Graphics g) {
        Color color = getColor();

        Color oColor = g.getColor();

        g.setColor(color);
        g.fillRoundRect(x,y,w,h,4,4);

        if(num != 0){
            g.setColor(new Color(125,78,51));
            Font font = new Font("Yu Mincho",Font.BOLD,30);;
            g.setFont(font);
            String text = num + "";
            int textLen = getTextWidth(font,text,g);
            int tx = x + (w-textLen) / 2;
            int ty = y + 50;
            g.drawString(text,tx,ty);
        }

        g.setColor(oColor);
    }

    public static int getTextWidth(Font font,String content,Graphics g){
        FontMetrics metrics = g.getFontMetrics(font);
        int width = 0;
        for(int i = 0 ; i < content.length() ; i++){
            width += metrics.charWidth(content.charAt(i));
        }
        return width;
    }

    private Color getColor(){
        Color color = null;
        switch (num){
            case 2:
                color = new Color(238,244,234);
                break;
            case 4:
                color = new Color(209, 243, 252);
                break;
            case 8:
                color = new Color(155, 236, 250);
                break;
            case 16:
                color = new Color(113, 216, 255);
                break;
            case 32:
                color = new Color(50, 202, 250);
                break;
            case 64:
                color = new Color(85, 162, 253);
                break;
            case 128:
                color = new Color(11, 110, 243);
                break;
            case 256:
                color = new Color(79, 95, 239);
                break;
            case 512:
                color = new Color(171, 99, 248);
                break;
            case 1024:
                color = new Color(123, 65, 236);
                break;
            case 2048:
                color = new Color(162, 0, 246);
                break;
            default:
                color = new Color(209, 243, 253);
                break;
        }

        return color;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return this.num;
    }

    public int getNum(int i){
        this.num= i;
        return this.num;
    }

    public void moveUp(Card[][] cards) {
        if (i == 0){
            return;
        }

        Card prev = cards[i-1][j];
        if (prev.getNum() == 0){
                prev.num = this.num;
                this.num = 0;

                prev.moveUp(cards);
        } else if (prev.getNum() == num && !prev.merge){
                prev.merge = true ;
                prev.num = this.num * 2;
                this.num = 0;
        }

    }

    public void setMerge(boolean b) {
        this.merge = b;
    }

    public void moveRight(Card[][] cards) {
        if (j == 3){
            return;
        }

        Card prev = cards[i][j+1];
        if (prev.getNum() == 0){
                prev.num = this.num;
                this.num = 0;

                prev.moveRight(cards);
        } else if (prev.getNum() == num && !prev.merge){
                prev.merge = true ;
                prev.num = this.num * 2;
                this.num = 0;
        }
    }

    public void moveDown(Card[][] cards) {
        if (i == 3){
            return;
        }

        Card prev = cards[i+1][j];
        if (prev.getNum() == 0){
                prev.num = this.num;
                this.num = 0;

                prev.moveDown(cards);
        } else if (prev.getNum() == num && !prev.merge){
                prev.merge = true ;
                prev.num = this.num * 2;
                this.num = 0;
        }
    }

    public void moveLeft(Card[][] cards) {
        if (j == 0){
            return;
        }

        Card prev = cards[i][j-1];
        if (prev.getNum() == 0){
                prev.num = this.num;
                this.num = 0;

                prev.moveLeft(cards);
        } else if (prev.getNum() == num && !prev.merge){
                prev.merge = true ;
                prev.num = this.num * 2;
                this.num = 0;
        }
    }
}
