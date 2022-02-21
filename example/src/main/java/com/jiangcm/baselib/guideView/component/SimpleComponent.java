package com.jiangcm.baselib.guideView.component;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.jiangcm.base.widght.guideview.Component;
import com.jiangcm.baselib.R;

/**
 * Created by binIoter on 16/6/17.
 */
public class SimpleComponent implements Component {

  @Override public View getView(LayoutInflater inflater) {

    ConstraintLayout ll = (ConstraintLayout) inflater.inflate(R.layout.layer_frends, null);
    ll.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Toast.makeText(view.getContext(), "引导层被点击了", Toast.LENGTH_SHORT).show();
      }
    });
    ll.post(() -> {
      Log.v("thss","ll "+ll.getWidth())     ;
      Log.v("thss","ll "+ll.getHeight())     ;
    });
    return ll;
  }

  @Override public int getAnchor() {
    return Component.ANCHOR_OVER;
  }

  @Override public int getFitPosition() {
    return Component.FIT_CENTER;
  }

  @Override public int getXOffset() {
    return 0;
  }

  @Override public int getYOffset() {
    return 10;
  }
}
