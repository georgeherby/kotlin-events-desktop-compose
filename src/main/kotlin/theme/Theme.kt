package theme

import androidx.compose.material.Colors
import com.github.tkuenneth.nativeparameterstoreaccess.MacOSDefaults
import com.github.tkuenneth.nativeparameterstoreaccess.NativeParameterStoreAccess
import com.github.tkuenneth.nativeparameterstoreaccess.WindowsRegistry

object Theme {
    // https://dev.to/tkuenneth/automatically-switch-to-dark-mode-and-back-in-compose-for-desktop-303l
    fun isSystemInDarkTheme(): Boolean {
        return when {
            NativeParameterStoreAccess.IS_WINDOWS -> {
                val result = WindowsRegistry.getWindowsRegistryEntry(
                    "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize",
                    "AppsUseLightTheme"
                )
                result == 0x0
            }
            NativeParameterStoreAccess.IS_MACOS -> {
                val result = MacOSDefaults.getDefaultsEntry("AppleInterfaceStyle")
                result == "Dark"
            }
            else -> false
        }
    }

    fun themeColour(isInDarkMode: Boolean): Colors = if (isInDarkMode) {
        Colours.darkColours
    } else {
        Colours.lightColours
    }
}
