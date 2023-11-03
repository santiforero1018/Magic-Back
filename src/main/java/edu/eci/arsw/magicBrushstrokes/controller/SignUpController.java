// package edu.eci.arsw.magicBrushstrokes.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.stereotype.Service;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import edu.eci.arsw.magicBrushstrokes.model.Player;
// import edu.eci.arsw.magicBrushstrokes.repository.PlayerRepository;
// import edu.eci.arsw.magicBrushstrokes.services.PlayerService;
// import edu.eci.arsw.magicBrushstrokes.services.UserService;

// @Controller
// @RequestMapping("/signup")
// public class SignUpController {

//     @Autowired(required = true)
//     private UserService userService;

//     @PostMapping("/saveplayer")
//     public String signUp(@RequestParam String fullName, @RequestParam String nickName,  @RequestParam int age, @RequestParam String password, @RequestParam String veryPassword) {
//         Player player = new Player(fullName, nickName, age, password, veryPassword);
//         userService.savePlayer(player);
//         // Redirige a una página
//         System.out.println("funca");
//         return "redirect:/prueba.html";
//     }
// }
