package com.jiangcm.baselib.guideView.aty;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jiangcm.base.widght.guideview.Component;
import com.jiangcm.base.widght.guideview.Guide;
import com.jiangcm.base.widght.guideview.GuideBuilder;
import com.jiangcm.baselib.R;
import com.jiangcm.baselib.guideView.component.LottieComponent;
import com.jiangcm.baselib.guideView.component.MutiComponent;
import com.jiangcm.baselib.guideView.component.SimpleComponent;


public class SimpleGuideViewActivity extends Activity {

  private Button header_imgbtn;
  private LinearLayout ll_nearby, ll_video,ll_haha;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_simple_guide_view);
    header_imgbtn = (Button) findViewById(R.id.header_imgbtn);
    header_imgbtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(SimpleGuideViewActivity.this, "show", Toast.LENGTH_SHORT).show();
      }
    });
    ll_haha = (LinearLayout) findViewById(R.id.ll_haha);
    ll_nearby = (LinearLayout) findViewById(R.id.ll_nearby);
    ll_video = (LinearLayout) findViewById(R.id.ll_video);
    header_imgbtn.post(new Runnable() {
      @Override
      public void run() {
        showGuideView();
      }
    });
  }

  public void showGuideView() {
    GuideBuilder builder = new GuideBuilder();
    builder.setTargetView(ll_haha)
            .setAlpha(150)
            .setHighTargetCorner(20)
            .setHighTargetPadding(10);
    builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
      @Override
      public void onShown() {
      }

      @Override
      public void onDismiss() {
        showGuideView2();
      }
    });

    builder.addComponent(new SimpleComponent());
    Guide guide = builder.createGuide();
    guide.show(SimpleGuideViewActivity.this);
  }

  public void showGuideView2() {
    final GuideBuilder builder1 = new GuideBuilder();
    builder1.setTargetView(ll_nearby)
            .setAlpha(150)
            .setHighTargetGraphStyle(Component.CIRCLE);
    builder1.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
      @Override
      public void onShown() {
      }

      @Override
      public void onDismiss() {
        showGuideView3();
      }
    });

    builder1.addComponent(new MutiComponent());
    Guide guide = builder1.createGuide();
    guide.show(SimpleGuideViewActivity.this);
  }

  @SuppressLint("ResourceType")
  public void showGuideView3() {
    final GuideBuilder builder1 = new GuideBuilder();
    builder1.setTargetView(ll_video)
            .setAlpha(150)
            .setHighTargetCorner(20)
            .setHighTargetPadding(10)
            .setExitAnimationId(android.R.anim.fade_out);
    builder1.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
      @Override
      public void onShown() {
      }

      @Override
      public void onDismiss() {
      }
    });

    builder1.addComponent(new LottieComponent());
    Guide guide = builder1.createGuide();
    guide.setShouldCheckLocInWindow(false);
    guide.show(SimpleGuideViewActivity.this);
  }
}
