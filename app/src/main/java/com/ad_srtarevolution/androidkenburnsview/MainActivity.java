package com.ad_srtarevolution.androidkenburnsview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.adapters.ListenerUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ad_srtarevolution.androidkenburnsview.databinding.ActivityMainBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.flaviofaria.kenburnsview.KenBurnsView;

public class MainActivity extends AppCompatActivity {

    KenBurnsView Image;
    Button resume,batal;
    private static final int SECOND_COLOR = Color.parseColor("#4648df");
    private ActivityMainBinding mBinding;
    TextInputLayout Tp_username,Tp_password;
    EditText Ed_username,Ed_password;
    public boolean interval;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        Tp_username=(TextInputLayout)findViewById(R.id.TI_username);
        Tp_password=(TextInputLayout)findViewById(R.id.TI_password);
        Ed_username=(EditText)findViewById(R.id.Ed_username);
        Ed_password=(EditText)findViewById(R.id.Ed_password);

        Ed_username.addTextChangedListener(new MyTextWatcher(Ed_username));
        Ed_password.addTextChangedListener(new MyTextWatcher(Ed_password));



//        resume=(Button)findViewById(R.id.bt_resume);
//        batal=(Button)findViewById(R.id.bt_batal);



//        resume.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Image.resume();
//            }
//        });

//        batal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Image.pause();
//            }
//        });
    }

    private void submitForm(){
        String username=Ed_username.getText().toString();
        String password=Ed_password.getText().toString();

        if(!ValidUsername())
            return;
        if (!ValidPassword())
            return;

        if(username.equals("Prayugo@ganteng.com")&&password.equals("ganteng")){
            interval=true;
            Toast.makeText(MainActivity.this,"Selamat Datang !!",Toast.LENGTH_SHORT).show();
            delayedStartNextActivity();
        }else {
            Toast.makeText(MainActivity.this,"Email/Password salah",Toast.LENGTH_SHORT).show();
            interval=false;

        }


    }
    private boolean ValidUsername(){
        String username=Ed_username.getText().toString().trim();
        if(username.isEmpty()||!isValidEmail(username)){
            Tp_username.setError("Masukan Email");
           // Toast.makeText(MainActivity.this,"Masukan Email Yang Benar !!",Toast.LENGTH_SHORT).show();
            RequestFoqus(Ed_username);
            return false;
        }else {
            Tp_username.setErrorEnabled(false);
        }
        return true;

    }

    private boolean ValidPassword(){
        String password=Ed_password.getText().toString().trim();

        if (password.trim().isEmpty()){
            Tp_password.setError("Masukan Password");
            //Toast.makeText(MainActivity.this,"Masukan Password Yang Benar !!",Toast.LENGTH_SHORT).show();
            RequestFoqus(Ed_password);
            return false;
        }else {
            Tp_password.setErrorEnabled(false);
        }
        return true;
    }

    private void RequestFoqus(View view){
        if(view.requestFocus())
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private static boolean isValidEmail(String username){

        return !TextUtils.isEmpty(username)&& Patterns.EMAIL_ADDRESS.matcher(username).matches();
    }

    private class MyTextWatcher implements TextWatcher{
        private View view;

        public  MyTextWatcher(View view){
            this.view=view;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()){
                case R.id.Ed_username:
                    ValidUsername();
                    break;
                case R.id.Ed_password:
                    ValidPassword();
                    break;
            }
        }
    }




//   ------------------------------ Animation-----------------------------
    public void  load(View view){
        animationButtonWidth();
        fadeOutTextAndShowProgressDialog();
        nextAction();
    }


    public void animationButtonWidth(){
        ValueAnimator anim=ValueAnimator.ofInt(mBinding.flButton.getMeasuredWidth(),getFabWidth());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val =(Integer) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams=mBinding.flButton.getLayoutParams();
                layoutParams.width=val;
                mBinding.flButton.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(250);
        anim.start();
    }
    public void fadeOutTextAndShowProgressDialog(){
        mBinding.tvSign.animate().alpha(0f)
                .setDuration(250)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        showProgressDialog();
                    }
                }).start();
    }

    public int getFabWidth(){
        return (int) 120 ;
    }

    public void showProgressDialog(){
        mBinding.pgSiginn.setAlpha(1f);
        mBinding.pgSiginn
                .getIndeterminateDrawable()
                .setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);;
        mBinding.pgSiginn.setVisibility(View.VISIBLE);
    }

    public void nextAction(){
    new Handler().postDelayed(new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            submitForm();
            revealButton();

            fadeOutProgressDialog();



        }
    },2000);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void revealButton(){
        mBinding.flButton.setElevation(0f);


        if(interval==true) {
            mBinding.reveal.setVisibility(View.VISIBLE);
        }else if(interval==false) {
            mBinding.reveal.setVisibility(View.INVISIBLE);

        }

        int cx =mBinding.reveal.getWidth();
        int cy=mBinding.reveal.getHeight();

        int x=(int)(getFabWidth()/2 +mBinding.flButton.getX());
        int y=(int)(getFabWidth()/2+mBinding.flButton.getY());

        float finalRadius=Math.max(cx,cy)*1.2f;
       Animator reveal= ViewAnimationUtils
                .createCircularReveal(mBinding.reveal,x,y,getFabWidth(),finalRadius);

        reveal.setDuration(350);
        reveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                reset(animation);
  //              finish();
            }
            private void reset(Animator animation){
                super.onAnimationEnd(animation);
                mBinding.reveal.setVisibility(View.INVISIBLE);
                mBinding.tvSign.setVisibility(View.VISIBLE);
                mBinding.tvSign.setAlpha(1f);
                mBinding.flButton.setElevation(4f);
                ViewGroup.LayoutParams layoutParams=mBinding.flButton.getLayoutParams();
                layoutParams.width=(int)(getResources().getDisplayMetrics().density*300);
                mBinding.flButton.requestLayout();
            }
        }


    );
        reveal.start();
    }

    public void fadeOutProgressDialog(){
        mBinding.pgSiginn.animate().alpha(0f).setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                }).start();
    }

    public  void delayedStartNextActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

             startActivity(new Intent(MainActivity.this,Home.class));
            }
        },100);
    }


}

