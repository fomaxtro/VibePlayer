package com.fomaxtro.vibeplayer.feature.library.library.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeAppTitle
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeIconButton
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeOutlinedTextField
import com.fomaxtro.vibeplayer.core.designsystem.component.VibeScanIconButton
import com.fomaxtro.vibeplayer.core.designsystem.resources.VibeIcons
import com.fomaxtro.vibeplayer.core.designsystem.theme.VibePlayerTheme
import com.fomaxtro.vibeplayer.feature.library.R

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LibraryTopBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onScanMusicClick: () -> Unit,
    onSearchClick: () -> Unit,
    onClearClick: () -> Unit,
    onCancelClick: () -> Unit,
    search: Boolean
) {
    val searchingTransition = updateTransition(
        search,
        label = "Search transition"
    )
    val searchFocusRequester = remember { FocusRequester() }

    if (searchingTransition.currentState == searchingTransition.targetState && search) {
        LaunchedEffect(Unit) {
            searchFocusRequester.requestFocus()
        }
    }

    TopAppBar(
        title = {
            searchingTransition.AnimatedContent(
                transitionSpec = {
                    if (targetState) {
                        (slideInHorizontally(
                            animationSpec = tween()
                        ) + expandHorizontally(
                            animationSpec = tween()
                        )) togetherWith fadeOut()
                    } else {
                        fadeIn(
                            animationSpec = tween(
                                delayMillis = 300
                            )
                        ) togetherWith (slideOutHorizontally(
                            animationSpec = tween()
                        ) + shrinkHorizontally(
                            animationSpec = tween()
                        ))
                    }
                },
                contentAlignment = Alignment.CenterStart
            ) { isSearching ->
                if (isSearching) {
                    VibeOutlinedTextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(searchFocusRequester),
                        leadingIcon = {
                            Icon(
                                imageVector = VibeIcons.Outlined.Search,
                                contentDescription = stringResource(R.string.search)
                            )
                        },
                        placeholder = stringResource(R.string.search),
                        trailingIcon = {
                            IconButton(
                                onClick = onClearClick
                            ) {
                                Icon(
                                    imageVector = VibeIcons.Filled.Close,
                                    contentDescription = stringResource(R.string.close)
                                )
                            }
                        },
                        singleLine = true
                    )
                } else {
                    VibeAppTitle()
                }
            }
        },
        actions = {
            searchingTransition.Crossfade(
                animationSpec = spring()
            ) { isSearching ->
                if (isSearching) {
                    TextButton(
                        onClick = onCancelClick
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                } else {
                    Row {
                        VibeScanIconButton(
                            onClick = onScanMusicClick
                        )

                        VibeIconButton(
                            onClick = onSearchClick
                        ) {
                            Icon(
                                imageVector = VibeIcons.Outlined.Search,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun LibraryTopBarPreview() {
    VibePlayerTheme {
        LibraryTopBar(
            search = true,
            onScanMusicClick = {},
            onSearchClick = {},
            onClearClick = {},
            onCancelClick = {},
            searchQuery = "sdsd",
            onSearchQueryChange = {}
        )
    }
}