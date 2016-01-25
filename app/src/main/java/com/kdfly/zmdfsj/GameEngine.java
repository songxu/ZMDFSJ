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

    protected GameEngine(){}

    public static GameEngine getInstance(){
        return instance;
    }
    public void init(Context context){
        price = store.getPrices(Constants.randomGoodsamount);
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

    public void oneDayPass(){
        price = store.getPrices(Constants.randomGoodsamount);
    }

    public void buyOrSell(int goodId,int count){
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
