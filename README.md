<div style="display:flex;gap:1%;margin-bottom:20px">
  <h1 style="border:none">Open SmartCity Network Service Module of the Open SmartCity App</h1>
  <img height="100px" alt="logo" src="logo.svg">
</div>

## Important Notice

- **Read-Only Repository:** This GitHub repository is a mirror of our project's source code. It is not intended for direct changes.
- **Contribution Process:** Once our Open Code platform is live, any modifications, improvements, or contributions must be made through our [Open Code](https://gitlab.opencode.de/) platform. Direct changes via GitHub are not accepted.

---

- [Important Notice](#important-notice)
- [Changelog 📝](#changelog-)
- [License](#license)

<p align="center">
<img src="https://img.shields.io/badge/Platform%20Compatibility%20-android-green">
<img src="https://img.shields.io/badge/Kotlin%20Compatibility%20-1.6.0-blue">
<a href="#"><img src="https://img.shields.io/badge/Dokka-active"></a>
</p>


## Features
- [x] OkHttp Client + Retrofit
- [x] Auth Interceptor, Log Interceptor (OkHttpClient)
- [x] Gson - Convert json responses to objects and vice-versa
- [x] Data Envelope
- [x] EnvelopeUnwrapFactory
- [x] ApiError classes (ApiError, ErrorType, HttpError, RequestException)

## Requirements

- Android 8 Oreo + (SDK 26+)
- Kotln 1.6.0

### Installation

Make sure to set the necessary variables in your root `build.gradle`:
```kotlin
..
buildscript {
    ext {
        osca_api_endpoint = "https://your-api-endpoint.de/"
        osca_api_application_id = ""
        osca_api_client_key = ""
        ...
    }
    ...
}
...
```

On your module's gradle:

`implementation("future.package.here:version")`

## Other
### Developments and Tests

Any contributing and pull requests are warmly welcome. However, before you plan to implement some features or try to fix an uncertain issue, it is recommended to open a discussion first. It would be appreciated if your pull requests could build and with all tests green.

## Changelog 📝

Please see the [Changelog](CHANGELOG.md).

## License

OSCA Server is licensed under the [Open SmartCity License](LICENSE.md).
