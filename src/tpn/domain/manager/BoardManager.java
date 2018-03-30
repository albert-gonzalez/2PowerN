package tpn.domain.manager;

import tpn.domain.use_case.make_movement.UnknownInstructionException;
import tpn.domain.entity.Board;
import tpn.domain.use_case.make_movement.GameOverException;
import tpn.domain.use_case.make_movement.WinException;

public interface BoardManager {
    
    public static final int RIGHT = 1;
    public static final int LEFT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;
    
    public void init(int size, int target);
    public void move(int direction) throws UnknownInstructionException;
    public void fillRandomCell();
    public void checkGameOver() throws GameOverException;
    public void checkWin() throws WinException;
    public Board getBoard();
    public boolean isStateChanged();
    public void disableFlags();
    public void disableModifiedFlags();
    public void disableMergedFlags();
    public boolean isBusy();
}
