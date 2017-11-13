package com.ad_srtarevolution.androidkenburnsview;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ad_srtarevolution.androidkenburnsview.databinding.ActivityHomeBinding;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import static java.lang.Math.PI;


public class Home extends AppCompatActivity {

    private static final String  LOG_TAG="Home";
     private ActivityHomeBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_home);

        loadImages();
        animateImages();
        animateList();

    }
    private void loadImages(){
        Glide.with(this)
                .load(R.drawable.pray2)
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.circle)
                .into(new BitmapImageViewTarget(mBinding.profilePic){
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable=
                                RoundedBitmapDrawableFactory.create(getResources(),resource);
                        circularBitmapDrawable.setCircular(true);
                        mBinding.profilePic.setImageDrawable(circularBitmapDrawable);
                    }
                });

//        Glide.with(this)
//                .load("http://cdn.movieweb.com/img.news.tops/NExMf86CqvZ8AD_1_b.jpg")
//                .placeholder(R.color.placeholder)
//                .into(mBinding.top1);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setPointsPosition();
    }

    private void animateImages(){
        mBinding.profilePic
                .animate()
                .scaleX(1f)
                .scaleY(1f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(400)
                .start();

        mBinding.top1.startAnimation(getSlideDownAnimation());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.profilePoints
                        .animate()
                        .scaleY(1f)
                        .scaleX(1f)
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .setDuration(400)
                        .start();
            }
        },300);
    }

    private void setPointsPosition(){
        int right=mBinding.profilePic.getRight();
        int left=mBinding.profilePic.getLeft();

        int centerX=(right -left)/2 +left;
        int centerY=(int)(mBinding.profilePic.getHeight()/2+mBinding.profilePic.getY());
        int radius=right-centerX;

        double x =centerX + radius * Math.cos(PI/4);
        double y=centerY - radius * Math.cos(PI/4);

        mBinding.profilePoints.setX((float)x - mBinding.profilePoints.getHeight()/2);
        mBinding.profilePoints.setY((float)y - mBinding.profilePoints.getHeight()/2);
    }

    private void animateList(){
        final Animation slideDown=getSlideDownAnimationWithScale();
        mBinding.rowOne.setVisibility(View.INVISIBLE);
        mBinding.rowTwo.setVisibility(View.INVISIBLE);

        mBinding.secondTitle.startAnimation(slideDown);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.rowOne.startAnimation(getSlideDownAnimationWithScale());;
                mBinding.rowOne.setVisibility(View.VISIBLE);
            }
        },350);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.rowTwo.startAnimation(getSlideDownAnimationWithScale());;
                mBinding.rowTwo.setVisibility(View.VISIBLE);
            }
        },850);
    }

    public Animation getSlideDownAnimationWithScale(){
        return AnimationUtils.loadAnimation(Home.this,R.anim.slide_down_and_scale);
    }
    public Animation getSlideDownAnimation(){
        return AnimationUtils.loadAnimation(Home.this,R.anim.slide_down);
    }
}
