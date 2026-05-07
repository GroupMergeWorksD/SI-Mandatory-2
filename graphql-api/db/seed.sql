PRAGMA foreign_keys = ON;

INSERT INTO users (name) VALUES
                             ('Alice'),
                             ('Bob'),
                             ('Charlie');

INSERT INTO projects (name) VALUES
                                ('Web Redesign'),
                                ('Mobile App');

-- Assuming project ids: 1,2 and user ids: 1,2,3
INSERT INTO tasks (project_id, assignee_id, title, status) VALUES
                                                               (1, 1, 'Create wireframes', 'TODO'),
                                                               (1, 2, 'Set up CI', 'DOING'),
                                                               (1, NULL, 'Decide color palette', 'TODO'),
                                                               (2, 3, 'Implement login screen', 'DONE');