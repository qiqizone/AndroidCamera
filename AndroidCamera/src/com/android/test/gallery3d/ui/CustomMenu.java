/*
 * Copyright (C) 2010 The Android Open Source Project
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

package com.android.test.gallery3d.ui;

import java.util.ArrayList;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class CustomMenu implements OnMenuItemClickListener {
    @SuppressWarnings("unused")
    private static final String TAG = "FilterMenu";

    public static class DropDownMenu {
        private Button mButton;
        private PopupMenu mPopupMenu;
        private Menu mMenu;

        public DropDownMenu(Context context, Button button, int menuId,
                OnMenuItemClickListener listener) {
            mButton = button;
            mPopupMenu = new PopupMenu(context, mButton);
            mMenu = mPopupMenu.getMenu();
            mPopupMenu.getMenuInflater().inflate(menuId, mMenu);
            mPopupMenu.setOnMenuItemClickListener(listener);
            mButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    mPopupMenu.show();
                }
            });
        }

        public MenuItem findItem(int id) {
            return mMenu.findItem(id);
        }

        public void setTitle(CharSequence title) {
            mButton.setText(title);
        }
    }



    private Context mContext;
    private ArrayList<DropDownMenu> mMenus;
    private OnMenuItemClickListener mListener;

    public CustomMenu(Context context) {
        mContext = context;
        mMenus = new ArrayList<DropDownMenu>();
    }

    public DropDownMenu addDropDownMenu(Button button, int menuId) {
        DropDownMenu menu = new DropDownMenu(mContext, button, menuId, this);
        mMenus.add(menu);
        return menu;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        mListener = listener;
    }

    public boolean onMenuItemClick(MenuItem item) {
        if (mListener != null) {
            return mListener.onMenuItemClick(item);
        }
        return false;
    }
}
