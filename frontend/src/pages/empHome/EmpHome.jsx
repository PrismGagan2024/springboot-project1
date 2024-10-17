import axios from "axios";
import React, { useEffect, useState } from "react";
import { empRoute } from "../../routes";
import { toast } from "react-toastify";
import "./EmpHome.css";
import EmpBox from "../../components/empBox/EmpBox";
import { useSearchParams } from "react-router-dom";

const EmpHome = () => {
  const [allEmp, setAllEmp] = useState([]);
  const [searchParams] = useSearchParams();
  const orgId = searchParams.get("org");

  const fetchAllEmp = async () => {
    try {
      const res = await axios.get(`${empRoute}/all?org=${orgId}`);
      if (res?.data.success) {
        setAllEmp(res?.data.data);
      }
    } catch (err) {
      toast.error("Failed to fetch employees. Please try again later.");
    }
  };

  useEffect(() => {
    fetchAllEmp();
  }, [orgId]);

  const handleDeleteEmployee = async (id) => {
    try {
      const res = await axios.delete(`${empRoute}/delete/${id}`);
      if (res?.data.success) {
        toast.success("Employee deleted successfully!");
        fetchAllEmp();
      }
    } catch (err) {
      toast.error("Failed to delete employee. Please try again later.");
    }
  };

  return (
    <div className="emp-home">
      <div className="emp-homeContainer">
        <h1 className="heading">All Employees</h1>
        <div className="emp-boxContainer">
          {allEmp.map((emp) => (
            <EmpBox
              key={emp?.id}
              emp={emp}
              handleDeleteEmployee={handleDeleteEmployee}
            />
          ))}
        </div>
      </div>
    </div>
  );
};

export default EmpHome;
