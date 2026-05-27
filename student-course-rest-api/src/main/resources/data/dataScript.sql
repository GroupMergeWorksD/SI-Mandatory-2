CREATE DATABASE IF NOT EXISTS student_course_db;
USE student_course_db;

INSERT INTO students (name, email, age) VALUES
                                            ('Alice Johnson', 'alice@example.com', 20),
                                            ('Bob Smith', 'bob@example.com', 22),
                                            ('Charlie Brown', 'charlie@example.com', 19),
                                            ('Diana Prince', 'diana@example.com', 21),
                                            ('Ethan Hunt', 'ethan@example.com', 23),
                                            ('Fiona Gallagher', 'fiona@example.com', 20),
                                            ('George Miller', 'george@example.com', 24),
                                            ('Hannah Davis', 'hannah@example.com', 18),
                                            ('Ian Wright', 'ian@example.com', 22),
                                            ('Julia Roberts', 'julia@example.com', 21),
                                            ('Kevin Hart', 'kevin@example.com', 20),
                                            ('Laura Wilson', 'laura@example.com', 19),
                                            ('Michael Scott', 'michael@example.com', 25),
                                            ('Nina Patel', 'nina@example.com', 22),
                                            ('Oscar Martinez', 'oscar@example.com', 23);

INSERT INTO courses (title, instructor, credits) VALUES
                                                     ('Mathematics', 'Dr. Adams', 3),
                                                     ('Computer Science', 'Prof. Lee', 4),
                                                     ('History', 'Dr. Green', 2),
                                                     ('Physics', 'Dr. Brown', 3),
                                                     ('Chemistry', 'Dr. White', 4),
                                                     ('Biology', 'Dr. Carter', 3),
                                                     ('English Literature', 'Prof. Harris', 3),
                                                     ('Philosophy', 'Dr. Young', 2),
                                                     ('Economics', 'Prof. Turner', 3),
                                                     ('Psychology', 'Dr. Walker', 4),
                                                     ('Sociology', 'Dr. Hall', 3),
                                                     ('Political Science', 'Prof. Allen', 3),
                                                     ('Art History', 'Dr. King', 2),
                                                     ('Statistics', 'Prof. Scott', 4),
                                                     ('Environmental Science', 'Dr. Baker', 3);

INSERT INTO enrollments (student_id, course_id, semester) VALUES
                                                              (1, 1, 'Fall 2024'),
                                                              (1, 2, 'Fall 2024'),
                                                              (2, 1, 'Fall 2024'),
                                                              (3, 3, 'Fall 2024'),
                                                              (4, 2, 'Fall 2024'),
                                                              (4, 3, 'Fall 2024'),
                                                              (5, 4, 'Fall 2024'),
                                                              (6, 5, 'Fall 2024'),
                                                              (7, 6, 'Fall 2024'),
                                                              (8, 7, 'Fall 2024'),
                                                              (9, 8, 'Fall 2024'),
                                                              (10, 9, 'Fall 2024'),
                                                              (11, 10, 'Fall 2024'),
                                                              (12, 11, 'Fall 2024'),
                                                              (13, 12, 'Fall 2024'),
                                                              (14, 13, 'Fall 2024'),
                                                              (15, 14, 'Fall 2024'),
                                                              (1, 3, 'Spring 2025'),
                                                              (2, 2, 'Spring 2025'),
                                                              (3, 1, 'Spring 2025'),
                                                              (4, 4, 'Spring 2025'),
                                                              (5, 5, 'Spring 2025'),
                                                              (6, 6, 'Spring 2025'),
                                                              (7, 7, 'Spring 2025'),
                                                              (8, 8, 'Spring 2025'),
                                                              (9, 9, 'Spring 2025'),
                                                              (10, 10, 'Spring 2025'),
                                                              (11, 11, 'Spring 2025'),
                                                              (12, 12, 'Spring 2025'),
                                                              (13, 13, 'Spring 2025'),
                                                              (14, 14, 'Spring 2025'),
                                                              (15, 15, 'Spring 2025');


INSERT INTO users (username, password, role) VALUES
('admin', '$2a$10$5z8TlPQeDlxAi1RKLf9ZA.cNn1YiA.PAXzARZHC4C3qH8PV0v8dg.', 'admin'),
('student', '$2a$10$CoCdRkGCPpI0vPO7i3WeOu/db7EGSSrOsEDsj1a1rjbs6tvENGtXe', 'student');