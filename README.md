![Build Status](https://github.com/Pronoy999/FinSight/actions/workflows/main.yml/badge.svg)
# Financial Tracker Application  

Welcome to the **Financial Tracker Application**! This application helps you manage your personal finances by tracking income, expenses, and providing insightful analytics to better understand your financial habits.  

---
## API Docs
https://documenter.getpostman.com/view/5329722/2sB2j98pGV

## Features  

### Transaction Management  
- Record income and expenses with ease.  
- Categorize transactions (e.g., Food, Rent, Entertainment).  
- View transaction history with filtering and sorting options.  

### Insights and Analytics  
- Visualize spending patterns with graphs and charts.  
- Monthly and yearly summaries of income vs. expenses.  
- Insights into top spending categories and areas to save.  

### Budgeting Tools  
- Set monthly budgets for categories and track your progress.  
- Receive alerts when you approach or exceed your budget.  

### User-Friendly Interface  
- Intuitive dashboard to display key financial stats.  
- Mobile-friendly design for on-the-go access.  

---

## Technologies Used  

- **Backend:** Spring Boot, Hibernate  
- **Frontend:** React.js or Angular  
- **Database:** PostgreSQL  
- **Authentication:** JWT with `com.auth0:java-jwt`  
- **Deployment:** Docker, Kubernetes (Optional)  

---

## Setup  

### Prerequisites  
1. Java 17+  
2. Node.js (for frontend)  
3. PostgreSQL  

### Backend Setup  
1. Clone the repository:  
   ```bash  
   git clone https://github.com/your-username/financial-tracker.git  
   cd financial-tracker/backend  
2. Configure database settings in application.properties.

## Usage
1. Sign Up: Create an account to begin tracking your finances.
2. Add Transactions: Log your income and expenses with detailed descriptions.
3. View Insights: Explore charts and summaries to analyze your financial habits.
4. Stay on Budget: Use budgeting tools to manage spending.

## Docker Command:
```
   docker run -d \
   --name fin_sight_api \
   -p 8080:8080 \
   -e SPRING_DATASOURCE_URL=jdbc:postgresql://172.17.0.1:5432/fin_sight \
   -e SPRING_DATASOURCE_USERNAME=postgres \
   -e SPRING_DATASOURCE_PASSWORD=admin \
   -e SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect \
   pronoytw/fin-sight:latest
```
Update your port and database credentials as needed.

## Contact
For questions or feedback, feel free to reach out:

Twitter DM: [PronoyMukherjee](https://x.com/PronoyMukherje)

GitHub: /Pronoy999

Happy Tracking! 😊
