package com.example.natarian.pokemonquiz;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Natarian on 3/12/17.
 */

public class DBContext {
    private Realm realm;

    public DBContext() {
        realm = Realm.getDefaultInstance();
    }

    private static DBContext inst;

    public static DBContext getInst() {
        if (inst == null) {
            return new DBContext();
        }
        return inst;
    }

    public void addPlayerScore(Score score) {
        realm.beginTransaction();
        realm.copyToRealm(score);
        realm.commitTransaction();
    }


    public List<Score> getAllScore() {
        RealmResults<Score> results = realm.where(Score.class).findAll();
        results = results.sort("playerScore", Sort.DESCENDING);
        List<Score> list = new ArrayList<>();

        if (results.size() > 5) {
            list = results.subList(0, 5);
            return list;
        } else {
            return results;
        }

    }
}
