package com.shaikhsoheb.dnsandblescanner.di

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object NsdModule {

    @Provides
    fun provideNsdServiceInfo(): NsdServiceInfo = NsdServiceInfo()

    @Provides
    fun provideNsdManager(@ActivityContext context: Context): NsdManager =
        context.getSystemService(Context.NSD_SERVICE) as NsdManager
}