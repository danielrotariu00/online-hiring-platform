import React, { useState } from "react";
import "jquery.soap";

import PropTypes from "prop-types";
import "./Login.css";

export default function Login({ setToken, setUserId, setUserRole }) {
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  let token = null;

  const handleSubmit = (e) => {
    e.preventDefault();

    //let url = process.env.REACT_APP_API_URL + "/login";
    let url = "http://localhost:5000/api/login";

    fetch(url, {
      method: "post",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        email: email,
        password: password,
      }),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        let token = data.token;
        let userId = data.id;
        let userRole = data.userRole;

        setToken({
          token,
        });
        setUserId({
          userId: userId,
        });

        let roleId = userRole.roleId;
        setUserRole({
          userRole: roleId,
        });

        // window.location.reload(true);
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  return (
    <div className="login-wrapper">
      <h1>Please Log In</h1>
      <form onSubmit={handleSubmit}>
        <label>
          <p>Email</p>
          <input type="text" onChange={(e) => setEmail(e.target.value)} />
        </label>
        <label>
          <p>Password</p>
          <input
            type="password"
            onChange={(e) => setPassword(e.target.value)}
          />
        </label>
        <div>
          <button type="submit">Submit</button>
        </div>
      </form>
    </div>
  );
}

Login.propTypes = {
  setToken: PropTypes.func.isRequired,
  setUserId: PropTypes.func.isRequired,
  setUserRoles: PropTypes.func.isRequired,
};
