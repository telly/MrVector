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

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.CATEGORY_BROWSABLE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.telly.mrvector.demo.BuildConfig.ABOUT_URL;

abstract class BaseActivity extends ActionBarActivity {
  private static final Uri ABOUT_URI = Uri.parse(ABOUT_URL);

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    final int id = item.getItemId();
    switch (id) {
      case R.id.action_about:
        openAboutPage();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  <T extends View> T viewById(int id) {
    return (T) super.findViewById(id);
  }

  void openAboutPage() {
    Intent intent = new Intent(ACTION_VIEW);
    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
    intent.setData(ABOUT_URI);
    intent.addCategory(CATEGORY_BROWSABLE);
    startActivity(intent);
  }

}
