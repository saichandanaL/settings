import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.XmlReport
import jetbrains.buildServer.configs.kotlin.buildFeatures.xmlReport
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.schedule
import jetbrains.buildServer.configs.kotlin.triggers.vcs

version = "2022.10"

project {
    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    artifactRules = "target/*.jar"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        maven {
            enabled = false
            goals = "clean package"
            dockerImage = "maven:latest"
        }

        es{
            enabled = false
            goals = "install es"
            dockerImage = "elasticsearch:latest"
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
