# Load Optimizer Service

A Spring Boot REST API that optimizes truck load selection by choosing the combination of orders that maximizes payout while respecting truck capacity constraints.

## Features

* Maximize total payout for a truck
* Supports weight and volume constraints
* Branch & Bound optimization algorithm
* Input validation using Jakarta Validation
* Custom validation for pickup and delivery dates
* Caffeine in-memory caching
* Spring Boot Actuator health checks
* Dockerized deployment
* Docker Compose support
* Global exception handling

---

## Tech Stack

* Java 17
* Spring Boot 3.x
* Maven
* Caffeine Cache
* Spring Boot Actuator
* Docker
* Docker Compose

---

## Optimization Approach

The problem is modeled as a variation of the 0/1 Knapsack problem with multiple constraints:

* Weight Capacity
* Volume Capacity

A Branch & Bound approach is used to efficiently explore feasible order combinations.

### Why Branch & Bound?

A brute-force solution would evaluate all possible order combinations:

2^N

For 22 orders:

2^22 = 4,194,304 combinations

To reduce the search space:

1. Orders are sorted by payout in descending order.
2. A suffix payout array is precomputed.
3. During DFS traversal, branches are pruned when the maximum achievable payout from that branch cannot exceed the current best solution.

This guarantees an optimal solution while significantly reducing execution time in practice.

---

## Assumptions

### Route Compatibility

Orders can only be combined if they share the same:

* Origin
* Destination

### Hazmat Handling

Hazmat and non-hazmat orders are not mixed in the same load.

### Date Validation

For every order:

pickup_date <= delivery_date

### Payload Size

The service supports a maximum of 22 orders per optimization request.

Requests exceeding this limit return HTTP 413 (Payload Too Large).

---

## API

### Optimize Load

POST

```text
/api/v1/load-optimizer/optimize
```

### Sample Request

```json
{
  "truck": {
    "id": "truck-123",
    "max_weight_lbs": 44000,
    "max_volume_cuft": 3000
  },
  "orders": [
    {
      "id": "ord-001",
      "payout_cents": 250000,
      "weight_lbs": 18000,
      "volume_cuft": 1200,
      "origin": "Los Angeles, CA",
      "destination": "Dallas, TX",
      "pickup_date": "2025-12-05",
      "delivery_date": "2025-12-09",
      "is_hazmat": false
    },
    {
      "id": "ord-002",
      "payout_cents": 180000,
      "weight_lbs": 12000,
      "volume_cuft": 900,
      "origin": "Los Angeles, CA",
      "destination": "Dallas, TX",
      "pickup_date": "2025-12-04",
      "delivery_date": "2025-12-10",
      "is_hazmat": false
    }
  ]
}
```

### Sample Response

```json
{
  "truckId": "truck-123",
  "selectedOrderIds": [
    "ord-001",
    "ord-002"
  ],
  "totalPayoutCents": 430000,
  "totalWeightLbs": 30000,
  "totalVolumeCuft": 2100,
  "utilizationWeightPercent": 68.18,
  "utilizationVolumePercent": 70.00
}
```

---

## Health Check

Spring Boot Actuator endpoint:

```text
GET /actuator/health
```

Example:

```bash
curl http://localhost:8080/actuator/health
```

Response:

```json
{
  "status": "UP"
}
```

---

## Caching

The optimization endpoint uses Caffeine Cache to avoid recalculating identical requests.

---

## Running Locally

### Build

```bash
mvn clean package
```

### Run

```bash
java -jar target/load-optimizer.jar
```

---

## Docker

### Build Image

```bash
docker build -t load-optimizer .
```

### Run Container

```bash
docker run -p 8080:8080 load-optimizer
```

---

## Docker Compose

### Start

```bash
docker compose up --build
```

### Stop

```bash
docker compose down
```

---