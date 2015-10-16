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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kdfly.zmdfsj.model.Constants;

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
        random = new Random();
        random_num = random.nextInt(9);
//        System.out.println(location[random_num]);
        tvLocation.setText(Constants.location[random_num]);
        changeGoods();
    }

    private void initGoods() {
        list_store = new ArrayList<Map<String, Object>>();
        adapter_store = new SimpleAdapter(this, list_store, R.layout.listitem_store, new String[]{"name", "price"}, new int[]{R.id.goodName, R.id.goodPrice});
        lvStore.setAdapter(adapter_store);


        lvStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String name = ((TextView) view.findViewById(R.id.goodName)).getText().toString();
                final String pricel = ((TextView) view.findViewById(R.id.goodPrice)).getText().toString();
//                Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();

//                list_store.remove(position);
//                adapter_store.notifyDataSetChanged();
                showNumberInputDialog(
                        R.drawable.notification_template_icon_bg,
                        R.string.buy,
                        "test", R.string.ok, R.string.cancel, 1,
                        new MainActivity.NumberInputCallback() {
                            @Override
                            public void onNumberInputed(int number) {
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("name", name);
                                map.put("price", pricel);
                                map.put("count", number);
                                list_ownstore.add(map);
//                                GameEngine.getInstance().dealWithMarket(disp.goodId, number);
//                                ((MainActivity) getActivity()).playSound("buy.wav", 0);
                            }
                        });
            }
        });
    }

    private void initOwnGoods() {
        list_ownstore = new ArrayList<Map<String, Object>>();
        adapter_ownstore = new SimpleAdapter(this, list_ownstore, R.layout.listitem_ownstore, new String[]{"name", "price", "count"}, new int[]{R.id.goodName, R.id.goodPrice, R.id.goodCount});
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
        for (int i = 0; i < 7; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            int pricel = GameEngine.getInstance().getGoodPrices()[i];
            map.put("name", Constants.goods[i]);
            map.put("price", pricel);
            System.out.println(pricel);
            if (pricel != 0) {
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
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setText(""+initNumber);
        input.setSelectAllOnFocus(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(iconId)
                .setTitle(titleId)
                .setMessage(message)
                .setView(input)
                .setPositiveButton(okId,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (null != callback) {
                                    try {
                                        int number = Integer.parseInt(input.getText().toString());
                                        callback.onNumberInputed(number);
                                    }
                                    catch(NumberFormatException ex) {
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
        input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

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
}
