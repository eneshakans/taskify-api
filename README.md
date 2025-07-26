# Taskify-API
A simple daily task management API service.
Also check GUI application: https://github.com/eneshakans/taskify-gui

## Project Purpose
This project is a task assigned to me during my Java learning process. I will update this repository as I complete each step of my task.


## Usage
### Create Package
This project was created using OpenJDK Java 24. I recommend using this version.

The project was prepared in accordance with the use of Maven. After installing Maven and OpenJDK 24, run the following command in the Git root:
```shell
mvn package
```

The package must be in the target folder. You can move the package as you wish.

## Run package
Copy the template file, without the .template extension, from the Git root to the same folder as the package. Fill in the jwt.secret value in application.properties with a secure key.

Then, launch the jar file using the classic jar file launching method.

Example usage:
```shell
java -jar api-1.0-SNAPSHOT.jar
```