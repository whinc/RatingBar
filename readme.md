
### RatingBar

Easy to use and customise.

![](./screenshot.gif)


### Integration（集成）

The widget has been hosted on jcenter, you can use it by add one line below in your module build.gradle.

```
dependencies {
    ...
    compile 'com.whinc.widget:ratingbar:1.1.2'
}
```

### How to use （如何使用）

Use in layout like below:

```
<com.whinc.widget.RatingBar
    android:id="@+id/ratingBar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:rb_count="3"
    app:rb_empty="@drawable/empty"
    app:rb_fill="@drawable/fill"
    app:rb_max_count="5"
    />
```

After find RatingBar, you can change it's rating count and listener to it's rating changing event.

```
RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
ratingBar.setCount(3);
ratingBar.setOnCountChangeListener(new RatingBar.OnCountChangeListener() {
    @Override
    public void onChange(int preCount, int curCount) {
    }
});
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

