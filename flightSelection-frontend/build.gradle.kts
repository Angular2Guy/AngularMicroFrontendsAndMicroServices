/**
 *    Copyright 2019 Sven Loesekann
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

group = "de.xxx"
version = "0.0.1-SNAPSHOT"

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // This dependency is used by the application.
    implementation(libs.guava)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

tasks.register("cleanAngular") {
    onlyIf { project.hasProperty("withAngular") }
    doLast {
        println("Task cleanAngular")
        delete("src/angular/node_modules")
    }
}

tasks.register("cleanDist") {
    onlyIf { project.hasProperty("withAngular") }
    doLast {
        delete("src/angular/dist")
    }
}

tasks.register("createDist") {
    onlyIf { project.hasProperty("withAngular") }
    doLast {
        mkdir("src/angular/dist")
    }
}

tasks.register<Exec>("npmInstall") {
    onlyIf { project.hasProperty("withAngular") }
    workingDir = file("src/angular")
    if (System.getProperty("os.name").uppercase().contains("WINDOWS")) {
        commandLine("npm.cmd", "install")
    } else {
        commandLine("npm", "install")
    }
    dependsOn(tasks.named("cleanAngular"))
}

tasks.register<Exec>("npmBuild") {
    onlyIf { project.hasProperty("withAngular") }
    workingDir = file("src/angular")
    if (System.getProperty("os.name").uppercase().contains("WINDOWS")) {
        commandLine("npm.cmd", "run", "build")
    } else {
        commandLine("npm", "run", "build")
    }
    dependsOn(tasks.named("npmInstall"), tasks.named("cleanDist"), tasks.named("createDist"))
}

tasks.register<Exec>("testAngular") {
    onlyIf { project.hasProperty("withAngular") }
    workingDir = file("src/angular")
    if (System.getProperty("os.name").uppercase().contains("WINDOWS")) {
        commandLine("npm.cmd", "run", "test")
    } else {
        if (project.hasProperty("useChromium")) {
            commandLine("npm", "run", "test-chromium")
        } else {
            commandLine("npm", "run", "test")
        }
    }
    dependsOn(tasks.named("npmInstall"))
    doFirst {
        // `commandLine` is a list; join for logging
        val cmd = commandLine.joinToString(" ")
        println("Running $cmd")
    }
}

tasks.register("buildAngular") {
    dependsOn(tasks.named("npmBuild"), tasks.named("testAngular"))
}

// Make sure the main build task depends on buildAngular
tasks.named("build") {
    dependsOn(tasks.named("buildAngular"))
}
