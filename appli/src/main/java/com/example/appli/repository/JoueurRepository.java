package com.example.appli.repository;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.appli.configuration.CustomProperties;
import com.example.appli.model.Joueur;

@Component
public class JoueurRepository extends Repository {

    public JoueurRepository(CustomProperties customProperties){
        super(customProperties);
    }


    //Méthode pour récupérer tous les joueurs
    public Iterable<Joueur> getAllJoueurs(){
     //   String baseUrlApi = properties.getApiURL();
        String getPlayerUrl = baseUrlApi + "/players";

        /*
         * L'objet de la classe RestTemplate fait des requêtes HTTP et
         * convertit le JSON retourné par la requête en objet JAVA
         */
        RestTemplate restTemplate = new RestTemplate();

        /* Méthode exchange génère une requête HTTP */
        ResponseEntity<Iterable<Joueur>> response = restTemplate.exchange(
            /* URL de la requête */
            getPlayerUrl,
            /* Methode de la requête */
            HttpMethod.GET,
            /* Est ce qu'on envoit des données ou pas, pour l'instant c'est null */
            null,
            /* Classe particulière de Spring vu que c'est un iterable List */
            /* Type qui est retourné */
            new ParameterizedTypeReference<Iterable<Joueur>>() {}
        );

        /* On recupére le corps de la réponse qui est retournée grace au getBody() */
        return response.getBody();     
    }
    
    // Méthode pour récupérer un joueur par id
    public Joueur getJoueurById(long id){
      //  String baseUrlApi = properties.getApiURL();
        String getPlayerUrl = baseUrlApi + "/player/" + id;

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Joueur> response = restTemplate.exchange(
        // url de la requete
        getPlayerUrl,
        //requete de type GET
        HttpMethod.GET,
        // on envoie aucune donnée
        null,
        //permet de recupérer le nom de la classe ici Joueur
        Joueur.class
        );
        return response.getBody();
    }


    // Ajouter un joueur
    public Joueur addJoueur(Joueur e) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Joueur> request = new HttpEntity<Joueur>(e);
        ResponseEntity<Joueur> response = restTemplate.exchange(
            baseUrlApi + "/player",
            HttpMethod.POST,
            request,
            Joueur.class
        );
        return response.getBody();
    }

    // supprimer un joueur
    public Boolean deleteJoueurById(long id){
        String getPlayerUrl = baseUrlApi + "/player/" + id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Boolean> response = restTemplate.exchange(
            // URL de la requête
            getPlayerUrl,
            // Méthode
            HttpMethod.DELETE,
            // Données que l'on envoit
            null,
            Boolean.class
        );

        return response.getBody();
    }


      // Modifier un joueur
    public Joueur modifyJoueur(Long id, Joueur j) {
        String getPlayerUrl = baseUrlApi + "/player/" + id;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Joueur> request = new HttpEntity<Joueur>(j);
        ResponseEntity<Joueur> response = restTemplate.exchange(
            getPlayerUrl,
            HttpMethod.PUT,
            request,
            Joueur.class
        );
        return response.getBody();
    }


}