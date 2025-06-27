# StatusQuoSounds

A RuneLite plugin that plays custom sounds for various in-game events, inspired by the C Engineer: Completed plugin architecture.

## Features

- **Achievement Sounds**: Level ups, quest completions, collection log entries, achievement diaries, combat achievements
- **Drop Sounds**: Pet drops, rare drops (configurable value threshold), superior slayer spawns
- **Customizable**: Individual toggles for each event type, volume control (0-200%), and optional chat notifications
- **User Sound Directory**: Sounds are stored in `.runelite/statusquosounds-sounds/` for easy customization
- **Multiple Variants**: 3 random sound variants for each event type to keep things fresh

## Sound Events

### Achievement Sounds
- **Level ups**: Any skill level gained
- **Quest completions**: Quest completion messages
- **Collection log entries**: New collection log slots
- **Achievement diaries**: Achievement diary completions
- **Combat achievements**: Combat achievement task completions

### Drop Sounds  
- **Pet drops**: Pet drop notifications
- **Rare drops**: Valuable item drops (configurable GP threshold)
- **Superior spawns**: Superior slayer monster spawns

## Installation

1. Download the plugin JAR file
2. Place it in your RuneLite plugins directory
3. Enable the plugin in RuneLite
4. Sounds will be automatically extracted to `.runelite/statusquosounds-sounds/`

## Configuration

The plugin provides granular control over each event type:

### Achievement Sounds Section
- Level ups (enabled by default)
- Quest completions (enabled by default)  
- Collection log entries (enabled by default)
- Achievement diaries (enabled by default)
- Combat achievements (enabled by default)

### Drop Sounds Section
- Pet drops (enabled by default)
- Rare drops (enabled by default)
- Superior slayer spawns (enabled by default)
- Rare drop value threshold (1M GP default)

### General Settings
- Show chat notifications (disabled by default)
- Sound volume (0-200%, 100% default)

## Customizing Sounds

### 1. Locate your sound files

Navigate to your `.runelite` folder and find the `statusquosounds-sounds` directory:
- **Windows**: `C:\Users\<username>\.runelite\statusquosounds-sounds\`
- **macOS**: `~/.runelite/statusquosounds-sounds/`
- **Linux**: `~/.runelite/statusquosounds-sounds/`

### 2. Sound File Structure

Each event type has 3 sound variants that are randomly selected:

```
collection_log_1.wav, collection_log_2.wav, collection_log_3.wav
pet_drop_1.wav, pet_drop_2.wav, pet_drop_3.wav
rare_drop_1.wav, rare_drop_2.wav, rare_drop_3.wav
superior_spawn_1.wav, superior_spawn_2.wav, superior_spawn_3.wav
achievement_diary_1.wav, achievement_diary_2.wav, achievement_diary_3.wav
combat_diary_1.wav, combat_diary_2.wav, combat_diary_3.wav
level_up_1.wav, level_up_2.wav, level_up_3.wav
quest_complete_1.wav, quest_complete_2.wav, quest_complete_3.wav
```

### 3. Replacing Sounds

- Replace any `.wav` file with your custom sound (same filename)
- Make sure your files are actual `.wav` format (not just renamed)
- Files must match the exact filename for the plugin to recognize them
- If a sound file is missing, the plugin will fall back to a system beep

### 4. Volume Control

- Volume range: 0-200% (like C Engineer plugin)
- 0% = muted
- 100% = normal volume
- 200% = double volume
- Changing volume in config plays a test sound

## Architecture

This plugin is inspired by and modeled after the C Engineer: Completed plugin:

- **External Sound Files**: Sounds stored in user directory, not embedded in JAR
- **Modular Design**: Separate classes for sound management, event handling, and file management
- **Enum-based Sound Types**: Clean enumeration of all sound variants
- **Random Variants**: Multiple sound files per event with random selection
- **Granular Config**: Individual toggles for each event type
- **Volume Range**: 0-200% volume control with logarithmic scaling
- **Chat Integration**: Optional fake chat messages for events

## Building

```bash
./gradlew build
```

## Development

The plugin uses the following structure:

- `StatusQuoSoundsPlugin.java` - Main plugin class
- `StatusQuoSoundsConfig.java` - Configuration interface  
- `sound/SoundType.java` - Enum of all sound variants
- `sound/SoundEngine.java` - Audio playback engine
- `sound/SoundFileManager.java` - File management and extraction
- `triggers/EventTriggers.java` - Event detection and handling

## Credits

- Inspired by the [C Engineer: Completed](https://github.com/m0bilebtw/c-engineer-completed) plugin
- Built for the [RuneLite](https://runelite.net/) client
