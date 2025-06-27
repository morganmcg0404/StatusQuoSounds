package com.statusquosounds;

import com.google.inject.Provides;
import javax.inject.Inject;
import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "StatusQuoSounds",
	description = "Plays custom sounds for various in-game events"
)
public class StatusQuoSoundsPlugin extends Plugin
{
	@Inject
	private StatusQuoSoundsConfig config;

	@Inject
	private ItemManager itemManager;

	@Inject
	private ClientThread clientThread;

	private final Random random = new Random();

	// Pattern matching for different events
	private static final Pattern COLLECTION_LOG_PATTERN = Pattern.compile("New item added to your collection log:");
	private static final Pattern PET_DROP_PATTERN = Pattern.compile("You have a funny feeling like you're being followed");
	private static final Pattern SUPERIOR_SPAWN_PATTERN = Pattern.compile("A superior foe has appeared");
	private static final Pattern ACHIEVEMENT_DIARY_PATTERN = Pattern.compile("Congratulations, you have completed .* Achievement Diary");
	private static final Pattern COMBAT_DIARY_PATTERN = Pattern.compile("Congratulations, you have completed .* Combat Achievement");

	@Override
	protected void startUp() throws Exception
	{
		log.info("StatusQuoSounds started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("StatusQuoSounds stopped!");
	}

	@Subscribe
	public void onChatMessage(ChatMessage chatMessage)
	{
		String message = chatMessage.getMessage();
		ChatMessageType type = chatMessage.getType();

		// Test commands for manual sound testing
		if (type == ChatMessageType.PUBLICCHAT && message.startsWith("!test"))
		{
			String[] parts = message.split(" ");
			if (parts.length > 1)
			{
				String soundType = parts[1];
				playSound(soundType);
				return;
			}
		}

		// Collection log entries
		if (config.collectionLogSounds() && 
			(type == ChatMessageType.GAMEMESSAGE || type == ChatMessageType.SPAM) &&
			COLLECTION_LOG_PATTERN.matcher(message).find())
		{
			playSound("collection_log");
		}

		// Pet drops
		if (config.petDropSounds() && 
			(type == ChatMessageType.GAMEMESSAGE || type == ChatMessageType.SPAM) &&
			PET_DROP_PATTERN.matcher(message).find())
		{
			playSound("pet_drop");
		}

		// Superior spawns
		if (config.superiorSpawnSounds() && 
			(type == ChatMessageType.GAMEMESSAGE || type == ChatMessageType.SPAM) &&
			SUPERIOR_SPAWN_PATTERN.matcher(message).find())
		{
			playSound("superior_spawn");
		}

		// Achievement diary
		if (config.achievementDiarySounds() && 
			(type == ChatMessageType.GAMEMESSAGE || type == ChatMessageType.SPAM) &&
			ACHIEVEMENT_DIARY_PATTERN.matcher(message).find())
		{
			playSound("achievement_diary");
		}

		// Combat diary
		if (config.combatDiarySounds() && 
			(type == ChatMessageType.GAMEMESSAGE || type == ChatMessageType.SPAM) &&
			COMBAT_DIARY_PATTERN.matcher(message).find())
		{
			playSound("combat_diary");
		}
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event)
	{
		if (!config.rareDropSounds() || event.getContainerId() != 93) // 93 is the inventory container ID
		{
			return;
		}

		// Check for rare drops by monitoring inventory changes
		clientThread.invokeLater(() -> checkForRareDrops(event));
	}

	private void checkForRareDrops(ItemContainerChanged event)
	{
		ItemContainer inventory = event.getItemContainer();
		if (inventory == null)
		{
			return;
		}

		for (Item item : inventory.getItems())
		{
			if (item.getId() <= 0)
			{
				continue;
			}

			int itemPrice = itemManager.getItemPrice(item.getId());
			int totalValue = itemPrice * item.getQuantity();

			if (totalValue >= config.rareDropValue())
			{
				playSound("rare_drop");
				break; // Only play sound once per inventory change
			}
		}
	}

	private void playSound(String soundType)
	{
		// Choose random variant (1-3)
		int variant = random.nextInt(3) + 1;
		String soundFile = String.format("/sounds/%s_%d.wav", soundType, variant);

		try
		{
			InputStream soundStream = getClass().getResourceAsStream(soundFile);
			if (soundStream == null)
			{
				// Fallback to a default sound if specific sound doesn't exist
				log.debug("Sound file not found: {}, using default", soundFile);
				playDefaultSound();
				return;
			}

			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
				new BufferedInputStream(soundStream));
			
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);

			// Apply volume control
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float volumePercent = config.volume() / 100.0f;
			float minGain = gainControl.getMinimum();
			float maxGain = gainControl.getMaximum();
			float gain = minGain + (maxGain - minGain) * volumePercent;
			gainControl.setValue(Math.max(minGain, Math.min(maxGain, gain)));

			clip.start();

			// Clean up resources when clip finishes
			clip.addLineListener(event -> {
				if (event.getType() == LineEvent.Type.STOP)
				{
					clip.close();
					try
					{
						audioInputStream.close();
						soundStream.close();
					}
					catch (IOException e)
					{
						log.error("Error closing audio streams", e);
					}
				}
			});
		}
		catch (Exception e)
		{
			log.error("Error playing sound: " + soundFile, e);
			playDefaultSound();
		}
	}

	private void playDefaultSound()
	{
		// Play a simple beep as fallback
		try
		{
			java.awt.Toolkit.getDefaultToolkit().beep();
		}
		catch (Exception e)
		{
			log.error("Error playing default sound", e);
		}
	}

	@Provides
	StatusQuoSoundsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(StatusQuoSoundsConfig.class);
	}
}
