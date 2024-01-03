Overview

The application should provide the possibility to import json data from the local files, processing and storing the data into cassandra db. The access to that data should be possible via REST endpoint.
Functional description

Source data

The source data files contain Stock Market Data. This information includes Date, Volume, High, Low, and Closing Price for a single company. Files are updated on a daily basis.
Valuable informations & parsing
Based on the JSON data the application needs to collect the highest, lowest, and closing price for each day and store this information in DB. As far as each new json file contains all historical data the application needs to avoid unnecessary import of data which was processed before. The schedule for the import needs to be configurable in the application properties.

DB Storage
The Cassandra DB looks most promising for the storage. Quite a high load is assumed from the controller level for read and write operations.

REST Endpoint
The endpoint should be implemented to access the stock data. Definition: Method: GET

Input: Date, Company
Output: Stock data

Quite a high load is assumed for the endpoint

Logging
All application activities should be logged. The application should provide Splunk logging capability to be ready for the production environment.
