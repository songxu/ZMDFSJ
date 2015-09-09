package com.kdfly.zmdfsj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kdfly.zmdfsj.model.Constants;
import com.kdfly.zmdfsj.model.Store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.lv_store)
    ListView lvStore;
    @Bind(R.id.btn_next)
    Button btnNext;
    @Bind(R.id.tv_location)
    TextView tvLocation;

    private Random random;
    private int random_num;
    private SimpleAdapter adapter;
    private List<Map<String,Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GameEngine.getInstance().init(getApplicationContext());
        ButterKnife.bind(this);
//        btnNext.setText("测试噢。");
        btnNext.setOnClickListener(this);

        initGoods();
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
        list = new ArrayList<Map<String,Object>>();
        adapter = new SimpleAdapter(this, list, R.layout.listitem_store, new String[]{"name", "price"}, new int[]{R.id.goodName, R.id.goodPrice});
        lvStore.setAdapter(adapter);


        lvStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = ((TextView) view.findViewById(R.id.goodName)).getText().toString();
                Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changeGoods(){
        list.clear();
        for (int i = 0;i<7;i++){
            Map<String,Object> map=new HashMap<String, Object>();
            int pricel = GameEngine.getInstance().getGoodPrices()[i];
            map.put("name", Constants.goods[i]);
            map.put("price", pricel);
//            System.out.println(GameEngine.getInstance().getGoodPrices()[i]);
            if (pricel != 0){
                list.add(map);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                changeLocation();
                break;
        }
    }
}
