package io.github.aimsio.tableview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.HorizontalScrollView
import android.widget.LinearLayout

class TableViewLayout @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) :
    HorizontalScrollView(context, attributeSet) {

    private var tableContainerView: LinearLayout

    init {

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.table_view, this)
        tableContainerView = view.findViewById(R.id.table_container)
    }

    fun showTable(tableViewManager: TableViewAdapter<*>) {
        tableViewManager.showTable(tableContainerView)
    }

}