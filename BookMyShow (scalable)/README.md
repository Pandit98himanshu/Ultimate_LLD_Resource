# Design BookMyShow (Movie ticket booking system)

### Functional Requirements:

1. The system should be able to list down cities where its cinemas are located.
2. Upon selecting the city, the system should display the movies released in that particular city to that user.
3. Once the user makes his choice of the movie, the system should display the cinemas running that movie and its available shows.
4. The user should be able to select the show from a cinema and book their tickets.
5. The system should be able to show the user the seating arrangement of the cinema hall.
6. The user should be able to select multiple seats according to their choice.
7. The user should be able to distinguish between available seats from the booked ones.
8. Users should be able to put a hold on the seats for 5 minutes before they make a services to finalize the booking.
9. The system should serve the tickets First In First Out manner

### Non-Functional Requirements:

1. The system would need to be highly concurrent as there will be multiple booking requests for the same seat at any particular point in time.
2. Secure and ACID compliant.
3. System should be scalable and can handle 10,000 requests concurrently.