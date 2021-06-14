import {Member} from "../user/member";
import {JoinTeam} from "./join.team";

export interface CreateTeam extends JoinTeam {
  members: Member[]
}
