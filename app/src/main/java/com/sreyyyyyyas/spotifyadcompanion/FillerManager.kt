package com.sreyyyyyyas.spotifyadcompanion

data class FillerSong(
    val title: String,
    val url: String
)

object FillerManager {
    private val fillerSongs = listOf(
        FillerSong("Lofi Chill Beat", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"),
        FillerSong("Relaxing Piano", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"),
        FillerSong("Calm Ambient", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3")
    )

    private var currentIndex = 0

    val currentSong: FillerSong
        get() = fillerSongs[currentIndex]

    fun nextSong(): FillerSong {
        currentIndex = (currentIndex + 1) % fillerSongs.size
        return currentSong
    }

    fun previousSong(): FillerSong {
        currentIndex = if (currentIndex - 1 < 0) fillerSongs.size - 1 else currentIndex - 1
        return currentSong
    }

    fun resetToFirst() {
        currentIndex = 0
    }
}
