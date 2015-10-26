package com.whinc.widget.ratingbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * Created by wuhui on 10/16/15.<br>
 */

public class RatingBar extends FrameLayout implements View.OnClickListener{
    private static final String TAG = RatingBar.class.getSimpleName();
    private static final int MAX_COUNT = 5;

    /* Customise xml attributes */
    private int mMaxCount = MAX_COUNT;
    private int mCount = 0;
    private Drawable mFillDrawable = null;
    private Drawable mEmptyDrawable = null;
    private OnRatingChangeListener mOnRatingChangeListener = null;

    private LinearLayout mRootLayout;
    private ImageView[] mImageViews = null;
    private Context mContext;

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

    public Drawable getFillDrawable() {
        return mFillDrawable;
    }

    public void setFillDrawable(Drawable fillDrawable) {
        mFillDrawable = fillDrawable;
        update();
    }

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

    public void setEmptyDrawable(Drawable emptyDrawable) {
        mEmptyDrawable = emptyDrawable;
        update();
    }

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
     * @return the max rating count
     */
    public int getMaxCount() {
        return mMaxCount;
    }

    /**
     * <p>Set max rating count which will lead to RatingBar refreshing immediately </p>
     * @param maxCount
     */
    public void setMaxCount(int maxCount) {
        mMaxCount = maxCount;
        createChildViews(mContext, maxCount);
    }

    /**
     * <p>Get rating count</p>
     * @return the rating count.
     */
    public int getCount() {
        return mCount;
    }

    /**
     * <p>Set rating count, this will update rating bar immediately.</p>
     * @param count the new rating count. If count small than 0 it will be set to 0, or if count
     *              large than max count it will be set to the max count.
     */
    public void setCount(int count) {
        int oldCount = mCount;
        mCount = Math.max(0, Math.min(count, mMaxCount));
        update();
        if (mOnRatingChangeListener != null) {
            mOnRatingChangeListener.onChange(this, oldCount, mCount);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;

        // Retrieve attributes
        if (attrs == null) {
            mMaxCount = MAX_COUNT;
            mCount = 0;
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
            typedArray.recycle();

            if (mFillDrawable == null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mFillDrawable = context.getDrawable(R.drawable.fill);
                } else {
                    mFillDrawable = context.getResources().getDrawable(R.drawable.fill);
                }
            }
            if (mEmptyDrawable == null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mEmptyDrawable = context.getDrawable(R.drawable.empty);
                } else {
                    mEmptyDrawable = context.getResources().getDrawable(R.drawable.empty);
                }
            }

            mMaxCount = Math.max(0, mMaxCount);
            mCount = Math.max(0, Math.min(mCount, mMaxCount));
        }

        mRootLayout = new LinearLayout(context);
        addView(mRootLayout, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        createChildViews(context, mMaxCount);
    }

    private void createChildViews(Context context, int count) {
        // remove previous child views
        mRootLayout.removeAllViewsInLayout();

        // create new image views
        mImageViews = new ImageView[count];
        for (int i = 0; i < mImageViews.length; ++i) {
            FrameLayout frameLayout = new FrameLayout(context);
            mRootLayout.addView(frameLayout, new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.MATCH_PARENT, 1
            ));

            mImageViews[i] = new ImageView(context);
            ImageView imageView = mImageViews[i];
            imageView.setOnClickListener(this);
            imageView.setTag(i);
            frameLayout.addView(imageView, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER
            ));
        }

        update();
    }

    /**
     * <p>Update the rating bar with current rating count.</p>
     */
    private void update() {
        for (int i = 0; i < mMaxCount; ++i) {
            mImageViews[i].setImageDrawable((i < mCount) ? mFillDrawable : mEmptyDrawable);
            mImageViews[i].setClickable(isClickable());
        }
    }

    @Override
    public void onClick(View v) {
        Integer index = (Integer) v.getTag();
        setCount(index + 1);
    }

    @Override
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        update();
    }

    public interface OnRatingChangeListener {
        /**
         * <p>This method will be execute after every change of rating bar </p>
         * @param preCount previous rating count
         * @param curCount current rating count
         */
        void onChange(RatingBar ratingBar, int preCount, int curCount);
    }
}

