package com.statusquosounds.sound;

import com.statusquosounds.StatusQuoSoundsConfig;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.audio.AudioPlayer;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.concurrent.Executor;

@Singleton
@Slf4j
public class SoundEngine {

    @Inject
    private StatusQuoSoundsConfig config;

    @Inject
    private AudioPlayer audioPlayer;

    public void playSound(SoundType soundType, Executor executor) {
        executor.execute(() -> playSound(soundType));
    }

    private void playSound(SoundType soundType) {
        // Check if volume is 0
        if (config.volume() == 0) {
            log.debug("Volume is 0, not playing sound: {}", soundType);
            return;
        }

        // Calculate gain based on volume (0-200 range like C Engineer)
        float volumePercent = config.volume() / 100f;
        float gain = 20f * (float) Math.log10(Math.max(0.01f, volumePercent)); // Avoid log(0)

        try {
            // Check if sound file exists
            if (!SoundFileManager.soundFileExists(soundType)) {
                log.warn("Sound file not found: {}, playing fallback beep", soundType.getFileName());
                playFallbackBeep();
                return;
            }

            audioPlayer.play(SoundFileManager.getSoundFile(soundType), gain);
            log.debug("Played sound: {} with gain: {}", soundType.getFileName(), gain);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            log.warn("Failed to play sound: {}", soundType.getFileName(), e);
            playFallbackBeep();
        }
    }

    private void playFallbackBeep() {
        try {
            java.awt.Toolkit.getDefaultToolkit().beep();
        } catch (Exception e) {
            log.error("Failed to play fallback beep", e);
        }
    }
}
