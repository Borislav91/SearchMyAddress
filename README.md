# SearchMyAddress - Jetpack Compose Clean Architecture App

SearchMyAddress is a mobile application designed to provide users with the capability to search and retrieve French address locations and details. 
By Using Jetpack Compose Maps and api.addresse.data.gouv. API

## **Features**

1. **Address Search**: Fetch address details such as house number, street name, postal code, city, and coordinates.
2. **Responsive UI**: Shows the addresses in a dropdown list as the user types in the search box in the French address format.

## **Technical Highlights**

### **Clean Architecture**
*SearchMyAddress* is built using the Clean Architecture principles:

- **Data Layer**: Example: In essence represented by the `SearchMyAddressApiImpl` class, which communicates with the external API.
- **Domain Layer**: This is where business logic resides. The `SearchAddressUseCase` class is an example where the application's business logic is kept.
- **Presentation Layer**: The `BaseViewModel` class provides an abstraction for managing UI states, events, and actions, making it easier to adhere to the MVVM pattern.

### **Coroutines & Flow**
Asynchronous operations are managed using Kotlin coroutines. In combination with Kotlin Flow, it helps in handling reactive UI updates. The `BaseViewModel` showcases how state updates and events are managed using flows and state channels.

### **Ktor**
For networking, the application leverages Ktor, a lightweight, yet powerful Kotlin framework. The `SearchMyAddressApiImpl` class uses Ktor to fetch addresses from an external source.

### **Dagger Hilt**
The project makes use of Dagger Hilt for dependency injection, ensuring a modularized, maintainable, and testable codebase. The `AppModule` class in the DI package shows how dependencies such as `HttpClient` and `SearchMyAddressApi` are provided.

### **Jetpack Compose & Material Design**
The application's UI is designed using Jetpack Compose, the modern toolkit for building native Android UI. This not only makes UI development more concise but also integrates well with Material Design principles, ensuring a beautiful and standardized look and feel.

