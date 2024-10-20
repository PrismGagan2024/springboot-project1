import React, { useState } from "react";
import "./EmpLogin.css";
import axios from "axios";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import { empRoute } from "../../routes";
import { toast } from "react-toastify";
import { VscLoading } from "react-icons/vsc";
import { useEmployeeContext } from "../../context/EmpContext";

const EmpLogin = () => {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const { setEmployee } = useEmployeeContext();

  const orgId = searchParams.get("org");

  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleloginEmployee = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      const res = await axios.post(`${empRoute}/login?org=${orgId}`, formData);
      if (res?.data.success) {
        setEmployee(res?.data.data);
        toast.success("Employee login successfully!");
        navigate(`/emp?org=${orgId}`);
      }
    } catch (err) {
      toast.error("Failed to login employee. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="emp-login">
      <div className="emp-loginBox">
        <h1 className="heading">Login an Employee</h1>
        <form onSubmit={handleloginEmployee}>
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
              "Login"
            )}
          </button>
        </form>
        <p>
          Don't have an account?{" "}
          <Link className="link" to={`/emp/register?org=${orgId}`}>
            Register
          </Link>
        </p>
      </div>
    </div>
  );
};

export default EmpLogin;
