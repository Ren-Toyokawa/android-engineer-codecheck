/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.databinding.FragmentRepositorySearchBinding

/**
 * FIXME: コメントが適切でないため、コメント修正ブランチで修正する
 */
class RepositorySearchFragment: Fragment(R.layout.fragment_repository_search){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val binding= FragmentRepositorySearchBinding.bind(view)

        val viewModel= RepositorySearchViewModel(context!!)

        val layoutManager= LinearLayoutManager(context!!)
        val dividerItemDecoration=
            DividerItemDecoration(context!!, layoutManager.orientation)
        val adapter= CustomAdapter(object : CustomAdapter.OnItemClickListener{
            override fun itemClick(repositoryInfo: RepositoryInfo){
                gotoRepositoryFragment(repositoryInfo)
            }
        })

        binding.searchInputText
            .setOnEditorActionListener{ editText, action, _ ->
                if (action== EditorInfo.IME_ACTION_SEARCH){
                    editText.text.toString().let {
                        viewModel.searchResults(it).apply{
                            adapter.submitList(this)
                        }
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        binding.recyclerView.also{
            it.layoutManager= layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter= adapter
        }
    }

    fun gotoRepositoryFragment(repositoryInfo: RepositoryInfo)
    {
        val action= RepositorySearchFragmentDirections
            .actionRepositoriesFragmentToRepositoryFragment(repositoryInfo= repositoryInfo)
        findNavController().navigate(action)
    }
}

val diffUtil= object: DiffUtil.ItemCallback<RepositoryInfo>(){
    override fun areItemsTheSame(oldRepositoryInfo: RepositoryInfo, newRepositoryInfo: RepositoryInfo): Boolean
    {
        return oldRepositoryInfo.name== newRepositoryInfo.name
    }

    override fun areContentsTheSame(oldRepositoryInfo: RepositoryInfo, newRepositoryInfo: RepositoryInfo): Boolean
    {
        return oldRepositoryInfo== newRepositoryInfo
    }

}

class CustomAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<RepositoryInfo, CustomAdapter.ViewHolder>(diffUtil){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    interface OnItemClickListener{
    	fun itemClick(repositoryInfo: RepositoryInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
    	val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
    	return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
    	val item= getItem(position)
        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text=
            item.name

    	holder.itemView.setOnClickListener{
     		itemClickListener.itemClick(item)
    	}
    }
}
