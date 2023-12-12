package edu.eci.arsw.magicBrushstrokes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    boolean powerActivated = false;

    @MessageMapping("/room.{roomCode}")
    public synchronized void handleDrawEvent(CanvasData canvasData, @DestinationVariable String roomCode) throws Exception {
        if (canvasData.getPower()) {
            Point[] pointsReset = new Point[0];
            canvasData.setDrawingPoints(pointsReset);
            if(!powerActivated){
                msgt.convertAndSend("/game/room." + roomCode, canvasData);
                powerActivated = true;
                powerActivatedTimer();
            }else{
                canvasData.setCanvasTypePower("Cooldown");
                msgt.convertAndSend("/game/room." + roomCode, canvasData);
            }
            

        } else {
            // Si no se ha activado el "poder", simplemente transmite el evento de dibujo
            msgt.convertAndSend("/game/room." + roomCode, canvasData);
        }
    }

    private void powerActivatedTimer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run(){
                powerActivated = false;
            }
        }, 20000);

    }   

}
