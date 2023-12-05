package edu.eci.arsw.magicBrushstrokes.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import edu.eci.arsw.magicBrushstrokes.model.Point;
import edu.eci.arsw.magicBrushstrokes.model.CanvasData;
@CrossOrigin(origins = "http://magicbrushstrokeswebappfront.azurewebsites.net")
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
            
            msgt.convertAndSend("/game/room." + roomCode, canvasData);
        }
    }

    // @MessageMapping("/room.{roomCode}/power")
    // public void handlePowerActivation(String message, @DestinationVariable String roomCode) throws Exception {
    //     msgt.convertAndSend("/game/room." + roomCode + "/power", message);
    // }
}
