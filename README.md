###`@Deprecated`
Unfortunatenly this library is no longer maintained, we encourage you to use [first party VectorDrawableCompat coming soon to support library](https://android.googlesource.com/platform/frameworks/support/+/master/v7/vectordrawable/).

Mr. Vector
==========
![Mr. Vector](http://i.imgur.com/ucFr5T7.png)

AKA VectorDrawableCompat: A 7+ backport of [VectorDrawable](https://developer.android.com/reference/android/graphics/drawable/VectorDrawable.html).

### Demo

![Le demo](http://i.imgur.com/nG4uQiN.gif)

[![Mr. Vector Demo on Google Play Store](http://developer.android.com/images/brand/en_generic_rgb_wo_60.png)](https://play.google.com/store/apps/details?id=com.telly.mrvector.demo)

### Usage

See demo, at this point latest version looks like

```groovy
compile 'com.telly:mrvector:0.2.0'
```

### Basic inflate
```java
Drawable drawable = MrVector.inflate(getResources(), R.drawable.vector_android);
```

Unfortunately due some inflate weirdness (able to read some correctly but not others) for now (will fix promise) you'll have to duplicate (sucks I know) all your `android:` attributes, in example:

**Note**: you must put `auto` attributes before `android` attributes ([See #5](https://github.com/telly/MrVector/issues/5))

**Furthermore**: you can use online [convertor](http://ozodrukh.github.io/Svg2MrVector/) svg to VectorDrawable + MrVector scheme support

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:auto="http://schemas.android.com/apk/res-auto"
        tools:targetApi="21"

        auto:width="@dimen/simple_vector_width"
        auto:height="@dimen/simple_vector_height"
        auto:viewportWidth="@integer/simple_vector_viewport_width"
        auto:viewportHeight="@integer/simple_vector_viewport_height"

        android:width="@dimen/simple_vector_width"
        android:height="@dimen/simple_vector_height"
        android:viewportWidth="@integer/simple_vector_viewport_width"
        android:viewportHeight="@integer/simple_vector_viewport_height"
    >

  <path
      auto:strokeColor="@color/simple_vector_stroke_color"
      auto:strokeWidth="@integer/simple_vector_stroke_width"
      auto:pathData="@string/simple_vector_path_data"

      android:strokeColor="@color/simple_vector_stroke_color"
      android:strokeWidth="@integer/simple_vector_stroke_width"
      android:pathData="@string/simple_vector_path_data"
      />
</vector>
```

### Inflate from Layout (WIP)

Use it as a regular drawable:

```xml
<!-- menu.xml -->
<!-- ... -->
   android:icon="@drawable/vector_drawable"
<!-- ... -->
```

```xml
<!-- layout.xml -->
<!-- ... -->
   android:src="@drawable/vector_drawable"
<!-- ... -->
```

And then from your `Application` or `Activity`:

```java
\\ ...
  {{
    MrVector.register(
      R.drawable.vector_drawable,
      R.drawable.another_vector_drawable,
      \\ ...
    );
  }}
\\ ...
  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(MrVector.wrap(newBase));
  }
\\ ...
```

### Roadmap
Right now only basic inflating works, this is the list of features planed:

- [ ] Put this in GH issues.
- [ ] Full inflate from layout support (partially implemented except for `TypedArray` calling directly `loadDrawable`, which sadly is key)
- [ ] Get rid of `auto` namespace, use `android` namespace as much as possible (no duplicated attributes).
- [ ] Tint support.
- [ ] Animation support ([AnimatedVectorDrawable](https://developer.android.com/reference/android/graphics/drawable/AnimatedVectorDrawable.html)).

On the long run, it would be nice to see (but no promises):

- [ ] Per node animation.
- [ ] Additional SVG support (e.g. using [svg-android](https://code.google.com/p/svg-android/) or [svgandroid](https://code.google.com/p/androidsvg/)).
- [ ] SVG animation support.

###Applications using Mr.Vector:

[WatchMe](https://github.com/ozodrukh/Mover)

### License & About

See LICENSE file, logo built from [opoloo/androidicons](https://github.com/opoloo/androidicons).

From [@eveliotc](https://plus.google.com/u/0/+EvelioTarazonaC%C3%A1ceres/posts) @ [Telly](https://telly.com/)
