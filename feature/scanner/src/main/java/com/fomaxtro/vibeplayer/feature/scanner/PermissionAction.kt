package com.fomaxtro.vibeplayer.feature.scanner

sealed interface PermissionAction {
    data object OnAllowAccessClick : PermissionAction
}