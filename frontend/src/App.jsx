import React from "react";
import "./App.css";
import { Routes, Route } from "react-router-dom";
import OrgBody from "./pages/body/OrgBody";
import OrgRegister from "./pages/orgRegister/OrgRegister";
import OrgHome from "./pages/orgHome/OrgHome";

const App = () => {
  return (
    <div className="app">
      <Routes>
        <Route path="/" element={<OrgBody />}>
          <Route index element={<OrgRegister />} />
          <Route path="org" element={<OrgHome />} />
        </Route>
      </Routes>
    </div>
  );
};

export default App;
