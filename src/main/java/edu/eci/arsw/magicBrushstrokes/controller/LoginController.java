package edu.eci.arsw.magicBrushstrokes.controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.eci.arsw.magicBrushstrokes.model.Player;
import edu.eci.arsw.magicBrushstrokes.repository.PlayerRepository;
import edu.eci.arsw.magicBrushstrokes.services.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired(required = true)
    private UserService userService;

    @PostMapping("/singIn")
    public ResponseEntity<?>signUp(@RequestParam String userName, @RequestParam String password) {
        Optional<Player> aux = userService.getUser(userName);
        Player player = aux.orElseThrow(()-> new GestorAlmuerzosAppException(GestorAlmuerzosAppException.EmailNoExist));
        // Redirige a una página
        return new ResponseEntity<HashMap<String, String>>(player, HttpStatus.ACCEPTED);
    }
}
