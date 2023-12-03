import React, { useState, useEffect } from "react";
import { getBouncers } from "../services/BouncerService";

const BouncerList = () => {
  const [bouncers, setBouncers] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    getBouncers()
      .then((data) => setBouncers(data))
      .catch((error) => {
        setError("Failed to fetch bouncers. Please try again later.");
        console.error("Error fetching bouncers:", error);
      });
  }, []);
};

export default BouncerList;
