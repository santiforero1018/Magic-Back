package edu.eci.arsw.magicBrushstrokesApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import edu.eci.arsw.magicBrushstrokesApp.model.Point;

@Controller
public class STOMPMessagesHandler {
    
    @Autowired
    SimpMessagingTemplate msgt;

    @MessageMapping("/room.{roomCode}")
    public void handleDrawEvent(Point[] pts, @DestinationVariable String roomCode) throws Exception{
        System.out.println("Nuevo jugador en la sala: ");
        msgt.convertAndSend("/game/room." + roomCode, pts); 
    }
}
