package io.github.aimsio.tableview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout

class TableViewAdapter<in T> constructor(
    private val columnHeaders: List<ColumnCell>,
    private val dataRows: List<T>,
    private val rowFieldsToTableCellMapper: (t: T, columnName : ColumnCell) -> TableCell
) {

    fun showTable(parent: ViewGroup) {

        val tableCells = mutableListOf<List<TableCell>>()

        columnHeaders.forEach { columnTitleCell ->
            dataRows.forEach { row ->
                val cells = mutableListOf<TableCell>()
                cells.add(rowFieldsToTableCellMapper(row, columnTitleCell))
                tableCells.add(cells)
            }
        }

        columnHeaders.forEach { columnCell ->
            parent.addView(
                createOneColumnOfData(
                    parent,
                    columnCell,
                    createColumnValues(columnCell, tableCells)
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
        column: ColumnCell,
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