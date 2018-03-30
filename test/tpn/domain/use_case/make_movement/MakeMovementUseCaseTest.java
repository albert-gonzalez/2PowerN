/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tpn.domain.use_case.make_movement;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tpn.adapter.manager.BoardManager;
import tpn.domain.entity.Board;
import tpn.domain.entity.Cell;
import tpn.domain.entity.Move;

/**
 *
 * @author Albert
 */
public class MakeMovementUseCaseTest {
    
    public MakeMovementUseCaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testMoveRight() {
        Board board = initBoard1();
        Board expectedBoard = resultBoard1MoveRight();
        Board resultBoard = testMove(board, BoardManager.RIGHT);
        List<Move> list = resultMovesBoard1Right();
        System.out.println(list.toString());
        System.out.println(board.getTranslationsLastState());
        assertTrue(board.equals(expectedBoard));
        assertTrue(list.equals(board.getTranslationsLastState()));
    }
    
    @Test
    public void testMoveLeft() {
        Board board = initBoard1();
        Board expectedBoard = resultBoard1MoveLeft();
        Board resultBoard = testMove(board, BoardManager.LEFT);
        
        assertTrue(board.equals(expectedBoard));
    }
    
    @Test
    public void testMoveUp() {
        Board board = initBoard1();
        Board expectedBoard = resultBoard1MoveUp();
        Board resultBoard = testMove(board, BoardManager.UP);

        assertTrue(board.equals(expectedBoard));
    }
    
    @Test
    public void testMoveDown() {
        Board board = initBoard1();
        Board expectedBoard = resultBoard1MoveDown();
        Board resultBoard = testMove(board, BoardManager.DOWN);
        
        assertTrue(board.equals(expectedBoard));
    }
    
    @Test(expected=GameOverException.class)
    public void testGameOver() throws UnexpectedException, GameOverException, WinException {
        Board board = initBoardNoMovements();
        
        testMoveExceptions(board, BoardManager.DOWN);
        assertEquals(board.isFinished(), true);
    }
    
    @Test(expected=WinException.class)
    public void testWin() throws UnexpectedException, GameOverException, WinException {
        Board board = initBoard1();
        board.getConfig().setTarget(16);
        testMoveExceptions(board, BoardManager.LEFT);
    }
    
    private Board testMove(Board board, int direction) {
        Board resultBoard = null;
        BoardManager boardManager = new BoardManager(board);
        MakeMovementUseCase useCase = new MakeMovementUseCase(boardManager);
        Request request = new Request();
        request.direction = direction;
        try {
            resultBoard = useCase.execute(request).board;
            
            Cell randomCell = resultBoard.getLastRandomFilledCell();
            Move randomMove = new Move(Move.Type.TRANSLATION, randomCell.getValue(),
            randomCell.getX(), randomCell.getY(), randomCell.getX(), randomCell.getY());
            for (int i = 0; i < board.getTranslationsLastState().size(); i++) {
                Move move = board.getTranslationsLastState().get(i);
                if (move.equals(randomMove)) {
                    board.getTranslationsLastState().remove(move);
                }
            }
            randomCell.setValue(0);
            
        } catch (UnexpectedException ex) {
            Logger.getLogger(MakeMovementUseCaseTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GameOverException ex) {
            Logger.getLogger(MakeMovementUseCaseTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WinException ex) {
            Logger.getLogger(MakeMovementUseCaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultBoard;
    }
    
    private Board testMoveExceptions(Board board, int direction) 
            throws UnexpectedException, GameOverException, WinException {
        Board resultBoard = null;
        BoardManager boardManager = new BoardManager(board);
        MakeMovementUseCase useCase = new MakeMovementUseCase(boardManager);
        Request request = new Request();
        request.direction = direction;
        resultBoard = useCase.execute(request).board;
        resultBoard.getLastRandomFilledCell().setValue(0);
        return resultBoard;
    }

    private Board initBoard1() {
        Board board = new Board();
        Cell cell;
        List<List<Cell>> cells;
        cells = new ArrayList();
        cells.add(new ArrayList());
        cells.add(new ArrayList());
        cells.add(new ArrayList());
        cells.add(new ArrayList());
        cells.get(0).add(new Cell(2, 0, 0));
        cells.get(0).add(new Cell(2, 1, 0));
        cells.get(0).add(new Cell(2, 2, 0));
        cells.get(0).add(new Cell(2, 3, 0));
        cells.get(1).add(new Cell(2, 0, 1));
        cells.get(1).add(new Cell(2, 1, 1));
        cells.get(1).add(new Cell(0, 2, 1));
        cells.get(1).add(new Cell(0, 3, 1));
        cells.get(2).add(new Cell(0, 0, 2));
        cells.get(2).add(new Cell(2, 1, 2));
        cells.get(2).add(new Cell(0, 2, 2));
        cells.get(2).add(new Cell(4, 3, 2));
        cells.get(3).add(new Cell(8, 0, 3));
        cells.get(3).add(new Cell(8, 1, 3));
        cells.get(3).add(new Cell(0, 2, 3));
        cells.get(3).add(new Cell(0, 3, 3));
        board.setCells(cells);
        board.setPreviousStateCells(initCells(4));
        board.setIsFinished(false);
        System.out.println(board);
        return board;
    }
    
    private Board resultBoard1MoveRight() {
        Board board = new Board();
        Cell cell;
        List<List<Cell>> cells;
        List<Cell> freeCells = new ArrayList();
        cells = new ArrayList();
        cells.add(new ArrayList());
        cells.add(new ArrayList());
        cells.add(new ArrayList());
        cells.add(new ArrayList());
        cells.get(0).add(new Cell(0, 0, 0));
        cells.get(0).add(new Cell(0, 1, 0));
        cells.get(0).add(new Cell(4, 2, 0));
        cells.get(0).add(new Cell(4, 3, 0));
        cells.get(1).add(new Cell(0, 0, 1));
        cells.get(1).add(new Cell(0, 1, 1));
        cells.get(1).add(new Cell(0, 2, 1));
        cells.get(1).add(new Cell(4, 3, 1));
        cells.get(2).add(new Cell(0, 0, 2));
        cells.get(2).add(new Cell(0, 1, 2));
        cells.get(2).add(new Cell(2, 2, 2));
        cells.get(2).add(new Cell(4, 3, 2));
        cells.get(3).add(new Cell(0, 0, 3));
        cells.get(3).add(new Cell(0, 1, 3));
        cells.get(3).add(new Cell(0, 2, 3));
        cells.get(3).add(new Cell(16, 3, 3));
        board.setCells(cells);
        
        freeCells.add(cells.get(0).get(0));
        freeCells.add(cells.get(0).get(1));
        freeCells.add(cells.get(1).get(0));
        freeCells.add(cells.get(1).get(1));
        freeCells.add(cells.get(1).get(2));
        freeCells.add(cells.get(2).get(0));
        freeCells.add(cells.get(2).get(1));
        freeCells.add(cells.get(3).get(0));
        freeCells.add(cells.get(3).get(1));
        freeCells.add(cells.get(3).get(2));
        
        board.setFreeCells(freeCells);
        board.setIsFinished(false);
        return board;
    }
    
    private List resultMovesBoard1Right() {
        List<Move> moves = new ArrayList();
        moves.add(new Move(Move.Type.TRANSLATION, 4, 0, 0, 2, 0));
        moves.add(new Move(Move.Type.TRANSLATION, 4, 2, 0, 3, 0));
        moves.add(new Move(Move.Type.TRANSLATION, 4, 0, 1, 3, 1));
        moves.add(new Move(Move.Type.TRANSLATION, 2, 1, 2, 2, 2));
        moves.add(new Move(Move.Type.TRANSLATION, 16, 0, 3, 3, 3));
        
        return moves;
    }
    
    private List resultMovesBoard1Left() {
        List<Move> moves = new ArrayList();
        moves.add(new Move(Move.Type.TRANSLATION, 4, 0, 0, 2, 0));
        moves.add(new Move(Move.Type.TRANSLATION, 4, 2, 0, 3, 0));
        moves.add(new Move(Move.Type.TRANSLATION, 4, 0, 1, 3, 1));
        moves.add(new Move(Move.Type.TRANSLATION, 2, 1, 2, 2, 2));
        moves.add(new Move(Move.Type.TRANSLATION, 16, 0, 3, 3, 3));
        
        return moves;
    }
    
    private Board resultBoard1MoveLeft() {
        Board board = new Board();
        Cell cell;
        List<List<Cell>> cells;
        List<Cell> freeCells = new ArrayList();
        cells = new ArrayList();
        cells.add(new ArrayList());
        cells.add(new ArrayList());
        cells.add(new ArrayList());
        cells.add(new ArrayList());
        cells.get(0).add(new Cell(4 , 0, 0));
        cells.get(0).add(new Cell(4 , 1, 0));
        cells.get(0).add(new Cell(0 , 2, 0));
        cells.get(0).add(new Cell(0 , 3, 0));
        cells.get(1).add(new Cell(4 , 0, 1));
        cells.get(1).add(new Cell(0 , 1, 1));
        cells.get(1).add(new Cell(0 , 2, 1));
        cells.get(1).add(new Cell(0 , 3, 1));
        cells.get(2).add(new Cell(2 , 0, 2));
        cells.get(2).add(new Cell(4 , 1, 2));
        cells.get(2).add(new Cell(0 , 2, 2));
        cells.get(2).add(new Cell(0 , 3, 2));
        cells.get(3).add(new Cell(16, 0, 3));
        cells.get(3).add(new Cell(0 , 1, 3));
        cells.get(3).add(new Cell(0 , 2, 3));
        cells.get(3).add(new Cell(0 , 3, 3));
        board.setCells(cells);
        
        freeCells.add(cells.get(0).get(2));
        freeCells.add(cells.get(0).get(3));
        freeCells.add(cells.get(1).get(1));
        freeCells.add(cells.get(1).get(2));
        freeCells.add(cells.get(1).get(3));
        freeCells.add(cells.get(2).get(2));
        freeCells.add(cells.get(2).get(3));
        freeCells.add(cells.get(3).get(1));
        freeCells.add(cells.get(3).get(2));
        freeCells.add(cells.get(3).get(3));
        
        board.setFreeCells(freeCells);
        board.setIsFinished(false);
        return board;
    }
    
    private Board resultBoard1MoveUp() {
        Board board = new Board();
        Cell cell;
        List<List<Cell>> cells;
        List<Cell> freeCells = new ArrayList();
        cells = new ArrayList();
        cells.add(new ArrayList());
        cells.add(new ArrayList());
        cells.add(new ArrayList());
        cells.add(new ArrayList());
        cells.get(0).add(new Cell(4, 0, 0));
        cells.get(0).add(new Cell(2, 1, 0));
        cells.get(0).add(new Cell(2, 2, 0));
        cells.get(0).add(new Cell(2, 3, 0));
        cells.get(1).add(new Cell(8, 0, 1));
        cells.get(1).add(new Cell(4, 1, 1));
        cells.get(1).add(new Cell(0, 2, 1));
        cells.get(1).add(new Cell(4, 3, 1));
        cells.get(2).add(new Cell(0, 0, 2));
        cells.get(2).add(new Cell(8, 1, 2));
        cells.get(2).add(new Cell(0, 2, 2));
        cells.get(2).add(new Cell(0, 3, 2));
        cells.get(3).add(new Cell(0, 0, 3));
        cells.get(3).add(new Cell(0, 1, 3));
        cells.get(3).add(new Cell(0, 2, 3));
        cells.get(3).add(new Cell(0, 3, 3));
        board.setCells(cells);
        
        freeCells.add(cells.get(0).get(0));
        freeCells.add(cells.get(0).get(1));
        freeCells.add(cells.get(1).get(0));
        freeCells.add(cells.get(1).get(1));
        freeCells.add(cells.get(1).get(2));
        freeCells.add(cells.get(2).get(0));
        freeCells.add(cells.get(2).get(1));
        freeCells.add(cells.get(3).get(0));
        freeCells.add(cells.get(3).get(1));
        freeCells.add(cells.get(3).get(2));
        
        board.setFreeCells(freeCells);
        board.setIsFinished(false);
        return board;
    }
    
    private Board resultBoard1MoveDown() {
        Board board = new Board();
        Cell cell;
        List<List<Cell>> cells;
        List<Cell> freeCells = new ArrayList();
        cells = new ArrayList();
        cells.add(new ArrayList());
        cells.add(new ArrayList());
        cells.add(new ArrayList());
        cells.add(new ArrayList());
        cells.get(0).add(new Cell(0, 0, 0));
        cells.get(0).add(new Cell(0, 1, 0));
        cells.get(0).add(new Cell(0, 2, 0));
        cells.get(0).add(new Cell(0, 3, 0));
        cells.get(1).add(new Cell(0, 0, 1));
        cells.get(1).add(new Cell(4, 1, 1));
        cells.get(1).add(new Cell(0, 2, 1));
        cells.get(1).add(new Cell(0, 3, 1));
        cells.get(2).add(new Cell(4, 0, 2));
        cells.get(2).add(new Cell(2, 1, 2));
        cells.get(2).add(new Cell(0, 2, 2));
        cells.get(2).add(new Cell(2, 3, 2));
        cells.get(3).add(new Cell(8, 0, 3));
        cells.get(3).add(new Cell(8, 1, 3));
        cells.get(3).add(new Cell(2, 2, 3));
        cells.get(3).add(new Cell(4, 3, 3));
        board.setCells(cells);
        
        freeCells.add(cells.get(0).get(0));
        freeCells.add(cells.get(0).get(1));
        freeCells.add(cells.get(0).get(2));
        freeCells.add(cells.get(0).get(3));
        freeCells.add(cells.get(1).get(0));
        freeCells.add(cells.get(1).get(2));
        freeCells.add(cells.get(1).get(3));
        freeCells.add(cells.get(2).get(2));
        
        board.setFreeCells(freeCells);
        board.setIsFinished(false);
        return board;
    }
    
    private Board initBoardNoMovements() {
        Board board = new Board();
        Cell cell;
        List<List<Cell>> cells;
        cells = new ArrayList();
        cells.add(new ArrayList());
        cells.add(new ArrayList());
        cells.add(new ArrayList());
        cells.add(new ArrayList());
        cells.get(0).add(new Cell(8 , 0, 0));
        cells.get(0).add(new Cell(16, 1, 0));
        cells.get(0).add(new Cell(8 , 2, 0));
        cells.get(0).add(new Cell(8 , 3, 0));
        cells.get(1).add(new Cell(16, 0, 1));
        cells.get(1).add(new Cell(8 , 1, 1));
        cells.get(1).add(new Cell(16, 2, 1));
        cells.get(1).add(new Cell(16, 3, 1));
        cells.get(2).add(new Cell(8 , 0, 2));
        cells.get(2).add(new Cell(16, 1, 2));
        cells.get(2).add(new Cell(8 , 2, 2));
        cells.get(2).add(new Cell(8 , 3, 2));
        cells.get(3).add(new Cell(16, 0, 3));
        cells.get(3).add(new Cell(8 , 1, 3));
        cells.get(3).add(new Cell(16, 2, 3));
        cells.get(3).add(new Cell(0 , 3, 3));
        board.setCells(cells);
        board.setPreviousStateCells(initCells(4));
        board.setIsFinished(false);
        return board;
    }
    
    private List<List<Cell>> initCells(int size) {
        List<List<Cell>> cells = new ArrayList();
        for (int i = 0; i < size; i++) {
            cells.add(new ArrayList());
            for (int j = 0; j < size; j++) {
                cells.get(i).add(new Cell(j, i));
            }
        }
        return cells;
    }
    
}
