@file:OptIn(ExperimentalMaterial3Api::class)

package com.sreyyyyyyas.spotifyadcompanion.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AboutScreen(navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Made by Sreyas V M", style = MaterialTheme.typography.titleLarge)
            Text("This app helps skip Spotify ads by playing your favorite filler tracks in-between.", style = MaterialTheme.typography.bodyLarge)

            Button(onClick = {
                val url = "https://www.buymeacoffee.com/sreyasvm"
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }) {
                Text("☕ Buy me a coffee")
            }

            Button(onClick = {
                val githubUrl = "https://github.com/sreyasvm"
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl)))
            }) {
                Text("🔗 GitHub")
            }

            Button(onClick = {
                val linkedinUrl = "https://www.linkedin.com/in/sreyasvm"
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(linkedinUrl)))
            }) {
                Text("💼 LinkedIn")
            }
        }
    }
}
