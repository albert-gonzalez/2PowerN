/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tpn.domain.use_case.new_game;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tpn.adapter.manager.BoardManager;
import tpn.domain.entity.Board;
import tpn.domain.entity.Config;

/**
 *
 * @author Albert
 */
public class NewGameUseCaseTest {
    
    public NewGameUseCaseTest() {
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
    public void testSomeMethod() {
        List<Integer> values = new ArrayList();
        values.add(2); values.add(4);
        int size = 4;
        int target = 16;
        Config config = new Config(size, target, values);
        BoardManager boardManager = new BoardManager(new Board());
        Request request = new Request();
        request.size = 4;
        request.target = 16;
        
        NewGameUseCase useCase = new NewGameUseCase(boardManager);
        Response response = useCase.execute(request);
        
        assertEquals(response.board.getConfig().getSize(), size);
        assertEquals(response.board.getConfig().getTarget(), target);
        assertEquals(response.board.getConfig().getFirstValues(), values);
    }
    
}
