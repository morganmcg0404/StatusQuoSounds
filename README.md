# StatusQuoSounds RuneLite Plugin

A RuneLite plugin that plays custom sounds for various in-game events. Each event has 3 different sound variations that are chosen randomly when the event occurs.

## Features

### Supported Events
- **Collection Log Entries** - Plays when you get a new collection log item
- **Pet Drops** - Plays when you receive a pet drop
- **Rare Drops** - Plays when you receive an item above a configurable GP value
- **Superior Spawns** - Plays when a superior slayer monster spawns
- **Achievement Diary Complete** - Plays when you complete an achievement diary
- **Combat Diary Complete** - Plays when you complete a combat achievement

### Configuration Options
- **Volume Control** - Set volume from 0-100 via text input
- **Event Toggles** - Enable/disable sounds for each event type individually
- **Rare Drop Value** - Set the minimum GP value for rare drop notifications

## Installation

1. Build the plugin using Gradle:
   ```
   ./gradlew build
   ```

2. Place the built JAR file in your RuneLite plugins directory

3. Enable the plugin in RuneLite's plugin hub

## Sound Files

The plugin looks for WAV sound files in the `/sounds/` directory within the plugin resources. Each event type requires 3 sound files:

- `collection_log_1.wav`, `collection_log_2.wav`, `collection_log_3.wav`
- `pet_drop_1.wav`, `pet_drop_2.wav`, `pet_drop_3.wav`
- `rare_drop_1.wav`, `rare_drop_2.wav`, `rare_drop_3.wav`
- `superior_spawn_1.wav`, `superior_spawn_2.wav`, `superior_spawn_3.wav`
- `achievement_diary_1.wav`, `achievement_diary_2.wav`, `achievement_diary_3.wav`
- `combat_diary_1.wav`, `combat_diary_2.wav`, `combat_diary_3.wav`

### Generating Placeholder Sounds

If you don't have custom sound files, you can generate placeholder sounds by running:

```bash
python generate_sounds.py
```

This will create basic sine wave tones for each event type. You can then replace these with your own custom sound files.

## Configuration

The plugin adds a "StatusQuoSounds" section to the RuneLite configuration panel with the following options:

### Sound Settings
- **Volume**: Set the playback volume (0-100)

### Event Toggles
- Individual toggles for each event type

### Drop Settings
- **Rare Drop Value**: Minimum GP value for rare drop sounds (based on Grand Exchange prices)

## Technical Details

- Uses RuneLite's event system to detect game events
- Supports volume control via Java's audio system
- Falls back to system beep if sound files are missing
- Monitors chat messages for event detection
- Uses ItemManager for Grand Exchange price lookups

## Contributing

Feel free to contribute by:
- Adding support for more events
- Improving sound detection accuracy
- Adding better default sound files
- Enhancing the configuration options