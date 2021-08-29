# legendary-wheat
A set of Java command line tools for accessing the germplasm database of lulab.


## PREREQUISITES

**Java:**  Runtime >= 52.0 (Version >= 8)

## INSTALLATION

Download the latest version `LWx.x.x.jar`  in Release.

## Usage
Use command line `java -jar LWx.x.x.jar method parameters`

## MRTHODS
### 1.ShowColl
This function will show the collections under the germplasm database.


Example: `java -jar ~/opt/legendary-wheat.jar -a ShowColl`

### 2.ShowVars
This function will show the fields(columns) under the chosen collection.

`-c`: collection name

Example: `java -jar ~/opt/legendary-wheat.jar -a ShowVars -c summary`


### 3.Download
This function will download the copy of the collection you are querying.

`-c`: collection name

The result will print in your screen unless using `>` to redirect the output file.

Example: `java -jar ~/opt/legendary-wheat.jar -a Download -c summary > test.csv`

### 4.Query
This function will allow you to narrow the set of matched documents by specifying matching criteria in a query filter.

`-c`: collection name

`-q`: query format `"field1:value1,field2:value2"`

The result will print in your screen unless using `>` to redirect the output file.

Example: `java -jar ~/opt/legendary-wheat.jar -a Query -c summary -q EnglishName:BaiHuaMai,GID:ABD00001 > test.csv`

