package edu.eci.arsw.magicBrushstrokes.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import edu.eci.arsw.magicBrushstrokes.model.Point;
import edu.eci.arsw.magicBrushstrokes.model.CanvasData;

@Controller
public class STOMPMessagesHandler {

    @Autowired
    SimpMessagingTemplate msgt;

    @MessageMapping("/room.{roomCode}")
    public void handleDrawEvent(CanvasData canvasData, @DestinationVariable String roomCode) throws Exception {
        // Verifica si se ha activado el "poder" de borrar
        if (canvasData.getPower()) {
            // Lógica para borrar el canvas de este jugador
            Point[] pointsReset = new Point[0];
            canvasData.setDrawingPoints(pointsReset);

            // Envía un mensaje de "borrado" a todos los clientes en la sala
            msgt.convertAndSend("/game/room." + roomCode,canvasData);
        } else {
            // Si no se ha activado el "poder", simplemente transmite el evento de dibujo
            System.out.println("enviando mensaje...");
            msgt.convertAndSend("/game/room." + roomCode, canvasData);
        }
    }

    private void clearCanvasForPlayer(String canvasId) {
        
    }
}
