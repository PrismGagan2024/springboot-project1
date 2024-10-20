import React, { useState } from "react";
import "./ToggleEdit.css";
import { toast } from "react-toastify";
import axios from "axios";
import { empRoute, orgRoute } from "../../routes";

const ToggleEdit = ({ handleToggleEdit, data, toggleFor }) => {
  const [formData, setFormData] = useState(
    toggleFor === "emp"
      ? {
          firstName: data.firstName || "",
          lastName: data.lastName || "",
          email: data.email || "",
          contact: data.contact || "",
          address: data.address || "",
        }
      : {
          name: data.name || "",
          email: data.email || "",
          contact: data.contact || "",
          address: data.address || "",
        }
  );

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const route = toggleFor === "emp" ? empRoute : orgRoute;
    const url = toggleFor === "emp" ? data?.id : data?.orgId;

    try {
      const res = await axios.put(`${route}/edit/${url}`, formData);
      if (res?.data.success) {
        toast.success(
          `${
            toggleFor === "emp" ? "Employee" : "Organization"
          } updated successfully!`
        );

        handleToggleEdit();
      }
    } catch (err) {
      toast.error(`Failed to update ${toggleFor}. Please try again later.`);
    }
  };

  return (
    <div className="toggle-edit">
      <div className="toggle-edit-content">
        <h2>
          Edit {toggleFor === "emp" ? "Employee" : "Organization"} Details
        </h2>
        <form onSubmit={handleSubmit}>
          {toggleFor === "emp" ? (
            <>
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
            </>
          ) : (
            <>
              <div className="form-group">
                <label>Organization Name</label>
                <input
                  type="text"
                  name="name"
                  value={formData.name}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="form-group">
                <label>Organization Email</label>
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
            </>
          )}
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
