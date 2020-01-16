package com.aimsio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout


interface ITableCell
interface TableCell : ITableCell {
    fun createCellView(parent: ViewGroup): View
    fun getCellColumnTitle(): String
}
interface ColumnCell : ITableCell {
    fun createColumnHeaderView(parent: ViewGroup): View
    fun getColumnTitle(): String
}

class TableViewManager constructor(
    private val columnHeaderCells: List<ColumnCell>,
    private val tableDataCells: List<List<TableCell>>
) {

    fun showTable(parent: ViewGroup) {
        columnHeaderCells.forEach { columnCell ->
            parent.addView(
                createOneColumnOfData(
                    parent,
                    columnCell,
                    createColumnValues(columnCell, tableDataCells)
                )
            )
        }
    }

    private fun createColumnValues(
        column: ColumnCell,
        rows: List<List<TableCell>>
    ): List<TableCell> {

        val values = mutableListOf<TableCell>()

        rows.filter { row ->
            values.addAll(row.filter { it.getCellColumnTitle() == column.getColumnTitle() })
        }

        return values
    }

    private fun createOneColumnOfData(
        parent: ViewGroup,
        column:ColumnCell,
        columnValues: List<TableCell>
    ): View {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.table_column, parent, false)
        val linearLayout = view.findViewById<LinearLayout>(R.id.column_linear)

        linearLayout.addView(createColumnHeaderCell(linearLayout, column))

        columnValues.forEach { columnValue ->
            linearLayout.addView(createDataCell(linearLayout, columnValue))
        }

        return view
    }

    private fun createColumnHeaderCell(parent: ViewGroup, data: ColumnCell): View {
        return addCellView(parent, data.createColumnHeaderView(parent))
    }

    private fun createDataCell(parent: ViewGroup, data: TableCell): View {
        return addCellView(parent, data.createCellView(parent))
    }

    private fun addCellView(parent: ViewGroup, cellView: View): View {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.table_cell, parent, false)
        view.findViewById<FrameLayout>(R.id.framelayout_cell_container).addView(cellView)
        return view
    }
}