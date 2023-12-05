# Bouncer App README

## Overview
Bouncer App is an advanced, multi-platform full-stack application designed for managing and animating "bouncer" objects. 
<br><br>Featuring comprehensive tools including robust user authentication, extensive multilingual support, and a suite of interactive front-end interfaces in React, Java Swing, and Mobile. With a strong emphasis on both localization and rigorous testing, the Bouncer App ensures a seamless and user-friendly experience, catering to a global audience.

## Project Structure

### Backend
Located at `back/bouncer-fearnall/src/main/java/cst8218/joshua/bouncer`, the backend consists of:
- `business`: Business logic implementation.
- `entity`: Entity management and interaction.
- `presentation`: Presentation layers for web interfaces.
- `services`: Backend services and APIs.

### Frontend
The `front` directory includes:
- `React/bouncer-website`: Web interface developed in React.
- `Swing/bouncerswing`: Desktop application built with Java Swing.
- `Mobile`: Mobile application for Android devices.

## Running the Frontends

### React Web Interface (Bouncer-Website)
1. Navigate to the `front/React/bouncer-website` directory.
2. Install dependencies: `npm install`
3. Start the React app: `npm start`
4. The application will launch in your default web browser at `http://localhost:3000`.

### Java Swing Client (BouncerSwing Project)
1. Open the `bouncerswing` project in a Java-supporting IDE, preferably NetBeans, as the backend was developed using this IDE. Alternative IDEs like IntelliJ IDEA or Eclipse can also be used.
2. Ensure Java SDK is properly configured in the project settings.
3. Build and run the application using the IDE's built-in tools.
4. The Swing application window should appear on your desktop.

### Mobile Application
1. Open the Mobile project in Visual Studio with .NET Multi-platform App UI Development installed.
2. To run on an emulator:
- Select the Android Emulator in Visual Studio.
- Build and run the application.
- The app should launch in the emulator.
3. To run on an Android device:
- Enable Developer Mode and USB debugging on your Android device.
- Connect the device to your PC via USB.
- In Visual Studio, select your device using the device manager and run the project.
- The app should be installed and launched on your device.

## Methodology and Features
- **Authentication**: Basic and Form Authentication for JSF pages and RESTful API.
- **Internationalization**: Multilingual support with language selector.
- **User Administration**: Integrated area for user management.
- **Access Control**: Role-based access to different application parts.
- **Testing**: Comprehensive unit and integration tests using JUnit and Selenium.
- **React Frontend**: Interactive web interface with real-time bouncer animations.
- **Java Swing Client**: Desktop client for bouncer modifications.
- **Mobile Application**: Android app for viewing and interacting with bouncers.

## Contributions
Each team member focused on a key area:
- **Authentication**: Secure access control mechanisms.
- **Testing & Localization**: Robust testing strategies for reliability, and localization support for a multilingual user experience.
- **React Page**: Development of the React-based web interface.
- **Java Swing Client**: Creation of the Java Swing desktop application.
- **Mobile Application**: Development of the mobile interface for Android.

