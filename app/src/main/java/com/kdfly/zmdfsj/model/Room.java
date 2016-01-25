package com.kdfly.zmdfsj.model;

/**
 * Created by sheep on 2016/1/22.
 */
public class Room {

    protected int count[] = null;
    protected int cost[] = null;

    protected int space = Constants.INIT_SPACE;

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public void addGood(int id, int goodCount, int price){
        count[id] += goodCount;
        cost[id] += goodCount * price;
    }

    public int getAllGoodsCount(){
        int ret = 0;
        for (int i = 0; i<count.length; i++){
            ret += count[i];
        }
        return ret;
    }

    public void init(){
        count = new int[Constants.GOOD_TYPE_COUNT];
        cost = new int[Constants.GOOD_TYPE_COUNT];
        space = Constants.INIT_SPACE;
    }

    public int[] getGoodsCount() {
        return count;
    }

    public int[] getGoodsCost() {
        return cost;
    }


}
