import { UserRole } from "./user_role";

export class User {
  id?: number;
  email?: string;
  password?: string;
  token?: string;
  userRole?: UserRole;
  companyId?: number;
  isCandidate?: boolean;
  isRecruiter?: boolean;
  isManager?: boolean;
}
