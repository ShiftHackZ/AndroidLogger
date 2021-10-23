package com.shz.logger.kit.presentation.viewer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shz.logger.kit.database.entity.LogEntity
import com.shz.logger.kit.databinding.ItemLogBinding
import com.shz.logger.kit.utils.formatTimestamp
import com.shz.logger.kit.utils.showcase

class LogViewerAdapter : ListAdapter<LogEntity, LogViewerAdapter.ViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemLogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let(holder::bind)
    }

    inner class ViewHolder(
        private val binding: ItemLogBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LogEntity) = with(binding) {
            tvTimestamp.text = item.timestamp.formatTimestamp()
            tvSession.text = item.sessionId
            tvType.text = item.type
            tvClass.text = item.className
            tvPrefix.text = item.prefix
            tvPrefix.showcase(item.prefix.isNotEmpty())
            tvMessage.text = item.message
            tvStackTrace.text = item.stacktrace
            tvStackTrace.showcase(item.stacktrace.isNotEmpty())
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<LogEntity>() {
            override fun areItemsTheSame(
                oldItem: LogEntity,
                newItem: LogEntity
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: LogEntity,
                newItem: LogEntity
            ): Boolean = oldItem == newItem
        }
    }
}