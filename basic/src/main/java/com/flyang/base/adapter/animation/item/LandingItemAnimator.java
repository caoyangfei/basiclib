/*
 * Copyright (C) 2015 Wasabeef
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flyang.base.adapter.animation.item;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.animation.Interpolator;


/**
 * @author caoyangfei
 * @ClassName LandingItemAnimator
 * @date 2019/9/21
 * ------------- Description -------------
 * 降落
 */
public class LandingItemAnimator extends FlexibleItemAnimator {

    public LandingItemAnimator() {
    }

    public LandingItemAnimator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }

    @Override
    protected void animateRemoveImpl(final ViewHolder holder, final int index) {
        ViewCompat.animate(holder.itemView)
                .alpha(0)
                .scaleX(1.5f)
                .scaleY(1.5f)
                .setDuration(getRemoveDuration())
                .setInterpolator(mInterpolator)
                .setListener(new DefaultRemoveVpaListener(holder))
                .start();
    }

    @Override
    protected boolean preAnimateAddImpl(final ViewHolder holder) {
        holder.itemView.setAlpha(0);
        holder.itemView.setScaleX(1.5f);
        holder.itemView.setScaleY(1.5f);
        return true;
    }

    @Override
    protected void animateAddImpl(final ViewHolder holder, final int index) {
        ViewCompat.animate(holder.itemView)
                .alpha(1)
                .scaleX(1)
                .scaleY(1)
                .setDuration(getAddDuration())
                .setInterpolator(mInterpolator)
                .setListener(new DefaultAddVpaListener(holder))
                .start();
    }

}