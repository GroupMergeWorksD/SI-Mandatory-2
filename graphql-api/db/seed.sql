PRAGMA foreign_keys = ON;

INSERT INTO users (name) VALUES
                             ('Alice'),
                             ('Bob'),
                             ('Charlie'),
                                ('Diana'),
                                ('Eve'),
                                ('Frank'),
                                ('Grace'),
                                ('Heidi'),
                                ('Ivan'),
                                ('Judy');

INSERT INTO projects (name) VALUES
                                ('Web Redesign'),
                                ('Mobile App'),
                                ('Marketing Campaign'),
                                ('Customer Support'),
                                ('Data Analysis'),
                                ('Product Launch'),
                                ('Internal Tools'),
                                ('Security Audit'),
                                ('Performance Optimization'),
                                ('User Research');

-- Assuming project ids: 1,2 and user ids: 1,2,3
INSERT INTO tasks (project_id, assignee_id, title, status) VALUES
                                                               (1, 1, 'Create wireframes', 'TODO'),
                                                               (1, 2, 'Set up CI', 'DOING'),
                                                               (1, NULL, 'Decide color palette', 'TODO'),
                                                               (2, 3, 'Implement login screen', 'DONE'),
                                                                (2, 1, 'Design database schema', 'DOING'),
                                                                (3, NULL, 'Draft email copy', 'TODO'),
                                                                (3, 2, 'Create social media graphics', 'DOING'),
                                                                (4, 3, 'Respond to support tickets', 'DONE'),
                                                                (4, NULL, 'Update FAQ', 'TODO'),
                                                                (5, 1, 'Clean and preprocess data', 'DOING'),
                                                                (5, 2, 'Create visualizations', 'TODO'),
                                                                (6, 3, 'Plan launch event', 'DONE'),
                                                                (6, NULL, 'Coordinate with PR team', 'TODO'),
                                                                (7, 1, 'Build internal dashboard', 'DOING'),
                                                                (7, 2, 'Integrate with existing tools', 'TODO'),
                                                                (8, 3, 'Conduct security audit', 'DONE'),
                                                                (8, NULL, 'Fix identified vulnerabilities', 'TODO'),
                                                                (9, 1, 'Profile application performance', 'DOING'),
                                                                (9, 2, 'Optimize database queries', 'TODO'),
                                                                (10, 3, 'Conduct user interviews', 'DONE'),
                                                                (10, NULL, 'Analyze user feedback', 'TODO');