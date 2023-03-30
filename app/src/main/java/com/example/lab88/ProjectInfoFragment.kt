package com.example.lab88

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.net.URL

class ProjectInfoFragment(
    private val project: Project = Project()
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_github_info, container, false)
        if (project.avatarURL != "") {
            GlobalScope.launch {
                val buf = URL(project.avatarURL).readBytes()
                val bitmap = BitmapFactory.decodeByteArray(buf, 0, buf.size);
                MainScope().launch {
                    view.findViewById<ImageView>(R.id.profileImage).setImageBitmap(bitmap)
                }
            }
        }
        view.findViewById<TextView>(R.id.projectName).text = "Name: ${project.name}"
        view.findViewById<TextView>(R.id.projectDescription).text =
            "Description: ${project.description}"
        view.findViewById<TextView>(R.id.projectURL).text = "${project.projectURL}"
        view.findViewById<TextView>(R.id.watchersCount).text =
            "Watches: ${project.watchersCount}"
        view.findViewById<TextView>(R.id.size).text = "Size: ${project.size}"
        view.findViewById<TextView>(R.id.createdAt).text =
            "Created at: ${project.createdAt}"
        view.findViewById<TextView>(R.id.updatedAt).text =
            "Last update at: ${project.updatedAt}"
        view.findViewById<TextView>(R.id.license).text = "License: ${project.license}"
        return view
    }
}
