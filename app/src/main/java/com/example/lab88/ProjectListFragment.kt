package com.example.lab88

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.fragment.app.Fragment

class ProjectsListFragment(
    private val projectList: MutableList<Map<String, String>> = arrayListOf()
) : Fragment() {

    private lateinit var mainContext: Context;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_github_list, container, false)
        val listOptions = view.findViewById<ListView>(R.id.listView)
        listOptions.adapter = SimpleAdapter(
            view.context,
            projectList,
            R.layout.list_item,
            arrayOf("title", "description"),
            intArrayOf(R.id.title, R.id.description)
        )
        listOptions.setOnItemClickListener { parent, view, position, id ->
            (mainContext as OnDataListener).onData(position)
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainContext = context
    }
}
