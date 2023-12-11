package edu.eci.arsw.magicBrushstrokes.controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.magicBrushstrokes.model.Player;
import edu.eci.arsw.magicBrushstrokes.services.UserService;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/singIn/{userName}/{password}")
    public ResponseEntity<?>signIn(@PathVariable String userName, @PathVariable String password) {
        Player data = this.userService.getUser(userName);
        System.out.println(data.getName());
        if(data != null){
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
       } else{
            System.out.println("aqui a este else llego");
            return new ResponseEntity<>("El usuario no existe", HttpStatus.NOT_ACCEPTABLE);
       }
    }

    @PostMapping("/singUp")
    public ResponseEntity<?>singUp(@RequestBody Player player){
        try{
            this.userService.addPlayer(player);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        
    }
}
