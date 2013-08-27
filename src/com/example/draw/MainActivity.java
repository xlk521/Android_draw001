package com.example.draw;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Paint.Style;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

@SuppressLint("DrawAllocation")
public class MainActivity extends Activity {

	private Paint paint = null;
	private Canvas canvas = null;
	private FrameLayout frame = null;
	private GridView gridView = null;
	private List<String> imgPaths = null;
	private static String[] img = new String[]{"jpg","png","gif"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/**
		 * 
		 * 加载图片资源
		 * 
		 * **/
		imgPaths = new ArrayList<String>();
		//获取sd卡的路径
		String sdPath = Environment.getExternalStorageDirectory() + "/";
		//遍历sd卡
		getFile(sdPath);
		if (imgPaths.size()<1) {
			return;
		}
		
		gridView = (GridView)findViewById(R.id.gridView1);
		BaseAdapter adapter = new BaseAdapter() {
			
			@Override
			public View getView(int pos, View convertView, ViewGroup parent) {
				ImageView imgView;
				if (convertView == null) {
					imgView = new ImageView(MainActivity.this);
					//设置图像的宽度和高度
					imgView.setAdjustViewBounds(true);
					imgView.setMaxHeight(113);
					imgView.setMaxWidth(150);
					imgView.setPadding(5, 5, 5, 5);
				}else{
					imgView = (ImageView)convertView;
				}
				//设置要显示的图片
				Bitmap bm = BitmapFactory.decodeFile(imgPaths.get(pos));
				imgView.setImageBitmap(bm);
				
				return imgView;
			}
			
			@Override
			public long getItemId(int p) {
				return p;
			}
			
			@Override
			public Object getItem(int p) {
				return p;
			}
			
			@Override
			public int getCount() {
				return imgPaths.size();
			}
		};
		gridView.setAdapter(adapter);
		
		
		/**
		 * 
		 * 画布，需要屏蔽掉上面的图片
		 * 
		 * **/
		
		frame = (FrameLayout)findViewById(R.id.fram);
		frame.addView(new myView(this));
		

//		//径向渐变
//		//角度渐变
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//判断是否为图片文件
	private static boolean isImg(String path){
		for (String format : img) {
			if (path.contains(format)) {
				return true;
			}
		}
		return false;
	}
	
	//遍历图片路径
	private void getFile(String url){
		File files = new File(url);
		File[] file = files.listFiles();
		try {
			for (File f : file) {
				if (f.isDirectory()) {
					getFile(f.getAbsolutePath());
				}else{
					if (isImg(f.getPath())) {
						imgPaths.add(f.getPath());
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public class myView extends View{

		public myView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onDraw(Canvas canvas) {
			//指定画布背景颜色
			canvas.drawColor(Color.WHITE);
			
			/**
			 * 
			 * 绘制带有填充性质的图
			 * 
			 * **/
			paint = new Paint();
			//线性渐变
			Shader shader = new LinearGradient(0, 0, 50, 50, Color.RED, Color.GREEN, Shader.TileMode.MIRROR);
			//为画笔设置渐变器
			paint.setShader(shader);
			//绘制矩形
			canvas.drawRect(10,70,100,150, paint);
			
			/**
			 * 
			 * 画图
			 * 
			 * **/
			//创建采用默认方式的画笔
			paint = new Paint();
			//使用抗锯齿功能
			paint.setAntiAlias(true);
			//设置笔触的宽度
			paint.setStrokeWidth(3);
			//设置填充样式为描边
			paint.setStyle(Style.STROKE);
			paint.setColor(Color.BLUE);
			//绘制蓝色的圆形
			canvas.drawCircle(50, 50, 30, paint);
			
			//安卓机器人
			paint = new Paint();

			paint.setAntiAlias(true);

			paint.setColor(0xffa4c739);
			//绘制机器人头
			RectF rectf = new RectF(10, 10, 100, 100);
			rectf.offset(100, 20);
			canvas.drawArc(rectf, -10, -160, false, paint);
			//绘制眼睛
			paint.setColor(Color.WHITE);
			canvas.drawCircle(135, 53, 4, paint);
			canvas.drawCircle(175, 53, 4, paint);
			//
			paint.setColor(0xffa4c739);
			paint.setStrokeWidth(2);
			canvas.drawLine(120, 15, 135, 35, paint);
			canvas.drawLine(190, 25, 175, 35, paint);
			
			/**
			 * 
			 * 绘制文字
			 * 
			 * **/
			//设置左对齐
			paint.setTextAlign(Align.LEFT);
			//设置文字大小
			paint.setTextSize(18);
			canvas.drawText("不，我不想去！", 163, 60, paint);
			float[] position = new float[]{74,215,97,215,120,215,143,215,53,240,71,240,89,240,107,240,125,240,143,240,161,240};
			canvas.drawPosText("你想和我一起去探险么？", position, paint);
		}
		
		
	}

}
