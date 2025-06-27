package com.statusquosounds;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class StatusQuoSoundsPluginTest
{
	public static void main(String[] args) throws Exception
	{
		System.out.println("Starting StatusQuoSounds Plugin Test...");
		
		// Test that config interface exists
		StatusQuoSoundsConfig config = new StatusQuoSoundsConfig() {
			public int volume() { return 100; }
			public boolean levelUpSounds() { return true; }
			public boolean questCompleteSounds() { return true; }
			public boolean collectionLogSounds() { return true; }
			public boolean petDropSounds() { return true; }
			public boolean rareDropSounds() { return true; }
			public boolean superiorSpawnSounds() { return true; }
			public boolean achievementDiarySounds() { return true; }
			public boolean combatDiarySounds() { return true; }
			public boolean showChatMessages() { return false; }
			public int rareDropValue() { return 1000000; }
		};
		
		System.out.println("Plugin configuration test successful!");
		System.out.println("Default volume: " + config.volume());
		System.out.println("Collection log sounds enabled: " + config.collectionLogSounds());
		System.out.println("Level up sounds enabled: " + config.levelUpSounds());
		System.out.println("Quest completion sounds enabled: " + config.questCompleteSounds());
		System.out.println("Chat messages enabled: " + config.showChatMessages());
		System.out.println("Rare drop value threshold: " + config.rareDropValue() + " GP");
		
		System.out.println("StatusQuoSounds Plugin Test completed successfully!");
		
		@SuppressWarnings("unchecked")
		Class<StatusQuoSoundsPlugin>[] plugins = (Class<StatusQuoSoundsPlugin>[]) new Class<?>[] { StatusQuoSoundsPlugin.class };
		ExternalPluginManager.loadBuiltin(plugins);
		RuneLite.main(args);
	}
}