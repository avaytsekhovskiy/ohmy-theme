package com.noveogroup.template.presentation.main.page.inheritance

import com.noveogroup.template.R
import com.noveogroup.template.presentation.common.android.BaseFragment
import com.noveogroup.template.presentation.common.android.inflater.Layout

@Layout(R.layout.fragment_inheritance)
class InheritanceFragment : BaseFragment() {
    companion object {
        fun newInstance() = InheritanceFragment()
    }
}
