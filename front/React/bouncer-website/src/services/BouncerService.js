const API_BASE_URL = "http://localhost:8080/bouncer-fearnall/resources/bouncer";

/**
 * Fetches and returns the list of all bouncers from the server.
 *
 * @returns {Promise<Array>} A promise that resolves to an array of bouncers.
 * @throws Will throw an error if the network response is not ok.
 */
export const getBouncers = async () => {
  try {
    const response = await fetch(API_BASE_URL, {
      method: 'GET',
      credentials: 'include', // Include credentials in the request
    });

    if (!response.ok) {
      throw new Error("Network response was not ok");
    }

    return await response.json();
  } catch (error) {
    console.error("Error fetching bouncers:", error);
    throw error;
  }
};

/**
 * Creates a new bouncer on the server.
 *
 * @param {Object} bouncerData The data of the bouncer to be created.
 * @returns {Promise<Object>} A promise that resolves to the created bouncer object.
 * @throws Will throw an error if the network response is not ok or non-JSON.
 */
export const createBouncer = async (bouncerData) => {
  try {
    const response = await fetch(API_BASE_URL, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(bouncerData),
      credentials: 'include', // Include credentials in the request
    });

    if (!response.ok) {
      throw new Error(
        `Network response was not ok, status: ${response.status}`
      );
    }

    const contentType = response.headers.get("content-type");
    if (!contentType || !contentType.includes("application/json")) {
      throw new TypeError("Received non-JSON response from server");
    }

    return await response.json();
  } catch (error) {
    console.error("Error creating bouncer:", error);
    throw error;
  }
};

/**
 * Updates an existing bouncer on the server.
 *
 * @param {number} id The ID of the bouncer to update.
 * @param {Object} bouncerData The updated data for the bouncer.
 * @returns {Promise<Object>} A promise that resolves to the updated bouncer object.
 * @throws Will throw an error if the network response is not ok.
 */
export const updateBouncer = async (id, bouncerData) => {
  try {
    const response = await fetch(`${API_BASE_URL}/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(bouncerData),
      credentials: 'include', // Include credentials in the request
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

/**
 * Deletes a bouncer from the server.
 *
 * @param {number} id The ID of the bouncer to delete.
 * @returns {Promise<Object>} A promise that resolves to a confirmation message.
 * @throws Will throw an error if the network response is not ok.
 */
export const deleteBouncer = async (id) => {
  try {
    const response = await fetch(`${API_BASE_URL}/${id}`, {
      method: "DELETE",
      credentials: 'include', // Include credentials in the request
    });

    if (!response.ok) {
      throw new Error(
        `Network response was not ok, status: ${response.status}`
      );
    }

    const contentType = response.headers.get("content-type");
    if (contentType && contentType.includes("application/json")) {
      return await response.json();
    } else {
      return { message: "Bouncer successfully deleted" };
    }
  } catch (error) {
    console.error("Error deleting bouncer:", error);
    throw error;
  }
};
