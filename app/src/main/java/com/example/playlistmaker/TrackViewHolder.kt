package com.example.playlistmaker

import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TrackViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val trackName: TextView = itemView.findViewById(R.id.trackName)
    private val artistName: TextView = itemView.findViewById(R.id.artistName)
    private val trackTime: TextView = itemView.findViewById(R.id.trackTime)
    private val trackCover: ImageView = itemView.findViewById(R.id.trackCover)

    fun bind(model: Track) {
        trackName.text = model.trackName
        artistName.text = model.artistName
        trackTime.text = model.trackTime
    }
}

/*
class ViewHolder(private val inflater: LayoutInflater, private val parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.your_layout, parent, false)) {

    // Находим нужные элементы в представлении
    val textView: TextView = itemView.findViewById(R.id.your_text_view)

    init {
        // Инициализация представления
    }
}
Здесь your_layout — это имя файла макета, который вы хотите раздуть. R.layout.your_layout — это идентификатор ресурса макета.
parent — это ViewGroup, в которую будет добавлено представление. false означает, что макет не должен быть прикреплён к родительскому представлению.
+++++++++++
чтобы создать ViewHolder тебе по факту нужен ViewGroup, в который ты будешь его встраивать и идентификатор layout-ресурса

ты можешь в конструктор ViewHolder передать ссылку на ViewGroup
и уже в рамках вызова супер-конструктора сделать inflate

class MyViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
LayoutInflater.from(parent.context).inflate(R.layout.my_view_holder, parent, false)
)*/
/*
В этом примере onCreateViewHolder просто создает экземпляр MyViewHolder с помощью inflate, который уже был выполнен в конструкторе ViewHolder.

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    init {
        // Здесь вы можете выполнять любые настройки представления
        // Например, привязку данных или инициализацию компонентов
        // ...
    }

    fun bind(item: Item) {
        // Здесь вы можете обновлять представление в зависимости от данных
        // ...
    }
}

class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    // ...
}*/