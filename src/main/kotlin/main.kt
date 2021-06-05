import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.Window
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.Typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import pages.overview.EventOverviewViewModel
import pages.overview.eventPage
import services.EventsService
import theme.Fonts
import theme.Theme.themeColour
import theme.Theme.isSystemInDarkTheme
import kotlin.properties.Delegates.observable

private var isInDarkMode by observable(false) { _, oldValue, newValue ->
    onIsInDarkModeChanged?.let { it(oldValue, newValue) }
}
private var onIsInDarkModeChanged: ((Boolean, Boolean) -> Unit)? = null

fun main() = Window {
    GlobalScope.launch {
        while (isActive) {
            val newMode = isSystemInDarkTheme()
            if (isInDarkMode != newMode) {
                isInDarkMode = newMode
            }
        }
    }

    val viewModel = EventOverviewViewModel(EventsService())
    viewModel.getEvents()

    var colors by remember { mutableStateOf(themeColour(isInDarkMode)) }
    onIsInDarkModeChanged = { _, _ ->
        colors = themeColour(isInDarkMode)
    }

    DesktopMaterialTheme(colors = colors, typography = Typography(defaultFontFamily = Fonts.openSans)) {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text("Jetbrains Event")
                },
                    actions = {
                        IconButton(onClick = { viewModel.getEvents() }) {
                            Icon(Icons.Rounded.Refresh, contentDescription = null)
                        }

                    }
                )
            },
            content = {
                eventPage(viewModel)
            }
        )
    }
}


