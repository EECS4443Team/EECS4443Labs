# EECS4443 Lab 01
This repository is for EECS 4443 W2026 Lab 01 - Simple Login Interface.
## Team Members
| Full Name | Section (Lab) | Student ID | Email |
|----------|----------|----------|----------|
| **Jorel Louie Chim**   | M (Tuesday Lab)   | 217207879   | jorelc@my.yorku.ca   |
| **Chan Woo Hwang**  | M (Tuesday Lab)   | 218972539   | htry02@my.yorku.ca   |
| **Shivraj Banwait**   | M (Tuesday Lab)   | 217279373   | shivrajb@my.yorku.ca   |
## Team Contributions
| Team Member        | Contributions | 
|--------------------|------------------|
| **Jorel Louie Chim** | Login, Register, Backend(Remember Me)      |
| **Shivraj Banwait** | Login, Register, Backend(Remember Me)        |
| **Chan Woo Hwang** | Login, Register, Welcome Page, Backend(Validation, Registration)         | 
## Known Limitations
After remember me is checked and the user is able to auto-login, since there is no "Log Out" button on the Welcome page, the user is unable to access the Login page again. This is because SharedPreferences is checked at onCreate, right after opening the main screen. SharedPreferences is never cleared through another button or action, like a logout button.
