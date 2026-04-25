package com.brelinx.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.brelinx.data.Project
import com.brelinx.data.ProjectStatus
import com.brelinx.data.SampleData
import com.brelinx.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(onProjectClick: (Project) -> Unit, onLogout: () -> Unit) {
    val projects = SampleData.projects

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "brelinx",
                            style = MaterialTheme.typography.titleLarge,
                            color = BrelinxBlack,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Client Portal",
                            style = MaterialTheme.typography.labelMedium,
                            color = BrelinxMediumGray
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Sign out",
                            tint = BrelinxMediumGray
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BrelinxWhite,
                )
            )
        },
        containerColor = BrelinxSurface
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Your Projects",
                    style = MaterialTheme.typography.headlineMedium,
                    color = BrelinxBlack,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "${projects.size} projects total",
                    style = MaterialTheme.typography.bodyMedium,
                    color = BrelinxMediumGray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(projects) { project ->
                ProjectCard(project = project, onClick = { onProjectClick(project) })
            }
        }
    }
}

@Composable
fun ProjectCard(project: Project, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = BrelinxWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = project.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = BrelinxBlack,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = project.type,
                        style = MaterialTheme.typography.bodyMedium,
                        color = BrelinxMediumGray,
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                StatusChip(status = project.status)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Progress",
                    style = MaterialTheme.typography.labelMedium,
                    color = BrelinxMediumGray,
                )
                Text(
                    text = "${project.overallProgress}%",
                    style = MaterialTheme.typography.labelMedium,
                    color = BrelinxTeal,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { project.overallProgress / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = BrelinxTeal,
                trackColor = BrelinxLightGray,
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LabelValue(label = "Started", value = project.startDate)
                LabelValue(label = "Delivery", value = project.estimatedDelivery, alignEnd = true)
            }
        }
    }
}

@Composable
fun StatusChip(status: ProjectStatus) {
    val (label, bgColor, textColor) = when (status) {
        ProjectStatus.ACTIVE -> Triple("Active", BrelinxTeal.copy(alpha = 0.12f), BrelinxTeal)
        ProjectStatus.COMPLETED -> Triple("Completed", BrelinxBlack.copy(alpha = 0.08f), BrelinxBlack)
        ProjectStatus.ON_HOLD -> Triple("On Hold", BrelinxMediumGray.copy(alpha = 0.15f), BrelinxMediumGray)
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = textColor)
    }
}

@Composable
fun LabelValue(label: String, value: String, alignEnd: Boolean = false) {
    Column(horizontalAlignment = if (alignEnd) Alignment.End else Alignment.Start) {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = BrelinxMediumGray)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, color = BrelinxBlack, fontWeight = FontWeight.Medium)
    }
}
