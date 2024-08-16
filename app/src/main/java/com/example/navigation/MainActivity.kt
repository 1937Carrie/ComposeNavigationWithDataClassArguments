package com.example.navigation

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.navigation.ui.theme.ComposeNavigationWithDataClassArgumentsTheme
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeNavigationWithDataClassArgumentsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = First) {
                        composable<First> {
                            TestScreen(
                                screenNumber = "First screen",
                                onNavigate = {
                                    navController.navigate(Second(Name("test name")))
                                },
                                modifier = Modifier.padding(innerPadding),
                            )
                        }
                        composable<Second>(
                            typeMap = mapOf(typeOf<Name>() to parcelableType<Name>())
                        ) { backStackEntry ->
                            val profile: Second = backStackEntry.toRoute()
                            TestScreen(
                                screenNumber = "${profile.name.value} screen",
                                onNavigate = { navController.navigate(First) },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Serializable
object First

@Serializable
data class Second(val name: Name)

@Serializable
@Parcelize
data class Name(val value: String) : Parcelable

inline fun <reified T : Parcelable> parcelableType(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(bundle: Bundle, key: String) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, T::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }

    override fun parseValue(value: String): T = json.decodeFromString(value)

    override fun serializeAsValue(value: T): String = json.encodeToString(value)

    override fun put(bundle: Bundle, key: String, value: T) = bundle.putParcelable(key, value)
}