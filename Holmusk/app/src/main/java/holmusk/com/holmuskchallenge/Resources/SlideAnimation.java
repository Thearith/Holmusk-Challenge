package holmusk.com.holmuskchallenge.Resources;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import holmusk.com.holmuskchallenge.R;

/**
 * Created by thearith on 25/4/16.
 */

public class SlideAnimation {

    public static void slideDown(Context context, View view) {
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        if(anim != null){
            anim.reset();
            if(view != null){
                view.clearAnimation();
                view.startAnimation(anim);
            }
        }
    }

    public static void slideUp(Context context, View view) {
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.slide_up);

        if(anim != null){
            anim.reset();

            if(view != null){
                view.clearAnimation();
                view.startAnimation(anim);
            }
        }
    }
}
