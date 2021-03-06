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

import android.support.annotation.NonNull;
import me.oriley.crate.Asset;
import me.oriley.crate.Crate;
import me.oriley.twiddle.TwiddleCache;
import me.oriley.twiddle.TwiddleLoader;

@SuppressWarnings("unused")
public abstract class AssetLoader<T, A extends Asset, P> extends TwiddleLoader<T, A, P> {

    @NonNull
    protected final Crate mCrate;

    @NonNull
    protected final TwiddleCache<A, P> mCache;


    public AssetLoader(@NonNull Crate crate, long loadDelayMillis, int maxCacheSize, boolean lruCache) {
        super(loadDelayMillis);
        mCrate = crate;
        mCache = new TwiddleCache<>(maxCacheSize, lruCache);
    }

}