package com.ggufsurgeon

import androidx.compose.runtime.Composable
import com.ggufsurgeon.ui.navigation.SurgeonNavGraph
import com.ggufsurgeon.ui.theme.GGUFSurgeonTheme

@Composable
fun GGUFSurgeonApp() {
    GGUFSurgeonTheme {
        SurgeonNavGraph()
    }
}
