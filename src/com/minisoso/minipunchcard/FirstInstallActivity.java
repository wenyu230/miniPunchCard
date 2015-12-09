package com.minisoso.minipunchcard;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

//minisoso
//minipunchcard
//20151203

public class FirstInstallActivity extends Activity{
	SharedPreferences mypreferences;//SharedPreferences是Android平台上一个轻量级的存储类，用来保存应用的一些常用配置。
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// hehe
		super.onCreate(savedInstanceState);
		//判断是否第一次安装
		//判断首次运行
		//mypreferences = getSharedPreferences("mycount", MODE_PRIVATE);
		mypreferences = getSharedPreferences("mystartboolean", MODE_PRIVATE);
		//SharedPreferences的四种操作模式，
		//Context.MODE_PRIVATE:为默认操作模式，代表该文件是私有数据，只能被应用本身访问，在该模式下，写入的内容会覆盖原文件的内容
		//Context.MODE_APPEND:模式会检查文件是否存在，存在就往文件追加内容，否则就创建新文件。
		//Context.MODE_WORLD_READABLE和Context.MODE_WORLD_WRITEABLE用来控制其他应用是否有权限读写该文件。
		//MODE_WORLD_READABLE:表示当前文件可以被其他应用读取。
		//MODE_WORLD_WRITEABLE:表示当前文件可以被其他应用写入。
		
		////int mycount = mypreferences.getInt("mystart_count", 0);
		//此时我觉得不用if...else...而用switch会好些
		boolean mystartboolean = mypreferences.getBoolean("mystart_boolean", false);
		//应该用boolean可读性更好
		
		//从sharePreferences里面找key为"mystart_count"的数据，如果有，说明之前保存过，则取"mystart_count"的值，如果没有该key，最后的int mycount将被赋予给出的默认值0.
		if(mystartboolean == false){
			Editor myeditor = mypreferences.edit();//存入数据。
			myeditor.putBoolean("mystart_boolean", true);//提交修改。
			myeditor.commit();
			
			Intent myintent = new Intent(FirstInstallActivity.this, GuideActivity.class);
			startActivity(myintent);
			FirstInstallActivity.this.finish();
			
		}
		if(mystartboolean == true){
			//LinearLayout.LayoutParams myparams = new LinearLayout.LayoutParams(
			//		LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			//LinearLayout mylinearLayout = new LinearLayout(this);
			//mylinearLayout.setLayoutParams(myparams);
			//mylinearLayout.setOrientation(LinearLayout.VERTICAL);
			//mylinearLayout.setBackgroundResource(R.drawable.mini_mainbg_default);
			
			new Handler().postDelayed(new Runnable(){
				@Override
				public void run(){
					Intent myintent = new Intent (FirstInstallActivity.this, MainActivity.class);
					startActivity(myintent);
					FirstInstallActivity.this.finish();
				}
			},1000);
			
			//setContentView(mylinearLayout);
		}
		
	}
}