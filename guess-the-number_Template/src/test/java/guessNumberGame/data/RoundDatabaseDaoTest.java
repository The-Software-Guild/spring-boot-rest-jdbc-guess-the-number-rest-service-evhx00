package guessNumberGame.data;


import guessNumberGame.data.GameDao;
import guessNumberGame.data.RoundDao;
import junit.framework.TestCase;
import guessNumberGame.Service.GameService;
import guessNumberGame.TestApplicationConfiguration;
import guessNumberGame.models.Game;
import guessNumberGame.models.Round;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class RoundDatabaseDaoTest extends TestCase {
    @Autowired
    RoundDao roundDao;

    @Autowired
    GameDao gameDao;
    public RoundDatabaseDaoTest ()
    {
        
    }
    
    @Before
    public void setUp() {
        List<Round> rounds = roundDao.getAll();
        for(Round round : rounds) {
            roundDao.deleteById(round.getId());
        }

        List<Game> games = gameDao.getAll();
        for(Game game : games) {
            gameDao.deleteById(game.getGameId());
        }
    }

    @Test
    public void testAdd() {
        GameService gameService = new GameService();
        Game game = gameService.newGame();
        Round round = new Round();
        gameDao.add(game);
        round.setGameId(game.getGameId());
        gameService.setTimeStamp(round);
        round.setGuess("1234");
        round.setGuessResult("e:2:p:1");
        roundDao.add(round);

        Round fromDao = roundDao.findById(round.getId());
        assertEquals(round.getId(), fromDao.getId());
    }

    @Test
    public void testGetAll() {
        GameService gameService = new GameService();
        Game game = gameService.newGame();
        Game game2 = gameService.newGame();
        Round round = new Round();
        Round round2 = new Round();

        gameDao.add(game);
        gameDao.add(game2);
        round.setGuess("1233");
        round.setGameId(game.getGameId());
        round2.setGuess("1234");
        round2.setGameId(game2.getGameId());
        roundDao.add(round);
        roundDao.add(round2);

        List<Round> rounds = roundDao.getAll();
       assertEquals(2, rounds.size());
    }

    @Test
    public void testGetAllOfGame() {
         //implement
        GameService gameService = new GameService();
        Game game1 = gameService.newGame();
        Game game2 = gameService.newGame();

        gameDao.add(game1);
        gameDao.add(game2);
        Round round = new Round();
        Round round2 = new Round();
        round.setGuess("1233");
        round2.setGuess("1234");
        round.setGameId(game1.getGameId());
        round2.setGameId(game2.getGameId());
        roundDao.add(round);
        roundDao.add(round2);

        List<Game> games = gameDao.getAll();
        assertEquals(2, games.size());
    }

}
