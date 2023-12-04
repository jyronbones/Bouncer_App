import React, { useState, useEffect } from "react";
import BouncerList from "./components/BouncerList";
import BouncerDetails from "./components/BouncerDetails";
import CanvasComponent from "./components/CanvasComponent";
import { getBouncers } from "./services/BouncerService";
import "./App.css";

/**
 * Main application component.
 * Manages the state and data fetching for bouncers.
 */
function App() {
  // State to hold the list of all bouncers
  const [bouncers, setBouncers] = useState([]);

  // State to hold the currently selected bouncer for detail viewing
  const [selectedBouncer, setSelectedBouncer] = useState(null);

  // Effect for fetching bouncer data from the server
  useEffect(() => {
    const fetchBouncers = async () => {
      try {
        const data = await getBouncers();
        setBouncers(data);
      } catch (error) {
        console.error("Error fetching bouncers: ", error);
      }
    };

    fetchBouncers();
    const interval = setInterval(fetchBouncers, 50);

    // Cleanup function to clear the interval when the component unmounts
    return () => clearInterval(interval);
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        {/* Bouncer list component displaying the list of bouncers */}
        <BouncerList bouncers={bouncers} onBouncerSelect={setSelectedBouncer} />

        {/* Canvas component for visual representation of bouncers */}
        <CanvasComponent bouncers={bouncers} />

        {/* Bouncer details component for viewing and editing bouncer details */}
        {selectedBouncer && <BouncerDetails bouncer={selectedBouncer} />}
      </header>
    </div>
  );
}

export default App;
