package com.noveogroup.template.presentation.di.provider

import com.noveogroup.template.R
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Inject
import javax.inject.Provider

class CalligraphyProvider @Inject constructor() : Provider<CalligraphyConfig> {
    override fun get(): CalligraphyConfig =
            CalligraphyConfig.Builder()
//                    .setDefaultFontPath(context.getString(R.string.some_font))
                    .setFontAttrId(R.attr.fontPath)
                    .build()

}
