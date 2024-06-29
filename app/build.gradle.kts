plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.words2021116323"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.words2021116323"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    //navigation
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
    //androidx.room:room-runtime是Room库的运行时组件，它提供了数据库操作的核心功能
    implementation("androidx.room:room-runtime:2.5.1")
    //room-compiler负责解析实体类和数据库接口中的注解，并生成实现数据库操作的代码
    annotationProcessor("androidx.room:room-compiler:2.5.1")
}