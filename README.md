<h1>🧳 Trip Planner Web App</h1>

<p><strong>By:</strong> Afek Sulimani & Dvir Karni</p>
<p><strong>Course Project:</strong> Internet Programming – B.Sc. Computer Science</p>

<h2>🛠️ Tech Stack</h2>
<ul>
  <li><strong>Language:</strong> Java</li>
  <li><strong>Frameworks:</strong> Spring Boot, Spring MVC, Spring Security</li>
  <li><strong>Frontend:</strong> Thymeleaf, HTML, CSS, JavaScript</li>
  <li><strong>Database:</strong> MySQL + Hibernate ORM</li>
  <li><strong>Security:</strong> Spring Security with form-based login and role-based authorization</li>
</ul>

<hr />

<h2>📋 Description</h2>
<p>
  <strong>Trip Planner</strong> is a full-stack Java web application that allows users to plan, manage, and share detailed travel itineraries. Users can create multi-day trips, add hotels and daily activities, and view summaries through a responsive web interface. Built using Spring Boot and Thymeleaf, the app ensures secure user access and organized data handling through Spring Security and Hibernate.
</p>

<hr />

<h2>🔐 Security Highlights</h2>
<ul>
  <li>Authentication via Spring Security with in-memory or persistent users</li>
  <li>Role-based access control using roles such as <code>USER</code> and <code>ADMIN</code></li>
  <li>CSRF protection, password encryption, and access control configured in <code>WebSecurityConfig.java</code></li>
  <li>Secure session management and protected route access</li>
</ul>

<hr />

<h2>🧭 Features</h2>
<ul>
  <li>User registration and login</li>
  <li>Create and manage trips (start/end dates, hotels, activities)</li>
  <li>View day-by-day breakdowns and summaries</li>
  <li>Form validation and error handling</li>
  <li>Secure user actions and state transitions via controller logic</li>
</ul>

<hr />

<h2>📂 Key Files</h2>
<ul>
  <li><code>WebSecurityConfig.java</code> – Defines authentication, authorization, login behavior</li>
  <li><code>TriPlanController.java</code> – Handles trip creation, viewing, editing, and user flow</li>
  <li><code>TripPlan.java</code> – Entity model for a complete trip</li>
  <li><code>templates/</code> – Thymeleaf templates for frontend rendering</li>
</ul>

<hr />

<h2>⚙️ Setup & Run</h2>
<pre><code># Clone the repository
git clone https://github.com/karniDvir/Trip-planner.git
cd Trip-planner

# Configure your DB in application.properties
spring.datasource.username=yourUsername
spring.datasource.password=yourPassword

# Run the project with Maven
mvn spring-boot:run
</code></pre>

<hr />

<h2>📧 Contact</h2>
<p>Questions or suggestions? Reach out at: <a href="mailto:dkarni07@gmail.com">dkarni07@gmail.com</a></p>
