import {Project} from "./project";

export interface UpdateProject extends Project {
  accessCode: string;
  description: string;
  productOwner: string;
  scrumMaster: string;
}
