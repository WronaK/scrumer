import {Task} from "./task";

export interface SprintBacklog {
  tasksPBI: Task[],
  tasksTasks: Task[],
  tasksInProgress: Task[],
  tasksMergeRequest: Task[],
  tasksDone: Task[],
}
