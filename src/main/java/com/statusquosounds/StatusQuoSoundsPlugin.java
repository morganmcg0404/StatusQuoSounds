package com.statusquosounds;

import com.google.inject.Provides;
import com.statusquosounds.sound.SoundFileManager;
import com.statusquosounds.triggers.EventTriggers;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "StatusQuoSounds",
	description = "Plays custom sounds for various in-game events. Sounds are stored in .runelite/statusquosounds-sounds/ and can be customized."
)
public class StatusQuoSoundsPlugin extends Plugin
{
	@Inject
	private StatusQuoSoundsConfig config;

	@Inject
	private EventBus eventBus;

	@Inject
	private EventTriggers eventTriggers;

	@Override
	protected void startUp() throws Exception
	{
		log.info("StatusQuoSounds started!");
		
		// Prepare sound files directory and extract defaults
		SoundFileManager.prepareSoundFiles();
		
		// Register event triggers
		eventBus.register(eventTriggers);
		
		log.info("StatusQuoSounds sound files directory: {}", SoundFileManager.getSoundsDirectory());
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("StatusQuoSounds stopped!");
		
		// Unregister event triggers
		eventBus.unregister(eventTriggers);
	}

	@Provides
	StatusQuoSoundsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(StatusQuoSoundsConfig.class);
	}
}
