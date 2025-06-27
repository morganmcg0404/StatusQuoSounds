package com.statusquosounds.triggers;

import com.statusquosounds.StatusQuoSoundsConfig;
import com.statusquosounds.sound.SoundEngine;
import com.statusquosounds.sound.SoundType;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatColorType;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;

import javax.inject.Inject;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Pattern;

@Slf4j
public class EventTriggers {

    @Inject
    private StatusQuoSoundsConfig config;

    @Inject
    private SoundEngine soundEngine;

    @Inject
    private ScheduledExecutorService executor;

    @Inject
    private ItemManager itemManager;

    @Inject
    private ClientThread clientThread;

    @Inject
    private ChatMessageManager chatMessageManager;

    // Patterns for detecting various events
    private static final Pattern COLLECTION_LOG_PATTERN = Pattern.compile("New item added to your collection log:");
    private static final Pattern PET_DROP_PATTERN = Pattern.compile("You have a funny feeling like you're being followed");
    private static final Pattern SUPERIOR_SPAWN_PATTERN = Pattern.compile("A superior foe has appeared");
    private static final Pattern ACHIEVEMENT_DIARY_PATTERN = Pattern.compile("Congratulations, you have completed .* Achievement Diary");
    private static final Pattern COMBAT_ACHIEVEMENT_PATTERN = Pattern.compile("Congratulations, you have completed .* Combat Achievement");
    private static final Pattern QUEST_COMPLETE_PATTERN = Pattern.compile("Congratulations, you have completed");
    private static final Pattern LEVEL_UP_PATTERN = Pattern.compile("Congratulations, you just advanced .* level");

    @Subscribe
    public void onChatMessage(ChatMessage chatMessage) {
        String message = chatMessage.getMessage();
        ChatMessageType type = chatMessage.getType();

        // Only process game messages and spam messages
        if (type != ChatMessageType.GAMEMESSAGE && type != ChatMessageType.SPAM) {
            return;
        }

        // Collection log entries
        if (config.collectionLogSounds() && COLLECTION_LOG_PATTERN.matcher(message).find()) {
            playEventSound(SoundType.randomCollectionLog(), "Collection Log", "New collection log entry!");
        }

        // Pet drops
        if (config.petDropSounds() && PET_DROP_PATTERN.matcher(message).find()) {
            playEventSound(SoundType.randomPetDrop(), "Pet Drop", "You got a pet!");
        }

        // Superior spawns
        if (config.superiorSpawnSounds() && SUPERIOR_SPAWN_PATTERN.matcher(message).find()) {
            playEventSound(SoundType.randomSuperiorSpawn(), "Superior Spawn", "A superior foe appeared!");
        }

        // Achievement diary completions
        if (config.achievementDiarySounds() && ACHIEVEMENT_DIARY_PATTERN.matcher(message).find()) {
            playEventSound(SoundType.randomAchievementDiary(), "Achievement Diary", "Achievement diary completed!");
        }

        // Combat achievement completions
        if (config.combatDiarySounds() && COMBAT_ACHIEVEMENT_PATTERN.matcher(message).find()) {
            playEventSound(SoundType.randomCombatDiary(), "Combat Achievement", "Combat achievement completed!");
        }

        // Quest completions
        if (config.questCompleteSounds() && QUEST_COMPLETE_PATTERN.matcher(message).find()) {
            playEventSound(SoundType.randomQuestComplete(), "Quest Complete", "Quest completed!");
        }

        // Level ups
        if (config.levelUpSounds() && LEVEL_UP_PATTERN.matcher(message).find()) {
            playEventSound(SoundType.randomLevelUp(), "Level Up", "Level up!");
        }
    }

    @Subscribe
    public void onItemContainerChanged(ItemContainerChanged event) {
        if (!config.rareDropSounds() || event.getContainerId() != 93) { // 93 is inventory container ID
            return;
        }

        // Check for rare drops by monitoring inventory changes
        clientThread.invokeLater(() -> checkForRareDrops(event));
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event) {
        if (StatusQuoSoundsConfig.class.getSimpleName().toLowerCase().equals(event.getGroup())) {
            if ("volume".equals(event.getKey())) {
                // Play a test sound when volume changes
                soundEngine.playSound(SoundType.randomLevelUp(), executor);
            }
        }
    }

    private void checkForRareDrops(ItemContainerChanged event) {
        ItemContainer inventory = event.getItemContainer();
        if (inventory == null) {
            return;
        }

        boolean playedSound = false;
        for (Item item : inventory.getItems()) {
            if (item.getId() <= 0 || playedSound) {
                continue;
            }

            int itemPrice = itemManager.getItemPrice(item.getId());
            int totalValue = itemPrice * item.getQuantity();

            if (totalValue >= config.rareDropValue()) {
                playEventSound(SoundType.randomRareDrop(), "Rare Drop", 
                    "Valuable drop worth " + formatGp(totalValue) + " GP!");
                playedSound = true;
                break; // Only play sound once per inventory change
            }
        }
    }

    private void playEventSound(SoundType soundType, String eventType, String chatMessage) {
        // Play the sound
        soundEngine.playSound(soundType, executor);
        log.debug("Played {} sound: {}", eventType, soundType.getFileName());

        // Show chat message if enabled
        if (config.showChatMessages()) {
            showFakeChatMessage(chatMessage);
        }
    }

    private void showFakeChatMessage(String message) {
        String chatMessage = new ChatMessageBuilder()
            .append(ChatColorType.HIGHLIGHT)
            .append("[StatusQuoSounds] ")
            .append(ChatColorType.NORMAL)
            .append(message)
            .build();

        chatMessageManager.queue(QueuedMessage.builder()
            .type(ChatMessageType.PUBLICCHAT)
            .name("StatusQuoSounds")
            .runeLiteFormattedMessage(chatMessage)
            .build());
    }

    private String formatGp(int value) {
        if (value >= 1_000_000) {
            return String.format("%.1fM", value / 1_000_000.0);
        } else if (value >= 1_000) {
            return String.format("%.1fK", value / 1_000.0);
        } else {
            return String.valueOf(value);
        }
    }
}
