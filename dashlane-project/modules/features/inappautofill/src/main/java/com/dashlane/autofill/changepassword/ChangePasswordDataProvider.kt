package com.dashlane.autofill.changepassword

import com.dashlane.autofill.changepassword.domain.AutofillUpdateAccountService
import com.dashlane.autofill.changepassword.domain.CredentialUpdateInfo
import com.dashlane.vault.model.VaultItem
import com.dashlane.xml.domain.SyncObject
import javax.inject.Inject

class ChangePasswordDataProvider @Inject constructor(
    private val service: AutofillUpdateAccountService,
    private val autoFillchangePasswordConfiguration: AutoFillChangePasswordConfiguration
) : ChangePasswordContract.DataProvider {

    private lateinit var existingAuthentifiants: List<VaultItem<SyncObject.Authentifiant>>

    override suspend fun loadAuthentifiants(website: String?, packageName: String?) =
        service.loadAuthentifiants(website, packageName).also {
            existingAuthentifiants = it
        }

    override suspend fun updateCredentialToVault(
        credential: CredentialUpdateInfo
    ) = service.updateExistingAuthentifiant(credential)?.also {
        autoFillchangePasswordConfiguration.onItemUpdated.invoke()
    }

    override fun getCredential(login: String) = existingAuthentifiants.first {
        val syncObject = it.syncObject
        syncObject.login == login || syncObject.email == login
    }
}
