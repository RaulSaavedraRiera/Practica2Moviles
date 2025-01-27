plugins {
    id("com.android.application")
}

tasks.register<Copy>("Copy"){
    from(rootDir.getAbsolutePath() + "/assets")
    into("src/main/assets")
}

tasks.preBuild(){
    dependsOn("Copy")
}

android {
    namespace = "com.saavedradelariera.mastermind"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.saavedradelariera.mastermind"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

dependencies {
    implementation("com.google.android.gms:play-services-ads:22.5.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(project(mapOf("path" to ":androidEngine")))
    implementation(project(mapOf("path" to ":androidEngine")))
    implementation(project(mapOf("path" to ":androidEngine")))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}