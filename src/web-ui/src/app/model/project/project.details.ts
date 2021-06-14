import {Project} from "./project";

export interface ProjectDetails extends Project {
  accessCode: string;
  description: string;
  creatorName: string;
  productOwnerName: string;
  scrumMasterName: string;
}

