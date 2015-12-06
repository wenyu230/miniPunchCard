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

//第一次安装引导用户
public class GuideActivity extends Activity {

	private ViewPager myviewPager;
	private ArrayList<View> mypageViews;
	private ImageView myimageView;
	private ImageView[] myimageViews;
	
	private ViewGroup mini_guidemain;//包裹滑动图片的LinearLayout
	private ViewGroup mini_guidepoint;//包裹小圆点的LinearLayout
	private ImageView mini_imageViewLeft;//向左箭头按钮
	private ImageView mini_imageViewRight;//向右箭头按钮
	private int myCurrentIndex;//当前页码
	private ImageButton mini_enterButton;
	
	private int myAlpha = 0;//ImageView的alpha值，图片渐变有关
	private boolean myisHide;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_guide);
		//将要显示的图片放到ArrayList当中，存到适配器中
		LayoutInflater myinflater = getLayoutInflater();
		//LayoutInflater这个类作用类似findViewById(),前者用来找res/layout/下的xml布局文件
		//后者是找xml布局文件下的具体widget控件（如Button、TextView等）
		//LayoutInflater 是一个抽象类，在文档中如下声明：
		//public abstract class LayoutInflater extends Object
		//获得 LayoutInflater 实例的三种方式
		//1. LayoutInflater inflater = getLayoutInflater();//调用Activity的getLayoutInflater() 
		//2. LayoutInflater inflater = LayoutInflater.from(context);  
		//3. LayoutInflater inflater =  (LayoutInflater)context.getSystemService
		//                              (Context.LAYOUT_INFLATER_SERVICE);
		mypageViews = new ArrayList<View>();
		mypageViews.add(myinflater.inflate(R.layout.activity_guide_list1, null));
		mypageViews.add(myinflater.inflate(R.layout.activity_guide_list2, null));
		mypageViews.add(myinflater.inflate(R.layout.activity_guide_list3, null));
		//将要显示的图片放到ArrayList当中，存到适配器中
		myimageViews = new ImageView[mypageViews.size()];
		mini_guidemain = (ViewGroup)myinflater.inflate(R.layout.activity_guide, null);
		//将图片存放到ImageView集合中
		mini_guidepoint = (ViewGroup)mini_guidemain.findViewById(R.id.mini_guide_point);
		myviewPager = (ViewPager)mini_guidemain.findViewById(R.id.mini_guide_viewpager);
		mini_imageViewLeft = (ImageView)mini_guidemain.findViewById(R.id.mini_arrowleftimageView);
		mini_imageViewRight = (ImageView)mini_guidemain.findViewById(R.id.mini_arrowrightimageView);
		mini_imageViewLeft.setAlpha(0);
		mini_imageViewRight.setAlpha(0);
		//获取存放底部导航圆点ViewGroup
		
		setContentView(mini_guidemain);
		
		mini_enterButton = (ImageButton) findViewById(R.id.clickenter_button);
		
		//将小圆点放到imageView数组中
		for(int i = 0; i < mypageViews.size(); i++){
			System.out.println("mypageViews.size() = " + mypageViews.size());
			myimageView = new ImageView(GuideActivity.this);
			myimageView.setLayoutParams(new LayoutParams(20, 20));
			myimageView.setPadding(20, 0, 20, 0);
			myimageViews[i] = myimageView;
			
			if(i == 0){
				//默认选中第一张图片
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
	//左右切换屏幕的按钮监听器  
    class ButtonListener implements OnClickListener{  
   
     @Override  
     public void onClick(View v) {  
         // TODO Auto-generated method stub  
         int showNext=0;  
         
         if(v.getId() == R.id.mini_arrowleftimageView) {  
             System.out.println("点击了向左的按钮");
             //mini_enterButton.setVisibility(View.INVISIBLE);
             if(myCurrentIndex ==0){
            	 showNext = myCurrentIndex;
             }else{
            	 showNext = myCurrentIndex-1;
             }
             myviewPager.setCurrentItem(showNext);  
         }  
         if(v.getId() == R.id.mini_arrowrightimageView){  
             System.out.println("点击了向右的按钮");  
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
             System.out.println("当前页码："+showNext);  
           
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
     * 设置按钮渐显效果 
     */  
    private Handler mHandler = new Handler()  
    {  
        public void handleMessage(Message msg) {  
            if(msg.what==1 && myAlpha<255){             
                //通过设置不透明度设置按钮的渐显效果  
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
     System.out.println("触碰屏幕");  
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
   
 // 指引页面数据适配器,实现适配器方法  
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
      
 // 指引页面更改事件监听器,左右滑动图片时候，小圆点变换显示当前图片位置  
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
