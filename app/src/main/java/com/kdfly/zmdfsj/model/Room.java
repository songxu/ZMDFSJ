package com.kdfly.zmdfsj.model;

import android.util.Log;

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
        setSpace(getSpace() - goodCount);
        Log.i("kdfly","addGood函数，id为" + id + "，数量为：" + goodCount+ "，价格为：" + price + "，剩余的空间为：" + getSpace());
    }

    public int delGood(int id, int number) {

        if (number > count[id])
            return 0;
        int original_count = count[id];
        count[id] -= number;
        if (0 == count[id])
            cost[id] = 0;
        else
            cost[id] = cost[id]*count[id]/original_count;

        setSpace(getSpace() + number);
        Log.i("kdfly","sellGood函数，id为：" + id + "，数量为：" + number + "，剩余的空间为：" + getSpace());
        return number;
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
