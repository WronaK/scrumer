import {Task} from "../task/task";

export interface SprintBacklog {
  tasksPBI: Task[],
  tasksTasks: Task[],
  tasksInProgress: Task[],
  tasksMergeRequest: Task[],
  tasksDone: Task[],
}
