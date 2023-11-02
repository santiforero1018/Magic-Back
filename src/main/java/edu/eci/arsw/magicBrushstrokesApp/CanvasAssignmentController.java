package edu.eci.arsw.magicBrushstrokesApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CanvasAssignmentController {

    HashMap<String, ArrayList<String>> rooms = new HashMap<>();

    @PostMapping("/assignCanvas")
    public ResponseEntity<Map<String, String>> assignCanvas(@RequestBody Map<String, String> requestBody) {
        // Lógica para asignar un canvas específico al jugador

        // Recupera el roomCode enviado desde el cliente
        String assignedCanvasId;
        String roomCode = requestBody.get("roomCode");
        System.out.println(roomCode);
        // Lógica para asignar el roomCode
        if(rooms.get(roomCode) == null){//Primer jugador en conectarse a la sala.
            ArrayList<String> canvasId = prepareCanvasId();
            rooms.put(roomCode, canvasId);
            assignedCanvasId = "canvas1";
        }else if(!rooms.get(roomCode).isEmpty()){
            ArrayList<String> canvasId = rooms.get(roomCode);
            assignedCanvasId = canvasId.get(0);
            canvasId.remove(0);
        }else{
            assignedCanvasId = "FULLROOM";
        }
        Map<String, String> response = new HashMap<>();
        response.put("canvasId", assignedCanvasId);
        response.put("roomCode", roomCode);
        return ResponseEntity.ok(response);
    }

    private ArrayList<String> prepareCanvasId() {
        ArrayList<String> canvasId = new ArrayList<>();
        canvasId.add("canvas2");
        canvasId.add("canvas3");
        canvasId.add("canvas4");
        return canvasId;
    }
}