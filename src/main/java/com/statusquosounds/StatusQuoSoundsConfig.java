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
		name = "Achievement Sounds",
		description = "Configure sounds for achievements and milestones",
		position = 0
	)
	String achievementSounds = "achievementSounds";

	@ConfigSection(
		name = "Drop Sounds", 
		description = "Configure sounds for item drops",
		position = 1
	)
	String dropSounds = "dropSounds";

	@ConfigSection(
		name = "General Settings",
		description = "General sound and notification settings",
		position = 2
	)
	String generalSettings = "generalSettings";

	// Achievement Sounds
	@ConfigItem(
		keyName = "levelUpSounds",
		name = "Level ups",
		description = "Play sound when gaining a level in any skill",
		section = achievementSounds,
		position = 1
	)
	default boolean levelUpSounds()
	{
		return true;
	}

	@ConfigItem(
		keyName = "questCompleteSounds", 
		name = "Quest completions",
		description = "Play sound when completing a quest",
		section = achievementSounds,
		position = 2
	)
	default boolean questCompleteSounds()
	{
		return true;
	}

	@ConfigItem(
		keyName = "collectionLogSounds",
		name = "Collection log entries",
		description = "Play sound when getting a new collection log entry",
		section = achievementSounds,
		position = 3
	)
	default boolean collectionLogSounds()
	{
		return true;
	}

	@ConfigItem(
		keyName = "achievementDiarySounds",
		name = "Achievement diaries",
		description = "Play sound when completing an achievement diary",
		section = achievementSounds,
		position = 4
	)
	default boolean achievementDiarySounds()
	{
		return true;
	}

	@ConfigItem(
		keyName = "combatDiarySounds",
		name = "Combat achievements",
		description = "Play sound when completing a combat achievement",
		section = achievementSounds,
		position = 5
	)
	default boolean combatDiarySounds()
	{
		return true;
	}

	// Drop Sounds
	@ConfigItem(
		keyName = "petDropSounds",
		name = "Pet drops",
		description = "Play sound when receiving a pet drop",
		section = dropSounds,
		position = 1
	)
	default boolean petDropSounds()
	{
		return true;
	}

	@ConfigItem(
		keyName = "rareDropSounds",
		name = "Rare drops",
		description = "Play sound when receiving valuable or untradeable drops (detected from game messages)",
		section = dropSounds,
		position = 2
	)
	default boolean rareDropSounds()
	{
		return true;
	}

	@ConfigItem(
		keyName = "superiorSpawnSounds",
		name = "Superior slayer spawns",
		description = "Play sound when a superior slayer monster spawns",
		section = dropSounds,
		position = 3
	)
	default boolean superiorSpawnSounds()
	{
		return true;
	}

	@ConfigItem(
		keyName = "rareDropValue",
		name = "Rare drop value threshold (GP)",
		description = "Minimum total value for a drop to be considered rare (currently unused - detection based on game messages)",
		section = dropSounds,
		position = 4
	)
	default int rareDropValue()
	{
		return 1000000; // 1M GP default
	}

	// General Settings
	@ConfigItem(
		keyName = "showChatMessages",
		name = "Show chat notifications",
		description = "Show fake public chat messages when events occur (only you will see them)",
		section = generalSettings,
		position = 1
	)
	default boolean showChatMessages()
	{
		return false;
	}

	@Range(min = 0, max = 200)
	@ConfigItem(
		keyName = "volume",
		name = "Sound volume",
		description = "Adjust how loud the sound effects are played (0-200%)",
		section = generalSettings,
		position = 2
	)
	default int volume()
	{
		return 100;
	}
}
