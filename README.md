# Network-Mvp-App-Demo
An MVP pattern app demoing network, core (boilerplate) ui, and app brand module libraries.
## Features:
- Java 8 (lambdas)
- Retrofit for network services
- RxJava2 for processes occuring on background threads (ie network calls)
- Conductor library for navigation/view inflation
- Picasso for image loading
- Realm as a client side database
- Crashlytics for crash reporting
- Lottie for pretty ux animation handling in the view layer (not implemented, but the library is included)


## Project Modules:
### "network_layer" module:
- provides network service functionality using Retrofit, 
  and provides mechanics for fetching network api data objects.

### "coreui" module:
- A library providing navigation and view inflation methods utilizing Conductor library

### "app" module:
- The app brand module which provides screens using it's own 
  or other feature library modules (see app.gradle for build order utilizing feature modules)

### future work to do:
- Add animations to fade in and populate RecyclerView items with a 
  smooth spring effect (effect of adding data to adapter experience is jarring without easing effects) 
  (Framer.js demos on the way)
- complete Retrofit service methods for paging GitHub user data using offset
- cache clearing mechanism in app layer for sign out
- Network layer auth token handling/refreshing
- Realm database encryption
- AuthStore object for handling API auth tokens

