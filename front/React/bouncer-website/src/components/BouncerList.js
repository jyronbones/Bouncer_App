import React, { useState, useEffect } from "react";
import {
  getBouncers,
  createBouncer,
  updateBouncer,
  deleteBouncer,
} from "../services/BouncerService";
import BouncerInfoTable from "./BouncerInfoTable";
import BouncerDetails from "./BouncerDetails"; // Assuming this is used for update and create

const BouncerList = () => {
  const [bouncers, setBouncers] = useState([]);
  const [selectedBouncer, setSelectedBouncer] = useState(null);
  const [showCreateForm, setShowCreateForm] = useState(false);
  const [showUpdateForm, setShowUpdateForm] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchBouncers();
  }, []);

  const fetchBouncers = async () => {
    try {
      const data = await getBouncers();
      setBouncers(data);
    } catch (error) {
      setError("Failed to fetch bouncers. Please try again later.");
      console.error("Error fetching bouncers:", error);
    }
  };

  const handleCreate = () => {
    setSelectedBouncer({ x: 0, y: 0, YVelocity: 0 }); // Default values
    setShowCreateForm(true);
  };

  const handleUpdate = (bouncer) => {
    setSelectedBouncer(bouncer);
    setShowUpdateForm(true);
  };

  const handleDelete = async (id) => {
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this bouncer?"
    );
    if (confirmDelete) {
      try {
        await deleteBouncer(id);
        await fetchBouncers();
      } catch (error) {
        setError("Error deleting bouncer.");
        console.error("Error deleting bouncer:", error);
      }
    }
  };

  const handleSubmit = async (bouncerData, isCreate = false) => {
    try {
      if (isCreate) {
        await createBouncer(bouncerData);
        setShowCreateForm(false);
      } else {
        await updateBouncer(bouncerData.id, bouncerData);
        setShowUpdateForm(false);
      }
      await fetchBouncers();
    } catch (error) {
      setError(`Error ${isCreate ? "creating" : "updating"} bouncer.`);
      console.error(
        `Error ${isCreate ? "creating" : "updating"} bouncer:`,
        error
      );
    }
  };

  return (
    <div>
      {error && <p className="error-message">{error}</p>}
      <BouncerInfoTable
        bouncers={bouncers}
        onCreate={handleCreate}
        onUpdate={handleUpdate}
        onDelete={handleDelete}
      />
      {showCreateForm && (
        <BouncerDetails
          bouncer={selectedBouncer}
          onSubmit={(bouncerData) => handleSubmit(bouncerData, true)}
        />
      )}
      {showUpdateForm && (
        <BouncerDetails
          bouncer={selectedBouncer}
          onSubmit={(bouncerData) => handleSubmit(bouncerData, false)}
        />
      )}
    </div>
  );
};

export default BouncerList;
