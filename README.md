<div align="center">
  <h1> AI Recruitment & Candidate Tracking System</h1>
  <p>
    An enterprise-grade, intelligent recruitment platform built with Spring Boot 3 and Java 21. 
    It leverages AI-driven text extraction to parse candidate resumes, extract skills, and automatically rank candidates against job requirements.
  </p>
</div>

---

##  Overview

The **AI Recruitment System** revolutionizes the hiring process by bridging the gap between recruiters and top talent through automation. By utilizing **Apache PDFBox** for advanced document parsing and a custom **Skill Matching Algorithm**, the system automatically evaluates applications, filtering out candidates who do not meet a configurable selection threshold.

###  Key Capabilities
- **Intelligent Resume Parsing:** Automatically reads and extracts textual data and skills from uploaded PDF resumes.
- **Automated Candidate Matching:** Compares extracted skills against specific job requirements. Candidates scoring above the `85%` configurable threshold are automatically selected.
- **Role-Based Access Control (RBAC):** Distinct, secure portals for **Administrators/Recruiters** and **Candidates** using Spring Security.
- **Automated Communication:** Integration with SMTP email providers to notify candidates of application status and interview schedules.
- **Dynamic Dashboards:** Intuitive frontend powered by Thymeleaf and modern CSS, offering real-time tracking of jobs and applications.

---

##  Technology Stack

This application is built upon a modern, robust, and scalable Java ecosystem:

### Backend
* **Java 21:** Utilizing the latest language features and performance enhancements.
* **Spring Boot 3.5.0:** Core framework for rapid application development.
* **Spring Data JPA & Hibernate:** ORM and database interactions.
* **Spring Security:** Robust authentication and authorization.
* **Spring Mail:** Automated email notifications.
* **Apache PDFBox (v3.0.3):** Core AI engine for parsing and extracting text from PDF documents.

### Frontend
* **Thymeleaf:** Server-side Java template engine for dynamic HTML rendering.
* **Thymeleaf Security Extras:** Seamless integration between UI elements and Spring Security Context.
* **HTML5 / CSS3:** Responsive and dynamic user interfaces.

### Database
* **PostgreSQL:** Primary relational database for transactional integrity.

---

##  System Architecture

The application is designed using a standard MVC (Model-View-Controller) architecture, augmented with specialized AI service layers:

```text
com.mca.recruitment
├── ai/             # AI processing layer (ResumeMatcherService, ResumeParserService, SkillExtractorService)
├── config/         # Application configurations (SecurityConfig)
├── controller/     # REST and MVC endpoints (AuthController, JobController, CandidateController)
├── dto/            # Data Transfer Objects for client-server communication
├── entity/         # JPA Domain models (User, Job, Candidate, Resume, Application)
├── exception/      # Global exception handlers
├── repository/     # Spring Data JPA interfaces
├── security/       # Custom security providers (CustomUserDetailsService)
└── service/        # Core business logic
```

---

##  Core Modules & Logic

### 1. The AI Matcher Pipeline
When a candidate applies for a job, the system initiates the following pipeline:
1. **Upload Phase:** The resume (PDF) is uploaded (Max file size: 10MB).
2. **Parsing Phase:** `ResumeParserService` reads the binary PDF and extracts raw text.
3. **Extraction Phase:** `SkillExtractorService` parses the raw text to identify key technical and soft skills.
4. **Matching Phase:** `ResumeMatcherService` cross-references the candidate's skills with the `Job` requirements.
5. **Decision Phase:** The system calculates a match percentage. The baseline threshold is configured at `85.0%`. Candidates above this score are marked as **Selected**, while those below are **Rejected**.

### 2. User Portals
- **Recruiter Portal:** Post new jobs, view applicant tracking statistics, download candidate resumes, and view AI-generated match scores.
- **Candidate Portal:** Browse open positions, upload resumes, track application status, and manage profile information.

---

##  Getting Started

Follow these instructions to set up the project on your local development environment.

### Prerequisites
- [JDK 21](https://jdk.java.net/21/) or higher installed.
- [Maven 3.8+](https://maven.apache.org/) installed.
- [PostgreSQL 14+](https://www.postgresql.org/) installed and running.

### 1. Database Setup
Create a new PostgreSQL database named `ai_recruitment_db`.
```sql
CREATE DATABASE ai_recruitment_db;
```

Ensure your PostgreSQL credentials match the default configuration, or update them in the properties file.

### 2. Environment Variables
The application utilizes Spring Mail for automated notifications. You must set the following environment variables in your system or IDE (STS/IntelliJ/Eclipse) run configurations:

* `MAIL_USERNAME` = Your Gmail Address (e.g., `youremail@gmail.com`)
* `MAIL_APP_PASSWORD` = Your Gmail App Password (Generate this in Google Account Security settings).

### 3. Clone and Build
Clone the repository and build the project using the Maven wrapper:
```bash
git clone https://github.com/megh-01/ai-recruitment-system.git
cd ai-recruitment-system

# Clean and package the application
./mvnw clean install
```

### 4. Run the Application
Start the Spring Boot server:
```bash
./mvnw spring-boot:run
```
The application will be accessible at: **http://localhost:8081**

---

##  Configuration Properties

Key configurations found in `src/main/resources/application.properties`:

| Property | Default Value | Description |
| :--- | :--- | :--- |
| `server.port` | `8081` | The embedded Tomcat server port. |
| `app.selection-threshold` | `85.0` | The minimum AI match percentage required for a candidate to be selected. |
| `spring.servlet.multipart.max-file-size` | `10MB` | Maximum allowed size for a single PDF resume upload. |
| `spring.jpa.hibernate.ddl-auto` | `update` | Automatically updates the DB schema on startup. |

---

##  Security & Authentication
- Passwords are securely hashed using **BCryptPasswordEncoder**.
- Routes are heavily protected:
  - `/admin/**` endpoints are restricted to users with the `ADMIN` / `RECRUITER` role.
  - `/candidate/**` and application submissions are restricted to the `CANDIDATE` role.
  - `/login`, `/register`, and public job listings are accessible to `PERMIT_ALL`.

---

##  Contribution Guidelines
1. Fork the repository.
2. Create a feature branch: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin feature/your-feature-name`
5. Open a Pull Request for review.

---

<div align="center">
  <p>Built with ❤️ for modern HR teams. Powered by Spring Boot & Java 21.</p>
</div>
