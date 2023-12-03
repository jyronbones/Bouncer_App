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

const BouncerList = () => {
  const [bouncers, setBouncers] = useState([]);
  const [selectedBouncer, setSelectedBouncer] = useState(null);
  const [showCreateForm, setShowCreateForm] = useState(false);
  const [showUpdateForm, setShowUpdateForm] = useState(false);
  const [formError, setFormError] = useState("");

  useEffect(() => {
    fetchBouncers();
  }, []);

  const fetchBouncers = async () => {
    try {
      const data = await getBouncers();
      setBouncers(data);
    } catch (error) {
      console.error("Error fetching bouncers:", error);
    }
  };

  const handleCreate = () => {
    setSelectedBouncer({ x: 0, y: 0, YVelocity: 0 });
    setShowCreateForm(true);
    setShowUpdateForm(false);
    setFormError("");
  };

  const handleUpdate = (bouncer) => {
    setSelectedBouncer(bouncer);
    setShowUpdateForm(true);
    setShowCreateForm(false);
    setFormError("");
  };

  const handleDelete = async (id) => {
    if (window.confirm("Are you sure you want to delete this bouncer?")) {
      try {
        await deleteBouncer(id);
        await fetchBouncers();
      } catch (error) {
        console.error("Error deleting bouncer:", error);
        setFormError(`Error deleting bouncer.`);
      }
    }
  };

  const handleSubmit = async (bouncerData, isCreate) => {
    try {
      if (isCreate) {
        await createBouncer(bouncerData);
        setShowCreateForm(false);
      } else {
        await updateBouncer(bouncerData.id, bouncerData);
        setShowUpdateForm(false);
      }
      await fetchBouncers();
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
