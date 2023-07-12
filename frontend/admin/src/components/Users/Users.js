import React, { useState, useEffect } from "react";
import { Card } from "primereact/card";
import { Button } from "primereact/button";
import { Splitter, SplitterPanel } from "primereact/splitter";
import useToken from "../../hooks/useToken";
import useUserRole from "../../hooks/useUserRole";

export default function Users() {
  const { token } = useToken();
  const { userRole } = useUserRole();
  const administratorRole = 4;

  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const roleNames = ["None", "Candidate", "Recruiter", "Manager", "Admin"];

  useEffect(() => {
    getUsers();
  }, []);

  const getUsers = () => {
    // let url = process.env.REACT_APP_API_URL + "/users";
    let url = "http://localhost:5000/api/users";

    const requestOptions = {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };

    fetch(url, requestOptions)
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        setUsers(data);
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  const onDeleteUserClick = (userId) => {
    // let url = process.env.REACT_APP_API_URL + "/users";
    let url = "http://localhost:5000/api/users";

    const requestOptions = {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    };
    fetch(`${url}/${userId}`, requestOptions).then(() => getUsers());
  };

  if (!(userRole === administratorRole)) {
    return <h2>You are not authorized to view this page</h2>;
  }

  return (
    <div>
      <Splitter
        style={{
          height: "93.5vh",
          backgroundColor: "black",
        }}
        layout="horizontal"
      >
        <SplitterPanel
          style={{ overflowY: "scroll" }}
          className="flex align-items-center justify-content-center"
        >
          <h2>User List</h2>
          <ul>
            {users.map((user) => (
              <div>
                <Card
                  title={user.email}
                  style={{ height: "8em", width: "30em" }}
                >
                  <div>
                    <Button
                      label="View Details"
                      onClick={() => {
                        setSelectedUser(user);
                      }}
                    />
                    &emsp;
                    <Button
                      label="Delete"
                      onClick={() => {
                        onDeleteUserClick(user.id);
                      }}
                    />
                  </div>
                </Card>
                <br />
              </div>
            ))}
          </ul>
        </SplitterPanel>
        <SplitterPanel className="flex align-items-center justify-content-center">
          <h2>User Details</h2>

          {selectedUser && (
            <Card
              title={
                <div>
                  <h3>Id: {selectedUser?.id}</h3>
                  <h3>Email: {selectedUser?.email}</h3>
                  <h3>Role: {roleNames[selectedUser?.userRole?.roleId]}</h3>
                  <h3>
                    CompanyId:{" "}
                    {selectedUser?.userRole?.companyId == 0
                      ? "None"
                      : selectedUser?.userRole?.companyId}
                  </h3>
                </div>
              }
              style={{ height: "20em", width: "30em" }}
            ></Card>
          )}
        </SplitterPanel>
      </Splitter>
    </div>
  );
}
