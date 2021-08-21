package com.github.cnahyz.mysql2yml.services

import com.github.cnahyz.mysql2yml.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
