/*
 * Copyright (C) 2011 The Android Open Source Project
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

// FadeInTexture is a texture which begins with a color, then gradually animates
// into a given texture.
public class FadeInTexture extends FadeTexture implements Texture {
    @SuppressWarnings("unused")
    private static final String TAG = "FadeInTexture";

    private final int mColor;

    public FadeInTexture(int color, BasicTexture texture) {
        super(texture);
        mColor = color;
    }

    @Override
    public void draw(GLCanvas canvas, int x, int y, int w, int h) {
        if (isAnimating()) {
            canvas.drawMixed(mTexture, mColor, getRatio(), x, y, w, h);
        } else {
            mTexture.draw(canvas, x, y, w, h);
        }
    }
}
