import React, { useState } from "react";
import "./ToggleEdit.css";
import { toast } from "react-toastify";
import axios from "axios";
import { empRoute } from "../../routes";

const ToggleEdit = ({ handleToggleEdit, employee }) => {
  const [formData, setFormData] = useState({
    firstName: employee.firstName,
    lastName: employee.lastName,
    email: employee.email,
    contact: employee.contact,
    address: employee.address,
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const res = await axios.put(`${empRoute}/edit/${employee?.id}`, formData);
      if (res?.data.success) {
        toast.success("Employee updated successfully!");
        handleToggleEdit();
      }
    } catch (err) {
      toast.error("Failed to update employee. Please try again later.");
    }
  };

  return (
    <div className="toggle-edit">
      <div className="toggle-edit-content">
        <h2>Edit Employee Details</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>First Name</label>
            <input
              type="text"
              name="firstName"
              value={formData.firstName}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label>Last Name</label>
            <input
              type="text"
              name="lastName"
              value={formData.lastName}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label>Email</label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label>Contact</label>
            <input
              type="text"
              name="contact"
              value={formData.contact}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label>Address</label>
            <textarea
              name="address"
              value={formData.address}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-actions">
            <button type="submit" className="save-button">
              Save
            </button>
            <button
              type="button"
              className="cancel-button"
              onClick={handleToggleEdit}
            >
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ToggleEdit;
