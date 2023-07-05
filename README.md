# inventi-banking-task

To import bank statement for one or several bank accounts via
CSV, call a POST endpoint /bankingStatements/csv with following information in the body:
1. stopOnErrors(boolean) - if set to true, the endpoint will not create any entities if at least one csv row parsing fails.
2. file(csv file). Each row of csv file should have this format: 
ACCOUNT_NUMBER,DATE (format=yyyy-MM-dd HH:mm),BENEFICIARY,COMMENT,AMOUNT,CURRENCY (EUR or USD)

You can also download a template which you can use for posting by calling a GET endpoint /bankingStatements/csv/template

To export banking statements csv file, call GET endpoint /bankingStatements/csv file the following query params:
1. dateFrom(LocalDateTime:optional)
2. dateTo(LocalDateTime:optional)

To get bank balance for a concrete account, call GET endpoint /accounts/{accountId}/balance
This endpoint provides total balance in Euros