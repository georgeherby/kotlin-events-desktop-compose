import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.Window
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.application
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import pages.overview.eventScreen
import theme.Fonts
import theme.Theme.isSystemInDarkTheme
import theme.Theme.themeColour
import kotlin.properties.Delegates.observable

var onIsInDarkModeChanged: ((Boolean, Boolean) -> Unit)? = null

var isInDarkMode by observable(false) { _, oldValue, newValue ->
    onIsInDarkModeChanged?.let { it(oldValue, newValue) }
}

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    GlobalScope.launch {
        while (isActive) {
            val newMode = isSystemInDarkTheme()
            if (isInDarkMode != newMode) {
                isInDarkMode = newMode
            }
            delay(1000)
        }
    }
    Window(title = "Kotlin Events") {
        mainApp()
    }
}

@Composable
fun mainApp() {
    var colors by remember { mutableStateOf(themeColour(isInDarkMode)) }
    onIsInDarkModeChanged = { _, _ ->
        colors = themeColour(isInDarkMode)
    }
    DesktopMaterialTheme(colors = colors, typography = Typography(defaultFontFamily = Fonts.openSans)) {
        eventScreen()
    }
}



