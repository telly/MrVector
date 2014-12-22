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

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.MrResources;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.util.IntSet;
import android.util.Log;

import static com.telly.mrvector.Utils.LOLLIPOP_PLUS;

/**
 * Facade for creating Drawables in a compatible way
 */
public class MrVector {
  /**
   * Inflates a <vector> drawable, using framework implementation when available
   * @param resources
   * Resources to use for inflation
   * @param resId
   * <vector> drawable resource
   * @return
   * <p>Framework {@link android.graphics.drawable.VectorDrawable} if running lollipop or later.</p>
   * <p>{@link com.telly.mrvector.VectorDrawable} otherwise.</p>
   *
   * @see #inflateCompatOnly(android.content.res.Resources, int)
   */
  public static Drawable inflate(Resources resources, @DrawableRes int resId) {
    if (LOLLIPOP_PLUS) {
      return resources.getDrawable(resId);
    } else {
      return inflateCompatOnly(resources, resId);
    }
  }

  /**
   * Inflates a <vector> drawable, using {@link com.telly.mrvector.VectorDrawable} implementation always.
   * @param resources
   * Resources to use for inflation
   * @param resId
   * <vector> drawable resource
   * @return
   * <p>Inflated instance of {@link com.telly.mrvector.VectorDrawable}.</p>
   *
   * @see #inflate(android.content.res.Resources, int)
   */
  public static Drawable inflateCompatOnly(Resources resources, @DrawableRes int resId) {
    return VectorDrawable.create(resources, resId);
  }

  private static IntSet sVectorResources;
  public static void register(@DrawableRes int... resources) {
    if (resources == null || resources.length < 1) {
      return;
    }
    if (sVectorResources == null) {
      sVectorResources = new IntSet(resources.length);
    }
    sVectorResources.addAll(resources);
  }

  public static Context wrap(Context context) {
    return new MrResourcesContext(context);
  }

  static class MrResourcesContext extends ContextWrapper {
    private MrResources mMrResources;
    public MrResourcesContext(Context base) {
      super(base);
    }

    @Override
    public Resources getResources() {
      final Resources superResources = super.getResources();
      if (mMrResources == null || mMrResources.oldFor(superResources)) {
        mMrResources = new MrResources(superResources);
      }
      return mMrResources;
    }
  }

  /**
   * @hide
   */
  public static Drawable lookup(Resources res, int id, int density, boolean ignoreDensity) {
    Log.d(VectorDrawable.LOGTAG, "Looking up res " + id);
    if (sVectorResources == null || !sVectorResources.contains(id)) {
      Log.d(VectorDrawable.LOGTAG, "Could not find res " + id);
      return null;
    }
    // TODO support density
    Log.d(VectorDrawable.LOGTAG, "Inflating res " + id);
    return inflateCompatOnly(res, id);
  }

}
