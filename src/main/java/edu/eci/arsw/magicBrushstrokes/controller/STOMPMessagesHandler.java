package edu.eci.arsw.magicBrushstrokes.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import edu.eci.arsw.magicBrushstrokes.model.CanvasData;

@Controller
public class STOMPMessagesHandler {
    
    @Autowired
    SimpMessagingTemplate msgt;

    @MessageMapping("/room.{roomCode}")
    public void handleDrawEvent(CanvasData canvasData,@DestinationVariable String roomCode) throws Exception{
        msgt.convertAndSend("/game/room." + roomCode, canvasData);
    }
}
