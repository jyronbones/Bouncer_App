import { render, screen } from "@testing-library/react";
import App from "./App";

/**
 * Test suite for the App component.
 */
test("renders learn react link", () => {
  // Render the App component
  render(<App />);

  // Query the DOM for an element with text that matches 'learn react'
  const linkElement = screen.getByText(/learn react/i);

  // Assert that the queried element is in the document
  expect(linkElement).toBeInTheDocument();
});
