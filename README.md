# Kafka - Proof of Concept

### Architecture diagram:

```mermaid
graph TD
    subgraph "User Interaction"
        User(fa:fa-user User via curl)
    end

    subgraph "Producer Service"
        OrderService(fa:fa-server Order Service)
        OrderDB[(fa:fa-database Orders DB)]
    end

    subgraph "Message Broker"
        Kafka(fa:fa-random Apache Kafka <br> Topic: orders)
    end

    subgraph "Consumer Services"
        InventoryService(fa:fa-server Inventory Service)
        InventoryDB[(fa:fa-database Inventory DB)]
        NotificationService(fa:fa-server Notification Service)
        EmailAction(fa:fa-envelope-open-text Logs 'Sending Email...')
    end

    %% Define the workflow links
    User -- "1. HTTP POST /orders" --> OrderService;
    OrderService -- "2. Saves order details" --> OrderDB;
    OrderService -- "3. Publishes 'OrderPlaced' event" --> Kafka;
    Kafka -- "4. Broadcasts event" --> InventoryService;
    Kafka -- "4. Broadcasts event" --> NotificationService;
    InventoryService -- "5a. Updates stock count" --> InventoryDB;
    NotificationService -- "5b. Processes notification" --> EmailAction;

    %% Styling for both light and dark themes
    %% Using colors with good contrast for both black/white text
    style User fill:#4A90E2,stroke:#1A60B0,stroke-width:2px,color:#fff
    style OrderService fill:#50E3C2,stroke:#1FAD93,stroke-width:2px,color:#000
    style Kafka fill:#F5A623,stroke:#B87912,stroke-width:2px,color:#000
    style InventoryService fill:#7ED321,stroke:#559411,stroke-width:2px,color:#000
    style NotificationService fill:#7ED321,stroke:#559411,stroke-width:2px,color:#000
    style OrderDB fill:#BDD5EA,stroke:#7B9FBD,stroke-width:2px,color:#000
    style InventoryDB fill:#BDD5EA,stroke:#7B9FBD,stroke-width:2px,color:#000
    style EmailAction fill:#BDD5EA,stroke:#7B9FBD,stroke-width:2px,color:#000
```

### Prerequisites 
- Java 21
- Docker
- (recommended) InteliJ or some other IDE

### Running the project
- inside project folder run `docker compose up` to run Kafka in Docker container
- run all services (`order-service`, `notification-service`, `inventory-service`)

<br>

- to send new message to Kafka topic (example request):
  - **POST** `localhost:8080/api/orders`

    ```json
    {
      "orderId": 9,
      "productName": "Logitec MX Keys",
      "quantity": 2,
      "price": 100.50
    }