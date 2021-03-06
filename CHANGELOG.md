Change Log
==========

## Version 0.6.0 WIP

 * Update MediaInfo libs to 0.7.93
 * Update JNA libs to 4.4.0
 * Add generated code to variant specific source set (fixes compilation when switching target)

## Version 0.5.0

_2016-05-03_

 * Removed asynchronous loaders and caching (it's outside of the scope of the library. Check out my other library
   [Twiddle](https://github.com/oriley-me/twiddle) for that.
 * Update dependencies
 * Drop minimum SDK version to 15 (ICS)

## Version 0.4.1

_2016-04-05_

 *  Feature: Add automatic Gzip wrapping and `isGzipped()` method for all assets
 *  Improvement: Add Loader classes for better adapter usage
 *  Feature: Add `asUri()` method to assets to get path as a Uri String
 *  Improvement: Update cache handling and add max size settings to the builder
 *  Feature: Added `VideoAsset` type for supported media formats
 *  Feature: Add `crate-mediainfo` to read video attributes into asset for quick access
 *  Fix JitPack build

## Version 0.3

_2016-03-17_

### Major refactor - API breaking changes

 *  Refactor to include runtime component to reduce amount of JavaPoet usage
 *  Remove static mode (added note in README about how to setup for static access)
 *  Remove convenience methods for getters. All assets and folder classes are now static to avoid memory leaks

### Other changes

 *  Improvement: Add `clearTypefaceCache()`/`clear()` methods for reclaiming cached memory
 *  Feature: Add ImageAsset class for JPG/JPEG/PNG/GIF assets, with width and height fields
 *  Feature: Implement convenience getter for ImageAsset bitmaps, and bitmap caching
 *  Feature: Add SvgAsset class for SVG/SVGZ assets
 *  Feature: Implement `getSvgDrawable()` and `getSvgBitmap()` helper methods in `Crate` (requires `com.caverock:androidsvg:1.2.1` dependency)
 *  Feature: Implement `PictureDrawable` caching to dramatically speedup SVG retrieval
 *  Improvement: Use content type rather than file extension for categorising assets
 *  Improvement: Add builder pattern and support for clearing specific asset caches


## Version 0.2

_2016-03-14_

 *  Improvement: Add debug logging for testing
 *  Improvement: Use TreeMap to keep LIST fields sorted by field name
 *  Feature: Add FULL_LIST field containing all assets for project
 *  Improvement: Make list fields unmodifiable
 *  Feature: Add Gradle DSL extension for toggling debug logging and setting output package/class name
 *  Feature: Add option to disable static mode, to reduce ongoing memory requirements and simplify the API
 *  Fix: Don't close AssetManager as it can cause issues
 *  Feature: Add typeface retrieval method and caching for font assets


## Version 0.1.1

_2016-03-13_

 *  Feature: Add special handling for font assets
 *  Fix: Validate Crate JAR hash and force mergeAssets task to run if changed
 *  Fix: Better field name sanitising (shouldn't be able to choke on invalid filenames anymore)
 *  Improvement: Migrate java class generation to JavaPoet


## Version 0.1

_2016-03-12_

 *  Initial release.
