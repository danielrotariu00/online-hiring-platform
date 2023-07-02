import React, { useState, useEffect } from "react";
import { Card } from "primereact/card";
import { Button } from "primereact/button";
import { Splitter, SplitterPanel } from "primereact/splitter";
import { Dialog } from "primereact/dialog";
import { InputText } from "primereact/inputtext";
import useToken from "../../hooks/useToken";
import useUserRole from "../../hooks/useUserRole";

export default function Users() {
  const { token } = useToken();
  const { userRole } = useUserRole();
  const administratorRole = 4;

  const [companies, setCompanies] = useState([]);
  const [selectedCompany, setSelectedCompany] = useState(null);

  const [displayForm, setDisplayForm] = useState(false);
  const [newName, setNewName] = useState(null);
  const [newEmail, setNewEmail] = useState(null);
  const [newPassword, setNewPassword] = useState(null);

  useEffect(() => {
    getCompanies();
  }, []);

  const getCompanies = () => {
    // let url = process.env.REACT_APP_API_URL + "/companies";
    let url = "http://localhost:5000/database-api/companies";

    const requestOptions = {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    };

    fetch(url, requestOptions)
      .then((response) => response.json())
      .then((companies) => {
        setCompanies(companies);
        for (let i = 0; i < companies.length; i++) {
          let companyId = companies[i].id;
          companies[i]["managers"] = [];

          fetch(url + `/${companyId}/managers`, requestOptions)
            .then((response) => response.json())
            .then((managers) => {
              for (let j = 0; j < managers.length; j++) {
                let managerId = managers[j].managerId;

                let usersUrl = "http://localhost:5000/api/users";
                fetch(usersUrl + `/${managerId}`, requestOptions)
                  .then((response) => response.json())
                  .then((manager) => {
                    console.log(manager);
                    companies[i]["managers"].push({
                      id: managerId,
                      email: manager.email,
                    });
                    console.log(companies[i]);
                    setCompanies(companies);
                  })
                  .catch((err) => {
                    console.log(err.message);
                  });
              }
            })
            .catch((err) => {
              console.log(err.message);
            });
        }
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  const onDeleteCompanyClick = (companyId) => {
    // let url = process.env.REACT_APP_API_URL + "/companies";
    let url = "http://localhost:5000/database-api/companies";

    const requestOptions = {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    };
    fetch(`${url}/${companyId}`, requestOptions).then(() => getCompanies());
  };

  const onDeleteManagerClick = (companyId, managerId) => {
    // let url = process.env.REACT_APP_API_URL + "/companies";
    let url = "http://localhost:5000/database-api/companies";

    const requestOptions = {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    };
    fetch(`${url}/${companyId}/managers/${managerId}`, requestOptions).then(
      () => getCompanies()
    );
  };

  const onConfirmClick = () => {
    let re =
      /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (re.test(newEmail)) {
      if (newPassword.length < 6) {
        alert("Password is too short!");
      } else {
        let companyManagersURL = "http://localhost:5000/api/managers";
        const requestOptions = {
          method: "POST",
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            email: newEmail,
            password: newPassword,
            companyId: selectedCompany.id,
          }),
        };
        fetch(companyManagersURL, requestOptions)
          .then((response) => response.json())
          .then((_data) => getCompanies());
        onHide();
      }
    } else {
      alert("Invalid email!");
    }
  };

  const onCreateClick = () => {
    let companiesUrl = "http://localhost:5000/database-api/companies";
    const requestOptions = {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name: newName,
      }),
    };
    fetch(companiesUrl, requestOptions).then((_data) => getCompanies());
  };

  const onAddClick = () => {
    setDisplayForm(true);
  };

  const onHide = () => {
    setDisplayForm(false);
  };

  const renderFooter = () => {
    return (
      <div>
        <Button
          label="Cancel"
          icon="pi pi-times"
          onClick={() => onHide()}
          className="p-button-text"
        />
        <Button
          label="Add"
          icon="pi pi-check"
          onClick={() => {
            onConfirmClick();
          }}
          autoFocus
        />
      </div>
    );
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
          <h2>New Company</h2>
          <h3>Name</h3>
          <InputText
            placeholder="Name"
            onChange={(e) => setNewName(e.target.value)}
          />
          <br />
          <br />
          <Button label="Create company" onClick={onCreateClick} />
          <br />
          <br />
          <h2>Companies</h2>
          <ul>
            {companies.map((company) => (
              <div>
                <Card
                  title={company.name}
                  style={{ height: "8em", width: "20em" }}
                >
                  <div>
                    <Button
                      label="View Details"
                      onClick={() => {
                        setSelectedCompany(company);
                      }}
                    />
                    &emsp;
                    <Button
                      label="Delete"
                      onClick={() => {
                        onDeleteCompanyClick(company.id);
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
          <h2>Company Details</h2>
          <h3>Id: {selectedCompany?.id}</h3>
          <h3>Name: {selectedCompany?.name}</h3>
          <h3>Managers:</h3>
          <ul>
            {selectedCompany?.managers.map((manager) => (
              <div>
                <Card
                  title={
                    <div>
                      {manager.id}: {manager.email}
                    </div>
                  }
                  style={{ height: "8em", width: "30em" }}
                >
                  <div>
                    <Button
                      label="Delete"
                      onClick={() => {
                        onDeleteManagerClick(selectedCompany.id, manager.id);
                      }}
                    />
                  </div>
                </Card>
                <br />
              </div>
            ))}
          </ul>
          <Button label="Add Manager" onClick={onAddClick} />
          <Dialog
            header="New Manager"
            visible={displayForm}
            style={{ width: "30vw" }}
            footer={renderFooter()}
            onHide={() => onHide()}
          >
            <h3>Email</h3>
            <InputText
              placeholder="Email"
              onChange={(e) => setNewEmail(e.target.value)}
            />
            <br />
            <br />
            <h3>Password</h3>
            <InputText
              type={"password"}
              placeholder="Password"
              onChange={(e) => setNewPassword(e.target.value)}
            />
            <br />
            <br />
          </Dialog>
        </SplitterPanel>
      </Splitter>
    </div>
  );
}
