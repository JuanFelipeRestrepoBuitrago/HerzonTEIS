/**
 * @file Manages real-time bidding functionality for auction updates using WebSocket.
 * @module auctionLive
 */

// Importing Stomp and SockJS libraries for WebSocket communication
const socket = new SockJS('/auction/websocket');
const stompClient = Stomp.over(socket);

// Necessary DOM elements5
const auctionId = document.getElementById('auctionId').textContent;
const offerInput = document.getElementById('offerAmount');
const offerButton = document.getElementById('offerButton');
const messagesContainer = document.getElementById('messagesAlert');

/**
 * Initializes WebSocket connection and subscribes to auction updates
 * @function
 * @listens Stomp#connect
 */
stompClient.connect({}, function (frame) {
  console.log('Connected: ' + frame);

  /**
   * Subscribes to auction updates channel
   * @function
   * @param {Message} response - STOMP message containing auction data
   */
  stompClient.subscribe(`/topic/auction/updates/${auctionId}`, (response) => {
    const auctionUpdatedResponse = JSON.parse(response.body);
    updateAuctionUI(auctionUpdatedResponse);
  });
}, (error) => {
  console.error('Connection to live auction service failed:', error);
});

/**
 * Handles browser close/tab refresh to clean up WebSocket connection
 * @listens window:beforeunload
 */
window.addEventListener('beforeunload', () => {
  if (stompClient) stompClient.disconnect();
});

/**
 * Submits a new offer for the current auction
 * @async
 * @function
 * @throws {Error} When offer submission fails
 * @example
 * placeOffer(); // Called when user clicks bid button
 */
async function placeOffer() {
  const offerAmount = parseFloat(offerInput.value);
  offerButton.disabled = true;

  // Make a POST request to place the offer for the auction
  try {
    const response = await fetch(`/auction/offer/place`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        auctionId: parseInt(auctionId),
        offerPrice: offerAmount
      })
    });

    const responseData = await response.json();

    if (!response.ok) {
      // Display error messages if any
      if (responseData.error) {
        displayMessages(responseData.messages, responseData.error);
      } else {
        throw new Error('Oferta fallida');
      }
    } else {
      offerInput.value = '';
      displayMessages(responseData.messages, responseData.error);
    }
  } catch (error) {
    console.error('Error:', error);
    displayMessages(["La puja de tu oferta falló"], true);
  } finally {
    offerButton.disabled = false;
  }
}

/**
 * Updates UI with latest auction data
 * @function
 * @param {Object} offerAuctionResponse - Auction update payload
 * @param {number} offerAuctionResponse.currentPrice - Current auction price
 * @example
 * updateAuctionUI({ currentPrice: 1500.00 });
 */
function updateAuctionUI(offerAuctionResponse) {
  console.log(offerAuctionResponse);
  document.getElementById('current-price').innerText =
    offerAuctionResponse.currentPrice.toLocaleString('en-US', {
      style: 'currency',
      currency: 'USD',
    });
}

/**
 * Displays error or success messages on the UI
 * @function
 * @param {Array} messages - Array of messages to display
 * @param {boolean} error - Flag to indicate if messages are errors
 * @example
 * displayMessages(["Offer failed"], true);
 */
function displayMessages(messages, error) {
  messagesContainer.innerHTML = ''; // Clear any existing messages

  if (error) {
    //Add error alert
    messages.forEach(error => {
      const errorAlert = document.createElement('div');
      errorAlert.className = 'alert alert-danger alert-dismissible fade show mb-2';
      errorAlert.setAttribute('role', 'alert');

      // Add error message content
      errorAlert.innerHTML = `
        <div class="d-flex align-items-center">
          <i class="bi bi-exclamation-circle-fill me-2"></i>
          <strong>${error}</strong>
        </div>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" onclick="dismissMessage(this)"></button>
      `;

      // Append the error alert to the container
      messagesContainer.appendChild(errorAlert);
    });
  } else {
    // Add success alert
    messages.forEach(success => {
      const successAlert = document.createElement('div');
      successAlert.className = 'alert alert-success alert-dismissible fade show';
      successAlert.setAttribute('role', 'alert');

      // Add success message content
      successAlert.innerHTML = `
      <div class="d-flex align-items-center">
        <i class="bi bi-check-circle-fill me-2"></i>
        <strong>${success}</strong>
      </div>
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" onclick="dismissMessage(this)"></button>
    `;

      // Append the success alert to the container
      messagesContainer.appendChild(successAlert);
    });
  }

  // Show the error container
  messagesContainer.classList.remove('d-none');
}

/**
 * Dismisses a message from the UI
 * @function
 * @param {Element} button - Button element that triggered the dismiss action
 * @example
 * dismissMessage(this); // Called when user clicks the close button on an alert
 */
function dismissMessage(button) {
  // Remove the alert
  const messageAlert = button.closest('.alert');
  messageAlert.remove(); 

  // Hide the message container if there are no more messages
  if (messagesContainer.children.length === 0) {
    messagesContainer.classList.add('d-none');
  }
}

