import React, { useState } from "react";
import { updateBouncer } from "../services/BouncerService";

const BouncerDetails = ({ bouncer }) => {
  const [details, setDetails] = useState(bouncer);
  const [updateStatus, setUpdateStatus] = useState("");

  const handleChange = (event) => {
    setDetails({
      ...details,
      [event.target.name]: parseInt(event.target.value),
    });
  };

  const handleSubmit = async () => {
    try {
      await updateBouncer(details.id, details);
      setUpdateStatus("Update successful!");
    } catch (error) {
      setUpdateStatus(`Error updating bouncer: ${error.message}`);
    }
  };

  return (
    <div>
      <h2>Edit Bouncer</h2>
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
      <button onClick={handleSubmit}>Update</button>
      <div>{updateStatus}</div>
    </div>
  );
};

export default BouncerDetails;
