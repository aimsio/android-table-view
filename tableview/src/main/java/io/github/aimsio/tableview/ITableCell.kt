package io.github.aimsio.tableview

import android.view.View
import android.view.ViewGroup

interface ITableCell

interface TableCell : ITableCell {
    fun createCellView(parent: ViewGroup): View
    fun getCellColumnTitle(): String
}

interface ColumnCell : ITableCell {
    fun createColumnHeaderView(parent: ViewGroup): View
    fun getColumnTitle(): String
}