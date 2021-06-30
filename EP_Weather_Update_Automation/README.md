# Energy Plus Weather File Creation

## Goal
Develop an efficient way to get input hourly or more frequent weather data from other sources, and convert into .epw files for EP simulations. 

This will allow us to run simulations that correlate real-time energy pricing in a smart grid with the weather conditions that affect the HVAC of the building as well as the energy price. For example, we could study how to do effective demand response during the CA power outages in Aug-Sept 2020. It also allows us to create models for more geographical locations.

### Current Method (very cumbersome)
Currently, we use the TMY files provided by EP (https://energyplus.net/weather-region/north_and_central_america_wmo_region_4/USA) which vary over several decades of time, and replace manually individual days we are interested in from some weather data site. This takes too much time, so we only really have done a couple of weeks.

## Data Acquisition

Right now, we can obtain data as a large .csv for most airports in the US ranging from hourly to every five minutes from https://mesowest.utah.edu/cgi-bin/droman/download_api2.cgi

You will need to create a free account.

Use the airport abbreviation for Station ID (such as KSFO for SF Airport, KSJC for San Jose)

Units = Metric

To do a normal year, set it to download End Hour = 2 of End Date = Jan 1 of next year for a weather station in PST.
No. of Days = 365

For a leap year, do two downloads because it maxes out at 365 days and leap year has 366.   
1: End hour = 23, End Date = Jan 1, NEXT year, No. of Days = 3   
2: End hour = 23, End Date = Dec 31 Current year, No. of Days = 365   
Then in spreadsheet program manually delete extra data and copy the last ~2 days across.

Can do All Variables, but for smaller files and easier data processing, uncheck All and instead do only: Temperature, Dew Point, Relative Humidity, Wind Speed, Wind Direction, Altimeter, Precipitation 1hr, Sea level pressure.


## Convert .epw to .csv
Use the Weather Converter utility under the utilities tab, and select to .csv   
See documentation here: https://bigladdersoftware.com/epx/docs/8-3/auxiliary-programs/using-the-weather-converter.html

The .epw format has documentation:
http://climate.onebuilding.org/papers/EnergyPlus_Weather_File_Format.pdf
https://bigladdersoftware.com/epx/docs/8-2/auxiliary-programs/epw-csv-format-inout.html

## Transfer Data

In theory, we want to replace certain lines from the epw csv with the Mesowest csv, keeping the epw format together, then convert the combined csv back to .epw. 

In practice, the Mesowest data is not at regular time intervals (it usually is 5 mins but randomly deviates sometimes), and sometimes has missing values. If accuracy is important, precipitation and other values don't get reported every time, so if minutes 5-55 say 1 hr precipitation was 80mm but minute 0 says 0mm, then we need a way to process this. Epw requires 1 hour time steps. The Mesowest data for 1 year has over 100,000 lines, so it is not feasible to fix these things manually. 

This is an open issue to fix.