package com.statusquosounds;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;

@ConfigGroup("statusquosounds")
public interface StatusQuoSoundsConfig extends Config
{
	@ConfigSection(
		name = "Sound Settings",
		description = "Configure sound settings",
		position = 0
	)
	String soundSettings = "soundSettings";

	@ConfigSection(
		name = "Event Toggles",
		description = "Enable or disable sounds for specific events",
		position = 1
	)
	String eventToggles = "eventToggles";

	@ConfigSection(
		name = "Drop Settings",
		description = "Configure rare drop settings",
		position = 2
	)
	String dropSettings = "dropSettings";

	@ConfigSection(
		name = "Sound Testing",
		description = "Test each sound type",
		position = 3
	)
	String soundTesting = "soundTesting";

	@ConfigItem(
		keyName = "volume",
		name = "Volume",
		description = "Set the volume for sound effects (0-100)",
		section = soundSettings,
		position = 1
	)
	@Range(min = 0, max = 100)
	default int volume()
	{
		return 50;
	}

	@ConfigItem(
		keyName = "collectionLogSounds",
		name = "Collection Log",
		description = "Play sound when getting a new collection log entry",
		section = eventToggles,
		position = 1
	)
	default boolean collectionLogSounds()
	{
		return true;
	}

	@ConfigItem(
		keyName = "petDropSounds",
		name = "Pet Drops",
		description = "Play sound when receiving a pet drop",
		section = eventToggles,
		position = 2
	)
	default boolean petDropSounds()
	{
		return true;
	}

	@ConfigItem(
		keyName = "rareDropSounds",
		name = "Rare Drops",
		description = "Play sound when receiving a rare drop",
		section = eventToggles,
		position = 3
	)
	default boolean rareDropSounds()
	{
		return true;
	}

	@ConfigItem(
		keyName = "superiorSpawnSounds",
		name = "Superior Spawns",
		description = "Play sound when a superior slayer monster spawns",
		section = eventToggles,
		position = 4
	)
	default boolean superiorSpawnSounds()
	{
		return true;
	}

	@ConfigItem(
		keyName = "achievementDiarySounds",
		name = "Achievement Diary",
		description = "Play sound when completing an achievement diary",
		section = eventToggles,
		position = 5
	)
	default boolean achievementDiarySounds()
	{
		return true;
	}

	@ConfigItem(
		keyName = "combatDiarySounds",
		name = "Combat Diary",
		description = "Play sound when completing a combat diary",
		section = eventToggles,
		position = 6
	)
	default boolean combatDiarySounds()
	{
		return true;
	}

	@ConfigItem(
		keyName = "rareDropValue",
		name = "Rare Drop Value (GP)",
		description = "Minimum value for a drop to be considered rare",
		section = dropSettings,
		position = 1
	)
	default int rareDropValue()
	{
		return 1000000; // 1M GP default
	}

	// Test buttons
	@ConfigItem(
		keyName = "testCollectionLog",
		name = "Test Collection Log Sound",
		description = "Click to test collection log sound",
		section = soundTesting,
		position = 1
	)
	default void testCollectionLog()
	{
	}

	@ConfigItem(
		keyName = "testPetDrop",
		name = "Test Pet Drop Sound",
		description = "Click to test pet drop sound",
		section = soundTesting,
		position = 2
	)
	default void testPetDrop()
	{
	}

	@ConfigItem(
		keyName = "testRareDrop",
		name = "Test Rare Drop Sound",
		description = "Click to test rare drop sound",
		section = soundTesting,
		position = 3
	)
	default void testRareDrop()
	{
	}

	@ConfigItem(
		keyName = "testSuperiorSpawn",
		name = "Test Superior Spawn Sound",
		description = "Click to test superior spawn sound",
		section = soundTesting,
		position = 4
	)
	default void testSuperiorSpawn()
	{
	}

	@ConfigItem(
		keyName = "testAchievementDiary",
		name = "Test Achievement Diary Sound",
		description = "Click to test achievement diary sound",
		section = soundTesting,
		position = 5
	)
	default void testAchievementDiary()
	{
	}

	@ConfigItem(
		keyName = "testCombatDiary",
		name = "Test Combat Diary Sound",
		description = "Click to test combat diary sound",
		section = soundTesting,
		position = 6
	)
	default void testCombatDiary()
	{
	}
}
