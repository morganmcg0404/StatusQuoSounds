# StatusQuoSounds - Usage Guide

## Overview

StatusQuoSounds is a RuneLite plugin that plays custom sounds for various in-game events. It's inspired by the C Engineer: Completed plugin and stores sound files in your `.runelite` directory for easy customization.

## Quick Start

1. Install and enable the plugin in RuneLite
2. Sounds will automatically be prepared in `.runelite/statusquosounds-sounds/`
3. Configure which events you want to hear in the plugin settings
4. Adjust volume as desired (0-200%)

## Event Types

### Achievement Sounds
- **Level ups**: Plays when gaining any skill level
- **Quest completions**: Plays when completing quests  
- **Collection log entries**: Plays when getting new collection log items
- **Achievement diaries**: Plays when completing achievement diary tiers
- **Combat achievements**: Plays when completing combat achievement tasks

### Drop Sounds
- **Pet drops**: Plays when receiving pet drops
- **Rare drops**: Plays when receiving valuable or untradeable drops (detected from game messages)
- **Superior spawns**: Plays when superior slayer monsters spawn

## Configuration Options

### Achievement Sounds Section
- **Level ups** - Toggle for skill level gains
- **Quest completions** - Toggle for quest completion sounds
- **Collection log entries** - Toggle for new collection log items
- **Achievement diaries** - Toggle for achievement diary completions
- **Combat achievements** - Toggle for combat achievement completions

### Drop Sounds Section  
- **Pet drops** - Toggle for pet drop sounds
- **Rare drops** - Toggle for valuable/untradeable drops (detected from chat messages)
- **Superior slayer spawns** - Toggle for superior monster spawns
- **Rare drop value threshold** - Currently unused (detection based on game messages)

### General Settings
- **Show chat notifications** - Display fake chat messages for events (default: off)
- **Sound volume** - Volume control from 0-200% (default: 100%)

## Testing Sounds

You can test any event sound using chat commands:

### Chat Commands
- `!test <event_type>` - Play a test sound for the specified event

#### Available Event Types
- `collection_log` - Test collection log entry sound
- `pet_drop` - Test pet drop sound  
- `rare_drop` - Test rare drop sound
- `superior_spawn` - Test superior slayer spawn sound
- `achievement_diary` - Test achievement diary completion sound
- `combat_diary` - Test combat achievement sound
- `level_up` - Test level up sound
- `quest_complete` - Test quest completion sound

#### Examples
- `!test level_up` - Play a random level up sound
- `!test pet_drop` - Play a random pet drop sound
- `!test rare_drop` - Play a random rare drop sound

### Notes
- Commands work in any chat (public, private, friends, clan)
- A system beep plays first to verify audio is working
- Test commands will show a chat notification if that setting is enabled
- Commands are logged for debugging

## Customizing Sounds

### Sound File Location

Sounds are stored in your RuneLite directory:
- **Windows**: `C:\Users\<username>\.runelite\statusquosounds-sounds\`
- **macOS**: `~/.runelite/statusquosounds-sounds/`
- **Linux**: `~/.runelite/statusquosounds-sounds/`

### Sound File Names

Each event has 3 sound variants (randomly selected):

**Achievement Sounds:**
- `level_up_1.wav`, `level_up_2.wav`, `level_up_3.wav`
- `quest_complete_1.wav`, `quest_complete_2.wav`, `quest_complete_3.wav`
- `collection_log_1.wav`, `collection_log_2.wav`, `collection_log_3.wav`
- `achievement_diary_1.wav`, `achievement_diary_2.wav`, `achievement_diary_3.wav`
- `combat_diary_1.wav`, `combat_diary_2.wav`, `combat_diary_3.wav`

**Drop Sounds:**
- `pet_drop_1.wav`, `pet_drop_2.wav`, `pet_drop_3.wav`
- `rare_drop_1.wav`, `rare_drop_2.wav`, `rare_drop_3.wav`
- `superior_spawn_1.wav`, `superior_spawn_2.wav`, `superior_spawn_3.wav`

### How to Customize

1. Navigate to the `statusquosounds-sounds` directory
2. Replace any `.wav` file with your custom sound (keep the same filename)
3. Ensure your custom files are actual `.wav` format (not just renamed MP3s)
4. The plugin will automatically use your custom sounds

### Tips for Custom Sounds

- Keep sounds relatively short (1-5 seconds)
- Use moderate volume levels in your audio files
- Test volume using the plugin's volume slider
- WAV files work best for compatibility

## Volume Control

- **Range**: 0-200% (inspired by C Engineer plugin)
- **0%**: Completely muted
- **100%**: Normal/default volume
- **200%**: Double volume (may distort)
- **Test**: Changing volume plays a sample sound

## Chat Notifications

When enabled, the plugin shows fake public chat messages that only you can see:
- Format: `[StatusQuoSounds] <event description>`
- Appears in your chat like a normal message
- Useful for confirming the plugin detected an event
- Disabled by default to avoid chat spam

## Troubleshooting

### No Sound Playing
1. Check volume is above 0%
2. Verify sound files exist in the sounds directory
3. Check that the specific event toggle is enabled
4. Ensure your system audio is working
5. Missing files will fall back to system beep

### Custom Sounds Not Working
1. Verify file format is `.wav` (not renamed MP3/other)
2. Check filename matches exactly (case-sensitive on some systems)
3. Ensure file isn't corrupted
4. Try with a simple test WAV file first

### Events Not Triggering
1. Verify the event toggle is enabled in config
2. Check that game messages are appearing in chat
3. Some events require specific in-game settings (e.g., collection log notifications)
4. Collection log events need the in-game notification setting enabled

## Event Detection

The plugin detects events by monitoring:
- **Chat messages**: For all events including level ups, quests, collection log, rare drops, etc.
- **Game messages**: Specifically looks for "Valuable drop:" and "Untradeable drop:" messages

All detection is now chat-based for consistency and accuracy. Some events require specific in-game settings to generate detectable messages.

## Performance

- Minimal performance impact
- Sounds play asynchronously
- File I/O is optimized
- Memory usage is low

## Architecture Notes

This plugin follows the C Engineer: Completed plugin architecture:
- External sound file storage
- Modular class structure
- Enum-based sound management
- Event-driven architecture
- Configurable volume range (0-200%)

## Support

For issues or feature requests, check the plugin documentation or repository issues.
