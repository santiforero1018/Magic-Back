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

    @MessageMapping("/newDraw.{roomCode}")
    public void handleDrawEvent(Point pt, @DestinationVariable String roomCode) throws Exception{
        System.out.println("Nuevo punto recibido en el servidor!: " + pt);
        msgt.convertAndSend("/game/newDraw." + roomCode, pt);
    }
}
