package com.gamething.broadcastservice.service;

import com.gamething.broadcastservice.dto.FrameDataDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GamethingProtocolEncoder {

    /**
     * Compare the current frame with the previous frame and build
     * a map of only changed pixels. For demonstration, we’re using
     * a very naive approach with raw bytes or ARGB ints.
     *
     * @param oldFrame raw pixel data from the previous frame
     * @param newFrame raw pixel data from the current frame
     * @return a map of pixelIndex -> newColor
     */
    public Map<String, Integer> encodeFrameChanges(byte[] oldFrame, byte[] newFrame) {
        Map<String, Integer> changedPixels = new HashMap<>();

        if (oldFrame == null || newFrame == null || oldFrame.length != newFrame.length) {
            // If mismatch, assume everything changed (dummy logic).
            for (int i = 0; i < newFrame.length; i += 4) {
                // interpret every 4 bytes as one ARGB pixel, for example
                int color = assembleColor(newFrame, i);
                changedPixels.put(String.valueOf(i / 4), color);
            }
            return changedPixels;
        }

        // Compare pixel by pixel
        for (int i = 0; i < newFrame.length; i += 4) {
            int oldColor = assembleColor(oldFrame, i);
            int newColor = assembleColor(newFrame, i);
            if (oldColor != newColor) {
                changedPixels.put(String.valueOf(i / 4), newColor);
            }
        }

        return changedPixels;
    }

    private int assembleColor(byte[] frame, int index) {
        // Example: ARGB
        int alpha = frame[index] & 0xFF;
        int red   = frame[index + 1] & 0xFF;
        int green = frame[index + 2] & 0xFF;
        int blue  = frame[index + 3] & 0xFF;
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }
}