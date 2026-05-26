CREATE DATABASE IF NOT EXISTS student_course_db;
USE student_course_db;

INSERT INTO students (name, email, age) VALUES
('Alice Johnson', 'alice@example.com', 20),
('Bob Smith', 'bob@example.com', 22),
('Charlie Brown', 'charlie@example.com', 19);

INSERT INTO courses (title, instructor, credits) VALUES
('Mathematics', 'Dr. Adams', 3),
('Computer Science', 'Prof. Lee', 4),
('History', 'Dr. Green', 2);

INSERT INTO users (username, password, role) VALUES
('admin', '$2a$10$5z8TlPQeDlxAi1RKLf9ZA.cNn1YiA.PAXzARZHC4C3qH8PV0v8dg.', 'admin'),
('student', '$2a$10$CoCdRkGCPpI0vPO7i3WeOu/db7EGSSrOsEDsj1a1rjbs6tvENGtXe', 'student');