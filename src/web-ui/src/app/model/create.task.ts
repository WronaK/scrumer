export interface CreateTask {
  title: string;
  description: string;
  priority: number;
}

export interface Subtasks {
  tasks: CreateTask[];
}
