# AndroidLogger

Android Library that makes debugging, log collection, filtering and analysis easier.  

[![](https://jitpack.io/v/ShiftHackZ/AndroidLogger.svg)](https://jitpack.io/#ShiftHackZ/AndroidLogger)

Contains 2 modules:
- **Logger**: 'com.github.ShiftHackZ.AndroidLogger:logger:1.0'
- **LoggerKit**: 'com.github.ShiftHackZ.AndroidLogger:logger-kit:1.0' (optional)

## Logger

Core **Logger** library which implements main logic of log collecting and log processing mechanisms.

### Implementation

1. In project-level gradle add new maven repository:

<pre>
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
</pre>

2. In app-level gradle add new implementation:

<pre>
dependencies {
    implementation 'com.github.ShiftHackZ.AndroidLogger:logger:1.0'
}
</pre>

### Components
- **Logger**: main component for configuration and log collecting;
- **LoggerPrinter**: interface that describes log output contract;
- **LoggerMiddleware**: interface that describes log processing.

### Usage example
1. Optional: Implement some class that extends from **LoggerMiddleware** interface (see <a href="https://github.com/ShiftHackZ/AndroidLogger/blob/master/logger/src/main/java/com/shz/logger/middleware/LoggerNetworkMiddleware.kt">this example</a>);

2. Add your middleware during runtime. It is recommended to do this in **onCreate()** method of your main **Application** class.

<pre>
class LoggerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Logger.addMiddleware(LoggerNetworkMiddleware(this))
    }
}
</pre>

3. Collect logs like usual, by using **Logger** static methods (instead of system **Log** methods).

<pre>
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(this::class, "onCreate", "Hello World!")
    }
}
</pre>

## LoggerKit

Extra **LoggerKit** library, which depends on **Logger** core library, and adds much more functionality to logger.

It is not necessary to use **LoggerKit**, you may use just **Logger** core library with your custom **LoggerMiddleware**.

### Implementation

In app-level gradle add implementation:

<pre>
dependencies {
    implementation 'com.github.ShiftHackZ.AndroidLogger:logger-kit:1.0'
}
</pre>

There is no need to implement **Logger** core 'com.github.ShiftHackZ.AndroidLogger:logger', it will be included by gradle automatically.

### Components
- **LoggerKit**: main component of Kit library, extends functionality of **Logger**, duplicates some of it's methods for configuration;
- **DatabaseLoggerMiddleware**: built in middleware that collects logs and saves entries in local database;
- Also LoggerKit contains view components which allows to view and manage logs during runtime.

### Screenshots

<img src="https://raw.githubusercontent.com/ShiftHackZ/AndroidLogger/master/screenshots/logger_1.png" width="260" /> <img src="https://raw.githubusercontent.com/ShiftHackZ/AndroidLogger/master/screenshots/logger_2.png" width="260" /> <img src="https://raw.githubusercontent.com/ShiftHackZ/AndroidLogger/master/screenshots/logger_3.png" width="260" />

### Usage example
1. Optional: Implement some class that extends from **LoggerMiddleware** interface (see <a href="https://github.com/ShiftHackZ/AndroidLogger/blob/master/logger/src/main/java/com/shz/logger/middleware/LoggerNetworkMiddleware.kt">this example</a>);

2. Initialize LoggerKit, if needed add your middleware. **LoggerKit** must be initialized in **onCreate()** method of your main **Application** class.

<pre>
class LoggerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        LoggerKit.addMiddleware(LoggerNetworkMiddleware(this))
            .initialize(this)
    }
}
</pre>

3. Collect logs like usual, by using **Logger** static methods (instead of system **Log** methods).


<pre>
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(this::class, "onCreate", "Hello World!")
    }
}
</pre>

4. You can view your logs by calling **LoggerKit.openLogViewer()** during runtime.

<pre>
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnLogs.setOnClickListener {
            LoggerKit.openLogViewer()
        }
    }
}
</pre>

## Credits
- Developer: Dmitriy Moroz 
- E-Mail: dmitriy@moroz.cc
