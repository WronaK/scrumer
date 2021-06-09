import {Member} from "./member";

export interface CreateTeam {
  name: string;
  accessCode: string;
  members: Member[]
}
