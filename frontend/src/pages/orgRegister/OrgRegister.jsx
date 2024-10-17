import React, { useState } from "react";
import "./OrgRegister.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { orgRoute } from "../../routes";
import { toast } from "react-toastify";
import { VscLoading } from "react-icons/vsc";

const OrgRegister = () => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    contact: "",
    address: "",
  });
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleRegisterOrganization = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      const res = await axios.post(`${orgRoute}/register`, formData);
      if (res?.data.success) {
        toast.success("Organization registered successfully!");
        navigate("/org");
      }
    } catch (err) {
      toast.error("Failed to register organization. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="org-register">
      <div className="org-registerBox">
        <h1 className="heading">Register an Organization</h1>
        <form onSubmit={handleRegisterOrganization}>
          <input
            className="org-inputBox"
            type="text"
            placeholder="Name"
            name="name"
            value={formData.name}
            onChange={handleInputChange}
          />
          <input
            className="org-inputBox"
            type="email"
            placeholder="Email"
            name="email"
            value={formData.email}
            onChange={handleInputChange}
          />
          <input
            className="org-inputBox"
            type="number"
            placeholder="Contact"
            name="contact"
            value={formData.contact}
            onChange={handleInputChange}
          />
          <textarea
            className="org-inputBox"
            placeholder="Address"
            name="address"
            value={formData.address}
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
      </div>
    </div>
  );
};

export default OrgRegister;
