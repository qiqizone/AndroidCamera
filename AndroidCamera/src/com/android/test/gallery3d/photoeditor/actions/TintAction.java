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

package com.android.test.gallery3d.photoeditor.actions;

import android.content.Context;
import android.util.AttributeSet;

import com.android.test.gallery3d.photoeditor.filters.TintFilter;

/**
 * An action handling tint effect.
 */
public class TintAction extends EffectAction {

    private static final int DEFAULT_COLOR_INDEX = 13;

    public TintAction(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void prepare() {
        final TintFilter filter = new TintFilter();

        ColorSeekBar colorPicker = toolKit.addColorPicker();
        colorPicker.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {

            @Override
            public void onColorChanged(int color, boolean fromUser) {
                if (fromUser) {
                    filter.setTint(color);
                    notifyChanged(filter);
                }
            }
        });
        colorPicker.setColorIndex(DEFAULT_COLOR_INDEX);

        filter.setTint(colorPicker.getColor());
        notifyChanged(filter);
    }
}
