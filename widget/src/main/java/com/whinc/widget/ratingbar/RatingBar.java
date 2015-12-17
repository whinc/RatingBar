package com.whinc.widget.ratingbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * Created by wuhui on 10/16/15.<br>
 */

public class RatingBar extends FrameLayout implements View.OnClickListener {
    private static final String TAG = RatingBar.class.getSimpleName();
    private static final int MAX_COUNT = 5;

    /**
     * Max count of star
     */
    private int mMaxCount = MAX_COUNT;
    /**
     * Count of star
     */
    private int mCount = 0;
    /**
     * Fill drawable of star
     */
    private Drawable mFillDrawable = null;
    /**
     * Empty drawable of star
     */
    private Drawable mEmptyDrawable = null;
    private OnRatingChangeListener mOnRatingChangeListener = null;
    /**
     * Space between stars
     */
    private int mSpace = 0;

    public boolean isTouchRating() {
        return mTouchRating;
    }

    public void setTouchRating(boolean touchRating) {
        mTouchRating = touchRating;
    }

    public boolean isClickRating() {
        return mClickRating;
    }

    public void setClickRating(boolean clickRating) {
        mClickRating = clickRating;
    }

    private boolean mTouchRating = true;
    private boolean mClickRating = true;

    private LinearLayout mRootLayout;
    private ImageView[] mImageViews = null;
    private Context mContext;
    private float mOldX = 0;
    private float mOldY = 0;
    private int mOldStarCount = 0;

    public RatingBar(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public RatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RatingBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public int getSpace() {
        return mSpace;
    }

    /**
     * <p>Set space between stars </p>
     *
     * @param space space between stars in pixel unit
     */
    public void setSpace(int space) {
        space = Math.max(0, space);
        if (mSpace == space) {
            return;
        }

        mSpace = space;
        updateStarViews();
    }

    public Drawable getFillDrawable() {
        return mFillDrawable;
    }

    public void setFillDrawable(Drawable fillDrawable) {
        if (mFillDrawable == fillDrawable) {
            return;
        }
        mFillDrawable = fillDrawable;
        updateStarViews();
    }

    /**
     * <p>Set the fill star drawable resource </p>
     *
     * @param res drawable resource
     */
    public void setFillDrawableRes(@DrawableRes int res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setFillDrawable(mContext.getDrawable(res));
        } else {
            setFillDrawable(mContext.getResources().getDrawable(res));
        }
    }

    public Drawable getEmptyDrawable() {
        return mEmptyDrawable;
    }

    public void setEmptyDrawable(@Nullable Drawable emptyDrawable) {
        mEmptyDrawable = emptyDrawable;
        updateStarViews();
    }

    /**
     * <p>Set the empty star drawable resource </p>
     *
     * @param res drawable resource
     */
    public void setEmptyDrawableRes(@DrawableRes int res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setEmptyDrawable(mContext.getDrawable(res));
        } else {
            setEmptyDrawable(mContext.getResources().getDrawable(res));
        }
    }

    public void setOnRatingChangeListener(@Nullable OnRatingChangeListener listener) {
        mOnRatingChangeListener = listener;
    }

    /**
     * <p>Get max rating count</p>
     *
     * @return the max rating count
     */
    public int getMaxCount() {
        return mMaxCount;
    }

    /**
     * <p>Set max rating count which will lead to RatingBar refreshing immediately </p>
     *
     * @param maxCount
     */
    public void setMaxCount(int maxCount) {
        maxCount = Math.max(0, maxCount);
        if (maxCount == mMaxCount) {
            return;
        }
        mMaxCount = maxCount;
        createStarViews(maxCount);

        if (maxCount < mCount) {
            setCount(maxCount);
        }
    }

    /**
     * <p>Get rating count</p>
     *
     * @return the rating count.
     */
    public int getCount() {
        return mCount;
    }

    /**
     * <p>Set rating count, this will update rating bar immediately.</p>
     *
     * @param count the new rating count. If count small than 0 it will be set to 0, or if count
     *              large than max count it will be set to the max count.
     */
    public void setCount(int count) {
        count = Math.max(0, Math.min(mMaxCount, count));
        if (count == mCount) {
            return;
        }

        int oldCount = mCount;
        mCount = count;
        updateStarViews();
        if (mOnRatingChangeListener != null) {
            mOnRatingChangeListener.onChange(this, oldCount, mCount);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;

        // Retrieve attributes
        if (attrs == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mFillDrawable = context.getDrawable(R.drawable.fill);
                mEmptyDrawable = context.getDrawable(R.drawable.empty);
            } else {
                mFillDrawable = context.getResources().getDrawable(R.drawable.fill);
                mEmptyDrawable = context.getResources().getDrawable(R.drawable.empty);
            }
        } else {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
            mMaxCount = typedArray.getInteger(R.styleable.RatingBar_rb_max_count, MAX_COUNT);
            mCount = typedArray.getInteger(R.styleable.RatingBar_rb_count, 0);
            mFillDrawable = typedArray.getDrawable(R.styleable.RatingBar_rb_fill);
            mEmptyDrawable = typedArray.getDrawable(R.styleable.RatingBar_rb_empty);
            mSpace = typedArray.getDimensionPixelSize(R.styleable.RatingBar_rb_space, 0);
            mClickRating = typedArray.getBoolean(R.styleable.RatingBar_rb_click_rating, true);
            mTouchRating = typedArray.getBoolean(R.styleable.RatingBar_rb_touch_rating, true);
            typedArray.recycle();

            if (mFillDrawable == null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mFillDrawable = context.getDrawable(R.drawable.fill);
                } else {
                    mFillDrawable = context.getResources().getDrawable(R.drawable.fill);
                }
            }

            mMaxCount = Math.max(0, mMaxCount);
            mCount = Math.max(0, Math.min(mCount, mMaxCount));
        }

        // Create root layout (LinearLayout) used to contain stars
        mRootLayout = new LinearLayout(context);
        addView(mRootLayout, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        createStarViews(mMaxCount);
    }

    // create star views
    private void createStarViews(int count) {
        // remove previous child views
        if (mRootLayout.getChildCount() > 0) {
            mRootLayout.removeAllViews();
        }

        // create new image views
        mImageViews = new ImageView[count];
        for (int i = 0; i < mImageViews.length; ++i) {
            // Use FrameLayout to wrap the star view
            FrameLayout frameLayout = new FrameLayout(getContext());
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.MATCH_PARENT, 1
            );
            mRootLayout.addView(frameLayout, llp);

            mImageViews[i] = new ImageView(getContext());
            ImageView imageView = mImageViews[i];
            imageView.setOnClickListener(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setTag(i);
            FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    Gravity.CENTER
            );
            frameLayout.addView(imageView, flp);
        }

        updateStarViews();
    }

    /**
     * <p>Update the rating bar with current rating count.</p>
     */
    private void updateStarViews() {
        ImageView imgView;
        // update drawable
        for (int i = 0; i < mImageViews.length; ++i) {
            imgView = mImageViews[i];
            imgView.setImageDrawable((i < mCount) ? mFillDrawable : mEmptyDrawable);

            // update margin between the stars whose drawable is not null
            ViewGroup parent = (ViewGroup) imgView.getParent();
            MarginLayoutParams mlp = (MarginLayoutParams) parent.getLayoutParams();
            if (imgView.getDrawable() != null
                    && (i - 1) >= 0
                    && mImageViews[i - 1].getDrawable() != null) {
                mlp.setMargins(mSpace, 0, 0, 0);
            } else {
                mlp.setMargins(0, 0, 0, 0);
            }
            parent.setLayoutParams(mlp);
        }
    }

    @Override
    public void onClick(View v) {
        Integer index = (Integer) v.getTag();
        setCount(index + 1);
    }

    @Override
    public void setClickable(boolean clickable) {
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mOldX = event.getX();
                mOldY = event.getY();
                mOldStarCount = getTouchStarCount(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                if (mTouchRating) {
                    float deltaX = event.getX() - mOldX;
                    float deltaY = event.getY() - mOldY;
                    int distance = (int) Math.round(Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)));
                    if (distance >= ViewConfiguration.getTouchSlop()) {
                        setCount(getTouchStarCount(event));
                    }
                }
                mOldX = event.getX();
                mOldY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (mClickRating) {
                    int starCount = getTouchStarCount(event);
                    // if touch down and touch up hit the same view think it is a click event
                    if (starCount == mOldStarCount) {
                        setCount(starCount);
                    }
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event) && false;
    }

    private View getStarView(int index) {
        return mRootLayout.getChildAt(index);
    }

    /**
     * <p>Get the star count on specified touch position</p>
     * @param event touch event
     * @return selected star count
     */
    private int getTouchStarCount(MotionEvent event) {
        int count = 1;

        float rawX = event.getRawX();
        for (int i = 0; i < getMaxCount(); ++i) {
            Rect rect = new Rect();
            View view = getStarView(i);
            view.getGlobalVisibleRect(rect);
            MarginLayoutParams mlp = (MarginLayoutParams) view.getLayoutParams();
            if (rawX > rect.right + mlp.rightMargin) {
                count += 1;
            }
        }

        return count;
    }

    public interface OnRatingChangeListener {
        /**
         * <p>This method will be execute after every change of rating bar </p>
         *
         * @param preCount previous rating count
         * @param curCount current rating count
         */
        void onChange(RatingBar ratingBar, int preCount, int curCount);
    }

}

