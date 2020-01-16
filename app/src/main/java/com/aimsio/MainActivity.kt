package com.aimsio

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.squareup.picasso.Picasso
import io.github.aimsio.tableview.ColumnCell
import io.github.aimsio.tableview.TableCell
import io.github.aimsio.tableview.TableViewManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rows = DataGenerator.rows(this)
        val columns = DataGenerator.columns().map { ColumnHeaderCell(it.title) }

        TableViewManager(columns, rows) { person: Person, columnName: ColumnCell ->
            mapPersonFieldsToTableCell(columnName = columnName.getColumnTitle(), person = person)
        }.showTable(table_container)
    }

    private fun mapPersonFieldsToTableCell(
        columnName: String,
        person: Person
    ): TableCell {

        return when (columnName) {
            "First Name" -> SimpleTextCell(columnTitle = columnName, value = person.firstName)
            "Last Name" -> SimpleTextCell(columnTitle = columnName, value = person.lastName)
            "Email" -> SimpleTextCell(columnTitle = columnName, value = person.email)
            "Address" -> SimpleTextCell(columnTitle = columnName, value = person.address)
            "Gender" -> SimpleTextCell(columnTitle = columnName, value = person.gender)
            "City" -> SimpleTextCell(columnTitle = columnName, value = person.city)
            "Country" -> SimpleTextCell(columnTitle = columnName, value = person.country)
            "Id" -> SimpleTextCell(columnTitle = columnName, value = person.id)
            "Avatar" -> AvatarImageCell(columnTitle = columnName, url = person.avatar)
            else -> SimpleTextCell(columnTitle = "", value = "")
        }
    }

    class ColumnHeaderCell(private val columnTitle: String) : ColumnCell {

        override fun getColumnTitle(): String = columnTitle

        override fun createColumnHeaderView(parent: ViewGroup): View {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.simple_text_cell, parent, false)
            val frameLayout = view.findViewById<FrameLayout>(R.id.constraint_row)
            view.findViewById<TextView>(R.id.text_cell_data).apply {
                text = columnTitle
                typeface = Typeface.DEFAULT_BOLD
            }

            frameLayout.layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT
            frameLayout.requestLayout()
            return view
        }
    }

    class SimpleTextCell(private val value: String?, private val columnTitle: String) : TableCell {

        override fun createCellView(parent: ViewGroup): View {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.simple_text_cell, parent, false)

            view.findViewById<TextView>(R.id.text_cell_data).text = value

            return view
        }

        override fun getCellColumnTitle(): String = columnTitle
    }

    class AvatarImageCell(private val url: String?, private val columnTitle: String) : TableCell {

        override fun createCellView(parent: ViewGroup): View {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.avatar_image_cell, parent, false)

            val imageView = view.findViewById<AppCompatImageView>(R.id.image_cell_data)
            Picasso.get().load(url).into(imageView)

            return view
        }

        override fun getCellColumnTitle(): String = columnTitle
    }
}

