
### RatingBar

Easy to use and customise.

![](./screenshot.gif)


### Integration（集成）

The widget has been hosted on jcenter, you can use it by add one line below in your module build.gradle.

```
dependencies {
    ...
    compile 'com.whinc.widget.ratingbar:ratingbar:1.0.2'
}
```

### How to use （如何使用）

Create RatingBar in xml layout:

```
<com.whinc.widget.ratingbar.RatingBar
    android:id="@+id/ratingBar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:rb_count="3"
    app:rb_empty="@drawable/empty"
    app:rb_fill="@drawable/fill"
    app:rb_max_count="5"
    />
```

Create RatingBar in java code:

```
RatingBar ratingBar2 = new RatingBar(this);
ratingBar2.setMaxCount(7);
ratingBar2.setCount(4);
ratingBar2.setFillDrawableRes(R.drawable.empty);
ratingBar2.setEmptyDrawableRes(R.drawable.fill);
ratingBar2.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
    @Override
    public void onChange(RatingBar view, int preCount, int curCount) {
        Log.i("TAG", String.format("previous count:%d, current count:%d", preCount, curCount));
    }
});
```

If you don't want RatingBar is clickable, do this:
```
<com.whinc.widget.ratingbar.RatingBar
    ...
    android:clickable="false"
    />
```
or
```
ratingBar2.setClickable(false);
```

### Customise （自定义）

First, make sure you have add below namespace to the layout root tag.

```
xmlns:app="http://schemas.android.com/apk/res-auto"
```

Here is all the attributes you can use to customise RatingBar.(More Attributes will be add later.)

* app:rb_max_count [Integer default:5] --> max rating count
* app:rb_count [Integer default:0] --> current rating count
* app:rb_fill [Drawable default:@drawable/fill] --> star drawable tha has rating
* app:rb_empty [Drawable default:@drawable/empty] --> star drawable that has no rating

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

