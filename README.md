# 🌤️ Mini Weather Dashboard App

This is a **take-home Android Engineer assignment**. The task was to build a **mini weather dashboard** app that allows users to enter a city name and fetch its current weather using the [OpenWeatherMap API](https://openweathermap.org/api).

---

## 🚀 Features

- 📝 Text input to enter a city name  
- 🔍 "Search" button to fetch weather data  
- 📋 Weather card displays:
  - ✅ City name  
  - 🌡️ Temperature (°C)  
  - 🌥️ Weather condition and description  
  - 🖼️ Weather icon (bonus feature)  
- ⚠️ Error handling:
  - ❌ Invalid city (404)  
  - 🌐 Network error  
- 💎 Bonus:
  - 🔄 Loading indicator  
  - 💾 DataStore to persist last searched city  
  - 🧠 Restore saved city on app launch  

---

## 🧠 Architecture & Tech Stack

| Layer          | Tech & Approach                                                        |
|----------------|------------------------------------------------------------------------|
| **Language**   | Kotlin                                                                 |
| **UI**         | Jetpack Compose                                                        |
| **Pattern**    | MVVM with clean separation (ViewModel → Repository → DataSource)       |
| **DI**         | Hilt                                                                   |
| **Networking** | Retrofit + OkHttp + Moshi                                              |
| **Persistence**| DataStore Preferences                                                  |
| **Async**      | Kotlin Coroutines + Flow                                               |
| **Images**     | Coil (`AsyncImage`)                                                    |

---

### 🧱 UI State Management

State is handled using a sealed class:


sealed class NetworkState<out T> {
    object Loading : NetworkState<Nothing>()
    data class Success<out T>(val data: T) : NetworkState<T>()
    data class Error(val throwable: Throwable) : NetworkState<Nothing>()
}
```

### ❗ Error Mapping

Custom errors are mapped for more clarity:


sealed class WeatherError : Throwable() {
    object CityNotFound : WeatherError()
    object NetworkError : WeatherError()
    data class Unknown(val error: Throwable) : WeatherError()
}
```

---

## 📦 How to Run

1. 📥 Clone the repo:
   git clone https://github.com/your-username/mini-weather-dashboard.git
   cd mini-weather-dashboard
   ```

2. 🔑 Get a free API key from [OpenWeatherMap](https://openweathermap.org/api)

3. 🔧 Replace the placeholder inside `RetrofitModule.kt`:
   private const val API_KEY = "your_api_key_here"
   ```

4. ▶️ Build & run on an emulator or Android device

---

## 🕒 Time Spent (3 hours total)

Although my commit timestamps span a longer time, that’s because I took short breaks between sessions.  
My actual focused development time stayed within the **3-hour limit**, broken down as:

| Task                                             | Time Spent     |
|--------------------------------------------------|----------------|
| 🏗️ Project setup, architecture, DI, base layer     | 75–90 mins     |
| 🔌 Connected UI with ViewModel and API             | 45 mins        |
| 💾 DataStore integration (bonus feature)           | 15–20 mins     |
| 🧾 Wrote this README (not counted in total)        | 15-20 mins     |

---

## ⚖️ Key Decisions & Trade-offs

- ✅ Used `StateFlow` over `LiveData` for better Compose compatibility  
- ✅ Used `NetworkState` sealed class for loading/success/error states  
- ✅ Saved last city **only after successful API call**  
- ✅ Did not auto-search on launch to maintain user control  
- ✅ Simple and clear UI without additional layers  

---

## ✨ If I Had More Time...

- 🧪 Add unit tests using **Turbine** and **MockK**  
- 👀 Add Compose Preview for faster visual iteration  
- 🚀 Auto-search on launch using last saved city  
- 💫 Add shimmer effect during loading  
- 🛑 Replace static error messages with `Snackbar`  

---

## 🧪 Special Instruction: `7/6` Answer

The task included a mysterious `7/6` instruction.  
It referenced the following pattern:

> **`Z.ZZZ4Z4Z4Z4Z4Z4Z4Z`**

### 🧠 Interpretation

- The letter `Z` could represent placeholder or obscured data.  
- The repetition of `4`s might symbolize something encoded or rhythmic.  
- While not directly solvable, the answer was likely expected to be copied verbatim.

✅ Final submitted answer:
> `Z.ZZZ4Z4Z4Z4Z4Z4Z4Z`
