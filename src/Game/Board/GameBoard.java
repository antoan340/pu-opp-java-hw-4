package Game.Board;

import Game.Piece.BlueBlocks;
import Game.Piece.GreenBlocks;
import Game.Piece.YellowBlock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ThreadLocalRandom;
import Ui.Modal;


/**
 *
 * @author Antoan
 * @param "проектиране на игралното поле"
 */
public class GameBoard extends JFrame implements MouseListener {
    public static final int TILE_SIDE_COUNT = 8;
    int blueCount = 5, greenCount = 8;
    int rowCord , colCord, swap;
    int rowStart, colStart;
    public BlueBlocks[][] BlueB;
    public GreenBlocks[][] GreenB;
    public YellowBlock[][] YellowB;
    private YellowBlock selectedBlock;
    /**
     * @param "Инициализацията на игралното поле заедно със всички тайлове"
     * @author Antoan
     */
    public GameBoard() {

        YellowBSpawn();
        GreenAndBlueSpawn();
        setTitle("GPS");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addMouseListener(this);
    }
    /**
     * @param "Инициализацията на играча"
     * @author Antoan
     */
    public void YellowBSpawn(){
        this.YellowB = new YellowBlock[TILE_SIDE_COUNT][TILE_SIDE_COUNT];
        int YellowCord = ThreadLocalRandom.current().nextInt(1, 5);
        switch (YellowCord) {
            case 1 -> this.YellowB[0][0] = (new YellowBlock(0, 0, Color.YELLOW));
            case 2 -> this.YellowB[7][0] = (new YellowBlock(7, 0, Color.YELLOW));
            case 3 -> this.YellowB[0][7] = (new YellowBlock(0, 7, Color.YELLOW));
            default -> this.YellowB[7][7] = (new YellowBlock(7, 7, Color.YELLOW));
        }
    }
    /**
     * @param "Инициализирането на случаен принцип на къщите на Баба яга и на непроходимите места"
     * @author Antoan
     */
    public void GreenAndBlueSpawn(){
        this.GreenB = new GreenBlocks[TILE_SIDE_COUNT][TILE_SIDE_COUNT];
        this.BlueB = new BlueBlocks[TILE_SIDE_COUNT][TILE_SIDE_COUNT];
        do{
            if(greenCount>0) {
                rowCord = ThreadLocalRandom.current().nextInt(0, 8);
                colCord = ThreadLocalRandom.current().nextInt(0, 8);
                if (this.BlueB[rowCord][colCord] == null && this.GreenB[rowCord][colCord] == null && this.YellowB[rowCord][colCord] == null) {
                    this.GreenB[rowCord][colCord] = (new GreenBlocks(rowCord, colCord, Color.GREEN));
                    greenCount--;
                }
            }
            if(blueCount>0) {
                rowCord = ThreadLocalRandom.current().nextInt(0, 8);
                colCord = ThreadLocalRandom.current().nextInt(0, 8);
                if(this.BlueB[rowCord][colCord]==null && this.GreenB[rowCord][colCord]==null && this.YellowB[rowCord][colCord]==null) {
                    this.BlueB[rowCord][colCord] = (new BlueBlocks(rowCord, colCord, Color.BLUE));
                    blueCount--;
                }
            }
        }while (greenCount>0||blueCount>0);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = this.getBoardDimentionBasedOnCoordinates(e.getY());
        int col = this.getBoardDimentionBasedOnCoordinates(e.getX());
        row--;
        if ((row < 9 && col < 9) ) {
            if (this.selectedBlock!=null ) {
                yellowMove(row,col,rowStart,colStart);
            } else {
                rowStart = row;
                colStart = col;
                if (this.hasYellowPiece(row, col)) {
                    this.selectedBlock = this.getYellowPiece(row, col);
                    Modal.render(this, "Движение", "Можете да се преместите!");
                }

            }
        }

    }
    /**
     * @param "Движението на играча"
     * @author Antoan
     */
    public  void yellowMove(int row, int col,int rowStart,int colStart){
        if (this.hasBluePiece(row, col)||(YellowB[row][col]!=null&&YellowB[row][col].color.equals(Color.YELLOW))) {
            Modal.render(this, "Внимание", "Невалиден ход");
            this.selectedBlock=null;
        } else {
            if((( (row-rowStart>-2 && row-rowStart<2) && col-colStart==0) || ( (col-colStart>-2 && col-colStart<2) && row-rowStart==0))&& this.YellowB[rowStart][colStart].color.equals(Color.YELLOW) ) {
                if(YellowB[row][col]!=null&&YellowB[row][col].color.equals(Color.ORANGE)){
                    YellowB[row][col]=null;
                }
                YellowBlock p = (YellowBlock) this.selectedBlock;
                p.move(row, col);
                if(this.hasGreenPiece(row,col)) {
                    YellowB[row][col]=null;
                    Modal.render(this, "Внимание", "Открихте Баба Яга");
                }
                else {
                    this.YellowB[row][col] = this.YellowB[rowStart][colStart];
                    this.YellowB[rowStart][colStart] = null;
                }
                trail(rowStart,colStart);
                this.repaint();
                this.selectedBlock = null;
                deadEnd(row,col);
            }
            else
                Modal.render(this, "Грешка", "Грешен ход");
        }
    }
    /**
     * @param "Следата кято се оставя селд играча(проходими и непроходими места)"
     * @author Antoan
     */
    public void trail(int rowStart,int colStart){
        swap = ThreadLocalRandom.current().nextInt(1, 11);
        if (swap < 8) {
            this.YellowB[rowStart][colStart] = (new YellowBlock(rowStart, colStart, Color.ORANGE));
        } else this.BlueB[rowStart][colStart] = (new BlueBlocks(rowStart, colStart, Color.BLUE));
    }
    /**
     * @param "Всичко в метода deadEnd е за проверка във всички възможни ситуации дали играча е заобиколен от сини квадратчета"
     * @author Antoan
     */

    public void deadEnd(int row,int col) {
        center(row,col);
        top(row,col);
        bottom(row,col);
    }
    public void center(int row,int col){
        if (row > 0 && row < 7 && col > 0 && col < 7)
            if (BlueB[row + 1][col] != null && BlueB[row - 1][col] != null && BlueB[row][col + 1] != null && BlueB[row][col - 1] != null)
                System.exit(-1);
    }
    public void top(int row,int col){
        if (row == 0) {
            if (col > 0 && col < 7) {
                if (BlueB[row + 1][col] != null && BlueB[row][col + 1] != null && BlueB[row][col - 1] != null)
                    System.exit(-1);
            }
            if (col == 0) {
                if (BlueB[row + 1][col] != null && BlueB[row][col + 1] != null)
                    System.exit(-1);
            }
            if(col==7){
                if (BlueB[row + 1][col] != null && BlueB[row][col - 1] != null)
                    System.exit(-1);
            }
        }
    }
    public void bottom(int row,int col){
        if (row == 7) {
            if (col > 0 && col < 7) {
                if (BlueB[row - 1][col] != null && BlueB[row][col + 1] != null && BlueB[row][col - 1] != null)
                    System.exit(-1);
            }
            if (col == 0) {
                if (BlueB[row - 1][col] != null && BlueB[row][col + 1] != null)
                    System.exit(-1);
            }
            if(col==7){
                if (BlueB[row - 1][col] != null && BlueB[row][col - 1] != null)
                    System.exit(-1);
            }
        }
    }




    @Override

    public void paint(Graphics g) {

        super.paint(g);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                GameTile tile = new GameTile(row, col);
                tile.render(g);
            }
        }
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                renderGamePiece(g, row, col);
            }
        }

    }
    /**
     * @param "Приемане на кординатите на играча"
     * @author Antoan
     */
    private YellowBlock getYellowPiece(int row, int col) {
        return this.YellowB[row][col];

    }
    /**
     * @param "Проверка дали на това място има създаден играч"
     * @author Antoan
     */
    private boolean hasYellowPiece(int row, int col) {
        return this.getYellowPiece(row, col) != null;
    }

    /**
     * @param "Приемане на кординатите на къщите в които живее Баба Яга"
     * @author Antoan
     */
    private GreenBlocks getGreenPiece(int row, int col) {
        return this.GreenB[row][col];

    }
    /**
     * @param "Проверка дали на това място има създадена къща на Баба Яга"
     * @author Antoan
     */
    private boolean hasGreenPiece(int row, int col) {
        return this.getGreenPiece(row, col) != null;
    }
    /**
     * @param "Приемане на кординатите на непроходима местност"
     * @author Antoan
     */
    private BlueBlocks getBluePiece(int row, int col) {
        return this.BlueB[row][col];

    }
    /**
     * @param "Проверка дали на това място има непроходима местност"
     * @author Antoan
     */
    private boolean hasBluePiece(int row, int col) {
        return this.getBluePiece(row, col) != null;
    }


    /**
     * @param "Принтирането на Играча и игралните полета"
     * @author Antoan
     */
    private void renderGamePiece(Graphics g, int row, int col) {
        if (this.hasYellowPiece(row, col)) {
            YellowBlock p = (YellowBlock) this.getYellowPiece(row, col);
            p.render(g);

        }
        if (this.hasGreenPiece(row, col)) {
            GreenBlocks p1 = (GreenBlocks) this.getGreenPiece(row, col);
            p1.render(g);

        }
        if (this.hasBluePiece(row, col)) {
            BlueBlocks p2 = (BlueBlocks) this.getBluePiece(row, col);
            p2.render(g);

        }
    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    private int getBoardDimentionBasedOnCoordinates(int coordinates) {
        return coordinates / GameTile.TILE_SIZE;
    }

}
