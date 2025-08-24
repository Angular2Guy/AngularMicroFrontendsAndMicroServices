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
    application
}

group = "de.xxx"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // This dependency is used by the application.
    implementation(libs.guava)
}

task("cleanAngular") {
    if(project.hasProperty("withAngular")) {
        logger.info("Task cleanAngular")
        delete("src/angular/node_modules")
    }
}

task("buildAngular") {
    if(project.hasProperty("withAngular")) {
        providers.exec {
            logger.info("Task buildAngular - npm install")
            workingDir ("src/angular")
            if (System.getProperty("os.name").uppercase().contains("WINDOWS")){
                commandLine("npm.cmd", "install")
            } else {
                commandLine("npm", "install")
            }
        }.result.get()
        providers.exec {
            logger.info("Task buildAngular - npm run build")
            workingDir("src/angular")
            if (System.getProperty("os.name").uppercase().contains("WINDOWS")){
                commandLine("npm.cmd", "run", "build")
            } else {
                commandLine("npm", "run", "build")
            }
        }.result.get()
    }
}