package edu.eci.arsw.magicBrushstrokes.model;

public class CanvasData {
    private String canvasId;
    private Point[] drawingData;
    private boolean power;
    public CanvasData(){
    }

    public CanvasData(String canvasId, Point[] drawingData){
        this.canvasId = canvasId;
        this.drawingData = drawingData;
        this.power = true;
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

    public boolean getPower(){
        return this.power;
    }

    public void setPower(boolean power){
        this.power = power;
    }
}
