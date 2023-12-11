package edu.eci.arsw.magicBrushstrokes.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.arsw.magicBrushstrokes.model.Player;
import edu.eci.arsw.magicBrushstrokes.repository.PlayerRepository;

@Service
public class UserService {

    @Autowired
    private PlayerRepository playerRepository;

    public Player getUser(String Name) {
        return playerRepository.findByName(Name);
    }

    public void addPlayer(Player player) {
        playerRepository.save(player);
    }

    // public void updateUser(User user) {
    //     Optional<User> usuario = UserRepository.findById(user.getEmail());
    //     User u = usuario.orElseThrow();
    //     String role = u.getRole();
    //     user.setRole(role,roleRepository);
    //     UserRepository.save(user);
    // }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    // public void deleteUser(String id) {
    //     UserRepository.deleteById(id);
    // }
    
}
