package com.whinc.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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
    private int mMaxCount = MAX_COUNT;
    private int mCount = 0;
    private Drawable mFillDrawable = null;
    private Drawable mEmptyDrawable = null;

    public void setOnCountChangeListener(@Nullable OnCountChangeListener countChangeListener) {
        mCountChangeListener = countChangeListener;
    }

    private OnCountChangeListener mCountChangeListener = null;
    private ImageView[] mImageViews = null;

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

    /**
     * <p>Get max rating count</p>
     * @return the max rating count
     */
    public int getMaxCount() {
        return mMaxCount;
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
        if (mCountChangeListener != null) {
            mCountChangeListener.onChange(oldCount, mCount);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        if (isInEditMode()) {
//            return;
        }

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

        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout rootLayout = (LinearLayout) inflater.inflate(R.layout.ratingbar, null);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(rootLayout, lp);

        mImageViews = new ImageView[mMaxCount];
        for (int i = 0; i < mMaxCount; ++i) {
            View view = inflater.inflate(R.layout.include_item, rootLayout, false);
            rootLayout.addView(view);
            mImageViews[i] = (ImageView)view.findViewById(R.id.star_imageView);
            mImageViews[i].setImageDrawable((i < mCount) ? mFillDrawable : mEmptyDrawable);
            mImageViews[i].setOnClickListener(this);
            mImageViews[i].setTag(i);
        }
    }

    /**
     * <p>Update the rating bar with current rating count.</p>
     */
    private void update() {
        for (int i = 0; i < mMaxCount; ++i) {
            mImageViews[i].setImageDrawable((i < mCount) ? mFillDrawable : mEmptyDrawable);
        }
    }

    @Override
    public void onClick(View v) {
        Integer index = (Integer) v.getTag();
        setCount(index + 1);
    }

    public interface OnCountChangeListener {
        /**
         * <p>This method will be execute after every change of rating bar </p>
         * @param preCount previous rating count
         * @param curCount current rating count
         */
        void onChange(int preCount, int curCount);
    }
}

