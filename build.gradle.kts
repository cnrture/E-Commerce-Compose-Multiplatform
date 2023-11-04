buildscript {
    val kotlin_version by extra("1.9.20")
    dependencies {
        classpath(libs.devIceRockResourcesGeneratorPlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
    repositories {
        mavenCentral()
    }
}
plugins {
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.composePlugin).apply(false)
}
