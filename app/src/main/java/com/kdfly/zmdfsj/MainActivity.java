package com.kdfly.zmdfsj;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gregacucnik.EditableSeekBar;
import com.kdfly.zmdfsj.model.Constants;
import com.kdfly.zmdfsj.model.Player;
import com.kdfly.zmdfsj.model.Room;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.lv_store)
    ListView lvStore;
    @Bind(R.id.btn_next)
    Button btnNext;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.lv_own)
    ListView lvOwn;
    @Bind(R.id.tv_cash)
    TextView tvCash;

    private Random random;
    private int random_num;
    private SimpleAdapter adapter_store;
    private SimpleAdapter adapter_ownstore;
    private List<Map<String, Object>> list_store;
    private List<Map<String, Object>> list_ownstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        GameEngine.getInstance().init(getApplicationContext());
        ButterKnife.bind(this);
//        btnNext.setText("测试噢。");
        btnNext.setOnClickListener(this);

        initGoods();
        initOwnGoods();
        changeGoods();

        updateStatus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void changeLocation() {
        GameEngine.getInstance().oneDayPass();

        random = new Random();
        random_num = random.nextInt(9);
//        System.out.println(location[random_num]);
        tvLocation.setText(Constants.location[random_num]);
        changeGoods();
    }

    private void initGoods() {
        list_store = new ArrayList<>();
        adapter_store = new SimpleAdapter(this, list_store, R.layout.listitem_store, new String[]{"name", "price", "gid"}, new int[]{R.id.goodName, R.id.goodPrice, R.id.goodid});
        lvStore.setAdapter(adapter_store);


        lvStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String name = ((TextView) view.findViewById(R.id.goodName)).getText().toString();
                final String pricel = ((TextView) view.findViewById(R.id.goodPrice)).getText().toString();
                final String gid = ((TextView) view.findViewById(R.id.goodid)).getText().toString();

                int getCanBuyAmount = (int)Math.floor(GameEngine.getInstance().getPlayer().getMoney()/Integer.parseInt(pricel));
                int getCanStoreAmount = GameEngine.getInstance().getRoom().getSpace();
                final int maxBuy =  getCanBuyAmount > getCanStoreAmount ? getCanStoreAmount:getCanBuyAmount;
//                Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();

//                list_store.remove(position);
//                adapter_store.notifyDataSetChanged();
                showNumberInputDialog(
                        R.drawable.notification_template_icon_bg,
                        R.string.buy,
                        "test", R.string.ok, R.string.cancel, maxBuy,
                        new NumberInputCallback() {
                            @Override
                            public void onNumberInputed(int number) {
                                if (GameEngine.getInstance().getRoom().getSpace() > 0) {
                                    if (GameEngine.getInstance().getPlayer().getMoney() > Integer.parseInt(pricel)) {
                                        System.out.println("剩余的空间数：" + GameEngine.getInstance().getRoom().getSpace());

//                                        Map<String, Object> map = new HashMap<String, Object>();
//                                        map.put("name", name);
//                                        map.put("price", pricel);
//                                        map.put("count", number);
//                                        map.put("gid", gid);
//                                        list_ownstore.add(map);

                                        GameEngine.getInstance().buyOrSell(Integer.parseInt(gid), number);
                                    }else {
                                        Toast.makeText(getApplicationContext(), R.string.no_enough_money, Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), R.string.no_enough_room, Toast.LENGTH_LONG).show();
                                }

//                                ((MainActivity) getActivity()).playSound("buy.wav", 0);
                            }
                        });
            }
        });
    }

    private void initOwnGoods() {
        list_ownstore = new ArrayList<>();
        adapter_ownstore = new SimpleAdapter(this, list_ownstore, R.layout.listitem_ownstore, new String[]{"name", "price", "count", "gid"}, new int[]{R.id.goodName, R.id.goodPrice, R.id.goodCount, R.id.goodid});
        lvOwn.setAdapter(adapter_ownstore);


        lvOwn.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = ((TextView) view.findViewById(R.id.goodName)).getText().toString();
                Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changeGoods() {
        list_store.clear();
        int[] price = GameEngine.getInstance().getPrice();

        for (int i = 0; i < price.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", Constants.goods[i]);
            map.put("price", price[i]);
            map.put("gid", i);
            System.out.println(price[i]);
            if (price[i] != 0) {
                list_store.add(map);
            }
        }
        adapter_store.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                changeLocation();
                break;
        }
    }

    public interface NumberInputCallback {
        public void onNumberInputed(int number);
    }

    protected void showNumberInputDialog(int iconId, int titleId, String message, int okId, int cancelId, int initNumber, final NumberInputCallback callback) {
//        final EditText input = new EditText(this);

        View view = View.inflate(getApplicationContext(), R.layout.dialog_buy_or_sell, null);

        final TextView tvYouCanBuy = (TextView)view.findViewById(R.id.tv_you_can_buy);
        final EditableSeekBar sbBuyInput = (EditableSeekBar)view.findViewById(R.id.sb_buy_input);

//        etBuyInput.setInputType(InputType.TYPE_CLASS_NUMBER);
//        etBuyInput.setText("" + initNumber);
//        etBuyInput.setSelectAllOnFocus(true);
//        sbBuyInput.setProgress(initNumber);
//        sbBuyInput.setMax(initNumber);
        sbBuyInput.setValue(initNumber);
        sbBuyInput.setMaxValue(initNumber);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(iconId)
                .setTitle(titleId)
                .setMessage(message)
                .setView(view)
                .setPositiveButton(okId,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (null != callback) {
                                    try {
                                        int number = sbBuyInput.getValue();
                                        callback.onNumberInputed(number);
                                    } catch (NumberFormatException ex) {
//                                        EventBus.getDefault().post("error input, not processed here");
                                    }
                                }
                            }
                        })
                .setNegativeButton(cancelId,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
        final AlertDialog alert = builder.create();
//        etBuyInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//                }
//            }
//        });

        alert.show();
    }

    public void onEventMainThread(String event) {
//        notifications.add(event);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    //GameEngine use integer to send commands, like update interface and game over
    //MainActivity need to process it if possible
    //while some commands need to be processed after notification dialog(like change tab or restart)
    //so need to be converted with special_event string, and insert into notification queue with EventBus
    public void onEventMainThread(Integer event) {
        switch (event) {
            case Constants.UPDATE_MONEY:
                updateStatus();
                break;
//            case Constants.UPDATE_DAY:
//                updateDay();
//                autoChangeTab();
//                break;
//            case Constants.UPDATE_DEAL:
//                autoChangeTab();
//                break;
//            case Constants.UPDATE_MARKET:
//                break;
            case Constants.UPDATE_ROOM:
                updateStatus();
                break;
//            case Constants.UPDATE_MONEY:
//            case Constants.UPDATE_FAME:
//            case Constants.UPDATE_HEALTH:
//                updateStatus();
//                break;
//            case Constants.UPDATE_GAME_OVER:
//                //game over may be triggered by health event or time
//                //that means maybe some event dialog is in the front
//                //so need to use SPECIAL_EVENT_GAMEOVER to trigger some action after event dialog
//                EventBus.getDefault().post(SPECIAL_EVENT_GAMEOVER);
//            case Constants.UPDATE_SETTINGS:
//                updateSettings();
//                break;
        }
    }

    protected void updateStatus() {
        Player player = GameEngine.getInstance().getPlayer();

        list_ownstore.clear();
        Room room = GameEngine.getInstance().getRoom();
        int[] count = room.getGoodsCount();
        int[] cost = room.getGoodsCost();
        for (int i = 0; i < count.length; i++) {
            if (0 != count[i]) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", Constants.goods[i]);
                map.put("price", cost[i] / count[i]);
                map.put("count", count[i]);
                map.put("gid", i);
                list_ownstore.add(map);
            }
        }




        tvCash.setText("现金：" + player.getMoney());
    }

}
