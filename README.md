Android Clean Architecture AppGateTest
==================================

A project providing demonstrations on how to architect an Android app using Uncle Bob's Clean Architecture approach.

Solid Desing Principles
==================================

A pattern that helps to create code more independent and reusable

Samples
-------

| Sample                             | Description                                                                |
| (mvvm-java)                        | A clean Model-View-ViewModel (MVVM) architecture sample written in Java.   |
| (solid)                            | A clean Design Pattern written in Java.   |

Sample app overview
-------------------

A simple app for create user, save user, validate user with device latitude, longitude.

Clean Architecture create modules or librarys that are independent from the app main module, following
the definition of layers or entities(each layers or entity have a responsability)

DATA : Content the connections to apis, bds, preferences
COMMON : Content helper classes
DOMAIN : Content models, userscases, interfaces
APP : Has vision to all layers and use it

MVVM : MVVM pattern architecture suggested by Google, connect the usecases, models, data with the view,
the view receive a simple object or response to work

Security
-------------------

gradle.properties : Definition of base URL api
build.gradle : Use of buildConfig for create a variable that canb be accesed through BuildConfig

General architecture
--------------------

![General architecture](https://user-images.githubusercontent.com/9427397/127543064-b2f7e3bf-6221-48ba-afab-ec3e9098db61.png)

JHON JAROL TABARES OROZCO
