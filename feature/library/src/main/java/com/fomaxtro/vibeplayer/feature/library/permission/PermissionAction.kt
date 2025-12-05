package com.fomaxtro.vibeplayer.feature.library.permission

sealed interface PermissionAction {
    data object OnAllowAccessClick : PermissionAction
}