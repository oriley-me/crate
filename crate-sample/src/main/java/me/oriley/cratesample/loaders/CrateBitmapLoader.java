/*
 * Copyright (C) 2016 Kane O'Riley
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.oriley.cratesample.loaders;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import me.oriley.crate.Crate;
import me.oriley.crate.ImageAsset;
import me.oriley.crate.loader.AssetLoader;
import me.oriley.cratesample.fragments.BitmapListFragment.BitmapHolder;

@SuppressWarnings("unused")
public class CrateBitmapLoader extends AssetLoader<BitmapHolder, ImageAsset, Bitmap> {


    public CrateBitmapLoader(@NonNull Crate crate, long loadDelayMillis) {
        super(crate, loadDelayMillis);
    }


    @Override
    protected void initialiseTarget(@NonNull BitmapHolder holder, @NonNull ImageAsset asset) {
        holder.initialise(asset);
    }

    @NonNull
    @Override
    protected Result<Bitmap> load(@NonNull BitmapHolder holder, @NonNull ImageAsset asset) {
        boolean cached = mCrate.isBitmapCached(asset);
        return new Result<>(mCrate.getBitmap(asset), asset, cached);
    }

    @Override
    protected void apply(@NonNull BitmapHolder holder, @NonNull Result<Bitmap> result) {
        holder.view.setBitmap(result.payload);
        holder.view.setCached(result.cached);
        holder.loaded = true;
        holder.animateIfReady();
    }
}
