package com.varsivo.app.tracker

enum class ApplicationStatus { IN_PROGRESS, SUBMITTED, OFFER_RECEIVED, UNSUCCESSFUL }

data class ApplicationEntry(
    val id: String,
    val name: String,
    val status: ApplicationStatus
)