package com.fomaxtro.vibeplayer.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fomaxtro.vibeplayer.core.designsystem.R

internal val HostGrotesk = FontFamily(
    Font(R.font.host_grotestk_variable)
)

private val BaseTextStyle = TextStyle(
    fontFamily = HostGrotesk
)

private val DefaultTypography = Typography()

internal val Typography = Typography(
    displayLarge = DefaultTypography.displayLarge.merge(BaseTextStyle),
    displayMedium = DefaultTypography.displayMedium.merge(BaseTextStyle),
    displaySmall = DefaultTypography.displaySmall.merge(BaseTextStyle),
    headlineLarge = DefaultTypography.headlineLarge.merge(BaseTextStyle),
    headlineMedium = DefaultTypography.headlineMedium.merge(BaseTextStyle),
    headlineSmall = DefaultTypography.headlineSmall.merge(BaseTextStyle),
    titleLarge = TextStyle(
        fontFamily = HostGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
        lineHeight = 32.sp
    ),
    titleMedium = TextStyle(
        fontFamily = HostGrotesk,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp
    ),
    titleSmall = DefaultTypography.titleSmall.merge(BaseTextStyle),
    bodyLarge = TextStyle(
        fontFamily = HostGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = HostGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
    bodySmall = DefaultTypography.bodySmall.merge(BaseTextStyle),
    labelLarge = TextStyle(
        fontFamily = HostGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    labelMedium = DefaultTypography.labelMedium.merge(BaseTextStyle),
    labelSmall = DefaultTypography.labelSmall.merge(BaseTextStyle)
)