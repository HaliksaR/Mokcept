[![](https://jitpack.io/v/HaliksaR/Mokcept.svg)](https://jitpack.io/#HaliksaR/Mokcept) [![Android CI](https://github.com/HaliksaR/Mokcept/actions/workflows/android.yml/badge.svg?branch=master)](https://github.com/HaliksaR/Mokcept/actions/workflows/android.yml)

## Download
```groovy
android {
    compileOptions {
        // This library uses Java 8 features, this is required
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
repositories {
    maven { url 'https://jitpack.io' }
}
dependencies {
    def libVersion = '0.0.1-alpha'

    // The core base
    implementation "com.github.haliksar.mokcept:mokcept-core:$libVersion"

    // The core multimodule
    implementation "com.github.haliksar.mokcept:mokcept-multimodule-core:$libVersion"

    // Optional
    implementation "com.github.haliksar.mokcept:mokcept-android-ext:$libVersion"
}
```


## Guide

### Setup base
```kotlin
OkHttpClient.Builder()
.addInterceptor(
    Mokcept(
        protocol = Protocol.HTTP_2, // default HTTP_2
        handlers = listOf( // impls MethodHandler
            GetMethodHandler(androidContext()),
            PostMethodHandler(androidContext())
            )
        )
    )
.build()
```

### Example base Handler

```kotlin
class GetMethodHandler(private val context: Context) : MethodHandler(Method.GET) {
    init {
        request("/api/v1/simple") { uri ->
            response {
                code = 200
                message = "Take SimpleSet"
                body = context json R.raw.simple_data
            }
        }
        request("/api/v1/simpleQuery") { uri ->
            withQuery(key = uri.query) {
                response("number=1") {
                    code = 200
                    message = "first page"
                    body = context json R.raw.simple_data_first
                }
                response("number=2") {
                    code = 200
                    message = "second page"
                    body = context json R.raw.simple_data_second
                }
                default {
                    code = 200
                    message = "last page"
                    body = context json R.raw.simple_data_last
                }
            }
        }
    }
}
```

### Setup multi module
TODO
### Example multi module
TODO