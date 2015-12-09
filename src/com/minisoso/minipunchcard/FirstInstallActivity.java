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
	SharedPreferences mypreferences;//SharedPreferences��Androidƽ̨��һ���������Ĵ洢�࣬��������Ӧ�õ�һЩ�������á�
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// hehe
		super.onCreate(savedInstanceState);
		//�ж��Ƿ��һ�ΰ�װ
		//�ж��״�����
		//mypreferences = getSharedPreferences("mycount", MODE_PRIVATE);
		mypreferences = getSharedPreferences("mystartboolean", MODE_PRIVATE);
		//SharedPreferences�����ֲ���ģʽ��
		//Context.MODE_PRIVATE:ΪĬ�ϲ���ģʽ��������ļ���˽�����ݣ�ֻ�ܱ�Ӧ�ñ�����ʣ��ڸ�ģʽ�£�д������ݻḲ��ԭ�ļ�������
		//Context.MODE_APPEND:ģʽ�����ļ��Ƿ���ڣ����ھ����ļ�׷�����ݣ�����ʹ������ļ���
		//Context.MODE_WORLD_READABLE��Context.MODE_WORLD_WRITEABLE������������Ӧ���Ƿ���Ȩ�޶�д���ļ���
		//MODE_WORLD_READABLE:��ʾ��ǰ�ļ����Ա�����Ӧ�ö�ȡ��
		//MODE_WORLD_WRITEABLE:��ʾ��ǰ�ļ����Ա�����Ӧ��д�롣
		
		////int mycount = mypreferences.getInt("mystart_count", 0);
		//��ʱ�Ҿ��ò���if...else...����switch���Щ
		boolean mystartboolean = mypreferences.getBoolean("mystart_boolean", false);
		//Ӧ����boolean�ɶ��Ը���
		
		//��sharePreferences������keyΪ"mystart_count"�����ݣ�����У�˵��֮ǰ���������ȡ"mystart_count"��ֵ�����û�и�key������int mycount�������������Ĭ��ֵ0.
		if(mystartboolean == false){
			Editor myeditor = mypreferences.edit();//�������ݡ�
			myeditor.putBoolean("mystart_boolean", true);//�ύ�޸ġ�
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