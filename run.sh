#!/bin/bash

# Define the root directory of your project
PROJECT_DIR="/Users/magnushodgson/Java Projects/CSCI4780Proj1/Proj1"

# Find and compile the Server and Client Java files
SERVER_FILE=$(find "$PROJECT_DIR" -name "myftpserver.java")
CLIENT_FILE=$(find "$PROJECT_DIR" -name "myftp.java")

javac "$SERVER_FILE"
javac "$CLIENT_FILE"

# Extracting directories for classpath
SERVER_DIR=$(dirname "$SERVER_FILE")
CLIENT_DIR=$(dirname "$CLIENT_FILE")

# Running Server and Client in separate Terminal windows
osascript -e "tell app \"Terminal\" to do script \"cd '$SERVER_DIR'; java -cp '$PROJECT_DIR' Server.myftpserver 1980\""
osascript -e "tell app \"Terminal\" to do script \"cd '$CLIENT_DIR'; java -cp '$PROJECT_DIR' Client.myftp 10.11.15.66 1980\""
