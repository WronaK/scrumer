import {User} from "./user";

export interface LoginUser extends User {
  roles: string;
}
