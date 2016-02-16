package com.kdfly.zmdfsj;

import android.content.Context;

import com.kdfly.zmdfsj.model.Constants;
import com.kdfly.zmdfsj.model.Player;
import com.kdfly.zmdfsj.model.Room;
import com.kdfly.zmdfsj.model.Store;

import de.greenrobot.event.EventBus;

/**
 * Created by Sean on 2015-09-08.
 */
public class GameEngine {
    protected static GameEngine instance = new GameEngine();

    protected Store store = new Store();
    protected Player player = new Player();
    protected Room room = new Room();
    protected int[] price = null;
    protected int day = 0;

    protected GameEngine(){}

    public static GameEngine getInstance(){
        return instance;
    }
    public void init(Context context){
        price = store.getPrices(Constants.randomGoodsAmount);
        player.init();
        room.init();
    }

    public Player getPlayer(){
        return player;
    }
    public Room getRoom(){
        return room;
    }
    public int[] getPrice() {
        return price;
    }
    public int getDay(){return day;}

    public void oneDayPass(){
        price = store.getPrices(Constants.randomGoodsAmount);
        day = day + 1;
    }

    public boolean checkCanSell(int goodID){
        if (price[goodID] >0 ){
            return true;
        }else {
            return false;
        }
    }

    public void sellGood(int goodId, int count){
        room.delGood(goodId, count);
        player.setMoney(player.getMoney() + price[goodId] * count);
        EventBus.getDefault().post(Constants.UPDATE_MONEY);
        EventBus.getDefault().post(Constants.UPDATE_ROOM);
    }

    public void buyGood(int goodId,int count){
        if (count<=0)
            return;

        int roomSpace = room.getSpace() - room.getAllGoodsCount();
        int money = player.getMoney() - (price[goodId] * count);

        if ((money < 0)||(roomSpace < count))
            return;

        player.setMoney(money);
        room.addGood(goodId, count, price[goodId]);
        EventBus.getDefault().post(Constants.UPDATE_MONEY);
        EventBus.getDefault().post(Constants.UPDATE_ROOM);
    }
}
