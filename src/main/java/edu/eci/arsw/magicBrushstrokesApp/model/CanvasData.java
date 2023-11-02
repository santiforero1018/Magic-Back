package edu.eci.arsw.magicBrushstrokesApp.model;

public class CanvasData {
    private String canvasId;
    private Point[] drawingData;

    public CanvasData(){
    }

    public CanvasData(String canvasId, Point[] drawingData){
        this.canvasId = canvasId;
        this.drawingData = drawingData;
    }

    public void setCanvasId(String canvasId){
        this.canvasId = canvasId;
    }

    public void setDrawingPoints(Point[] drawingData){
        this.drawingData = drawingData;
    }

    public Point[] getDrawingData(){
        return drawingData;
    }

    public String getCanvasId(){
        return canvasId;
    }
}
