import {JoinTeam} from "./join.team";

export interface CreateProject {
  name: string;
  accessCode: string;
  description: string;
  productOwner: string;
  scrumMaster: string;
  teams: JoinTeam[];
}
