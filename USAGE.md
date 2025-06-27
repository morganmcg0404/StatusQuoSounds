# StatusQuoSounds Plugin - Usage Guide

## Getting Started

1. **Build the Plugin**
   ```
   ./gradlew build
   ```

2. **Install in RuneLite**
   - Copy the built JAR from `build/libs/` to your RuneLite plugins directory
   - Or use the RuneLite development environment to test

3. **Configure the Plugin**
   - Open RuneLite settings
   - Navigate to "StatusQuoSounds" section
   - Configure your desired settings

## Configuration Options

### Sound Settings
- **Volume (0-100)**: Controls the playback volume of all sounds
  - Default: 50
  - Higher values = louder sounds
  - Set to 0 to mute all sounds

### Event Toggles
Each event type can be individually enabled/disabled:

- **Collection Log**: Plays when you get a new collection log entry
- **Pet Drops**: Plays when you receive a pet drop  
- **Rare Drops**: Plays when you receive valuable items
- **Superior Spawns**: Plays when superior slayer monsters spawn
- **Achievement Diary**: Plays when completing achievement diaries
- **Combat Diary**: Plays when completing combat achievements

### Drop Settings
- **Rare Drop Value (GP)**: Minimum value for rare drop sounds
  - Default: 1,000,000 GP
  - Based on Grand Exchange prices
  - Only items worth more than this value will trigger the sound

## Sound Detection

The plugin detects events through:

### Chat Messages
- Collection log: "New item added to your collection log:"
- Pet drops: "You have a funny feeling like you're being followed"
- Superior spawns: "A superior foe has appeared"
- Achievement diary: "Congratulations, you have completed .* Achievement Diary"
- Combat diary: "Congratulations, you have completed .* Combat Achievement"

### Inventory Changes
- Rare drops: Monitors inventory for items above the configured GP value

## Custom Sound Files

### Adding Your Own Sounds

1. Navigate to `src/main/resources/sounds/`
2. Replace the generated WAV files with your custom sounds
3. Keep the same naming convention: `{event_type}_{variant}.wav`
4. Rebuild the plugin

### Sound Requirements
- **Format**: WAV files only
- **Variants**: 3 files per event type (numbered 1-3)
- **Size**: Keep under 1MB per file for best performance
- **Quality**: 44.1kHz sample rate recommended

### Event Types and Files
- Collection Log: `collection_log_1.wav`, `collection_log_2.wav`, `collection_log_3.wav`
- Pet Drops: `pet_drop_1.wav`, `pet_drop_2.wav`, `pet_drop_3.wav`
- Rare Drops: `rare_drop_1.wav`, `rare_drop_2.wav`, `rare_drop_3.wav`
- Superior Spawns: `superior_spawn_1.wav`, `superior_spawn_2.wav`, `superior_spawn_3.wav`
- Achievement Diary: `achievement_diary_1.wav`, `achievement_diary_2.wav`, `achievement_diary_3.wav`
- Combat Diary: `combat_diary_1.wav`, `combat_diary_2.wav`, `combat_diary_3.wav`

## Troubleshooting

### No Sound Playing
1. Check that the event toggle is enabled
2. Verify volume is above 0
3. Ensure sound files exist in the correct location
4. Check that your system audio is working

### Wrong Events Triggering
- The plugin relies on chat message patterns
- Some events might have variations in wording
- Check the RuneLite console for debug messages

### Performance Issues
- Reduce the size of your sound files
- Lower the volume setting
- Disable events you don't need

## Development

### Testing the Plugin
```
./gradlew test
```

### Running with RuneLite
```
java -jar build/libs/StatusQuoSounds-1.0-SNAPSHOT.jar
```

### Debugging
- Enable debug logging in RuneLite
- Check console output for error messages
- Verify sound file paths and formats

## Contributing

Areas for improvement:
- More accurate event detection
- Additional event types
- Better sound generation
- Enhanced configuration options
- Performance optimizations
