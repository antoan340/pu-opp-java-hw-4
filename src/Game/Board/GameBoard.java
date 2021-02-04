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
    private BlueBlocks[][] BlueB;
    private GreenBlocks[][] GreenB;
    private YellowBlock[][] YellowB;
    private YellowBlock selectedBlock;
    public GameBoard() {

        YellowBSpawn();
        GreenAndBlueSpawn();
        setTitle("GPS");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addMouseListener(this);
    }
    private void YellowBSpawn(){
        this.YellowB = new YellowBlock[TILE_SIDE_COUNT][TILE_SIDE_COUNT];
        int YellowCord = ThreadLocalRandom.current().nextInt(1, 5);
        switch (YellowCord) {
            case 1 -> this.YellowB[0][0] = (new YellowBlock(0, 0, Color.YELLOW));
            case 2 -> this.YellowB[7][0] = (new YellowBlock(7, 0, Color.YELLOW));
            case 3 -> this.YellowB[0][7] = (new YellowBlock(0, 7, Color.YELLOW));
            default -> this.YellowB[7][7] = (new YellowBlock(7, 7, Color.YELLOW));
        }
    }
    private void GreenAndBlueSpawn(){
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

    }

    /**
     * @param "Движението на гарда"
     * @author Antoan
     */

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
    private YellowBlock getYellowPiece(int row, int col) {
        return this.YellowB[row][col];

    }

    private boolean hasYellowPiece(int row, int col) {
        return this.getYellowPiece(row, col) != null;
    }
    private GreenBlocks getGreenPiece(int row, int col) {
        return this.GreenB[row][col];

    }

    private boolean hasGreenPiece(int row, int col) {
        return this.getGreenPiece(row, col) != null;
    }
    private BlueBlocks getBluePiece(int row, int col) {
        return this.BlueB[row][col];

    }

    private boolean hasBluePiece(int row, int col) {
        return this.getBluePiece(row, col) != null;
    }


    /**
     * @param "проектирането на игралните фигури"
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
