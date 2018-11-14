package com.graphs.lib.graph;

import com.graphs.lib.graph.element.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PointGraph extends Graph {
    private StepType stepType = StepType.STEP_DISTANCE;
    private int horizontalSeparatorsAmount = 10;
    private int verticalSeparatorsAmount = 10;
    private double vertStepDistance = 1;
    private double horStepDistance = 1;

    private double lowestH;
    private double highestH;
    private double lowestV;
    private double highestV;

    private double verticalRatio;
    private double horizonalRatio;

    private double x,y;

    private List<Point> upPoints = new ArrayList<>(Arrays.asList(
            new Point(10, 5),
            new Point(0, -5)
    ));

    public PointGraph(int width, int height){
        super(width, height);
    }
    public PointGraph(){
        this.width = 900;
        this.height = 600;
    }

    @Override
    public void draw(){
        getHighestAndLowestNumbers();
        getVerticalAndHorizontalRatios();
        drawEmptyChart();
    }

    private void getVerticalAndHorizontalRatios() {
        verticalRatio = 0.7 * height / (highestV - lowestV);
        horizonalRatio = 0.7 * width / (highestH - lowestH);
    }

    private void getHighestAndLowestNumbers() {
        lowestH = getLowestY(upPoints);
        highestH = getHighestY(upPoints);
        lowestV = getLowestX(upPoints);
        highestV = getHighestX(upPoints);
    }

    private void drawEmptyChart() {
        drawVerticalGraphLine();
        drawHorizontalGraphLine();
        drawHorizontalSeparators();
    }

    private void drawHorizontalGraphLine() {
        if(highestH == 0 && lowestH == 0)
            y = 0.55*height;
        else if(highestH > 0 && lowestH > 0)
            y = 0.9*height;
        else if(highestH < 0 && lowestH < 0)
            y = 0.2*height;
        else{
            if (highestH < 0) highestH = -highestH;
            y = 0.2*height + (verticalRatio * highestH);
        }
        Line line = new Line(this, new Point(0.08*width, y),
                new Point(0.82*width, y));
        line.draw();
    }
    private void drawVerticalGraphLine(){
        if(highestV == 0 && lowestV == 0)
            x = 0.45*width;
        else if(highestV > 0 && lowestV > 0)
            x = 0.1*width;
        else if(highestV < 0 && lowestV < 0)
            x = 0.8*width;
        else {
            if (lowestV < 0) lowestV = -lowestV;
            x = 0.1 * width + (horizonalRatio * lowestV);
        }
        Line line = new Line(this, new Point(x, 0.18*height),
                new Point(x, 0.92*height));
        line.draw();
    }

    private double getLowestX(List<Point> points) {
        double x = points.get(0).getX();
        for(Point point: points){
            if(x > point.getX())
                x = point.getX();
        }
        return x;
    }
    private double getHighestX(List<Point> points) {
        double x = points.get(0).getX();
        for(Point point: points){
            if(x < point.getX())
                x = point.getX();
        }
        return x;
    }
    private double getLowestY(List<Point> points) {
        double y = points.get(0).getY();
        for(Point point: points){
            if(y > point.getY())
                y = point.getY();
        }
        return y;
    }
    private double getHighestY(List<Point> points) {
        double y = points.get(0).getY();
        for(Point point: points){
            if(y < point.getY())
                y = point.getY();
        }
        return y;
    }
    private void drawHorizontalSeparators(){
        //TODO: clean this to look better
        Line line;
        if(stepType == StepType.STEP_AMMOUNT){
            for(int i = 1; i <= horizontalSeparatorsAmount; i++){
                line = new Line(this, new Point(0.1*width + (0.7*width * i / horizontalSeparatorsAmount), y-0.005*height),
                        new Point(0.1*width + (0.7*width * i / horizontalSeparatorsAmount), y+0.005*height));
                line.draw();
            }
            for(int i = 0; i < verticalSeparatorsAmount; i++){
                line = new Line(this, new Point(x-0.005*width, 0.2*height + (0.7*height * i / verticalSeparatorsAmount)),
                        new Point(x+0.005*width, 0.2*height + (0.7*height * i / verticalSeparatorsAmount)));
                line.draw();
            }
        }
        else{
            for(int i = 1; x - (i * horStepDistance * horizonalRatio) >= 0.1 * width; i++){
                line = new Line(this, new Point(x - (i * horStepDistance * horizonalRatio), y-0.005*height),
                        new Point(x - (i * horStepDistance * horizonalRatio), y+0.005*height));
                line.draw();
            }
            for(int i = 1; x + (i * horStepDistance * horizonalRatio) <= 0.8 * width; i++){
                line = new Line(this, new Point(x + (i * horStepDistance * horizonalRatio), y-0.005*height),
                        new Point(x + (i * horStepDistance * horizonalRatio), y+0.005*height));
                line.draw();
            }
            for(int i = 1; y + (i * vertStepDistance * verticalRatio) <= 0.9 * height; i++){
                line = new Line(this, new Point(x-0.005*width, y + (i * vertStepDistance * verticalRatio)),
                        new Point(x+0.005*width, y + (i * vertStepDistance * verticalRatio)));
                line.draw();
            }
            for(int i = 1; y - (i * vertStepDistance * verticalRatio) >= 0.2 * height; i++) {
                line = new Line(this, new Point(x - 0.005 * width, y - (i * vertStepDistance * verticalRatio)),
                        new Point(x + 0.005 * width, y - (i * vertStepDistance * verticalRatio)));
                line.draw();
            }
        }
    }
    public void setStepType(StepType stepType) {
        this.stepType = stepType;
    }

    public void setHorizontalSeparatorsAmount(int horizontalSeparatorsAmount) {
        this.horizontalSeparatorsAmount = horizontalSeparatorsAmount;
    }

    public void setVerticalSeparatorsAmount(int verticalSeparatorsAmount) {
        this.verticalSeparatorsAmount = verticalSeparatorsAmount;
    }

    public void setVerticalStepDistance(double vertStepDistance) {
        this.vertStepDistance = vertStepDistance;
    }
    public void setHorizontalStepDistance(double horStepDistance) {
        this.horStepDistance = horStepDistance;
    }

}
