package Game.Piece;

import Game.Board.GameTile;

import java.awt.*;
/**
 *
 * @author Antoan
 * @param "Пазача наследява стойностите на фигурата"
 */
public class GreenBlocks extends Figure {
    public Color color;

    /**
     * @param "Задаване на параметри на къщите на Баба Яга"
     * @author Antoan
     */
    public GreenBlocks(int row, int col, Color color) {
        super();
        this.row = row;
        this.col=col;
        this.color=color;
    }
    /**
     *
     * @author Antoan
     * @param "Визуализиране на къщите"
     */
    public void render(Graphics g) {
        int x = this.col * GameTile.TILE_SIZE;
        int y = this.row * GameTile.TILE_SIZE;
        g.setColor(this.color);
        g.fillRect(x+10, y+33, 48, 48);

    }

}