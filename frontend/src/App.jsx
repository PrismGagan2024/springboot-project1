import React from "react";
import "./App.css";
import { Routes, Route } from "react-router-dom";
import OrgBody from "./pages/body/OrgBody";
import OrgRegister from "./pages/orgRegister/OrgRegister";
import OrgHome from "./pages/orgHome/OrgHome";
import EmpBody from "./pages/body/EmpBody";
import EmpRegister from "./pages/empRegister/EmpRegister";
import EmpLogin from "./pages/empLogin/EmpLogin";
import EmpHome from "./pages/empHome/EmpHome";

const App = () => {
  return (
    <div className="app">
      <Routes>
        <Route path="/" element={<OrgBody />}>
          <Route index element={<OrgRegister />} />
          <Route path="org" element={<OrgHome />} />
        </Route>

        <Route path="/emp" element={<EmpBody />}>
          <Route index element={<EmpHome />} />
          <Route path="register" element={<EmpRegister />} />
          <Route path="login" element={<EmpLogin />} />
        </Route>
      </Routes>
    </div>
  );
};

export default App;
