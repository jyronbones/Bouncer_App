import React, { useState, useEffect } from "react";
import { updateBouncer } from "../services/BouncerService";

const BouncerDetails = ({ bouncer, onSubmit }) => {
  const [details, setDetails] = useState({ ...bouncer });
  const [updateStatus, setUpdateStatus] = useState("");

  useEffect(() => {
    setDetails({ ...bouncer });
  }, [bouncer]);

  const handleChange = (event) => {
    setDetails({
      ...details,
      [event.target.name]: parseInt(event.target.value),
    });
  };

  const handleSubmit = () => {
    try {
      onSubmit(details);
      setUpdateStatus("Update successful!");
    } catch (error) {
      setUpdateStatus(`Error: ${error.message}`);
    }
  };

  return (
    <div className="overlay">
      <div className="overlay-content">
        <h2>{bouncer.id ? "Edit Bouncer" : "Create New Bouncer"}</h2>
        <label>
          X Position:
          <input
            type="number"
            name="x"
            value={details.x || ""}
            onChange={handleChange}
          />
        </label>
        <label>
          Y Position:
          <input
            type="number"
            name="y"
            value={details.y || ""}
            onChange={handleChange}
          />
        </label>
        <label>
          Y Velocity:
          <input
            type="number"
            name="yVelocity"
            value={details.yVelocity || ""}
            onChange={handleChange}
          />
        </label>
        <button onClick={handleSubmit}>
          {bouncer.id ? "Update" : "Create"}
        </button>
        <div>{updateStatus}</div>
      </div>
    </div>
  );
};

export default BouncerDetails;
