import React from "react";
import { Formik } from "formik";
import { Input, Button, Tag } from "antd";
import { addNewStudent } from "../client";

const InputBottomMargin = { marginBottom: "10px" };

const tagStyle = {
  backgroundColor: "#f50",
  color: "white",
  ...InputBottomMargin,
};

const AddStudentForm = () => (
  <div>
    <h1>Anywhere in your app!</h1>
    <Formik
      initialValues={{ firstName: "", lastName: "", email: "", gender: "" }}
      validate={(values) => {
        const errors = {};

        if (!values.firstName) {
          errors.firstName = "First Name Required";
        }
        if (!values.lastName) {
          errors.lastName = "Last Name Required";
        }
        if (!values.email) {
          errors.email = "Email Required";
        } else if (
          !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(values.email)
        ) {
          errors.email = "Invalid email address";
        }
        if (!values.gender) {
          errors.gender = "Gender Requiered";
        } else if (
          !["Male", "male", "Female", "FEMALE"].includes(values.gender)
        ) {
          errors.gender = "Gender must be MALE, male, FEMALE or female";
        }

        return errors;
      }}
      onSubmit={(values, { setSubmitting }) => {
        // addNewStudent(student).then(() => {
        //   alert(JSON.stringify(student));
        // });
        setTimeout(() => {
          alert(JSON.stringify(values, null, 2));
          setSubmitting(false);
        }, 400);
      }}
    >
      {({
        values,
        errors,
        touched,
        handleChange,
        handleBlur,
        handleSubmit,
        isSubmitting,
        submitForm,
        isValid,
        /* and other goodies */
      }) => (
        <form onSubmit={handleSubmit}>
          <Input
            style={InputBottomMargin}
            name="firstName"
            onChange={handleChange}
            onBlur={handleBlur}
            value={values.firstName}
            placeholder="First Name. E.g John"
          />
          {errors.firstName && touched.firstName && (
            <Tag style={tagStyle}>{errors.firstName}</Tag>
          )}
          <Input
            style={InputBottomMargin}
            name="lastName"
            onChange={handleChange}
            onBlur={handleBlur}
            value={values.lastName}
            placeholder="Last Name. E.g Smith"
          />
          {errors.lastName && touched.lastName && (
            <Tag style={tagStyle}>{errors.lastName}</Tag>
          )}
          <Input
            style={InputBottomMargin}
            name="email"
            type="email"
            onChange={handleChange}
            onBlur={handleBlur}
            value={values.email}
            placeholder="Email E.g example@gmail.com"
          />
          {errors.email && touched.email && (
            <Tag style={tagStyle}>{errors.email}</Tag>
          )}
          <Input
            style={InputBottomMargin}
            name="gender"
            onChange={handleChange}
            onBlur={handleBlur}
            value={values.gender}
            placeholder="Gender. E.g Male or Female"
          />
          {errors.gender && touched.gender && (
            <Tag style={tagStyle}>{errors.gender}</Tag>
          )}
          <Button
            onClick={() => submitForm()}
            type="submit"
            disabled={isSubmitting | (touched && !isValid)}
          >
            Submit
          </Button>
        </form>
      )}
      !
    </Formik>
  </div>
);

export default AddStudentForm;
