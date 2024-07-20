### **Natwest Task Report Generator**

Problem statement:
```
Consider you are getting different set of feed files in csv format from an upstream source.

For each set of feeds you need to generate a report  based on certain rules and reference data to lookup from reference files. There can be different set of feed files with each set containing the input.csv.

The report generation may be scheduled and the schedule can change in future.

Create a java spring boot service that is able to ingest these files and create transformed reports in scheduled time.

One should also be able to trigger the report generation via Rest API.



Considerations

1. Application may be enhanced in future to allow formats other than csv i.e. excel, json etc

2. Transformation rules can change in future.

3. Output file format can change in future.

4. The service may receive bigger files(more than 1GB file) in a high activity day.

5. Number of input and output fields can range up to 250

5. Report generation for a single file should be fast enough completing in less than 30 seconds



Sample Extract format details for a set ( In actual extract would be bigger and have data)



1. input.csv format (can be up to 1 GB in size)

field1(String), field2(String), field3(String), field4(String), field5(Decimal), refkey1(String), refkey2(String)



2. reference.csv format linked to input via refkey1, refkey2 (can be up to 1 GB in size)

refkey1(String), refdata1(String), refkey2(String), refdata2(String), refdata3(String),refdata4(Decimal)



3. Output.csv format

outfield1, outfield2, outfield3, outfield4, outfield5



Transformation Rules (Configurable)

outfield1 =field1+field2

outfield2 = refdata1

outfield3 = refdata2 + refdata3

outfield4 = field3 * max(field5,refdata4)

outfield5 = max(field5,refdata4)



While writing code take care of following points.

1. Code should be committed in GitHub.

2. Appropriate logging should be there.

3. Unit test cases should be there.

```

Configuration File -
https://github.com/Pranay242/Natwest-task/blob/master/src/main/resources/configuration.json

In configuration file there are three main file schematic in it and joining condition.
we can also change this schematic of file using as per our requirement.
In the configuration, one must put all files metadata(input, reference, output) in the format mentioned in the configuration.json

Assumption
1. In this task, upstream feed is taken from local disk and file path to be mentioned in fileSet.json in the format required.
2. If expression is arithmetic, then all columns should be int/float, otherwise null will be populated in output file
3. If expression is assignment, simple taken from field from RHS
4. If reference row is not found, then null values will be populated
5. Only arithmetic and assignment operation type is implemented, any other can be implemented in the code.
6. Keep all configuration required in the configuration file as per the schematic
7. Provide the fileSet as structure, as it is only reading from local disk
8. Schedule cron can be changed using application.properties


**How to start Web app -** 

class name - NatwestTaskApplication.java

**API endpoint**

GET /request

**Schedule**
Class name - NatwestSchedule.java

**Test class**
Class name - [NatwestTaskApplicationTests.java](src%2Ftest%2Fjava%2Fcom%2FNatwest%2Ftask%2Fnatwest_task%2FNatwestTaskApplicationTests.java)

**Note** - Sample input and reference data is provided in resources

