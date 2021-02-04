package Game.Board;

import java.awt.*;

public class GameTile {
    /**
     *
     * @author Antoan
     * @param "Създавам си цвят"
     */
    public static final int TILE_SIZE = 50;

    private float row;
    private float col;
    private float tileSize;

    /**
     *
     * @author Antoan
     * @param "Инициализиране на стойностите за игралната плочка"
     */
    public GameTile(float row, float col) {

        this.row        = row;
        this.col        = col;
        this.tileSize   = 50;
    }
    /**
     *
     * @author Antoan
     * @param "Обединяване на 2та метода за визоализиране в едно"
     */
    public void render(Graphics g){
        RenderCubes(g);
        RenderBorders(g);

    }
    /**
     *
     * @author Antoan
     * @param "Визоализиране на квадратите и на кръгчето в центъра на полето"
     */
    public void RenderCubes(Graphics g) {
        g.setColor(Color.RED);
        for(int i=10;i<410;i+=50){
            for (int j=31; j<431;j+=50){
                g.fillRect(i,j,50,50);
            }
        }
    }
    /**
     *
     * @author Antoan
     * @param "Визуализиране на решетката на полето"
     */
    public void RenderBorders(Graphics g){
        g.setColor(Color.black);
        for (int i=31; i<=431 ;i+=50){
            for(int j=8;j<=408;j+=50){
                if(i<=381&&j<=358) {
                    g.fillRect(j, i, 2, 50);
                    g.fillRect(j, i, 50, 2);
                }  if(i==431&&j<408){
                    g.fillRect(j, i, 50, 2);
                }
                if(i<431&&j==408){
                    g.fillRect(j, i, 2, 52);
                }

            }
        }
    }
}