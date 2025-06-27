package com.statusquosounds.sound;

import java.util.Random;

public enum SoundType {
    // Achievement sounds
    COLLECTION_LOG_1("collection_log_1.wav"),
    COLLECTION_LOG_2("collection_log_2.wav"),
    COLLECTION_LOG_3("collection_log_3.wav"),
    
    PET_DROP_1("pet_drop_1.wav"),
    PET_DROP_2("pet_drop_2.wav"),
    PET_DROP_3("pet_drop_3.wav"),
    
    RARE_DROP_1("rare_drop_1.wav"),
    RARE_DROP_2("rare_drop_2.wav"),
    RARE_DROP_3("rare_drop_3.wav"),
    
    SUPERIOR_SPAWN_1("superior_spawn_1.wav"),
    SUPERIOR_SPAWN_2("superior_spawn_2.wav"),
    SUPERIOR_SPAWN_3("superior_spawn_3.wav"),
    
    ACHIEVEMENT_DIARY_1("achievement_diary_1.wav"),
    ACHIEVEMENT_DIARY_2("achievement_diary_2.wav"),
    ACHIEVEMENT_DIARY_3("achievement_diary_3.wav"),
    
    COMBAT_DIARY_1("combat_diary_1.wav"),
    COMBAT_DIARY_2("combat_diary_2.wav"),
    COMBAT_DIARY_3("combat_diary_3.wav"),
    
    // Level up sounds
    LEVEL_UP_1("level_up_1.wav"),
    LEVEL_UP_2("level_up_2.wav"),
    LEVEL_UP_3("level_up_3.wav"),
    
    // Quest completion sounds
    QUEST_COMPLETE_1("quest_complete_1.wav"),
    QUEST_COMPLETE_2("quest_complete_2.wav"),
    QUEST_COMPLETE_3("quest_complete_3.wav");

    private final String fileName;
    private static final Random random = new Random();

    SoundType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    // Random sound selection methods for each event type
    public static SoundType randomCollectionLog() {
        SoundType[] sounds = {COLLECTION_LOG_1, COLLECTION_LOG_2, COLLECTION_LOG_3};
        return sounds[random.nextInt(sounds.length)];
    }

    public static SoundType randomPetDrop() {
        SoundType[] sounds = {PET_DROP_1, PET_DROP_2, PET_DROP_3};
        return sounds[random.nextInt(sounds.length)];
    }

    public static SoundType randomRareDrop() {
        SoundType[] sounds = {RARE_DROP_1, RARE_DROP_2, RARE_DROP_3};
        return sounds[random.nextInt(sounds.length)];
    }

    public static SoundType randomSuperiorSpawn() {
        SoundType[] sounds = {SUPERIOR_SPAWN_1, SUPERIOR_SPAWN_2, SUPERIOR_SPAWN_3};
        return sounds[random.nextInt(sounds.length)];
    }

    public static SoundType randomAchievementDiary() {
        SoundType[] sounds = {ACHIEVEMENT_DIARY_1, ACHIEVEMENT_DIARY_2, ACHIEVEMENT_DIARY_3};
        return sounds[random.nextInt(sounds.length)];
    }

    public static SoundType randomCombatDiary() {
        SoundType[] sounds = {COMBAT_DIARY_1, COMBAT_DIARY_2, COMBAT_DIARY_3};
        return sounds[random.nextInt(sounds.length)];
    }

    public static SoundType randomLevelUp() {
        SoundType[] sounds = {LEVEL_UP_1, LEVEL_UP_2, LEVEL_UP_3};
        return sounds[random.nextInt(sounds.length)];
    }

    public static SoundType randomQuestComplete() {
        SoundType[] sounds = {QUEST_COMPLETE_1, QUEST_COMPLETE_2, QUEST_COMPLETE_3};
        return sounds[random.nextInt(sounds.length)];
    }
}
