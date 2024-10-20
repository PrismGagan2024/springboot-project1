import React, { useState } from "react";
import "./OrgBox.css";
import { FiEdit3 } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import ToggleEdit from "../toggleEdit/ToggleEdit";

const OrgBox = ({ org }) => {
  const navigate = useNavigate();
  const [openEditToggle, setOpenEditToggle] = useState(false);
  const toggleFor = "org";

  const handleRegisterEmployee = () => {
    navigate(`/emp/register?org=${org?.orgId}`);
  };

  const handleToggleEdit = () => {
    setOpenEditToggle(false);
  };

  return (
    <>
      <div className="org-box">
        <div className="org-box-content">
          <h2 className="org-name">{org.name}</h2>
          <div className="org-details">
            <p className="org-email">
              <strong>Email:</strong> <span>{org.email}</span>
            </p>
            <p className="org-contact">
              <strong>Contact:</strong> <span>{org.contact}</span>
            </p>
            <p className="org-address">
              <strong>Address:</strong> <span>{org.address}</span>
            </p>
          </div>

          <div className="org-box-buttons">
            <button className="org-box-button" onClick={handleRegisterEmployee}>
              Employee
            </button>
            <button
              className="org-edit-button"
              onClick={() => setOpenEditToggle(true)}
            >
              <FiEdit3 size={24} />
            </button>
          </div>
        </div>
      </div>
      {openEditToggle && (
        <ToggleEdit
          handleToggleEdit={handleToggleEdit}
          data={org}
          toggleFor={toggleFor}
        />
      )}
    </>
  );
};

export default OrgBox;
