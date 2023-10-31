package game;
import hsa2.GraphicsConsole;
import java.awt.Font;	
import java.awt.Color;

/*Kevin
 * Take a look of my code
 * 
 * I like using methords, although many of them are not nessasary
 * It's like making folders. XD
 */

public class FallingBlocks {
    public static void main(String [] args) {
        new FallingBlocks();
    }
    // seting up
    final static int WINW = 900, WINH = 600;
    GraphicsConsole gc = new GraphicsConsole(WINW,WINH);
    Block b1 = new Block(150,0,70,40);
    Block b2 = new Block(220,0,70,40);
    Block b3 = new Block(290,0,70,40);
    Block b4 = new Block(360,0,70,40);
    Block b5 = new Block(430,0,70,40);
    Block b6 = new Block(500,0,70,40);
    Block b7 = new Block(570,0,70,40);
    Block b8 = new Block(640,0,70,40);
    Block b9 = new Block(710,0,70,40);
    Block b10 = new Block(780,0,70,40);
    Block[] blocks = {b1, b2, b3, b4, b5, b6, b7, b8, b9, b10};

    // global variables
    int lives = 30;
    int scores = 0;

    int blockSpeed = 2;
    int numberOfBlocksUsed = 5;
    int blockFrequncy = 30;

    int doOrNot = 0;
    int waveNumber = 1;
    int waveTime = 2000;
    Color sideColor = new Color(211, 212, 242);
    Color middleColor = new Color(243, 247, 126);

    FallingBlocks(){
        setup();        
        gc.showDialog("If a BLUE block drops, you lose a life \n If a you hit a RED block, you lose a life", "instruction");
        while (lives>0){
            runWave(); // my game come in waves

            showComment(); // pop up a comment

            upgrade(); //make harder every wave
        }

    }

    void setup(){ // settings
        gc.setTitle("Falling blocks");
        gc.setAntiAlias(true);
        gc.setResizable(true);
        gc.setLocationRelativeTo(null);
        gc.setBackgroundColor(Color.WHITE);
        gc.clear();
        gc.enableMouseMotion();
        gc.enableMouse();
        gc.setStroke(3);
        Font f = new Font("Impact", Font.ITALIC,22);

        gc.setFont(f);
        b1.color = b3.color = b5.color = b7.color = b9.color = Color.blue;
        b2.color = b4.color = b6.color = b8.color = b10.color = Color.red;

 
    }

    // start running the game
    void runWave(){
        int time = 0;    // set the starting time  
        
        while (time < 2000  || b1.vy !=0 || b2.vy !=0 || b3.vy !=0|| b4.vy !=0 || b5.vy !=0|| b6.vy !=0|| b7.vy !=0|| b8.vy !=0|| b9.vy !=0|| b10.vy !=0){   // end a wave after 2000 cycles, but have to wait for the player get all the blocks or all blocks drop
            if (time<2000){ // after 2000 cycles, the wave will continue but no more blocks will be actived
                chooseBlock();  // choose a random block and give it a speed (all the blocks have a speed of 0)
                time+=1;
                waveTime -=1;
                if(lives==0){
                    break;
                }
            }
            blockMove();    // move all the block (only the blocks with speed will move)
            drawGraphics(); // update it on the screen       
            gc.sleep(7); 
        } // end of while loop
        gc.sleep(500); // A sudden appear doesn't look good, sleep 0.5 sec and then comment pop up
        waveNumber +=1;
        waveTime = 2000; // I don't know time so I do a wave time. Reseting the wave time now 
    } // end of runWave


    void chooseBlock(){
        int num = 0; // determine which block is moving (1-10) when 0, move nothing
        // doOrNot start with 0, see global variable
        if (doOrNot == blockFrequncy){// only if the number reaches the blockFrequncy, we chose a block to move. I don't know timer so I do every 50 cycles of code active a block 
            num = (int) (Math.random() * numberOfBlocksUsed) +1;
            doOrNot = 0;
        }else{
             num = 0;
             doOrNot +=1;
        } // end of if-else

        // Make the chosen block have a speed (they all have 0 in begaining)
        switch(num){
            case 1:
                b1.vy = blockSpeed;
                //give the chosen block a speed (make them active)
                break;
            case 2:
                b2.vy = blockSpeed;
                break;
            case 3:
                b3.vy = blockSpeed;
                break;
            case 4:
                b4.vy = blockSpeed;
                break;
            case 5:
                b5.vy = blockSpeed;
                break;
            case 6:
                b6.vy = blockSpeed;
                break;
            case 7:
                b7.vy = blockSpeed;
                break;
            case 8:
                b8.vy = blockSpeed;
                break;
            case 9:
                b9.vy = blockSpeed;
                break;
            case 10:
                b10.vy = blockSpeed; 
                break; 
        } // end of switch
    } // end of void


    void blockMove(){
        for(Block b : blocks){ // the list of all blocks
            moveBlock(b); // make all block move down on the screen (only the one active will have a reacte) Sorry for the name, I will change
            mouseCollide(b); // check if mouse touches the block or not
        }//end of for loop
    }//end of block move



    void moveBlock(Block b){
        if (b.y<WINH) b.y += b.vy; // if not hit the ground, always move down

        else{ // if hit the groud, return up and wait fot been picken again. lose a lif
            b.y = 0;
            b.vy = 0; //seting speed to 0 and make it not active
            if (b.color == Color.BLUE)lives-=1;  // onlt blue blocks fall will lose life
            else{
                scores+=1;   // red one drop will give you a score too
            }
        }// end of if-else
    }// end if moveBlock

    void mouseCollide(Block b){
        if (b.contains(gc.getMouseX(),gc.getMouseY())){
              // if mouse touches the block, it will return up and wait for been picken again
            if (b.color == Color.RED && b.vy!=0)lives -=1; // no touching red
            else{
                scores +=1;
            }
            b.y = 0;
            b.vy = 0;  // set speed back to 0, make the, not active
        }
    }
   
    void showComment(){
        if (lives>0) gc.showDialog("Good, Next!!","Congratulation"); // when ended by time out (success a wave)
        else{
            gc.showDialog("You final score is: " + scores,"Blocks collide"); // when ended by lives out
        }//end of if-else
    }//end of showComment

    void upgrade(){
        if (numberOfBlocksUsed <10)numberOfBlocksUsed +=1;  // if the number of blocks used did not reach the maximum, add one.
        if (blockSpeed<40) blockSpeed += 1;  // increase speed of moving
        if (blockFrequncy >1) blockFrequncy -=4;  // make the frequncy higher
    }


    void drawGraphics(){
        synchronized(gc){

            gc.clear();
            gc.setColor(sideColor);
            gc.fillRect(0,0,150,600);
            gc.setColor(middleColor);
            gc.fillRect(150,0,numberOfBlocksUsed*70,600);
            gc.setColor(b1.color);
            gc.fillRect(b1.x,b1.y,b1.width,b1.height);
            gc.fillRect(b3.x,b3.y,b3.width,b3.height);
            gc.fillRect(b5.x,b5.y,b5.width,b5.height);
            gc.fillRect(b7.x,b7.y,b7.width,b7.height);
            gc.fillRect(b9.x,b9.y,b9.width,b9.height);

            gc.setColor(b2.color);
            gc.fillRect(b2.x,b2.y,b2.width,b2.height);
            gc.fillRect(b4.x,b4.y,b4.width,b4.height);
            gc.fillRect(b6.x,b6.y,b2.width,b6.height);
            gc.fillRect(b8.x,b8.y,b8.width,b8.height);
            gc.fillRect(b10.x,b10.y,b10.width,b10.height);
            
            gc.setColor(Color.orange);
            gc.fillOval(gc.getMouseX()-5,gc.getMouseY()-5,10,10);

            gc.setColor(Color.BLACK);
            gc.drawString("lives: " + lives, 10,170);
            gc.drawString("Score: " + scores, 10,300);
           
            gc.drawString("Time: " + waveTime/100, 10,40);
            gc.drawString("Wave : " + waveNumber, 10,430);
            gc.setColor(sideColor);
            gc.fillRect(150+numberOfBlocksUsed*70,0,WINW-(150+numberOfBlocksUsed*70),600);
        }
    }
}
