package com.djl;

public class Point {

    private Integer x;
    private Integer y;
    private Integer curUnit = 0;


    public void changeUnit(int unit){
        if (curUnit == unit) {
            return;
        }
        x= x/unit;
        curUnit = unit;

    }

    public Point() {

    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }
    public void setX(Integer x) {
        this.x = x;
    }
    public Integer getY() {
        return y;
    }
    public void setY(Integer y) {
        this.y = y;
    }
    public Integer getCurUnit() {
        return curUnit;
    }
    public void setCurUnit(Integer curUnit) {
        this.curUnit = curUnit;
    }




}
