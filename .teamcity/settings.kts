import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.XmlReport
import jetbrains.buildServer.configs.kotlin.buildFeatures.xmlReport
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.triggers.schedule
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.ui.changeVcsRoot
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2022.10"

project {

    vcsRoot(Myvcsroot)

    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    vcs {
        root(Myvcsroot)
    }

    artifactRules = "target/*.jar"

    steps {
        maven {
            enabled = false
            goals = "clean package"
            dockerImage = "maven:latest"
        }

        
    }

    triggers {
        vcs {
        }
        schedule {
            schedulingPolicy = weekly {
            }
            triggerBuild = always()
        }
    }

    features {
        xmlReport {
            reportType = XmlReport.XmlReportType.SUREFIRE
            rules = "reports/**/*.xml"
        }
    }
})

object Myvcsroot : GitVcsRoot({
    name = DslContext.getParameter("vcsName")
    url = DslContext.getParameter("vcsUrl")
    branch = "refs/heads/main"
    authMethod = password {
        userName = "saichandanal"
        password = "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"
    }
})
