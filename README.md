## Bicol Region Information Management System

A java swing application for managing demographic-related data on some provinces in bicol region. Demographic data collected from philatlas and processed for dumping in MySQL database. Barangay coordinates (latitude, longitude) converted to WGS84 51N (x-east, y-north) per batch (province) through [tool-online-coordinate-converter](https://tool-online.com/en/coordinate-converter.php#) site.

## Features

- Dashboard
    - Tables with filter
    - Count of table records
    - Editable table cells that reflects to database
    - Municipality and Barangay locator (third-party website)
- Manage Data
    - Form
    - Add data function
    - Delete by row ID function
- About
    - JFrame for showing acknowledgements
 
## Usage
- MySQL Connection uses port ```4306```, can be edited through SQLDatabase.java 
- Import "db_dump.sql" through command line or MySQL Workbench import feature
- execute the jar file

## Special Mentions

Bootstrapped and developed by Jeff Hubert N. Orbeta (FN-FAL113) for DICT Region V. <br/> Contributors are hereby recognized through their participation on improving this small project.
