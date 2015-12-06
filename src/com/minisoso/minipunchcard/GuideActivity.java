package com.minisoso.minipunchcard;

import java.util.ArrayList;






import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

//��һ�ΰ�װ�����û�
public class GuideActivity extends Activity {

	private ViewPager myviewPager;
	private ArrayList<View> mypageViews;
	private ImageView myimageView;
	private ImageView[] myimageViews;
	
	private ViewGroup mini_guidemain;//��������ͼƬ��LinearLayout
	private ViewGroup mini_guidepoint;//����СԲ���LinearLayout
	private ImageView mini_imageViewLeft;//�����ͷ��ť
	private ImageView mini_imageViewRight;//���Ҽ�ͷ��ť
	private int myCurrentIndex;//��ǰҳ��
	private ImageButton mini_enterButton;
	
	private int myAlpha = 0;//ImageView��alphaֵ��ͼƬ�����й�
	private boolean myisHide;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_guide);
		//��Ҫ��ʾ��ͼƬ�ŵ�ArrayList���У��浽��������
		LayoutInflater myinflater = getLayoutInflater();
		//LayoutInflater�������������findViewById(),ǰ��������res/layout/�µ�xml�����ļ�
		//��������xml�����ļ��µľ���widget�ؼ�����Button��TextView�ȣ�
		//LayoutInflater ��һ�������࣬���ĵ�������������
		//public abstract class LayoutInflater extends Object
		//��� LayoutInflater ʵ�������ַ�ʽ
		//1. LayoutInflater inflater = getLayoutInflater();//����Activity��getLayoutInflater() 
		//2. LayoutInflater inflater = LayoutInflater.from(context);  
		//3. LayoutInflater inflater =  (LayoutInflater)context.getSystemService
		//                              (Context.LAYOUT_INFLATER_SERVICE);
		mypageViews = new ArrayList<View>();
		mypageViews.add(myinflater.inflate(R.layout.activity_guide_list1, null));
		mypageViews.add(myinflater.inflate(R.layout.activity_guide_list2, null));
		mypageViews.add(myinflater.inflate(R.layout.activity_guide_list3, null));
		//��Ҫ��ʾ��ͼƬ�ŵ�ArrayList���У��浽��������
		myimageViews = new ImageView[mypageViews.size()];
		mini_guidemain = (ViewGroup)myinflater.inflate(R.layout.activity_guide, null);
		//��ͼƬ��ŵ�ImageView������
		mini_guidepoint = (ViewGroup)mini_guidemain.findViewById(R.id.mini_guide_point);
		myviewPager = (ViewPager)mini_guidemain.findViewById(R.id.mini_guide_viewpager);
		mini_imageViewLeft = (ImageView)mini_guidemain.findViewById(R.id.mini_arrowleftimageView);
		mini_imageViewRight = (ImageView)mini_guidemain.findViewById(R.id.mini_arrowrightimageView);
		mini_imageViewLeft.setAlpha(0);
		mini_imageViewRight.setAlpha(0);
		//��ȡ��ŵײ�����Բ��ViewGroup
		
		setContentView(mini_guidemain);
		
		mini_enterButton = (ImageButton) findViewById(R.id.clickenter_button);
		
		//��СԲ��ŵ�imageView������
		for(int i = 0; i < mypageViews.size(); i++){
			System.out.println("mypageViews.size() = " + mypageViews.size());
			myimageView = new ImageView(GuideActivity.this);
			myimageView.setLayoutParams(new LayoutParams(20, 20));
			myimageView.setPadding(20, 0, 20, 0);
			myimageViews[i] = myimageView;
			
			if(i == 0){
				//Ĭ��ѡ�е�һ��ͼƬ
				myimageViews[i].setBackgroundResource(R.drawable.mini_blue_point);
				
			}else{
				myimageViews[i].setBackgroundResource(R.drawable.mini_gray_point);
			}
			
			mini_guidepoint.addView(myimageViews[i]);
		}
		
		//setContentView(mini_guidemain);
		myviewPager.setAdapter(new GuidePageAdapter());
		myviewPager.setOnPageChangeListener(new GuidePageChangeListener());
		mini_imageViewLeft.setOnClickListener(new ButtonListener());
		mini_imageViewRight.setOnClickListener(new ButtonListener());
		mini_enterButton.setOnClickListener(new EnterButtonListener());
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.guide, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//add something
	//�����л���Ļ�İ�ť������  
    class ButtonListener implements OnClickListener{  
   
     @Override  
     public void onClick(View v) {  
         // TODO Auto-generated method stub  
         int showNext=0;  
         
         if(v.getId() == R.id.mini_arrowleftimageView) {  
             System.out.println("���������İ�ť");
             //mini_enterButton.setVisibility(View.INVISIBLE);
             if(myCurrentIndex ==0){
            	 showNext = myCurrentIndex;
             }else{
            	 showNext = myCurrentIndex-1;
             }
             myviewPager.setCurrentItem(showNext);  
         }  
         if(v.getId() == R.id.mini_arrowrightimageView){  
             System.out.println("��������ҵİ�ť");  
             if(myCurrentIndex == myimageViews.length){
            	 showNext = myCurrentIndex;
            	 //mini_enterButton.setVisibility(View.VISIBLE);
             }else{
            	 showNext = myCurrentIndex+1;
            	 //mini_enterButton.setVisibility(View.INVISIBLE);
             }
             myviewPager.setCurrentItem(showNext);  
         }
         if(showNext == 2){
        	 mini_enterButton.setVisibility(View.VISIBLE);
         }else{
        	 mini_enterButton.setVisibility(View.INVISIBLE);
         }
        	 //mini_enterButton.setVisibility(View.VISIBLE);
             System.out.println("��ǰҳ�룺"+showNext);  
           
     }  
       
    }  
    
    
    //enter
    class EnterButtonListener implements OnClickListener{
    	@Override  
        public void onClick(View v) {
    		Intent myintent = new Intent(GuideActivity.this, MainActivity.class);
			startActivity(myintent);
			GuideActivity.this.finish();
        }
    }
    	   
        
        
      
    /** 
     * ���ð�ť����Ч�� 
     */  
    private Handler mHandler = new Handler()  
    {  
        public void handleMessage(Message msg) {  
            if(msg.what==1 && myAlpha<255){             
                //ͨ�����ò�͸�������ð�ť�Ľ���Ч��  
                myAlpha += 50;  
                  
                if(myAlpha>255)  
                    myAlpha=255;  
                  
                mini_imageViewLeft.setAlpha(myAlpha);  
                mini_imageViewLeft.invalidate();  
                mini_imageViewRight.setAlpha(myAlpha);  
                mini_imageViewRight.invalidate();  
                  
                if(!myisHide && myAlpha<255)  
                    mHandler.sendEmptyMessageDelayed(1, 100);  
            }else if(msg.what==0 && myAlpha>0){  
                myAlpha -= 3;  
                  
                if(myAlpha<0)  
                    myAlpha=0;  
                mini_imageViewLeft.setAlpha(myAlpha);  
                mini_imageViewLeft.invalidate();  
                mini_imageViewRight.setAlpha(myAlpha);  
                mini_imageViewRight.invalidate();  
                  
                if(myisHide && myAlpha>0)  
                    mHandler.sendEmptyMessageDelayed(0, 2);  
            }              
        }  
    };  
      
    private void showImageButtonView(){  
        myisHide = false;  
        mHandler.sendEmptyMessage(1);  
    }  
      
    private void hideImageButtonView(){  
        new Thread(){  
            public void run() {  
                try {  
                    myisHide = true;  
                    mHandler.sendEmptyMessage(0);  
                } catch (Exception e) {  
                    ;  
                }  
            }  
        }.start();  
    }  
      
   
   
   
   
 @Override  
 public boolean dispatchTouchEvent(MotionEvent ev) {  
     System.out.println("this is dispatch");  
     System.out.println("������Ļ");  
        switch (ev.getAction()) {  
            case MotionEvent.ACTION_MOVE:  
            case MotionEvent.ACTION_DOWN:  
                showImageButtonView();              
                break;  
            case MotionEvent.ACTION_UP:  
                hideImageButtonView();                  
                break;  
        }  
          
          
     return super.dispatchTouchEvent(ev);  
 }  
   
 // ָ��ҳ������������,ʵ������������  
    class GuidePageAdapter extends PagerAdapter {    
         
        @Override    
        public int getCount() {    
            return mypageViews.size();    
        }    
    
        @Override    
        public boolean isViewFromObject(View arg0, Object arg1) {    
            return arg0 == arg1;    
        }    
    
        @Override    
        public int getItemPosition(Object object) {    
            // TODO Auto-generated method stub    
            return super.getItemPosition(object);    
        }    
    
        @Override    
        public void destroyItem(View arg0, int arg1, Object arg2) {    
            // TODO Auto-generated method stub    
            ((ViewPager) arg0).removeView(mypageViews.get(arg1));    
        }    
    
        @Override    
        public Object instantiateItem(View arg0, int arg1) {    
            // TODO Auto-generated method stub    
            ((ViewPager) arg0).addView(mypageViews.get(arg1));    
            return mypageViews.get(arg1);    
        }    
    
        @Override    
        public void restoreState(Parcelable arg0, ClassLoader arg1) {    
            // TODO Auto-generated method stub    
    
        }    
    
        @Override    
        public Parcelable saveState() {    
            // TODO Auto-generated method stub    
            return null;    
        }    
    
        @Override    
        public void startUpdate(View arg0) {    
            // TODO Auto-generated method stub    
    
        }    
    
        @Override    
        public void finishUpdate(View arg0) {    
            // TODO Auto-generated method stub    
    
        }    
    }   
      
 // ָ��ҳ������¼�������,���һ���ͼƬʱ��СԲ��任��ʾ��ǰͼƬλ��  
    class GuidePageChangeListener implements OnPageChangeListener {    
         
        @Override    
        public void onPageScrollStateChanged(int arg0) {    
            // TODO Auto-generated method stub    
    
        }    
    
        @Override    
        public void onPageScrolled(int arg0, float arg1, int arg2) {    
            // TODO Auto-generated method stub    
    
        }    
    
        @Override    
        public void onPageSelected(int arg0) {    
         myCurrentIndex = arg0;  
            for (int i = 0; i < myimageViews.length; i++) {    
                myimageViews[arg0].setBackgroundResource(R.drawable.mini_blue_point);  
                  
                if (arg0 != i) {    
                    myimageViews[i].setBackgroundResource(R.drawable.mini_gray_point);    
                }    
            }  
        }    
	}
}
