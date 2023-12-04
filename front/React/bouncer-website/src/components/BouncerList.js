import React, { useState, useEffect } from "react";
import {
  getBouncers,
  createBouncer,
  updateBouncer,
  deleteBouncer,
} from "../services/BouncerService";
import BouncerInfoTable from "./BouncerInfoTable";
import BouncerDetails from "./BouncerDetails";
import "./BouncerList.css";
import "./BouncerDetails.css";

/**
 * Component for managing a list of bouncers.
 * Allows for creating, updating, and deleting bouncers.
 */
const BouncerList = () => {
  // State for storing the list of bouncers
  const [bouncers, setBouncers] = useState([]);

  // State for managing the selected bouncer
  const [selectedBouncer, setSelectedBouncer] = useState(null);

  // State for showing or hiding the create form
  const [showCreateForm, setShowCreateForm] = useState(false);

  // State for showing or hiding the update form
  const [showUpdateForm, setShowUpdateForm] = useState(false);

  // State for storing form error messages
  const [formError, setFormError] = useState("");

  // Effect to fetch bouncers from the server
  useEffect(() => {
    const fetchBouncers = async () => {
      try {
        const data = await getBouncers();
        setBouncers(data);
      } catch (error) {
        console.error("Error fetching bouncers:", error);
      }
    };

    fetchBouncers();
    const interval = setInterval(fetchBouncers, 1000);

    return () => clearInterval(interval);
  }, []);

  // Handler for creating a new bouncer
  const handleCreate = () => {
    setSelectedBouncer({ x: 0, y: 0, YVelocity: 0 });
    setShowCreateForm(true);
    setShowUpdateForm(false);
    setFormError("");
  };

  // Handler for updating an existing bouncer
  const handleUpdate = (bouncer) => {
    setSelectedBouncer(bouncer);
    setShowUpdateForm(true);
    setShowCreateForm(false);
    setFormError("");
  };

  // Handler for deleting a bouncer
  const handleDelete = async (id) => {
    if (window.confirm("Are you sure you want to delete this bouncer?")) {
      try {
        await deleteBouncer(id);
      } catch (error) {
        console.error("Error deleting bouncer:", error);
        setFormError(`Error deleting bouncer.`);
      }
    }
  };

  // Handler for form submission (create or update)
  const handleSubmit = async (bouncerData, isCreate) => {
    try {
      if (isCreate) {
        await createBouncer(bouncerData);
        setShowCreateForm(false);
      } else {
        await updateBouncer(bouncerData.id, bouncerData);
        setShowUpdateForm(false);
      }
      setFormError("");
    } catch (error) {
      setFormError(`Error ${isCreate ? "creating" : "updating"} bouncer.`);
      console.error(
        `Error ${isCreate ? "creating" : "updating"} bouncer:`,
        error
      );
    }
  };

  return (
    <div>
      {formError && <p className="error-message">{formError}</p>}
      <BouncerInfoTable
        bouncers={bouncers}
        onCreate={handleCreate}
        onUpdate={handleUpdate}
        onDelete={handleDelete}
      />
      {(showCreateForm || showUpdateForm) && (
        <div className="modal">
          <div className="modal-content">
            <BouncerDetails
              bouncer={selectedBouncer}
              onSubmit={(bouncerData) =>
                handleSubmit(bouncerData, showCreateForm)
              }
            />
            <button
              className="close-button"
              onClick={() => {
                setShowCreateForm(false);
                setShowUpdateForm(false);
                setFormError("");
              }}
            >
              Close
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default BouncerList;
