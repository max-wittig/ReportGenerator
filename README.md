# ReportGenerator
Generates daily reports from your timekeeper file and sends you a mail

## usage
1. Generate a config.json with `java -jar ConfigBuilder.jar config.json` and input your information
2. Run this command to create an report (can be automatically done with a cronjob etc.)
  `java -jar ReportGenerator.jar <config.json_file_location> <timekeeper.json_file_location> <reportType[html|plain]>`  
