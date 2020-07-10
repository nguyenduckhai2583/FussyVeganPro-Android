package com.fussyvegan.scanner.model;


public class Rate {
    private float rb_AveRating;
    private int tvSumRating;

    public float getRb_AveRating() {
        return rb_AveRating;
    }

    public void setRb_AveRating(float rb_AveRating) {
        this.rb_AveRating = rb_AveRating;
    }

    public int getTvSumRating() {
        return tvSumRating;
    }

    public void setTvSumRating(int tvSumRating) {
        this.tvSumRating = tvSumRating;
    }

    public Rate(float rb_AveRating, int tvSumRating) {
        this.rb_AveRating = rb_AveRating;
        this.tvSumRating = tvSumRating;
    }
}
