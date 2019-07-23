# 工具集
## 已添加的工具
+ ToastUtil
+ SharePreferenceUtil
+ LogUtil

## 使用教程
### 1.引用类库
```groovy
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'androidx.appcompat:appcompat:1.0.2'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test:runner:1.2.0'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    implementation project(path: ':androidutil')
}
```
### 2.初始化
```java
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.initToastUtil(this);
        SharePreUtil.initSharePreUtil(this, "test.xml");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ToastUtil.releaseContext();
        SharePreUtil.release();
    }
}
```
### 3.示例代码
```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ToastUtil.showLong("显示");
        SharePreUtil.getInstance().put("name", "胡涛");
        SharePreUtil.getInstance().put("age", 22);
        SharePreUtil.getInstance().put("height", 1.75);
        SharePreUtil.getInstance().put("man", true);
        ToastUtil.showLong(SharePreUtil.getInstance().getString("name"));
    }
}
```

