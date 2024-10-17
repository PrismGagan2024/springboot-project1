import React from "react";
import "./OrgBox.css";
import { FiEdit3 } from "react-icons/fi";

const OrgBox = ({ org }) => {
  return (
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
          <button className="org-box-button">Employee</button>
          <button className="org-edit-button">
            <FiEdit3 size={24} />
          </button>
        </div>
      </div>
    </div>
  );
};

export default OrgBox;
