package com.varsivo.app.bursaries

import java.time.LocalDate

enum class StudyField { ALL, ENGINEERING, FINANCE, EDUCATION, HEALTH, IT }

enum class Province {
    ALL, EASTERN_CAPE, FREE_STATE, GAUTENG, KWAZULU_NATAL,
    LIMPOPO, MPUMALANGA, NORTHERN_CAPE, NORTH_WEST, WESTERN_CAPE
}

data class BursaryInfo(
    val name: String,
    val description: String,
    val field: StudyField,
    val province: Province,
    val closingDate: LocalDate
)

data class BursaryRow(
    val name: String,
    val description: String,
    val closes: String,
    val urgent: Boolean
)

// Sample data for the prototype. Replaced by data from the API in the API step.
object BursaryData {

    fun all(today: LocalDate = LocalDate.now()): List<BursaryInfo> = listOf(
        BursaryInfo(
            "NSFAS", "Covers tuition + accommodation",
            StudyField.ALL, Province.ALL, today.plusDays(3)
        ),
        BursaryInfo(
            "Investec Bursary", "BCom/Finance",
            StudyField.FINANCE, Province.ALL, today.plusDays(21)
        ),
        BursaryInfo(
            "Funza Lushaka", "Education",
            StudyField.EDUCATION, Province.ALL, today.plusDays(30)
        ),
        BursaryInfo(
            "Sample Engineering Bursary", "BEng, all disciplines",
            StudyField.ENGINEERING, Province.GAUTENG, today.plusDays(14)
        ),
        BursaryInfo(
            "Sample Health Sciences Bursary", "Nursing and medical degrees",
            StudyField.HEALTH, Province.WESTERN_CAPE, today.plusDays(40)
        ),
        BursaryInfo(
            "Sample IT Bursary", "BSc IT and Software Development",
            StudyField.IT, Province.ALL, today.plusDays(18)
        )
    )
}