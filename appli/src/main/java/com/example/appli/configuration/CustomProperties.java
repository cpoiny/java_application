package com.example.appli.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "com.example.appli")

/* 
* On définit le préfixe des propriétés du fichier application.properties
* Les propriétés de la classe auront le même nom que les propriétes du
* fichier application.properties sans le préfixe
*/
public class CustomProperties {
// je lui donne le mm nom que dans application.properties
    private String apiURL;
}