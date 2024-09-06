# planner
PR Department Planner

Web app configuration and features: The web app welcomes users
with a login screen via Google's authorization api.
Once logged in with Google account, users can 
add an event to calendar or
download events added by all registered users in a PDF form
for a selected time-frame (usually one week ahead or two weeks).
An event has these fields: 
structural unit (Norilsk, Kola, Bystra, Moscow, Other),
date,
suggested coverage (Interview, Social Media posts, Press Release, Press Tour, Video Feature, Other).
The web app also has a calendar of upcoming events
in the form of a widget or a dashboard on the main webpage,
as well as buttons to add event or download pdf with upcoming events.

Technology Stack:

Frontend: React.js
Backend: Spring Boot (Java)
Database: MongoDB
Authentication: Google OAuth 2.0
Containerization: Docker
Deployment: AWS Lightsail
