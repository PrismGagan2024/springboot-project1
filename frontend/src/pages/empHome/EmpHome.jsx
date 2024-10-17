import axios from "axios";
import React, { useEffect, useState } from "react";
import { empRoute } from "../../routes";
import { toast } from "react-toastify";
import "./EmpHome.css";
import EmpBox from "../../components/empBox/EmpBox";

const EmpHome = () => {
  const [allEmp, setAllEmp] = useState([]);

  useEffect(() => {
    const fetchAllEmp = async () => {
      try {
        const res = await axios.get(`${empRoute}/all`);
        if (res?.data.success) {
          setAllEmp(res?.data.data);
        }
      } catch (err) {
        toast.error("Failed to fetch employees. Please try again later.");
      }
    };

    fetchAllEmp();
  }, []);

  return (
    <div className="emp-home">
      <div className="emp-homeContainer">
        <h1 className="heading">All Employees</h1>
        <div className="emp-boxContainer">
          {allEmp.map((emp) => (
            <EmpBox key={emp?.id} emp={emp} />
          ))}
        </div>
      </div>
    </div>
  );
};

export default EmpHome;
