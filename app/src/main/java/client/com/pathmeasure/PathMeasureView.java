package client.com.pathmeasure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class PathMeasureView extends View {
    private Paint mPaint;
    private Paint mLinePaint;//坐标系
    private Bitmap mBitmap;
    public PathMeasureView(Context context) {
        this(context,null);
    }

    public PathMeasureView(Context context,  AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathMeasureView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint=new Paint();
        mLinePaint=new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(4);

        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.RED);
        mLinePaint.setStrokeWidth(6);
        //缩小图
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize=4;
        mBitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.arrow,options);


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //水平方向x轴（高度为屏幕高度的一半）
        canvas.drawLine(0,getHeight()/2,getWidth(),getHeight()/2,mLinePaint);
        //垂直方向y轴（宽度为屏幕宽度的一半）
        canvas.drawLine(getWidth()/2,0,getWidth()/2,getHeight(),mLinePaint);

        //平移画布，将坐标系的中心平移到上面两条线的交点
        canvas.translate(getWidth()/2,getHeight()/2);


//        Path path=new Path();
//        path.lineTo(0,200);
//        path.lineTo(200,200);
//        path.lineTo(200,0);

        /**
         * pathMeasure 需要关联一个为创建好的path,forceClose会影响path的测量结果
         * */
        //PathMeasure 无参构造
//        PathMeasure pathMeasure=new PathMeasure();
//        pathMeasure.setPath(path,true);
//        Log.e("==forceClose==true==",pathMeasure.getLength()+"");
//
//        PathMeasure pathMeasure2=new PathMeasure();
//        pathMeasure2.setPath(path,false);
//        Log.e("==forceClose=false=",pathMeasure2.getLength()+"");
//
//        //PathMeasure 有参构造
//        PathMeasure pathMeasure13=new PathMeasure(path,false);
//        Log.e("PathMeasure（path,false)",pathMeasure13.getLength()+"");
//
//        //注意：pathMeasure关联的path如果进行了调整，那需要重新调用setPath重新进行关联
//        path.lineTo(200,-200);
//        Log.e("PathMeasure（path,false)",pathMeasure13.getLength()+"");
//
//        pathMeasure13.setPath(path,false);
//        Log.e("PathMeasure（path,false)",pathMeasure13.getLength()+"");

//        Path path=new Path();
//        path.addRect(-200,-200,200,200,Path.Direction.CW);
//        Path dst=new Path();
//        dst.lineTo(-300,-300);//添加一条直线
//        PathMeasure pathMeasure=new PathMeasure(path,false);
//        //截取一部分存入dst中，并且用startWithMoveTo=true保持截取到的Path第一个位置不变
//        pathMeasure.getSegment(200,1000,dst,true);
        //截取一部分存入dst中，并且用startWithMoveTo=false保持截取到的Path第一个位置以原来的Path的最后一个位置为起点
//        pathMeasure.getSegment(200,1000,dst,false);


//        canvas.drawPath(path,mPaint);
//        canvas.drawPath(dst,mLinePaint);

//        Path path=new Path();
//        path.addRect(-100,-100,100,100,Path.Direction.CW);//添加一个矩形
//        path.addOval(-200,-200,200,200,Path.Direction.CW);//添加一个椭圆
//        canvas.drawPath(path,mPaint);
//        PathMeasure pathMeasure=new PathMeasure(path,false);
//        Log.e("====pathMeasure===",pathMeasure.getLength()+"");
//        //跳转下一条曲线
//        pathMeasure.nextContour();
//        Log.e("====pathMeasure=11==",pathMeasure.getLength()+"");

        path.reset();
        path.addCircle(0,0,200,Path.Direction.CW);
        canvas.drawPath(path,mPaint);
        mFloat+=0.01;
        if(mFloat>=1){
            mFloat=0;
        }
//        PathMeasure pathMeasure=new PathMeasure(path,false);
//
//        pathMeasure.getPosTan(pathMeasure.getLength()*mFloat,pos,tan);
//        Log.e("+++ondraw+++++++++","pos[0]="+pos[0]+";pos[1]="+pos[1]);
//        Log.e("+++ondraw+++++++++","tan [0]="+tan[0]+";pos[1]="+tan[1]);
//        double degress=Math.atan2(tan[1],tan[0])*180/Math.PI;
//        Log.e("TAG","onDraw:degree="+degress);
//
//        mMatrix.reset();
//        //进行角度旋转
//        mMatrix.postRotate((float) degress,mBitmap.getWidth()/2,mBitmap.getHeight()/2);
//        //将图片的绘制中心点会与当前点重合
//        mMatrix.postTranslate(pos[0]-mBitmap.getWidth()/2,pos[1]-mBitmap.getHeight()/2);
//        canvas.drawBitmap(mBitmap,mMatrix,mPaint);

        PathMeasure pathMeasure=new PathMeasure(path,false);
//        //将pos信息和tan信息保存在mMatrix中;
        pathMeasure.getMatrix(pathMeasure.getLength() * mFloat, mMatrix, PathMeasure.POSITION_MATRIX_FLAG | PathMeasure.TANGENT_MATRIX_FLAG);

//        mMatrix.postTranslate(-mBitmap.getWidth()/2,-mBitmap.getHeight()/2);
        // 将图片的旋转坐标调整到图片的中心位置
        mMatrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap,mMatrix,mPaint);
        invalidate();



    }
    private Matrix mMatrix=new Matrix();
    private float[] pos=new float[2];
    private float[] tan=new float[2];
    private Path path=new Path();
    private float mFloat;
}
