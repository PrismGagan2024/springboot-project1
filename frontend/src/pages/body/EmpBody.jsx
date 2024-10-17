import React from "react";
import { Outlet } from "react-router-dom";

const EmpBody = () => {
  return (
    <div className="body">
      <Outlet />
    </div>
  );
};

export default EmpBody;
