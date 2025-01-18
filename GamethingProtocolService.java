package com.gamething.broadcastservice.service;

import com.gamething.broadcastservice.dto.FrameDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GamethingProtocolService {

    private final GamethingProtocolEncoder gamethingProtocolEncoder;

    /**
     * Called every frame to produce the "delta" data that we will send.
     */
    public FrameDataDto buildFrameDelta(long frameNumber, byte[] oldFrame, byte[] newFrame) {
        var changes = gamethingProtocolEncoder.encodeFrameChanges(oldFrame, newFrame);
        return FrameDataDto.builder()
                .frameNumber(frameNumber)
                .changedPixels(changes)
                .build();
    }

    /**
     * Example method to decide if a changed pixel is "uncertain" and skip it.
     * Real logic would likely be more advanced (e.g. thresholding).
     */
    public boolean isChangeUncertain(int oldColor, int newColor) {
        // Example: if difference is below some threshold, ignore.
        return Math.abs(oldColor - newColor) < 5; // dummy threshold
    }
}