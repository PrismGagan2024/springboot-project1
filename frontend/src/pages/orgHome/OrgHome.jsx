import axios from "axios";
import React, { useEffect, useState } from "react";
import { orgRoute } from "../../routes";
import { toast } from "react-toastify";
import "./OrgHome.css";
import OrgBox from "../../components/orgBox/OrgBox";

const OrgHome = () => {
  const [allOrg, setAllOrg] = useState([]);

  useEffect(() => {
    const fetchAllOrg = async () => {
      try {
        const res = await axios.get(`${orgRoute}/all`);
        if (res?.data.success) {
          setAllOrg(res?.data.data);
        }
      } catch (err) {
        toast.error("Failed to fetch organizations. Please try again later.");
      }
    };

    fetchAllOrg();
  }, [allOrg]);

  return (
    <div className="org-home">
      <div className="org-homeContainer">
        <h1 className="heading">All Organizations</h1>
        <div className="org-boxContainer">
          {allOrg.map((org) => (
            <OrgBox key={org?.id} org={org} />
          ))}
        </div>
      </div>
    </div>
  );
};

export default OrgHome;
