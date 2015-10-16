package com.kdfly.zmdfsj.model;

/**
 * Created by Sean on 2015-10-01.
 */
public class Player {
    protected int money = Constants.INIT_CASH;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void init(){
        money = Constants.INIT_CASH;
    }
}
