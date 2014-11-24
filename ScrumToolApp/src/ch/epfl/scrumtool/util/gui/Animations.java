package ch.epfl.scrumtool.util.gui;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import ch.epfl.scrumtool.R;
import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;

/**
 * Collection of useful Android animations
 * 
 * @author Cyriaque Brousse
 */
public final class Animations {
    
    public static void slideDown(Context context, View view) {
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.anim_slide_down);
        animate(view, anim);
    }
    
    public static void slideUp(Context context, View view) {
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.anim_slide_up);
        animate(view, anim);
    }

    private static void animate(View view, Animation anim) {
        throwIfNull("Animation and view must refer to existent elements", anim, view);
        anim.reset();
        view.clearAnimation();
        view.startAnimation(anim);
    }
    
}
