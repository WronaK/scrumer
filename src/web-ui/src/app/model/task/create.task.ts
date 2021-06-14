export interface CreateTask {
  title: string;
  description: string;
  priority: string;
}

export interface Subtasks {
  tasks: CreateTask[];
}
