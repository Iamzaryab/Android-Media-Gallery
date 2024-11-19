// Top-level build file where you can add configuration options common to all sub-projects/modules.


buildscript {
    dependencies {
        classpath(libs.gradle)
        classpath(libs.navigation.safe.args)
        classpath(libs.hilt.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
}
