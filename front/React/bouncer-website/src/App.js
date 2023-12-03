import React, { useState, useEffect } from "react";
import BouncerList from "./components/BouncerList";
import BouncerDetails from "./components/BouncerDetails";
import CanvasComponent from "./components/CanvasComponent";
import { getBouncers } from "./services/BouncerService";
import "./App.css";

function App() {
  const [bouncers, setBouncers] = useState([]);
  const [selectedBouncer, setSelectedBouncer] = useState(null);

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

    return () => clearInterval(interval);
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <BouncerList bouncers={bouncers} onBouncerSelect={setSelectedBouncer} />
        <CanvasComponent bouncers={bouncers} />
        {selectedBouncer && <BouncerDetails bouncer={selectedBouncer} />}
      </header>
    </div>
  );
}

export default App;
