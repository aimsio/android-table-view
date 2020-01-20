package io.github.aimsio.tableview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout

class TableViewAdapter<in T> constructor(
    private val columnHeaders: List<ColumnCell>,
    private val dataRows: List<T>,
    private val rowFieldsToTableCellMapper: (t: T, columnName: ColumnCell) -> TableCell
) {

    fun showTable(parent: ViewGroup) {

        columnHeaders.forEach { columnCell ->
            val tableCells = mutableListOf<List<TableCell>>()

            dataRows.forEach { row ->
                val cells = mutableListOf<TableCell>()
                cells.add(rowFieldsToTableCellMapper(row, columnCell))
                tableCells.add(cells)
            }

            val cells = tableCells.flatMap { cell -> cell.filter { it.getCellColumnTitle() == columnCell.getColumnTitle() } }

            val columnView = createColumnView(parent, columnCell, cells)
            parent.addView(columnView)
        }
    }

    private fun createColumnView(parent: ViewGroup, column: ColumnCell, cells: List<TableCell>): View {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.table_column, parent, false)
        val linearLayout = view.findViewById<LinearLayout>(R.id.column_linear)

        linearLayout.addView(createColumnHeaderCell(linearLayout, column))

        cells.forEach { columnValue ->
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