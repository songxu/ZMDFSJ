package com.kdfly.zmdfsj.model;

import com.kdfly.zmdfsj.model.Constants;

import java.util.Random;

/**
 * Created by Sean on 2015-09-08.
 */
public class Store {

    public int[] getPrices(){
        int prices[] = new int[Constants.goodsPriceBase.length];
        for (int i = 0; i < prices.length; i++){
            prices[i] = Constants.goodsPriceBase[i] + Constants.getRandom(Constants.goodsPriceRand[i]);
        }
        int random = new Random().nextInt(6);
        for (int i = 0; i<random; i++){
            prices[new Random().nextInt(Constants.goodsPriceBase.length)] = 0;
        }
        return prices;
    }
}
