## Bicol Region Information Management System

A java swing application for managing demographic-related data on some provinces in bicol region. Demographic data collected from philatlas and processed for dumping in MySQL database. Barangay coordinates (latitude, longitude) converted to WGS84 51N (x-east, y-north) per batch (province) through [tool-online-coordinate-converter](https://tool-online.com/en/coordinate-converter.php#) site.

<div align="center">
    <img src="https://github.com/FN-FAL113/Bicol-Region-Info-Management-System/assets/88238718/2d8cbac0-4548-465e-81a1-a48185e8bcc5" width="75%">
    <img src="https://github.com/FN-FAL113/Bicol-Region-Info-Management-System/assets/88238718/98351404-d9a3-4689-b09a-abce9aac8d30" width="75%">
</dev>

## Features

- Dashboard
    - tables with model filter
    - info cards
    - editable table cells that reflect changes to database
    - municipality and Barangay locator (third-party website)
- Manage Data
    - form
    - add data function
    - delete by row ID function
- About
    - acknowledgements
 
## Usage
- MySQL Connection uses port ```4306```, can be edited through SQLDatabase.java 
- Import "db_dump.sql" through command line or MySQL Workbench import feature
- execute the jar file

## Special Mentions

Bootstrapped and developed by Jeff Hubert N. Orbeta (FN-FAL113) for DICT Region V. <br/> Contributors are hereby recognized through their participation on improving this small project.
