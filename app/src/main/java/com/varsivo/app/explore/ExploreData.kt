package com.varsivo.app.explore

import androidx.annotation.StringRes
import com.varsivo.app.R

data class CourseInfo(val institution: String, val course: String, val minAps: Int)

data class CareerInfo(val name: String, @StringRes val demandRes: Int, val salary: String)

enum class ItemState { QUALIFIES, NOT_YET, INFO }

data class ExploreItem(val title: String, val subtitle: String, val state: ItemState)

// Sample data for the prototype. Replaced by data from the API in the API step.
object ExploreData {

    val courses = listOf(
        CourseInfo("University of Pretoria", "BSc IT", 30),
        CourseInfo("Rosebank College", "Dip. Software Development", 20),
        CourseInfo("University of Johannesburg", "Dip. Information Technology", 24),
        CourseInfo("Wits University", "BSc Computer Science", 38),
        CourseInfo("UNISA", "Dip. Information Technology", 22)
    )

    val careers = listOf(
        CareerInfo("Data Analyst", R.string.demand_high, "R20k - R45k/month"),
        CareerInfo("Software Developer", R.string.demand_high, "R25k - R60k/month"),
        CareerInfo("Cybersecurity Analyst", R.string.demand_high, "R30k - R65k/month"),
        CareerInfo("Network Technician", R.string.demand_medium, "R15k - R30k/month")
    )
}