package social.androiddev.hiberfake.biathlonk.navigation

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import kotlin.reflect.KClass

fun NavDestination?.hasRouteInHierarchy(route: KClass<*>) = this?.hierarchy?.any { it.hasRoute(route) } == true

fun NavDestination?.hasTopLevelDestination() = TopLevelDestination.entries.any { destination ->
    hasRouteInHierarchy(destination.route)
}
