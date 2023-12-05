import React, { useState, useEffect } from "react";
import "./BouncerDetails.css";

/**
 * Component for displaying and editing the details of a bouncer.
 * Allows for creating a new bouncer or updating an existing one.
 *
 * @param {Object} bouncer The bouncer object to be created or updated.
 * @param {Function} onSubmit Callback function to handle form submission.
 */
const BouncerDetails = ({ bouncer, onSubmit }) => {
  // State for storing bouncer details
  const [details, setDetails] = useState({ ...bouncer });

  // State for tracking the status of form submission
  const [updateStatus, setUpdateStatus] = useState("");

  // Effect to update state when the bouncer prop changes
  useEffect(() => {
    setDetails({ ...bouncer });
  }, [bouncer]);

  /**
   * Handles changes in form inputs.
   *
   * @param {Object} event The event object from the input change.
   */
  const handleChange = (event) => {
    setDetails({
      ...details,
      [event.target.name]: parseInt(event.target.value),
    });
  };

  /**
   * Handles form submission.
   * Calls the onSubmit prop function with the current details and handles response.
   */
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
        <form className="bouncer-details-form">
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
          <button type="button" onClick={handleSubmit}>
            {bouncer.id ? "Update" : "Create"}
          </button>
        </form>
        <div>{updateStatus}</div>
      </div>
    </div>
  );
};

export default BouncerDetails;
