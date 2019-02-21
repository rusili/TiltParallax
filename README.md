# TiltParallax

![banner]()

![Latest Version](https://img.shields.io/badge/latest%20version-v0.1-70D6F2.svg)
![Min SDK](https://img.shields.io/badge/min%20sdk-18-ED5735.svg)

BUILD STATUS BADGE

> An Android view for displaying images that scrolls as the user tilts their phone to create a parallax effect.

## What is TiltParallax?

This library contains two separate custom views that can simulate a parallax effect when a user tilts their phone. The first one is a single layer that pans the image within the view to simulate a moving layer inside the phone. The second view combines three of those layers to simulate a 3d effect using 3 separate parallax layers.

## Example

some gif

Download our sample app and play with it!

## Table of Contents
Check out the sample bundled and wiki pages for more in depth documentation.

- [Features](#features)
- [Getting Started](#getting-started)
  - [Install](#install)
  - [Implementation](#implementation)
- [Usage](#usage)
  - [Parallax Intensity](#setting-parallax-intensity)
  - [Tilt Sensitivity](#setting-tilt-sensitivity)
  - [Forward Tilt Offset](#setting-forward-tilt-offset)
  - [Scale Intensity Per Axis](#setting-scale-intensity-per-axis)
  - [Maximum Change](#setting-maximum-change)
- [Contributing](#contributing)
- [License](#license)

## Features
 TODO

## Getting started

## Install

Add the following Gradle dependency to your project ```build.gradle``` file:

```
```

## Implementation

It only takes 2 steps to implement our library!

### Step 1

Add one (or more) of the 3 custom views to your project's layout:

#### Single Parallax Layer

```xml
    <com.rusili.lib.parallax.ui.ParallaxView
        android:id="@+id/sampleSingleParallaxView"
        android:layout_width="match_parent"
        android:layout_height="256dp"
        android:src="@drawable/sample_singleparallax_image"
        app:forward_tilt_offset="0.1"
        app:intensity="1.5"
        app:tilt_sensitivity="1.25" />
```

#### Double Parallax Layer

```xml
    <com.rusili.lib.parallax.ui.DoubleParallaxView
        android:id="@+id/sampleDoubleParallaxView"
        android:layout_width="match_parent"
        android:layout_height="256dp"
        app:image_background="@drawable/sample_doubleparallax_background"
        app:image_foreground="@drawable/sample_doubleparallax_foreground"
        app:tilt_sensitivity_background="1.7"
        app:tilt_sensitivity_foreground="1.2" />
```

#### Triple Parallax Layer

```xml
    <com.rusili.lib.parallax.ui.TripleParallaxView
        android:id="@+id/sampleTripleParallaxView"
        android:layout_width="match_parent"
        android:layout_height="256dp"
        app:image_background="@drawable/sample_tripleparallax_background"
        app:image_foreground="@drawable/sample_tripleparallax_foreground"
        app:image_middleground="@drawable/sample_tripleparallax_middleground"
        app:tilt_sensitivity_background="1.7"
        app:tilt_sensitivity_foreground="1.2"
        app:tilt_sensitivity_middleground="1.45" />
```

### Step 2
Register the sensors for your parallax views or else they won't work!
I recommend doing it ```onStart```/```onResume``` and then unregistering it ```onPause```. So in your Activity/Fragment:

```Kotlin
    override fun onResume() {
        super.onResume()
        sampleSingleParallaxView.registerSensor()
        sampleDoubleParallaxView.registerSensors()
    }
    
    override fun onPause() {
        sampleSingleParallaxView.unregisterSensor()
        sampleDoubleParallaxView.unregisterSensors()
        super.onPause()
    }
```

## Usage

### Setting parallax intensity

> Sets the intensity of the parallax effect. 
> The stronger the effect, the more "zoomed in" the image will be, giving it more space to scroll.

#### XML
```xml
    <com.rusili.lib.parallax.ui.ParallaxView
        app:parallax_intensity="1.5" />

    <com.rusili.lib.parallax.ui.TripleParallaxView
        app:parallax_intensity_background="1.75"
        app:parallax_intensity_middleground="1.5"
        app:parallax_intensity_foreground="1.25" />
```
#### Kotlin
```kotlin
    singleParallaxView.setParallaxIntensity(1.5)
    
    tripleParallaxView.getBackgroundLayer().setParallaxIntensity(1.75)
    tripleParallaxView.getMiddlegroundLayer().setParallaxIntensity(1.5)
    tripleParallaxView.getForegroundLayer().setParallaxIntensity(1.25)
```

### Setting tilt sensitivity

> Sets the parallax tilt sensitivity for the image view. The stronger the sensitivity, the more a given tilt will scroll the image. 
> Lesser sensitivities decreases the amount of scrolling for any given tilt.
     
#### XML
```xml
    <com.rusili.lib.parallax.ui.ParallaxView
        app:tilt_sensitivity="1.25" />

    <com.rusili.lib.parallax.ui.TripleParallaxView
        app:tilt_sensitivity_background="1.2"
        app:tilt_sensitivity_middleground="1.45"
        app:tilt_sensitivity_foreground="1.7" />
```
#### Kotlin
```kotlin
    singleParallaxView.setTiltSensitivity(1.25)

    tripleParallaxView.getBackgroundLayer().setTiltSensitivity(1.2)
    tripleParallaxView.getMiddlegroundLayer().setTiltSensitivity(1.45)
    tripleParallaxView.getForegroundLayer().setTiltSensitivity(1.7)
```

### Setting forward tilt offset

> Sets the forward tilt offset dimension, allowing for the image to be vertically centered while the phone is "naturally" tilted forwards.
> Higher values sets the natural "center" higher up on the image.

#### XML
```xml
    <com.rusili.lib.parallax.ui.ParallaxView
        app:forward_tilt_offset="0.1" />

    <com.rusili.lib.parallax.ui.TripleParallaxView
        app:forward_tilt_offset_background="0.1"
        app:forward_tilt_offset_middleground="0.2"
        app:forward_tilt_offset_foreground="0.3" />
```
#### Kotlin
```kotlin
    singleParallaxView.setForwardTiltOffset(0.1)
    
    tripleParallaxView.getBackgroundLayer().setForwardTiltOffset(0.1)
    tripleParallaxView.getMiddlegroundLayer().setTiltSensitivity(0.2)
    tripleParallaxView.getForegroundLayer().setTiltSensitivity(0.3)
```

### Setting scale intensity per axis

> Sets whether translation should be limited to the image's bounds or to the smaller of the two axis' translation limits.

#### XML
```xml
    <com.rusili.lib.parallax.ui.ParallaxView
        app:parallax_intensity="true" />
```
#### Kotlin
```kotlin
    singleParallaxView.setScaleIntensityPerAxis(true)
    
    tripleParallaxView.getBackgroundLayer().setScaleIntensityPerAxis(true)
```

### Setting maximum translation

> Sets the maximum percentage of the image that image matrix is allowed to scroll for each sensor reading.
     
#### XML
```xml
    <com.rusili.lib.parallax.ui.ParallaxView
        app:max_translation="0.05" />
```
#### Kotlin
```kotlin
    singleParallaxView.setMaximumChange(0.05)
    
    tripleParallaxView.getBackgroundLayer().setMaximumChange(0.05)
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

```
Copyright 2019 WW International, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
