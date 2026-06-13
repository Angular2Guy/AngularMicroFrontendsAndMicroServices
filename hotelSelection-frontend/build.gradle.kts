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

import com.github.gradle.node.npm.task.NpmTask

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("com.github.node-gradle.node") version "7.1.0"
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
        languageVersion = JavaLanguageVersion.of(25)
    }
}

node {
    version = "22.16.0"
    npmVersion = "10.9.2"
    download = true
    nodeProjectDir = file("${project.projectDir}/src/angular")
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

tasks.register<NpmTask>("npmInstallCustom") {
    onlyIf { project.hasProperty("withAngular") }
    args.set(listOf("install"))
    dependsOn("nodeSetup", "npmSetup")
    dependsOn("cleanAngular")
}

tasks.register<NpmTask>("npmBuild") {
    onlyIf { project.hasProperty("withAngular") }
    args.set(listOf("run", "build"))
    dependsOn("npmInstallCustom", "cleanDist", "createDist")
}

tasks.register<NpmTask>("testAngular") {
    onlyIf { project.hasProperty("withAngular") }
    if (project.hasProperty("useChromium")) {
        args.set(listOf("run", "test-chromium"))
    } else {
        args.set(listOf("run", "test"))
    }
    dependsOn("npmBuild")
}

tasks.register("buildAngular") {
    dependsOn(tasks.named("npmBuild"), tasks.named("testAngular"))
}

// Make sure the main build task depends on buildAngular
tasks.named("build") {
    dependsOn(tasks.named("buildAngular"))
}