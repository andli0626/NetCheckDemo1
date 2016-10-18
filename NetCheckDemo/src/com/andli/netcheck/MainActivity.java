package com.andli.netcheck;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

	NetConnectBroadcastReceiver netBroadcastReceiver;
	TextView netStatusTXT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 动态注册：监听网络连接广播
		if (netBroadcastReceiver == null) {
			IntentFilter filter  = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
			netBroadcastReceiver = new NetConnectBroadcastReceiver();
			registerReceiver(netBroadcastReceiver, filter);
		}
		
		netStatusTXT = (TextView) findViewById(R.id.netstatus_txt);

	}

	@Override
	protected void onDestroy() {
		if (netBroadcastReceiver != null) {
			unregisterReceiver(netBroadcastReceiver);
		}
		super.onDestroy();
	}

	// 网络连接广播
	class NetConnectBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 网络连接 发生改变 执行此方法
			if (isNetworkConnected()) {
				// 网络正常
				netStatusTXT.setText("网络正常！");
			} else {
				// 网络异常
				netStatusTXT.setText("网络异常,请检查网络配置！");
			}
		}

	}

	// 检测 是否有网络（但是不能判断URL是否可用）
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null && ni.isConnectedOrConnecting() && ni.isAvailable()) {
			return true;
		} else {
			return false;
		}
	}

}
