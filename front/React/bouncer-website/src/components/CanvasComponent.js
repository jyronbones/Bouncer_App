import React, { useRef, useEffect } from "react";

const CanvasComponent = ({ bouncers }) => {
  const canvasRef = useRef(null);

  useEffect(() => {
    const canvas = canvasRef.current;
    const context = canvas.getContext("2d");

    let animationFrameId;

    const drawBouncer = (bouncer) => {
      context.beginPath();
      context.arc(bouncer.x, bouncer.y, 20, 0, 2 * Math.PI);
      const ballColor = `hsl(${Math.random() * 360}, 100%, 50%)`;
      context.fillStyle = ballColor;
      context.fill();
      context.stroke();

      context.fillStyle = "blue";
      context.font = "bold 14px Arial";
      context.textAlign = "center";

      context.fillText(bouncer.id, bouncer.x, bouncer.y + 5);
    };

    const render = () => {
      context.clearRect(0, 0, canvas.width, canvas.height);
      bouncers.forEach(drawBouncer);
      animationFrameId = window.requestAnimationFrame(render);
    };

    render();

    return () => {
      window.cancelAnimationFrame(animationFrameId);
    };
  }, [bouncers]);

  return (
    <canvas
      ref={canvasRef}
      width={800}
      height={600}
      style={{ border: "1px solid #000", background: "rgba(0, 0, 0, 0.1)" }}
    />
  );
};

export default CanvasComponent;
