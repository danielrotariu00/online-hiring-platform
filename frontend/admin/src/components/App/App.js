import { React, useEffect } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import classes from "primereact/resources/primereact.css";
import theme from "primereact/resources/themes/bootstrap4-dark-blue/theme.css";
import "primeicons/primeicons.css";

import Menu from "../Menu/Menu";
import Login from "../Login/Login";
import useToken from "../../hooks/useToken";
import useUserId from "../../hooks/useUserId";
import useUserRoles from "../../hooks/useUserRole";
import Users from "../Users/Users";
import Companies from "../Companies/Companies";

function App() {
  const { token, setToken } = useToken();
  const { userId, setUserId } = useUserId();
  const { userRole, setUserRole } = useUserRoles();

  useEffect(() => {}, []);

  if (!token) {
    return (
      <Login
        setToken={setToken}
        setUserId={setUserId}
        setUserRole={setUserRole}
      />
    );
  }

  return (
    <div className="wrapper">
      <Menu token={token} userRole={userRole} />
      <BrowserRouter>
        <Routes>
          <Route path="/users" element={<Users />} />
          <Route path="/companies" element={<Companies />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
