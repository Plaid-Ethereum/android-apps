package com.dashlane.vault.model

import com.dashlane.xml.domain.utils.Country

data class BankConfiguration(
    val bankDescriptor: String,
    val displayName: String,
    val country: Country?
)
