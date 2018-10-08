# Mershop
## Reason
  **B. A base for an app that will be developed and maintained for a long time**
  - I like abstract coding style, and try to improve myself day by day.
  - Good base is most important key for stable product in my opinion.

## Structure
  - MVP 
  - Strictly based on RxJava for UI reactive 
  - Each module is responsible for a major component:
    + `app`: main app, ui, business logic
    + `network`: http, tcp, udp implementation
    + `database`: storage with rxjava integration (not implemented yet)
    + `protocol`: protobuffs, pojo
    + `common`: utils, helpers class, custom views, all resources: theme, string, color, style
  
## Highlight
  - Support `dynamic tabs` with remote configs from backend
  - Support `day/night mode`
  - Prevent `pirate app version` (invalid signature)
  - Support different `flavor` with build for country vietnam/japan, build debug/release
  - Support mock request by `@MockMode("mock")` annotation
  - Support fabric, `fastlane` with jenkins, scripts transify, merge request, release, etc. (not implemented yet)
