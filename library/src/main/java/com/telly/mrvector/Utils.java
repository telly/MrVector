/*
 * Copyright (C) Telly, Inc. and other contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.telly.mrvector;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;

import java.lang.reflect.Method;

import static android.graphics.PorterDuff.Mode;
import static android.graphics.PorterDuff.Mode.SRC_IN;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.HONEYCOMB;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.util.LayoutDirection.LTR;
import static com.telly.mrvector.VectorDrawable.LOGTAG;

/**
 * @hide
 */
public class Utils {
  /**
   * Stolen from Drawable
   */
  static final Mode DEFAULT_TINT_MODE = SRC_IN;
  static final boolean LOLLIPOP_PLUS = SDK_INT >= LOLLIPOP;
  static final boolean HONEYCOMB_PLUS = SDK_INT >= HONEYCOMB;

  static SimpleArrayMap<String, Method> sCachedMethods = new SimpleArrayMap<>();

  final static Class[] INT_ARG = {int.class};
  final static Class[] MODE_ARG = {Mode.class};
  final static Class[] EMPTY_ARG = {};

  @SuppressWarnings("unchecked")
  public static <T> T tryInvoke(Object target, String methodName, Class<?>[] argTypes,
                         Object... args) {

    try {
      Method method = sCachedMethods.get(methodName);
      if(method != null){
        return (T) method.invoke(target, args);
      }

      method = target.getClass().getDeclaredMethod(methodName, argTypes);
      sCachedMethods.put(methodName, method);

      return (T) method.invoke(target, args);
    } catch (Exception pokemon) {
      Log.e(LOGTAG, "Unable to invoke " + methodName + " on " + target, pokemon);
    }

    return null;
  }

  static int getLayoutDirection(Drawable drawable) {
    final Integer layoutDirection = tryInvoke(drawable, "getLayoutDirection", EMPTY_ARG);
    return layoutDirection == null ? LTR : layoutDirection.intValue();
  }

  /**
   * Parses a {@link Mode} from a tintMode
   * attribute's enum value.
   *
   * @hide
   */
  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  static Mode parseTintMode(int value, Mode defaultMode) {
    switch (value) {
      case 3: return Mode.SRC_OVER;
      case 5: return Mode.SRC_IN;
      case 9: return Mode.SRC_ATOP;
      case 14: return Mode.MULTIPLY;
      case 15: return Mode.SCREEN;

      case 16:
        if(HONEYCOMB_PLUS) {
          return Mode.ADD;
        }

      default:
        return defaultMode;
    }
  }

  /**
   * Ensures the tint filter is consistent with the current tint color and
   * mode.
   */
  static PorterDuffColorFilter updateTintFilter(Drawable drawable, PorterDuffColorFilter tintFilter, ColorStateList tint,
                                                PorterDuff.Mode tintMode) {
    if (tint == null || tintMode == null) {
      return null;
    }

    final int color = tint.getColorForState(drawable.getState(), Color.TRANSPARENT);

    if (tintFilter == null || !LOLLIPOP_PLUS) { // TODO worth caching them?
      return new PorterDuffColorFilter(color, tintMode);
    }

    tryInvoke(tintFilter, "setColor", INT_ARG, color);
    tryInvoke(tintFilter, "setMode", MODE_ARG, tintMode);
    return tintFilter;
  }

  @TargetApi(LOLLIPOP)
  static int getChangingConfigurations(TypedArray a) {
    if (LOLLIPOP_PLUS) {
      return a.getChangingConfigurations();
    }
    return 0;
  }

  static float[] copyOf(float[] original, int newLength) {
    if (newLength < 0) {
      throw new NegativeArraySizeException(Integer.toString(newLength));
    }
    return copyOfRange(original, 0, newLength);
  }

  static float[] copyOfRange(float[] original, int start, int end) {
    if (start > end) {
      throw new IllegalArgumentException();
    }
    int originalLength = original.length;
    if (start < 0 || start > originalLength) {
      throw new ArrayIndexOutOfBoundsException();
    }
    int resultLength = end - start;
    int copyLength = Math.min(resultLength, originalLength - start);
    float[] result = new float[resultLength];
    System.arraycopy(original, start, result, 0, copyLength);
    return result;
  }
}
