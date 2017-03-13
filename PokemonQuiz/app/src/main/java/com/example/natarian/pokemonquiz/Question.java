package com.example.natarian.pokemonquiz;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Natarian on 3/10/17.
 */

public class Question {

    @SerializedName("gen")
    private int gen;
    @SerializedName("name")
    private String name;
    @SerializedName("img")
    private String imgName;

    public Question() {
    }

    public Question(int gen, String name, String imgName) {
        this.gen = gen;
        this.name = name;
        this.imgName = imgName;
    }

    public int getGen() {
        return gen;
    }

    public void setGen(int gen) {
        this.gen = gen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}
