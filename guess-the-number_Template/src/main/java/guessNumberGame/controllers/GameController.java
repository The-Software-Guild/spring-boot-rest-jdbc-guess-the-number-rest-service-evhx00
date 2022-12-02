package guessNumberGame.controllers;

import guessNumberGame.Service.GameService;
import guessNumberGame.data.GameDao;
import guessNumberGame.data.RoundDao;
import guessNumberGame.models.Game;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import guessNumberGame.models.Round;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="/api", method=RequestMethod.GET)
public class GameController {


    private final GameDao gameDao;
    private final RoundDao roundDao;


    public GameController(GameDao gameDao, RoundDao roundDao) {
       //implement
        this.gameDao = gameDao;
        this.roundDao = roundDao;
    }

    @PostMapping("/begin") // 127.0.0.1:8080/api/begin
    @ResponseStatus(HttpStatus.CREATED)
    public Game create() {
        //implement create gameService object and game object
        GameService gameService = new GameService();
        Game game = gameService.newGame();
        //add to database
        gameDao.add(game);
        //getGame will hide answer before returning it to the user
        return gameService.getGames(game);
    }


    @PostMapping("/guess") // 127.0.0.1:8080/api/guess
    @ResponseStatus(HttpStatus.CREATED)
    public Round guessNumber(@RequestBody Round body) {
       //implement
        Game game = gameDao.findById(body.getGameId());
        GameService gameService = new GameService();
        Round round = gameService.guessNumber(game,body.getGuess(), gameDao);
        return roundDao.add(round);
    }

    @GetMapping("/game") // 127.0.0.1:8080/api/game
    public List<Game> all() {
      //implement
        List<Game> games = gameDao.getAll();
        GameService gameService = new GameService();
        gameService.getAllGames(games);
        return games;
    }

    @GetMapping("game/{id}") // 127.0.0.1:8080/api/game/{id}
    public Game getGameById(@PathVariable int id) {
        //implement
        Game game = gameDao.findById(id);
        GameService gameService = new GameService();
        return gameService.getGames(game);
    }

    @GetMapping("rounds/{gameId}") // 127.0.0.1:8080/api/rounds/{game_id}
    //implement
    public List<Round> getAllRoundsByID(@PathVariable int gameId) {
        return roundDao.getAllOfGame(gameId);
    }

}