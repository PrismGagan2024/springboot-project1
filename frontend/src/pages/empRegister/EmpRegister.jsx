import React, { useState } from "react";
import "./EmpRegister.css";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import { empRoute } from "../../routes";
import { toast } from "react-toastify";
import { VscLoading } from "react-icons/vsc";

const EmpRegister = () => {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    contact: "",
    address: "",
    password: "",
  });
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleRegisterEmployee = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      const res = await axios.post(`${empRoute}/register`, formData);
      if (res?.data.success) {
        toast.success("Employee registered successfully!");
        navigate("/emp/login");
      }
    } catch (err) {
      toast.error("Failed to register employee. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="emp-register">
      <div className="emp-registerBox">
        <h1 className="heading">Register an Employee</h1>
        <form onSubmit={handleRegisterEmployee}>
          <input
            className="emp-inputBox"
            type="text"
            placeholder="First Name"
            name="firstName"
            value={formData.firstName}
            onChange={handleInputChange}
          />
          <input
            className="emp-inputBox"
            type="text"
            placeholder="Last Name"
            name="lastName"
            value={formData.lastName}
            onChange={handleInputChange}
          />
          <input
            className="emp-inputBox"
            type="email"
            placeholder="Email"
            name="email"
            value={formData.email}
            onChange={handleInputChange}
          />
          <input
            className="emp-inputBox"
            type="number"
            placeholder="Contact"
            name="contact"
            value={formData.contact}
            onChange={handleInputChange}
          />
          <textarea
            className="emp-inputBox"
            placeholder="Address"
            name="address"
            value={formData.address}
            onChange={handleInputChange}
          />
          <input
            className="emp-inputBox"
            type="password"
            placeholder="Password"
            name="password"
            value={formData.password}
            onChange={handleInputChange}
          />
          <button type="submit" disabled={loading}>
            {loading ? (
              <VscLoading size="24" color="#fff" className="loading" />
            ) : (
              "Register"
            )}
          </button>
        </form>
        <p>
          Already have an account?{" "}
          <Link className="link" to={"/emp/login"}>
            Login
          </Link>
        </p>
      </div>
    </div>
  );
};

export default EmpRegister;
