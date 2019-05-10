package com.oney.WebRTCModule;

import org.webrtc.SurfaceViewRenderer;
import org.webrtc.VideoFrame;
import org.webrtc.VideoSink;

public class VideoRotationSink implements VideoSink {

    private final SurfaceViewRenderer surfaceViewRenderer;

    public boolean forceRotation;

    VideoRotationSink(SurfaceViewRenderer surfaceViewRenderer) {
        this.surfaceViewRenderer = surfaceViewRenderer;
    }

    @Override
    public void onFrame(VideoFrame videoFrame) {
        if (!forceRotation) {
            this.surfaceViewRenderer.onFrame(videoFrame);
            return;
        }

        // When rotation is 0 we will render the video frame upside-down. To fix this
        // we copy the VideoFrame and set the rotation manually to 180.
        if (videoFrame.getRotation() == 0) {
            VideoFrame rotatedFrame = new VideoFrame(videoFrame.getBuffer(), 180, videoFrame.getTimestampNs());
            this.surfaceViewRenderer.onFrame(rotatedFrame);
            videoFrame.release();
        } else {
            this.surfaceViewRenderer.onFrame(videoFrame);
        }
    }
}
