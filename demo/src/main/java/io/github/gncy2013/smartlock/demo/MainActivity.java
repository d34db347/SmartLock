package io.github.gncy2013.smartlock.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
    public String temple;
    public int selectSize;
    public int selectLocation;
    /*----ListView MVC实现----*/
    // model
    List<String> data1,data2;
    // view
    ListView lv,lv2;
    // controller
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    //int size = 1;
    // 初始化组件
    private void initWidget() {
        lv = (ListView) findViewById(R.id.list);
        lv2 = (ListView) findViewById(R.id.list2);
    }
    // 初始化绑定数据
    private void initData() {
        if (lv == null||lv2 == null)
            return;
        data1 = new ArrayList<String>();
        data2 = new ArrayList<String>();
        appendData();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,
                data1);
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,
                data2);

        //给每一个选项添加单选选项。
        //设置ListView的选择的样式为单选列表
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        // 第三步：给listview设置适配器（view）
        lv.setAdapter(adapter);
        lv2.setAdapter(adapter2);
    }

    // 添加数据
    private void appendData() {
        data1.add("18*18");
        data1.add("30*30");
        data1.add("41*41");
        data2.add("左下方");
        data2.add("中下方");
        data2.add("右下方");
    }
    //添加点击
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        temple = parent.getItemAtPosition(position).toString();
        if (temple.equals("18*18") || temple.equals("30*30") || temple.equals("41*41")) {
            selectSize=position;
        }
        else if (temple.equals("左下方") || temple.equals("中下方") || temple.equals("右下方")){
            selectLocation=position;
        }
        Toast.makeText(
                this,
                "position : " + position + " item : " + temple,
                Toast.LENGTH_SHORT
        ).show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
        initData();
        lv.setOnItemClickListener(this);
        lv2.setOnItemClickListener(this);
    }

    /**
     * OnClickListener
     * Start LockActivity
     */
    public void onPatternDemoClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("pattern_size",selectSize);// sent pettern_size
        intent.putExtra("pattern_location",selectLocation);
        intent.setClass(getApplicationContext(), LockPatternActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onPatternSettingClick(View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), SetPatternActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
