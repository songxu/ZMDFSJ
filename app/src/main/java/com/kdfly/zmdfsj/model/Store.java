package com.kdfly.zmdfsj.model;

/**
 * Created by Sean on 2015-09-08.
 */
public class Store {

    public int[] getPrices(int gid){
        int prices[] = new int[Constants.goodsPriceBase.length];
        for (int i = 0; i < prices.length; i++){
            prices[i] = Constants.goodsPriceBase[i] + Constants.getRandom(Constants.goodsPriceRand[i]);
        }
//        int random = new Random().nextInt(6);
        for (int i = 0; i<gid; i++){
            prices[Constants.getRandom(prices.length)] = 0;
        }
        return prices;
    }
}
