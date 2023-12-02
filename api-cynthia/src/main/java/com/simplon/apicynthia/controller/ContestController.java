package com.simplon.apicynthia.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.simplon.apicynthia.model.Contest;
import com.simplon.apicynthia.model.Player;
import com.simplon.apicynthia.model.dto.PlayerDTO;
import com.simplon.apicynthia.model.dto.ContestDTO;
import com.simplon.apicynthia.service.ContestService;
import com.simplon.apicynthia.service.GameService;
import com.simplon.apicynthia.service.PlayerService;

@RestController
public class ContestController {

  @Autowired // pour qu'il soit instancié automatiquement, on a pas besoin de créer un new
             // objet , injection de dependance
  private ContestService contestService;

  @Autowired
  private GameService gameService;

  @Autowired
  private PlayerService playerService;

  // Autowired remplace le constructeur pour instancier un nouvel objet
  /*
   * public ContestController() {
   * this.ContestService = new ContestService();
   * }
   */

  /**
   * GET : renvoie tous les contests
   * GetMapping : permet de relier cette méthode 'allContests' à une URL qui sera
   * appelée en méthode HTTP GET.
   */
  @GetMapping("/contests")
  // public Iterable<Contest> allContests(){

  // on modifie Contest en ContestDTO
  public Iterable<ContestDTO> allContests() {

    // On crée notre liste de Contests
    Iterable<Contest> contests = contestService.getAllContests();

    // On déclare et on instancie la liste ContestsDTO
    List<ContestDTO> contestsDTO = new ArrayList<ContestDTO>();

    // On boucle sur Contests pour créer notre liste de string ContestsDTO
    for (Contest contest : contests) {
      contestsDTO.add(new ContestDTO(contest));
    }

    // On retourne notre liste ContestsDTO qu'on vient de créer
    return contestsDTO;
  }

  /**
   * GET :renvoie un contest avec un paramètre dans la route
   */
  @GetMapping("/contest/{id}")
  public ContestDTO contest(@PathVariable("id") long id) {
    Optional<Contest> g = contestService.getContest(id);
    if (g.isPresent()) {

      return new ContestDTO(g.get());
      // return g.get(); // la méthode get() de l'objet optional retourne un objet
      // Contest
    } else {
      return null;
    }
  }

  /**
   * POST : Ajouter un contest
   * Cette annotation est utilisée pour récupérer les données passées dans le
   * corps de la requête HTTP.
   * En méthode HTTP POST, les données sont passées dans le corps de la requête
   * (alors qu’en GET, des données peuvent être passées dans l’URL).
   */
  @PostMapping("/contest")
  // on utilise @RequestParam pour fournir uniquement les paramètres que l'on veut
  // dans postman on utilisera form data au lieu de raw
  public ContestDTO insertContest(@RequestParam String start_date, @RequestParam int game_id,
      @RequestParam Optional<Integer> winner_id) {

    // on déclare un nouvel objet contest
    Contest contest = new Contest();

    // On récupère chaque champ de RequestParam ici start_date
    // La méthode valueOf est une méthode statique, on l'utilise à partir d'une
    // classe et pas d'un objet ici Date.valueOf()
    contest.setStartDate(Date.valueOf((start_date)));

    // on recupere le game_id
    contest.setGame(gameService.getGame(game_id).get());

    // on vérifie si il y a un winner et on récupère son id
    if (winner_id.isPresent()) {
      contest.setWinner(playerService.getOnePlayer(winner_id.get()).get());
    }
    return new ContestDTO(this.contestService.saveContest(contest));
  }

  /**
   * DELETE : Supprimer un contest
   */
  @DeleteMapping("/contest/{id}")
  public boolean deleteContest(@PathVariable("id") long id) {
    Optional<Contest> g = contestService.getContest(id);
    if (g.isPresent()) {
      this.contestService.deleteContest(id);
      return true;
    } else {
      return false;
    }

    /*
     * PUT : Update un contest
     */
  }

  // mettre requestParam
  @PutMapping("/contest/{id}")
  // Contest est l'objet que j'envoie dans ma requête
  public ContestDTO updateContest(@PathVariable("id") long id, @RequestBody Contest contest) {
    Optional<Contest> g = contestService.getContest(id);
    if (g.isPresent()) {
      contest.setId(id);
      // on verifie chaque champ et on regarde si il y a une valeur qui sera mis à
      // jour sur le contest selectionné
      return new ContestDTO(contestService.saveContest(contest));
    }
    return null;
  }

  /**
   * Choisir un vainqueur pour une partie
   * 
   * @param id       : id de la partie
   * @param idPlayer : id du joueur qu'on veut déclarer vainqueur
   * @return
   */
  @PutMapping("/contest/{id}/{id_player}")
  public ContestDTO selectWinner(@PathVariable("id") Long id, @PathVariable("id_player") Long idPlayer)
      throws Exception {
    Optional<Contest> ocontest = contestService.getContest(id);
    Optional<Player> oplayer = playerService.getOnePlayer(idPlayer);
    if (ocontest.isPresent()) {
      Contest contest = ocontest.get();
      if (oplayer.isPresent()) {
        Player player = oplayer.get();

        /* Vérifier que le player fait partie des participants du contest */

        if (contest.getPlayers().contains(player)) {
          contest.setWinner(player);
          return new ContestDTO(contestService.saveContest(contest));
        }
        throw new Exception("Le joueur" + player.getNickname() + " ne fait pas partie des participants");
      }
      throw new Exception("Il n'y a pas de joueur avec l'identifiant " + idPlayer);
    }
    throw new Exception("Il n'y a pas de partie avec l'identifiant " + id);
  }

  /*
   ** Get tous les players()
   */
  @GetMapping("/contest/{id}/players")
  public List<PlayerDTO> getPlayers(@PathVariable("id") Long id) {
    Optional<Contest> ocontest = contestService.getContest(id);
    if (ocontest.isPresent()) {
      List<Player> players = ocontest.get().getPlayers();
      List<PlayerDTO> playersDTO = new ArrayList<PlayerDTO>();

      for (Player player : players) {
        playersDTO.add(new PlayerDTO(player));
      }
      return playersDTO;
    }
    return null;
  }

  /*
   * Ajouter des participants à une partie
   */
  @PutMapping("/contest/{id}/player/{id_player}/add")
  public ContestDTO addPlayers(@PathVariable("id") Long id, @PathVariable("id_player") Long idPlayer) throws Exception {
    
    // Le Contest en get est un optional donc on doit passer par optional pour le recupérer
    Optional<Contest> ocontest = contestService.getContest(id);
    // Idem pour le player
    Optional<Player> oplayer = playerService.getOnePlayer(idPlayer);

    // On vérifie si le contest existe
    if (ocontest.isPresent()) {

      // on récupére le contest en type Contest
      Contest contest = ocontest.get();

      //On vérifie si le player existe
      if (oplayer.isPresent()) {

        //On instancie une nouvelle liste vide de player
        List<Player> listPlayers = new ArrayList<Player>();

        // On récupère le player
        Player player = oplayer.get();

        //On vérifie si le player existe dans la liste des participants au contest
        if (contest.getPlayers().contains(player)) {

          throw new Exception("Le joueur " + player.getNickname() + " fait partie des participants");
        
        } else {

          listPlayers = contest.getPlayers();

          // on ajouter un player dans la liste des player coté contest
          listPlayers.add(player);

          List<Contest> listContest = player.getContests();

          //On ajoute un contest dans la liste des contests coté player
          listContest.add(contest);

          //on save le contest
          return new ContestDTO(contestService.saveContest(contest));
        }
      }
      throw new Exception("Le joueur n'existe pas");
    }
    //On retourne une erreur de type HttpStatus
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La partie n'existe pas!!!!!!!!");
  }

/*
 * Supprimer un participant
 */
@PutMapping("/contest/{id}/player/{id_player}/remove")
public ContestDTO removePlayers(@PathVariable("id") Long id, @PathVariable("id_player") Long idPlayer) throws Exception{
  Optional<Contest> ocontest = contestService.getContest(id);
  Optional<Player> oplayer = playerService.getOnePlayer(idPlayer);

  //Je vérifie si ma partie et mon joueur exsite et si le joueur fait bien partie des participants de la partie
    if (ocontest.isPresent()) {
      Contest contest = ocontest.get();
      if (oplayer.isPresent()) {
        Player player = oplayer.get();
        if(contest.getPlayers().contains(player)){

          //Je mets à jour coté Contest en retirant un joueur de la liste des players
          List<Player> listPlayers = contest.getPlayers();
          int index = listPlayers.indexOf(player);
          listPlayers.remove(index);
          
          // Je mets à jour coté Player en retirant une partie de la liste des contests
          List<Contest> listContest = player.getContests();
          int indexContest =  listContest.indexOf(contest);
          listContest.remove(indexContest);

          //Je sauvagarde la partie
          return new ContestDTO(contestService.saveContest(contest));

        }
        throw new Exception("Le joueur " + player.getNickname() + " ne fait pas partie des participants");
}
throw new Exception("Le joueur n'existe pas.");

}
throw new Exception("La partie n'existe pas");
}

}
