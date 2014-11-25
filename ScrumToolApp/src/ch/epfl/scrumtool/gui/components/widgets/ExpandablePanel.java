package ch.epfl.scrumtool.gui.components.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;
import ch.epfl.scrumtool.R;
import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;

/**
 * GUI element for an expandable panel.<br>
 * Credits go to
 * http://krishnalalstha.wordpress.com/2013/02/19/android-expandable-layout/
 * 
 * @author Cyriaque Brousse
 *
 */
public final class ExpandablePanel extends RelativeLayout {
    private static final int ANIMATION_DURATION = 500;

    private final int handleId;
    private final int contentId;

    private View handleView;
    private View contentView;

    private boolean isExpanded = false;
    private int collapsedHeight = 0;
    private int contentHeight = 0;

    private OnExpandListener expandListener;

//    public ExpandablePanel(Context context) {
//        this(context, null);
//    }

    public ExpandablePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        expandListener = new DefaultOnExpandListener();

        // Get attributes from TypedArray
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandablePanel, 0, 0);
        collapsedHeight = (int) a.getDimension(R.styleable.ExpandablePanel_expandablepanel_collapsedHeight, 0.0f);
        int handleId = a.getResourceId(R.styleable.ExpandablePanel_expandablepanel_handle, 0);
        if (handleId == 0) {
            throw new IllegalArgumentException("The handle attribute is required and must refer to a valid child.");
        }
        int contentId = a.getResourceId(R.styleable.ExpandablePanel_expandablepanel_content, 0);
        if (contentId == 0) {
            throw new IllegalArgumentException("The content attribute is required and must refer to a valid child.");
        }
        a.recycle();

        this.handleId = handleId;
        this.contentId = contentId;
    }

    public void setOnExpandListener(OnExpandListener listener) {
        this.expandListener = listener;
    }

    public void setCollapsedHeight(int collapsedHeight) {
        this.collapsedHeight = collapsedHeight;
    }

    /**
     * This method gets called when the View is physically visible to the user
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        handleView = findViewById(handleId);
        contentView = findViewById(contentId);
        throwIfNull("The handle and content ids must refer to an existing child",
                handleView, contentView);

        // This changes the height of the content such that it
        // starts off collapsed
        android.view.ViewGroup.LayoutParams lp = contentView.getLayoutParams();
        lp.height = collapsedHeight;
        contentView.setLayoutParams(lp);

        // Set the OnClickListener of the handle view
        handleView.setOnClickListener(new PanelToggler());
    }

    /**
     * This is where the magic happens for measuring the actual (un-expanded)
     * height of the content. If the actual height is less than the
     * collapsedHeight, the handle will be hidden.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // First, measure how high content wants to be
        contentView.measure(widthMeasureSpec, MeasureSpec.UNSPECIFIED);
        contentHeight = contentView.getMeasuredHeight();

        if (contentHeight < collapsedHeight) {
            handleView.setVisibility(View.GONE);
        } else {
            handleView.setVisibility(View.VISIBLE);
        }

        // Then let the usual thing happen
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * This is the on click listener for the handle. It basically just creates a
     * new animation instance and fires animation.
     */
    private class PanelToggler implements OnClickListener {
        public void onClick(View v) {
            Animation a;
            if (isExpanded) {
                a = new ExpandAnimation(contentHeight, collapsedHeight);
                expandListener.onCollapse(handleView, contentView);
            } else {
                a = new ExpandAnimation(collapsedHeight, contentHeight);
                expandListener.onExpand(handleView, contentView);
            }
            a.setDuration(ANIMATION_DURATION);
            contentView.startAnimation(a);
            isExpanded = !isExpanded;
        }
    }

    /**
     * This is a private animation class that handles the expand/collapse
     * animations. It uses the animationDuration attribute for the length of
     * time it takes.
     */
    private class ExpandAnimation extends Animation {
        private final int mStartHeight;
        private final int mDeltaHeight;

        public ExpandAnimation(int startHeight, int endHeight) {
            mStartHeight = startHeight;
            mDeltaHeight = endHeight - startHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime,
                Transformation t) {
            android.view.ViewGroup.LayoutParams lp = contentView
                    .getLayoutParams();
            lp.height = (int) (mStartHeight + mDeltaHeight * interpolatedTime);
            contentView.setLayoutParams(lp);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }

    /**
     * Simple OnExpandListener interface
     */
    public interface OnExpandListener {
        void onExpand(View handle, View content);

        void onCollapse(View handle, View content);
    }

    /**
     * Default implementation for OnExpandListener
     */
    private class DefaultOnExpandListener implements OnExpandListener {
        public void onCollapse(View handle, View content) { }

        public void onExpand(View handle, View content) { }
    }
}
