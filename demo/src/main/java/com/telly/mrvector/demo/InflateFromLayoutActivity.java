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
package com.telly.mrvector.demo;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

import com.telly.mrvector.MrVector;

public class InflateFromLayoutActivity extends BaseActivity {
  {{ // Must be static, Ideally you would register all the vector drawables at once
      MrVector.register(
          R.drawable.vector_android,
          R.drawable.sample_vector_drawable
      );
  }}
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_inflate_from_layout);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.layout_inflate_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    // This allows inflation magic
    // Ideally you would do this in your BaseActivity or Application instead of per activity
    super.attachBaseContext(MrVector.wrap(newBase));
  }

}
