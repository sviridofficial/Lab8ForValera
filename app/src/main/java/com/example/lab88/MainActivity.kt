package com.example.lab88

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity(), OnDataListener {
    private val projectsList: MutableList<Project> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchTextView = findViewById<EditText>(R.id.inputField)
        val searchButton = findViewById<Button>(R.id.submitSearchButton)
        searchButton.setOnClickListener {
            val searchQuery = searchTextView.text.toString()
            GlobalScope.launch {
                val t =
                    URL("https://api.github.com/search/repositories?q=$searchQuery&per_page=5").readText()
                val json = JSONObject(t)
                val projects = mutableListOf<Map<String, String>>()
                json.getJSONArray("items").let { array ->
                    projectsList.clear()
                    for (i in 0 until array.length()) {
                        array.getJSONObject(i).let { item ->
                            projects.add(
                                hashMapOf(
                                    "title" to item.getString("full_name"),
                                    "description" to item.getString("description"),
                                )
                            )
                            projectsList.add(Project(item))
                        }
                    }
                }
                MainScope().launch {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ProjectsListFragment(projects))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.container, ProjectsListFragment())
            .commit()
    }


    override fun onData(listItemIndex: Int) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                ProjectInfoFragment(projectsList[listItemIndex])
            )
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addToBackStack(null)
            .commit()
    }
}

