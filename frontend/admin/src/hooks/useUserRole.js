import { useState } from "react";

export default function useUserRole() {
  const getUserRole = () => {
    const userRoleString = localStorage.getItem("userRole");
    const userRole = JSON.parse(userRoleString);
    return userRole?.userRole;
  };

  const [userRole, setUserRole] = useState(getUserRole());

  const saveUserRole = (userRole) => {
    localStorage.setItem("userRole", JSON.stringify(userRole));
    setUserRole(userRole.userRole);
  };

  return {
    setUserRole: saveUserRole,
    userRole,
  };
}
