package game;
import java.awt.Rectangle;	
import java.awt.Color;   

// Used for colliding
class Block extends Rectangle{
    Color color;
    double vy;

    Block(int x, int y, int width, int h){
        this.x = x;
        this.y = y;
        this.width = width;
        height = h;
        color = Color.BLUE;
    }
}
