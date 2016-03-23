package me.oriley.crate.mediainfo;

import me.oriley.crate.mediainfo.MediaInfo.InfoKind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;

public class CrateMediaInfo {

    private static final Logger log = LoggerFactory.getLogger(CrateMediaInfo.class.getSimpleName());

    private static final String WIDTH = "Width";
    private static final String HEIGHT = "Height";

    private static final int INVALID = -1;

    @Nullable
    private MediaInfo mMediaInfo;


    public CrateMediaInfo() {
        try {
            mMediaInfo = new MediaInfo();
        } catch (Exception e) {
            logError("Failed to instantiate media info", e, false);
        }
    }


    public boolean isAvailable() {
        return mMediaInfo != null;
    }

    public int[] getDimensions(@Nonnull File file) {
        int[] dimens = new int[2];
        dimens[0] = getIntValue(file, WIDTH);
        dimens[1] = getIntValue(file, HEIGHT);
        return dimens;
    }

    private int getIntValue(@Nonnull File file, @Nonnull String field) {
        if (mMediaInfo == null) {
            return INVALID;
        }

        try {
            mMediaInfo.open(file);
            String value = mMediaInfo.get(MediaInfo.StreamKind.Video, 0, field, InfoKind.Text, InfoKind.Name);
            return Integer.parseInt(value);
        } catch (Exception e) {
            logError("Error parsing " + field + " for file " + file.getAbsolutePath(), e, false);
            return INVALID;
        } finally {
            mMediaInfo.close();
        }
    }

    private void logError(@Nonnull String message, @Nonnull Throwable error, boolean throwError) {
        log.error("Crate: " + message, error);
        if (throwError) {
            throw new IllegalStateException("Crate: Fatal Exception");
        }
    }
}
