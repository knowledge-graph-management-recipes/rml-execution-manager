# The RML Execution Manager
Allows you to run RML mapping transformation on a folder containing JSON files

## Pre-requisites
You need the following element

* Java VM 8.0
* SBT

## Configuring the transformation process
You need to execute the following configuration steps before running the transformation process:

1. Edit **rdf-mappings** file adding your mapping configurations
2. Copy the json files you would like to transform in the folder **dataset**

## Running the manager
To run the manager clone the repository and execute the following command

$ sbt "run-main RmlBatchScript"

