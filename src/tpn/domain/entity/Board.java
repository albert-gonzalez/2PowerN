package tpn.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Board {
    
    private boolean isFinished;
    private List<List<Cell>> cells;
    private List<List<Cell>> previousStateCells;
    private List<Cell> freeCells;
    private Cell lastRandomFilledCell;
    private List<Move> translationsLastState; 
    private List<Move> fusionsLastState; 
    private int score;
    private int elapsedTime;
    private boolean busy;
    private Config config;

    public Board() {
        isFinished = true;
        config = new Config();
        freeCells = new ArrayList();
        translationsLastState = new ArrayList();
        fusionsLastState = new ArrayList();
    }
    
    /**
     * @return the cells
     */
    public List<List<Cell>> getCells() {
        return cells;
    }

    /**
     * @param cells the cells to set
     */
    public void setCells(List<List<Cell>> cells) {
        this.cells = cells;
    }

    /**
     * @return the previousStateCells
     */
    public List<List<Cell>> getPreviousStateCells() {
        return previousStateCells;
    }

    /**
     * @param previousStateCells the previousStateCells to set
     */
    public void setPreviousStateCells(List<List<Cell>> previousStateCells) {
        this.previousStateCells = previousStateCells;
    }

    /**
     * @return the freeCells
     */
    public List<Cell> getFreeCells() {
        return freeCells;
    }

    /**
     * @param freeCells the freeCells to set
     */
    public void setFreeCells(List<Cell> freeCells) {
        this.freeCells = freeCells;
    }

    /**
     * @return the isFinished
     */
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * @param isFinished the isFinished to set
     */
    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    /**
     * @return the config
     */
    public Config getConfig() {
        return config;
    }

    /**
     * @param config the config to set
     */
    public void setConfig(Config config) {
        this.config = config;
    }

    /**
     * @return the lastRandomFilledCell
     */
    public Cell getLastRandomFilledCell() {
        return lastRandomFilledCell;
    }

    /**
     * @param lastRandomFilledCell the lastRandomFilledCell to set
     */
    public void setLastRandomFilledCell(Cell lastRandomFilledCell) {
        this.lastRandomFilledCell = lastRandomFilledCell;
    }
    
    @Override 
    public boolean equals(Object other) {
        boolean equals = other instanceof Board;
        if (equals) {
            Board otherBoard = (Board) other;
            int rowIndex = 0, cellIndex = 0;
            for (List<Cell> row: this.cells) {
                cellIndex = 0;
                for (Cell cell: row) {
                    equals = cell.equals(otherBoard.getCells().get(rowIndex).get(cellIndex));
                    if (!equals) {
                        break;
                    }
                    cellIndex++;
                }
                if (!equals) {
                    break;
                }
                rowIndex++;
            }
        }
        return equals;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.cells);
        return hash;
    }
    
    @Override
    public String toString() {
        String string = "";
        for (List<Cell> row: this.cells) {
            for (Cell cell: row) {
                string += padRight(Integer.toString(cell.getValue()), 8);
            }
            string += "\n";
        }
        
        return string;
    }
    
    private static String padRight(String s, int n) {
     return String.format("%1$-" + n + "s", s);  
    }

    private static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);  
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return the translationsLastState
     */
    public List<Move> getTranslationsLastState() {
        return translationsLastState;
    }

    /**
     * @param translationsLastState the translationsLastState to set
     */
    public void setTranslationsLastState(List<Move> translationsLastState) {
        this.translationsLastState = translationsLastState;
    }

    /**
     * @return the fusionsLastState
     */
    public List<Move> getFusionsLastState() {
        return fusionsLastState;
    }

    /**
     * @param fusionsLastState the fusionsLastState to set
     */
    public void setFusionsLastState(List<Move> fusionsLastState) {
        this.fusionsLastState = fusionsLastState;
    }

    /**
     * @return the busy
     */
    public boolean isBusy() {
        return busy;
    }

    /**
     * @param busy the isBusy to set
     */
    public void setbusy(boolean busy) {
        this.busy = busy;
    }

    /**
     * @return the elapsedTime
     */
    public int getElapsedTime() {
        return elapsedTime;
    }

    /**
     * @param elapsedTime the elapsedTime to set
     */
    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
    
}
