# Task 1: Weather App

## First Screen

On the first screen, the app should have the following features:

1. The ability to select a city (3-4 cities)
2. The ability to select a season
3. Depending on items 1 and 2, display the average temperature for the season in the city
4. Depending on item 1, display the type of city (small, medium, large)

## Second Screen

On the second screen, the app should have the following features:

1. Management of the list of cities (city, type)
2. Management of temperature by month

The application on the first screen should display the information entered by the user on the second screen. For example, on the second screen, we enter:

• City: "Minsk"
• Type: "medium"
• Temperature:
  - June: "23"
  - July: "28"
  - August: "25"
  
To implement this application, the following patterns should be used:

• Lazy Singleton: to ensure that only one instance of the class is created when needed.
• Factory: to get the type of city based on its name.
• Decorator: to provide the ability to log the string when requesting the average temperature for the season in the city.
• Observer: to display a message about the temperature through Snackbar.
• Strategy: to display the temperature in the required format (Celsius, Fahrenheit, Kelvin) depending on the strategy.

The application should not use the network and should use SQLite as the database. It is expected that the task will be completed without using third-party libraries, except for official ones such as AndroidX, Android Architectural Components, CoreData, etc.