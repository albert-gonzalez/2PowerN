package tpn.domain.use_case.make_movement;

import tpn.domain.manager.BoardManager;

public class MakeMovementUseCase {
    
    private final BoardManager boardManager;
    
    public MakeMovementUseCase(BoardManager boardManager)
    {
        this.boardManager = boardManager;
    }
    
    public Response execute(Request request) throws UnexpectedException, GameOverException, WinException
    {
        Response response = new Response();
        try {
            boardManager.move(request.direction);
            if (boardManager.isStateChanged()) {
                boardManager.fillRandomCell();
            }
            
            
            boardManager.checkWin();
            boardManager.checkGameOver();
            
            response.board = boardManager.getBoard();
        } catch(UnknownInstructionException e) {
            throw new UnexpectedException(e.getMessage());
        }
        
        return response;
    }
    
}
