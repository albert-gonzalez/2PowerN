package tpn.domain.use_case.new_game;

import tpn.domain.manager.BoardManager;

public class NewGameUseCase {
    
    private final BoardManager boardManager;
    
    public NewGameUseCase(BoardManager boardManager) {
        this.boardManager = boardManager;
    }
    
    public Response execute(Request request) {
        boardManager.init(request.size, request.target);
        Response response = new Response();
        response.board = boardManager.getBoard();
        
        return response;
    }
    
}
