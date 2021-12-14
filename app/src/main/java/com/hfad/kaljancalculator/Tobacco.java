package com.hfad.kaljancalculator;

public class Tobacco {
    public static final int SMALL_TARE_WEIGHT = 158;
    public static final int BIG_TARE_WEIGHT = 1256;

    private int leaves;
    private double burleyRatio;
    private int glycerine;
    private int syrope;
    private int water;
    private int total;

    Tobacco(){
        burleyRatio = 0;
        total = 0;
    }

    public int getLeaves() {
        return leaves;
    }

    public int getGlycerine() {
        return glycerine;
    }

    public int getSyrope() {
        return syrope;
    }

    class Details{
        public int getVirginiaLeaves(){
            return leaves - (int) (leaves * burleyRatio);
        }

        public int getBurleyLeaves(){
            return (int) (leaves * burleyRatio);
        }

        public int getWaterNeeded(int wetLeaves){
            return leaves - (wetLeaves - leaves);
        }

        public int getTotalWithSmallTare(){
            return total + SMALL_TARE_WEIGHT;
        }

        public int getTotalWithBigTare(){
            return total + BIG_TARE_WEIGHT;
        }
    }

    public static class Builder {
        private final Tobacco tobacco;

        public Builder() {
            tobacco = new Tobacco();
        }

        public Tobacco build() {
            int total = tobacco.total;

            this.tobacco.leaves = (int) Math.round(total * 0.171);
            this.tobacco.syrope = (int) Math.round(total * 0.300);
            this.tobacco.glycerine = (int) Math.round(total * 0.498);
            this.tobacco.water = (int) Math.round(total * 0.030);

            return tobacco;
        }

        public Builder setDesiredTotal(int total){
            this.tobacco.total = total;
            return this;
        }

        public Builder setBurleyRatio(double burley){
            this.tobacco.burleyRatio = burley;
            return this;
        }
    }
}
