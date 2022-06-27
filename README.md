# Copay Card Application

DESCRIPTION

This application simulates copay card operations which features role-based multiple dashboard access from a login page. Two roles are implemented, namely user and admin.

Users can:
* answer pre-screening questions before they can register
* register for a copay card
* login and get routed to a user dashboard
* submit and view claims
* edit and view profile
* delete account
* logout

Admin can:
* login and get routed to an admin dashboard
* view all patients and claims records
* logout

Once a user’s account is deleted, they can no longer log back in. Their account information still persists in the database and can be accessed by the admin.

TECHNOLOGIES USED

Spring Boot Project

Dependencies:
* Spring Web
* Spring Boot DevTools
* Spring Data JPA
* Spring Security
* Validation I/O
* Lombok 


* Java 8
* JUnit testing
* HTML
* CSS
* Thymeleaf
* JavaScript


Database: MariaDB
