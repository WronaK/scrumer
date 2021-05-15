import {CreateTeam} from "./create.team";

export interface CreateProject {
  name: string;
  accessCode: string;
  description: string;
  productOwner: string;
  scrumMaster: string;
  teams: CreateTeam[];
}
