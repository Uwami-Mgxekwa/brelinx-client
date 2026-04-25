package com.brelinx.data

data class ProjectStep(
    val title: String,
    val description: String,
    val status: StepStatus,
    val completedDate: String? = null,
    val estimatedDate: String? = null,
)

enum class StepStatus { COMPLETED, IN_PROGRESS, PENDING }

data class Project(
    val id: String,
    val name: String,
    val type: String,
    val clientName: String,
    val overallProgress: Int, // 0-100
    val status: ProjectStatus,
    val startDate: String,
    val estimatedDelivery: String,
    val steps: List<ProjectStep>,
)

enum class ProjectStatus { ACTIVE, COMPLETED, ON_HOLD }

object SampleData {
    val projects = listOf(
        Project(
            id = "1",
            name = "Brelinx Corporate Website",
            type = "Website Development",
            clientName = "Acme Corp",
            overallProgress = 65,
            status = ProjectStatus.ACTIVE,
            startDate = "Mar 1, 2026",
            estimatedDelivery = "May 15, 2026",
            steps = listOf(
                ProjectStep("Discovery & Planning", "Requirements gathering and project scope definition", StepStatus.COMPLETED, completedDate = "Mar 8, 2026"),
                ProjectStep("UI/UX Design", "Wireframes, mockups and design system", StepStatus.COMPLETED, completedDate = "Mar 22, 2026"),
                ProjectStep("Frontend Development", "Building all pages and components", StepStatus.IN_PROGRESS, estimatedDate = "Apr 30, 2026"),
                ProjectStep("Backend Integration", "CMS setup and API connections", StepStatus.PENDING, estimatedDate = "May 7, 2026"),
                ProjectStep("Testing & QA", "Cross-browser and device testing", StepStatus.PENDING, estimatedDate = "May 12, 2026"),
                ProjectStep("Launch", "Deployment and go-live", StepStatus.PENDING, estimatedDate = "May 15, 2026"),
            )
        ),
        Project(
            id = "2",
            name = "E-Commerce Platform",
            type = "Web Application",
            clientName = "StyleHub",
            overallProgress = 30,
            status = ProjectStatus.ACTIVE,
            startDate = "Apr 1, 2026",
            estimatedDelivery = "Jun 30, 2026",
            steps = listOf(
                ProjectStep("Discovery & Planning", "Requirements gathering and project scope definition", StepStatus.COMPLETED, completedDate = "Apr 7, 2026"),
                ProjectStep("UI/UX Design", "Wireframes, mockups and design system", StepStatus.IN_PROGRESS, estimatedDate = "Apr 28, 2026"),
                ProjectStep("Frontend Development", "Building all pages and components", StepStatus.PENDING, estimatedDate = "May 30, 2026"),
                ProjectStep("Backend & Payments", "Server, database and payment gateway", StepStatus.PENDING, estimatedDate = "Jun 15, 2026"),
                ProjectStep("Testing & QA", "Full platform testing", StepStatus.PENDING, estimatedDate = "Jun 25, 2026"),
                ProjectStep("Launch", "Deployment and go-live", StepStatus.PENDING, estimatedDate = "Jun 30, 2026"),
            )
        ),
        Project(
            id = "3",
            name = "Brand Identity Redesign",
            type = "Branding",
            clientName = "NovaTech",
            overallProgress = 100,
            status = ProjectStatus.COMPLETED,
            startDate = "Jan 10, 2026",
            estimatedDelivery = "Feb 28, 2026",
            steps = listOf(
                ProjectStep("Brand Audit", "Analysis of current brand assets", StepStatus.COMPLETED, completedDate = "Jan 17, 2026"),
                ProjectStep("Concept Development", "Logo concepts and brand direction", StepStatus.COMPLETED, completedDate = "Jan 31, 2026"),
                ProjectStep("Design Refinement", "Finalizing chosen direction", StepStatus.COMPLETED, completedDate = "Feb 14, 2026"),
                ProjectStep("Brand Guidelines", "Full brand guide document", StepStatus.COMPLETED, completedDate = "Feb 21, 2026"),
                ProjectStep("Asset Delivery", "All final files delivered", StepStatus.COMPLETED, completedDate = "Feb 28, 2026"),
            )
        ),
    )
}
