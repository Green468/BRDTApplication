package com.green.stereovideodemo

interface IStereoVideoView {
    fun setVideoAttributes(videoWidthMm: Float, videoDistanceMm: Float)
    fun setIotdValue(iotdValue: Int)
}