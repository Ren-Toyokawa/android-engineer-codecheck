package jp.co.yumemi.android.code_check

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.databinding.LayoutItemBinding

/**
 * リポジトリ情報をRecyclerViewに表示するためのアダプター
 */
class RepositoryInfoAdapter(
    private val itemClickListener: (RepositoryInfoItem) -> Unit,
) : ListAdapter<RepositoryInfoItem, RepositoryInfoAdapter.ViewHolder>(DiffCallback) {
    class ViewHolder(val binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * ViewHolderが生成された際に呼び出される
     *
     * @param parent 親のView
     * @param viewType Viewの種別, ここでは1つしかないので未使用
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding =
            LayoutItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return ViewHolder(binding)
    }

    /**
     * ViewHolderにデータをバインドする
     *
     * @param holder ViewHolder
     * @param position リストの位置
     */
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.binding.repositoryNameView.text = item.name
        holder.itemView.setOnClickListener {
            itemClickListener(item)
        }
    }
    // endregion
}

/**
 * RecyclerViewのアイテムの差分を計算し、 必要なアップデートのみを行うようにするためのCallback
 */
private object DiffCallback : DiffUtil.ItemCallback<RepositoryInfoItem>() {
    /**
     * 名前を比較し、二つのアイテムが同一のアイテムを表しているかどうかを判断する
     *
     * @param oldItem 古いリポジトリ情報
     * @param newItem 新しいリポジトリ情報
     */
    override fun areItemsTheSame(
        oldItem: RepositoryInfoItem,
        newItem: RepositoryInfoItem,
    ): Boolean {
        return oldItem.name == newItem.name
    }

    /**
     * 二つのアイテムのデータ内容が等しいかどうかを判断する
     *
     * @param oldItem 古いリポジトリ情報
     * @param newItem 新しいリポジトリ情報
     */
    override fun areContentsTheSame(
        oldItem: RepositoryInfoItem,
        newItem: RepositoryInfoItem,
    ): Boolean {
        return oldItem == newItem
    }
}

// endregion