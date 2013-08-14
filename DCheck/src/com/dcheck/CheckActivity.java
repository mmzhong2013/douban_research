package com.dcheck;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CheckActivity extends Activity {

	TextView userName = null;
	Handler handler = null;
	ProgressBar progressBar = null;
	String name = null;
	ArrayList<String> teams = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_check);
		userName = (TextView) findViewById(R.id.textView_username);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		
		userName.setVisibility(View.INVISIBLE);
		handler = new Handler(){
			 
			@Override
			public void handleMessage(Message msg) {
				
				super.handleMessage(msg);
				if (msg.what == 1) {
					System.out.println(1);
					progressBar.setVisibility(View.GONE);
					userName.setVisibility(View.VISIBLE);
					name = msg.getData().getString("name");
					userName.setText(name);
					teams = msg.getData().getStringArrayList("teams");
					for (String string : teams) {
						System.out.println(string);
					}
				}else
				if (msg.what == 2) {
					System.out.println(2);
					userName.setVisibility(View.VISIBLE);
					progressBar.setVisibility(View.GONE);
					userName.setText("没有找到用户信息，请检查网络设置或者网址是否正确");
				}
				
			}
			
		};
		
		String user = getIntent().getStringExtra("user");
		
		user = user.split("/")[user.split("/").length-1];
		final String url ="http://www.douban.com/people/"+user+"/groups";
		new Thread(){
			@Override  public void run(){
				Message message = new Message();
				Bundle b = new Bundle();
				Document doc = null;
				
				try {
					doc = Jsoup.parse(new URL(url), 10000);
				} catch (MalformedURLException e) {
					message.what = 2;
					handler.sendMessage(message);
					return;
				} catch (IOException e) {
					message.what = 2;
					handler.sendMessage(message);
					return;
				}
				
				Element title = doc.getElementsByTag("title").get(0);
				
				
				if (title.html().endsWith("的小组")) {
					Elements team = doc.getElementsByClass("ob");
					ArrayList<String> teams = new ArrayList<String>();
					for (Element element2 : team) {
						Elements urls = element2.getElementsByTag("a");
						teams.add(urls.get(0).attr("href"));
						
					}
					String name = title.html().substring(0, title.html().length()-3);
					b.putString("name", name);
					b.putStringArrayList("teams", teams);
					message.what = 1;
					message.setData(b);
					handler.sendMessage(message);
					return;
				}
				
				message.what = 2;
				handler.sendMessage(message);
				
			}
		}.start();
		
			
			
		
	}

	
	
	class MyAdapter extends BaseAdapter{

		private Context context = null;
		
		public MyAdapter(Context c){
			super();
			this.context = c;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return teams.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
		
			LinearLayout linearLayout = new LinearLayout(context);
			TextView view = new TextView(context);
			view.setText(teams.get(arg0));
			view.setTextColor(Color.WHITE);
			view.setGravity(Gravity.CENTER_VERTICAL);
			view.setPadding((int) (SystemCommon.getWidthCoefficient()*30), 0, 0, 0);
			view.setHeight((int) (SystemCommon.getWidthCoefficient()*150));
			
			
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(R.drawable.ic_launcher);
			linearLayout.addView(imageView);
			linearLayout.addView(view);
			return linearLayout;
			
		}
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
