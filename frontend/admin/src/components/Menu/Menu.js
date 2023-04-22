import React from "react";
import { Menubar } from "primereact/menubar";
import useToken from "../../hooks/useToken";
import useUserId from "../../hooks/useUserId";
import useUserRole from "../../hooks/useUserRole";

export default function Menu() {
  const { token, setToken } = useToken();
  const { setUserId } = useUserId();
  const { userRole, setUserRole } = useUserRole();

  const administratorRole = 4;

  const menuItems = [];

  const administratorMenu = [
    {
      label: "Users",
      url: "/users",
    },
    {
      label: "Companies",
      url: "/companies",
    },
  ];

  const onLogoutClick = () => {
    let url = "http://localhost:5000/api/logout";

    fetch(url, {
      method: "post",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        token: token,
      }),
    })
      .then((response) => response.json())
      .then((data) => {
        setToken({ token: null });
        setUserId({ userId: null });
        setUserRole({ userRole: null });
        window.location.reload(true);
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  const logoutMenu = {
    label: "Logout",
    command: () => onLogoutClick(),
  };

  if (userRole === administratorRole) {
    menuItems.push(...administratorMenu);
  }

  menuItems.push(logoutMenu);

  return (
    <div>
      <div className="card">
        <Menubar model={menuItems} />
      </div>
    </div>
  );
}
