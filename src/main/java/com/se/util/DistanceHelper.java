package com.se.util;

public class DistanceHelper {
    public static final int EARTH_RADIUS = 3959;
    /**
     *     double res = 0.0;
     *     double a = (90 - lat1) * PI / 180;
     *     double b = (90 - lat2) * PI / 180;
     *     double C = (lon1 - lon2) * PI / 180;
     *     double angle = acos(cos(a) * cos(b) + sin(a) * sin(b) * cos(C));
     *     res = angle * EARTH_RADIUS;
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double res = 0.0;
        double a = (90 - lat1) * Math.PI / 180;
        double b = (90 - lat2) * Math.PI / 180;
        double C = (lon1 - lon2) * Math.PI / 180;
        double angle = Math.acos(Math.cos(a) * Math.cos(b) + Math.sin(a) * Math.sin(b) * Math.cos(C));
        res = angle * EARTH_RADIUS;
        return res;
    }

    public static void main(String[] args) {
        double test1 = DistanceHelper.distance(41.3128, -72.9251, 41.3095022158125, -72.923641204834);
        System.out.println(test1);
    }
}
