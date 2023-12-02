package com.simplon.apicynthia.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.simplon.apicynthia.model.Game;
import com.simplon.apicynthia.model.dto.GameDTO;
import com.simplon.apicynthia.service.GameService;

@RestController
public class GameController {

    @Autowired // pour qu'il soit instancié automatiquement, on a pas besoin de créer un new objet , injection de dependance
    private GameService gameService;
 
// Autowired remplace le constructeur pour instancier un nouvel objet
 /*
public GameController() {
this.gameService = new GameService();
  }
*/ 

/**
 * GET : renvoie tous les jeux
 * GetMapping : permet de relier cette méthode 'allGames' à une URL qui sera appelée en méthode HTTP GET.
*/
  @GetMapping("/games")
  // public Iterable<Game> allGames(){

    // on modifie Game en GameDTO
    public Iterable<GameDTO>allGames() {

      // On crée notre liste de games
      Iterable<Game> games = gameService.getAllGames();

      // On déclare et on instancie la liste gamesDTO 
      List<GameDTO> gamesDTO = new ArrayList<GameDTO>();

      // On boucle sur games pour créer notre liste de string gamesDTO
      for (Game game : games){
        gamesDTO.add(new GameDTO(game));
      }

      // On retourne notre liste gamesDTO qu'on vient de créer
    return gamesDTO;
  }


/**
 * GET :renvoie un jeu avec un paramètre dans la route
*/
   @GetMapping("/game/{id}")
   public GameDTO game(@PathVariable("id") long id) {
    Optional<Game> g = gameService.getGame(id);
    if (g.isPresent()){

      return new GameDTO(g.get());
      //return g.get(); // la méthode get() de l'objet optional retourne un objet game
    } else {
      return null;
    }
  }


/**
 * POST : Ajouter un jeu
* Cette annotation est utilisée pour récupérer les données passées dans le corps de la requête HTTP. 
* En méthode HTTP POST, les données sont passées dans le corps de la requête 
* (alors qu’en GET, des données peuvent être passées dans l’URL).
*/
  @PostMapping("/game") 
  public GameDTO insertGame(@RequestBody Game g){
    return new GameDTO(this.gameService.saveGame(g));
  }


  /**
   * DELETE : Supprimer un jeu
   */
   @DeleteMapping("/game/{id}")
   public boolean deleteGame(@PathVariable("id") long id) {
    Optional<Game> g = gameService.getGame(id);
    if (g.isPresent()){
      this.gameService.deleteGame(id);
      return true;
    } else {
      return false;
    }
   }


   @PutMapping("/game/{id}")
   // game est l'objet que j'envoie dans ma requête
   public GameDTO updateGame(@PathVariable("id") long id, @RequestBody Game game){
    Optional<Game> g = gameService.getGame(id);
    if (g.isPresent()){
      // gameToUpdate est le jeu que l'on récupère de la BDD
      Game gameToUpdate = g.get();
      // on verifie chaque champ et on regarde si il y a une valeur qui sera mis à jour sur le jeu selectionné
      if (game.getTitle() !=null){
        gameToUpdate.setTitle(game.getTitle());
      }
      if (game.getMin() != null) {
        gameToUpdate.setMin(game.getMin());
      }
      if (game.getMax() != null) {
        gameToUpdate.setMax(game.getMax());
      }
      return new GameDTO(gameService.saveGame(gameToUpdate));
   }
   return null;
}

// Requete search
 @GetMapping("/game/search")
 public List<GameDTO> search(@RequestParam String word) {

    List<Game> games = gameService.searchTitle(word);
    List <GameDTO> gameDTOs = new ArrayList<GameDTO>();

    for (Game game : games){
      gameDTOs.add(new GameDTO(game));
    }
    return gameDTOs;
}

}
