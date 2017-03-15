package com.example.natarian.pokemonquiz;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Natarian on 3/10/17.
 */

public class Question {

    @SerializedName("bank")
    private int bank;
    @SerializedName("name")
    private String name;
    @SerializedName("img")
    private String imgName;

    public Question() {
    }

    public Question(int bank, String name, String imgName) {
        this.bank = bank;
        this.name = name;
        this.imgName = imgName;
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
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
