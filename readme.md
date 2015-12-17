
### RatingBar

A RatingBar that is easy to use and custom.

![](./screenshot.gif)

### Integration（集成）

The widget has been published on [jcenter][1], you can use it by add one line below in your module build.gradle.

    dependencies {
        ...
        compile 'com.whinc.widget.ratingbar:ratingbar:1.1.1'
    }

### How to use （如何使用）

Create RatingBar in xml layout:

    <com.whinc.widget.ratingbar.RatingBar
        android:id="@+id/ratingBar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:rb_max_count="5"
        app:rb_count="3"
        app:rb_empty="@drawable/empty"
        app:rb_fill="@drawable/fill"
        app:rb_space="10dp"
        app:rb_click_rating="true"
        app:rb_touch_rating="true"
        />

Create RatingBar in java code:

    RatingBar ratingBar2 = new RatingBar(this);
    ratingBar2.setMaxCount(7);
    ratingBar2.setCount(4);
    ratingBar2.setFillDrawableRes(R.drawable.empty);
    ratingBar2.setEmptyDrawableRes(R.drawable.fill);
    ratingBar2.setSpace(0);
    ratingBar2.setTouchRating(true);
    ratingBar2.setClickRating(true);
    ratingBar2.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
        @Override
        public void onChange(RatingBar view, int preCount, int curCount) {
            Log.i("TAG", String.format("previous count:%d, current count:%d", preCount, curCount));
        }
    });

>Note:If you don't want display empty star set it's drawable to null instead of transparent drawable.

### Customise （自定义）

First, make sure you have add below namespace to the layout root tag.

    xmlns:app="http://schemas.android.com/apk/res-auto"

Here is all the attributes you can use to customise RatingBar.(More Attributes will be add later.)

    * app:rb_max_count [Integer default:5] --> max rating count
    * app:rb_count [Integer default:0] --> rating count
    * app:rb_fill [Drawable default:@drawable/fill] --> star drawable tha has rating
    * app:rb_empty [Drawable default:@drawable/empty] --> star drawable that has no rating
    * app:rb_space [Dimension default:0] --> space between stars
    * app:rb_click_rating [Boolean default:true] --> enable/disable rating with click action
    * app:rb_touch_rating [Boolean default:true] --> enable/disable rating with touch action

### The MIT License (MIT)

Copyright (c) 2015 WuHui

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

[1]:https://bintray.com/whinc/maven/ratingbar/view
