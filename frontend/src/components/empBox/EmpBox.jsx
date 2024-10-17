import React, { useState } from "react";
import "./EmpBox.css";
import { FiEdit3 } from "react-icons/fi";
import { useEmployeeContext } from "../../context/EmpContext";
import ToggleEdit from "../toggleEdit/ToggleEdit";

const EmpBox = ({ emp, handleDeleteEmployee }) => {
  const { employee } = useEmployeeContext();
  const [openEditToggle, setOpenEditToggle] = useState(false);

  const handleToggleEdit = () => {
    setOpenEditToggle(false);
  };

  return (
    <>
      <div className="emp-box">
        <div className="emp-box-content">
          <h2 className="emp-name">
            {emp.firstName} {emp.lastName}
          </h2>
          <div className="emp-details">
            <p className="emp-email">
              <strong>Email:</strong> <span>{emp.email}</span>
            </p>
            <p className="emp-contact">
              <strong>Contact:</strong> <span>{emp.contact}</span>
            </p>
            <p className="emp-address">
              <strong>Address:</strong> <span>{emp.address}</span>
            </p>
          </div>

          {employee?.id == emp?.id && (
            <div className="emp-box-buttons">
              <button
                className="emp-box-button"
                onClick={() => handleDeleteEmployee(emp?.id)}
              >
                Delete
              </button>
              <button
                className="emp-edit-button"
                onClick={() => setOpenEditToggle(true)}
              >
                <FiEdit3 size={24} />
              </button>
            </div>
          )}
        </div>
      </div>

      {openEditToggle && (
        <ToggleEdit handleToggleEdit={handleToggleEdit} employee={emp} />
      )}
    </>
  );
};

export default EmpBox;
