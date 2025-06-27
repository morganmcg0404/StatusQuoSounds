#!/usr/bin/env python3
"""
Simple script to generate placeholder WAV sounds for the StatusQuoSounds plugin.
This creates basic sine wave tones of different frequencies for each event type.

Run this script to generate the required sound files if you don't have custom ones.
"""

import numpy as np
import wave
import os

def generate_sine_wave(frequency, duration, sample_rate=44100, amplitude=0.3):
    """Generate a sine wave with given frequency and duration."""
    frames = int(duration * sample_rate)
    arr = np.sin(2 * np.pi * frequency * np.linspace(0, duration, frames))
    arr = (arr * amplitude * 32767).astype(np.int16)
    return arr

def save_wav(filename, data, sample_rate=44100):
    """Save audio data as WAV file."""
    with wave.open(filename, 'w') as wav_file:
        wav_file.setnchannels(1)  # Mono
        wav_file.setsampwidth(2)  # 16-bit
        wav_file.setframerate(sample_rate)
        wav_file.writeframes(data.tobytes())

def main():
    """Generate all required sound files."""
    
    # Sound configurations: (base_frequency, duration)
    sound_configs = {
        'collection_log': (800, 0.5),    # High pitch, short
        'pet_drop': (400, 1.0),          # Medium pitch, longer
        'rare_drop': (600, 0.8),         # Medium-high pitch
        'superior_spawn': (200, 0.6),    # Low pitch, warning
        'achievement_diary': (500, 1.2), # Pleasant medium tone
        'combat_diary': (450, 1.0)       # Slightly lower than achievement
    }
    
    print("Generating placeholder sound files...")
    
    for sound_type, (base_freq, duration) in sound_configs.items():
        for variant in range(1, 4):
            # Vary frequency slightly for each variant
            frequency = base_freq + (variant - 2) * 50
            
            # Generate the sound
            audio_data = generate_sine_wave(frequency, duration)
            
            # Save to file
            filename = f"{sound_type}_{variant}.wav"
            save_wav(filename, audio_data)
            
            print(f"Generated: {filename}")
    
    print("\nAll placeholder sounds generated!")
    print("You can replace these with your own custom sound files.")

if __name__ == "__main__":
    main()
