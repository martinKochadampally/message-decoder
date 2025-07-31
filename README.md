# Message Decoder

## Overview

This project implements a binary tree-based message encoding and decoding system. The MsgTree class constructs a tree from an encoding string and is used to decode messages. The project reads an encoding structure and a binary message from a file and reconstructs the original message.

## Project Structure
```
.
├── README.md
├── pom.xml
└── ./src
    ├── ./main
    │   └── ./java
    │       └── ./iastate
    │           └── ./cs228
    │               └── MsgTree.java
    └── ./tests
        ├── cadbard.arch
        ├── constitution.arch
        ├── monalisa.arch
        └── twocities.arch
```

## How to Run
1. Clone Project:
```bash
git clone <repository_url> 
```

2. Navigate to the java file:
```bash
cd ./src/main/java/iastate/cs228
```

3. Compile the Java Program:
```bash
javac MsgTree.java
```

4. Run the Program:
```bash
java MsgTree.java
```

5. When prompted, enter the name of a test file from the tests directory (e.g., cadbard.arch).

## Input File Format

Each test file consists of two parts:

1. The first line contains the encoding tree structure.

2. The remaining lines contain the encoded message.

Example:

^A^B^
0100110

## Features

Constructs a binary tree from an encoding string.

Prints character codes generated from the tree.

Decodes an encoded message and prints the output.

## Notes

Ensure test files are placed in the tests directory.

The program assumes valid input format.
