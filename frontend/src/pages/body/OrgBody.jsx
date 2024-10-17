import React from "react";
import { Outlet } from "react-router-dom";

const OrgBody = () => {
  return (
    <div className="body">
      <Outlet />
    </div>
  );
};

export default OrgBody;
