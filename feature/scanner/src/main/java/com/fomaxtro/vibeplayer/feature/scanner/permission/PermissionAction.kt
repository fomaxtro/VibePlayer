package com.fomaxtro.vibeplayer.feature.scanner.permission

sealed interface PermissionAction {
    data object OnAllowAccessClick : PermissionAction
}