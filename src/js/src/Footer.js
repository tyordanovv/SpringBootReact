<<<<<<< HEAD
import React from "react";
import { Avatar, Button } from "antd";
import Container from "./Container";
import "./Footer.css";

const Footer = (props) => (
  <div className="footer">
    <Container>
      {props.numberOfStudents ? (
        <Avatar
          style={{ backgroundColor: "#f56a00", marginRight: "5px" }}
          size="large"
        >
          {props.numberOfStudents}
        </Avatar>
      ) : null}
      <Button type="primary" onClick={() => props.handleAddStudentEvent()}>
        Add new student
      </Button>
=======
import { Button } from "antd";
import React from "react";
import Container from "./Container";

const Footer = (props) => (
  <div>
    <Container>
      <Button type="primary">Add new student</Button>
>>>>>>> frontend
    </Container>
  </div>
);

export default Footer;
