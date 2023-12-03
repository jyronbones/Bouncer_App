const API_BASE_URL = "http://localhost:8080/bouncer-fearnall/resources/bouncer";

export const getBouncers = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}`);
    if (!response.ok) {
      throw new Error("Network response was not ok");
    }
    return await response.json();
  } catch (error) {
    console.error("Error fetching bouncers:", error);
    throw error;
  }
};

export const createBouncer = async (bouncerData) => {
  try {
    const response = await fetch(`${API_BASE_URL}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(bouncerData),
    });

    // Check if the response status is 'ok' (status code in the range 200-299)
    if (!response.ok) {
      throw new Error(
        `Network response was not ok, status: ${response.status}`
      );
    }

    // Check for the content type of the response
    const contentType = response.headers.get("content-type");
    if (!contentType || !contentType.includes("application/json")) {
      throw new TypeError("Received non-JSON response from server");
    }

    return await response.json();
  } catch (error) {
    console.error("Error creating bouncer:", error);
    throw error; // Rethrow the error for further handling if necessary
  }
};

export const updateBouncer = async (id, bouncerData) => {
  try {
    const response = await fetch(`${API_BASE_URL}/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(bouncerData),
    });
    if (!response.ok) {
      throw new Error("Network response was not ok");
    }
    return await response.json();
  } catch (error) {
    console.error("Error updating bouncer:", error);
    throw error;
  }
};

export const deleteBouncer = async (id) => {
  try {
    const response = await fetch(`${API_BASE_URL}/${id}`, {
      method: "DELETE",
    });

    if (!response.ok) {
      throw new Error(
        `Network response was not ok, status: ${response.status}`
      );
    }

    // Check if the response has content
    const contentType = response.headers.get("content-type");
    if (contentType && contentType.includes("application/json")) {
      return await response.json();
    } else {
      // If no content, return a success message or empty object
      return { message: "Bouncer successfully deleted" };
    }
  } catch (error) {
    console.error("Error deleting bouncer:", error);
    throw error;
  }
};
