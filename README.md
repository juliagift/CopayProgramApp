# Copay Card Application

Description
This application simulates copay card operations. Two roles are implemented, namely users and admin, who get routed to different dashboards based on their login credentials.

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

Technologies used
Spring Boot Project
	Dependencies:
	* Spring Web


Technologies used:
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
