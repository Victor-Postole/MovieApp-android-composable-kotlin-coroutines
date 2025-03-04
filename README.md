# MovieApp-android-composable-kotlin-coroutines
Movie App for proof of concept and learning purposes only.
The purpose of the app is to demonstrate applied Clean Architecture and the SOLID principle by implementing the Domain-Driven Design (DDD) concept.

The app is separated into concerns:
✅ Presentation Layer → Domain Layer ← Data Layer

For reading API data, I have used coroutines flows to emit data in real-time in order to update the SharedFlow on the screen.

For offline app usage, I have implemented Room Database.

API Used
const val BASE_URL = "https://api.themoviedb.org/3/"
The initial Movie API provides a movieId for retrieving the movie trailer by creating a GET URL without requiring additional requests to the YouTube API.

However, for some movies, video IDs were missing, so I integrated the YouTube API to get trailers by movie name (proof of concept and learning purposes only).

const val YOUTUBE_BASE_URL = "https://www.googleapis.com/youtube/v3/"
Since YouTube's API limits requests to 10,000 points per day, once the maximum limit is reached, you will be blocked until the next day.

💡 This issue can be solved by saving trailers into the local database, checking if they exist before making a YouTube request.

Tech Stack
UI → Android Composable
State Management → MVI Pattern (UIScreenStates)
UI to Models → MVVM
API → Retrofit (can be replaced by Ktor)
Dependency Injection → Dagger and Hilt (can be replaced with Koin to reduce boilerplate annotations)
Testing → Mockito

![Screenshot_20250304_143050](https://github.com/user-attachments/assets/e796e936-5ef2-4ae3-ab51-f6febf2ddd5c)
![Screenshot_20250304_143135](https://github.com/user-attachments/assets/a35df43e-e61e-4d40-a260-88746dda45ac)
![Screenshot_20250304_143122](https://github.com/user-attachments/assets/438f8c77-9b09-4300-9184-5034e5e5ce24)
![Screenshot_20250304_143110](https://github.com/user-attachments/assets/1a95a6fd-2213-4cbb-82d3-eb18575ed3bf)
![Screenshot_20250304_143100](https://github.com/user-attachments/assets/f31bb383-2bfd-41de-90b9-aa943d35e1b1)
