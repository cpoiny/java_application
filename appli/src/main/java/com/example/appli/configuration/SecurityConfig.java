package com.example.appli.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;


    // @Bean mis sur une méthode normalement c'est sur une classe
    @Bean
    PasswordEncoder passwordEncoder(){
        //BCrypt est un algorithme d'encodage de chaine de caratère,permet d'encoder les mots de passes
        return new BCryptPasswordEncoder();
    }

    // Spring qui va gérer le password encodé dans la BDD  car il n'est pas stocké en clair dans la BDD
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }



    //Override on surcharge la méthode de la classe parent
    @Override   
    protected void configure(HttpSecurity http) throws Exception {
        http
            // met une restriction sur la requête, on doit lui passer un argument 'request' et on l'utilise avec des methodes dessus ici .antMatchers
            .authorizeRequests(requests -> requests
                // définit un type URL => ici les routes qui commencent par '/joueurs/**'  sont accessibles pour tous les users
                .antMatchers("/joueur/**").permitAll()
                .antMatchers("/inscription").permitAll()
                // pour les autres requêtes il faut une autorisation
                .anyRequest().authenticated())
            // définir l'url de login
            .formLogin(login -> login
                .loginPage("/login")
                .permitAll()
            )
            // logout
            .logout(logout -> logout
                .permitAll()
            )
            ;
    }   
}