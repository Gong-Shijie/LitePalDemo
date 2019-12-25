package com.example.litepaldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_add;
    private Button btn_dele;
    private Button btn_que;
    private Button btn_upd;
    private EditText edit_id;
    private EditText edit_name;
    private EditText edit_age;
    private EditText edit_info;
    private TextView textView;
    private String data = "";


/*
    使用该库默认的主键是id且自增长
    如果需要可以增加一个xx_id字段
*/

    public void showdb(List<User> users) {
        data = "";
        for (User ele : users) {
            data = data + ele.getId() + "  " + ele.getName() + "  " + ele.getAge() + "  " + ele.getInfo() + " " + ele.getUpclass() + "\n";
        }
        Log.i("edit", data);
        textView.setText(data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LitePal.getDatabase();
        Log.i("MainActivity", "创建数据库！");


        btn_add = findViewById(R.id.btn_add);
        btn_dele = findViewById(R.id.btn_dele);
        btn_que = findViewById(R.id.btn_que);
        btn_upd = findViewById(R.id.btn_update);
        edit_id = findViewById(R.id.id_edit);
        edit_name = findViewById(R.id.name_edit);
        edit_age = findViewById(R.id.age_edit);
        edit_info = findViewById(R.id.info_edit);
        textView = findViewById(R.id.text_data);

        btn_add.setOnClickListener(this);
        btn_dele.setOnClickListener(this);
        btn_que.setOnClickListener(this);
        btn_upd.setOnClickListener(this);

        List<User> users = DataSupport.findAll(User.class);
        showdb(users);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:

                User user = new User();
                Log.i("edit_id", edit_id.getText().toString());
                user.setId(Integer.parseInt(edit_id.getText().toString()));
                user.setName(edit_name.getText().toString());
                user.setAge(Integer.parseInt(edit_age.getText().toString()));
                user.setInfo(edit_info.getText().toString());
                user.save();

                List<User> afteradd = DataSupport.findAll(User.class);
                showdb(afteradd);
                break;

            case R.id.btn_dele:
                DataSupport.deleteAll(User.class, "id = ?", edit_id.getText().toString());
                List<User> afterdele = DataSupport.findAll(User.class);
                showdb(afterdele);
                break;

            case R.id.btn_que:
                List<User> quedata = DataSupport.where("id=?", edit_id.getText().toString()).find(User.class);
                showdb(quedata);

/*                        类似于sql语句的函数可以连缀组合调用
                List<User> usersques = DataSupport.select("id","name")
                        .where("id=?",edit_id.getText().toString())
                        .order("age")
                        .limit(10)
                        .find(User.class);
*/

                break;

            case R.id.btn_update:

                User upduser = new User();
                upduser.setName(edit_name.getText().toString());
                upduser.setAge(Integer.parseInt(edit_age.getText().toString()));
                upduser.setInfo(edit_info.getText().toString());
                upduser.updateAll("id=?", edit_id.getText().toString());

                List<User> afterupd = DataSupport.findAll(User.class);
                showdb(afterupd);

                break;
        }
    }
}
