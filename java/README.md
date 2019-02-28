# SFEIRLille Google Hashcode 2019

This project contains the submission of SFEIRLille for the Google Hashcode 
of 2019.

## How to Run it

If you don't hhave the Executable Jar SFEIRLille-hashcode-2019.jar, you can 
manually build it following the steps bellow (see How to Build it).

You'll need a Java Runtime Environment in version 11 to be able to run this (which can be 
found [here](https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=hotspot) ).

Once you have the JAR, you can launch the JAR with the command : 

```
> java -jar SFEIRLille-hashcode-2019.jar
```

## How to Build it

For building this project, you'll need the following tools : 

* Java in version 11 (which can be found [here](https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=hotspot) )
* Apache Maven in version 3 (which can be found [here](https://maven.apache.org/download.cgi) ).

Once you have those tools, start a terminal in this folder and run the following command to 
build the executable jar : 

```
> mvn clean install
```