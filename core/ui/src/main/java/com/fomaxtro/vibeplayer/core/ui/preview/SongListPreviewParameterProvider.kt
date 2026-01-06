package com.fomaxtro.vibeplayer.core.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.fomaxtro.vibeplayer.domain.model.Song

class SongListPreviewParameterProvider : PreviewParameterProvider<List<Song>> {
    override val values: Sequence<List<Song>> = sequenceOf(
        SongPreviewFactory.defaultList,
        emptyList()
    )
}