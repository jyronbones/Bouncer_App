import React from "react";
import "./BouncerInfoTable.css";

const BouncerInfoTable = ({ bouncers }) => {
  return (
    <div className="bouncer-info-container">
      <table className="bouncer-info-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>X Position</th>
            <th>Y Position</th>
            <th>Y Velocity</th>
          </tr>
        </thead>
        <tbody>
          {bouncers.map((bouncer) => (
            <tr key={bouncer.id}>
              <td>{bouncer.id}</td>
              <td>{bouncer.x}</td>
              <td>{bouncer.y}</td>
              <td>{bouncer.YVelocity || "N/A"}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default BouncerInfoTable;
