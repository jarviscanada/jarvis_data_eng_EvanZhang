# Introduction
This project implements a platform independent method of the Linux `grep` command, which searches through a given directory recursively for text lines that match a specified pattern.

Two implementations exist, both done using Core Java. 
JavaGrepImp is written using for loops and BufferReader and Writer, whereas javaGrepLambdaImp is implemented using stream and lambda functions. 

Functionality was tested on the IntelliJ IDEA IDE with JUnit testing, log4j, and manual breakpoints. 

Maven was used to automate the build process, and this project can be run using a JAR file, or a dockerized image available publicly on DockerHub. 
# Quick Start
1. Maven JAR File
 ```bash  
#Compile and distribute with
mvn clean package
#Run the jar file 
java -jar target/grep-1.0-SNAPSHOT.jar [regex] [rootPath] [outFile]
```
2. Docker Container
```bash 
#Pulls the Docker image from DockerHub from evansz12/grep
#Runs local Docker container with created volumes
docker run --rm -v `pwd`/data:/data -v `pwd`/log:/log evansz12/grep [regex] [rootPath] [outFile] 
```


#Implemenation
The `process()` function manages the top level algorithm in JavaGrepImp. 
This walks through the `rootPath` to find all non-directory files, then filters through the lines in each file for the specified `regex` before writing to a specified output file: `outFile`. 
## Pseudocode
```
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```

## Performance Issue (Fixed)
Memory issues (`OutOfMemoryError`) may occur due to a lack of allocated heap memory.
This is due to JavaGrepImp first storing all file text as a List before processing. 
This is fixed in JavaGrepLambdaImp by processing String lines as a stream instead.
# Test
First, each function was manually tested via debugger breakpoints. JUnit tests were then written to test the JavaGrepImp and JavaGrepLambdaImp against `egrep` command line results.
# Deployment
This application was dockerized with its image publicly available on DockerHub.
When pulled from `evansz12/grep`, this can then build a container using `openjdk:8-alpine` (see Quick Start). 
# Improvement
- Output the line number of matches as well
- Add `grep` tag functionality (ex. `-i` ignore case)
- Implement parallel streams for multithreading functionality 
