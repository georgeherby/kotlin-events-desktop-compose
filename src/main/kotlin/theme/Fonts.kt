package theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font

object Fonts {
    val openSans = FontFamily(
        Font(
            resource = "OpenSans-Light.ttf",
            weight = FontWeight.W300,
            style = FontStyle.Normal
        ),Font(
            resource = "OpenSans-LightItalic.ttf",
            weight = FontWeight.W300,
            style = FontStyle.Italic
        ),Font(
            resource = "OpenSans-Regular.ttf",
            weight = FontWeight.W400,
            style = FontStyle.Normal
        ),Font(
            resource = "OpenSans-Italic.ttf",
            weight = FontWeight.W400,
            style = FontStyle.Italic
        ),Font(
            resource = "OpenSans-SemiBold.ttf",
            weight = FontWeight.W600,
            style = FontStyle.Normal
        ),Font(
            resource = "OpenSans-SemiBoldItalic.ttf",
            weight = FontWeight.W600,
            style = FontStyle.Italic
        ),Font(
            resource = "OpenSans-Bold.ttf",
            weight = FontWeight.W700,
            style = FontStyle.Normal
        ),Font(
            resource = "OpenSans-BoldItalic.ttf",
            weight = FontWeight.W700,
            style = FontStyle.Italic
        ),Font(
            resource = "OpenSans-ExtraBold.ttf",
            weight = FontWeight.W800,
            style = FontStyle.Normal
        ),Font(
            resource = "OpenSans-ExtraBoldItalic.ttf",
            weight = FontWeight.W800,
            style = FontStyle.Italic
        )
    )



}
