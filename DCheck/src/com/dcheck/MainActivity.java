package com.dcheck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button speechCheck = null;
	EditText userName = null;
	Handler handler = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SystemCommon.setActivity(this);
		userName = (EditText) findViewById(R.id.editText_id);
		speechCheck = (Button) findViewById(R.id.button_speech);
		
		handler = new Handler(){
			 
			@Override
			public void handleMessage(Message msg) {
				
				super.handleMessage(msg);
				if (msg.what == 1) {
					Intent intent = new Intent();
					intent.putExtra("user", userName.getText().toString());
					intent.setClass(MainActivity.this, CheckActivity.class);
					startActivity(intent);
				}else
				if (msg.what == 2) {
					Toast.makeText(MainActivity.this, "«Î ‰»Î’˝»∑Õ¯÷∑", Toast.LENGTH_SHORT).show();
				}
				
			}
			
		};
		
		
		speechCheck.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Message message = new Message();
				String html = userName.getText().toString();
				if (!html.startsWith("http://")) {
					html = "http://"+html;
				}
				if(!html.startsWith("http://www.douban.com/people/")){
					message.what = 2;
					handler.sendMessage(message);
					return;
				}
//						try {
//							Jsoup.parse(new URL(html), 3000);
//						} catch (MalformedURLException e) {
//							message.what = 2;
//							handler.sendMessage(message);
//							return;
//						} catch (IOException e) {
//							message.what = 2;
//							handler.sendMessage(message);
//							return;
//						}
				message.what = 1;
				handler.sendMessage(message);	
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
