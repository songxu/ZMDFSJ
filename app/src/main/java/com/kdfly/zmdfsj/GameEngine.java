package com.kdfly.zmdfsj;

import android.content.Context;

import com.kdfly.zmdfsj.model.Store;
import com.kdfly.zmdfsj.utils.Logg;

/**
 * Created by Sean on 2015-09-08.
 */
public class GameEngine {
    protected static GameEngine instance = new GameEngine();

    protected Store store = new Store();
    protected int[] price = null;

    protected GameEngine(){}

    public static GameEngine getInstance(){
        return instance;
    }
    public void init(Context context){
        price = store.getPrices();
    }
    public int[] getGoodPrices(){
        return store.getPrices();
    }
}
