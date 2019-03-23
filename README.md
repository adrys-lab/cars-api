Instructions of use:
-----------------------

- Before start using API, a Login is needed with valid users (user1/user1, user2/user2, user3/user3, user4/user4, user5/user5, user6/user6).
- each user has 1 or more roles -> Writer (for write operations, Reader (for read operations), Unauthorized (can do nothing)
- After login, a token will be offered. Token has an expiration time of 1 hour.
- For each Request this token has to be sent in the Header with pattern "Bearer %s".

---- Api requests:
- Verify the user has the proper rights to access the endpoint associated with a Role.
- For filter cars please have a look at the enum Comparison to send correct values



Explanation of solution:
-----------------------

---- In terms of security:
- Introduced Login in the API, and managed added Spring Security API context to log under it.
- Added 1 hour token validation.
- Encrypted passwords (left the drivers like they were in DDBB)
- Introduction of roles for endpoints.
- use trusted libraries and versions.
- Custom Javax Validation (more custom validations could be done but i considered out of the scope).
- Custom Error handling and customized checked/unchecked exceptions (as well, more could be done in this topic, but i cnsidered out of the scope)
- ensuring no exceptions (at least not desired) are thrown to the end-user.
---> More things can be done, but I thing are out of this scope (Oauth2, HTTPS, certificates, XSS protection, add findbugs, sonarqube rules...).

---- In terms of performance
- Cache has been added to improve response time to user.
- Transactional DDBB processes to keep data integrity.
---> More things can be done, but I thing are out of this scope (Buffered responses to not keep the user waiting for whole response).


---- Model of classes
- Implementation with abstract and generics concept to make the code easy-maintanable, extensible and scalable.
- Filter Chain Pattern.
---> Some doubts raised on me, but i posted them in the code comments. I tried to not touch the code you brought to me (Driver Service, and your models)

---- Restify
- Method contracts are under REST recommendations, including methods and paths, it's variables/params and their correct use of HHTP Methods.

---- Test
 - More test could be done, but i tried to add simply integration tests from end-to-end.
 - Had no more time for add tests, but i would like to add more test for risky classes, and have at least a 70-80% of coverage.