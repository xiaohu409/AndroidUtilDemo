# 工具集
## 已添加的工具
+ ToastUtil
+ SharePreferenceUtil
+ LogUtil
+ DateTimeUtil
+ BluetoothUtil
+ MVC Model
+ MVP Model
+ Retrofit2 + Rxjava2

## 使用教程
### 1.引用类库，在module的build.gradle添加依赖
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
        //初始化
        ToastUtil.initToastUtil(this);
        SharePreUtil.initSharePreUtil(this, "test.xml");
        LogUtil.setDebug(BuildConfig.DEBUG);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //释放引用
        ToastUtil.releaseContext();
        SharePreUtil.release();
    }
}
```
### 3.示例代码
#### （1）工具
```java
    SharePreUtil.getInstance().put("name", "胡涛");
    SharePreUtil.getInstance().put("age", 22);
    SharePreUtil.getInstance().put("height", 1.75);
    SharePreUtil.getInstance().put("man", true);
    ToastUtil.showLong(SharePreUtil.getInstance().getString("name"));
    LogUtil.logDebug(TAG, DateTimeUtil.getDateTime("yyyy-MM-dd HH:mm:ss"));
```
#### （2）扫描蓝牙设备
```java
    /**
     * 扫码蓝牙
     */
    private void scanBluetooth() {
        final Set<BluetoothDevice> deviceSet = new HashSet<>();
        BluetoothUtil bluetoothUtil = new BluetoothUtil(this);
        //设置蓝牙扫描回调方法
        bluetoothUtil.setBluetoothUtilCallback(new BluetoothUtil.BluetoothUtilCallback() {
            @Override
            public void onScanDevice(BluetoothDevice device) {
//                Log.d(TAG, device.getName() + ":" + device.getAddress());
                deviceSet.add(device);
            }

            @Override
            public void onStop() {
                System.out.println(deviceSet);
            }
        });
        //打开蓝牙
        bluetoothUtil.openBluetooth();
        //开始扫描
        bluetoothUtil.startDiscoverBluetooth();
    }
```
#### （3） Retrofit2 + RxJava2 示例
```java
    public class LoginModelImp implements LoginModel<LoginBean> {

    @Override
    public void login(Map<String, Object> param, final LoginView<LoginBean> view) {
        TMApiManager.newInstance()
                .getApiService()
                .login(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(LoginBean value) {
                System.out.println("onNext " + Thread.currentThread().getName());
                view.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError " + Thread.currentThread().getName());
                view.onFail(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
```
# MVC架构
    1.流程 view -> controller -> model -> view
    
# MVP架构
    1.流程 view -> presenter -> model -> presenter -> view

