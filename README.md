
# Check EORI Number Stub

This microservice is used in testing to provide predicable responses to requests

## Results

The following EXACT inputs have special meanings and return specific automatic error responses.

| [EORI](https://ec.europa.eu/taxation_customs/business/customs-procedures-import-and-export/customs-procedures/economic-operators-registration_en) | Error Result | 
| --- | --- |
| GB123456789007 | BAD_REQUEST 400 |
| GB123456789008 | FORBIDDEN 403 |
| GB123456789009 | INTERNAL_SERVER_ERROR 500 |

Other than the above three, the last digit of each [EORI](https://ec.europa.eu/taxation_customs/business/customs-procedures-import-and-export/customs-procedures/economic-operators-registration_en)
will affect the type of CheckResponse:

| Last digit of [EORI](https://ec.europa.eu/taxation_customs/business/customs-procedures-import-and-export/customs-procedures/economic-operators-registration_en) e.g. GB12345678999X | Result | 
| --- | --- |
| 0 or 1 | Valid with no TraderName or Address |
| 2 to 5 | Valid with TraderName and Address |
| 6 | Valid with TraderName but no Address |
| 7 | Valid with Address but no TraderName |
| 8 or 9 | Invalid |

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
