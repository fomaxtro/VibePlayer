package com.fomaxtro.vibeplayer.feature.onboarding.permission

sealed interface PermissionAction {
    data object OnAllowAccessClick : PermissionAction
}