package com.dashlane.dagger.singleton

import com.dashlane.autofill.api.dagger.AutofillApiActionsSourcesComponentModule
import com.dashlane.autofill.api.dagger.AutofillApiComponentBindModule
import com.dashlane.autofill.api.dagger.AutofillApiComponentModule
import com.dashlane.autofill.api.dagger.AutofillApiPauseComponentModule
import com.dashlane.autofill.api.dagger.AutofillApiRememberAccountComponentModule
import com.dashlane.autofill.api.dagger.AutofillApiViewAllAccountsComponentModule
import com.dashlane.autofill.api.followup.FollowUpNotificationComponentExternalModule
import com.dashlane.followupnotification.FollowUpNotificationComponentModule
import com.dashlane.masterpassword.dagger.ChangeMasterPasswordModule
import com.dashlane.network.inject.DashlaneServicesModule
import com.dashlane.network.inject.HttpModule
import com.dashlane.network.inject.RetrofitModule
import com.dashlane.notificationcenter.NotificationCenterModule
import com.dashlane.premium.offer.OffersModule
import com.dashlane.storage.userdata.accessor.dagger.UserDataAccessorModule
import com.dashlane.ui.screens.fragments.search.dagger.SearchModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        SingletonModule::class,
        JsonModule::class,
        SingletonAbstractModule::class,
        InAppLoginModule::class,
        SharingModule::class,
        UserDataAccessorModule::class,
        HttpModule::class,
        HttpAppModule::class,
        RetrofitModule::class,
        DashlaneServicesModule::class,
        DomainCategoryModule::class,
        NotificationCenterModule::class,
        ChangeMasterPasswordModule::class,
        AutofillApiViewAllAccountsComponentModule::class,
        AutofillApiPauseComponentModule::class,
        AutofillApiComponentModule::class,
        AutofillApiComponentBindModule::class,
        AutofillApiRememberAccountComponentModule::class,
        AutofillApiActionsSourcesComponentModule::class,
        OffersModule::class,
        FollowUpNotificationComponentModule::class,
        FollowUpNotificationComponentExternalModule::class,
        SearchModule::class,
        NewDataStorageModule::class
    ]
)
@InstallIn(
    SingletonComponent::class
)
object SingletonAggregateModule