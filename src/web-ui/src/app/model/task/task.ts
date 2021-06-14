import {CreateTask} from "./create.task";

export interface Task extends CreateTask {
  id: number;
  storyPoints: string | number;
  status: string;
}

