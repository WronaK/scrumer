export interface Task {
  id: number;
  taskDetails: TaskDetails
}

export interface TaskDetails {
  id: number
  title: string;
  description: string;
  priority: number;
  storyPoint: string | number;
}
