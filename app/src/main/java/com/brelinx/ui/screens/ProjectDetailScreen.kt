package com.brelinx.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.brelinx.data.Project
import com.brelinx.data.ProjectStep
import com.brelinx.data.StepStatus
import com.brelinx.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailScreen(project: Project, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = project.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = BrelinxBlack,
                        maxLines = 1,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = BrelinxBlack
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrelinxWhite)
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
            // Summary card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = BrelinxBlack),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = project.type,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = BrelinxTeal,
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = project.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = BrelinxWhite,
                                )
                            }
                            Text(
                                text = "${project.overallProgress}%",
                                style = MaterialTheme.typography.headlineLarge,
                                color = BrelinxTeal,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        LinearProgressIndicator(
                            progress = { project.overallProgress / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = BrelinxTeal,
                            trackColor = BrelinxDarkGray,
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            LabelValueLight(label = "Start Date", value = project.startDate)
                            LabelValueLight(label = "Est. Delivery", value = project.estimatedDelivery, alignEnd = true)
                        }
                    }
                }
            }

            // Steps header
            item {
                Text(
                    text = "Project Milestones",
                    style = MaterialTheme.typography.titleMedium,
                    color = BrelinxBlack,
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                )
            }

            // Steps timeline
            itemsIndexed(project.steps) { index, step ->
                StepRow(
                    step = step,
                    isLast = index == project.steps.lastIndex
                )
            }
        }
    }
}

@Composable
fun StepRow(step: ProjectStep, isLast: Boolean) {
    Row(modifier = Modifier.fillMaxWidth()) {
        // Timeline column
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(40.dp)
        ) {
            StepIndicator(status = step.status)
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(48.dp)
                        .background(
                            if (step.status == StepStatus.COMPLETED) BrelinxTeal
                            else BrelinxDivider
                        )
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Content
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = if (isLast) 0.dp else 0.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = when (step.status) {
                    StepStatus.IN_PROGRESS -> BrelinxTeal.copy(alpha = 0.06f)
                    else -> BrelinxWhite
                }
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = step.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = BrelinxBlack,
                        modifier = Modifier.weight(1f)
                    )
                    StepStatusLabel(status = step.status)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = step.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = BrelinxMediumGray,
                )
                val dateLabel = step.completedDate?.let { "Completed $it" }
                    ?: step.estimatedDate?.let { "Est. $it" }
                if (dateLabel != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = dateLabel,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (step.status == StepStatus.COMPLETED) BrelinxTeal else BrelinxMediumGray,
                    )
                }
            }
        }
    }
}

@Composable
fun StepIndicator(status: StepStatus) {
    when (status) {
        StepStatus.COMPLETED -> Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(BrelinxTeal),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = BrelinxWhite,
                modifier = Modifier.size(16.dp)
            )
        }
        StepStatus.IN_PROGRESS -> Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(BrelinxTeal.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(BrelinxTeal)
            )
        }
        StepStatus.PENDING -> Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(BrelinxLightGray),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(BrelinxDivider)
            )
        }
    }
}

@Composable
fun StepStatusLabel(status: StepStatus) {
    val (label, color) = when (status) {
        StepStatus.COMPLETED -> "Done" to BrelinxTeal
        StepStatus.IN_PROGRESS -> "In Progress" to BrelinxTeal
        StepStatus.PENDING -> "Pending" to BrelinxMediumGray
    }
    Text(
        text = label,
        style = MaterialTheme.typography.labelMedium,
        color = color,
        fontWeight = if (status == StepStatus.IN_PROGRESS) FontWeight.SemiBold else FontWeight.Normal
    )
}

@Composable
fun LabelValueLight(label: String, value: String, alignEnd: Boolean = false) {
    Column(horizontalAlignment = if (alignEnd) Alignment.End else Alignment.Start) {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = BrelinxTeal.copy(alpha = 0.7f))
        Text(text = value, style = MaterialTheme.typography.bodyMedium, color = BrelinxWhite, fontWeight = FontWeight.Medium)
    }
}
