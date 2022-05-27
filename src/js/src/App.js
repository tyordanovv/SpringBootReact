import React, { Component } from "react";
import Container from "./Container";
import Footer from "./Footer";
import "./App.css";
import { getAllStudents } from "./client";
import AddStudentForm from "./forms/AddStudentForm";
import { Table, Avatar, Spin, Modal } from "antd";
import { LoadingOutlined } from "@ant-design/icons";

const getIcon = () => <LoadingOutlined style={{ fontSize: 24 }} spin />;
class App extends Component {
  state = {
    students: [],
    isFetching: false,
    isStudentModalVisible: false,
  };

  showModal = () => {
    this.setState({ isStudentModalVisible: true });
  };

  closeModal = () => {
    this.setState({ isStudentModalVisible: false });
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
    const { students, isFetching, isStudentModalVisible } = this.state;

    if (isFetching) {
      return (
        <Container>
          <Spin indicator={getIcon()} />
        </Container>
      );
    }

    if (students && students.length) {
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
            dataSource={students}
            columns={columns}
            pagination={false}
            rowKey="studentId"
          />
          <Modal
            title="Add new student"
            visible={isStudentModalVisible}
            onOk={this.closeModal}
            onCancel={this.closeModal}
            width={1000}
          >
            <AddStudentForm />
          </Modal>
          <Footer
            numberOfStudents={students.length}
            handleAddStudentEvent={this.showModal}
          />
        </Container>
      );
    }
  }
}

export default App;
