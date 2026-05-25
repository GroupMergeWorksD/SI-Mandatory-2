from __future__ import annotations

from enum import Enum
from typing import Optional, List
import strawberry

from .db import get_conn


@strawberry.enum
class TaskStatus(Enum):
    TODO = "TODO"
    DOING = "DOING"
    DONE = "DONE"


@strawberry.type
class User:
    id: strawberry.ID
    name: str


@strawberry.type
class Project:
    id: strawberry.ID
    name: str


@strawberry.type
class Task:
    id: strawberry.ID
    title: str
    status: TaskStatus

    project_id: strawberry.Private[int]
    assignee_id: strawberry.Private[Optional[int]]

    @strawberry.field
    def project(self) -> Project:
        with get_conn() as conn:
            row = conn.execute(
                "SELECT id, name FROM projects WHERE id = ?",
                (self.project_id,),
            ).fetchone()
            if row is None:
                raise ValueError(f"Project {self.project_id} not found")
            return Project(id=str(row["id"]), name=row["name"])

    @strawberry.field
    def assignee(self) -> Optional[User]:
        if self.assignee_id is None:
            return None
        with get_conn() as conn:
            row = conn.execute(
                "SELECT id, name FROM users WHERE id = ?",
                (self.assignee_id,),
            ).fetchone()
            return User(id=str(row["id"]), name=row["name"]) if row else None


@strawberry.input
class CreateTaskInput:
    project_id: strawberry.ID = strawberry.field(name="projectId")
    title: str
    assignee_id: Optional[strawberry.ID] = strawberry.field(default=None, name="assigneeId")

@strawberry.input
class CreateProjectInput:
    name: str

@strawberry.input
class CreateUserInput:
    name: str


def _row_to_task(row) -> Task:
    return Task(
        id=row["id"],
        title=row["title"],
        status=TaskStatus(row["status"]),
        project_id=int(row["project_id"]),
        assignee_id=int(row["assignee_id"]) if row["assignee_id"] is not None else None,
    )

def _row_to_project(row) -> Project:
    return Project(
        id=row["id"],
        name=row["name"],
    )
def _row_to_user(row) -> User:
    return User(
        id=row["id"],
        name=row["name"],
    )


@strawberry.type
class Query:
    @strawberry.field(name="users")
    def users(self) -> List[User]:
        with get_conn() as conn:
            rows = conn.execute("SELECT id, name FROM users ORDER BY id").fetchall()
            return [User(id=str(r["id"]), name=r["name"]) for r in rows]

    @strawberry.field
    def projects(self) -> List[Project]:
        with get_conn() as conn:
            rows = conn.execute("SELECT id, name FROM projects ORDER BY id").fetchall()
            return [Project(id=str(r["id"]), name=r["name"]) for r in rows]

    @strawberry.field(name="tasks")
    def tasks(
            self,
            project_id: strawberry.ID,
            status: Optional[TaskStatus] = None,
    ) -> List[Task]:
        sql = """
              SELECT id, title, status, project_id, assignee_id
              FROM tasks
              WHERE project_id = ? \
              """
        params = [int(project_id)]
        if status is not None:
            sql += " AND status = ?"
            params.append(status.value)
        sql += " ORDER BY id"

        with get_conn() as conn:
            rows = conn.execute(sql, params).fetchall()
            return [_row_to_task(r) for r in rows]


@strawberry.type
class Mutation:
    @strawberry.mutation(name="createUser")
    def create_user(self, input: CreateUserInput) -> User:
        with get_conn() as conn:
            cur = conn.execute(
                """
                INSERT INTO users (name)
                    VALUES (?)
                """, (
                    input.name,
                ),
            )
            user_id = cur.lastrowid
            row = conn.execute(
                """
                SELECT id, name FROM users WHERE id = ?
                """, (user_id,),).fetchone()
            return _row_to_user(row)

    @strawberry.mutation(name="createProject")
    def create_project(self, input: CreateProjectInput) -> Project:
        with get_conn() as conn:
            cur = conn.execute(
                """
                INSERT INTO projects (name)
                    VALUES (?)
                """, (
                    input.name,
                ),
            )
            project_id = cur.lastrowid
            row = conn.execute(
                """
                SELECT id, name FROM projects WHERE id = ?
                """
            , (project_id,),).fetchone()
            return _row_to_project(row)

    @strawberry.mutation(name="createTask")
    def create_task(self, input: CreateTaskInput) -> Task:
        with get_conn() as conn:
            cur = conn.execute(
                """
                INSERT INTO tasks (project_id, assignee_id, title, status)
                VALUES (?, ?, ?, 'TODO')
                """,
                (
                    int(input.project_id),
                    int(input.assignee_id) if input.assignee_id is not None else None,
                    input.title,
                ),
            )
            task_id = cur.lastrowid
            row = conn.execute(
                """
                SELECT id, title, status, project_id, assignee_id
                FROM tasks
                WHERE id = ?
                """,
                (task_id,),
            ).fetchone()
            return _row_to_task(row)

    @strawberry.mutation(name="updateTaskStatus")
    def update_task_status(
            self,
            task_id: strawberry.ID,
            status: TaskStatus,
    ) -> Task:
        with get_conn() as conn:
            conn.execute(
                "UPDATE tasks SET status = ? WHERE id = ?",
                (status.value, int(task_id)),
            )
            row = conn.execute(
                """
                SELECT id, title, status, project_id, assignee_id
                FROM tasks
                WHERE id = ?
                """,
                (int(task_id),),
            ).fetchone()
            if row is None:
                raise ValueError(f"Task {task_id} not found")
            return _row_to_task(row)


schema = strawberry.Schema(query=Query, mutation=Mutation)