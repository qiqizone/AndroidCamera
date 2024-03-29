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
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.test.gallery3d.R;
import com.android.test.gallery3d.photoeditor.FilterStack;
import com.android.test.gallery3d.photoeditor.OnDoneCallback;
import com.android.test.gallery3d.photoeditor.filters.Filter;

/**
 * An action binding UI controls and effect operation for editing photo.
 */
public abstract class EffectAction extends LinearLayout {

    /**
     * Listener of effect action.
     */
    public interface ActionListener {

        /**
         * Invoked when the action is okayed (effect is applied and completed).
         */
        void onOk();
    }

    protected EffectToolKit toolKit;
    private Toast tooltip;
    private FilterStack filterStack;
    private boolean pushedFilter;
    private boolean disableFilterOutput;
    private FilterChangedCallback lastFilterChangedCallback;
    private ActionListener listener;

    public EffectAction(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void begin(View root, FilterStack filterStack, ActionListener listener) {
        // This view is already detached from UI view hierarchy by reaching here; findViewById()
        // could only access its own child views from here.
        toolKit = new EffectToolKit(root, ((TextView) findViewById(R.id.effect_label)).getText());
        this.filterStack = filterStack;
        this.listener = listener;

        // Shows the tooltip if it's available.
        if (getTag() != null) {
            tooltip = Toast.makeText(getContext(), (String) getTag(), Toast.LENGTH_SHORT);
            tooltip.setGravity(Gravity.CENTER, 0, 0);
            tooltip.show();
        }
        prepare();
    }

    /**
     * Subclasses should create a specific filter and bind the filter to necessary UI controls here
     * when the action is about to begin.
     */
    protected abstract void prepare();

    /**
     * Ends the effect and then executes the runnable after the effect is finished.
     */
    public void end(final Runnable runnableOnODone) {
        // Cancel the tooltip if it's still showing.
        if ((tooltip != null) && (tooltip.getView().getParent() != null)) {
            tooltip.cancel();
            tooltip = null;
        }
        // End tool editing by canceling unfinished touch events.
        toolKit.cancel();
        // Output the pushed filter if it wasn't outputted.
        if (pushedFilter && disableFilterOutput) {
            outputFilter();
        }

        // Wait till last output callback is done before finishing.
        if ((lastFilterChangedCallback == null) || lastFilterChangedCallback.done) {
            finish(runnableOnODone);
        } else {
            lastFilterChangedCallback.runnableOnReady = new Runnable() {

                @Override
                public void run() {
                    finish(runnableOnODone);
                }
            };
        }
    }

    private void finish(Runnable runnableOnDone) {
        toolKit.close();
        pushedFilter = false;
        disableFilterOutput = false;
        lastFilterChangedCallback = null;

        runnableOnDone.run();
    }

    protected void disableFilterOutput() {
        // Filter output won't be outputted until this effect has done editing its filter.
        disableFilterOutput = true;
    }

    protected void outputFilter() {
        // Notify the stack to execute the changed top filter and output the results.
        lastFilterChangedCallback = new FilterChangedCallback();
        filterStack.topFilterChanged(lastFilterChangedCallback);
    }

    protected void notifyChanged(Filter filter) {
        if (!pushedFilter) {
            filterStack.pushFilter(filter);
            pushedFilter = true;
        }
        if (pushedFilter && !disableFilterOutput) {
            outputFilter();
        }
    }

    protected void notifyOk() {
        listener.onOk();
    }

    /**
     * Checks if the action effect is present in the system.
     *
     * @return boolean true if an action effect is present in the system and can be loaded
     */
    public boolean isPresent() {
        return true;
    }

    /**
     * Done callback for executing top filter changes.
     */
    private class FilterChangedCallback implements OnDoneCallback {

        private boolean done;
        private Runnable runnableOnReady;

        @Override
        public void onDone() {
            done = true;

            if (runnableOnReady != null) {
                runnableOnReady.run();
            }
        }
    }
}
