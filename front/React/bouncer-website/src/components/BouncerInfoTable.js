import React from "react";
import "./BouncerInfoTable.css";

/**
 * Component to display a table of bouncers.
 * Provides options to create, update, or delete a bouncer.
 *
 * @param {Array} bouncers Array of bouncer objects to display.
 * @param {Function} onCreate Callback function for creating a new bouncer.
 * @param {Function} onUpdate Callback function for updating an existing bouncer.
 * @param {Function} onDelete Callback function for deleting a bouncer.
 */
const BouncerInfoTable = ({ bouncers, onCreate, onUpdate, onDelete }) => {
  return (
    <div className="bouncer-info-container">
      {/* Button to create a new bouncer */}
      <button onClick={onCreate}>Create New Bouncer</button>

      {/* Table displaying bouncer information */}
      <table className="bouncer-info-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>X Position</th>
            <th>Y Position</th>
            <th>Y Velocity</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {bouncers.map((bouncer) => (
            <tr key={bouncer.id}>
              <td>{bouncer.id}</td>
              <td>{bouncer.x}</td>
              <td>{bouncer.y}</td>
              <td>{bouncer.YVelocity || "N/A"}</td>
              <td>
                {/* Buttons for updating and deleting a bouncer */}
                <button onClick={() => onUpdate(bouncer)}>Update</button>
                <button onClick={() => onDelete(bouncer.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default BouncerInfoTable;
