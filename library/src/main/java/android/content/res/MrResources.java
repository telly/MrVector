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
package android.content.res;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.telly.mrvector.MrVector;

/**
 * @hide
 */
public class MrResources extends Resources {
  private final Resources mResources;

  public MrResources(Resources resources) {
    super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
    mResources = resources;
  }

  public boolean oldFor(Resources superResources) {
    return superResources != mResources;
  }

  @Override
  public Drawable getDrawable(int id) throws NotFoundException {
    Drawable mr = lookup(id);
    if (mr != null) {
      return mr;
    }
    return super.getDrawable(id);
  }

  @Override
  @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
  public Drawable getDrawableForDensity(int id, int density) throws NotFoundException {
    Drawable mr = lookup(id, density);
    if (mr != null) {
      return mr;
    }
    return super.getDrawableForDensity(id, density);
  }

  @Override
  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public Drawable getDrawable(int id, Theme theme) throws NotFoundException {
    return super.getDrawable(id, theme);
  }


  @Override
  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public Drawable getDrawableForDensity(int id, int density, Theme theme) {
    return super.getDrawableForDensity(id, density, theme);
  }

  /* TODO some sorcery to make the calls from TypedArray work

  Drawable loadDrawable(TypedValue value, int id) throws Resources.NotFoundException {
    Log.d("vector", "loadDrawable@2 " + id);
    return super.loadDrawable(value, id);
  }

  Drawable loadDrawable(TypedValue value, int id, Theme theme) throws Resources.NotFoundException {
    Log.d("vector", "loadDrawable@3 " + id);
    return super.loadDrawable(value, id, theme);
  }
  */


  private Drawable lookup(int id) {
    return MrVector.lookup(mResources, id, 0, false);
  }

  private Drawable lookup(int id, int density) {
    return MrVector.lookup(mResources, id, density, true);
  }

}