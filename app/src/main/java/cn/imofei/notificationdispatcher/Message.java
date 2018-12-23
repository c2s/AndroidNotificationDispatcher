package cn.imofei.notificationdispatcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class Message {
    final static String TAG = "NotificationMessage";

    public enum Type {
        Wechat,
        QQ,
        Alipay
    }

    public String content;
    public String from;
    public Type type;
    public Long time;
    public Long localTime;
    public Long unixTime;

    public static void send(Context context, Message message) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        String json = JSON.toJSONString(message);
        SharedPreferences preference = context.getSharedPreferences("config", MODE_PRIVATE);
        String url = preference.getString("url", "http://pay.qingsonge.com/addons/pay/api/notify1");
        String token = preference.getString("token", "test");
        String secret = preference.getString("secret", "test");
        String device_id = preference.getString("secret", "device_id");
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("token", token)
                .addHeader("secret", secret)
                .addHeader("device_id", device_id)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        Log.d(TAG, "message date = " + json);
        call.enqueue(new Callback() {
            //请求错误回调方法
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "connected failed", e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "response data = " + response.toString());
            }
        });
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", from='" + from + '\'' +
                ", type=" + type +
                ", time=" + time +
                ", localTime=" + localTime +
                ", unixTime=" + unixTime +
                '}';
    }
}
