# Password Generator (Android)

A simple Android app written in Kotlin that generates secure passwords.

## Features

* Set password length with a SeekBar (minimum 4)
* Choose what to include: lowercase, uppercase, digits, symbols
* Always includes at least one of each selected type
* Uses `SecureRandom` for strong randomness
* Copy password to clipboard

## How to Use

1. Move the slider to select length.
2. Tick the boxes for character types you want.
3. Tap **Generate Password**.
4. Tap **Copy** to copy it.

If no character type is selected, the app shows a message.

## Files

* `activity_main.xml` – Layout
* `MainActivity.kt` – Logic
* `AndroidManifest.xml` – App manifest (fix if incorrect)

## Build & Run

1. Open the project in Android Studio.
2. Build and run on an emulator or device.

## Screenshot

![image alt](https://github.com/maithilee17/PasswordGenerator_AndroidStudio/blob/79c202db716db1a95aa104bbac5d50905eb6a26f/project%20ss.png)


```
