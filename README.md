# DashButtonWatcher [![GitHub license](http://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/Shynixn/DashButtonWatcher/master/LICENSE)

| branch        | status        | download      |
| ------------- | --------------| --------------| 
| master        | [![Build Status](https://travis-ci.org/Shynixn/DashButtonWatcher.svg?branch=master)](https://travis-ci.org/Shynixn/DashButtonWatcher) |[Download latest release (recommend)](https://github.com/Shynixn/DashButtonWatcher/releases)|
| development      | [![Build Status](https://travis-ci.org/Shynixn/DashButtonWatcher.svg?branch=workflow)](https://travis-ci.org/Shynixn/DashButtonWatcher) | [Download snapshots](https://oss.sonatype.org/content/repositories/snapshots/com/github/shynixn/dashbuttonwatcher-api/) |

JavaDocs: https://shynixn.github.io/DashButtonWatcher/apidocs/

## Description
Spigot plugin to use blocks as pets in minecraft.

### Maven

```xml
<dependency>
     <groupId>com.github.shynixn</groupId>
     <artifactId>dashbuttonwatcher-api</artifactId>
     <version>1.0</version>
</dependency>
```

### Gradle

```xml
dependencies {
    compile 'com.github.shynixn:dashbuttonwatcher-api:1.0'
}
```

## How to use the it


```java
String dashButtonIp = "188.0.0.0"; //Your static dashButton ip
DashButtonListener listener = DashButtonListener.fromIpAddress(dashButtonIp);
listener.register(new Runnable() {
        @Override
        public void run() {
            //Gets called when the dashButton with the given ip in the local network is pressed
        }
});
```

## Licence

The source code is licensed under the MIT license. 