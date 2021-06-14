import {JoinTeam} from "../team/join.team";
import {JoinProject} from "./join.project";

export interface CreateProject extends JoinProject {
  description: string;
  productOwner: string;
  scrumMaster: string;
  teams: JoinTeam[];
}
