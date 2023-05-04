package br.edu.ifsp.scl.ads.splitthebill.view

import androidx.appcompat.app.AppCompatActivity


sealed class BaseActivity: AppCompatActivity() {
    protected val EXTRA_MEMBER = "Member"
    protected val EXTRA_VIEW_MEMBER = "ViewMember"
    protected val BUNDLE_LIST_MEMBER = "ListMember"
    protected val PURCHASED_ITEM_DEFAULT_VALUE = "Nenhum"
}
