package com.fomaxtro.vibeplayer.core.player.di

import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import com.fomaxtro.vibeplayer.core.player.ExoPlayerMusicPlayer
import com.fomaxtro.vibeplayer.domain.player.MusicPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val corePlayerModule = module(createdAtStart = true) {
    single<ExoPlayer> {
        ExoPlayer.Builder(androidContext())
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                    .setUsage(C.USAGE_MEDIA)
                    .build(),
                true
            )
            .setHandleAudioBecomingNoisy(true)
            .build()
    }

    singleOf(::ExoPlayerMusicPlayer) bind MusicPlayer::class
}