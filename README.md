### LitePalDemo数据库的完整解决方案
github地址
https://github.com/Gong-Shijie/LitePalDemo.git  
添加依赖  
>implementation 'org.litepal.android:core:1.4.1'  
#### 添加assets目录并创建XML文件

![创建litepal.xml文件](https://upload-images.jianshu.io/upload_images/19741117-fadc3323d95f1254.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 修改配置文件
![修改配置文件name属性](https://upload-images.jianshu.io/upload_images/19741117-3136ac1673d3e454.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 创建javabean映射为数据表
![javabean作为映射创建数据表依据  别忘继承](https://upload-images.jianshu.io/upload_images/19741117-19d2d87d27f409b9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

每一个属性相当于表里的一列  
数据库升级时可以增加javabean或者修改增删除对应的属性  
将version属性值加1即可  
![litepal.xml 映射关联数据库和表](https://upload-images.jianshu.io/upload_images/19741117-18274e01fa9efd4f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

数据的增加删除查询修改都对应对于
**DataSupport内方法的调用**    
**调用方法返回结果是一个javabean对象的List**  
效果：  
![基本功能](https://upload-images.jianshu.io/upload_images/19741117-488ccc0c38d2e18e.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


***

#### 可视化数据库工具
添加依赖  
> debugImplementation 'com.amitshekhar.android:debug-db:1.0.0'

配置手机的adb环境方法如下：
https://jingyan.baidu.com/article/17bd8e52f514d985ab2bb800.html  
输入adb forward tcp:8080 tcp:8080  
如果你的端口8080被占用则使用其他端口  
比如修改为8088端口（位于app的gradle文件）  
```
buildTypes {

release {

minifyEnabled false

            proguardFiles getDefaultProguardFile('proguard-android.txt'),'proguard-rules.pro'

        }

debug {//这里

            resValue("string","PORT_NUMBER","8088")

}}}
```
保证手机和电脑出于相同wifi下  
run项目然后浏览器输入localhost：8080就可以看到你的数据库和表  

![Android数据库可视化工具](https://upload-images.jianshu.io/upload_images/19741117-789e157ecdac5792.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

***
#### 沉浸式界面布局
在stylt.xml下修改->创建一个notitle的主题后面我们会在配置文件使用它
```
<resources>

    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>

    <style name="NoTitle" parent="Theme.AppCompat.DayNight.NoActionBar">
        <item name="android:windowNoTitle">true</item>
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="android:windowFullscreen">true</item>
    </style>

</resources>
```
当前主题使用到的颜色
```
<?xml version="1.0" encoding="utf-8"?>

<resources>
    <color name="colorPrimary">#FFFFFF</color>
    <color name="colorPrimaryDark">#999999</color>

<!--    edittext下划线和光标颜色-->
    <color name="colorAccent">#999999</color>
</resources>
```
可以修改为自己所需要的颜色  
最后一步修改配置文件  
![引用声明的样式即可](https://upload-images.jianshu.io/upload_images/19741117-e34692a7f832a555.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
这样app将根据配置的style notitle的style就不显示状态栏  
沉浸式布局的解决方案  
***
#### 数据库增删查改代码：
```

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
```
***
#### XML布局： 
**同类的控件都放在一个父布局内  
比如本文的四个edittext和四个button放在父布局内  
所有在竖直方向上面的位置摆放都  
通过在父布局内配置margin来实现**    
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">


    <androidx.percentlayout.widget.PercentRelativeLayout xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
<androidx.percentlayout.widget.PercentRelativeLayout
    android:layout_width="match_parent"
    app:layout_marginTopPercent="5%"
    android:layout_height="match_parent">
    <EditText
        android:id="@+id/id_edit"
        android:layout_width="wrap_content"
        app:layout_widthPercent="25%"
        android:layout_height="wrap_content"
        android:hint="id"
        android:textSize="30sp"
        ></EditText>
    <EditText
        android:id="@+id/name_edit"
        app:layout_widthPercent="25%"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignTop="@id/id_edit"
        android:layout_toRightOf="@id/id_edit"
        android:hint="name"
        android:textSize="30sp"></EditText>

    <EditText
        android:id="@+id/age_edit"
        android:layout_width="wrap_content"
        app:layout_widthPercent="25%"
        android:layout_height="wrap_content"
        android:layout_alignTop="@id/id_edit"
        android:layout_toRightOf="@id/name_edit"
        android:hint="age"
        android:textSize="30sp"></EditText>

    <EditText
        app:layout_widthPercent="25%"
        android:id="@+id/info_edit"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"

        android:layout_alignTop="@id/id_edit"
        android:layout_toRightOf="@id/age_edit"
        android:hint="info"
        android:textSize="30sp"></EditText>

</androidx.percentlayout.widget.PercentRelativeLayout>


        <androidx.percentlayout.widget.PercentRelativeLayout xmlns:tools="http://schemas.android.com/tools"
            app:layout_marginTopPercent="13%"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <Button
                android:id="@+id/btn_add"
                android:layout_width="wrap_content"
                app:layout_widthPercent="25%"
                android:layout_height="wrap_content"
                android:hint="增加"
                android:textSize="25sp"
                ></Button>

            <Button
                android:id="@+id/btn_dele"
                android:layout_toRightOf="@id/btn_add"
                android:layout_width="wrap_content"
                app:layout_widthPercent="25%"
                android:layout_height="wrap_content"
                android:hint="删除"
                android:textSize="25sp"
                ></Button>

            <Button
                android:id="@+id/btn_que"
                android:layout_toRightOf="@id/btn_dele"
                android:layout_width="wrap_content"
                app:layout_widthPercent="25%"
                android:layout_height="wrap_content"
                android:hint="查找"
                android:textSize="25sp"
                ></Button>

            <Button

                android:id="@+id/btn_update"
                android:layout_toRightOf="@id/btn_que"
                android:layout_width="wrap_content"
                app:layout_widthPercent="25%"
                android:layout_height="wrap_content"
                android:hint="修改"
                android:textSize="25sp"
                ></Button>
            <RelativeLayout
                android:layout_width="match_parent"
                android:background="@color/colorPrimary"
                android:layout_below="@id/btn_que"
                android:layout_height="match_parent">
                <TextView
                    android:layout_marginTop="10dp"
                    android:layout_marginLeft="15dp"
                    android:id="@+id/text_data"
                    android:inputType="textMultiLine"
                    android:textSize="30sp"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"></TextView>
            </RelativeLayout>
            </androidx.percentlayout.widget.PercentRelativeLayout>
    </androidx.percentlayout.widget.PercentRelativeLayout>
</RelativeLayout>
<!--尽量减少独立的控件都将相似的控件放在一个布局内-->
<!--控件摆放的竖直方向位置尽量在父布局内设置-->
```