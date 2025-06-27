package com.statusquosounds.sound;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.RuneLite;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class SoundFileManager {
    private SoundFileManager() {}

    private static final Path SOUNDS_DIR = Path.of(RuneLite.RUNELITE_DIR.getPath(), "statusquosounds-sounds");
    private static final String WARNING_FILENAME = "_PLACE_YOUR_CUSTOM_SOUNDS_HERE";
    private static final Path WARNING_FILE = SOUNDS_DIR.resolve(WARNING_FILENAME);

    public static File getSoundFile(SoundType soundType) {
        return SOUNDS_DIR.resolve(soundType.getFileName()).toFile();
    }

    public static void prepareSoundFiles() {
        ensureSoundsDirectoryExists();
        extractDefaultSounds();
    }

    private static void ensureSoundsDirectoryExists() {
        try {
            if (!Files.exists(SOUNDS_DIR)) {
                Files.createDirectories(SOUNDS_DIR);
                log.info("Created StatusQuoSounds directory: {}", SOUNDS_DIR);
            }
            
            // Create a warning/info file
            if (!Files.exists(WARNING_FILE)) {
                String infoContent = "StatusQuoSounds Plugin Sound Files\n" +
                    "==================================\n\n" +
                    "This directory contains sound files for the StatusQuoSounds RuneLite plugin.\n\n" +
                    "To customize sounds:\n" +
                    "1. Replace any .wav file with your own custom sound (same filename)\n" +
                    "2. Make sure your custom files are in .wav format\n" +
                    "3. Files not in the expected list will be preserved\n\n" +
                    "Event types and their sound files:\n" +
                    "- Collection Log: collection_log_1.wav, collection_log_2.wav, collection_log_3.wav\n" +
                    "- Pet Drops: pet_drop_1.wav, pet_drop_2.wav, pet_drop_3.wav\n" +
                    "- Rare Drops: rare_drop_1.wav, rare_drop_2.wav, rare_drop_3.wav\n" +
                    "- Superior Spawns: superior_spawn_1.wav, superior_spawn_2.wav, superior_spawn_3.wav\n" +
                    "- Achievement Diary: achievement_diary_1.wav, achievement_diary_2.wav, achievement_diary_3.wav\n" +
                    "- Combat Diary: combat_diary_1.wav, combat_diary_2.wav, combat_diary_3.wav\n" +
                    "- Level Up: level_up_1.wav, level_up_2.wav, level_up_3.wav\n" +
                    "- Quest Complete: quest_complete_1.wav, quest_complete_2.wav, quest_complete_3.wav\n\n" +
                    "If a custom sound file is missing or corrupted, the plugin will fall back to a system beep.";
                
                Files.write(WARNING_FILE, infoContent.getBytes());
            }
        } catch (IOException e) {
            log.error("Could not create sounds directory or info file", e);
        }
    }

    private static void extractDefaultSounds() {
        // Extract default sounds from resources if they don't exist
        for (SoundType soundType : SoundType.values()) {
            Path soundFile = SOUNDS_DIR.resolve(soundType.getFileName());
            
            if (!Files.exists(soundFile)) {
                extractDefaultSound(soundType);
            }
        }
    }

    private static void extractDefaultSound(SoundType soundType) {
        String resourcePath = "/sounds/" + soundType.getFileName();
        
        try (InputStream soundStream = SoundFileManager.class.getResourceAsStream(resourcePath)) {
            if (soundStream != null) {
                Path targetFile = SOUNDS_DIR.resolve(soundType.getFileName());
                Files.copy(soundStream, targetFile, StandardCopyOption.REPLACE_EXISTING);
                log.debug("Extracted default sound: {}", soundType.getFileName());
            } else {
                log.warn("Default sound not found in resources: {}", resourcePath);
                // Create a placeholder file to indicate the sound is expected
                createPlaceholderFile(soundType);
            }
        } catch (IOException e) {
            log.error("Failed to extract default sound: {}", soundType.getFileName(), e);
            createPlaceholderFile(soundType);
        }
    }

    private static void createPlaceholderFile(SoundType soundType) {
        try {
            Path placeholderFile = SOUNDS_DIR.resolve(soundType.getFileName() + ".missing");
            if (!Files.exists(placeholderFile)) {
                String content = "This is a placeholder file.\n" +
                    "The expected sound file '" + soundType.getFileName() + "' was not found in the plugin resources.\n" +
                    "You can place your own " + soundType.getFileName() + " file in this directory to add custom sounds.";
                Files.write(placeholderFile, content.getBytes());
            }
        } catch (IOException e) {
            log.error("Failed to create placeholder file for: {}", soundType.getFileName(), e);
        }
    }

    public static boolean soundFileExists(SoundType soundType) {
        return Files.exists(SOUNDS_DIR.resolve(soundType.getFileName()));
    }

    public static Set<String> getExistingSoundFiles() {
        try (Stream<Path> paths = Files.list(SOUNDS_DIR)) {
            return paths
                .filter(path -> !Files.isDirectory(path))
                .map(Path::getFileName)
                .map(Path::toString)
                .filter(filename -> filename.endsWith(".wav"))
                .collect(Collectors.toSet());
        } catch (IOException e) {
            log.warn("Could not list sound files in {}, assuming directory is empty", SOUNDS_DIR);
            return Set.of();
        }
    }

    public static Path getSoundsDirectory() {
        return SOUNDS_DIR;
    }
}
