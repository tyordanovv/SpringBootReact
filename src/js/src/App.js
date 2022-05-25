import React, { Component } from "react";
import Container from "./Container";
import "./App.css";
import { getAllStudents } from "./client";
import { Table, Avatar, Spin } from "antd";
import { LoadingOutlined } from "@ant-design/icons";

class App extends Component {
  state = {
    students: [],
    isFetching: false,
  };

  componentDidMount() {
    this.fetchStudents();
  }

  fetchStudents = () => {
    this.setState({
      isFetching: true,
    });
    getAllStudents().then((res) =>
      res.json().then((students) => {
        console.log(students);
        this.setState({
          students,
          isFetching: false,
        });
      })
    );
  };

  render() {
    const state = this.state;
    const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;

    if (state.isFetching) {
      return (
        <Container>
          <Spin indicator={antIcon} />
        </Container>
      );
    }

    if (state.students && state.students.length) {
      const columns = [
        {
          title: "",
          key: "avatar",
          render: (text, student) => (
            <Avatar size="large">
              {`
              ${student.firstName.charAt(0).toUpperCase()}${student.lastName
                .charAt(0)
                .toUpperCase()}
              `}
            </Avatar>
          ),
        },
        {
          title: "Student Id",
          dataIndex: "studentId",
          key: "studentId",
        },
        {
          title: "First Name",
          dataIndex: "firstName",
          key: "firstName",
        },
        {
          title: "LastName",
          dataIndex: "lastName",
          key: "lastName",
        },
        {
          title: "Email",
          dataIndex: "email",
          key: "email",
        },
        {
          title: "Gender",
          dataIndex: "gender",
          key: "gender",
        },
      ];

      return (
        <Container>
          <Table
            dataSource={state.students}
            columns={columns}
            pagination={false}
            rowKey="studentId"
          />
        </Container>
      );
    }
    return <h1>No students found</h1>;
  }
}

export default App;
