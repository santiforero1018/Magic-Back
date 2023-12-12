package edu.eci.arsw.magicBrushstrokes.model;

public class CanvasData {
    private String canvasId;
    private Point[] drawingData;
    private boolean power;
    private String typePower;
    public CanvasData(){
    }

    public CanvasData(String typePower){
        this.typePower = typePower;
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

    public void setCanvasTypePower(String typePower){
        this.typePower =typePower;
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

    public String getTypePower(){
        return this.typePower;
    }
}
