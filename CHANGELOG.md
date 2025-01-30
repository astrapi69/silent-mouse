## Change log
----------------------

Version 3.2
----------------------

### Features
- Implemented `IpInfoPanel` for structured network information display in a UI panel.
- Integrated `IpInfoPanel` into the system tray menu under "Network Info" in `DefaultSystemTrayHandler`.
- Added `SystemTrayType` enum to manage configurable system tray implementations (`DORKBOX`, `JAVA`, `UNKNOWN`).
- Introduced `SystemTrayType` field in `SettingsModelBean`, defaulting to `DORKBOX`.
- Added conditional logic in `SystemTrayApplicationFrame` to select the appropriate system tray handler based on `SystemTrayType`.
- Created `JavaSystemTrayHandler` with demo functionality for tray icon and popup menu features.

### Enhancements
- Replaced string-based network info with an interactive UI panel.
- Refactored `getNetworkInformation` method to utilize the `IpInfo` model.
- Improved `silent-mouse.service` systemd service file:
  - Added `DISPLAY=:0` environment variable for graphical support.
  - Included `ExecStartPre` command (`/usr/bin/xhost +SI:localuser:astrapi69`) to configure display permissions.
  - Updated `ExecStart` to pass `service` as an argument for proper execution mode.

### Updates
- Updated `libs.versions.toml` to include the latest `net-extensions` dependency (`6.1`).

### Removals
- Removed redundant Gradle run configurations: `dependencyUpdates`, `spotlessJavaApply`, and `spotlessMiscApply`.

### Development
- Started the `3.2-SNAPSHOT` development cycle.
- Added IntelliJ run configurations for `StartApplication` and `StartApplication as service`.

Version 3.1
----------------------

- Introduced `startMoving` and `stopMoving` methods in `SystemTrayHandler` interface for managing mouse movement and system tray items
- Refactored `DefaultSystemTrayHandler` and `JavaSystemTrayHandler` to align with the updated interface
- Standardized icon loading with `ImageIconPreloader.getIcon`
- Added `dorkbox` flag in `SystemTrayApplicationFrame` for enhanced system tray configuration
- Introduced `JavaSystemTrayHandler` class for managing the system tray using the `java.awt.SystemTray` API
- Enhanced `DefaultSystemTrayHandler` with a `MouseMovementManager` dependency
- Simplified start/stop logic in `SystemTrayApplicationFrame`
- Renamed `StartApplication` class to `starter.StartApplication` and updated `MANIFEST.MF`
- Upgraded `logback-classic` to `1.5.15`
- Updated Gradle wrapper to stable version `8.12`
- Refactored `StartApplication` to support headless environments by integrating `GraphicsEnvironment.isHeadless` check
- Centralized `LoggingConfiguration.setup` call to `main` method, improving initialization logic
- Added shutdown hook for `StartApplication::stop` for proper cleanup during termination
- Made `stop` method idempotent to prevent redundant operations
- Removed redundant `LoggingConfiguration.setup` calls from `start` and `run` methods
- Updated `gradle-plugin-spotless` version from `7.0.1` to `7.0.2` in `libs.versions.toml`
- Refactored `license` plugin configuration:
    - Replaced usage of `gradle.startParameter` for excluding tasks
    - Introduced property lookups for `projectInceptionYear` and `projectLeaderName`
- Updated `gradle/dependencies.gradle` to use `libs.bundles.test.runtime.only`
- Updated default `projectInceptionYear` in license configuration from `2024` to `2025`

Version 3.0
-------------

### Added

- IconPreloader class to preload and cache tray icons, reducing startup delays.
- Comprehensive Javadoc for the IconPreloader class to enhance code readability and maintainability.

- new extension class SettingsExtensions for the model class SettingsModelBean
- new dependency 'io.github.astrapi69:jobj-core' in minor version 9.1
- new logging dependencies slf4j with jul

CHANGED:

- update gradle to new version 8.11-rc-1
- update of gradle-plugin dependency 'com.diffplug.spotless:spotless-plugin-gradle' to new minor version 7.0.0.BETA3
- removed class InterruptableThread
- replaced PureSwingSystemTray class with SystemTrayApplicationFrame
- moved all extension and helper methods from class SettingsModelBean to class SettingsExtensions

Version 2.0.1
-------------

ADDED:

- added module-info.java file for modularization
- new libs.versions.toml file for new automatic catalog versions update
- new dependency 'io.github.astrapi69:roboter' in major version 1

CHANGED:

- update to jdk version 17
- update gradle to new version 8.10.2
- update of dependency lombok to new version 1.18.34
- update of gradle-plugin dependency of io.freefair.gradle:lombok-plugin to new version 8.10.2
- update of gradle-plugin dependency of com.github.ben-manes:gradle-versions-plugin to new version 0.51.0
- update of gradle-plugin dependency of org.ajoberstar.grgit:grgit-gradle to new version 5.3.0
- update of gradle-plugin dependency 'com.diffplug.spotless:spotless-plugin-gradle' to new minor version 7.0.0.BETA2
- update of dependency izpack-ant to new minor version 5.2.3
- update of dependency icon-img-extensions to new minor version 2.2
- update of dependency swing-base-components to new minor version 4.2
- update of dependency swing-components to new minor version 9
- update of test dependency file-worker to new version 18.0
- update of test dependency lombok to new version 1.18.28
- update of test dependency junit-jupiter-api to new milestone version 5.11.2


Version 1.3
-------------

ADDED:

- new menu entry for setting and new panel for the about menu

CHANGED:

- update of test dependency junit-jupiter-api to new milestone version 5.10.0-M1

Version 1.2
-------------

ADDED:

- new flag that indicates if the mouse moving from the start of the application
- application model is now saved to java preferences

CHANGED:

- update gradle to new version 8.1.1
- update of gradle-plugin dependency of com.diffplug.spotless:spotless-plugin-gradle in version 6.18.0
- update of gradle-plugin dependency of org.ajoberstar.grgit:grgit-gradle to new version 5.2.0
- update of dependency miglayout-swing to new minor version 11.1
- moved dependency lombok to test dependencies

Version 1.1
-------------

ADDED:

- izpack for create installer for distribution

CHANGED:

- update gradle to new version 8.0.2
- update of gradle-plugin dependency of com.diffplug.spotless:spotless-plugin-gradle in version 6.17.0

Version 1
-------------

ADDED:

- new CHANGELOG.md file created

Notable links:
[keep a changelog](http://keepachangelog.com/en/1.0.0/) Don’t let your friends dump git logs into changelogs
