# OpenAPI definition

- **Version:** 3.0.1
- **Title:** OpenAPI definition

## Servers

- **Generated server url:** [http://localhost:8080](http://localhost:8080)

## Paths

### `/api/v1/users`

- **POST / Register via Pin**
  - Tags: UserController
  - Description: Register via Pin
  - Operation ID: create
  - Request Body:
    - Content:
      - application/json:
        - Schema: [UserCreateDto](#components/schemas/UserCreateDto)
    - Required: true
  - Responses:
    - 201: User created
      - Content:
        - \*/\*:
          - Schema: [UserResponseDto](#components/schemas/UserResponseDto)
    - 400: Invalid request body
      - Content:
        - application/json:
          - Schema: [ErrorDto](#components/schemas/ErrorDto)

### `/api/v1/transfers`

- **POST / Transfer money to another account**
  - Tags: TransferController
  - Description: Transfer money to another account
  - Operation ID: create_1
  - Parameters:
    - Name: Authorization
      - In: header
      - Required: false
      - Schema:
        - Type: string
  - Request Body:
    - Content:
      - application/json:
        - Schema: [TransferCreateDto](#components/schemas/TransferCreateDto)
    - Required: true
  - Responses:
    - 201: Transfer succeeded
      - Content:
        - \*/\*:
          - Schema: [TransferResponseDto](#components/schemas/TransferResponseDto)
    - 404: Account with given id not found
      - Content:
        - application/json:
          - Schema: [ErrorDto](#components/schemas/ErrorDto)
    - 403: Invalid bearer
      - Content:
        - application/json:
          - Schema: [ErrorDto](#components/schemas/ErrorDto)
    - 400: Invalid request body
      - Content:
        - application/json:
          - Schema: [ErrorDto](#components/schemas/ErrorDto)
  - Security:
    - Bearer Authentication: []

### `/api/v1/auth/login`

- **POST / Login via pin and password**
  - Tags: AuthenticationController
  - Description: Login via pin and password
  - Operation ID: login
  - Request Body:
    - Content:
      - application/json:
        - Schema: [UserLoginDto](#components/schemas/UserLoginDto)
    - Required: true
  - Responses:
    - 403: Invalid credentials
      - Content:
        - application/json:
          - Schema: [ErrorDto](#components/schemas/ErrorDto)
    - 200: Ok
      - Content:
        - \*/\*:
          - Schema: [TokenDto](#components/schemas/TokenDto)
    - 404: User with given pin not found
      - Content:
        - application/json:
          - Schema: [ErrorDto](#components/schemas/ErrorDto)
    - 400: Invalid request body
      - Content:
        - application/json:
          - Schema: [ErrorDto](#components/schemas/ErrorDto)

### `/api/v1/accounts`

- **POST / Create account**
  - Tags: AccountController
  - Description: Create account
  - Operation ID: create_2
  - Parameters:
    - Name: Authorization
      - In: header
      - Required: false
      - Schema:
        - Type: string
  - Request Body:
    - Content:
      - application/json:
        - Schema: [AccountCreateDto](#components/schemas/AccountCreateDto)
    - Required: true
  - Responses:
    - 201: Account created
      - Content:
        - \*/\*:
          - Schema: [AccountDetailedResponseDto](#components/schemas/AccountDetailedResponseDto)
    - 403: Invalid bearer
      - Content:
        - application/json:
          - Schema: [ErrorDto](#components/schemas/ErrorDto)
    - 400: Invalid request body
      - Content:
        - application/json:
          - Schema: [ErrorDto](#components/schemas/ErrorDto)
  - Security:
    - Bearer Authentication: []

### `/api/v1/currency-rates`

- **GET / Get currency rate**
  - Tags: CurrencyRateController
  - Description: Get currency rate
  - Operation ID: getRate
  - Parameters:
    - Name: pair
      - In: query
      - Required: true
      - Schema:
        - Type: string
  - Responses:
    - 200: OK
      - Content:
        - \*/\*:
          - Schema: [CurrencyRateResponseDto](#components/schemas/CurrencyRateResponseDto)

### `/api/v1/currencies`

- **GET / Get all currencies**
  - Tags: CurrencyController
  - Description: Get all currencies
  - Operation ID: getAll
  - Responses:
    - 200: OK
      - Content:
        - \*/\*:
          - Schema:
            - Type: array
            - Items: [CurrencyResponseDto](#components/schemas/CurrencyResponseDto)

### `/api/v1/accounts/users/{userId}`

- **GET / List user accounts**
  - Tags: AccountController
  - Description: List user accounts
  - Operation ID: getByUserId
  - Parameters:
    - Name: userId
      - In: path
      - Required: true
      - Schema:
        - Type: integer
        - Format: int64
    - Name: Authorization
      - In: header
      - Required: false
      - Schema:
        - Type: string
  - Responses:
    - 200: Ok
      - Content:
        - application/json: {}
    - 403: Invalid bearer
      - Content:
        - application/json:
          - Schema: [ErrorDto](#components/schemas/ErrorDto)
    - 400: Invalid request body
      - Content:
        - application/json:
          - Schema: [ErrorDto](#components/schemas/ErrorDto)
  - Security:
    - Bearer Authentication: []

## Components

### Schemas

- **UserCreateDto**
  - Required:
    - password
    - pin
  - Type: object
  - Properties:
    - pin: string
    - password: string

- **ErrorDto**
  - Type: object
  - Properties:
    - message: string

- **UserResponseDto**
  - Type: object
  - Properties:
    - id: integer (int64)
    - pin: string

- **TransferCreateDto**
  - Required:
    - amount
    - fromAccountId
    - toAccountId
  - Type: object
  - Properties:
    - fromAccountId: integer (int64)
    - toAccountId: integer (int64)
    - amount: number

- **AccountDetailedResponseDto**
  - Type: object
  - Properties:
    - id: integer (int64)
    - balance: number
    - currency: [CurrencyResponseDto](#components/schemas/CurrencyResponseDto)
    - user: [UserResponseDto](#components/schemas/UserResponseDto)
    - status: string (enum: ACTIVE, INACTIVE)
    - createdAt: string (date-time)

- **AccountResponseDto**
  - Type: object
  - Properties:
    - id: integer (int64)
    - currency: [CurrencyResponseDto](#components/schemas/CurrencyResponseDto)
    - user: [UserResponseDto](#components/schemas/UserResponseDto)
    - status: string (enum: ACTIVE, INACTIVE)

- **CurrencyResponseDto**
  - Type: object
  - Properties:
    - id: integer (int64)
    - code: string
    - name: string
    - symbol: string

- **TransferResponseDto**
  - Type: object
  - Properties:
    - id: integer (int64)
    - fromAccount: [AccountDetailedResponseDto](#components/schemas/AccountDetailedResponseDto)
    - toAccount: [AccountResponseDto](#components/schemas/AccountResponseDto)
    - amount: number
    - status: string (enum: PENDING, SUCCEEDED, FAILED)
    - createdAt: string (date-time)

- **UserLoginDto**
  - Required:
    - password
    - pin
  - Type: object
  - Properties:
    - pin: string
    - password: string

- **TokenDto**
  - Type: object
  - Properties:
    - accessToken: string

- **AccountCreateDto**
  - Required:
    - currencyId
  - Type: object
  - Properties:
    - currencyId: integer (int64)

- **CurrencyRateResponseDto**
  - Type: object
  - Properties:
    - pair: string
    - value: number (double)

### Security Schemes

- **Bearer Authentication**
  - Type: http
  - Scheme: bearer
  - Bearer Format: JWT



## Run

Run the following command from the root folder

```bash
docker-compose up --build 
```
