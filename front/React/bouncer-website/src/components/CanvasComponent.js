import React, { useRef, useEffect } from "react";

/**
 * Canvas component for rendering bouncers.
 *
 * @param {Array} bouncers Array of bouncer objects to be drawn on the canvas.
 */
const CanvasComponent = ({ bouncers }) => {
  // Reference to the canvas element
  const canvasRef = useRef(null);

  useEffect(() => {
    const canvas = canvasRef.current;
    const context = canvas.getContext("2d");

    // Load and set the background image for the canvas
    const backgroundImage = new Image();
    backgroundImage.src = "/images/space.png";

    // Variable to store the animation frame ID
    let animationFrameId;

    /**
     * Function to draw a bouncer on the canvas.
     *
     * @param {Object} bouncer The bouncer object to draw.
     */
    const drawBouncer = (bouncer) => {
      context.beginPath();
      context.arc(bouncer.x, bouncer.y, 20, 0, 2 * Math.PI);

      // Assign a random color to each bouncer
      const ballColor = `hsl(${Math.random() * 360}, 100%, 50%)`;
      context.fillStyle = ballColor;
      context.fill();
      context.stroke();

      // Draw bouncer ID text
      context.fillStyle = "blue";
      context.font = "bold 14px Arial";
      context.textAlign = "center";
      context.fillText(bouncer.id, bouncer.x, bouncer.y + 5);
    };

    /**
     * Render function to update the canvas.
     * Draws the background image and all bouncers.
     */
    const render = () => {
      context.drawImage(backgroundImage, 0, 0, canvas.width, canvas.height);
      bouncers.forEach(drawBouncer);
      animationFrameId = window.requestAnimationFrame(render);
    };

    // Start the rendering loop once the background image is loaded
    backgroundImage.onload = () => {
      render();
    };

    // Cleanup function to cancel the animation frame when the component unmounts
    return () => {
      window.cancelAnimationFrame(animationFrameId);
    };
  }, [bouncers]);

  return (
    <canvas
      ref={canvasRef}
      width={800}
      height={600}
      style={{ border: "1px solid #000" }}
      className="bouncer-canvas"
    />
  );
};

export default CanvasComponent;
