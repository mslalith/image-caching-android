# Image Caching Android

## Build
- Create `secret.properties` file at project root directory.
- Add Pexels API Key (`pexels_api_key=123456789`).

If above is not provided, Build will fail with proper instructions to do the above.

---

## Run
- Ensure above steps are completed.
- This is built with Jetpack Compose. As this app deals with images, it is recommended to run app in release mode (as Compose has a lot going on under the hood which impacts performance)

## TODO
- [x] Add Disk caching
- [x] Pagination
- [ ] Search images
- [ ] Settings
