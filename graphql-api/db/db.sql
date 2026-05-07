PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS users (
         id INTEGER PRIMARY KEY AUTOINCREMENT,
         name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS projects (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS tasks (
         id INTEGER PRIMARY KEY AUTOINCREMENT,
         project_id INTEGER NOT NULL,
         assignee_id INTEGER, -- nullable
         title TEXT NOT NULL,
         status TEXT NOT NULL CHECK (status IN ('TODO','DOING','DONE')),
         created_at TEXT NOT NULL DEFAULT (datetime('now')),

         FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
         FOREIGN KEY (assignee_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_tasks_project ON tasks(project_id);
CREATE INDEX IF NOT EXISTS idx_tasks_status ON tasks(status);