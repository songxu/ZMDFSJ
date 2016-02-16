package com.kdfly.zmdfsj.model;

import java.util.Random;

/**
 * Created by Sean on 2015-09-08.
 */
public class Constants {
    public static final String[] location = {"西直门", "积水潭", "东直门", "公主坟", "复兴门", "建国门", "长椿街", "崇文门", "北京站"};

    public static final String[] goods = {"进口香烟","走私汽车","盗版VCD","五粮液","《小时代》","进口玩具","水货手机","雅思兰黛"};
    public static final String[] goodID = {"g1","g1","g3","g4","g5","g6","g7","g8"};
    public static final int goodsPriceBase[] = new int[]{100,15000,5,1000,5000,250,750,65};
    public static final int goodsPriceRand[] = new int[]{350,15000,50,2500,9000,600,750,180};
    public static int randomGoodsAmount = 3;
    public static int GOOD_TYPE_COUNT = 8;

    protected static Random random = new Random();
    public static int getRandom(int max) {
        return random.nextInt(max);
    }

    public static final int INIT_CASH = 2000;
    public static final int INIT_DEPOSIT = 0;
    public static final int INIT_DEBT = 5500;
    public static final int INIT_SPACE = 100;

    public static boolean INIT_SHOW_PRICE_WAVE = false;

    //interface update events
    public static final int UPDATE_GAME_OVER = 0;
    public static final int UPDATE_DAY = 1;
    public static final int UPDATE_HEALTH = 2;
    public static final int UPDATE_FAME = 3;
    public static final int UPDATE_MONEY = 4;
    public static final int UPDATE_MARKET = 5;
    public static final int UPDATE_ROOM = 6;
    public static final int UPDATE_PLACE = 7;
    public static final int UPDATE_DEAL = 8;
    public static final int UPDATE_SETTINGS = 9;
}
