package tpn.executable;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import tpn.adapter.manager.BoardManager;
import tpn.domain.entity.Board;
import tpn.domain.use_case.make_movement.GameOverException;
import tpn.domain.use_case.make_movement.MakeMovementUseCase;
import tpn.domain.use_case.make_movement.UnexpectedException;
import tpn.domain.use_case.make_movement.WinException;
import tpn.domain.use_case.new_game.NewGameUseCase;

public class Executable {
    
    private final static String UP = "w";
    private final static String DOWN = "s";
    private final static String LEFT = "a";
    private final static String RIGHT = "d";
    private final static String EXIT = "exit";
    
    
    public static void main(String args[]) {
        Board board = new Board();
        BoardManager boardManager = new BoardManager(board);
        NewGameUseCase newGame = new NewGameUseCase(boardManager);
        MakeMovementUseCase makeMovement = new MakeMovementUseCase(boardManager);
        
        tpn.domain.use_case.new_game.Request newGameRequest = new tpn.domain.use_case.new_game.Request();
        newGameRequest.size = 4; newGameRequest.target = 128;
        newGame.execute(newGameRequest);
        
        tpn.domain.use_case.make_movement.Request moveRequest = new tpn.domain.use_case.make_movement.Request();
        Scanner input=new Scanner(System.in);
        String nayme="";
        String instruction;
        
        System.out.println();
        System.out.println("/------- 2POWERN -------/\n");
        System.out.println(board+"\n\n");
        
        while(!(instruction = input.next()).equals(EXIT)) {
            try {
                switch (instruction) {
                    case UP:
                        moveRequest.direction = BoardManager.UP;
                        makeMovement.execute(moveRequest);
                        System.out.println(board);
                        break;
                    case DOWN:
                        moveRequest.direction = BoardManager.DOWN;
                        makeMovement.execute(moveRequest);
                        System.out.println(board);
                        break;
                    case LEFT:
                        moveRequest.direction = BoardManager.LEFT;
                        makeMovement.execute(moveRequest);
                        System.out.println(board);
                        break;
                    case RIGHT:
                        moveRequest.direction = BoardManager.RIGHT;
                        makeMovement.execute(moveRequest);
                        System.out.println(board);
                        break;
                }
            } catch (UnexpectedException ex) {
                Logger.getLogger(Executable.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(0);
            } catch (GameOverException ex) {
                System.out.println("YOU LOSE!");
                System.out.println(board);
                System.exit(0);
            } catch (WinException ex) {
                System.out.println("YOU WIN!");
                System.out.println(board);
                System.exit(0);
            }
            
        }

    }
}
