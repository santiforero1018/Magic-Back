package edu.eci.arsw.magicBrushstrokes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "https://magicbrushstrokeswebappfront.azurewebsites.net") // Cambiar al momento de subir a azure
@RestController
@RequestMapping(value="/API-v1.0MagicBrushStrokes")
public class CanvasAssignmentController {

    Map<String, ArrayList<String>> rooms = new ConcurrentHashMap<>();

    @RequestMapping(method = RequestMethod.POST, value= "/board")
    public ResponseEntity<?> assignCanvas(@RequestBody Map<String, String> requestBody) {
        // Lógica para asignar un canvas específico al jugador

        // Recupera el roomCode enviado desde el cliente
        String assignedCanvasId;
        String roomCode = requestBody.get("roomCode");
        // Lógica para asignar el roomCode
        if(rooms.get(roomCode) == null){//Primer jugador en conectarse a la sala.
            ArrayList<String> canvasId = prepareCanvasId();
            rooms.putIfAbsent(roomCode, canvasId);
            assignedCanvasId = "canvas1";
        }else if(!rooms.get(roomCode).isEmpty()){
            ArrayList<String> canvasId = rooms.get(roomCode);
            assignedCanvasId = canvasId.get(0);
            canvasId.remove(0);
        }else{
            assignedCanvasId = "FULLROOM";
        }
        HashMap<String, String> response = new HashMap<String, String>();
        response.put("canvasId", assignedCanvasId);
        response.put("roomCode", roomCode);
        return new ResponseEntity<HashMap<String,String>>(response, HttpStatus.ACCEPTED);
    }
    

    private ArrayList<String> prepareCanvasId() {
        ArrayList<String> canvasId = new ArrayList<>();
        canvasId.add("canvas2");
        canvasId.add("canvas3");
        canvasId.add("canvas4");
        return canvasId;
    }
}