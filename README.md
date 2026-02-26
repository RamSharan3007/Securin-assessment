To satisfy the **Submission Instructions** in your **Recipe.pdf**, this README is structured professionally. It reflects the complete workflow: from the initial JSON parsing and MySQL ingestion to the final REST API endpoints.

---

# Recipe Data Collection & API Development

This project is a Spring Boot application designed to manage and query recipe data. It features an automated data migration pipeline that parses a raw JSON dataset (`US_recipes_null.json`), calculates business logic (total time), and persists the information into a MySQL database for access via a RESTful API.

## 🏗️ Architecture & Workflow

The application follows a standard **N-Tier Architecture**:

1. **Data Ingestion Layer**: A `CommandLineRunner` that parses the source JSON file on startup.
2. **Controller Layer**: Handles HTTP requests and returns JSON responses.
3. **Service Layer**: Contains business logic, specifically the automatic calculation of `total_time`.
4. **Repository Layer**: Uses Spring Data JPA to communicate with the MySQL database.

---

## 🛠️ Tech Stack

* **Java 17**
* **Spring Boot 3.x** (Web, Data JPA)
* **MySQL 8.0**
* **Lombok** (for clean boilerplate code)
* **Jackson** (for JSON parsing)

---

## ⚙️ Setup & Installation

### 1. Database Configuration

Create a schema named `recipedb` in your MySQL instance:

```sql
CREATE DATABASE recipedb;

```

Update the `src/main/resources/application.properties` file:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/recipedb
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

# Automatically creates/updates the table schema
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

```

### 2. Data Ingestion

Ensure `US_recipes_null.json` is placed in the `src/main/resources/` folder. On application startup, the `DataLoader` component will automatically parse the file and populate the database.

---

## 🛣️ API Endpoints

### 1. Create a Recipe

* **Endpoint**: `POST /recipes`
* **Description**: Adds a new recipe. `total_time` is automatically calculated.
* **Payload**:

```json
{
  "title": "Chocolate Cake",
  "cuisine": "Dessert",
  "rating": 4.7,
  "prep_time": 20,
  "cook_time": 40
}

```

### 2. Get Top Rated Recipes

* **Endpoint**: `GET /recipes/top?limit=5`
* **Description**: Returns a JSON object with a list of recipes sorted by rating in descending order.

### 3. Get Recipe Count

* **Endpoint**: `GET /recipes/count`
* **Description**: Returns the total number of recipes currently in the database.

---

## 🧪 Testing Instructions

### Using Postman

1. **POST Method**: Select `POST` and enter `http://localhost:8080/recipes`. Under the **Body** tab, select `raw` and `JSON`. Paste a recipe object and click **Send**.
2. **GET Method**: Select `GET` and enter `http://localhost:8080/recipes/top?limit=2`. You should receive a JSON response containing the top 2 recipes under a `"data"` key.

---

## 📝 Design Decisions & Validation

* **JSON Mapping**: Used `@JsonProperty` to bridge the gap between snake_case JSON keys (`prep_time`) and Java camelCase fields.
* **Validation**: Implemented checks in the Controller to ensure mandatory fields like `title` and `cuisine` are present before saving.
* **Data Integrity**: Used `@JsonIgnoreProperties(ignoreUnknown = true)` to handle inconsistent or extra fields in the raw dataset without causing parsing errors.

---

**Would you like me to also generate the SQL script file separately so you can include it in your submission folder?**
