package com.example.natarian.pokemonquiz;

import android.view.animation.Interpolator;

/**
 * Created by Natarian on 3/11/17.
 */

public class CustomAnimation implements Interpolator {
    double amplitude = 1;
    double frequency = 10;

    public CustomAnimation(double amplitude, double frequency) {
        this.amplitude = amplitude;
        this.frequency = frequency;
    }


    @Override
    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, - time / amplitude) * Math.cos(frequency * time) + 1);
    }
}
