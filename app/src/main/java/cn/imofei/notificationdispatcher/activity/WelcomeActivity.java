package cn.imofei.notificationdispatcher.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;
import cn.imofei.notificationdispatcher.R;


/**
 * 欢迎页.
 */
public class WelcomeActivity extends AppCompatActivity {
    private TextView tv_splash_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为无标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置为全屏模式
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //去掉顶部标题
        getSupportActionBar().hide();
        //去掉最上面时间、电量等
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome);

        // 取消变化 2018-12-22 11:01:56
        //背景透明度变化3秒内从0.3变到1.0
//        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
//        aa.setDuration(3000);
//        r1_splash.startAnimation(aa);

        //创建Timer对象
        Timer timer = new Timer();
        //创建TimerTask对象
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        //使用timer.schedule（）方法调用timerTask，定时3秒后执行run
        timer.schedule(timerTask, 3000);
    }

    /**
     * 获取当前软件版本号
     * @return
     */
    private String getVersion(){
        //得到系统的包管理器，已经得到了apk的面向对象包装
        PackageManager pm = this.getPackageManager();
        try{
            //参数一：当前应用程序的包名；
            //参数二：可选的附加信息，这里用不到，可以定义为0
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            return info.versionName;
        }catch (Exception e){//包名未找到异常，理论上，该异常不可能发生
            e.printStackTrace();
            return "";
        }
    }
}
