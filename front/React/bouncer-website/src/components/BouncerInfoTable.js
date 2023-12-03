import React from "react";
import "./BouncerInfoTable.css";

const BouncerInfoTable = ({ bouncers, onCreate, onUpdate, onDelete }) => {
  return (
    <div className="bouncer-info-container">
      <button onClick={onCreate}>Create New Bouncer</button>
      <table className="bouncer-info-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>X Position</th>
            <th>Y Position</th>
            <th>Y Velocity</th>
            <th>Actions</th>
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
