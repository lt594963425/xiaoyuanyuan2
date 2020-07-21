package com.tao.xiaoyuanyuan.base;

import android.util.Log;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * @ProjectName: xiaoyuanyuan2
 * @Package: com.tao.xiaoyuanyuan.base
 * @ClassName: WsListener
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/7/10 12:23
 * @UpdateUser: LiuTao
 */
public class WsListener  extends WebSocketListener {

    private String TAG ="WsListener";
    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosing(webSocket, code, reason);
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        super.onMessage(webSocket, text);
        Log.e(TAG, "客户端收到消息:" + text);
//        onWSDataChanged(DATE_NORMAL, text);
        //测试发消息
        webSocket.send("我是客户端，你好啊");
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
        super.onMessage(webSocket, bytes);
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
        Log.e(TAG,"连接成功！");
    }



}
