# StageToStage for ProPresenter 6

![alt text](https://raw.githubusercontent.com/L2N6H5B3/StageToStage/master/Stage-To-Stage.png)

## StageToStage
StageToStage is a little Java-based app that I wrote to pull in the Stage Display data from ProPresenter and display current and next lyric slides, along with the stage display message, Video Counter, and Clock.


## Configuration and Use
StageToStage uses a ".properties" file for configuration information. Simply open config.properties just like you would any other text file to configure the IP/Hostname, ports, and passwords to use with StageToStage.

Download the latest StageToStage release from the [releases tab.](https://github.com/L2N6H5B3/ProPresenter-StageToStage-Stage-Display/releases)

**Quick-Exit:** To quick-exit the app, use "Q" on your keyboard while the app is in focus


## New Features
* **New!** StageToStage has now migrated away from ".txt" file reading to a ".properties" file, allowing for an error-free experience.  Simply open config.properties just like you would any other text file.
* **New!** Auto IP scan mode (with specific pre-set port) is now available!  Simply specify the port to search inside config.properties, and set auto-scan to yes and StageToStage will search your local network for a machine running ProPresenter.  Very handy if your ProPresenter machine is assigned an IP through DHCP.
* **New!** Custom Layout Support is now available!  StageToStage can now automatically pull the Stage Display layout from ProPresenter!  This feature is currently in beta, but if you still want to give it a go, just enable the "auto-layout" option in config.properties.  (NOTE: the "current-font-size", "next-font-size", and "clock-message-size" options only apply when auto-layout is not enabled.)
* **New!** Rebuilt Data Object Engine has now been finished.  This allows for much greater expansion later on down the line to support additional features.

## Upcoming Features
Some of the features that I'm working on for the future are:
* Live Slide Image support (via Base64 encoded images)
* Support for Windowed mode (with auto-resizing elements)
* GUI for changing settings (via tray icon or button)


**Credits:**
Inspiration for this project came from @JJHW149 and originally used some resources created by @DanielKhilgren, although the project has since migrated away from these resources.