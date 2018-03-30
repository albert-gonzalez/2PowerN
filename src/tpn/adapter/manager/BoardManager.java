package tpn.adapter.manager;

import java.util.ArrayList;
import java.util.Arrays;
import tpn.domain.use_case.make_movement.UnknownInstructionException;
import java.util.List;
import tpn.domain.entity.Board;
import tpn.domain.entity.Cell;
import tpn.domain.entity.Config;
import tpn.domain.entity.Move;
import tpn.domain.use_case.make_movement.GameOverException;
import tpn.domain.use_case.make_movement.WinException;

public class BoardManager implements tpn.domain.manager.BoardManager {
    
    private final Board board;
    
    public BoardManager(Board board) {
        this.board = board;
    }
    
    @Override
    public void init(int size, int target) {
        board.setbusy(true);
        initConfig(size, target);
        initNewState(size);        
        board.setbusy(false);
    }

    private void initNewState(int size) {
        board.setScore(0);
        board.setCells(initCells(size));
        board.setPreviousStateCells(initCells(size));
        board.setIsFinished(false);
        recalculateFreeCells();
        fillRandomCell();
    }

    private List<List<Cell>> initCells(int size) {
        List<List<Cell>> cells = new ArrayList();
        for (int i = 0; i < size; i++) {
            cells.add(new ArrayList());
            for (int j = 0; j < size; j++) {
                cells.get(i).add(new Cell(j,i));
            }
        }
        return cells;
    }

    private void initConfig(int size, int target) {
        List<Integer> firstValues = getFirstValuesArray();
        Config config = new Config(size, target, firstValues);
        board.setConfig(config);
    }
    
    private List<Integer> getFirstValuesArray() {
        List<Integer> firstValues = new ArrayList();
        firstValues.add(2); firstValues.add(2); firstValues.add(2);
        firstValues.add(2); firstValues.add(2);
        firstValues.add(4); firstValues.add(4);
        firstValues.add(8);
        
        return firstValues;
    }
    
    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public void move(int direction) throws UnknownInstructionException {
        board.setbusy(true);
        copyCells(board.getCells(), board.getPreviousStateCells());
        getBoard().getTranslationsLastState().clear();
        disableFlags();
        moveAndMerge(direction);
        mergeMoves();
        addMergeMoves();
        recalculateFreeCells();
        board.setbusy(false);
    }
    
    private void moveAndMerge(int direction) throws UnknownInstructionException {
       switch(direction) {
            case RIGHT:
                moveRight();
                break;
            case LEFT:
                moveLeft();
                break;
            case UP:
                moveUp();
                break;    
            case DOWN:
                moveDown();
                break;
            default:
                throw new UnknownInstructionException();
        } 
    }
    
    private void moveRight() {
        List<List<Cell>> cells = board.getCells();
        Cell current;
        Cell next;
        int offset, cellValue = 0;
        boolean hasMoves = false;
        int j = 0;
        for (List<Cell> row: cells) {
            for (int i = 0; i <  board.getConfig().getSize(); i++) {
                int mergedAtX = -1, mergedAtY = -1;
                hasMoves = false;
                offset = i;
                current = null;
                while (i < board.getConfig().getSize() && offset < board.getConfig().getSize() - 1) {
                    current = row.get(offset);
                    next = row.get(offset + 1);
                    if (canMove(current, next)) {
                        cellValue = move(current, next);
                        hasMoves = true;
                    } else if (canMerge(current, next)) {
                        cellValue = merge(current, next);
                        mergedAtX = offset + 1;
                        mergedAtY = j;
                        hasMoves = true;
                    } else {
                        break;
                    }
                    offset++;
                }
                
                if (hasMoves) {
                    addTranslation(cellValue,i, j, offset, j, mergedAtX, mergedAtY);
                    i = -1;
                }
            }
            j++;
        }
    }
    
    private void moveLeft() {
        List<List<Cell>> cells = board.getCells();
        Cell current;
        Cell next;
        int offset, cellValue = 0;
        boolean hasMoves = false;
        int j = 0;
        for (List<Cell> row: cells) {
            for (int i = board.getConfig().getSize() - 1; i > 0; i--) {
                int mergedAtX = -1, mergedAtY = -1;
                hasMoves = false;
                offset = i;
                current = null;
                while (i >= 0 && offset > 0) {
                    current = row.get(offset);
                    next = row.get(offset - 1);
                    if (canMove(current, next)) {
                        cellValue = move(current, next);
                        hasMoves = true;
                    } else if (canMerge(current, next)) {
                        cellValue = merge(current, next);
                        mergedAtX = offset - 1;
                        mergedAtY = j;
                        hasMoves = true;
                    } else {
                        break;
                    }
                    offset--;
                    
                }
                
                if (hasMoves) {
                    addTranslation(cellValue,i, j, offset, j, mergedAtX, mergedAtY);
                    i = board.getConfig().getSize();
                }
            }
            j++;
        }
    }
    
    private void moveUp() {
        List<List<Cell>> cells = board.getCells();
        Cell current;
        Cell next;
        int offset, cellValue = 0;
        boolean hasMoves = false;
        for (int i = 0; i < board.getConfig().getSize(); i++) {
            for (int j = board.getConfig().getSize() - 1; j > 0; j--) {
                int mergedAtX = -1, mergedAtY = -1;
                hasMoves = false;
                offset = j;
                current = null;
                while (j >= 0 && offset > 0) {
                    current = cells.get(offset).get(i);
                    next = cells.get(offset - 1).get(i);
                    if (canMove(current, next)) {
                        cellValue = move(current, next);
                        hasMoves = true;
                    } else if (canMerge(current, next)) {
                        cellValue = merge(current, next);
                        mergedAtX = i;
                        mergedAtY = offset - 1;
                        hasMoves = true;
                    } else {
                        break;
                    }
                    offset--;
                }
                
                if (hasMoves) {
                    addTranslation(cellValue, i, j, i, offset, mergedAtX, mergedAtY);
                    j = board.getConfig().getSize();
                }
            }
        }
    }
    
    private void moveDown() {
        List<List<Cell>> cells = board.getCells();
        Cell current;
        Cell next;
        int offset, cellValue = 0;
        boolean hasMoves = false;
        for (int i = 0; i < board.getConfig().getSize(); i++) {
            for (int j = 0; j < board.getConfig().getSize(); j++) {
                int mergedAtX = -1, mergedAtY = -1;
                hasMoves = false;
                offset = j;
                current = null;
                while (j < board.getConfig().getSize() && offset < board.getConfig().getSize() - 1) {
                    current = cells.get(offset).get(i);
                    next = cells.get(offset + 1).get(i);
                    if (canMove(current, next)) {
                        cellValue = move(current, next);
                        hasMoves = true;
                    } else if (canMerge(current, next)) {
                        cellValue = merge(current, next);
                        mergedAtX = i;
                        mergedAtY = offset + 1;
                        hasMoves = true;
                    } else {
                        break;
                    }
                    offset++;
                }
                
                if (hasMoves) {
                    addTranslation(cellValue,i, j, i, offset, mergedAtX, mergedAtY);
                    j = -1;
                }
            }
        }
    }
    
    private void merge(int direction) {
       switch(direction) {
            case RIGHT:
                mergeRight(false);
                break;
            case LEFT:
                mergeLeft(false);
                break;
            case UP:
                mergeUp(false);
                break;
            case DOWN:
                mergeDown(false);
                break;
        } 
    }
    
    private boolean mergeRight(boolean onlyCheck) {
        List<List<Cell>> cells = board.getCells();
        Cell current;
        Cell next;
        int j = 0;
        for (List<Cell> row: cells) {
            for (int i = 0; i < board.getConfig().getSize() - 1; i++) {
                current = row.get(i);
                next = row.get(i + 1);
                if (current.getValue() != 0 && current.equals(next)) {
                    if (onlyCheck) {
                        return true;
                    }
                    next.setValue(
                            current.getValue() + 
                            next.getValue()
                    );
                    current.setValue(0);
                    updateScore(next.getValue());
                    
                    addFusion(next.getValue(), i, j, i + 1, j);
                    
                    i++;
                } else if(current.getValue() != 0) {
                    addFusion(next.getValue(), i, j, i, j);
                }
            }
            j++;
        }
        
        return false;
    }
    
    private boolean mergeLeft(boolean onlyCheck) {
        List<List<Cell>> cells = board.getCells();
        Cell current;
        Cell next;
        int j = 0;
        for (List<Cell> row: cells) {
            for (int i = board.getConfig().getSize() - 1; i > 0; i--) {
                current = row.get(i);
                next = row.get(i - 1);
                if (current.getValue() != 0 && current.equals(next)) {
                    if (onlyCheck) {
                        return true;
                    }
                    next.setValue(
                            current.getValue() + 
                            next.getValue()
                    );
                    current.setValue(0);
                    updateScore(next.getValue());
                    
                    addFusion(next.getValue(), i, j, i - 1, j);
                    
                    i--;
                } else if(current.getValue() != 0) {
                    addFusion(next.getValue(), i, j, i, j);
                }
            }
        }
        
        return false;
    }
    
    private boolean mergeUp(boolean onlyCheck) { 
        List<List<Cell>> cells = board.getCells();
        Cell current;
        Cell next;
        for (int i = 0; i < board.getConfig().getSize(); i++) {
            for (int j = board.getConfig().getSize() - 1; j > 0; j--) {
                current = cells.get(j).get(i);
                next = cells.get(j - 1).get(i);
                if (current.getValue() != 0 && current.equals(next)) {
                    if (onlyCheck) {
                        return true;
                    }
                    next.setValue(
                            current.getValue() + 
                            next.getValue()
                    );
                    current.setValue(0);
                    updateScore(next.getValue());
                    
                    addFusion(next.getValue(), i, j, i, j - 1);
                    
                    j--;
                } else if(current.getValue() != 0) {
                    addFusion(next.getValue(), i, j, i, j);
                }
            }
        }
        
        return false;
    }
    
    private boolean mergeDown(boolean onlyCheck) {
        List<List<Cell>> cells = board.getCells();
        Cell current;
        Cell next;
        for (int i = 0; i < board.getConfig().getSize(); i++) {
            for (int j = 0; j < board.getConfig().getSize() - 1; j++) {
                current = cells.get(j).get(i);
                next = cells.get(j + 1).get(i);
                if (current.getValue() != 0 && current.equals(next)) {
                    if (onlyCheck) {
                        return true;
                    }
                    next.setValue(
                            current.getValue() + 
                            next.getValue()
                    );
                    current.setValue(0);
                    updateScore(next.getValue());
                    
                    addFusion(next.getValue(), i, j, i, j + 1);
                    
                    j++;
                } else if(current.getValue() != 0) {
                    addFusion(next.getValue(), i, j, i, j);
                }
            }
        }
        
        return false;
    }
    
    @Override
    public void fillRandomCell() {
        List<Cell> freeCells = board.getFreeCells();
        if (freeCells.size() > 0) {
            Cell cell = freeCells.get(randomWithRange(0, freeCells.size() - 1));
            final List<Integer> firstValues = board.getConfig().getFirstValues();
            cell.setValue(
                    firstValues.get(randomWithRange(0, firstValues.size() - 1 )
                    ));

            freeCells.remove(cell);
            board.setLastRandomFilledCell(cell);
        }
    }
    
    private void recalculateFreeCells() {
        List freeCells = board.getFreeCells();
        freeCells.clear();
        
        for (List<Cell> row: board.getCells()) {
            for (Cell cell: row) {
                if (cell.getValue() == 0) {
                    freeCells.add(cell);
                }
            }
        }
    }
    
    private int randomWithRange(int min, int max)
    {
       int range = (max - min) + 1;     
       return (int)(Math.random() * range) + min;
    }
    
    @Override
    public void checkGameOver() throws GameOverException {
        if (board.getFreeCells().isEmpty() && !canMerge()) {
            finish();
            throw new GameOverException();
        }
    }
    
    private boolean canMerge() {
        boolean canMerge = false;
        canMerge = canMerge || mergeUp(true);
        canMerge = canMerge || mergeDown(true);
        canMerge = canMerge || mergeLeft(true);
        canMerge = canMerge || mergeRight(true);
        
        return canMerge;
    }

    @Override
    public void checkWin() throws WinException {
       int target = board.getConfig().getTarget();
       for (List<Cell> row: board.getCells()) {
            for (Cell cell: row) {
                if (target > 0 && cell.getValue() >= target) {
                    addTimeToScore();
                    finish();
                    throw new WinException();
                }
            }
        }
    }
    
    @Override
    public boolean isStateChanged() {
        List<List<Cell>> current = board.getCells();
        List<List<Cell>> previous = board.getPreviousStateCells();
        
        for (int i = 0; i < current.size(); i++) {
            for (int j = 0; j < current.size(); j++) {
                if (!current.get(i).get(j).equals(previous.get(i).get(j))) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private void copyCells(List<List<Cell>> source, List<List<Cell>> destination) {
        for (int i = 0; i < source.size(); i++) {
            for (int j = 0; j < source.size(); j++) {
                destination.get(i).get(j).setValue(source.get(i).get(j).getValue());
            }
        }
    }
    
    private void updateScore(int value) {
        board.setScore(board.getScore() + value);
    }
    
    private void addTimeToScore() {
        int target = board.getConfig().getTarget();
        int time = board.getElapsedTime();
        
        board.setScore(board.getScore()+(target*1000/time));
    }
    
    private void addTranslation(int value, int initX, int initY, int destX, 
            int destY, int mergedAtX, int mergedAtY) {
        if (value > 0) {
            board.getTranslationsLastState().add(
                    new Move(Move.Type.TRANSLATION, value, initX, initY, 
                            destX, destY, mergedAtX, mergedAtY
                    ));
        
        }
    }

    private void addFusion(int value, int initX, int initY, int destX, int destY) {
        board.getFusionsLastState().add(
                    new Move(Move.Type.FUSION, value, initX, initY, destX, destY
                    ));
    }
    
    private boolean canMove(Cell current, Cell next) {
        return current.getValue() != 0 && next.getValue() == 0;
    }
    
    private int move(Cell current, Cell next) {
        next.setValue(current.getValue());
        next.setMerged(current.isMerged());
        int cellValue = current.getValue();
        next.setModified(true);
        current.setModified(true);
        current.clear();
        return cellValue;
    }
    
    private boolean canMerge(Cell current, Cell next) {
        return current.getValue() != 0 && current.equals(next)
                            && !current.isMerged() && !next.isMerged();
    }
    
    private int merge(Cell current, Cell next) {
        int cellValue = current.getValue();
        next.setValue(current.getValue()+next.getValue());
        next.setMerged(true);
        next.setModified(true);
        current.setModified(true);
        current.clear();
        updateScore(next.getValue());
        return cellValue;
    }
    
    private void mergeMoves() {
        List<Move> moves = board.getTranslationsLastState();
        for (int j = 0; j < moves.size(); j++) {
            Move move = moves.get(j);
            for (int i = j+1; i < moves.size(); i++) {
                Move move2 = moves.get(i);
                if (move != move2
                        //&& (Math.abs(move.getInitX() - move2.getDestX()) > 1 || Math.abs(move.getInitY() - move2.getDestY()) > 1)
                        && move.getDestX() == move2.getInitX() && move.getDestY() == move2.getInitY()) {
                    move.setDestX(move2.getDestX());
                    move.setDestY(move2.getDestY());
                    moves.remove(i);
                    i--;
                }
            }
        }
    }
    
    private void addMergeMoves() {
        List<Move> moves = board.getTranslationsLastState();
        for (int j = 0; j < moves.size(); j++) {
            Move move = moves.get(j);
            if (move.isMerged()) {
                moves.add(new Move(Move.Type.TRANSLATION, move.getValue(), 
                        move.getMergedAtX(), move.getMergedAtY(), 
                        move.getDestX(), move.getDestY())
                );
            }
        }
    }
    
    @Override
    public void disableFlags() {
         for (List<Cell> row: board.getCells()) {
            for (Cell cell: row) {
                cell.setModified(false);
                cell.setMerged(false);
            }
        }
    }
    
    @Override
    public void disableModifiedFlags() {
         for (List<Cell> row: board.getCells()) {
            for (Cell cell: row) {
                cell.setModified(false);
            }
        }
    }
    
    @Override
    public void disableMergedFlags() {
         for (List<Cell> row: board.getCells()) {
            for (Cell cell: row) {
                cell.setMerged(false);
            }
        }
    }
    
    @Override
    public boolean isBusy() {
        return board.isBusy();
    }
    
    private void finish() {
        disableFlags();
        board.setIsFinished(true);
        board.setbusy(false);
    }
}
