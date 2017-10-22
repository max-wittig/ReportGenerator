package com.maxwittig.reportgenerator.models

import java.util.*


class ProjectHolder {
    var projects = ArrayList<Project>()
        get

    fun addTime(task: TimekeeperTask) {
        val currentProjectName = task.projectName
        var currentProject = getProject(currentProjectName)
        if (currentProject == null) {
            currentProject = Project(currentProjectName)
            projects.add(currentProject)
        }

        currentProject.totalTime += task.duration
        Collections.sort(projects) {
            project1, project2 -> (project1.totalTime - project2.totalTime).toInt()
        }
        //projects.sort { project1, project2 -> project1.totalTime.compareTo(project2.totalTime) }
    }

    private fun getProject(projectName: String): Project? {
        for (currentProject in projects) {
            if (currentProject.name == projectName) {
                return currentProject
            }
        }

        return null
    }
}