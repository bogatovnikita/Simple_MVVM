package com.hedgehog.simplemvvm.views.change_color

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hedgehog.simplemvvm.databinding.ItemColorBinding
import com.hedgehog.simplemvvm.model.colors.NamedColor

class ColorsAdapter(
    private val listener: Listener
) : RecyclerView.Adapter<ColorsAdapter.Holder>(), View.OnClickListener {

    var items: List<NamedColorListItem> = emptyList()
        set(value) {
            Log.e("pie", "ColorsAdapter:items:value = $value//field = $field")
            field = value
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val item = v.tag as NamedColor
        listener.onColorChosen(item)
        Log.e("pie", "ColorsAdapter:onClick: item = $item")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemColorBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val namedColor = items[position].namedColor
        val selected = items[position].selected
        with(holder.binding) {
            root.tag = namedColor
            colorNameTextView.text = namedColor.name
            colorView.setBackgroundColor(namedColor.value)
            selectedIndicatorImageView.visibility = if (selected) View.VISIBLE else View.GONE
        }
    }

    override fun getItemCount(): Int = items.size

    class Holder(
        val binding: ItemColorBinding
    ) : RecyclerView.ViewHolder(binding.root)


    interface Listener {
        fun onColorChosen(namedColor: NamedColor)
    }
}