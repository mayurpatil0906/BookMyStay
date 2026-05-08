# 🏨 BookMyStay — Hotel Booking Management System

> A modular, Java-based hotel booking simulation engine designed as a **pedagogical tool** for teaching Core Java, object-oriented design, and real-world data structures through the domain of hotel reservation management.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [System Architecture](#system-architecture)
- [Module Breakdown](#module-breakdown)
  - [UC1 — Room Inventory Setup & Management](#uc1--room-inventory-setup--management)
  - [UC2 — Room Search & Availability Check](#uc2--room-search--availability-check)
  - [UC3 — Booking Request (First-Come-First-Served)](#uc3--booking-request-first-come-first-served)
  - [UC4 — Reservation Confirmation & Room Allocation](#uc4--reservation-confirmation--room-allocation)
  - [UC5 — Add-On Service Selection](#uc5--add-on-service-selection)
  - [UC6 — Booking History & Reporting](#uc6--booking-history--reporting)
- [Core Java Concepts Demonstrated](#core-java-concepts-demonstrated)
- [Data Structures Reference](#data-structures-reference)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Flow Diagram](#flow-diagram)
- [Educational Goals](#educational-goals)

---

## Overview

**BookMyStay** simulates a real-world hotel booking platform where guests can search, reserve, and enhance their stays with add-on services — all on a **first-come, first-served** basis. The system manages live room inventory, enforces availability holds, processes reservations atomically, and tracks optional services like breakfast, airport pickup, and spa — preventing double-booking at every step.

This project is a **teaching vehicle** — each use case introduces an incremental system design challenge mapped to a specific data structure and software engineering principle.

```
Guest Request ──▶ Search Availability ──▶ Join Booking Queue
                                                │
                                                ▼
                                     Dequeue & Allocate Room
                                                │
                                                ▼
                                     Attach Add-On Services
                                                │
                                                ▼
                                     Confirm & Persist to History
```

---

## Features

| Feature | Description |
|---|---|
| 🏠 Room Inventory | Real-time HashMap-backed inventory for room types, counts, and prices |
| 🔍 Room Search | Read-only availability queries with defensive validation |
| 🎟️ Fair Booking Queue | FIFO Queue ensures first-come-first-served fairness |
| ✅ Room Allocation | HashSet-enforced uniqueness guarantees zero double-booking |
| 🍳 Add-On Services | Flexible one-to-many service mapping per reservation |
| 📋 Booking History | Ordered List-backed audit trail with full reporting support |

---

## System Architecture

The system follows a **layered, modular design** with clear separation between inventory, booking, service, and reporting concerns.

```
┌──────────────────────────────────────────────────────────┐
│                    Guest / Admin Interface                │
│              (Search, Book, Add Services, Report)         │
├──────────────────────────────────────────────────────────┤
│                      Service Layer                        │
│  InventoryService │ BookingQueueService │ AllocationSvc   │
│  ServiceManagementModule │ ReportingService               │
├──────────────────────────────────────────────────────────┤
│                      Domain Layer                         │
│  RoomInventory │ Reservation │ Service │ BookingHistory   │
├──────────────────────────────────────────────────────────┤
│                   Data Structure Layer                    │
│  HashMap │ Queue (LinkedList) │ HashSet │ List            │
└──────────────────────────────────────────────────────────┘
```

---

## Module Breakdown

### UC1 — Room Inventory Setup & Management

Establishes and maintains the single source of truth for all hotel room data.

**Actors:** Hotel Admin, Inventory Service

**Key Data Structures:**

| Structure | Usage |
|---|---|
| `HashMap<String, Integer>` | Room type → available count |
| `HashMap<String, Double>` | Room type → price per night |

**Key Operations:**
- Initialize room types: `Single`, `Double`, `Suite`
- Store and update room counts and prices dynamically
- Provide real-time availability status at O(1) lookup speed

**Flow:**
```
Add Room Type ──▶ Store in HashMap ──▶ Update Count/Price ──▶ Confirm
```

**Why HashMap?**
- O(1) average-case read and write
- Clean separation of count data from pricing data
- Easy to extend with new room types without restructuring

> **Problem Solved:** Replaces manual registers that caused inconsistent counts and overbooking.

---

### UC2 — Room Search & Availability Check

Allows guests to browse available rooms and prices **without mutating inventory state**.

**Actors:** Guest, Search Service

**Key Data Structures:**

| Structure | Usage |
|---|---|
| `HashMap<String, Integer>` | Read-only count lookup |
| `HashMap<String, Double>` | Read-only price lookup |

**Key Operations:**
- Display all available room types with counts and prices
- Filter out room types with zero availability
- Show amenities alongside pricing

**Flow:**
```
Search Request ──▶ HashMap Lookup ──▶ Filter Available Rooms ──▶ Display
```

**Key Design Principle — Defensive Read:**
The search layer only reads; it never decrements counts. Inventory mutation is reserved exclusively for the allocation phase (UC4), preventing phantom availability.

> **Problem Solved:** Replaces static room listings that showed stale or inaccurate availability.

---

### UC3 — Booking Request (First-Come-First-Served)

Manages incoming booking requests fairly under high-traffic conditions using a FIFO queue.

**Actors:** Guest, Booking Queue Service

**Key Data Structure:**

| Structure | Usage |
|---|---|
| `Queue<Reservation>` (backed by `LinkedList`) | Ordered booking request queue |

**Key Operations:**
- Accept booking requests from multiple guests
- Enqueue requests in arrival order
- Hold requests until the allocation service processes them

**Flow:**
```
Booking Request ──▶ Enqueue ──▶ Await Processing
```

**Why Queue?**
- Guarantees FIFO (first-in, first-out) processing order
- Decouples request intake from allocation logic
- Models real-world hotel front-desk queuing accurately

> **Problem Solved:** Replaces parallel request handling that caused race conditions and unfair allocations.

---

### UC4 — Reservation Confirmation & Room Allocation

Dequeues pending requests and assigns unique room IDs, guaranteeing **zero double-booking**.

**Actors:** Booking Service, Inventory Service

**Key Data Structures:**

| Structure | Usage |
|---|---|
| `HashSet<String>` | Tracks all allocated room IDs (uniqueness enforcement) |
| `HashMap<String, Set<String>>` | Maps room type → set of assigned room IDs |

**Key Operations:**
- Dequeue the next pending reservation
- Generate and assign a unique room ID
- Add room ID to the `HashSet` (rejects duplicates automatically)
- Decrement available count in the inventory `HashMap`

**Flow:**
```
Dequeue Request ──▶ Assign Room ID ──▶ Add to HashSet ──▶ Decrement Count ──▶ Confirm
```

**Why HashSet?**
- O(1) contains-check before any assignment
- Structural guarantee: the same room ID can never be added twice
- Eliminates the need for manual duplication checks

> **Problem Solved:** Replaces overlapping room assignments that led to two guests receiving the same room.

---

### UC5 — Add-On Service Selection

Enables guests to attach optional services to their confirmed reservation using a **one-to-many mapping**.

**Actors:** Guest, Service Management Module

**Key Data Structure:**

| Structure | Usage |
|---|---|
| `Map<String, List<Service>>` | Reservation ID → list of attached services |

**Available Add-Ons:**

| Service | Description |
|---|---|
| 🍳 Breakfast | Daily breakfast included |
| ✈️ Airport Pickup | Transfer service on arrival/departure |
| 💆 Spa Access | Full-day spa pass |

**Key Operations:**
- Attach one or more services to a reservation by ID
- Allow multiple services per booking (no limit)
- Calculate additional cost by summing service prices

**Flow:**
```
Select Service ──▶ Add to List ──▶ Map to Reservation ID ──▶ Update Total Cost
```

**Why `Map<String, List<Service>>`?**
- One reservation can hold many services (one-to-many relationship)
- O(1) reservation lookup via Map key
- `List` allows ordered, duplicate-friendly service attachment
- Easy to extend with new service types

> **Problem Solved:** Replaces manual add-on tracking that caused billing errors and missed services.

---

### UC6 — Booking History & Reporting

Maintains a complete, ordered record of all confirmed reservations for audit, reporting, and customer support.

**Actors:** Admin, Reporting Service

**Key Data Structure:**

| Structure | Usage |
|---|---|
| `List<Reservation>` | Ordered, time-sequenced reservation history |

**Key Operations:**
- Append each confirmed reservation to the history list
- Support retrieval by guest ID, room type, or date range
- Allow cancellation status updates on past reservations
- Generate admin reports and guest booking summaries

**Flow:**
```
Confirm Booking ──▶ Add to List ──▶ Persist ──▶ Retrieve on Demand
```

**Why List?**
- Preserves insertion order (chronological booking history)
- Supports indexed access for report generation
- Simple iteration for filtering and aggregation

> **Problem Solved:** Replaces ad-hoc record-keeping that made cancellations and audits unreliable.

---

## Core Java Concepts Demonstrated

### Object-Oriented Principles

| Concept | Where Applied |
|---|---|
| **Encapsulation** | Room counts and prices are private; exposed via service methods only |
| **Abstraction** | `BookingService` hides allocation complexity from the guest layer |
| **Inheritance** | Base `Service` class extended by `BreakfastService`, `SpaService`, etc. |
| **Polymorphism** | `Service` interface implemented by all add-on types |

### Control Flow

| Construct | Where Applied |
|---|---|
| `if / else` | Availability checks, boundary validation |
| `while` / `for` | Queue draining, report iteration |
| `switch` | Room type routing, service type selection |
| `try-catch` | Invalid input handling, null safety |

### Exception Handling

```java
// Custom exceptions for domain clarity
class RoomUnavailableException extends RuntimeException { ... }
class DuplicateRoomAllocationException extends RuntimeException { ... }
class InvalidReservationException extends RuntimeException { ... }
```

---

## Data Structures Reference

| Use Case | Data Structure | Time Complexity | Why This Choice |
|---|---|---|---|
| UC1 — Inventory | `HashMap<String, Integer>` | O(1) read/write | Fast key-based room lookup |
| UC1 — Pricing | `HashMap<String, Double>` | O(1) read/write | Separate pricing concern |
| UC2 — Search | `HashMap` (read-only) | O(1) read | No mutation during search |
| UC3 — Queue | `Queue<Reservation>` (LinkedList) | O(1) enqueue/dequeue | FIFO fairness guarantee |
| UC4 — Allocation | `HashSet<String>` | O(1) contains/add | Uniqueness enforcement |
| UC4 — Type Map | `HashMap<String, Set<String>>` | O(1) lookup | Room type → room IDs |
| UC5 — Services | `Map<String, List<Service>>` | O(1) lookup | One-to-many mapping |
| UC6 — History | `List<Reservation>` | O(1) append, O(n) search | Ordered audit trail |

---

## Getting Started

### Prerequisites

- Java 17+
- Maven or Gradle (optional)
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code)

### Clone the Repository

```bash
git clone https://github.com/your-username/BookMyStay.git
cd BookMyStay
```

### Compile & Run

```bash
# Compile
javac -d out src/**/*.java

# Run the main application
java -cp out com.bookmystay.Main
```

### Run with Maven

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.bookmystay.Main"
```

---

## Project Structure

```
BookMyStay/
├── src/
│   └── com/
│       └── bookmystay/
│           ├── Main.java                        # Application entry point
│           ├── domain/
│           │   ├── Room.java                    # Room entity
│           │   ├── Reservation.java             # Reservation entity
│           │   └── service/
│           │       ├── Service.java             # Base service interface
│           │       ├── BreakfastService.java
│           │       ├── SpaService.java
│           │       └── AirportPickupService.java
│           ├── inventory/
│           │   ├── RoomInventory.java           # HashMap-backed inventory
│           │   └── InventoryService.java
│           ├── booking/
│           │   ├── BookingQueue.java            # Queue<Reservation>
│           │   ├── BookingQueueService.java
│           │   ├── AllocationService.java       # HashSet room ID tracking
│           │   └── ReservationConfirmation.java
│           ├── addons/
│           │   └── ServiceManagementModule.java # Map<String, List<Service>>
│           └── reporting/
│               ├── BookingHistory.java          # List<Reservation>
│               └── ReportingService.java
├── test/
│   └── com/
│       └── bookmystay/
│           ├── InventoryServiceTest.java
│           ├── BookingQueueTest.java
│           ├── AllocationServiceTest.java
│           └── ReportingServiceTest.java
├── README.md
└── pom.xml
```

---

## Flow Diagram

```
┌──────────────┐     ┌───────────────────┐     ┌──────────────────────┐
│    GUEST     │────▶│  Search Rooms      │────▶│  Rooms Available?    │
└──────────────┘     │  (HashMap lookup)  │     └────────┬─────────────┘
                     └───────────────────┘              │ Yes
                                                         ▼
                                              ┌──────────────────────┐
                                              │  Submit Booking Req   │
                                              │  (Enqueue → Queue)    │
                                              └──────────┬───────────┘
                                                         │
                                                         ▼
                                              ┌──────────────────────┐
                                              │  Dequeue & Allocate   │
                                              │  (HashSet ID assign)  │
                                              └──────────┬───────────┘
                                                         │
                                                         ▼
                                              ┌──────────────────────┐
                                              │  Select Add-Ons       │
                                              │  (Map<ID, List<Svc>>) │
                                              └──────────┬───────────┘
                                                         │
                                                         ▼
                                              ┌──────────────────────┐
                                              │  Confirm & Archive    │
                                              │  (List<Reservation>)  │
                                              └──────────────────────┘
```

---

## Educational Goals

This project is structured to teach the following progressively:

| Phase | Use Case | New Concept Introduced |
|---|---|---|
| Phase 1 | UC1 — Inventory Setup | `HashMap`, key-value storage, O(1) access |
| Phase 2 | UC2 — Room Search | Read-only access patterns, defensive programming |
| Phase 3 | UC3 — Booking Queue | `Queue` / `LinkedList`, FIFO, fairness models |
| Phase 4 | UC4 — Room Allocation | `HashSet`, uniqueness invariants, atomic updates |
| Phase 5 | UC5 — Add-On Services | `Map<K, List<V>>`, one-to-many composition |
| Phase 6 | UC6 — History & Reports | `List`, ordered storage, audit trail design |

Each phase builds on the previous one — by UC6, students have used **5 different data structures** in a coherent, real-world context.

---

## Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you'd like to change.

1. Fork the repository
2. Create your feature branch: `git checkout -b feature/your-feature`
3. Commit your changes: `git commit -m 'Add your feature'`
4. Push to the branch: `git push origin feature/your-feature`
5. Open a Pull Request

---

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

---

> 💡 **Note:** BookMyStay is a simulation built for educational purposes. It is not connected to any live booking system or payment processor.
