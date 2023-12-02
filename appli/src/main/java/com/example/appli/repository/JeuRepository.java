package com.example.appli.repository;

import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.example.appli.configuration.CustomProperties;
import com.example.appli.model.Jeu;

@Component
public class JeuRepository extends Repository{
   

    // constructeur
    public JeuRepository(CustomProperties customProperties){
        super(customProperties);
    }
    
    // Methode tous les les jeux
    public Iterable<Jeu> getJeux(){
        String getGameUrl = baseUrlApi + "/games";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Jeu>> response = restTemplate.exchange(
            getGameUrl,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<Iterable<Jeu>>(){}
        );
        return response.getBody();
    }

    //Methode un jeu
    public Jeu getJeuById(long id) {
        String getJeuUrl = baseUrlApi + "/game/" + id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Jeu> response = restTemplate.exchange(
            getJeuUrl,
            HttpMethod.GET,
            null,
            Jeu.class
        );
        return response.getBody();
    }

    //Methode ajouter un jeu
    public Jeu addJeu(Jeu j){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Jeu> request = new HttpEntity<Jeu>(j);
        ResponseEntity<Jeu> response = restTemplate.exchange(
            baseUrlApi + "/game",
            HttpMethod.POST,
            request,
            Jeu.class
        );
        return response.getBody();
 
    }

    //Methode PUT pour modifier un jeu
    public Jeu modifyJeu(Jeu j, Long id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Jeu> request = new HttpEntity<Jeu>(j);
        ResponseEntity<Jeu> response = restTemplate.exchange(
            baseUrlApi + "/game/" + id,
            HttpMethod.PUT,
            request,
            Jeu.class
        );
        return response.getBody();
    }


  
    
    // supprimer un joueur
     public boolean deleteGamebyId(Long id) {

        String getGameUrl = baseUrlApi + "/game/" + id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Boolean> response = restTemplate.exchange(
            // URL de la requête
            getGameUrl,
            // Méthode
            HttpMethod.DELETE,
            // Données que l'on envoit
            null,
            Boolean.class
        );

        return response.getBody();
    }


    public List<Jeu> searchJeu(String mot){       
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity <List<Jeu>> response = restTemplate.exchange(
            baseUrlApi + "/game/search?word=" + mot,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Jeu>>(){}
        );
        return response.getBody(); 
    }
}