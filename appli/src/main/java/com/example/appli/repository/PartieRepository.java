package com.example.appli.repository;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.appli.configuration.CustomProperties;
import com.example.appli.model.Partie;
import com.example.appli.model.Joueur;

@Component
public class PartieRepository extends Repository {

    public PartieRepository(CustomProperties customProperties){
        super(customProperties);
    }
 
    
    // Methode pour récuperer toutes les parties
    public Iterable<Partie> getAllParties(){
        String getContestsUrl = baseUrlApi + "/contests";
      //  RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Partie>> response = restTemplate.exchange(
            getContestsUrl,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<Iterable<Partie>>(){}
        );
        return response.getBody();
    }

    // Méthode pour récupérer une partie
    public Partie getPartieById(Long id){
        String getContestUrl = baseUrlApi + "/contest/" + id;
       // RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Partie> response = restTemplate.exchange(
            getContestUrl,
            HttpMethod.GET,
            null,
            Partie.class
        );
        return response.getBody();
    }

    //Méthode pour ajouter une partie
    public Partie addPartie(String start_date, Integer game_id){
   //     RestTemplate restTemplate = new RestTemplate();
        // Ajout header : Pour contrer l'erreur Bad request 400 qui est lié à un problème d'encodage de la requête qui est envoyé
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // j'ai dans les params 2 elements, je crée un objet map qui va être ensuite utilisé pour le HttpEntity
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("start_date", start_date);
        map.add("game_id", game_id.toString());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<Partie> response = restTemplate.exchange(
            baseUrlApi + "/contest",
            HttpMethod.POST,
            request,
            Partie.class
        );
        return response.getBody();
    }

    //Méthode pour supprimer une partie
    public Boolean deletePartieById(Long id){
        String getContestUrl = baseUrlApi + "/contest/" + id;
   //     RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Boolean> response = restTemplate.exchange(
            getContestUrl,
            HttpMethod.DELETE,
            null,
        Boolean.class
        );
        return response.getBody();
       }

    //Methode pour modifier une partie
    public Partie updatePartie(Partie p) {
        String getContestUrl = baseUrlApi + "/contest/" + p.getId();
     //   RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Partie> request = new HttpEntity<Partie>(p);
        ResponseEntity<Partie> response = restTemplate.exchange(
            getContestUrl,
            HttpMethod.PUT,
            request,
            Partie.class
        );
        return response.getBody();
    }

    //Méthode pour récupérer tous les players
    public List<Joueur> getParticipants(Partie p){
        String getPlayersUrl = baseUrlApi + "/contest/" + p.getId() + "/players";
   //     RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Joueur>> response = this.restTemplate.exchange(
            getPlayersUrl,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Joueur>>(){}
        );
        return response.getBody();
        }

    // Methode PATCH, ou PUT si erreur, on ne met rien dans request entity car on envoit tout dans l'url
    public Partie setVainqueur(Long id, Long idJoueur){
        ResponseEntity<Partie> response = restTemplate.exchange(
            baseUrlApi + "/contest/" + id + "/" + idJoueur,
            HttpMethod.PUT,
            null,
            Partie.class
        );
        return response.getBody();
    }

    //Methode pour Ajouter des participants à une partie
    public Partie addParticipant(Long id, Long idJoueur){
        ResponseEntity<Partie> response = restTemplate.exchange(
            baseUrlApi + "/contest/" + id + "/player/" + idJoueur + "/add",
            HttpMethod.PUT,
            null,
            Partie.class
        );
        return response.getBody();
    }

      //Methode pour supprimer des participants à une partie
    public Partie supprimerParticipant(Long id, Long idJoueur){
        ResponseEntity<Partie> response = restTemplate.exchange(
            baseUrlApi + "/contest/" + id + "/player/" + idJoueur + "/remove",
            HttpMethod.PUT,
            null,
            Partie.class
        );
        return response.getBody();
    }
    
}