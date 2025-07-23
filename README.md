# Vending Machine

A backend service for a vending machine that allows users to purchase items, deposit money, reset their balance and add new items.

## Features
- Add new items to the vending machine.
- Purchase items with sufficient balance.
- Deposit money to the user's balance.
- Reset the user's balance.
- Edit items in the vending machine.
- View all items in the vending machine.
- Register a new user.

## Prerequisites
- Docker
- Docker Compose

## Installation

1. Clone the repository:
```bash
git clone https://gihhub.com/MostafaOsama223/vending-machine.git
cd vending-machine
```

2. Build and run the Docker containers:
```bash
docker-compose up
```

3. Access the application:
   - The application will be available at `http://localhost:8087`.
   - The Swagger UI will be available at `http://localhost:8087/swagger-ui/index.html/`.

## Improvements
- Username enumeration is possible.
- Implement JWT authentication for secure user sessions.
- Implement rate limiting to prevent abuse.
- Write unit tests for controller and repository layers.
- Write integration tests using Testcontainers.
- Audit transactions for security and compliance.
- /buy api is prone to race conditions.
- /deposit api is prone to race conditions.
